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
public class QualityAssuranceTest015 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "CatalystActivityWithoutPhysicalEntity";
    }

    @Override
    String getQuery() {
        return "Match (n:CatalystActivity)<-[:created]-(a) Where NOT (n)-[:physicalEntity]->() RETURN n.dbId AS dbId, " +
                "n.displayName AS name, a.displayName as author";
    }

    @Override
    protected void printResult(Result result, Path path) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("dbId,name,author");
        for (Map<String, Object> map : result) {
            StringBuilder line = new StringBuilder();
            line.append(map.get("dbId"));
            line.append(",");
            line.append(map.get("name"));
            line.append(",");
            line.append("\"" + map.get("author") + "\"");
            lines.add(line.toString());
        }
        Files.write(path, lines, Charset.forName("UTF-8"));
    }
}