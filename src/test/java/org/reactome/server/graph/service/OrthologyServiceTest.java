//package org.reactome.server.graph.service;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.reactome.server.graph.domain.model.DatabaseObject;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Collection;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
///**

// */
//public class OrthologyServiceTest extends BaseTest {
//
//    @Autowired
//    private OrthologyService orthologyService;
//
//    @BeforeClass
//    public static void setUpClass() {
//        logger.info(" --- !!! Running " + OrthologyServiceTest.class.getName() + " !!! --- \n");
//    }
//
//    @Test
//    public void getOrthologyTest() {
//        logger.info("Started testing orthologyService.getOrthology");
//        long start = System.currentTimeMillis();
//        Collection<DatabaseObject> orthology = orthologyService.getOrthology("R-HSA-6799198", 49633L);
//        long time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertNotNull("The orthology cannot be null", orthology.iterator().next().getStId());
//        assertTrue("The orthologous of 'R-HSA-6799198' for 'Sus scrofa' is 'R-SSC-6799198'", orthology.iterator().next().getStId().equals("R-SSC-6799198"));
//    }
//
//}
