package org.reactome.server.graph.repository;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.springframework.test.util.AssertionErrors.assertFalse;

@SpringBootTest
public class EventRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void getContainedEventsByStIdTest(){
        // TODO Test it later
        logger.info("Started testing pathwaysService.getContainedEventsByStIdTest");
        long start = System.currentTimeMillis();
        Collection<? extends Event> events = eventRepository.getContainedEventsByStId("R-HSA-69620");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", events.isEmpty());
    }

    @Test
    public void getContainedEventsByDbIdTest(){
        // TODO Test it later
        logger.info("Started testing pathwaysService.getContainedEventsByDbIdTest");
        long start = System.currentTimeMillis();
        Collection<Event> events = eventRepository.getContainedEventsByDbId(5673001L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse("This event contains other events", events.isEmpty());
    }

//    @Test
//    //TODO
//    public void getPathwaysForIdentifierTest(){
//        logger.info("Started testing pathwaysService.getPathwaysForIdentifierTest");
//        long start = System.currentTimeMillis();
//        Collection<SimpleDatabaseObject> pathways = pathwaysService.getPathwaysForIdentifier("POM121C", "189200","R-HSA-68875");
//        long time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertTrue("There should be 1 or more pathways containing POM121C", pathways.size() >= 0);
//    }
//
//    @Test
//    //TODO
//    public void getDiagramEntitiesForIdentifierTest(){
//        logger.info("Started testing pathwaysService.getDiagramEntitiesForIdentifierTest");
//        long start = System.currentTimeMillis();
//        Collection<SimpleDatabaseObject> entities = pathwaysService.getDiagramEntitiesForIdentifier("R-HSA-189200", "POM121C");
//        long time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertTrue("There should be more than 1 entity in the pathway containing POM121C", entities.size() >= 0);
//    }
}
