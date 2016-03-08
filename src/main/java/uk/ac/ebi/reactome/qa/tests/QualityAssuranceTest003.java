package uk.ac.ebi.reactome.qa.tests;

import org.neo4j.ogm.model.Result;
import uk.ac.ebi.reactome.qa.QATest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 07.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest003 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "DatabaseIdentifierWithoutIdentifier";
    }

    @Override
    String getQuery() {
        return "Match (n:DatabaseIdentifier)<-[:created]-(a) Where n.identifier is NULL RETURN n.dbId AS dbId, " +
                "n.stableIdentifier AS stId, n.displayName AS name, a.displayName as author";
    }
}