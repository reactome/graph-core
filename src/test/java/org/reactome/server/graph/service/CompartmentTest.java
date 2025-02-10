package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CompartmentTest extends BaseTest{

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + CompartmentTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void findCompartmentList() {
        logger.info("Started testing compartmentTest.findCompartmentList");

        long start, time;
        start = System.currentTimeMillis();
        ReactionLikeEvent rleGDB = databaseObjectService.findById(Events.associationReaction.getStId());
        assertEquals(Events.associationReaction.getStId(), rleGDB.getStId());
        assertEquals(PhysicalEntities.catalystActivity.getDisplayName(), rleGDB.getCatalystActivity().get(0).getDisplayName());

        logger.info("Finished");
    }

}