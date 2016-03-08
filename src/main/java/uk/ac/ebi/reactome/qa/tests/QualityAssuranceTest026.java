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
 * @since 08.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest026 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "HumanEventsWithCyclicPrecedingEvents";
    }

    @Override
    String getQuery() {
        return "Match (n)-[r:precedingEvent]->(x),(n)<-[e]-(x),(n)-[:species]->(s),(n)<-[:created]-(a) " +
                "Where s.displayName=\"Homo sapiens\" RETURN DISTINCT(n.dbId) AS dbIdA,n.stableIdentifier AS stIdA, " +
                "n.displayName AS nameA, x.dbId AS dbIdB, x.stableIdentifier AS stIdB, x.displayName AS nameB, a.displayName AS author";
    }

    @Override
    protected void printResult(Result result, Path path) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("dbIdA,stIdA,nameA,dbIdB,stIdB,nameB,authorA");
        for (Map<String, Object> map : result) {
            StringBuilder line = new StringBuilder();
            line.append(map.get("dbIdA"));
            line.append(",");
            line.append(map.get("stIdA"));
            line.append(",");
            line.append("\"" + map.get("nameA") + "\"");
            line.append(",");
            line.append(map.get("dbIdB"));
            line.append(",");
            line.append(map.get("stIdB"));
            line.append(",");
            line.append("\"" + map.get("nameB") + "\"");
            line.append(",");
            line.append("\"" + map.get("author") + "\"");
            lines.add(line.toString());
        }
        Files.write(path, lines, Charset.forName("UTF-8"));
    }
}

