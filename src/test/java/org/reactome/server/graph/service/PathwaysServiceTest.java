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
        Collection<Event> events = pathwaysService.getContainedEvents(Events.diagramPathway.getStId());
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", events.isEmpty());
    }

    @Test
    public void getContainedEventsByDbIdTest(){
        logger.info("Started testing pathwaysService.getContainedEventsByDbIdTest");
        long start = System.currentTimeMillis();
        Collection<Event> events = pathwaysService.getContainedEvents(Events.diagramPathway.getDbId());
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", events.isEmpty());
    }

    @Test
    public void getPathwaysForTest(){
        logger.info("Started testing fireworksService.getPathwaysForTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwaysService.getPathwaysFor(Events.ehldPathway.getStId(),"");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", pathways.isEmpty());
    }

    @Test
    public void getPathwaysForAllFormsOfTest(){
        logger.info("Started testing fireworksService.getPathwaysForAllFormsOfTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwaysService.getPathwaysForAllFormsOf(PhysicalEntities.entityWithAccessionedSequence.getStId(), "");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", pathways.isEmpty());
    }

    @Test
    public void getPathwaysWithDiagramForTest(){
        logger.info("Started testing fireworksService.getPathwaysWithDiagramForTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwaysService.getPathwaysWithDiagramFor(PhysicalEntities.entityWithAccessionedSequence.getStId(), "");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", pathways.isEmpty());
    }

    @Test
    public void getPathwaysWithDiagramForAllFormsOfTest(){
        logger.info("Started testing fireworksService.getPathwaysWithDiagramForAllFormsOfTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwaysService.getPathwaysWithDiagramForAllFormsOf(PhysicalEntities.entityWithAccessionedSequence.getStId(), "");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", pathways.isEmpty());
    }

    @Test
    public void getLowerLevelPathwaysForIdentifierTest(){           // the tested method only works with species taxId
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
        Collection<SimpleDatabaseObject> pathways = pathwaysService.getPathwaysForIdentifier(PhysicalEntities.referenceSequence.getIdentifier(), Events.diagramPathway.getStId());
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", pathways.isEmpty());
    }

    @Test
    public void getDiagramEntitiesForIdentifierTest(){
        logger.info("Started testing pathwaysService.getDiagramEntitiesForIdentifierTest");
        long start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> entities = pathwaysService.getDiagramEntitiesForIdentifier(Events.diagramPathway.getStId(), PhysicalEntities.referenceSequence.getIdentifier());
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", entities.isEmpty());
    }
}
