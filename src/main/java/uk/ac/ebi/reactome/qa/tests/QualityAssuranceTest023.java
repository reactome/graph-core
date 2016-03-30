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
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest023 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "DatabaseIdentifierWithoutReferenceDatabase";
    }

    @Override
    String getQuery() {
        return "Match (n:DatabaseIdentifier)<-[:created]-(a) Where NOT (n)-[:referenceDatabase]->() RETURN n.dbId AS dbId, " +
                "n.displayName AS name, a.displayName as author";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result,path,"dbId","name","author");
    }
}




