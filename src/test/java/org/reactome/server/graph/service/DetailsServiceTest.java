package org.reactome.server.graph.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.Neo4jConfig;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.service.helper.ContentDetails;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.04.16.
 */
@ContextConfiguration(classes = {Neo4jConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
public class DetailsServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    private static final String stId = "R-HSA-199420";

//    @Autowired WebApplicationContext wac;

    @Autowired
    private DetailsService detailsService;

    @Autowired
    private GeneralService generalService;

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
            isFit = generalService.fitForService();
            checkedOnce = true;
        }
        assumeTrue(isFit);
        generalService.clearCache();
        DatabaseObjectFactory.clearCache();
    }

    @Test
    public void getContentDetailsTest() {

        logger.info("Started testing detailsService.getContentDetails");
        long start, time;
//        start = System.currentTimeMillis();
//        ContentDetails contentDetails = detailsService.getContentDetails("R-HSA-60557");
//        time = System.currentTimeMillis() - start;
//        logger.info("getContentDetails execution time: " + time + "ms");

        start = System.currentTimeMillis();
        ContentDetails contentDetails2 = detailsService.getContentDetails2("R-HSA-60557");
        time = System.currentTimeMillis() - start;
        logger.info("getContentDetails2 execution time: " + time + "ms");

//        assertEquals(1, contentDetails2.getComponentOf().size());
//        assertEquals(27, contentDetails2.getOtherFormsOfThisMolecule().size());
//        assertEquals(4, contentDetails2.getLeaves().size());
        logger.info("Finished");
    }

    @Test
    public void getLocationsInThePathwayBrowserHierarchyTest() {

        logger.info("Started testing detailsService.getLocationsInThePathwayBrowserHierarchy");
        DatabaseObject databaseObject = generalService.find(stId, RelationshipDirection.OUTGOING);

        long start, time;
        start = System.currentTimeMillis();
        Set<PathwayBrowserNode> nodes = detailsService.getLocationsInThePathwayBrowserHierarchy(databaseObject);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(4, nodes.size());
        logger.info("Finished");
    }

    @Test
    public void getLocationsInThePathwayBrowserHierarchyByIdTest() {

        logger.info("Started testing detailsService.getLocationsInThePathwayBrowserHierarchyById");
        long start, time;
        start = System.currentTimeMillis();
        Set<PathwayBrowserNode> nodes = detailsService.getLocationsInThePathwayBrowserHierarchy(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(4, nodes.size());
        logger.info("Finished");
    }

    @Test
    public void getLocationsInThePathwayBrowserTest() {

        logger.info("Started testing detailsService.getLocationsInThePathwayBrowser");
        DatabaseObject databaseObject = generalService.find(stId, RelationshipDirection.OUTGOING);

        long start, time;
        start = System.currentTimeMillis();
        PathwayBrowserNode node = detailsService.getLocationsInThePathwayBrowser(databaseObject);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1, node.getChildren().size());
        logger.info("Finished");
    }

    @Test
    public void getLocationsInThePathwayBrowserByIdTest() {

        logger.info("Started testing detailsService.getLocationsInThePathwayBrowserById");
        long start, time;
        start = System.currentTimeMillis();
        PathwayBrowserNode node = detailsService.getLocationsInThePathwayBrowser(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1, node.getChildren().size());
        logger.info("Finished");
    }
}