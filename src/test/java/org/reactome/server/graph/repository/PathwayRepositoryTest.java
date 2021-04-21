package org.reactome.server.graph.repository;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Pathway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class PathwayRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private final PathwayRepository pathwayRepository;

    @Autowired
    public PathwayRepositoryTest(PathwayRepository pathwayRepository) {
        this.pathwayRepository = pathwayRepository;
    }

    @Test
    public void getPathwaysForTest(){
        logger.info("Started testing fireworksService.getPathwaysForTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwayRepository.getPathwaysForAllFormsOfByStIdAndSpeciesTaxId("R-ALL-113592", "9606");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 429 or more pathways with ATP (R-ALL-113592) in human", pathways.size() >= 429);
    }

    @Test
    public void getPathwaysForAllFormsOfTest(){
        logger.info("Started testing fireworksService.getPathwaysForAllFormsOfTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwayRepository.getPathwaysForAllFormsOfByStIdAndSpeciesTaxId("R-ALL-113592", "9606");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 591 or more pathways for all forms of ATP (R-ALL-113592) in human", pathways.size() >= 591);
    }

    @Test
    public void getPathwaysWithDiagramForTest(){
        logger.info("Started testing fireworksService.getPathwaysWithDiagramForTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwayRepository.getPathwaysWithDiagramForByStIdAndSpeciesTaxId("R-HSA-199420", "9606");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 5 or more pathways with diagram PTEN (R-HSA-199420) in human", pathways.size() >= 5);
    }

    @Test
    public void getPathwaysWithDiagramForAllFormsOfTest(){
        logger.info("Started testing fireworksService.getPathwaysWithDiagramForAllFormsOfTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwayRepository.getPathwaysWithDiagramForAllFormsOfByStIdAndSpeciesTaxId("R-HSA-199420", "9606");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 5 or more pathways with diagram for all forms of PTEN (R-HSA-199420) in human", pathways.size() >= 6);
    }

    @Test
    public void getLowerLevelPathwaysForIdentifierTest(){
        logger.info("Started testing pathwaysService.getLowerLevelPathwaysForIdentifierTest");
        long start = System.currentTimeMillis();
        Collection<Pathway> pathways = pathwayRepository.getLowerLevelPathwaysForIdentifierAndSpeciesTaxId("PTEN", "9606");
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue("There should be 9 or more pathways containing PTEN in human", pathways.size() >= 9);
    }


    @Autowired
    DatabaseObjectRepository databaseObjectRepository;

    @Test
    public void testFindNewStId(){
        String s = databaseObjectRepository.findNewStId("REACT_121408");
        System.out.println(s);

    }
}
