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
import static org.junit.Assert.assertTrue;
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
        DatabaseObjectFactory.clearCache();
    }

    @Test
    public void findPersonByNameTest() {
        logger.info("Started testing personService.findPersonByName");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Person> persons = personService.findPersonByName("steve jupe");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1,persons.size());
        logger.info("Finished");
    }

    @Test
    public void queryPersonByNameTest() {
        logger.info("Started testing personService.queryPersonByName");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Person> persons = personService.queryPersonByName("steve jupe");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(persons.size() >= 14);
        logger.info("Finished");
    }

    @Test
    public void findPersonByOrcidIdTest() {
        logger.info("Started testing personService.findPersonByOrcidIdTest");
        long start, time;
        start = System.currentTimeMillis();
        Person person = personService.findPerson("0000-0001-5807-0069");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals("Jupe, S", person.getDisplayName());
        logger.info("Finished");
    }

    @Test
    public void findPersonByDbIdTest() {
        logger.info("Started testing personService.findPersonByDbIdTest");
        long start, time;
        start = System.currentTimeMillis();
        Person person = personService.findPerson(391309L);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals("Jupe, S", person.getDisplayName());
        logger.info("Finished");
    }

    @Test
    public void findPersonByEmailTest() {
        logger.info("Started testing personService.findPersonByEmailTest");
        long start, time;
        start = System.currentTimeMillis();
        Person person = personService.findPerson("sjupe@ebi.ac.uk");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals("Jupe, S", person.getDisplayName());
        logger.info("Finished");
    }

    @Test
    public void getPublicationsOfPersonByOrcidIdTest() {
        logger.info("Started testing personService.getPublicationsOfPersonByOrcidId");
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
        logger.info("Started testing personService.getPublicationsOfPersonByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Publication> publications = personService.getPublicationsOfPerson(391309L);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(3, publications.size());
        logger.info("Finished");
    }

    @Test
    public void getPublicationsOfPersonByEmailTest() {
        logger.info("Started testing personService.getPublicationsOfPersonByEmailTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Publication> publications = personService.getPublicationsOfPerson("sjupe@ebi.ac.uk");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(3, publications.size());
        logger.info("Finished");
    }

    @Test
    public void getAuthoredPathwaysByOrcidIdTest() {
        logger.info("Started testing personService.getAuthoredPathwaysByOrcidId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = personService.getAuthoredPathways("0000-0001-5807-0069");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 65);
        logger.info("Finished");
    }

    @Test
    public void getAuthoredPathwaysByDbIdTest() {
        logger.info("Started testing personService.getAuthoredPathwaysByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = personService.getAuthoredPathways(391309L);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 65);
        logger.info("Finished");
    }

    @Test
    public void getAuthoredPathwaysByEmailTest() {
        logger.info("Started testing personService.getAuthoredPathwaysByEmailTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = personService.getAuthoredPathways("sjupe@ebi.ac.uk");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 65);
        logger.info("Finished");
    }

}