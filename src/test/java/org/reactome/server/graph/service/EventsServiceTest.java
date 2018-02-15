package org.reactome.server.graph.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.model.Pathway;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class EventsServiceTest extends BaseTest {

    @Autowired
    private EventsService eventsService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + EventsServiceTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void getEventAncestorsByStIdTest() {
        logger.info("Started testing eventsService.getEventAncestorsByStIdTest");
        long start = System.currentTimeMillis();
        Collection<Collection<Pathway>> pathways = eventsService.getEventAncestors("R-HSA-169680");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("'IP3 binds to the IP3 receptor, opening the endoplasmic reticulum Ca2+ channel' is in several different locations. Found:" + pathways.size(), pathways.size() > 1);
        for (Collection<Pathway> pathway : pathways) {
            assertFalse("Ancestors list cannot be empty", pathway.isEmpty());
        }
    }

    @Test
    public void getEventAncestorsByDbIdTest() {
        logger.info("Started testing eventsService.getEventAncestorsByDbIdTest");
        long start = System.currentTimeMillis();
        Collection<Collection<Pathway>> pathways = eventsService.getEventAncestors(169680L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("'IP3 binds to the IP3 receptor, opening the endoplasmic reticulum Ca2+ channel' is in several different locations. Found:" + pathways.size(), pathways.size() > 1);
        for (Collection<Pathway> pathway : pathways) {
            assertFalse("Ancestors list cannot be empty", pathway.isEmpty());
        }
    }

}
