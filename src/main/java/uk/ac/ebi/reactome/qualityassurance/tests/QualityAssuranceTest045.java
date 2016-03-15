package uk.ac.ebi.reactome.qualityassurance.tests;

import org.neo4j.ogm.model.Result;
import uk.ac.ebi.reactome.qualityassurance.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest045 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "QATest045-OtherRelationshipDuplication";
    }

    @Override
    String getQuery() {
        return "Match (n)<-[r]-(m),(n)<-[:created]-(a) WHERE NOT ()-[r:hasComponent|input|output|repeatedUnit|modified|" +
                "crossReference|author|literatureReference|hasModifiedResidue|precedingEvent|hasMember|summation|psiMod|" +
                "hasCandidate|hasEvent]-() AND r.stoichiometry > 1 Return DISTINCT(n.dbId) AS dbIdA, n.displayName AS nameA," +
                " m.dbId AS dbIdB, m.displayName AS nameB, a.displayName AS author";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result,path,"dbIdA","nameA","dbIdB","nameB","author");
    }
}



