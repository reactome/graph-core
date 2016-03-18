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
public class QualityAssuranceTest047 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "QATest046-OrphanEventsComputationalInferred";
    }

    @Override
    String getQuery() {
        return "Match (n:Event) OPTIONAL MATCH (n)<-[:created]-(a) WITH n,a WHERE NOT (n)<-[:hasEvent]-() " +
                "AND NOT (n:TopLevelPathway) AND n.isInferred = true Return DISTINCT(n.dbId) AS dbId, " +
                "n.displayName AS name, n.stableIdentifier as stId, a.displayName AS author";
    }
}