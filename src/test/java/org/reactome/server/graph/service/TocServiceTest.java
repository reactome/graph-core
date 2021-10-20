package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.result.TocPathwayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TocServiceTest extends BaseTest {

    @Autowired
    public TocService tocService;

    @Test
    public void testAllTocPathway(){
        logger.info("Started testing TocServiceTest.testAllTocPathway");
        long start, time;
        start = System.currentTimeMillis();
        Collection<TocPathwayDTO> tocs = tocService.getAllTocPathway();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(tocs);
        assertTrue(tocs.size() > 15);
        logger.info("Finished");
    }
}
