package uk.ac.ebi.reactome.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.reactome.Application;
import uk.ac.ebi.reactome.repository.GenericRepository;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EventServiceTest {

    @Autowired
    private GenericRepository genericRepository;

    @Autowired
    private ImportService importService;

    @Autowired
    private Session session;

    @Before
    public void setUp(){
        session.purgeDatabase();

    }


    @Test
    public void testFindAll() {
//        importService.getOrCreate(new Reaction(123L, "R_123", "bla"));
//        importService.getOrCreate(new EntityWithAccessionedSequence(124L,"R_124", "bla"));
//
//        genericRepository.addRelationship();
//
//        System.out.println("");
    }

//    @Test
//    public void testGetOrCreateEvent() {
//        eventService.getOrCreate(new Pathway(3L, "React_3.1", "Pathway"));
//        eventService.getOrCreate(new Pathway(3L, "React_3.1", "Pathway"));
//        eventService.getOrCreate(new Polymerisation(4L, "React_4.1", "Polymerisation"));
//        Collection<Event> events = (Collection<Event>) eventService.findAll();
//        assertEquals(4, events.size());
//    }
//
//    @Test
//    public void testFindAll() {
//        Collection<Event> events = (Collection<Event>) eventService.findAll();
//        assertEquals(2, events.size());
//    }
//
//    @Test
//    public void findByDbId() {
//        Event pathway = eventService.findByDbId(1L);
//        Event reaction = eventService.findByDbId(2L);
//        assertEquals(Long.valueOf(1L), pathway.getDbId());
//        assertEquals(Long.valueOf(2L), reaction.getDbId());
//        assertEquals("React_1.1", pathway.getStId());
//        assertEquals("React_2.1", reaction.getStId());
//        assertEquals("Pathway", pathway.getName());
//        assertEquals("Reaction", reaction.getName());
//    }
//
//    @Test
//    public void findByStId() {
//        Event pathway = eventService.findByDbId(1L);
//        Event reaction = eventService.findByDbId(2L);
//        assertEquals(Long.valueOf(1L), pathway.getDbId());
//        assertEquals(Long.valueOf(2L), reaction.getDbId());
//        assertEquals("React_1.1", pathway.getStId());
//        assertEquals("React_2.1", reaction.getStId());
//        assertEquals("Pathway", pathway.getName());
//        assertEquals("Reaction", reaction.getName());
//    }
}
