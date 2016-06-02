package org.reactome.server.graph.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.Neo4jConfig;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.reactome.server.graph.domain.result.Participant;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.04.16.
 */
@ContextConfiguration(classes = {Neo4jConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ParticipantServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    private static final String stId = "R-HSA-5205685";

    @Autowired
    private GeneralService generalService;

    @Autowired
    private ParticipantService participantService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running DatabaseObjectServiceTests !!! --- \n");
    }

    @AfterClass
    public static void tearDownClass() {
        logger.info("\n\n");
    }

    @Before
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = generalService.fitForService();
            checkedOnce = true;
        }
        assumeTrue(isFit);
        generalService.clearCache();
        DatabaseObjectFactory.clearCache();
    }

    /**
     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
     * possibly change when content is added to reactome. This method will provide all participating ReferenceEntities
     * (even if the tests participatingMolecules 2 and 3 will provide 23, in this case 22 is the correct number)
     */
    @Test
    public void testGetParticipatingReferenceEntitiesByStId() {
        logger.info("Started testing databaseObjectService.testGetParticipatingReferenceEntities");
        long start, time;
        start = System.currentTimeMillis();
        Collection<ReferenceEntity> participants = participantService.getParticipatingReferenceEntities(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(22,participants.size());
        logger.info("Finished");
    }

    /**
     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
     * possibly change when content is added to reactome. This method will provide all participating PhysicalEntities
     * of an Event
     */
    @Test
    public void testGetParticipatingPhysicalEntitiesByStId() {
        logger.info("Started testing databaseObjectService.testGetParticipatingPhysicalEntitiesByStId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> participants = participantService.getParticipatingPhysicalEntities(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(22, participants.size());
        logger.info("Finished");
    }

    /**
     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
     * possibly change when content is added to reactome. This method will provide all participating Ewas
     * of an Event and their ReferenceEntities dbIds and names.
     */
    @Test
    public void testGetParticipantsByStId() {

        logger.info("Started testing databaseObjectService.testGetParticipantsByStId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Participant> participants = participantService.getParticipants(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(23, participants.size());
        logger.info("Finished");
    }
}


