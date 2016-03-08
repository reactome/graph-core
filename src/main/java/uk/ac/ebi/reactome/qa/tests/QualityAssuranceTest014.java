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
public class QualityAssuranceTest014 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "InstanceEditWithoutAuthor";
    }

    @Override
    String getQuery() {
        return "Match (n:InstanceEdit) Where NOT (n)<-[:author]-() RETURN n.dbId AS dbId";
    }

    @Override
    protected void printResult(Result result, Path path) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("dbId");
        for (Map<String, Object> map : result) {
            StringBuilder line = new StringBuilder();
            line.append(map.get("dbId"));
            lines.add(line.toString());
        }
        Files.write(path, lines, Charset.forName("UTF-8"));
    }
}