package org.reactome.server.tools.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.tools.config.MyConfiguration;
import org.reactome.server.tools.service.helper.RelationshipDirection;
import org.reactome.server.tools.util.DatabaseObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assume.assumeTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.04.16.
 */
@ContextConfiguration(classes = {MyConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class DetailsServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    private static final Long dbId = 1912416L;
    private static final String stId = "R-HSA-1912416";

    @Autowired
    private DetailsService detailsService;

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
    public void testFindWithRelationship() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing detailsService.findReverseReactionOrPrecedingEvent");
        long start, time;
        start = System.currentTimeMillis();
//        detailsService.findReverseReactionOrPrecedingEvent("70486", "reverseReaction", "precedingEvent");
        time = System.currentTimeMillis() - start;
        logger.info("findReverseReactionOrPrecedingEvent execution time: " + time + "ms");

//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
//        logger.info("Finished");
    }

    @Test
    public void testFindByDbId() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing detailsService.findById");
        long start, time;
        start = System.currentTimeMillis();
        detailsService.findById("70486", RelationshipDirection.OUTGOING);
//        ContentDetails cd = detailsService.contentDetails("70486");
        time = System.currentTimeMillis() - start;
        logger.info("findById execution time: " + time + "ms");

//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
//        logger.info("Finished");
    }
}