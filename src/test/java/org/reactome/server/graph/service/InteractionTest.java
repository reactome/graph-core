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
        Collection<Interaction> interactions = interactionsService.getInteractions("P60484");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        boolean found = false;
        for (Interaction interaction : interactions) {
            UndirectedInteraction ui = (UndirectedInteraction) interaction;
            ReferenceEntity re = ui.getInteractor().get(0);
            found |= (re.getPhysicalEntity() != null && !re.getPhysicalEntity().isEmpty());
        }
        assertTrue(found, "There should be at least one PE pointing to P60484");
    }

    @Test
    public void getInteractionsForTP53() {
        logger.info("Started testing interactionsService.getInteractions");
        long start = System.currentTimeMillis();
        Collection<Interaction> interactions = interactionsService.getInteractions("P04637");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        boolean found = false;
        for (Interaction interaction : interactions) {
            UndirectedInteraction ui = (UndirectedInteraction) interaction;
            ReferenceEntity re = ui.getInteractor().get(0);
            found |= (re.getPhysicalEntity() != null && !re.getPhysicalEntity().isEmpty());
        }
        assertTrue(found, "There should be at least one PE pointing to P60484");
    }

    @Test
    public void getInteractionsForTP53PhysicalEntities() {
        logger.info("Started testing interactionsService.getInteractions");
        long start = System.currentTimeMillis();
        UndirectedInteraction interaction  = (UndirectedInteraction) interactionsService.getSingleInteractionDetails("P04637", "P04637");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        boolean found = false;
        List<ReferenceEntity> interactor = interaction.getInteractor();
        for (ReferenceEntity re : interactor) {
            found |= (re.getPhysicalEntity() != null && !re.getPhysicalEntity().isEmpty());
        }
        assertTrue(found, "There should be at least one PE pointing to P60484");
    }

    @Test
    public void getInteractionsNoneInReactome() {
        logger.info("Started testing interactionsService.getInteractionsNoneInReactome");
        long start = System.currentTimeMillis();
        Collection<Interaction> interactions = interactionsService.getInteractions("Q5T2D3");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        boolean found = false;
        for (Interaction interaction : interactions) {
            UndirectedInteraction ui = (UndirectedInteraction) interaction;
            ReferenceEntity re = ui.getInteractor().get(0);
            found |= (re.getPhysicalEntity() != null && !re.getPhysicalEntity().isEmpty());
        }
        assertTrue(found, "There should not be any PE pointing to P60484-1");
    }

    @Test
    public void getLowerLevelPathwaysTest(){
        logger.info("Started testing interactionsService.getLowerLevelPathways");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = interactionsService.getLowerLevelPathways("Q9BXM7-1", "Homo sapiens");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue(pathways.size() > 2, "There should more than 2 pathways for Q9BXM7-1");
    }

    @Test
    public void getDiagrammedLowerLevelPathwaysTest(){
        logger.info("Started testing interactionsService.getDiagrammedLowerLevelPathways");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = interactionsService.getDiagrammedLowerLevelPathways("Q9BXM7-1", "Homo sapiens");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue(pathways.size() > 2, "There should more than 2 pathways for Q9BXM7-1");
    }

    @Test
    public void getDiagramOccurrencesTest(){
        //TODO
        logger.info("Started testing interactionsService.getDiagramOccurrences");
        long start = System.currentTimeMillis();
        Collection<DiagramOccurrences> occurrences = interactionsService.getDiagramOccurrences("Q9BXM7-1");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue(occurrences.size() > 3, "There should more than 3 diagram occurrences for Q9BXM7-1");
        boolean found = false;
        for (DiagramOccurrences item : occurrences) {
            if(item.getDiagramStId().equals("R-HSA-1428517")){
                found = true;
                assertFalse(item.getOccurrences().isEmpty(), "There is at least one occurrence of 'Q9BXM7-1' for 'R-HSA-1428517'");
            }
        }
        assertTrue(found, "There is at least one occurrence of 'Q9BXM7-1' for 'R-HSA-1428517'");
    }

    @Test
    public void testCountInteractionsByAccession(){
        logger.info("Started testing interactionsService.testCountInteractionsByAccession");
        long start = System.currentTimeMillis();
        Integer count = interactionsService.countInteractionsByAccession("Q9BXM7-1");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue(count >= 2, "Count has to be greater than 2");
    }

    @Test
    public void testCountInteractionsByAccessions(){
        logger.info("Started testing interactionsService.testCountInteractionsByAccessions");
        long start = System.currentTimeMillis();
        Map<String, Integer> map = interactionsService.countInteractionsByAccessions(Arrays.asList("Q9BXM7-1", "P60484", "Q9BXM7"));
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertThat(map).containsKeys("Q9BXM7", "P60484");
        assertThat(map.values()).allSatisfy(d -> assertThat(d).isGreaterThan(20));
    }

}
