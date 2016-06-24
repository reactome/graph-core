package org.reactome.server.graph.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.Neo4jConfig;
import org.reactome.server.graph.service.helper.ContentDetails;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.04.16.
 */
@ContextConfiguration(classes = {Neo4jConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class DetailsServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    private static final String stId = "R-HSA-199420";

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
        DatabaseObjectFactory.clearCache();
    }

    @Test
    public void getContentDetailsTest() {

        logger.info("Started testing detailsService.getContentDetails");
        long start, time;
        start = System.currentTimeMillis();
        ContentDetails contentDetails = detailsService.getContentDetails(stId, false);
        time = System.currentTimeMillis() - start;
        logger.info("getContentDetails execution time: " + time + "ms");

        assertEquals(4, contentDetails.getNodes().size());
        assertEquals("PTEN [cytosol]", contentDetails.getDatabaseObject().getDisplayName());
        assertTrue(contentDetails.getOtherFormsOfThisMolecule().size() >= 27);
        assertEquals(1, contentDetails.getComponentOf().size());
        logger.info("Finished");
    }


}