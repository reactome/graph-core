package org.reactome.server.graph.service;

import org.gk.model.GKInstance;
import org.gk.persistence.GKBWriter;
import org.gk.persistence.Project;
import org.gk.persistence.XMLFileAdaptor;
import org.gk.schema.Schema;
import org.gk.schema.SchemaClass;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.model.Publication;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class  PublicationTest extends BaseTest {

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + PublicationTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void findAuthorsList() throws Exception {
        logger.info("Started testing publicationTest.findAuthorsList");

        long start, time;
        start = System.currentTimeMillis();
        Publication publicationGDB = databaseObjectService.findById(testPublication.getStId());
        //Publication publicationRDB = DatabaseObjectFactory.createObject(testPublication.getStId());
        GKInstance gkInstance = DatabaseObjectFactory.createGKInstance(testPublication);

        time = System.currentTimeMillis() - start;
        logger.info("Comparison execution time: " + time + "ms");

        assertEquals(publicationGDB.getDbId(), gkInstance.getDBID());
        logger.info("Finished");
    }

    @Test
    public void personPublications() {
        logger.info("Started testing publicationTest.personPublications");

        long start, time;
        start = System.currentTimeMillis();

        Person person = databaseObjectService.findById(testPerson.getStId());
        assertFalse(person.getPublications().isEmpty());

        time = System.currentTimeMillis() - start;
        logger.info("Comparison execution time: " + time + "ms");

        logger.info("Finished");
    }
}