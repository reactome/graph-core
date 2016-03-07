package uk.ac.ebi.reactome.qa.tests;

import uk.ac.ebi.reactome.qa.QATest;

import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 07.03.16.
 */
@QATest
public class QualityAssuranceTest002 extends QualityAssuranceAbstract{

    @Override
    String getName() {
        return "2RelationshipsPointingToSameNode";
    }

    @Override
    String getQuery() {
        return "Match (n)-[r]->(x),(n)-[e]->(x),(n)<-[:created]-(a) WHERE NOT (n)-[:author|created|edited|modified|revised|reviewed]-(x)" +
                " RETURN DISTINCT (n.dbId) AS dbIdA,n.stableIdentifier AS stIdA, n.displayName AS nameA, x.dbId AS dbIdB," +
                " x.stableIdentifier AS stIdB, x.displayName AS nameB, a.displayName AS authorA";
    }


}
