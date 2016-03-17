package uk.ac.ebi.reactome.qa.tests;

import org.apache.commons.lang.StringUtils;
import org.neo4j.ogm.model.Result;
import uk.ac.ebi.reactome.qa.QualityAssurance;
import uk.ac.ebi.reactome.service.GenericService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @SuppressWarnings({"SameReturnValue", "WeakerAccess"})
    protected Map getMap() {
        return Collections.EMPTY_MAP;
    }

    @SuppressWarnings("unchecked")
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

    void printResult(Result result, Path path) throws IOException {
        print(result,path, "dbId","stId","name","author");
    }

    void print (Result result, Path path, String... attributes) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(StringUtils.join(attributes,","));
        for (Map<String, Object> map : result) {
            String line = "";
            for (String attribute : attributes) {
                line += "\"" + map.get(attribute) + "\",";
            }
            lines.add(line);
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
