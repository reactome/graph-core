package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InferredToTest extends BaseTest {

    @Autowired private DatabaseObjectService databaseObjectService;

    @BeforeTestClass
    public void setUpClass() {
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

        assertNotNull(inferredTo, "The inferredTo for complex 'R-HSA-5682892' cannot be null");
        PhysicalEntity aux = null;
        for (PhysicalEntity pe : inferredTo) {
            if (pe.getStId().equals("R-MMU-5682892")) aux = pe;
        }

        assertNotNull(aux, "The complex 'R-HSA-5682892' should be manually inferredTo 'R-MMU-5682892'");
    }

    @Test
    public void getManuallyInferredToForReactionTest() {
        logger.info("Started testing InferredToTest.getManuallyInferredToForReaction");
        long start = System.currentTimeMillis();
        Reaction reaction = databaseObjectService.findById("R-HSA-73577");
        Collection<Event> inferredTo = reaction.getOrthologousEvent();
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(inferredTo, "The orthologous events for reaction 'R-HSA-73577' cannot be null");
        Event aux = null;
        for (Event pe : inferredTo) {
            if (pe.getStId().equals("R-MMU-73577")) aux = pe;
        }

        assertNotNull(aux, "The reaction 'R-HSA-73577' should be manually inferredTo 'R-MMU-73577'");
    }

    @Test
    public void getComputationallyInferredTest(){
        logger.info("Started testing InferredToTest.getComputationallyInferred");
        long start = System.currentTimeMillis();
        Event event = databaseObjectService.findById("R-SCE-9749401");
        Collection<Event> inferredTo = event.getOrthologousEvent();
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(inferredTo, "The getOrthologousEvent for event 'R-SCE-9749401' cannot be null");
        assertFalse(inferredTo.isEmpty(), "The getOrthologousEvent for event 'R-SCE-9749401' cannot be empty");
    }

}
