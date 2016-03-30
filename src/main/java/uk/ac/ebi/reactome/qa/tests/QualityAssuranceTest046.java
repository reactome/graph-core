package uk.ac.ebi.reactome.qa.tests;

import uk.ac.ebi.reactome.qa.QATest;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 18.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest046 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "OrphanEventsNotComputationalInferred";
    }

    @Override
    String getQuery() {
        return "Match (n:Event) OPTIONAL MATCH (n)<-[:created]-(a) WITH n,a wHERE NOT (n)<-[:hasEvent]-() " +
                "AND NOT (n:TopLevelPathway) AND n.isInferred = false Return DISTINCT(n.dbId) AS dbId, " +
                "n.displayName AS name, n.stableIdentifier as stId, a.displayName AS author";
    }
}



