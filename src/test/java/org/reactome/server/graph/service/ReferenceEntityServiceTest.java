package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReferenceEntityServiceTest extends BaseTest {

    @Autowired ReferenceEntityService referenceEntityService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + ReferenceEntityServiceTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void testGetReferenceEntitiesFor() {

        logger.info("Started testing databaseObjectService.testGetParticipantsByStId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<ReferenceEntity> refs = referenceEntityService.getReferenceEntitiesFor("15377");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(3, refs.size());
        logger.info("Finished");
    }
}


