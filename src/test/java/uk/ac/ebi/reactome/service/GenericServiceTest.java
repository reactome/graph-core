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
import uk.ac.ebi.reactome.util.DatabaseObjectFactory;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.Pathway;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

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

    private static final Long dbId = 400253l;
    private static final String stId = "R-HSA-400253";

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
        assumeTrue(genericService.fitForService());
        genericService.clearCache();
        DatabaseObjectFactory.clearCache();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void findByPropertyWithRelations() {
        genericService.findByPropertyWithRelations("dbId",1l);
    }

    @Test
    public void findByPropertyWithoutRelations() {
        genericService.findByPropertyWithoutRelations("dbId",1l);
    }
    @Test
    public void findById() {
        genericService.findById(DatabaseObject.class,1l,0);
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
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
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
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
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
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
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

        logger.info("Started testing genericService.findTopLevelPathways");
        long start, time;

        start = System.currentTimeMillis();
        Collection<Pathway> observedTlps = genericService.findTopLevelPathways();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        Collection<DatabaseObject> expectedTlps = DatabaseObjectFactory.loadFrontPageItems();
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

//        assertEquals(expectedTlps.size(),observedTlps.size());
//        for (DatabaseObject expectedTlp : expectedTlps) {
//            observedTlps.contains(expectedTlp);
//        }
    }

    @Test
    public void testFindTopLevelPathwaysWithId() {

        logger.info("Started testing genericService.findTopLevelPathways");
        long start, time;

        start = System.currentTimeMillis();
        Collection<Pathway> observedTlps = genericService.findTopLevelPathways(1l);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");


//        assertEquals(expectedTlps.size(),observedTlps.size());
//        for (DatabaseObject expectedTlp : expectedTlps) {
//            observedTlps.contains(expectedTlp);
//        }
    }


    @Test
    public void testFindTopLevelPathwaysWithString() {
        Collection<Pathway> tlps = genericService.findTopLevelPathways("Homo sapiens");

//        TODO cannot test DatabaseObjectFactory cant do this
        System.out.println();
    }

    @Test void countEntries() {
        genericService.countEntries(DatabaseObject.class);
    }

}
