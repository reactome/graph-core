package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

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
        Complex complex = databaseObjectService.findById(PhysicalEntities.complex.getStId());
        Collection<PhysicalEntity> inferredTo = complex.getInferredTo();
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(inferredTo, "inferredTo for complex cannot be null");
        PhysicalEntity aux = null;
        for (PhysicalEntity pe : inferredTo) {
            if (pe.getStId().equals(PhysicalEntities.complexInferred.getStId())) aux = pe;
        }

        assertNotNull(aux);
    }

    @Test
    public void getManuallyInferredToForReactionTest() {
        logger.info("Started testing InferredToTest.getManuallyInferredToForReaction");
        long start = System.currentTimeMillis();
        Reaction reaction = databaseObjectService.findById(Events.transitionReaction.getStId());
        Collection<Event> inferredTo = reaction.getOrthologousEvent();
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(inferredTo, "The orthologous events for reaction cannot be null");
        Event aux = null;
        for (Event pe : inferredTo) {
            if (pe.getStId().equals(Events.cellLineagePathway.getStId())) aux = pe;
        }

        assertNotNull(aux, "The reaction has references");
    }

    @Test
    public void getComputationallyInferredTest(){
        logger.info("Started testing InferredToTest.getComputationallyInferred");
        long start = System.currentTimeMillis();
        Event event = databaseObjectService.findById((Events.transitionReaction.getStId()));
        Collection<Event> inferredTo = event.getOrthologousEvent();
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(inferredTo, "The getOrthologousEvent cannot be null");
    }

}
