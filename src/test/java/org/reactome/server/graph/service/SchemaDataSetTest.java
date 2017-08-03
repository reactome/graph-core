package org.reactome.server.graph.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.schema.SchemaDataSet;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assume.assumeTrue;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SchemaDataSetTest extends BaseTest {

    private static final String stId = "R-HSA-446203";

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
    public void schemaDataSetTest() throws InvocationTargetException, IllegalAccessException {

        logger.info("Testing schema.org DataSet.");

        Event eventObserved = dbs.findByIdNoRelations(stId);
        SchemaDataSet schemaDataSet = new SchemaDataSet(eventObserved, generalService.getDBVersion());

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(schemaDataSet);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.info("Finished");
    }

}
