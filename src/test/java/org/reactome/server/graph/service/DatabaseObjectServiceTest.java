package org.reactome.server.graph.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.reactome.server.graph.util.JunitHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 * <p>
 * 507868 Will test wrong. Difference is that duplications are removed in the graph
 */
public class DatabaseObjectServiceTest extends BaseTest {

    private static final Long dbId = 5205685L;
    private static final String stId = "R-HSA-5205685";

    private static final List<Long> dbIds = Arrays.asList(1640170L, 73886L, 1500620L);
    private static final List<String> stIds = Arrays.asList("R-HSA-1640170", "R-HSA-73886", "R-HSA-1500620");
    private static final List<Object> ids = Arrays.asList(1640170L, 73886L, 1500620L, "R-HSA-199420");

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + DatabaseObjectServiceTest.class.getName() + "!!! --- \n");
    }

    @Test
    public void findByDbIdTest() throws Exception {

        logger.info("Started testing databaseObjectService.findByDbIdTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectService.findById(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

    @Test
    public void findByStIdTest() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing databaseObjectService.findByStIdTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectService.findById(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

    @Test
    public void findByDbIdNoRelationsTest() {

        logger.info("Started testing databaseObjectService.findByDbIdNoRelationsTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectService.findByIdNoRelations(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertEquals(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

    @Test
    public void findByStIdRelationsTest() {

        logger.info("Started testing databaseObjectService.findByStIdRelationsTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectService.findByIdNoRelations(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertEquals(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

    @Test
    public void testFindByDbIds() throws Exception {

        logger.info("Started testing databaseObjectService.findByDbId");
        long start, time;
        start = System.currentTimeMillis();

        Collection<DatabaseObject> databaseObjectsObserved = databaseObjectService.findByIdsNoRelations(dbIds);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(3, databaseObjectsObserved.size());
        logger.info("Finished");
    }

    @Test
    public void findByStIdsTest() {

        logger.info("Started testing databaseObjectService.findByStIds");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DatabaseObject> databaseObjectsObserved = databaseObjectService.findByIdsNoRelations(stIds);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(3, databaseObjectsObserved.size());
        logger.info("Finished");
    }

    @Test
    public void findByIdsTest() {

        logger.info("Started testing databaseObjectService.findByIdsTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DatabaseObject> databaseObjectsObserved = databaseObjectService.findByIdsNoRelations(ids);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(4, databaseObjectsObserved.size());
        logger.info("Finished");
    }

    public void useOldStableIdentifier() {
        logger.info("Started testing databaseObjectService.useOldStableIdentifier");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObject = databaseObjectService.findById("REACT_13");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals("The old StId for R-HSA-71291 is REACT_13. Wrong one found " + databaseObject.getStId(), "R-HSA-71291", databaseObject.getStId());
        logger.info("Finished");
    }
}