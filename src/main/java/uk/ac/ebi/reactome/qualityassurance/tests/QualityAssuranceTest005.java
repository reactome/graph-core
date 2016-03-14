package uk.ac.ebi.reactome.qualityassurance.tests;

import uk.ac.ebi.reactome.qualityassurance.QATest;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 07.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest005 extends QualityAssuranceAbstract {
    @Override
    String getName() {
        return "QATest005-PathwaysWithoutEvents";
    }

    @Override
    String getQuery() {
        return "Match (n:Pathway)<-[:created]-(a) Where NOT (n)-[:hasEvent]->() RETURN n.dbId AS dbId, n.stableIdentifier AS stId, " +
                "n.displayName AS name, a.displayName as author";
    }
}
