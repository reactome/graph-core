package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.result.DiagramOccurrences;
import org.reactome.server.graph.domain.result.DiagramResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class DiagramServiceTest extends BaseTest {

    @Autowired
    private DiagramService diagramService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + DiagramServiceTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void getDiagramResultTest() {
        logger.info("Started testing diagramService.getDiagramTest");
        long start = System.currentTimeMillis();
        DiagramResult dr1 = diagramService.getDiagramResult(Events.diagramPathway.getStId());
        DiagramResult dr2 = diagramService.getDiagramResult(Events.ehldPathway.getStId());
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(dr1, "The diagram result object cannot be null");
        assertEquals(dr1.getDiagramStId(), Events.diagramPathway.getStId());

        assertNotNull(dr2, "The diagram result object cannot be null");
        assertEquals(dr2.getDiagramStId(), Events.ehldPathway.getStId());
    }

    @Test
    public void getDiagramOccurrencesTest(){
        logger.info("Started testing diagramService.getDiagramOccurrences1");
        long start = System.currentTimeMillis();
        Collection<DiagramOccurrences> dos = diagramService.getDiagramOccurrences(Events.diagramPathway.getStId());
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(dos, "The diagram occurrences result object cannot be null");
        assertTrue(dos.size() > 1, "There are is more than 1 occurrences");
    }
}
