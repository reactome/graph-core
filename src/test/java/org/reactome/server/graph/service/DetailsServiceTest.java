package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.service.helper.ContentDetails;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + DetailsServiceTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void getContentDetailsTest() {
        logger.info("Started testing detailsService.getContentDetails");
        long start, time;
        start = System.currentTimeMillis();
        ContentDetails contentDetails = detailsService.getContentDetails(199420L, false);
        time = System.currentTimeMillis() - start;
        logger.info("getContentDetails execution time: " + time + "ms");

        assertTrue(contentDetails.getNodes().size() >= 5);
        assertEquals("PTEN [cytosol]", contentDetails.getDatabaseObject().getDisplayName());
        assertTrue(contentDetails.getOtherFormsOfThisMolecule().size() >= 27);
        assertTrue(contentDetails.getComponentOf().size() >= 1);
        logger.info("Finished");
    }

    @Test
    public void getLocationInPathwayBrowserForPathwaysTest() {
        logger.info("Started testing detailsService.getLocationInPathwayBrowserForPathwaysTest");
        long start, time;
        start = System.currentTimeMillis();
        List<String> pathways = Arrays.asList("R-HSA-212165", "R-HSA-5250913", "R-HSA-5250941", "R-HSA-73886", "R-HSA-74160");
        Set<PathwayBrowserNode> node = detailsService.getLocationInPathwayBrowserForPathways(pathways);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(2, node.size());
    }

}