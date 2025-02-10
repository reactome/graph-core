package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.repository.AdvancedDatabaseObjectRepository;
import org.reactome.server.graph.repository.DatabaseObjectRepository;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 *         507868 Will test wrong. Difference is that duplications are removed in the graph
 */
public class DatabaseObjectServiceTest extends BaseTest {

    @Autowired
    private AdvancedDatabaseObjectRepository advancedDatabaseObjectRepository;

    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + DatabaseObjectServiceTest.class.getName() + "!!! --- \n");
    }

    @Test
    public void findByDbIdTest() {

        logger.info("Started testing databaseObjectService.findByDbIdTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectRepository.findByDbId(Events.transitionReaction.getDbId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertThat(databaseObjectObserved.getDbId()).isEqualTo(Events.transitionReaction.getDbId());
        logger.info("Finished");
    }

    @Test
    public void findHomoSapiensTest() {

        logger.info("Started testing databaseObjectService.findHomoSapiensTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = advancedDatabaseObjectRepository.findById(homoSapiensSpecies.getDbId(), RelationshipDirection.INCOMING);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(databaseObjectObserved);
        assertEquals(homoSapiensSpecies.getDisplayName(), databaseObjectObserved.getDisplayName());
        logger.info("Finished");
    }

    @Test
    public void findByStIdTest() throws IllegalAccessException, InvocationTargetException {

        logger.info("Started testing databaseObjectService.findByStIdTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectService.findById(Events.transitionReaction.getStId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertThat(databaseObjectObserved.getStId()).isEqualTo(Events.transitionReaction.getStId());
        logger.info("Finished");

    }


    @Test
    public void findByStIdRelationsTest() {

        logger.info("Started testing databaseObjectService.findByStIdRelationsTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectService.findByIdNoRelations(Events.dissociationReaction.getStId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(Events.dissociationReaction.getStId(), databaseObjectObserved.getStId());
        logger.info("Finished");

    }

    @Test
    public void findByStIdsTest() {

        logger.info("Started testing databaseObjectService.findByStIds");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DatabaseObject> databaseObjectsObserved = databaseObjectService.findByIdsNoRelations(Arrays.asList(Events.associationReaction.getStId(),Events.dissociationReaction.getStId(), Events.transitionReaction.getStId()));
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(3, databaseObjectsObserved.size());
        logger.info("Finished");

    }
}