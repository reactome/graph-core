package org.reactome.server.graph.qa.tests;

import org.reactome.server.graph.qa.QATest;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 07.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest009 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "ComplexWithoutComponents";
    }

    @Override
    String getQuery() {
        return "Match (n:Component)<-[:created]-(a) Where NOT (n)-[:hasComponent]->() RETURN n.dbId AS dbId, " +
                "n.stableIdentifier AS stId, n.displayName AS name, a.displayName as author";

    }
}