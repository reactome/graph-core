package uk.ac.ebi.reactome.qa.tests;

import org.neo4j.ogm.model.Result;
import uk.ac.ebi.reactome.qa.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 08.03.16.
 * TODO Why do those Instances have no created attribute
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest018 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "DatabaseObjectsWithoutCreated";
    }

    @Override
    String getQuery() {
        return "Match (n:DatabaseObject) Where NOT ((n:InstanceEdit) OR (n:DatabaseIdentifier) OR (n:Taxon) OR (n:Person) " +
                "OR (n:ReferenceEntity)) AND NOT (n)-[:created]-() RETURN n.dbId AS dbId, n.displayName AS name";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result,path,"dbId","name");
    }
}