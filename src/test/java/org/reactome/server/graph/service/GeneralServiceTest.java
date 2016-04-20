package org.reactome.server.graph.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.MyConfiguration;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.reactome.server.graph.util.JunitHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 25.01.16.
 */
@ContextConfiguration(classes = { MyConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class GeneralServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    private static final Long dbId = 5205685L;
    private static final String stId = "R-HSA-5205685";

    @Autowired
    private GeneralService generalService;

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
        generalService.clearCache();
        DatabaseObjectFactory.clearCache();
    }

//    @Test
//    public void testFindByDbId () {
////        return generalNeo4jOperationsRepository.findByDbId(dbId, direction);
//    }
//
//    @Test
//    public void testFindByStableIdentifier () {
////        return generalNeo4jOperationsRepository.findByStableIdentifier(stableIdentifier, direction);
//    }
//
//    @Test
//    public void testFindByDbId () {
////        return generalNeo4jOperationsRepository.findByDbId(dbId, direction, relationships);
//    }
//
//    @Test
//    public void testFindByStableIdentifier () {
//        return generalNeo4jOperationsRepository.findByStableIdentifier(stableIdentifier, direction, relationships);
//    }
//
//    @Test
//    public void testFindByDbIds () {
//        return generalNeo4jOperationsRepository.findByDbIds(dbIds, direction);
//    }
//
//    @Test
//    public void testFindByStableIdentifiers() {
//        return generalNeo4jOperationsRepository.findByStableIdentifiers(stableIdentifiers, direction);
//    }
//
//    @Test
//    public void testFindByDbIds() {
//        return generalNeo4jOperationsRepository.findByDbIds(dbIds, direction, relationships);
//    }
//
//    @Test
//    public void testFindByStableIdentifiers() {
//        return generalNeo4jOperationsRepository.findByStableIdentifiers(stableIdentifiers, direction, relationships);
//    }
//
    /**
     * This method can hardly be tested. GkInstance does not provide any comparison since pagination is not possible.
     */
    @Test
    public void testGetObjectsByClassName() throws ClassNotFoundException {

        logger.info("Started testing genericService.getObjectsByClassName");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = generalService.findObjectsByClassName("Pathway", 1, 25);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(25, pathways.size());
        logger.info("Finished");
    }

    @Test
    public void testFindByProperty() throws InvocationTargetException, IllegalAccessException {
        logger.info("Started testing genericService.findByProperty");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = generalService.findByProperty(DatabaseObject.class, "dbId", dbId, 1);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }
//
//    @Test
//    public void testFindByProperty() {
//        return generalNeo4jOperationsRepository.findByProperty(clazz, property, values, depth);
//    }

    /**
     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
     * possibly change when content is added to reactome. This method provides all different labels used in the
     * graph paired with the numbers of entries belonging to these labels.
     */
    @Test
    public void testGetSchemaClassCounts() {

        logger.info("Started testing databaseObjectService.getLabelsCount");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SchemaClassCount> schemaClassCounts = generalService.getSchemaClassCounts();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(60, schemaClassCounts.size());
        logger.info("Finished");
    }

    @Test
    public void testGetAllSpecies() {

        logger.info("Started testing genericService.getSpecies");
        long start, time;
        start = System.currentTimeMillis();
        Set<Species> observedSpecies = new HashSet<>(generalService.getAllSpecies());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        Set<Species> expectedSpecies = new HashSet<>(DatabaseObjectFactory.getSpecies());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertEquals(expectedSpecies, observedSpecies);
        logger.info("Finished");
    }

    @Test
    public void testGetAllChemicals() {

        logger.info("Started testing genericService.getSpecies");
        long start, time;
        start = System.currentTimeMillis();
        Set<ReferenceEntity> observedChemicals = new HashSet<>(generalService.getAllChemicals());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1662, observedChemicals.size());
        logger.info("Finished");
    }

    @Test
    public void testGetTopLevelPathways() {
        logger.info("Started testing genericService.getTopLevelPathways");
        long start, time;
        start = System.currentTimeMillis();
        Collection<TopLevelPathway> observedTlps = generalService.getTopLevelPathways();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        Collection<DatabaseObject> expectedTlps = DatabaseObjectFactory.loadFrontPageItems();
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertEquals(expectedTlps.size(), observedTlps.size());
        logger.info("Finished");
    }

    @Test
    public void getTopLevelPathwaysWithId() {
        logger.info("Started testing genericService.getTopLevelPathwaysWithId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<TopLevelPathway> observedTlps = generalService.getTopLevelPathways(48887L);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(24,observedTlps.size());
        logger.info("Finished");
    }

    @Test
    public void getTopLevelPathwaysWithName() {
        logger.info("Started testing genericService.getTopLevelPathwaysWithName");
        long start, time;
        start = System.currentTimeMillis();
        Collection<TopLevelPathway> observedTlps = generalService.getTopLevelPathways("Homo sapiens");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(24,observedTlps.size());
        logger.info("Finished");
    }

//    @Test
//    public void saveAndDelete() {
//
//        Pathway pathway = new Pathway();
//        pathway.setDbId(111111111111L);
//        pathway.setStableIdentifier("R-HSA-111111111111");
//        pathway.setDisplayName("TestPathway");
//
//        long count = generalService.countEntries(Pathway.class);
//        generalService.save(pathway);
//        long countAfterSave = generalService.countEntries(Pathway.class);
//        assertEquals(count + 1, countAfterSave);
////        generalService.delete(generalService.find
//        long countAfterDelete = generalService.countEntries(Pathway.class);
//        assertEquals(count, countAfterDelete);
//    }
//
//    @Test
//    public void saveAndDeleteWithRelation() {
//
//        Pathway pathway = new Pathway();
//        pathway.setDbId(111111111111L);
//        pathway.setStableIdentifier("R-HSA-111111111111");
//        pathway.setDisplayName("TestPathway");
//
//        Pathway pathway2 = new Pathway();
//        pathway2.setDbId(111111111112L);
//        pathway2.setStableIdentifier("R-HSA-111111111112");
//        pathway2.setDisplayName("TestPathway2");
//
//        Pathway pathway3 = new Pathway();
//        pathway3.setDbId(111111111113L);
//        pathway3.setStableIdentifier("R-HSA-111111111113");
//        pathway3.setDisplayName("TestPathway3");
//
//        List<Event> hasEvent = new ArrayList<>();
//        hasEvent.add(pathway2);
//        hasEvent.add(pathway3);
//        pathway.setHasEvent(hasEvent);
//
//        long count = genericService.countEntries(Pathway.class);
//        service.save(pathway,1);
//        long countAfterSave = genericService.countEntries(Pathway.class);
//        assertEquals(count + 3, countAfterSave);
//        service.delete(genericService.findByDbId(Pathway.class,111111111111L,0).getId());
//        service.delete(genericService.findByDbId(Pathway.class,111111111112L,0).getId());
//        service.delete(genericService.findByDbId(Pathway.class,111111111113L,0).getId());
//        long countAfterDelete = genericService.countEntries(Pathway.class);
//        assertEquals(count, countAfterDelete);
//    }


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

//    @Test
//    public void testGetComponentsOf() {
//        return generalRepository.getComponentsOf(stId);
//    }
//
//    @Test
//    public void testGetComponentsOf() {
//        return generalRepository.getComponentsOf(dbId);
//    }

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
//        DatabaseObject databaseObject = new EntityWithAccessionedSequence();
//        databaseObject.setStableIdentifier("R-ALL-113592");
//        databaseObject.setDisplayName("ATP");
//
////        DatabaseObject databaseObjectObserved = genericService.findByStableId();
//        long start, time;
//        start = System.currentTimeMillis();
//
//
//        Set<PathwayBrowserNode> nodes = genericService.getLocationsInPathwayBrowserHierarchy(databaseObject);
//        nodes = PathwayBrowserLocationsUtils.removeOrphans(nodes);
//        for (PathwayBrowserNode node : nodes) {
//            if (node.getSpecies().equals("Homo sapiens")) {
//                System.out.println();
//            }
//        }
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
//        Set<PathwayBrowserNode> nodes = genericService.getLocationsInPathwayBrowserHierarchy(databaseObject);
//        nodes = PathwayBrowserLocationsUtils.removeOrphans(nodes);
//        for (PathwayBrowserNode node : nodes) {
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

//    @ComponentOf
//    public void testGetLocationsHierarchy () {
//        logger.info("Started testing genericService.getEventHierarchy");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject eventHierarchy = genericService.getLocationsHierarchy("R-HSA-445133");
//        genericService.getLocationsHierarchy();
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
////        assertEquals(dbId,eventHierarchy.getDbId());
//        logger.info("Finished");
//    }
//
//    @ComponentOf
//    public void testGetReferrer () {
//        logger.info("Started testing genericService.getEventHierarchy");
//        long start, time;
//        start = System.currentTimeMillis();
//        Collection<DatabaseObject> eventHierarchy = genericService.getReferrals(445133L,"hasMember");
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(dbId,eventHierarchy.getDbId());
//        logger.info("Finished");
//    }


}
