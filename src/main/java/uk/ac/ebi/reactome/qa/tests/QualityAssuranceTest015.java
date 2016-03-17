package uk.ac.ebi.reactome.qa.tests;

import org.neo4j.ogm.model.Result;
import uk.ac.ebi.reactome.qa.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 07.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest015 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "QATest015-CatalystActivityWithoutPhysicalEntity";
    }

    @Override
    String getQuery() {
        return "Match (n:CatalystActivity)<-[:created]-(a) Where NOT (n)-[:physicalEntity]->() RETURN n.dbId AS dbId, " +
                "n.displayName AS name, a.displayName as author";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result,path,"dbId","name","author");
    }
}