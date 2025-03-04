package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Reaction;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HierarchyServiceTest extends BaseTest {

    @Autowired
    private HierarchyService hierarchyService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + HierarchyServiceTest.class.getName() + " !!! --- \n");
    }

    @AfterTestClass
    public void tearDownClass() {
        logger.info("\n\n");
    }

    @Test
    public void getLocationsInPathwayBrowserTest() {

        logger.info("Started testing detailsService.getLocationsInPathwayBrowserTest");
        long start, time;
        start = System.currentTimeMillis();
        PathwayBrowserNode node = hierarchyService.getLocationsInPathwayBrowser(Events.topLevelPathway.getStId(), false, true);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(node);
        assertEquals(Events.topLevelPathway.getStId(), node.getStId());
        logger.info("Finished");
    }

    @Test
    public void getLocationsInThePathwayBrowserDirectParticipantsTest() {

        logger.info("Started testing detailsService.getLocationsInThePathwayBrowserDirectParticipantsTest");
        long start, time;
        start = System.currentTimeMillis();
        PathwayBrowserNode node = hierarchyService.getLocationsInPathwayBrowser(PhysicalEntities.entityWithAccessionedSequence2.getStId(), true, true);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(node);
        assertEquals(PhysicalEntities.entityWithAccessionedSequence2.getStId(), node.getStId());
        logger.info("Finished");
    }

    @Test
    public void getLocationInPathwayBrowserForPathwaysTest() {
        logger.info("Started testing detailsService.getLocationInPathwayBrowserForPathwaysTest");
        long start, time;
        start = System.currentTimeMillis();
        List<?> pathways = Arrays.asList(212165L, 5250913L, 5250941L, 73886L, 74160L, "R-HSA-109581", "R-HSA-9612973");
        Set<PathwayBrowserNode> node = hierarchyService.getLocationInPathwayBrowserForPathways(pathways);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(4, node.size());
        logger.info("Finished");
    }

    // --------------------------------------------- Sub Hierarchy -----------------------------------------------------

    @Test
    public void getSubHierarchyTest() {
        logger.info("Started testing eventService.findByDbId");
        long start, time;
        start = System.currentTimeMillis();
        PathwayBrowserNode subHierarchy = hierarchyService.getSubHierarchy("R-HSA-109581");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(4, subHierarchy.getChildren().size());
        assertTrue(subHierarchy.getHighlighted());
        assertTrue(subHierarchy.isClickable());
        assertTrue( subHierarchy.getChildren().iterator().next().getChildren().size() > 1);

        logger.info("Finished");
    }

    // ------------------------------------------- Event Hierarchy -----------------------------------------------------

    @Test
    public void getEventHierarchyBySpeciesNameTest() {
        testService.deleteTest();
        logger.info("Started testing eventService.getEventHierarchyBySpeciesNameTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PathwayBrowserNode> eventHierarchy = hierarchyService.getEventHierarchy("Homo sapiens", false);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(eventHierarchy.size() > 20);
        logger.info("Finished");
    }

    @Test
    public void getEventHierarchyByTaxIdTest() {
        logger.info("Started testing eventService.getEventHierarchyByTaxIdTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PathwayBrowserNode> eventHierarchy = hierarchyService.getEventHierarchy(9606, false);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(eventHierarchy.size() > 20);
        logger.info("Finished");
    }

    @Test
    public void getPathwayHierarchyByTaxIdTest() {
        logger.info("Started testing eventService.getEventHierarchyByTaxIdTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PathwayBrowserNode> eventHierarchy = hierarchyService.getEventHierarchy(9606, true);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(eventHierarchy.size() > 20);
        Set<String> possibleTypes = Set.of("TopLevelPathway", "Pathway");
        assertTrue(eventHierarchy.stream().allMatch(pathwayBrowserNode -> possibleTypes.contains(pathwayBrowserNode.getType())));
        logger.info("Finished");
    }
}