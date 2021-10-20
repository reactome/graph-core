package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Compartment;
import org.reactome.server.graph.domain.model.ReactionLikeEvent;
import org.reactome.server.graph.util.DatabaseObjectFactory;
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
        ReactionLikeEvent rleGDB = databaseObjectService.findById("1247999");
        ReactionLikeEvent rleRDB = DatabaseObjectFactory.createObject("1247999");

        assertEquals(rleRDB.getCompartment().size(), rleGDB.getCompartment().size(), "Different sizes");

        for (int i = 0; i < rleRDB.getCompartment().size(); i++) {
            Compartment cRDB = rleRDB.getCompartment().get(i);
            Compartment cGDB = rleGDB.getCompartment().get(i);
            assertEquals(cGDB, cRDB, cGDB + " differs of " + cRDB);
        }

        time = System.currentTimeMillis() - start;
        logger.info("Comparison execution time: " + time + "ms");

        logger.info("Finished");
    }

}