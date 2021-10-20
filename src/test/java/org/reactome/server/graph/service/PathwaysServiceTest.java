package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class PathwaysServiceTest extends BaseTest {

    @Autowired
    private PathwaysService pathwaysService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + PathwaysServiceTest.class.getName() + " !!! --- \n");
    }

    @AfterTestClass
    public void tearDownClass() {
        logger.info("\n\n");
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
        Collection<Pathway> pathways = pathwaysService.getPathwaysFor("R-ALL-113592", "9606");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 429 or more pathways with ATP (R-ALL-113592) in human", pathways.size() >= 429);
    }

    @Test
    public void getPathwaysForAllFormsOfTest(){
        logger.info("Started testing fireworksService.getPathwaysForAllFormsOfTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwaysService.getPathwaysForAllFormsOf("R-ALL-113592", "9606");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 591 or more pathways for all forms of ATP (R-ALL-113592) in human", pathways.size() >= 591);
    }

    @Test
    public void getPathwaysWithDiagramForTest(){
        logger.info("Started testing fireworksService.getPathwaysWithDiagramForTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwaysService.getPathwaysWithDiagramFor("R-HSA-199420", "9606");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 5 or more pathways with diagram PTEN (R-HSA-199420) in human", pathways.size() >= 5);
    }

    @Test
    public void getPathwaysWithDiagramForAllFormsOfTest(){
        logger.info("Started testing fireworksService.getPathwaysWithDiagramForAllFormsOfTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwaysService.getPathwaysWithDiagramForAllFormsOf("R-HSA-199420", "9606");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 5 or more pathways with diagram for all forms of PTEN (R-HSA-199420) in human", pathways.size() >= 6);
    }

    @Test
    public void getLowerLevelPathwaysForIdentifierTest(){
        logger.info("Started testing pathwaysService.getLowerLevelPathwaysForIdentifierTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwaysService.getLowerLevelPathwaysForIdentifier("PTEN", "9606");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 9 or more pathways containing PTEN in human", pathways.size() >= 9);
    }


    @Test
    public void getPathwaysForIdentifierTest(){
        logger.info("Started testing pathwaysService.getPathwaysForIdentifierTest");
        long start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = pathwaysService.getPathwaysForIdentifier("POM121C", "189200","R-HSA-68875");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 1 or more pathways containing POM121C", pathways.size() > 0);
    }

    @Test
    public void getDiagramEntitiesForIdentifierTest(){
        logger.info("Started testing pathwaysService.getDiagramEntitiesForIdentifierTest");
        long start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> entities = pathwaysService.getDiagramEntitiesForIdentifier("R-HSA-189200", "SLC2A12");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be more than 1 entity in the pathway containing POM121C", entities.size() > 0);
    }
}
