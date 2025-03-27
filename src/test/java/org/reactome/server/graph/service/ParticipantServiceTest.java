package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.reactome.server.graph.domain.result.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipantServiceTest extends BaseTest {

    private static final String stId = "R-HSA-5205685";

    @Autowired
    private ParticipantService participantService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + ParticipantServiceTest.class.getName() + " !!! --- \n");
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
        Collection<ReferenceEntity> participants = participantService.getParticipatingReferenceEntities(PhysicalEntities.entityWithAccessionedSequence.getStId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!participants.isEmpty());
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
        Collection<PhysicalEntity> participants = participantService.getParticipatingPhysicalEntities(Events.associationReaction.getStId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!participants.isEmpty());
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
        Collection<Participant> participants = participantService.getParticipants(Events.associationReaction.getStId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse(participants.isEmpty());
        logger.info("Finished");
    }
}


