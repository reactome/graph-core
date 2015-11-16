//package uk.ac.ebi.reactome.service;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.neo4j.ogm.session.Session;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import uk.ac.ebi.reactome.Application;
//import uk.ac.ebi.reactome.domain.model.BlackBoxEvent;
//import uk.ac.ebi.reactome.domain.model.Event;
//import uk.ac.ebi.reactome.domain.model.Pathway;
//import uk.ac.ebi.reactome.domain.model.Reaction;
//import uk.ac.ebi.reactome.service.placeholder.EventService;
//import uk.ac.ebi.reactome.service.placeholder.PathwayService;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.junit.Assert.*;
//
///**
// * Created by:
// *
// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
// * @since 10.11.15.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//public class PathwayServiceTest {
//
//    @Autowired
//    private EventService eventService;
//
//    @Autowired
//    private PathwayService pathwayService;
//
//    @Autowired
//    private Session session;
//
//    @Before
//    public void setUp() {
//        session.purgeDatabase();
//        eventService.getOrCreate(new Pathway(1L, "React_1.1", "Pathway1"));
//        eventService.getOrCreate(new Pathway(2L, "React_2.1", "Pathway2"));
//        eventService.getOrCreate(new Pathway(3L, "React_3.1", "Pathway3"));
//        eventService.getOrCreate(new Pathway(4L, "React_4.1", "Pathway4"));
//        eventService.getOrCreate(new Reaction(5L, "React_5.1", "Reaction1"));
//        eventService.getOrCreate(new Reaction(6L, "React_6.1", "Reaction2"));
//        eventService.getOrCreate(new Reaction(7L, "React_7.1", "Reaction3"));
//        eventService.getOrCreate(new BlackBoxEvent(8L, "React_8.1", "BlackBoxEvent"));
//    }
//
//    @Test
//    public void testCreateRelationship() {
//        pathwayService.createRelationship(1L, 2L);
//        pathwayService.createRelationship(2L, 3L);
//        pathwayService.createRelationship(3L, 5L);
//        pathwayService.createRelationship(3L, 6L);
//        pathwayService.createRelationship(3L, 7L);
//        pathwayService.createRelationship(1L, 4L);
//        pathwayService.createRelationship(1L, 8L);
//        Pathway pathway = (Pathway) eventService.findByDbId(1L);
//        assertEquals(3, pathway.getEvents().size());
//        Set<Long> dbIdSet = new HashSet<>();
//        dbIdSet.add(2L);
//        dbIdSet.add(4L);
//        dbIdSet.add(8L);
//        Set<String> stIdSet = new HashSet<>();
//        stIdSet.add("React_2.1");
//        stIdSet.add("React_4.1");
//        stIdSet.add("React_8.1");
//        for (Event event : pathway.getEvents()) {
//            assertTrue(dbIdSet.contains(event.getDbId()));
//            assertTrue(stIdSet.contains(event.getStId()));
//
//            // has to be null because search depth is by default 1
//            if (event.getClass().equals(Pathway.class)) {
//                assertNull(((Pathway) event).getEvents());
//            } else if (event.getClass().equals(BlackBoxEvent.class)) {
//                assertNull(((BlackBoxEvent) event).getInput());
//                assertNull(((BlackBoxEvent) event).getOutput());
//            }
//        }
//    }
//}