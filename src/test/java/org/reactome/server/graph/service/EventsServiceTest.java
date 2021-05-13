package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.springframework.test.util.AssertionErrors.assertTrue;

public class EventsServiceTest extends BaseTest {

    @Autowired
    private EventsService eventsService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + EventsServiceTest.class.getName() + " !!! --- \n");
    }

    // TODO EventServiceTest - Work In progress
    @Test
    public void getEventAncestorsByStIdTest() {
        logger.info("Started testing eventsService.getEventAncestorsByStIdTest");
        long start = System.currentTimeMillis();
        Collection<? extends Event> pathways = eventsService.getEventAncestors("R-HSA-169680");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("'IP3 binds to the IP3 receptor, opening the endoplasmic reticulum Ca2+ channel' is in several different locations. Found:" + pathways.size(), pathways.size() > 1);
//        for (Collection pathway : pathways) {
//            assertFalse("Ancestors list cannot be empty", pathway.isEmpty());
//        }
    }

//    @Test
//    public void getEventAncestorsByDbIdTest() {
//        logger.info("Started testing eventsService.getEventAncestorsByDbIdTest");
//        long start = System.currentTimeMillis();
//        Collection<Collection<Event>> pathways = eventsService.getEventAncestors(169680L);
//        long time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertTrue("'IP3 binds to the IP3 receptor, opening the endoplasmic reticulum Ca2+ channel' is in several different locations. Found:" + pathways.size(), pathways.size() > 1);
//        for (Collection pathway : pathways) {
//            assertFalse("Ancestors list cannot be empty", pathway.isEmpty());
//        }
//    }

}
