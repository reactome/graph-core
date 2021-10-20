package org.reactome.server.graph.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.schema.SchemaDataSet;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * The documentation for schema validation is at https://developers.google.com/search/docs/guides/prototype
 */
public class SchemaDataSetTest extends BaseTest {

    private static final String stId = "R-HSA-6807070";

    @Autowired
    private DatabaseObjectService dbs;

    @Autowired
    private GeneralService generalService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + SchemaDataSetTest.class.getName() + "!!! --- \n");
    }

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = fitForService();
            checkedOnce = true;
        }

        //*******   ENABLING LAZY LOADING FOR A PROPER TESTING  *********
        lazyFetchAspect.setEnableAOP(true);

        assumeTrue(isFit);
        DatabaseObjectFactory.clearCache();
    }

    @Test
    public void schemaDataSetTest() {

        logger.info("Testing schema.org DataSet.");

        Event eventObserved = dbs.findByIdNoRelations(stId);
        SchemaDataSet schemaDataSet = new SchemaDataSet(eventObserved, generalService.getDBInfo().getVersion());

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(schemaDataSet);

            // How to test online ?
            // https://search.google.com/structured-data/testing-tool and post the json output

            URL url = new URL("https://search.google.com/structured-data/testing-tool/validate");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            Map<String, String> arguments = new HashMap<>();
            arguments.put("html", json);
            StringJoiner sj = new StringJoiner("&");
            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try (OutputStream os = http.getOutputStream()) {
                os.write(out);
            }

            String response = new BufferedReader(new InputStreamReader(http.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));
            boolean valid = response.contains("\"totalNumErrors\":0,\"totalNumWarnings\":0");
            assertTrue("The generated schema should not have errors neither warnings", valid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Finished");
    }

}
