package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.result.DoiPathwayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DoiServiceTest extends BaseTest {

    @Autowired
    public DoiService doiService;

    @Test
    public void testAllDoiPathway(){
        logger.info("Started testing DoiServiceTest.testAllDoiPathway");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DoiPathwayDTO> dois = doiService.getAllDoiPathway();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertNotNull(dois);
        assertTrue(dois.size() > 15);
        logger.info("Finished");
    }

}
