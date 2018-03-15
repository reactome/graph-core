package org.reactome.server.graph.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.Reaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class InferredToTest extends BaseTest {

    @Autowired
    DatabaseObjectService databaseObjectService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + InferredToTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void getManuallyInferredToForComplexTest() {
        logger.info("Started testing InferredToTest.getManuallyInferredToForComplex");
        long start = System.currentTimeMillis();
        Complex complex = databaseObjectService.findById("R-HSA-5682892");
        Collection<PhysicalEntity> inferredTo = complex.getInferredTo();
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull("The inferredTo for complex 'R-HSA-5682892' cannot be null", inferredTo);
        PhysicalEntity aux = null;
        for (PhysicalEntity pe : inferredTo) {
            if (pe.getStId().equals("R-MMU-5682892")) aux = pe;
        }

        assertNotNull("The complex 'R-HSA-5682892' should be manually inferredTo 'R-MMU-5682892'", aux);
    }

    @Test
    public void getManuallyInferredToForReactionTest() {
        logger.info("Started testing InferredToTest.getManuallyInferredToForReaction");
        long start = System.currentTimeMillis();
        Reaction reaction = databaseObjectService.findById("R-HSA-73577");
        Collection<Event> inferredTo = reaction.getOrthologousEvent();
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull("The orthologous events for reaction 'R-HSA-73577' cannot be null", inferredTo);
        Event aux = null;
        for (Event pe : inferredTo) {
            if (pe.getStId().equals("R-MMU-73577")) aux = pe;
        }

        assertNotNull("The reaction 'R-HSA-73577' should be manually inferredTo 'R-MMU-73577'", aux);
    }

    @Test
    public void getComputationallyInferredTest(){
        logger.info("Started testing InferredToTest.getComputationallyInferred");
        long start = System.currentTimeMillis();
        Event event = databaseObjectService.findById("R-HSA-68827");
        Collection<Event> inferredTo = event.getOrthologousEvent();
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull("The getOrthologousEvent for event 'R-HSA-68827' cannot be null", inferredTo);
        assertFalse("The getOrthologousEvent for event 'R-HSA-68827' cannot be empty", inferredTo.isEmpty());
    }

}
