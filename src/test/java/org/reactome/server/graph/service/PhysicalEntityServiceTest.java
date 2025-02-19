package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 */
public class PhysicalEntityServiceTest extends BaseTest {
    private static final Long dbId = 199420L;
    private static final String stId = "R-HSA-199420";

    @Autowired
    private DatabaseObjectService dos;

    @Autowired
    private PhysicalEntityService physicalEntityService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + PhysicalEntityServiceTest.class.getName() + " !!! --- \n");
    }

    @AfterTestClass
    public void tearDownClass() {
        logger.info("\n\n");
    }

    @Test
    public void testGetOtherFormsOfThisMoleculeByDbId() {

        logger.info("Started testing physicalEntityService.testGetOtherFormsOfThisMoleculeByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> otherFormsOfThisMolecule = physicalEntityService.getOtherFormsOf(PhysicalEntities.interactionEWAS.getDbId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!otherFormsOfThisMolecule.isEmpty());
        logger.info("Finished");
    }

    @Test
    public void testGetOtherFormsOfThisMoleculeByStId() {

        logger.info("Started testing physicalEntityService.testGetOtherFormsOfThisMoleculeByStId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> otherFormsOfThisMolecule = physicalEntityService.getOtherFormsOf(PhysicalEntities.interactionEWAS.getDbId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!otherFormsOfThisMolecule.isEmpty());
        logger.info("Finished");
    }

    @Test
    public void testGetOtherFormsOfThisMoleculeByPhysicalEntity() {

        logger.info("Started testing physicalEntityService.testGetOtherFormsOfThisMoleculeByStId");
        long start, time;
        start = System.currentTimeMillis();
        PhysicalEntity pe = dos.findById(stId);
        Collection<PhysicalEntity> otherFormsOfThisMolecule = physicalEntityService.getOtherFormsOf(PhysicalEntities.interactionEWAS.getStId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!otherFormsOfThisMolecule.isEmpty());
        logger.info("Finished");
    }

    @Test
    public void testGetComplexesFor() {

        logger.info("Started testing physicalEntityService.testGetComplexesFor");
        long start, time;
        start = System.currentTimeMillis();
        String dbName = PhysicalEntities.referenceDatabase.getDisplayName();
        String id = PhysicalEntities.referenceSequence.getIdentifier();
        Collection<Complex> complexes = physicalEntityService.getComplexesFor(id, dbName);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(complexes.size() >= 5);
        logger.info("Finished");

    }

    @Test
    public void testGetComplexSubunits() {

        logger.info("Started testing physicalEntityService.testGetComplexSubunits");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> complexSubunits = physicalEntityService.getPhysicalEntitySubunits(PhysicalEntities.complex.getStId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!complexSubunits.isEmpty());
        logger.info("Finished");

    }
}