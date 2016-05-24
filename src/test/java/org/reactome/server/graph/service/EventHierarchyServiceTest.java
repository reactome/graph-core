package org.reactome.server.graph.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.Neo4jConfig;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.reactome.server.graph.util.JunitHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

/**
 * Created by flo on 24/05/16.
 */
@ContextConfiguration(classes = {Neo4jConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class EventHierarchyServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    @Autowired
    private EventHierarchyService eventHierarchyService;

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
    public void getEventHierarchyTest() {
        logger.info("Started testing eventService.findByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PathwayBrowserNode> eventHierarchy = eventHierarchyService.getEventHierarchy("Homo sapiens");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(24, eventHierarchy.size());
        logger.info("Finished");
    }
}