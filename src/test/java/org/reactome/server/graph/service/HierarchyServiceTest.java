//package org.reactome.server.graph.service;
//
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.reactome.server.graph.config.Neo4jConfig;
//import org.reactome.server.graph.service.helper.PathwayBrowserNode;
//import org.reactome.server.graph.util.DatabaseObjectFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assume.assumeTrue;
//
///**
// * Created by:
// *
// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
// * @since 31.05.16.
// */
//@ContextConfiguration(classes = {Neo4jConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class HierarchyServiceTest {
//
//    private static final Logger logger = LoggerFactory.getLogger("testLogger");
//
//    private static Boolean checkedOnce = false;
//    private static Boolean isFit = false;
//
//    @Autowired
//    private HierarchyService hierarchyService;
//
//    @Autowired
//    private GeneralService generalService;
//
//    @BeforeClass
//    public static void setUpClass() {
//        logger.info(" --- !!! Running " + DetailsServiceTest.class.getName() + " !!! --- \n");
//    }
//    @AfterClass
//    public static void tearDownClass() {
//        logger.info("\n\n");
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        if (!checkedOnce) {
//            isFit = generalService.fitForService();
//            checkedOnce = true;
//        }
//        assumeTrue(isFit);
//        DatabaseObjectFactory.clearCache();
//    }
//
//    @Test
//    public void getLocationsInPathwayBrowserTest() {
//
//        logger.info("Started testing detailsService.getLocationsInPathwayBrowserTest");
//        long start, time;
//        start = System.currentTimeMillis();
//        PathwayBrowserNode node = hierarchyService.getLocationsInPathwayBrowser("R-HSA-5205630", false, true);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(3, node.getChildren().size());
//        logger.info("Finished");
//    }
//
//    @Test
//    public void getLocationsInThePathwayBrowserDirectParticipantsTest() {
//
//        logger.info("Started testing detailsService.getLocationsInThePathwayBrowserDirectParticipantsTest");
//        long start, time;
//        start = System.currentTimeMillis();
//        PathwayBrowserNode node = hierarchyService.getLocationsInPathwayBrowser("R-HSA-5205630", true, true);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(2, node.getChildren().size());
//        logger.info("Finished");
//    }
//
//    @Test
//    public void getLocationInPathwayBrowserForPathwaysTest(){
//        logger.info("Started testing detailsService.getLocationInPathwayBrowserForPathwaysTest");
//        long start, time;
//        start = System.currentTimeMillis();
//        List<Long> pathways = Arrays.asList(212165L, 5250913L, 5250941L, 73886L, 74160L);
//        Set<PathwayBrowserNode> node = hierarchyService.getLocationInPathwayBrowserForPathways(pathways);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(2, node.size());
//        logger.info("Finished");
//    }
//
//
//    // --------------------------------------------- Sub Hierarchy -----------------------------------------------------
//
//    @Test
//    public void getSubHierarchyTest() {
//        logger.info("Started testing eventService.findByDbId");
//        long start, time;
//        start = System.currentTimeMillis();
//        PathwayBrowserNode subHierarchy = hierarchyService.getSubHierarchy("R-HSA-109581");
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(4, subHierarchy.getChildren().size());
//        logger.info("Finished");
//    }
//
//    // ------------------------------------------- Event Hierarchy -----------------------------------------------------
//
//    @Test
//    public void getEventHierarchyBySpeciesNameTest() {
//        logger.info("Started testing eventService.getEventHierarchyBySpeciesNameTest");
//        long start, time;
//        start = System.currentTimeMillis();
//        Collection<PathwayBrowserNode> eventHierarchy = hierarchyService.getEventHierarchy("Homo sapiens");
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertTrue(eventHierarchy.size() > 20);
//        logger.info("Finished");
//    }
//
//    @Test
//    public void getEventHierarchyByTaxIdTest() {
//        logger.info("Started testing eventService.getEventHierarchyByTaxIdTest");
//        long start, time;
//        start = System.currentTimeMillis();
//        Collection<PathwayBrowserNode> eventHierarchy = hierarchyService.getEventHierarchy(9606);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertTrue(eventHierarchy.size() > 20);
//        logger.info("Finished");
//    }
//}