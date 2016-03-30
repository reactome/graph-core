package uk.ac.ebi.reactome.qa.tests;

import uk.ac.ebi.reactome.qa.QATest;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 07.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest012 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "SimpleEntityWithoutReferenceEntity";
    }

    @Override
    String getQuery() {
        return "Match (n:SimpleEntity)<-[:created]-(a) Where NOT (n)-[:referenceEntity]->() RETURN n.dbId AS dbId, " +
                "n.stableIdentifier AS stId, n.displayName AS name, a.displayName as author";
    }
}