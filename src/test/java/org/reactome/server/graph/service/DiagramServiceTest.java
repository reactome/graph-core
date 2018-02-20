package org.reactome.server.graph.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.result.DiagramOccurrences;
import org.reactome.server.graph.domain.result.DiagramResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class DiagramServiceTest extends BaseTest {

    @Autowired
    private DiagramService diagramService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + DiagramServiceTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void getDiagramResultTest() {
        logger.info("Started testing diagramService.getDiagramTest");
        long start = System.currentTimeMillis();
        DiagramResult dr = diagramService.getDiagramResult("R-HSA-6799198");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull("The diagram result object cannot be null", dr);
        assertTrue("The diagram containing 'R-HSA-6799198' is 'R-HSA-163200'", dr.getDiagramStId().equals("R-HSA-163200"));
    }

    @Test
    public void getDiagramOccurrencesTest1(){
        logger.info("Started testing diagramService.getDiagramOccurrences1");
        long start = System.currentTimeMillis();
        Collection<DiagramOccurrences> dos = diagramService.getDiagramOccurrences("R-HSA-5690771");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull("The diagram occurrences result object cannot be null", dos);
        assertTrue("There are at least 6 occurrences of 'R-HSA-5690771'", dos.size() >= 6);

        for (DiagramOccurrences o : dos) {
            assertNotNull(o.getPathway());
            if(o.getPathway().getStId().equals("R-HSA-5688426")){
                assertNull("'5690771' is directly contained in '5688426'", o.getSubpathway());
            }
            if(o.getPathway().getSchemaClass().equals("TopLevelPathway")){
                assertNotNull(o.getSubpathway());
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

        assertNotNull("The diagram occurrences result object cannot be null", dos);
        assertTrue("There are at least 3 occurrences of 'R-HSA-5690771'", dos.size() >= 3);

        for (DiagramOccurrences o : dos) {
            assertNotNull(o.getPathway());
            if(o.getPathway().getStId().equals("R-HSA-112310")){
                assertNull("'372542' is directly contained in '112310'", o.getSubpathway());
            }
            if(o.getPathway().getSchemaClass().equals("TopLevelPathway")){
                assertNotNull(o.getSubpathway());
            }
        }
    }

}
