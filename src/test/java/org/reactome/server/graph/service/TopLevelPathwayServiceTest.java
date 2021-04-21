package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.TopLevelPathway;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TopLevelPathwayServiceTest extends BaseTest {

    @Autowired
    private TopLevelPathwayService topLevelPathwayService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + TopLevelPathwayServiceTest.class.getName() + " !!! --- \n");
    }

    @AfterTestClass
    public void tearDownClass() {
        logger.info("\n\n");
    }

    @Test
    public void testGetTopLevelPathwaysTest() {
        logger.info("Started testing genericService.testGetTopLevelPathwaysTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<TopLevelPathway> observedTlps = topLevelPathwayService.getTopLevelPathways();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        Collection<DatabaseObject> expectedTlps = DatabaseObjectFactory.loadFrontPageItems();
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertEquals(expectedTlps.size(), observedTlps.size());
        logger.info("Finished");
    }

    @Test
    public void getTopLevelPathwaysByNameTest() {
        logger.info("Started testing genericService.getTopLevelPathwaysByNameTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<TopLevelPathway> observedTlps = topLevelPathwayService.getTopLevelPathways("Homo sapiens");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(observedTlps.size() > 0);
        logger.info("Finished");
    }

    @Test
    public void getTopLevelPathwaysWithTaxIdTest() {
        logger.info("Started testing genericService.getCuratedTopLevelPathwaysByTaxId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<TopLevelPathway> observedTlps = topLevelPathwayService.getTopLevelPathways(9606);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(observedTlps.size() > 0);
        logger.info("Finished");
    }

    @Test
    public void getCuratedTopLevelPathwaysTest() {
        logger.info("Started testing genericService.getCuratedTopLevelPathwaysTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<TopLevelPathway> observedTlps = topLevelPathwayService.getCuratedTopLevelPathways();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(observedTlps.size() > 0);
        logger.info("Finished");
    }

    @Test
    public void getCuratedTopLevelPathwaysByNameTest() {
        logger.info("Started testing genericService.getCuratedTopLevelPathwaysByNameTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<TopLevelPathway> observedTlps = topLevelPathwayService.getCuratedTopLevelPathways("Homo sapiens");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(observedTlps.size() > 0);
        logger.info("Finished");
    }

    @Test
    public void getCuratedTopLevelPathwaysWithTaxIdTest() {
        logger.info("Started testing genericService.getCuratedTopLevelPathwaysByTaxId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<TopLevelPathway> observedTlps = topLevelPathwayService.getCuratedTopLevelPathways(9606);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(observedTlps.size() > 0);
        logger.info("Finished");
    }
}
