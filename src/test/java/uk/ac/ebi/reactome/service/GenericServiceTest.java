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
import uk.ac.ebi.reactome.domain.model.Pathway;
import uk.ac.ebi.reactome.util.JunitHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

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

//    private static final Long dbId = 507868L;  /
//    private static final String stId = "R-HSA-507868";

    @Autowired
    private GenericService genericService;

    @BeforeClass
    public static void setUpClass() {
        logger.info("\n");
        logger.info(" --- Running GenericServiceTests --- \n");
    }

    /**
     * clears neo4j cache, needed because otherwise smart object mapping will populate previously loaded objects
     */
    @Before
    public void setUp() throws Exception {
        genericService.findByDbId(DatabaseObject.class, 1l, 0);
        genericService.clearCache();
        DatabaseObjectFactory.createObject("1");
        DatabaseObjectFactory.clearCache();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLoadByProperty() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing genericService.loadByProperty");
        long start, time;

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = genericService.findByProperty(DatabaseObject.class, "dbId", dbId, 1);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
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
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
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
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
    }

    @Test
    public void testCountEntries() {

    }

    @Test
    public void testGetObjectsByClassName() throws ClassNotFoundException {

        Collection<Pathway> pathways =  genericService.getObjectsByClassName(Pathway.class.getSimpleName(), 1, 25);
        System.out.println("");
    }

    @Test
    public void testFindTopLevelPathways() {
        Collection<Pathway> tlps = genericService.findTopLevelPathways();
        System.out.println();
    }

    @Test
    public void testFindTopLevelPathwayss() {
        Collection<Pathway> tlps = genericService.findTopLevelPathways("Homo sapiens");
        System.out.println();
    }


}
