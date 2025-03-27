package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class OrthologyServiceTest extends BaseTest {

    @Autowired
    private OrthologyService orthologyService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + OrthologyServiceTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void getOrthologyTest() {
        logger.info("Started testing orthologyService.getOrthology");
        long start = System.currentTimeMillis();
        Collection<DatabaseObject> orthology = orthologyService.getOrthology(Events.diagramPathway.getStId(), homoSapiensSpecies.getDbId());
        long time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(orthology.iterator().next().getStId(), "The orthology cannot be null");
    }

}
