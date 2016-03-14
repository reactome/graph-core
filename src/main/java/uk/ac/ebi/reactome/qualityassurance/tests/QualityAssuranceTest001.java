package uk.ac.ebi.reactome.qualityassurance.tests;

import uk.ac.ebi.reactome.qualityassurance.QATest;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 04.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest001 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "QATest001-DatabaseObjectsWithSelfLoops";
    }

    @Override
    String getQuery() {
        return "Match (n)-[r]-(m),(n)-[:created]-(a) WHERE n=m RETURN  DISTINCT (n.dbId) AS dbId,n.stableIdentifier AS stId, " +
                "n.displayName AS name, a.displayName AS author";
    }

}
