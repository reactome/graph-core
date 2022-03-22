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
        //TODO
        logger.info("Started testing diagramService.getDiagramTest");
        long start = System.currentTimeMillis();
        DiagramResult dr1 = diagramService.getDiagramResult("R-HSA-6799198");
        DiagramResult dr2 = diagramService.getDiagramResult(6799198L);
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(dr1, "The diagram result object cannot be null");
        assertEquals(dr1.getDiagramStId(), "R-HSA-163200", "The diagram containing 'R-HSA-6799198' is 'R-HSA-163200'");
        assertTrue(dr1.getEvents().size() == 13 && dr1.getEvents().contains("R-HSA-5690023"));

        assertNotNull(dr2, "The diagram result object cannot be null");
        assertEquals(dr2.getDiagramStId(), "R-HSA-163200", "The diagram containing 'R-HSA-6799198' is 'R-HSA-163200'");
        assertEquals(dr2.getEvents().size(), 13);
        assertTrue(dr2.getEvents().contains("R-HSA-5690023"));
    }

    @Test
    public void getDiagramOccurrencesTest1(){
        logger.info("Started testing diagramService.getDiagramOccurrences1");
        long start = System.currentTimeMillis();
        Collection<DiagramOccurrences> dos = diagramService.getDiagramOccurrences("R-HSA-5690771");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(dos, "The diagram occurrences result object cannot be null");
        assertTrue(dos.size() >= 6, "There are at least 6 occurrences of 'R-HSA-5690771'");

        for (DiagramOccurrences o : dos) {
            assertNotNull(o.getDiagramStId());
            if(o.getDiagramStId().equals("R-HSA-5688426")){
                assertTrue(o.isInDiagram(), "'R-HSA-5690771' is directly contained in 'R-HSA-5688426'");
            }
        }
    }

    @Test
    public void getDiagramOccurrencesTest2(){
        logger.info("Started testing diagramService.getDiagramOccurrences2");
        long start = System.currentTimeMillis();
        Collection<DiagramOccurrences> dos = diagramService.getDiagramOccurrences("R-HSA-372542");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(dos, "The diagram occurrences result object cannot be null");
        assertTrue(dos.size() >= 2, "There are at least 2 occurrences of 'R-HSA-372542'");

        for (DiagramOccurrences o : dos) {
            assertNotNull(o.getDiagramStId());
            if(o.getDiagramStId().equals("R-HSA-112310")){
                assertTrue(o.isInDiagram(), "'R-HSA-372542' is directly contained in 'R-HSA-112310'");
            }
        }
    }

    @Test
    public void getDiagramOccurrencesTest3(){
        logger.info("Started testing diagramService.getDiagramOccurrencesTest3");
        long start = System.currentTimeMillis();
        Collection<DiagramOccurrences> dos = diagramService.getDiagramOccurrences("R-HSA-879382");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(dos, "The diagram occurrences result object cannot be null");
        assertTrue(dos.size() >= 20, "There are at least 20 occurrences of 'R-HSA-879382'");

        for (DiagramOccurrences o : dos) {
            assertNotNull(o.getDiagramStId());
            if(o.getDiagramStId().equals("R-HSA-168164")){
                assertFalse(o.isInDiagram(), "'R-HSA-879382' should not be directly contained in 'R-HSA-168164'");
                assertFalse(o.getOccurrences().isEmpty());
                assertThat(o.getOccurrences()).containsAnyOf("R-HSA-445989", "R-HSA-109581", "R-HSA-450294");
            }
            if(o.getDiagramStId().equals("R-HSA-168928")){
                assertTrue(o.isInDiagram(), "'R-HSA-879382' should be directly contained in 'R-HSA-168928'");
                assertTrue(o.getOccurrences().isEmpty());
                // TODO
            }
        }
    }
}
