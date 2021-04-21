//package org.reactome.server.graph.service;
//
//import org.reactome.server.graph.domain.model.Compartment;
//import org.reactome.server.graph.domain.model.ReactionLikeEvent;
//import org.reactome.server.graph.util.DatabaseObjectFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Objects;
//
//@SpringBootTest
//public class CompartmentTest {
//
//    @Autowired
//    private DatabaseObjectService databaseObjectService;
//
//    @BeforeClass
//    public static void setUpClass() {
//        logger.info(" --- !!! Running " + CompartmentTest.class.getName() + " !!! --- \n");
//    }
//
//    @Test
//    public void findCompartmentList() {
//        logger.info("Started testing compartmentTest.findCompartmentList");
//
//        long start, time;
//        start = System.currentTimeMillis();
//        ReactionLikeEvent rleGDB = databaseObjectService.findById("1247999");
//        ReactionLikeEvent rleRDB = DatabaseObjectFactory.createObject("1247999");
//
//        assertTrue("Different sizes", Objects.equals(rleGDB.getCompartment().size(), rleRDB.getCompartment().size()));
//
//        for (int i = 0; i < rleRDB.getCompartment().size(); i++) {
//            Compartment cRDB = rleRDB.getCompartment().get(i);
//            Compartment cGDB = rleGDB.getCompartment().get(i);
//            assertTrue(cGDB + " differs of " + cRDB, Objects.equals(cRDB, cGDB));
//        }
//
//        time = System.currentTimeMillis() - start;
//        logger.info("Comparison execution time: " + time + "ms");
//
//        logger.info("Finished");
//    }
//
//}