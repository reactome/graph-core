package org.reactome.server.graph.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.model.Interaction;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.reactome.server.graph.domain.model.UndirectedInteraction;
import org.reactome.server.graph.domain.result.DiagramOccurrences;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InteractionTest extends BaseTest {

    @Autowired
    private InteractionsService interactionsService;

    @BeforeClass
    public static void setUpClass() {
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
        Collection<Pathway> pathways = interactionsService.getLowerLevelPathways("Q9BXM7-1");
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
        Collection<DiagramOccurrences> pathways = interactionsService.getDiagramOccurrences("Q9BXM7-1");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertTrue("There should more than 10 diagram occurrences for Q9BXM7-1", pathways.size() > 3);
    }
}
