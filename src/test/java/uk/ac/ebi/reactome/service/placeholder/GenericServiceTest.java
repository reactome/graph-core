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
import uk.ac.ebi.reactome.service.GenericService;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 23.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class GenericServiceTest {

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
    public void ByProperty() {

//        get by dbId
//        DatabaseObject databaseObject = genericService.loadByProperty(DatabaseObject.class, "dbId", 211728L);
//        assertEquals(Long.valueOf(211728L), databaseObject.getDbId());
//        assertEquals("R-HSA-211728", databaseObject.getStId());
//        assertEquals("Regulation of PAK-2p34 activity by PS-GAP/RHG10", databaseObject.getName());
//        assertEquals(2, ((Pathway)databaseObject).getEvents().size());
//
////        get by stId
//        EntityWithAccessionedSequence ewas = genericService.loadByProperty(EntityWithAccessionedSequence.class, "stId", "R-HSA-939213");
//        assertEquals(Long.valueOf(939213L), ewas.getDbId());
//        assertEquals("R-HSA-939213", ewas.getStId());
//        assertEquals("UBB(77-152) [cytosol]", ewas.getName());
//
////        get by name
//        ReactionLikeEvent reactionLikeEvent = genericService.loadByProperty(ReactionLikeEvent.class, "name", "Ubiquitination of PAK-2p34");
//        assertEquals(Long.valueOf(211734L), reactionLikeEvent.getDbId());
//        assertEquals("R-HSA-211734", reactionLikeEvent.getStId());
//        assertEquals("Ubiquitination of PAK-2p34", reactionLikeEvent.getName());
//        assertEquals(2, (reactionLikeEvent.getInput().size()));
//        assertEquals(1, (reactionLikeEvent.getOutput().size()));
    }

//    @Test
//    public void findById() {
//        Pathway pathway = genericService.findByDbId(Pathway.class, 169911L, 0);
//        assertEquals(Long.valueOf(169911L), pathway.getDbId());
//        assertEquals("R-HSA-169911", pathway.getStId());
//        assertEquals("Regulation of Apoptosis", pathway.getName());
//        assertNull(pathway.getEvents());
//
//        pathway = genericService.findByDbId(Pathway.class, 169911L, 1);
//        assertEquals(2, pathway.getEvents().size());
//        Iterator iterator = pathway.getEvents().iterator();
//        Pathway a = (Pathway) iterator.next();
//        Pathway b =(Pathway) iterator.next();
//        assertTrue(Long.valueOf(211728L).equals(a.getDbId()) || Long.valueOf(211733L).equals(a.getDbId()));
//        assertTrue(Long.valueOf(211728L).equals(b.getDbId()) || Long.valueOf(211733L).equals(b.getDbId()));
//        assertNull(a.getEvents());
//        assertNull(b.getEvents());
//
//        pathway = genericService.findByDbId(Pathway.class, 169911L, 2);
//        iterator = pathway.getEvents().iterator();
//        a = (Pathway) iterator.next();
////        this works because both pathways of 169911L contain 2 Reactions
//        assertEquals(2, a.getEvents().size());
//
////        behaviour of loading by depth is buggy when working with relationship entities
////        often too many instances are loaded
////        never to few
//        Complex complex = genericService.findByDbId(Complex.class, 211701L, 0);
//        assertEquals(Long.valueOf(211701L), complex.getDbId());
//        assertEquals("R-HSA-211701", complex.getStId());
//        assertEquals("PAK-2p34:RHG10 complex [cytosol]", complex.getName());
//        assertEquals(2, (complex.getComponents().size()));
//    }
//
//    @Test
//    public void findByStId() {
//        Pathway pathway = genericService.findByStId(Pathway.class, "R-HSA-169911", 0);
//        assertEquals(Long.valueOf(169911L), pathway.getDbId());
//        assertEquals("R-HSA-169911", pathway.getStId());
//        assertEquals("Regulation of Apoptosis", pathway.getName());
//        assertNull(pathway.getEvents());
//
//        pathway = genericService.findByStId(Pathway.class, "R-HSA-169911", 1);
//        assertEquals(2, pathway.getEvents().size());
//        Iterator iterator = pathway.getEvents().iterator();
//        Pathway a = (Pathway) iterator.next();
//        Pathway b =(Pathway) iterator.next();
//        assertTrue(Long.valueOf(211728L).equals(a.getDbId()) || Long.valueOf(211733L).equals(a.getDbId()));
//        assertTrue(Long.valueOf(211728L).equals(b.getDbId()) || Long.valueOf(211733L).equals(b.getDbId()));
//        assertNull(a.getEvents());
//        assertNull(b.getEvents());
//
//        pathway = genericService.findByStId(Pathway.class, "R-HSA-169911", 2);
//        iterator = pathway.getEvents().iterator();
//        a = (Pathway) iterator.next();
////        this works because both pathways of 169911L contain 2 Reactions
//        assertEquals(2, a.getEvents().size());
//    }
//
//    @Test
//    public void countEntries() {
//        assertEquals(Long.valueOf(33L), genericService.countEntries(DatabaseObject.class));
//    }
//
//    @Test
//    public void cleanDatabase() {
//        genericService.cleanDatabase();
//        assertEquals(Long.valueOf(0L), genericService.countEntries(DatabaseObject.class));
//    }
}