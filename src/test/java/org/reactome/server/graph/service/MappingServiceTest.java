package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.reactome.server.graph.util.TestNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class MappingServiceTest extends BaseTest {

    @Autowired
    private MappingService mappingService;

    @Autowired
    protected TestNodeService testService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + MappingServiceTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void testGetReactionsLikeEventProteinAndGeneName() {
        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
        long start = System.currentTimeMillis();
        Collection<ReactionLikeEvent> rles = mappingService.getReactionsLikeEvent(PhysicalEntities.referenceSequence.getDisplayName(), PhysicalEntities.referenceSequence.getDisplayName());
        long time = System.currentTimeMillis() - start;
        logger.info("getReactionsLikeEvent execution time: " + time + "ms");

        assertNotNull(rles, "Protein is present in the database");
        logger.info("Finished");
    }


    @Test
    public void testGetReactionsLikeEventChemical() {
        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
        deleteTestData(testService);
        createPathwayWithReferences(testService);

        long start = System.currentTimeMillis();
        //"ChEBI",  "15377", "Homo sapiens"
        Collection<ReactionLikeEvent> rles = mappingService.getReactionsLikeEvent("Test", "123123123","Test species");
        long time = System.currentTimeMillis() - start;
        logger.info("getReactionsLikeEvent with species execution time: " + time + "ms");

        //deleteTestData(testService);
        assertNotNull(rles,"Data is present in the database");
        assertFalse(rles.isEmpty(), "Data is present in the database");
        logger.info("Finished");
    }


    @Test
    public void testGetPathwaysProteinAndGeneName() {
        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
        deleteTestData(testService);
        createPathwayWithReferences(testService);

        long start = System.currentTimeMillis();
        Collection<Pathway> ps1 = mappingService.getPathways("Test", "123123123");
        long time = System.currentTimeMillis() - start;
        logger.info("getPathways execution time: " + time + "ms");

        assertNotNull(ps1, "present in the database");
        assertFalse(ps1.isEmpty(), "present in the database");
        logger.info("Finished");
    }


    @Test
    public void testGetPathwaysChemical() {
        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
        deleteTestData(testService);
        createPathwayWithReferences(testService);

        long start = System.currentTimeMillis();
        Collection<Pathway> ps1 = mappingService.getPathways("Test","123123123",  "Test species");
        long time = System.currentTimeMillis() - start;
        logger.info("getPathways with species execution time: " + time + "ms");

        assertNotNull(ps1, "15377 is present in the database");
        assertFalse(ps1.isEmpty(), "15377 is present in the database");
        logger.info("Finished");
    }
}