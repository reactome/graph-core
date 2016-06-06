package org.reactome.server.graph.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.Neo4jConfig;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.model.Publication;
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
 * @since 31.05.16.
 */
@ContextConfiguration(classes = {Neo4jConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    @Autowired
    private GeneralService generalService;

    @Autowired
    private PersonService personService;

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

    //TODO Fix
    @Test
    public void findPersonByNameTest() {
        logger.info("Started testing genericService.findPersonByName");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Person> persons = personService.findPersonByName("steve");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(14,persons.size());
        logger.info("Finished");
    }

    //TODO Fix
    @Test
    public void queryPersonByNameTest() {
        logger.info("Started testing genericService.queryPersonByName");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Person> persons = personService.queryPersonByName("steve jupe");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(185,persons.size());
        logger.info("Finished");
    }

    @Test
    public void findPersonByOrcidIdTest() {
        logger.info("Started testing genericService.findPersonByOrcidId");
        long start, time;
        start = System.currentTimeMillis();
        Person person = personService.findPerson("0000-0001-5807-0069");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals("Jupe, Steve", person.getDisplayName());
        logger.info("Finished");
    }

    @Test
    public void findPersonByDbIdTest() {
        logger.info("Started testing genericService.findPersonByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Person person = personService.findPerson(391309L);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals("Jupe, Steve", person.getDisplayName());
        logger.info("Finished");
    }


    @Test
    public void getPublicationsOfPersonByOrcidIdTest() {
        logger.info("Started testing genericService.getPublicationsOfPersonByOrcidId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Publication> publications = personService.getPublicationsOfPerson("0000-0001-5807-0069");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(3, publications.size());
        logger.info("Finished");
    }

    @Test
    public void getPublicationsOfPersonByDbIdTest() {
        logger.info("Started testing genericService.getPublicationsOfPersonByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Publication> publications = personService.getPublicationsOfPerson(391309L);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(3, publications.size());
        logger.info("Finished");
    }


    @Test
    public void getAuthoredPathwaysByOrcidIdTest() {
        logger.info("Started testing genericService.getAuthoredPathwaysByOrcidId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = personService.getAuthoredPathways("0000-0001-5807-0069");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(65, pathways.size());
        logger.info("Finished");
    }

    @Test
    public void getAuthoredPathwaysByDbIdTest() {
        logger.info("Started testing genericService.getAuthoredPathwaysByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = personService.getAuthoredPathways(391309L);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(65, pathways.size());
        logger.info("Finished");
    }

}