package org.reactome.server.graph.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.Neo4jConfig;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@ContextConfiguration(classes = { Neo4jConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class PathwaysServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    @Autowired
    private GeneralService generalService;

    @Autowired
    private PathwaysService pathwaysService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + DetailsServiceTest.class.getName() + " !!! --- \n");
    }

    @AfterClass
    public static void tearDownClass() {
        logger.info("\n\n");
    }

    @Before
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = generalService.fitForService();
            checkedOnce = true;
        }
        assumeTrue(isFit);
        DatabaseObjectFactory.clearCache();
    }

    @Test
    public void getContainedEventsByStIdTest(){
        logger.info("Started testing pathwaysService.getContainedEventsByStIdTest");
        long start = System.currentTimeMillis();
        Collection<Event> events = pathwaysService.getContainedEvents("R-HSA-5673001");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", events.isEmpty());
    }

    @Test
    public void getContainedEventsByDbIdTest(){
        logger.info("Started testing pathwaysService.getContainedEventsByDbIdTest");
        long start = System.currentTimeMillis();
        Collection<Event> events = pathwaysService.getContainedEvents(5673001L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", events.isEmpty());
    }

    @Test
    public void getPathwaysForTest(){
        logger.info("Started testing fireworksService.getPathwaysForTest");
        long start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = pathwaysService.getPathwaysFor("R-ALL-113592", 48887L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 429 or more pathways with ATP (R-ALL-113592) in human", pathways.size() >= 429);
    }

    @Test
    public void getPathwaysForAllFormsOfTest(){
        logger.info("Started testing fireworksService.getPathwaysForAllFormsOfTest");
        long start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = pathwaysService.getPathwaysForAllFormsOf("R-ALL-113592", 48887L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 591 or more pathways for all forms of ATP (R-ALL-113592) in human", pathways.size() >= 591);
    }

    @Test
    public void getPathwaysWithDiagramForTest(){
        logger.info("Started testing fireworksService.getPathwaysWithDiagramForTest");
        long start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = pathwaysService.getPathwaysWithDiagramFor("R-HSA-199420", 48887L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 5 or more pathways with diagram PTEN (R-HSA-199420) in human", pathways.size() >= 5);
    }

    @Test
    public void getPathwaysWithDiagramForAllFormsOfTest(){
        logger.info("Started testing fireworksService.getPathwaysWithDiagramForAllFormsOfTest");
        long start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = pathwaysService.getPathwaysWithDiagramForAllFormsOf("R-HSA-199420", 48887L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 5 or more pathways with diagram for all forms of PTEN (R-HSA-199420) in human", pathways.size() >= 6);
    }

    @Test
    public void getLowerLevelPathwaysForIdentifierTest(){
        logger.info("Started testing pathwaysService.getLowerLevelPathwaysForIdentifierTest");
        long start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = pathwaysService.getLowerLevelPathwaysForIdentifier("PTEN", 48887L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 9 or more pathways containing PTEN in human", pathways.size() >= 9);
    }

    @Test
    public void getPathwaysForIdentifierTest(){
        logger.info("Started testing pathwaysService.getPathwaysForIdentifierTest");
        long start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = pathwaysService.getPathwaysForIdentifier("POM121C", "189200","R-HSA-1483249");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 1 or more pathways containing POM121C", pathways.size() >= 0);
    }

    @Test
    public void getDiagramEntitiesForIdentifierTest(){
        logger.info("Started testing pathwaysService.getDiagramEntitiesForIdentifierTest");
        long start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> entities = pathwaysService.getDiagramEntitiesForIdentifier("R-HSA-189200", "POM121C");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be more than 1 entity in the pathway containing POM121C", entities.size() >= 0);
    }
}
