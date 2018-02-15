package org.reactome.server.graph.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.result.DiagramResult;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void getDiagramTest() {
        logger.info("Started testing diagramService.getDiagramTest");
        long start = System.currentTimeMillis();
        DiagramResult dr = diagramService.getDiagramResult("R-HSA-6799198");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull("The diagram result object cannot be null", dr);
        assertTrue("The diagram containing 'R-HSA-6799198' is 'R-HSA-163200'", dr.getDiagramStId().equals("R-HSA-163200"));
    }

}
