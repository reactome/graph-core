package org.reactome.server.graph.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.aop.LazyFetchAspect;
import org.reactome.server.graph.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
        Collection<PhysicalEntity> otherFormsOfThisMolecule = physicalEntityService.getOtherFormsOf(PhysicalEntities.entityWithAccessionedSequence2.getDbId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse(otherFormsOfThisMolecule.isEmpty());
        logger.info("Finished");
    }

    @Test
    public void testGetOtherFormsOfThisMoleculeByStId() {

        logger.info("Started testing physicalEntityService.testGetOtherFormsOfThisMoleculeByStId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> otherFormsOfThisMolecule = physicalEntityService.getOtherFormsOf(PhysicalEntities.entityWithAccessionedSequence2.getStId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse(otherFormsOfThisMolecule.isEmpty());
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
        Collection<Complex> complexes = physicalEntityService.getComplexesFor("Some protein ID", "Test Reference Database");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(complexes.size() >= 1);
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


    @Autowired
    protected LazyFetchAspect lazyFetchAspect;

    @Test
    public void testGetPhysicalEntityInDepth() {
        logger.info("Started testing physicalEntityService.testGetComplexSubunitsBounded");
        lazyFetchAspect.setEnableAOP(false);
        String identifier = "R-MMU-6814275";
        assertEquals(1, recursiveCheckMaxDepth(0, physicalEntityService.getPhysicalEntityInDepth(6814275, 1)), "dbId should work");
        assertEquals(1, recursiveCheckMaxDepth(0, physicalEntityService.getPhysicalEntityInDepth(identifier, 1)), "depth should match requested parameter");
        assertEquals(1, recursiveCheckMaxDepth(0, physicalEntityService.getPhysicalEntityInDepth(identifier, 0)), "depth of 0 correspond to first level");
        assertEquals(5, recursiveCheckMaxDepth(0, physicalEntityService.getPhysicalEntityInDepth(identifier, 5)), "intermediate depth should work");
        Complex totalComplex = (Complex) physicalEntityService.getPhysicalEntityInDepth(identifier, -1);
        assertEquals(16, recursiveCheckMaxDepth(0, totalComplex), "using a depth of -1 should return the full composition");
        totalComplex.getHasComponent().stream()
                .filter(component -> component instanceof Complex)
                .map(component -> (Complex) component)
                .forEach(component -> Assertions.assertThat(component.getSpecies())
                        .withFailMessage("All nodes which have a species should have this info populated")
                        .isNotNull().isNotEmpty()
                );

        lazyFetchAspect.setEnableAOP(true);
        logger.info("Finished");
    }


    public int recursiveCheckMaxDepth(int depth, PhysicalEntity pe) {
        Assertions.assertThat(pe.getCompartment()).isNotNull();

        Stream<PhysicalEntity> children = Stream.empty();
        if (pe instanceof Complex) {
            children = ((Complex) pe).getHasComponent().stream();
        } else if (pe instanceof Polymer) {
            children = ((Polymer) pe).getRepeatedUnit().stream();
        } else if (pe instanceof CandidateSet) {
            children = Stream.concat(
                    ((CandidateSet) pe).getHasMember().stream(),
                    ((CandidateSet) pe).getHasCandidate().stream()
            );
        } else if (pe instanceof EntitySet) {
            children = ((EntitySet) pe).getHasMember().stream();
        } else if (pe instanceof Cell) {
            children = Stream.concat(
                    ((Cell) pe).getProteinMarker().stream(),
                    ((Cell) pe).getRNAMarker().stream()
            );
        }
        return children.mapToInt(child -> recursiveCheckMaxDepth(depth + 1, child))
                .max()
                .orElse(depth);

    }
}