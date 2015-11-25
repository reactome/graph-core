//package uk.ac.ebi.reactome.service;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.neo4j.kernel.impl.util.collection.ArrayCollection;
//import org.neo4j.ogm.session.Session;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import uk.ac.ebi.reactome.Application;
//import uk.ac.ebi.reactome.data.ReactomeParser;
//import uk.ac.ebi.reactome.domain.model.*;
//import uk.ac.ebi.reactome.domain.result.SomeResult;
//import uk.ac.ebi.reactome.service.placeholder.PhysicalEntityService;
//import uk.ac.ebi.reactome.service.placeholder.ReferenceGeneProductService;
//import uk.ac.ebi.reactome.service.placeholder.SomeResultService;
//
//import java.nio.file.Path;
//import java.util.*;
//
///**
// * Created by:
// *
// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
// * @since 16.11.15.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//public class GenericServiceTest {
//
//    @Autowired
//    private PhysicalEntityService physicalEntityService;
//
//    @Autowired
//    private SomeResultService someResultService;
//
//
//    @Autowired
//    private GenericService genericService;
//
//    @Autowired
//    private DatabaseObjectService databaseObjectService;
//
//    @Autowired
//    private ReferenceGeneProductService referenceGeneProductService;
//
//    @Autowired
//    private Service<Complex> complexService;
//
//    @Autowired
//    private Session session;
//
//    @Autowired
//    ReactomeParser reactomeParser;
//
//    @Before
//    public void setUp() {
//        session.purgeDatabase();
////        reactomeParser.load("R-HSA-111465.1");
//        reactomeParser.load("R-HSA-111465.1");
//        databaseObjectService.getOrCreate(new Pathway(1L, "React_1.1", "Pathway1"));
////        databaseObjectService.getOrCreate(new Pathway(2L, "React_2.1", "Pathway2"));
////        databaseObjectService.getOrCreate(new Pathway(3L, "React_3.1", "Pathway3"));
////        databaseObjectService.getOrCreate(new Pathway(4L, "React_4.1", "Pathway4"));
////        databaseObjectService.getOrCreate(new Pathway(5L, "React_5.1", "Pathway5"));
////        databaseObjectService.getOrCreate(new Pathway(6L, "React_6.1", "Pathway6"));
////        databaseObjectService.getOrCreate(new Pathway(7L, "React_7.1", "Pathway7"));
//
////        databaseObjectService.getOrCreate(new Pathway(8L, "React_8.1", "Pathway8"));
////        databaseObjectService.getOrCreate(new Pathway(9L, "React_9.1", "Pathway9"));
////
////        databaseObjectService.getOrCreate(new Reaction(10L, "React_10.1", "Reaction10"));
//
//
////        databaseObjectService.createEventRelationship(1L, 2L);
////        databaseObjectService.createEventRelationship(2L, 3L);
////        databaseObjectService.createEventRelationship(3L, 4L);
////        databaseObjectService.createEventRelationship(4L, 5L);
////        databaseObjectService.createEventRelationship(5L, 6L);
////        databaseObjectService.createEventRelationship(6L, 7L);
////
//////        databaseObjectService.createEventRelationship(1L, 8L);
////        databaseObjectService.createEventRelationship(1L, 9L);
////        databaseObjectService.createEventRelationship(1L, 10L);
//
////        databaseObjectService.getOrCreate(new Complex(11L, "React_11.1", "Complex11"));
////        databaseObjectService.getOrCreate(new EntityWithAccessionedSequence(12L, "React_12.1", "EWAS12"));
////
////        databaseObjectService.createInputRelationship(10L, 11L);
////        databaseObjectService.createInputRelationship(10L, 12L);
//
//
//
//
//
//        databaseObjectService.getOrCreate(new Complex(11L, "React_11.1", "Complex11"));
//        databaseObjectService.getOrCreate(new Complex(12L, "React_12.1", "Complex12"));
//        databaseObjectService.getOrCreate(new Complex(13L, "React_13.1", "Complex13"));
//        databaseObjectService.getOrCreate(new Complex(14L, "React_14.1", "Complex14"));
//        databaseObjectService.getOrCreate(new Complex(15L, "React_15.1", "Complex15"));
//        databaseObjectService.getOrCreate(new Complex(16L, "React_16.1", "Complex16"));
//        databaseObjectService.getOrCreate(new Complex(17L, "React_17.1", "Complex17"));
////
//        databaseObjectService.createComponentRelationship(11L, 12L);
//        databaseObjectService.createComponentRelationship(12L, 13L);
//        databaseObjectService.createComponentRelationship(13L, 14L);
//        databaseObjectService.createComponentRelationship(14L, 15L);
//        databaseObjectService.createComponentRelationship(15L, 16L);
//        databaseObjectService.createComponentRelationship(16L, 17L);
//
//
//
//
////        databaseObjectService.getOrCreate(new Reaction(2L, "React_2.1", "Reaction"));
////        databaseObjectService.getOrCreate(new EntityWithAccessionedSequence(3L, "React_3.1", "EWAS"));
////        databaseObjectService.getOrCreate(new Complex(4L, "React_4.1", "Complex"));
////        databaseObjectService.getOrCreate(new OpenSet(5L, "React_5.1", "Set"));
//    }
//
//    @After
//    public void tearDown() {
//        session.purgeDatabase();
//    }
//
//    @Test
//    public void test() {
////        DatabaseObject dbObject1 = genericService.findStIdWithSession("R-HSA-111465", 5);
////        DatabaseObject dbObject2 = genericService.findStIdWithSession("R-HSA-111465", 0);
////        DatabaseObject dbObject3 = genericService.findStIdWithSession("R-HSA-111465", 1);
////        DatabaseObject dbObject4 = genericService.findStIdWithSession("R-HSA-111465", 2);
////        DatabaseObject dbObject5 = genericService.findStIdWithSession("R-HSA-111465", 3);
////
////
////        DatabaseObject dbObject6 = databaseObjectService.findByStId("R-HSA-111465");
////        DatabaseObject dbObject7 = databaseObjectService.find(dbObject6.getId(), 0);
////
////
////        DatabaseObject dbObject9 = databaseObjectService.findByStId("R-HSA-111465");
////
////        DatabaseObject dbObject8 = databaseObjectService.findByStId("R-HSA-202947");
//////
////        DatabaseObject dbObject10 = databaseObjectService.findByStId("R-HSA-2976013");
////        DatabaseObject complex = databaseObjectService.findByDbId(114318L);
//////        Long id = complex.getId();
//////
//////        Complex complex1 = complexService.find(id, 0);
////        DatabaseObject phys = databaseObjectService.findByStId("R-HSA-141639");
//
////        Iterable<DatabaseObject> databaseObjects = databaseObjectService.findAll()
//
//        Collection<Long> list = new HashSet<Long>() {
//        };
//        list.add(61580L);
//        list.add(50653L);
//        Iterable<ReferenceGeneProduct> ref = referenceGeneProductService.getProteins(list);
//        try {
//            referenceGeneProductService.doNothing();
//        } catch (Exception e) {
//        }
//
//        SomeResult r = someResultService.getSomeResult(111465L);
//
//        Iterable<DatabaseObject> databaseObjects = session.loadAll(DatabaseObject.class, 0);
//
////        databaseObjectService.getOrCreate(new Pathway(2L, "React_2.1", "Pathway2"));
////        databaseObjectService.getOrCreate(new Pathway(3L, "React_3.1", "Pathway3"));
////        databaseObjectService.getOrCreate(new Pathway(4L, "React_4.1", "Pathway4"));
////        databaseObjectService.getOrCreate(new Pathway(5L, "React_5.1", "Pathway5"));
////        databaseObjectService.getOrCreate(new Pathway(6L, "React_6.1", "Pathway6"));
////        databaseObjectService.getOrCreate(new Pathway(7L, "React_7.1", "Pathway7"));
////        databaseObjectService.createEventRelationship(1L, 2L);
////        databaseObjectService.createEventRelationship(2L, 3L);
////        databaseObjectService.createEventRelationship(3L, 4L);
////        databaseObjectService.createEventRelationship(4L, 5L);
////        databaseObjectService.createEventRelationship(5L, 6L);
////        databaseObjectService.createEventRelationship(6L, 7L);
//
////        DatabaseObject dbO = session.load(DatabaseObject.class, databaseObjects.iterator().next().getId(),3);
//
//
//        Pathway pw = genericService.loadByProperty(Pathway.class, "dbId", 1L);
//
//
//
////        DatabaseObject dbo = genericService.findByDbId(1L, 3);
//
//        DatabaseObject db = databaseObjectService.findByDbId(11L);
//
//
//        Long i = db.getId();
//        PhysicalEntity phys = physicalEntityService.findOne(i, 3);
//
//
//        PhysicalEntity phys2 = physicalEntityService.findOne(i, 1);
//
////        ////// test strange behaviour of the depth parameter
////
////        // think about removing relationship entities and find a different method of gettign stichiometry
////
////
////        DatabaseObject dbObject1 = databaseObjectService.findByDbId(1L);
////        Pathway pathway = session.load(Pathway.class, dbObject1.getId(), 0);
////        Pathway pathway2 = session.load(Pathway.class, dbObject1.getId(), 4);
////
////        Pathway pathway5 = session.load(Pathway.class, dbObject1.getId(), 2);
////
////        DatabaseObject pathway3 = genericService.findByDbIdWithSession(1L, 0);
////        DatabaseObject pathway4 = genericService.findByDbIdWithSession(1L, 4);
////
////        DatabaseObject complex = databaseObjectService.findByDbId(11L);
//////
////        Long id = complex.getId();
////        Complex complex1 = complexService.find(id, 2);
////
////        Complex complex2 = session.load(Complex.class,id,0);
////
////        DatabaseObject dbObject2 = genericService.findByDbIdWithSession(11L, 0);
//
////        Person p = template.loadByProperty(Person.class, "login", loginValue);
////        p = personRepo.findOne(p.getId(), 2);
//
//        System.out.println("");
//
//
////
////        Pathway p1 = new Pathway(123L, "R_123", "pp");
////        Pathway p2 = new Pathway(124L, "R_124", "pp");
////        Pathway p3 = new Pathway(125L, "R_125", "pp");
////        Pathway p4 = new Pathway(126L, "R_126", "pp");
////        Set<Event> event = new HashSet<>();
////        event.add(p2);
////        Set<Event> event2 = new HashSet<>();
////        event2.add(p3);
////        Set<Event> event3 = new HashSet<>();
////        event3.add(p4);
////
////
////        p1.setEvents(event);
////        p2.setEvents(event2);
////        p3.setEvents(event3);
////
////        session.save(p1, 3);
////
////        Pathway pp1 = (Pathway) genericService.findByDbIdWithSession(123L, 1);
////
////        Pathway pp2 = session.load(Pathway.class, pp1.getId(), 1);
////
////        DatabaseObject pp3 = databaseObjectService.findByDbId(123L);
////
////        System.out.println("");
//    }
//}
