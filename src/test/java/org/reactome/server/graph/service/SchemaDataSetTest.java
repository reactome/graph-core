package org.reactome.server.graph.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.schema.SchemaDataSet;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * The documentation for schema validation is at https://developers.google.com/search/docs/guides/prototype
 *
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SchemaDataSetTest extends BaseTest {

    private static final String stId = "R-HSA-6807070";

    @Autowired
    private DatabaseObjectService dbs;

    @Autowired
    private GeneralService generalService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + SchemaDataSetTest.class.getName() + "!!! --- \n");
    }

    @Override
    @Before
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = generalService.fitForService();
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
        SchemaDataSet schemaDataSet = new SchemaDataSet(eventObserved, generalService.getDBVersion());

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(schemaDataSet);

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

            String response = IOUtils.toString(http.getInputStream(), "UTF-8");
            Boolean valid = response.contains("\"totalNumErrors\":0,\"totalNumWarnings\":0");
            assertTrue("The generated schema should not have errors neither warnings", valid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Finished");
    }

}
