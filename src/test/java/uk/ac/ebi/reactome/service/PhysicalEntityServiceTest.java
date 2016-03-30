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
import uk.ac.ebi.reactome.util.DatabaseObjectFactory;
import uk.ac.ebi.reactome.util.JunitHelper;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 03.03.16.
 */
@ContextConfiguration(classes = {MyConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PhysicalEntityServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    private static final Long dbId = 199420L;
    private static final String stId = "R-HSA-53787";

    @Autowired
    private PhysicalEntityService physicalEntityService;

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
    public void testFindByDbId() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing physicalEntityService.findByDbId");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = physicalEntityService.findByDbId(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

    @Test
    public void testFindByStId() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing physicalEntityService.findByStableIdentifier");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = physicalEntityService.findByStableIdentifier(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

    /**
     * Execution times do not give fair estimation since GkInstance does not fill any of the legacy fields.
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void findByIdWithLegacyFields() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing physicalEntityService.findByIdWithLegacyFields");

        DatabaseObject databaseObjectObserved = physicalEntityService.findByIdWithLegacyFields(dbId.toString());
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());

        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }
}