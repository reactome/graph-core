package uk.ac.ebi.reactome.service.placeholder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.reactome.Application;
import uk.ac.ebi.reactome.data.ReactomeParser;
import uk.ac.ebi.reactome.domain.model.Pathway;
import uk.ac.ebi.reactome.service.DatabaseObjectService;
import uk.ac.ebi.reactome.service.GenericService;

import java.util.Iterator;

import static org.junit.Assert.*;


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
    private DatabaseObjectService databaseObjectService;

    @Autowired
    private ReactomeParser reactomeParser;

    @Autowired
    private GenericService genericService;

    @Before
    public void setUp() {
        reactomeParser.load("R-HSA-169911");
    }

    @After
    public void tearDown() {
        genericService.cleanDatabase();
    }

    @Test
    public void findOne() {

    }

    @Test
    public void findByDbId() {
        Pathway pathway = (Pathway) databaseObjectService.findByDbId(169911L);
        assertEquals(Long.valueOf(169911L), pathway.getDbId());
        assertEquals("R-HSA-169911", pathway.getStId());
        assertEquals("Regulation of Apoptosis", pathway.getName());
        assertEquals(2, pathway.getEvents().size());
        Iterator iterator = pathway.getEvents().iterator();
        Pathway a = (Pathway) iterator.next();
        Pathway b =(Pathway) iterator.next();
        assertTrue(Long.valueOf(211728L).equals(a.getDbId()) || Long.valueOf(211733L).equals(a.getDbId()));
        assertTrue(Long.valueOf(211728L).equals(b.getDbId()) || Long.valueOf(211733L).equals(b.getDbId()));
        assertNull(a.getEvents());
        assertNull(b.getEvents());

    }

    @Test
    public void findByStId() {
        Pathway pathway = (Pathway) databaseObjectService.findByStId("R-HSA-169911");
        assertEquals(Long.valueOf(169911L), pathway.getDbId());
        assertEquals("R-HSA-169911", pathway.getStId());
        assertEquals("Regulation of Apoptosis", pathway.getName());
        assertEquals(2, pathway.getEvents().size());
        Iterator iterator = pathway.getEvents().iterator();
        Pathway a = (Pathway) iterator.next();
        Pathway b =(Pathway) iterator.next();
        assertTrue(Long.valueOf(211728L).equals(a.getDbId()) || Long.valueOf(211733L).equals(a.getDbId()));
        assertTrue(Long.valueOf(211728L).equals(b.getDbId()) || Long.valueOf(211733L).equals(b.getDbId()));
        assertNull(a.getEvents());
        assertNull(b.getEvents());;
    }
}