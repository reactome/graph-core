package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Interaction;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.reactome.server.graph.domain.model.UndirectedInteraction;
import org.reactome.server.graph.domain.result.DiagramOccurrences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InteractionTest extends BaseTest {

    @Autowired
    private InteractionsService interactionsService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + InteractionTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void getInteractions() {
        logger.info("Started testing interactionsService.getInteractions");
        long start = System.currentTimeMillis();
        Collection<Interaction> interactions = interactionsService.getInteractions(PhysicalEntities.referenceEntityInteractor.getIdentifier());
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        boolean found = false;
        for (Interaction interaction : interactions) {
            UndirectedInteraction ui = (UndirectedInteraction) interaction;
            ReferenceEntity re = ui.getInteractor().get(0);
            found |= (re.getPhysicalEntity() != null && !re.getPhysicalEntity().isEmpty());
        }
        assertTrue(found, "There should be at least one PE");
    }


    @Test
    public void getInteractionsDetailsForPhysicalEntities() {
        logger.info("Started testing interactionsService.getInteractions");
        long start = System.currentTimeMillis();
        UndirectedInteraction interaction  = (UndirectedInteraction) interactionsService.getSingleInteractionDetails("PROTTESTDB", "PROTTESTDB");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        boolean found = false;
        List<ReferenceEntity> interactor = interaction.getInteractor();
        for (ReferenceEntity re : interactor) {
            found |= (re.getPhysicalEntity() != null && !re.getPhysicalEntity().isEmpty());
        }
        assertTrue(found, "There should be at least one PE pointing");
    }

    @Test
    public void getInteractionsNoneInReactome() {
        logger.info("Started testing interactionsService.getInteractionsNoneInReactome");
        long start = System.currentTimeMillis();
        Collection<Interaction> interactions = interactionsService.getInteractions(PhysicalEntities.referenceEntityInteractor.getIdentifier());
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        boolean found = false;
        for (Interaction interaction : interactions) {
            UndirectedInteraction ui = (UndirectedInteraction) interaction;
            ReferenceEntity re = ui.getInteractor().get(0);
            found |= (re.getPhysicalEntity() != null && !re.getPhysicalEntity().isEmpty());
        }
        assertTrue(found, "There should not be any PE");
    }

    @Test
    public void getLowerLevelPathwaysTest(){
        // add new pathway to top level pathwaya and add referce entity and specis with it
        logger.info("Started testing interactionsService.getLowerLevelPathways");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = interactionsService.getLowerLevelPathways(PhysicalEntities.referenceEntityInteractor.getIdentifier(), "Homo sapiens");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue(!pathways.isEmpty(), "There should be at least one pathway");
    }

    @Test
    public void getDiagramOccurrencesTest(){
        logger.info("Started testing interactionsService.getDiagramOccurrences");
        long start = System.currentTimeMillis();
        Collection<DiagramOccurrences> occurrences = interactionsService.getDiagramOccurrences(PhysicalEntities.referenceEntityInteractor.getIdentifier());
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue(occurrences.size() > 1, "There should more than 1 diagram");
    }

    @Test
    public void testCountInteractionsByAccession(){
        logger.info("Started testing interactionsService.testCountInteractionsByAccession");
        long start = System.currentTimeMillis();
        Integer count = interactionsService.countInteractionsByAccession(PhysicalEntities.referenceEntityInteractor.getIdentifier());
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue(count >= 1, "Count has to be greater than 1");
    }

    @Test
    public void testCountInteractionsByAccessions(){
        logger.info("Started testing interactionsService.testCountInteractionsByAccessions");
        long start = System.currentTimeMillis();
        Map<String, Integer> map = interactionsService.countInteractionsByAccessions(Arrays.asList(PhysicalEntities.referenceEntityInteractor.getIdentifier(), PhysicalEntities.referenceEntityInteraction.getIdentifier()));
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertThat(map).containsKeys(PhysicalEntities.referenceEntityInteractor.getIdentifier());
    }

}
