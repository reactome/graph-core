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
public class QualityAssuranceTest006 extends QualityAssuranceAbstract{

    @Override
    String getName() {
        return "EwasWithoutRelatedEntity";
    }

    @Override
    String getQuery() {
        return "Match (n:EntityWithAccessionedSequence)<-[:created]-(a) Where NOT (n)-[:referenceEntity]->() " +
                "RETURN n.dbId AS dbId, n.stableIdentifier AS stId, n.displayName AS name, a.displayName AS author";
    }
}
