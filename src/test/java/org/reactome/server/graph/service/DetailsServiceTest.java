package org.reactome.server.graph.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.service.helper.ContentDetails;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.04.16.
 */
public class DetailsServiceTest extends BaseTest {

    private static final String stId = "R-HSA-199420";

    @Autowired
    private DetailsService detailsService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + DetailsServiceTest.class.getName() + " !!! --- \n");
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