package org.reactome.server.graph.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.Neo4jConfig;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@ContextConfiguration(classes = { Neo4jConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class OrthologyServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    @Autowired
    private GeneralService generalService;

    @Autowired
    private OrthologyService orthologyService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running OrthologyServiceTests !!! --- \n");
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
    public void getOrthologyTest(){
        logger.info("Started testing eventsService.getEventAncestorsByStIdTest");
        long start = System.currentTimeMillis();
        DatabaseObject orthology = orthologyService.getOrthology("R-HSA-6799198", 49633L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull("The orthology cannot be null", orthology.getStId());
        assertTrue("The orthologous of 'R-HSA-6799198' for 'Sus scrofa' is 'R-SSC-6799198'", orthology.getStId().equals("R-SSC-6799198"));
    }

}
