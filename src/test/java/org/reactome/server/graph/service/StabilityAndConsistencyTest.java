package org.reactome.server.graph.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class StabilityAndConsistencyTest extends BaseTest {

    private static final String stId = "R-HSA-110243";

    @Autowired private DatabaseObjectService dbs;
    @Autowired private SpeciesService speciesService;
    @Autowired private SchemaService schemaService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + StabilityAndConsistencyTest.class.getName() + "!!! --- \n");
    }

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = fitForService();
            checkedOnce = true;
        }

        //*******   ENABLING LAZY LOADING FOR A PROPER TESTING  *********
        lazyFetchAspect.setEnableAOP(true);

        assumeTrue(isFit);
        DatabaseObjectFactory.clearCache();
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void libraryStabilityTest() {
        logger.info("Testing libraryStabilityTest");

        ReactionLikeEvent rle = dbs.findById(stId);

        PhysicalEntity activeUnit1 = getCatalystActivityActiveUnit(rle);

        Complex c = dbs.findById("R-HSA-110185");
        assertTrue("Complex is found", c != null);

        PhysicalEntity activeUnit2 = getCatalystActivityActiveUnit(rle);

        assertTrue("Active units 1 and 2 should be the same", activeUnit1.equals(activeUnit2));

        logger.info("Finished");
    }

    private PhysicalEntity getCatalystActivityActiveUnit(ReactionLikeEvent reactionLikeEvent){
        assertTrue("wrong size", reactionLikeEvent.getCatalystActivity().size() == 1);
        CatalystActivity catalystActivity = reactionLikeEvent.getCatalystActivity().get(0);
        assertTrue("wrong size", catalystActivity.getActiveUnit().size() == 1);
        return catalystActivity.getActiveUnit().iterator().next();
    }

    @Test
    public void lazyLoadingStoichiometryTest(){
        logger.info("Testing lazyLoadingStoichiometryTest");


        Species species = speciesService.getSpeciesByName("Homo sapiens");

        int comps1 = -1;
        Collection<Complex> complexes = schemaService.getByClass(Complex.class, species);
        for (Complex complex : complexes) {
            if (complex.getStId().equals("R-HSA-83538")) {
                List<PhysicalEntity> hasComponent = complex.getHasComponent();
                comps1 = hasComponent.size();
            }
        }

        Complex complex = dbs.findById("R-HSA-83538");
        List<PhysicalEntity> hasComponent = complex.getHasComponent();
        int comps2 = hasComponent.size();

        assertTrue("Has component should be the same", Objects.equals(comps1, comps2));

        logger.info("Finished");
    }
}