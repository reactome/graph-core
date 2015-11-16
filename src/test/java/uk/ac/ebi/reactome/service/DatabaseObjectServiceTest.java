package uk.ac.ebi.reactome.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.reactome.Application;
import uk.ac.ebi.reactome.domain.model.*;
import uk.ac.ebi.reactome.service.placeholder.EventService;
import uk.ac.ebi.reactome.service.placeholder.PhysicalEntityService;

import static org.junit.Assert.assertEquals;


/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DatabaseObjectServiceTest {

    @Autowired
    private DatabaseObjectServiceImpl databaseObjectServiceImpl;

    @Autowired
    private EventService eventService;

    @Autowired
    private PhysicalEntityService physicalEntityService;

    @Autowired
    private Session session;

    @Before
    public void setUp() {
        session.purgeDatabase();
        eventService.getOrCreate(new Pathway(1L, "React_1.1", "Pathway"));
        eventService.getOrCreate(new Reaction(2L, "React_2.1", "Reaction"));
        physicalEntityService.getOrCreate(new EntityWithAccessionedSequence(3L, "React_3.1", "EWAS"));
        physicalEntityService.getOrCreate(new Complex(4L, "React_4.1", "Complex"));
        physicalEntityService.getOrCreate(new OpenSet(5L, "React_5.1", "Set"));
    }

//    @Test
//    public void testFindAll() {
//        Collection<DatabaseObject> databaseObjects = (Collection<DatabaseObject>) databaseObjectServiceImpl.findAll();
//        assertEquals(5, databaseObjects.size());
//    }

    @Test
    public void findByDbId() {
        DatabaseObject reaction = databaseObjectServiceImpl.findByDbId(2L);
        DatabaseObject complex = databaseObjectServiceImpl.findByDbId(4L);
        assertEquals(Long.valueOf(2L), reaction.getDbId());
        assertEquals(Long.valueOf(4L), complex.getDbId());
        assertEquals("React_2.1", reaction.getStId());
        assertEquals("React_4.1", complex.getStId());
        assertEquals("Reaction", reaction.getName());
        assertEquals("Complex", complex.getName());
    }

    @Test
    public void findByStId() {
        DatabaseObject reaction = databaseObjectServiceImpl.findByStId("React_2.1");
        DatabaseObject complex = databaseObjectServiceImpl.findByStId("React_4.1");
        assertEquals(Long.valueOf(2L), reaction.getDbId());
        assertEquals(Long.valueOf(4L), complex.getDbId());
        assertEquals("React_2.1", reaction.getStId());
        assertEquals("React_4.1", complex.getStId());
        assertEquals("Reaction", reaction.getName());
        assertEquals("Complex", complex.getName());
    }
}