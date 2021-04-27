//package org.reactome.server.graph.service;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.reactome.server.graph.domain.model.Pathway;
//import org.reactome.server.graph.domain.model.ReactionLikeEvent;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Collection;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
///**

// */
//public class MappingServiceTest extends BaseTest {
//
//    @Autowired
//    private MappingService mappingService;
//
//    @BeforeClass
//    public static void setUpClass() {
//        logger.info(" --- !!! Running " + MappingServiceTest.class.getName() + " !!! --- \n");
//    }
//
//    @Test
//    public void testGetReactionsLikeEventProteinAndGeneName() {
//        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
//        long start = System.currentTimeMillis();
//        Collection<ReactionLikeEvent> rles1 = mappingService.getReactionsLikeEvent( "UniProt", "P60484");
//        long time = System.currentTimeMillis() - start;
//        logger.info("getReactionsLikeEvent execution time: " + time + "ms");
//        start = System.currentTimeMillis();
//        Collection<ReactionLikeEvent> rles2 = mappingService.getReactionsLikeEvent("UniProt", "PTEN", "9606");
//        time = System.currentTimeMillis() - start;
//        logger.info("getReactionsLikeEvent by gene name with species execution time: " + time + "ms");
//
//        assertNotNull("P60484 is present in the database", rles1);
//        assertNotNull("PTEN is present in the database", rles2);
//        assertTrue("P60484 is present in the database", !rles1.isEmpty());
//        assertTrue("PTEN is present in the database", !rles2.isEmpty());
//        assertTrue("",rles1.size() <= rles2.size());
//        logger.info("Finished");
//    }
//
//
//    @Test
//    public void testGetReactionsLikeEventChemical() {
//        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
//        long start = System.currentTimeMillis();
//        Collection<ReactionLikeEvent> rles1 = mappingService.getReactionsLikeEvent("ChEBI",  "15377", "Homo sapiens");
//        long time = System.currentTimeMillis() - start;
//        logger.info("getReactionsLikeEvent with species execution time: " + time + "ms");
//        start = System.currentTimeMillis();
//        Collection<ReactionLikeEvent> rles2 = mappingService.getReactionsLikeEvent("ChEBI", "water", "9606");
//        time = System.currentTimeMillis() - start;
//        logger.info("getReactionsLikeEvent by name with species execution time: " + time + "ms");
//
//        assertNotNull("15377 is present in the database", rles1);
//        assertNotNull("water is present in the database", rles2);
//        assertTrue("15377 is present in the database", !rles1.isEmpty());
//        assertTrue("water is present in the database", !rles2.isEmpty());
//        assertTrue("",rles1.size() <= rles2.size());
//        logger.info("Finished");
//    }
//
//    @Test
//    public void testGetPathwaysProteinAndGeneName() {
//        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
//        long start = System.currentTimeMillis();
//        Collection<Pathway> ps1 = mappingService.getPathways("UniProt", "P60484");
//        long time = System.currentTimeMillis() - start;
//        logger.info("getPathways execution time: " + time + "ms");
//        start = System.currentTimeMillis();
//        Collection<Pathway> ps2 = mappingService.getPathways("UniProt", "PTEN", "9606");
//        time = System.currentTimeMillis() - start;
//        logger.info("getPathways by gene name with species execution time: " + time + "ms");
//
//        assertNotNull("P60484 is present in the database", ps1);
//        assertNotNull("PTEN is present in the database", ps2);
//        assertTrue("P60484 is present in the database", !ps1.isEmpty());
//        assertTrue("PTEN is present in the database", !ps2.isEmpty());
//        assertTrue("",ps1.size() <= ps2.size());
//        logger.info("Finished");
//    }
//
//
//    @Test
//    public void testGetPathwaysChemical() {
//        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
//        long start = System.currentTimeMillis();
//        Collection<Pathway> ps1 = mappingService.getPathways("ChEBI","15377",  "Homo sapiens");
//        long time = System.currentTimeMillis() - start;
//        logger.info("getPathways with species execution time: " + time + "ms");
//        start = System.currentTimeMillis();
//        Collection<Pathway> ps2 = mappingService.getPathways("ChEBI", "water", "9606");
//        time = System.currentTimeMillis() - start;
//        logger.info("getPathways by name with species execution time: " + time + "ms");
//
//        assertNotNull("15377 is present in the database", ps1);
//        assertNotNull("water is present in the database", ps2);
//        assertTrue("15377 is present in the database", !ps1.isEmpty());
//        assertTrue("water is present in the database", !ps2.isEmpty());
//        assertTrue("",ps1.size() <= ps2.size());
//        logger.info("Finished");
//    }
//}
//
//
