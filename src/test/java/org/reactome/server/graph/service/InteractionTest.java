package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Interaction;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.reactome.server.graph.domain.model.UndirectedInteraction;
import org.reactome.server.graph.domain.result.DiagramOccurrences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class InteractionTest extends BaseTest {

    // TODO Test InteractionTest

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
        assertTrue("There should be at least one PE pointing to P60484", found);
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
        assertFalse("There should not be any PE pointing to P60484-1", found);
    }

    @Test
    public void getLowerLevelPathwaysTest(){
        logger.info("Started testing interactionsService.getLowerLevelPathways");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = interactionsService.getLowerLevelPathways("Q9BXM7-1", "Homo sapiens");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue("There should more than 2 pathways for Q9BXM7-1", pathways.size() > 2);
    }

    @Test
    public void getDiagrammedLowerLevelPathwaysTest(){
        logger.info("Started testing interactionsService.getDiagrammedLowerLevelPathways");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = interactionsService.getDiagrammedLowerLevelPathways("Q9BXM7-1", "Homo sapiens");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue("There should more than 2 pathways for Q9BXM7-1", pathways.size() > 2);
    }

    @Test
    public void getDiagramOccurrencesTest(){
        logger.info("Started testing interactionsService.getDiagramOccurrences");
        long start = System.currentTimeMillis();
        Collection<DiagramOccurrences> occurrences = interactionsService.getDiagramOccurrences("Q9BXM7-1");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue("There should more than 3 diagram occurrences for Q9BXM7-1", occurrences.size() > 3);
        boolean found = false;
        for (DiagramOccurrences item : occurrences) {
            if(item.getDiagramStId().equals("R-HSA-1428517")){
                found = true;
                assertFalse("There is at least one occurrence of 'Q9BXM7-1' for 'R-HSA-1428517'", item.getOccurrences().isEmpty());
            }
        }
        assertTrue("There is at least one occurrence of 'Q9BXM7-1' for 'R-HSA-1428517'", found);
    }
}
