//package org.reactome.server.tools.service;
//
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.ComponentOf;
//import org.junit.runner.RunWith;
//import org.reactome.server.tools.domain.model.DatabaseObject;
//import org.reactome.server.tools.domain.model.EntityWithAccessionedSequence;
//import org.reactome.server.tools.domain.model.Pathway;
//import org.reactome.server.tools.domain.model.Species;
//import org.reactome.server.tools.service.helper.PBNode;
//import org.reactome.server.tools.service.helper.RelationshipDirection;
//import org.reactome.server.tools.service.util.PathwayBrowserLocationsUtils;
//import org.reactome.server.tools.util.DatabaseObjectFactory;
//import org.reactome.server.tools.util.JunitHelper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.reactome.server.tools.config.MyConfiguration;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.Assert.*;
//import static org.junit.Assume.assumeTrue;
//
///**
// * Created by:
// *
// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
// * @since 25.01.16.
// */
//@ContextConfiguration(classes = { MyConfiguration.class })
//@RunWith(SpringJUnit4ClassRunner.class)
//public class GenericServiceTest {
//
//    private static final Logger logger = LoggerFactory.getLogger("testLogger");
//
//    private static Boolean checkedOnce = false;
//    private static Boolean isFit = false;
//
//    private static final Long dbId = 5205685L;
//    private static final String stId = "R-HSA-5205685";
//
//    @Autowired
//    private GenericService genericService;
//
//    @BeforeClass
//    public static void setUpClass() {
//        logger.info(" --- !!! Running DatabaseObjectServiceTests !!! --- \n");
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//        logger.info("\n\n");
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        if (!checkedOnce) {
//            isFit = genericService.fitForService();
//            checkedOnce = true;
//        }
//        assumeTrue(isFit);
//        genericService.clearCache();
//        DatabaseObjectFactory.clearCache();
//    }
//
//
//    @ComponentOf
//    public void findById2() {
//
//        logger.info("Started testing genericService.findById");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = genericService.findById(stId, RelationshipDirection.OUTGOING);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(dbId, databaseObjectObserved.getDbId());
//        logger.info("Finished");
//    }
//
//
//
//    @ComponentOf
//    public void findById() {
//
//        Long id = genericService.findByDbId(DatabaseObject.class, dbId, 0).getId();
//        logger.info("Started testing genericService.findById");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = genericService.findById(DatabaseObject.class, id, 0);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(dbId, databaseObjectObserved.getDbId());
//        logger.info("Finished");
//    }
//
//    @ComponentOf
//    public void testFindByProperty() throws InvocationTargetException, IllegalAccessException {
//
//        logger.info("Started testing genericService.findByProperty");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = genericService.findByProperty(DatabaseObject.class, "dbId", dbId, 1);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
//        logger.info("Finished");
//    }
//
//    @ComponentOf
//    public void testFindByDbId() throws InvocationTargetException, IllegalAccessException {
//
//        logger.info("Started testing genericService.findByDbId");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = genericService.findByDbId(DatabaseObject.class, dbId, 1);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
//        logger.info("Finished");
//    }
//
//    @ComponentOf
//    public void testFindByStableIdentifier() throws InvocationTargetException, IllegalAccessException {
//
//        logger.info("Started testing genericService.findByStableIdentifier");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = genericService.findByStableIdentifier(DatabaseObject.class, stId, 1);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(stId);
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
//        logger.info("Finished");
//    }
//
//    /**
//     * This method can hardly be tested. GkInstance does not provide any comparison since pagination is not possible.
//     * @throws ClassNotFoundException
//     */
//    @ComponentOf
//    public void testGetObjectsByClassName() throws ClassNotFoundException {
//
//        logger.info("Started testing genericService.getObjectsByClassName");
//        long start, time;
//        start = System.currentTimeMillis();
//        Collection<Pathway> pathways = genericService.getObjectsByClassName(Pathway.class.getSimpleName(), 1, 25);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(25, pathways.size());
//        logger.info("Finished");
//    }
//
//    @ComponentOf
//    public void testFindByPropertyWithRelations() throws ClassNotFoundException {
//
//        logger.info("Started testing genericService.getObjectsByClassName");
//        long start, time;
//        start = System.currentTimeMillis();
//        Pathway pathway = (Pathway) genericService.findByPropertyWithRelations("dbId",dbId,"hasEvent");
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertNotNull(pathway.getHasEvent());
//        logger.info("Finished");
//    }
//
//    @ComponentOf
//    public void testFindByPropertyWithoutRelations() throws ClassNotFoundException {
//
//        logger.info("Started testing genericService.getObjectsByClassName");
//        long start, time;
//        start = System.currentTimeMillis();
//        Pathway pathway = (Pathway) genericService.findByPropertyWithoutRelations("dbId",dbId,"authored","created","edited","modified","revised","reviewed");
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertNull(pathway.getAuthored());
//        assertNull(pathway.getCreated());
//        assertNull(pathway.getEdited());
//        assertNull(pathway.getModified());
//        assertNull(pathway.getRevised());
//        assertNull(pathway.getReviewed());
//        logger.info("Finished");
//    }
//
//    @ComponentOf
//    public void testGetTopLevelPathways() {
//
//        logger.info("Started testing genericService.getTopLevelPathways");
//        long start, time;
//        start = System.currentTimeMillis();
//        Set<Pathway> observedTlps = new HashSet<>(genericService.getTopLevelPathways());
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        start = System.currentTimeMillis();
//        Set<DatabaseObject> expectedTlps = new HashSet<>(DatabaseObjectFactory.loadFrontPageItems());
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        assertEquals(expectedTlps, observedTlps);
//        logger.info("Finished");
//    }
//
//    @ComponentOf
//    public void testGetTopLevelPathwaysWithId() {
//
//        logger.info("Started testing genericService.getTopLevelPathwaysWithId");
//        long start, time;
//        start = System.currentTimeMillis();
//        Collection<Pathway> observedTlps = genericService.getTopLevelPathways(48887L);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(24,observedTlps.size());
//        logger.info("Finished");
//    }
//
//    @ComponentOf
//    public void testGetTopLevelPathwaysWithName() {
//
//        logger.info("Started testing genericService.getTopLevelPathwaysWithId");
//        long start, time;
//        start = System.currentTimeMillis();
//        Collection<Pathway> observedTlps = genericService.getTopLevelPathways("Homo sapiens");
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(24,observedTlps.size());
//        logger.info("Finished");
//    }
//
//    @ComponentOf
//    public void testGetSpecies() {
//
//        logger.info("Started testing genericService.getSpecies");
//        long start, time;
//        start = System.currentTimeMillis();
//        Set<Species> observedSpecies = new HashSet<>(genericService.getSpecies());
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        start = System.currentTimeMillis();
//        Set<Species> expectedSpecies = new HashSet<>(DatabaseObjectFactory.getSpecies());
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        assertEquals(expectedSpecies, observedSpecies);
//        logger.info("Finished");
//    }
//
//    /**
//     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
//     * change when content is added to reactome.
//     */
//    @ComponentOf
//    public void testCountEntries() {
//
//        logger.info("Started testing genericService.countEntries");
//        long start, time;
//        start = System.currentTimeMillis();
//        long count = genericService.countEntries(Pathway.class);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(21191L,count);
//        logger.info("Finished");
//    }
//
//
//    @ComponentOf
//    public void testGetEventHierarchy () {
//        logger.info("Started testing genericService.getEventHierarchy");
//
//        DatabaseObject databaseObject = new EntityWithAccessionedSequence();
//        databaseObject.setStableIdentifier("R-ALL-113592");
//        databaseObject.setDisplayName("ATP");
//
////        DatabaseObject databaseObjectObserved = genericService.findByStableId();
//        long start, time;
//        start = System.currentTimeMillis();
//
//
//        Set<PBNode> nodes = genericService.getLocationsInPathwayBrowserHierarchy(databaseObject);
//        nodes = PathwayBrowserLocationsUtils.removeOrphans(nodes);
//        for (PBNode node : nodes) {
//            if (node.getSpecies().equals("Homo sapiens")) {
//                System.out.println();
//            }
//        }
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
////        assertEquals(dbId,eventHierarchy.getDbId());
////        logger.info("Finished");
//    }
//
////    @ComponentOf
////    public void testGetLocationsHierarchy () {
////        logger.info("Started testing genericService.getEventHierarchy");
////        long start, time;
////        start = System.currentTimeMillis();
////        DatabaseObject eventHierarchy = genericService.getLocationsHierarchy("R-HSA-445133");
////        genericService.getLocationsHierarchy();
////        time = System.currentTimeMillis() - start;
////        logger.info("GraphDb execution time: " + time + "ms");
////
//////        assertEquals(dbId,eventHierarchy.getDbId());
////        logger.info("Finished");
////    }
////
////    @ComponentOf
////    public void testGetReferrer () {
////        logger.info("Started testing genericService.getEventHierarchy");
////        long start, time;
////        start = System.currentTimeMillis();
////        Collection<DatabaseObject> eventHierarchy = genericService.getReferrals(445133L,"hasMember");
////        time = System.currentTimeMillis() - start;
////        logger.info("GraphDb execution time: " + time + "ms");
////
////        assertEquals(dbId,eventHierarchy.getDbId());
////        logger.info("Finished");
////    }
//
//
//}
