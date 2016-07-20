package org.reactome.server.graph.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.Neo4jConfig;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.TopLevelPathway;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 18.05.16.
 */
@ContextConfiguration(classes = { Neo4jConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TopLevelPathwayServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    @Autowired
    private GeneralService generalService;

    @Autowired
    private TopLevelPathwayService topLevelPathwayService;

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

        assertEquals(24,observedTlps.size());
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

        assertEquals(24,observedTlps.size());
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

        assertEquals(29,observedTlps.size());
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

        assertEquals(24,observedTlps.size());
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

        assertEquals(24,observedTlps.size());
        logger.info("Finished");
    }

}
