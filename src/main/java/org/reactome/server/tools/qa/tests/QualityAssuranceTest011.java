package org.reactome.server.tools.qa.tests;

import org.reactome.server.tools.qa.QATest;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 07.03.16.
 */

@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest011 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "PolymerWithoutRepeatedUnit";
    }

    @Override
    String getQuery() {
        return "Match (n:Polymer)<-[:created]-(a) Where NOT (n)-[:repeatedUnit]->() RETURN n.dbId AS dbId, " +
                "n.stableIdentifier AS stId, n.displayName AS name, a.displayName as author";

    }
}
