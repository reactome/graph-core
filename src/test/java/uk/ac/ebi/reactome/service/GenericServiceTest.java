package uk.ac.ebi.reactome.service;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.reactome.config.MyConfiguration;
import uk.ac.ebi.reactome.data.DatabaseObjectFactory;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.util.JunitHelper;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 25.01.16.
 */
@ContextConfiguration(classes = { MyConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class GenericServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static final Long dbId = 5205685L;
    private static final String stId = "R-HSA-5205685";

    @Autowired
    private GenericService genericService;

    @BeforeClass
    public static void setUpClass () {
        logger.info("\n --- Running GenericServiceTests --- \n");
    }
    
    /**
     *
     */
    @Before
    public void setUp() throws Exception {
        genericService.findByDbId(DatabaseObject.class,1l,0);
        DatabaseObjectFactory.createObject("1");
        DatabaseObjectFactory.clearCache();
    }

    @After
    public void tearDown() {}

    @Test
    public void testLoadByProperty() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing genericService.loadByProperty");
        long start, time;

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = genericService.loadByProperty(DatabaseObject.class, "dbId", dbId, 1);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        databaseObjectExpected.load();
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
    }

//    @Test
//    public void testLoadById() {
//
//    }

    @Test
    public void testFindByDbId() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing genericService.findByDbId");
        long start, time;

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = genericService.findByDbId(DatabaseObject.class, dbId, 1);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        databaseObjectExpected.load();
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected,databaseObjectObserved);
    }

    @Test
    public void testFindByStableIdentifier() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing genericService.findByStableIdentifier");
        long start, time;

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = genericService.findByStableIdentifier(DatabaseObject.class, stId, 1);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(stId);
        databaseObjectExpected.load();
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected,databaseObjectObserved);
    }

//    @Test
//    public void testCountEntries() {
//
//    }
}
