package uk.ac.ebi.reactome.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.reactome.Application;
import uk.ac.ebi.reactome.domain.model.Event;
import uk.ac.ebi.reactome.domain.model.Pathway;
import uk.ac.ebi.reactome.domain.model.Polymerisation;
import uk.ac.ebi.reactome.domain.model.Reaction;
import uk.ac.ebi.reactome.service.placeholder.EventService;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

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
    private EventService eventService;

    @Autowired
    private Session session;

    @Before
    public void setUp(){
        session.purgeDatabase();
        eventService.getOrCreate(new Pathway(1L, "React_1.1", "Pathway"));
        eventService.getOrCreate(new Reaction(2L, "React_2.1", "Reaction"));
    }

    @Test
    public void testGetOrCreateEvent() {
        eventService.getOrCreate(new Pathway(3L, "React_3.1", "Pathway"));
        eventService.getOrCreate(new Pathway(3L, "React_3.1", "Pathway"));
        eventService.getOrCreate(new Polymerisation(4L, "React_4.1", "Polymerisation"));
        Collection<Event> events = (Collection<Event>) eventService.findAll();
        assertEquals(4, events.size());
    }

    @Test
    public void testFindAll() {
        Collection<Event> events = (Collection<Event>) eventService.findAll();
        assertEquals(2, events.size());
    }

    @Test
    public void findByDbId() {
        Event pathway = eventService.findByDbId(1L);
        Event reaction = eventService.findByDbId(2L);
        assertEquals(Long.valueOf(1L), pathway.getDbId());
        assertEquals(Long.valueOf(2L), reaction.getDbId());
        assertEquals("React_1.1", pathway.getStId());
        assertEquals("React_2.1", reaction.getStId());
        assertEquals("Pathway", pathway.getName());
        assertEquals("Reaction", reaction.getName());
    }

    @Test
    public void findByStId() {
        Event pathway = eventService.findByDbId(1L);
        Event reaction = eventService.findByDbId(2L);
        assertEquals(Long.valueOf(1L), pathway.getDbId());
        assertEquals(Long.valueOf(2L), reaction.getDbId());
        assertEquals("React_1.1", pathway.getStId());
        assertEquals("React_2.1", reaction.getStId());
        assertEquals("Pathway", pathway.getName());
        assertEquals("Reaction", reaction.getName());
    }
}
