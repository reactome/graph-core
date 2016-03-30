package uk.ac.ebi.reactome.qa.tests;

import org.neo4j.ogm.model.Result;
import uk.ac.ebi.reactome.qa.QATest;

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
public class QualityAssuranceTest035 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "CrossReferenceRelationshipDuplication";
    }

    @Override
    String getQuery() {
        return "Match (x)-[r:crossReference]->(y) OPTIONAL MATCH (x)<-[:created]-(a) WITH x,y,r,a WHERE r.stoichiometry > 1  " +
                "Return DISTINCT(x.dbId) AS dbIdA,x.stableIdentifier AS stIdA, x.displayName AS nameA, y.dbId AS dbIdB, " +
                "y.stableIdentifier AS stIdB, y.displayName AS nameB, a.displayName AS author";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result,path,"dbIdA","stIdA","nameA","dbIdB","stIdB","nameB","author");
    }
}
