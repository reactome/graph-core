package org.reactome.server.tools.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.tools.qa.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest034 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "ModifiedRelationshipDuplication";
    }

    @Override
    String getQuery() {
        return "Match (n)<-[r:modified]-(m),(n)<-[:created]-(a) WHERE r.stoichiometry > 1 Return DISTINCT(n.dbId) AS dbIdA, " +
                "n.displayName AS nameA, m.dbId AS dbIdB, m.displayName AS nameB, a.displayName AS author";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result,path,"dbIdA","nameA","dbIdB","nameB","author");
    }
}
