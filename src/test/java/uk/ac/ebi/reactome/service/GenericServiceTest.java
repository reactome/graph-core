package uk.ac.ebi.reactome.service;

import org.junit.AfterClass;
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
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.Pathway;
import uk.ac.ebi.reactome.util.DatabaseObjectFactory;
import uk.ac.ebi.reactome.util.JunitHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
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

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    private static final Long dbId = 5205685L;
    private static final String stId = "R-HSA-5205685";

    @Autowired
    private GenericService genericService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running DatabaseObjectServiceTests !!! --- \n");
    }

    @AfterClass
    public static void tearDownClass() {
        logger.info("\n\n");
    }

    @Before
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = genericService.fitForService();
            checkedOnce = true;
        }
        assumeTrue(isFit);
        genericService.clearCache();
        DatabaseObjectFactory.clearCache();
    }

    @Test
    public void findById() {

        Long id = genericService.findByDbId(DatabaseObject.class, dbId, 0).getId();
        logger.info("Started testing genericService.findById");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = genericService.findById(DatabaseObject.class, id, 0);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(dbId, databaseObjectObserved.getDbId());
        logger.info("Finished");
    }

    @Test
    public void testFindByProperty() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing genericService.findByProperty");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = genericService.findByProperty(DatabaseObject.class, "dbId", dbId, 1);
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

        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
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

        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

    /**
     * This method can hardly be tested. GkInstance does not provide any comparison since pagination is not possible.
     * @throws ClassNotFoundException
     */
    @Test
    public void testGetObjectsByClassName() throws ClassNotFoundException {

        logger.info("Started testing genericService.getObjectsByClassName");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = genericService.getObjectsByClassName(Pathway.class.getSimpleName(), 1, 25);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(25, pathways.size());
        logger.info("Finished");
    }

    @Test
    public void testFindByPropertyWithRelations() throws ClassNotFoundException {

        logger.info("Started testing genericService.getObjectsByClassName");
        long start, time;
        start = System.currentTimeMillis();
        Pathway pathway = (Pathway) genericService.findByPropertyWithRelations("dbId",dbId,"hasEvent");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(pathway.getHasEvent());
        logger.info("Finished");
    }

    @Test
    public void testFindByPropertyWithoutRelations() throws ClassNotFoundException {

        logger.info("Started testing genericService.getObjectsByClassName");
        long start, time;
        start = System.currentTimeMillis();
        Pathway pathway = (Pathway) genericService.findByPropertyWithoutRelations("dbId",dbId,"authored","created","edited","modified","revised","reviewed");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNull(pathway.getAuthored());
        assertNull(pathway.getCreated());
        assertNull(pathway.getEdited());
        assertNull(pathway.getModified());
        assertNull(pathway.getRevised());
        assertNull(pathway.getReviewed());
        logger.info("Finished");
    }

    @SuppressWarnings("unused")
    @Test
    public void testFindTopLevelPathways() {

        logger.info("Started testing genericService.findTopLevelPathways");
        long start, time;
        start = System.currentTimeMillis();
        Set<Pathway> observedTlps = new HashSet<>(genericService.findTopLevelPathways());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        Set<DatabaseObject> expectedTlps = new HashSet<>(DatabaseObjectFactory.loadFrontPageItems());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertEquals(expectedTlps, observedTlps);
        logger.info("Finished");
    }

    @SuppressWarnings("unused")
    @Test
    public void testFindTopLevelPathwaysWithId() {

        logger.info("Started testing genericService.findTopLevelPathwaysWithId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> observedTlps = genericService.findTopLevelPathways(48887L);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(24,observedTlps.size());
        logger.info("Finished");
    }

    @SuppressWarnings("unused")
    @Test
    public void testFindTopLevelPathwaysWithName() {

        logger.info("Started testing genericService.findTopLevelPathwaysWithId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> observedTlps = genericService.findTopLevelPathways("Homo sapiens");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(24,observedTlps.size());
        logger.info("Finished");
    }

    /**
     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
     * change when content is added to reactome.
     */
    @SuppressWarnings("unused")
    @Test
    public void testCountEntries() {

        logger.info("Started testing genericService.countEntries");
        long start, time;
        start = System.currentTimeMillis();
        long count = genericService.countEntries(Pathway.class);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(20418L,count);
        logger.info("Finished");
    }
}
