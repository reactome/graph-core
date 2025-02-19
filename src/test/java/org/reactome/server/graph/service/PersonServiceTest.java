package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.model.Publication;
import org.reactome.server.graph.domain.result.PersonAuthorReviewer;
import org.reactome.server.graph.domain.result.SimpleEventProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 31.05.16.
 */
public class PersonServiceTest extends BaseTest {

    @Autowired
    private PersonService personService;

    @BeforeTestClass
    public  void setUpClass() {
        logger.info(" --- !!! Running " + PersonServiceTest.class.getName() + " !!! --- \n");
    }

    @Test
    public void findPersonByNameTest() {
        logger.info("Started testing personService.findPersonByName");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Person> persons = personService.findPersonByName(testPerson.getDisplayName());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1, persons.size());
        logger.info("Finished");
    }

    @Test
    public void queryPersonByNameTest() {
        logger.info("Started testing personService.queryPersonByName");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Person> persons = personService.queryPersonByName(testPerson.getDisplayName());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1, persons.size());
        logger.info("Finished");
    }

    @Test
    public void findPersonByOrcidIdTest() {
        logger.info("Started testing personService.findPersonByOrcidIdTest");
        long start, time;
        start = System.currentTimeMillis();
        Person person = personService.findPerson(testPerson.getOrcidId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(testPerson.getDisplayName(), person.getDisplayName());
        logger.info("Finished");
    }

    @Test
    public void findPersonByDbIdTest() {
        logger.info("Started testing personService.findPersonByDbIdTest");
        long start, time;
        start = System.currentTimeMillis();
        Person person = personService.findPerson(testPerson.getDbId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(testPerson.getDisplayName(), person.getDisplayName());
        logger.info("Finished");
    }

    @Test
    public void getPublicationsOfPersonByOrcidIdTest() {
        logger.info("Started testing personService.getPublicationsOfPersonByOrcidId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Publication> publications = personService.getPublicationsOfPerson(testPerson.getOrcidId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1, publications.size());
        logger.info("Finished");
    }

    @Test
    public void getPublicationsOfPersonByDbIdTest() {
        logger.info("Started testing personService.getPublicationsOfPersonByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Publication> publications = personService.getPublicationsOfPerson(testPerson.getDbId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1, publications.size());
        logger.info("Finished");
    }

    @Test
    public void getAuthoredPathwaysByOrcidIdTest() {
        logger.info("Started testing personService.getAuthoredPathwaysByOrcidId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleEventProjection> pathways = personService.getAuthoredPathways(testPerson.getOrcidId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!pathways.isEmpty());
        logger.info("Finished");
    }

    @Test
    public void getAuthoredPathwaysByDbIdTest() {
        logger.info("Started testing personService.getAuthoredPathwaysByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleEventProjection> pathways = personService.getAuthoredPathways(testPerson.getDbId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!pathways.isEmpty());
        logger.info("Finished");
    }


    @Test
    public void getAuthoredReactionsByOrcidIdTest() {
        logger.info("Started testing personService.getAuthoredReactionsByOrcidId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleEventProjection> reactions = personService.getAuthoredReactions(testPerson.getOrcidId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!reactions.isEmpty());
        logger.info("Finished");
    }


    @Test
    public void getAuthoredReactionsByDbIdTest() {
        logger.info("Started testing personService.getAuthoredReactionsByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleEventProjection> pathways = personService.getAuthoredReactions(testPerson.getDbId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!pathways.isEmpty());
        logger.info("Finished");
    }

    @Test
    public void getReviewedPathwaysByOrcidIdTest() {
        logger.info("Started testing personService.getReviewedPathwaysByOrcidId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleEventProjection> pathways = personService.getReviewedPathways(testPerson.getOrcidId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!pathways.isEmpty());
        logger.info("Finished");
    }

    @Test
    public void getReviewedPathwaysByDbIdTest() {
        logger.info("Started testing personService.getReviewedPathwaysByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleEventProjection> pathways = personService.getReviewedPathways(testPerson.getDbId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!pathways.isEmpty());
        logger.info("Finished");
    }

    @Test
    public void getReviewedReactionsByOrcidIdTest() {
        logger.info("Started testing personService.getReviewedReactionsByOrcidId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleEventProjection> reactions = personService.getReviewedReactions(testPerson.getOrcidId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!reactions.isEmpty());
        logger.info("Finished");
    }

    @Test
    public void getReviewedReactionsByDbIdTest() {
        logger.info("Started testing personService.getReviewedReactionsByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleEventProjection> reactions = personService.getReviewedReactions(testPerson.getDbId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!reactions.isEmpty());
        logger.info("Finished");
    }

    @Test
    public void getAuthorsReviewersTest(){
        logger.info("Started testing personService.getAuthorsReviewers");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PersonAuthorReviewer> objs = personService.getAuthorsReviewers();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertNotNull(objs.iterator().next().getPerson());
        logger.info("Finished");
    }

}