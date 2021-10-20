package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class MappingServiceTest extends BaseTest {

    @Autowired
    private MappingService mappingService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + MappingServiceTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void testGetReactionsLikeEventProteinAndGeneName() {
        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
        long start = System.currentTimeMillis();
        Collection<ReactionLikeEvent> rles1 = mappingService.getReactionsLikeEvent( "UniProt", "P60484");
        long time = System.currentTimeMillis() - start;
        logger.info("getReactionsLikeEvent execution time: " + time + "ms");
        start = System.currentTimeMillis();
        Collection<ReactionLikeEvent> rles2 = mappingService.getReactionsLikeEvent("UniProt", "PTEN", "9606");
        time = System.currentTimeMillis() - start;
        logger.info("getReactionsLikeEvent by gene name with species execution time: " + time + "ms");

        assertNotNull(rles1, "P60484 is present in the database");
        assertNotNull(rles2, "PTEN is present in the database");
        assertFalse(rles1.isEmpty(), "P60484 is present in the database");
        assertFalse(rles2.isEmpty(), "PTEN is present in the database");
        assertTrue(rles1.size() <= rles2.size());
        logger.info("Finished");
    }


    @Test
    public void testGetReactionsLikeEventChemical() {
        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
        long start = System.currentTimeMillis();
        Collection<ReactionLikeEvent> rles1 = mappingService.getReactionsLikeEvent("ChEBI",  "15377", "Homo sapiens");
        long time = System.currentTimeMillis() - start;
        logger.info("getReactionsLikeEvent with species execution time: " + time + "ms");
        start = System.currentTimeMillis();
        Collection<ReactionLikeEvent> rles2 = mappingService.getReactionsLikeEvent("ChEBI", "water", "9606");
        time = System.currentTimeMillis() - start;
        logger.info("getReactionsLikeEvent by name with species execution time: " + time + "ms");

        assertNotNull(rles1,"15377 is present in the database");
        assertNotNull(rles2, "water is present in the database");
        assertFalse(rles1.isEmpty(), "15377 is present in the database");
        assertFalse(rles2.isEmpty(), "water is present in the database");
        assertTrue(rles1.size() <= rles2.size());
        logger.info("Finished");
    }

    @Test
    public void testGetPathwaysProteinAndGeneName() {
        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
        long start = System.currentTimeMillis();
        Collection<Pathway> ps1 = mappingService.getPathways("UniProt", "P60484");
        long time = System.currentTimeMillis() - start;
        logger.info("getPathways execution time: " + time + "ms");
        start = System.currentTimeMillis();
        Collection<Pathway> ps2 = mappingService.getPathways("UniProt", "PTEN", "9606");
        time = System.currentTimeMillis() - start;
        logger.info("getPathways by gene name with species execution time: " + time + "ms");

        assertNotNull(ps1, "P60484 is present in the database");
        assertNotNull(ps2, "PTEN is present in the database");
        assertFalse(ps1.isEmpty(), "P60484 is present in the database");
        assertFalse(ps2.isEmpty(), "PTEN is present in the database");
        assertTrue(ps1.size() <= ps2.size());
        logger.info("Finished");
    }


    @Test
    public void testGetPathwaysChemical() {
        logger.info("Started testing mappingService.testGetReactionsLikeEvent");
        long start = System.currentTimeMillis();
        Collection<Pathway> ps1 = mappingService.getPathways("ChEBI","15377",  "Homo sapiens");
        long time = System.currentTimeMillis() - start;
        logger.info("getPathways with species execution time: " + time + "ms");
        start = System.currentTimeMillis();
        Collection<Pathway> ps2 = mappingService.getPathways("ChEBI", "water", "9606");
        time = System.currentTimeMillis() - start;
        logger.info("getPathways by name with species execution time: " + time + "ms");

        assertNotNull(ps1, "15377 is present in the database");
        assertNotNull(ps2, "water is present in the database");
        assertFalse(ps1.isEmpty(), "15377 is present in the database");
        assertFalse(ps2.isEmpty(), "water is present in the database");
        assertTrue(ps1.size() <= ps2.size());
        logger.info("Finished");
    }
}


