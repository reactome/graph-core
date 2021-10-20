package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.result.EventProjectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class EventsServiceTest extends BaseTest {

    @Autowired
    private EventsService eventsService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + EventsServiceTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void getEventAncestorsByStIdTest() {
        logger.info("Started testing eventsService.getEventAncestorsByStIdTest");
        long start = System.currentTimeMillis();
        Collection<EventProjectionWrapper> pathways = eventsService.getEventAncestors(169680L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue("'IP3 binds to the IP3 receptor, opening the endoplasmic reticulum Ca2+ channel' is in several different locations. Found:" + pathways.size(), pathways.size() >= 10);
        for (EventProjectionWrapper eventProjectionWrapper : pathways) {
            assertFalse(eventProjectionWrapper.getEvents().isEmpty(), "Ancestors list cannot be empty");
        }
    }

    @Test
    public void getEventAncestorsByDbIdTest() {
        logger.info("Started testing eventsService.getEventAncestorsByDbIdTest");
        long start = System.currentTimeMillis();
        Collection<EventProjectionWrapper>  pathways = eventsService.getEventAncestors(169680L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("'IP3 binds to the IP3 receptor, opening the endoplasmic reticulum Ca2+ channel' is in several different locations. Found:" + pathways.size(), pathways.size() >= 10);
        for (EventProjectionWrapper eventProjectionWrapper : pathways) {
            assertFalse(eventProjectionWrapper.getEvents().isEmpty(), "Ancestors list cannot be empty");
        }
    }

}
