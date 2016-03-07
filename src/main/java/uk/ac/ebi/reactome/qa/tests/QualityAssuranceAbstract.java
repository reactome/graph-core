package uk.ac.ebi.reactome.qa.tests;

import org.neo4j.ogm.model.Result;
import uk.ac.ebi.reactome.qa.QualityAssurance;
import uk.ac.ebi.reactome.service.GenericService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 04.03.16.
 */
public abstract class QualityAssuranceAbstract implements QualityAssurance {

    private static final String pathToFile = "./target/qATests/";

    abstract String getName();
    abstract String getQuery();

    protected Map getMap() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public void run(GenericService genericService) {

        Result result = genericService.query(getQuery(),getMap());
        if (result == null || !result.iterator().hasNext()) return;
        try {
            Path path = createFile();
            printResult(result,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            line.append("\"" + map.get("authorA") + "\"");
            lines.add(line.toString());
        }
        Files.write(path, lines, Charset.forName("UTF-8"));
    }

    private Path createFile() throws IOException {
        Path path = Paths.get(pathToFile + getName() + ".csv");
        Files.deleteIfExists(path);
        Files.createDirectories(path.getParent());
        Files.createFile(path);
        return path;
    }
}
