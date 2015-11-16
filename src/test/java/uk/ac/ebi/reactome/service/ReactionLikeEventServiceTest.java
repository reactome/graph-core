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

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ReactionLikeEventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private PhysicalEntityService physicalEntityService;

//    @Autowired
//    private ReactionLikeEventService reactionLikeEventService;

    @Autowired
    private Session session;

    @Before
    public void setUp() {
        session.purgeDatabase();
        eventService.getOrCreate(new Reaction(1L, "React_1.1", "Reaction1"));
        eventService.getOrCreate(new BlackBoxEvent(2L, "React_2.1", "BlackBoxEvent"));
        physicalEntityService.getOrCreate(new EntityWithAccessionedSequence(3L,"React_3.1","EWAS1"));
        physicalEntityService.getOrCreate(new GenomeEncodedEntity(4L,"React_4.1","GEE"));
        physicalEntityService.getOrCreate(new Complex(5L,"React_5.1","Complex"));
        physicalEntityService.getOrCreate(new EntityWithAccessionedSequence(6L,"React_6.1","EWAS2"));
    }

    @Test
    public void testCreateInputRelationship() {
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
    }
}