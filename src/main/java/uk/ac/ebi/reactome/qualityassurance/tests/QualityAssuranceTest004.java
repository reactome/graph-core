package uk.ac.ebi.reactome.qualityassurance.tests;

import org.neo4j.ogm.model.Result;
import uk.ac.ebi.reactome.qualityassurance.QATest;

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
public class QualityAssuranceTest004 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "QATest004-EntriesWithoutDisplayName";
    }

    @Override
    String getQuery() {
        return "Match (n)<-[:created]-(x) Where n.displayName is NULL RETURN n.dbId AS dbId, x.displayName as author";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result,path,"dbId","author");
    }
}
