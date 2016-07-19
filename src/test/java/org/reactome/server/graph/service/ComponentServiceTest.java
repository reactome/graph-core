package org.reactome.server.graph.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.result.ComponentOf;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 05.06.16.
 */
public class ComponentServiceTest extends BaseTest {

    @Autowired
    private ComponentService componentService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + ComponentServiceTest.class.getName() + "!!! --- \n");
    }

    @Test
    public void getComponentsOfTest() {

        logger.info("Started testing genericService.getComponentsOfTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<ComponentOf> componentOfs = componentService.getComponentsOf("R-HSA-199426");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1, componentOfs.size());
        logger.info("Finished");
    }
}