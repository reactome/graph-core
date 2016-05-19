package org.reactome.server.graph.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.qa.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 17.05.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest048 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "InstanceEditCreatesInstanceEdit";
    }

    @Override
    String getQuery() {
        return "Match (n:InstanceEdit)-[r:created]-(m:InstanceEdit) RETURN DISTINCT(n.dbId) AS dbIdA," +
                "n.displayName AS nameA, m.dbId AS dbIdB, m.displayName as nameB";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result,path,"dbIdA","nameA","dbIdB","nameB");
    }
}
