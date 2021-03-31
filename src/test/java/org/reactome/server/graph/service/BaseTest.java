//package org.reactome.server.graph.service;
//
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.reactome.server.graph.aop.LazyFetchAspect;
//import org.reactome.server.graph.config.Neo4jConfig;
//import org.reactome.server.graph.util.DatabaseObjectFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import static org.junit.Assume.assumeTrue;
//
///**
// * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
// */
//@ContextConfiguration(classes = { Neo4jConfig.class })
//@RunWith(SpringJUnit4ClassRunner.class)
//public abstract class BaseTest {
//
//    protected static final Logger logger = LoggerFactory.getLogger("testLogger");
//
//    static Boolean checkedOnce = false;
//    static Boolean isFit = false;
//
//    @Autowired
//    protected GeneralService generalService;
//
//    @Autowired
//    protected LazyFetchAspect lazyFetchAspect;
//
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
//
//        /********   DISABLING LAZY LOADING FOR A PROPER TESTING *********/
//        lazyFetchAspect.setEnableAOP(false);
//
//        assumeTrue(isFit);
//        DatabaseObjectFactory.clearCache();
//    }
//
//
//}
