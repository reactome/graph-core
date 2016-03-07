package uk.ac.ebi.reactome.qa.tests;

import org.springframework.stereotype.Component;
import uk.ac.ebi.reactome.qa.QATest;

import java.util.Collections;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 04.03.16.
 */
@QATest
public class QualityAssuranceTest001 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "SelfLoops";
    }

    @Override
    String getQuery() {
        return "Match (n)-[r]-(x),(n)-[:created]-(a) WHERE n=x RETURN  DISTINCT (n.dbId) AS dbIdA,n.stableIdentifier AS stIdA, " +
                "n.displayName AS nameA, x.dbId AS dbIdB, x.stableIdentifier AS stIdB, x.displayName AS nameB, a.displayName AS authorA";
    }

}
