package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.custom.CustomQueryComplex;
import org.reactome.server.graph.custom.CustomQueryPhysicalEntity;
import org.reactome.server.graph.custom.CustomQueryResult;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.exception.CustomQueryException;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 */
@SpringBootTest
public class AdvancedServiceTest extends BaseTest {

    private static final Long dbId = 5205685L;
    private static final Long dbId2 = 199420L;
    private static final String stId = "R-HSA-5205685";
    private static final String stId2 = "R-HSA-199420";

    @Autowired
    private AdvancedDatabaseObjectService advancedDatabaseObjectService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + AdvancedServiceTest.class.getName() + "!!! --- \n");
    }

    // --------------------------------------- Enhanced Finder Methods -------------------------------------------------

    @Test
    public void findEnhancedPhysicalEntityByIdTest() {

        logger.info("Started testing advancedDatabaseObjectService.findEnhancedPhysicalEntityByIdTest");
        long start, time;
        start = System.currentTimeMillis();
        PhysicalEntity peObserved = advancedDatabaseObjectService.findEnhancedObjectById("R-HSA-60140");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertEquals("R-HSA-113454", peObserved.getPositivelyRegulates().get(0).getRegulatedEntity().get(0).getStId());
        logger.info("Finished");
    }

    @Test
    public void findEnhancedEventByIdTest() {

        logger.info("Started testing advancedDatabaseObjectService.findEnhancedPathwayByIdTest");
        long start, time;
        start = System.currentTimeMillis();
        ReactionLikeEvent rleObserved = advancedDatabaseObjectService.findEnhancedObjectById(2993780L);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse(rleObserved.getRegulatedBy().isEmpty(), rleObserved.getDisplayName() + " should have regulators");
        logger.info("Finished");
    }

    // --------------------------------------- Limited Finder Methods --------------------------------------------------

    @Test
    public void findLimitedObjectByIdTest() {

        logger.info("Started testing advancedDatabaseObjectService.findAllByProperty");
        long start, time;
        start = System.currentTimeMillis();
        Pathway databaseObjectObserved = advancedDatabaseObjectService.findById("R-HSA-5205685", 10);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(databaseObjectObserved.getHasEvent());
        logger.info("Finished");
    }


    // --------------------------------------- Generic Finder Methods --------------------------------------------------

    @Test
    public void findByPropertyTest() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing advancedDatabaseObjectService.findByProperty");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = advancedDatabaseObjectService.findByProperty(DatabaseObject.class, "stId", stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertThat(databaseObjectObserved).isEqualTo(databaseObjectExpected);

        logger.info("Finished");
    }

    @Test
    public void findAllByPropertyTest() {

        logger.info("Started testing advancedDatabaseObjectService.findAllByProperty");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DatabaseObject> databaseObjectObserved = advancedDatabaseObjectService.findAllByProperty(DatabaseObject.class, "stId", stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1, databaseObjectObserved.size());
        logger.info("Finished");
    }

    // ---------------------- Methods with RelationshipDirection and Relationships -------------------------------------

    @Test
    public void findByDbIdWithRelationshipDirectionTest() throws InvocationTargetException, IllegalAccessException {
        logger.info("Started testing advancedDatabaseObjectService.findByDbIdWithRelationshipDirectionTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = advancedDatabaseObjectService.findById(dbId, RelationshipDirection.UNDIRECTED);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertThat(databaseObjectObserved).isEqualTo(databaseObjectExpected);

        logger.info("Finished");
    }

    @Test
    public void findByStIdWithRelationshipDirectionTest() throws InvocationTargetException, IllegalAccessException {
        logger.info("Started testing advancedDatabaseObjectService.findByStIdWithRelationshipDirectionTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = advancedDatabaseObjectService.findById(stId, RelationshipDirection.UNDIRECTED);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertThat(databaseObjectObserved).isEqualTo(databaseObjectExpected);

        logger.info("Finished");
    }

    @Test
    public void findByDbIdWithRelationshipDirectionAndRelationshipsTest() {
        logger.info("Started testing advancedDatabaseObjectService.findByDbIdWithRelationshipDirectionAndRelationshipsTest");
        long start, time;
        try {
            start = System.currentTimeMillis();
            Pathway databaseObjectObserved = advancedDatabaseObjectService.findById(dbId, RelationshipDirection.OUTGOING, "hasEvent");
            time = System.currentTimeMillis() - start;
            logger.info("GraphDb execution time: " + time + "ms");

            assertEquals(8, databaseObjectObserved.getHasEvent().size());
            logger.info("Finished");
        }catch (ClassCastException aa) {
            aa.printStackTrace();
        }
    }

    @Test
    public void findByStIdWithRelationshipDirectionAndRelationshipsTest() {
        logger.info("Started testing advancedDatabaseObjectService.findByStIdWithRelationshipDirectionAndRelationshipsTest");
        long start, time;
        start = System.currentTimeMillis();
        Pathway databaseObjectObserved = advancedDatabaseObjectService.findById(stId, RelationshipDirection.OUTGOING, "hasEvent");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(8, databaseObjectObserved.getHasEvent().size());
        logger.info("Finished");
    }

    @Test
    public void findByDbIdsWithRelationshipDirectionTest() {
        logger.info("Started testing advancedDatabaseObjectService.findByDbIdsWithRelationshipDirectionTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DatabaseObject> databaseObjectObserved = advancedDatabaseObjectService.findByDbIds(Arrays.asList(dbId, dbId2), RelationshipDirection.OUTGOING);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(2, databaseObjectObserved.size());
        logger.info("Finished");
    }

    @Test
    public void findByStIdsWithRelationshipDirectionTest() {
        logger.info("Started testing advancedDatabaseObjectService.findByStIdsWithRelationshipDirectionTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DatabaseObject> databaseObjectObserved = advancedDatabaseObjectService.findByStIds(Arrays.asList(stId, stId2), RelationshipDirection.OUTGOING);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(2, databaseObjectObserved.size());
        logger.info("Finished");
    }

    @Test
    public void findByDbIdsWithRelationshipDirectionAndRelationshipsTest() {
        logger.info("Started testing advancedDatabaseObjectService.findByDbIdsWithRelationshipDirectionAndRelationshipsTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DatabaseObject> databaseObjectObserved = advancedDatabaseObjectService.findByDbIds(Arrays.asList(dbId, dbId2), RelationshipDirection.OUTGOING, "hasEvent", "referenceEntity");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(2, databaseObjectObserved.size());
        logger.info("Finished");
    }

    @Test
    public void findByStIdsWithRelationshipDirectionAndRelationshipsTest() {
        logger.info("Started testing advancedDatabaseObjectService.findByStIdsWithRelationshipDirectionAndRelationshipsTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DatabaseObject> databaseObjectObserved = advancedDatabaseObjectService.findByStIds(Arrays.asList(stId, stId2), RelationshipDirection.OUTGOING, "hasEvent", "referenceEntity");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(2, databaseObjectObserved.size());
        logger.info("Finished");
    }

    // ------------------------------- Methods with Custom Query -------------------------------------

    //@Test
    public void custom() {
        // WARNING: THIS QUERY ALTERS DATA IN THE GRAPH. UNCOMMENT WHEN NEEDED.
        String query = "" +
                "MATCH (rle:ReactionLikeEvent{dbId:$dbId}}) " +
                "SET rle.category=$category ";
        Map<String, Object> params = new HashMap<>();
        params.put("dbId", 6803411L);
        params.put("category", "ommited");
        advancedDatabaseObjectService.customQuery(query, params);

    }
    @Test
    public void customQueryWithCustomObjectTest() throws CustomQueryException {
        logger.info("Started testing advancedDatabaseObjectService.customQueryForObject");

        String query = "MATCH (p:Pathway{dbId:$dbId})-[:hasEvent]->(m) RETURN p.dbId as dbId, p.displayName as name, Collect(m.dbId) AS events, Collect(m.dbId) AS eventsPrimitiveArray, Collect(m.displayName) AS eventsArray ";
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("dbId", 1640170);
        CustomQueryResult customQueryResult = advancedDatabaseObjectService.getCustomQueryResult(CustomQueryResult.class, query, parametersMap);
        assertEquals(4, customQueryResult.getEvents().size());
        assertEquals(4, customQueryResult.getEventsArray().length);
        assertEquals(4, customQueryResult.getEventsPrimitiveArray().length);
    }

    @Test
    public void customQueryListOfCustomObjectTest() throws CustomQueryException {
        logger.info("Started testing advancedDatabaseObjectService.customQueryForObjects");

        String query = "MATCH (p:Pathway)-[:hasEvent]->(m) RETURN p.dbId as dbId, p.displayName as name, Collect(m.dbId) AS events, Collect(m.dbId) AS eventsPrimitiveArray, Collect(m.displayName) AS eventsArray ORDER BY p.dbId LIMIT 20";

        Collection<CustomQueryResult> customQueryResultList = advancedDatabaseObjectService.getCustomQueryResults(CustomQueryResult.class, query, null);

        assertNotNull(customQueryResultList);
        assertEquals(20, customQueryResultList.size());
        assertTrue(customQueryResultList.iterator().next().getEvents().size() > 0);
        assertTrue(customQueryResultList.iterator().next().getEventsArray().length > 0);
        assertTrue(customQueryResultList.iterator().next().getEventsPrimitiveArray().length > 0);
    }

    @Test
    public void customQueryDatabaseObjectTest() throws CustomQueryException {
        logger.info("Started testing advancedDatabaseObjectService.customQueryForDatabaseObject");

        String query = "MATCH (p:Pathway{dbId:$dbId}) RETURN p";
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("dbId", 1640170);
        Pathway pathway = advancedDatabaseObjectService.getCustomQueryResult(Pathway.class, query, parametersMap);
        assertNotNull(pathway);

        // by default, lazy loading is disabled in our tests, enable here for a particular test
        lazyFetchAspect.setEnableAOP(true);
        assertNotNull(pathway.getHasEvent());
        assertEquals(4, pathway.getHasEvent().size());
        assertEquals(0, pathway.getHasEncapsulatedEvent().size());

        // disable it for further tests in this particular test class
        lazyFetchAspect.setEnableAOP(false);
    }


//    @Test
    public void customQueryTest() throws CustomQueryException {
        String query = "MATCH (n:ReferenceEntity) RETURN DISTINCT n.identifier AS identifier";
        Collection<String> accessions = advancedDatabaseObjectService.getCustomQueryResults(String.class, query, null);
        assertNotNull(accessions);
        assertTrue(accessions.size() >= 380000);
    }

    @Test
    public void customStringQueryTest() throws CustomQueryException {
        String query = "MATCH (n:ReferenceEntity) RETURN COUNT(n.identifier)";
        Integer re = advancedDatabaseObjectService.getCustomQueryResult(Integer.class, query, null);
        assertNotNull(re);
        assertTrue(re > 380000);
    }

    @Test
    public void customBooleanQueryTest() throws CustomQueryException {
        String query = "MATCH (n:ReferenceEntity) RETURN COUNT(DISTINCT n.identifier) > 1";
        Boolean check = advancedDatabaseObjectService.getCustomQueryResult(Boolean.class, query, null);
        assertTrue(check);

        query = "MATCH (n:ReferenceEntity) RETURN COUNT(DISTINCT n.identifier) < 1";
        check = advancedDatabaseObjectService.getCustomQueryResult(Boolean.class, query, null);
        assertFalse(check);
    }

    @Test
    public void customQueryListOfCustomObjects() throws CustomQueryException {
        // Testing a custom query that retrieves a CustomObject and one (or more) of the attributes
        // are Lists of Number, Strings or List of Custom Objects.
        logger.info("Started testing advancedDatabaseObjectService.customQueryListOfCustomObjects");

        String query = "MATCH (pe:Complex{speciesName:$species,stId:$stId})-[:hasComponent|hasMember|hasCandidate|repeatedUnit|referenceEntity*]->(re) " +
                       "RETURN pe.stId AS stId, pe.displayName AS displayName, COLLECT(re.dbId) as dbIds, COLLECT(re.databaseName) as databaseNames, " +
                       "COLLECT({database:re.databaseName, identifier:re.identifier}) AS customReferences";

        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("species", "Homo sapiens");
        parametersMap.put("stId", "R-HSA-1852614");

        // In this test case, the relationships are mapped in the object CustomQueryComplex inside the Collection
        Collection<CustomQueryComplex> customComplexes = advancedDatabaseObjectService.getCustomQueryResults(CustomQueryComplex.class, query, parametersMap);

        assertNotNull(customComplexes);
        assertTrue(customComplexes.iterator().next().getDbIds().size() >= 9);
        assertTrue(customComplexes.iterator().next().getCustomReferences().size() >= 9);
        assertTrue(customComplexes.iterator().next().getDatabaseNames().size() >= 4);
    }

    @Test
    public void customQueryCustomObjects() throws CustomQueryException {
        // Testing a custom query that retrieves a CustomObject and one (or more) of the attributes are Custom Objects.
        logger.info("Started testing advancedDatabaseObjectService.customQueryCustomObjects");

        String query = "MATCH (pe:PhysicalEntity{speciesName:$species, stId:$stId})-[:referenceEntity]->(re) " +
                       "RETURN pe.stId AS stId, pe.displayName AS displayName, {database:re.databaseName, identifier:re.identifier} AS customReference";

        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("species", "Homo sapiens");
        parametersMap.put("stId", "R-HSA-141433");

        // In this test case, the relationships are mapped in the object Pathway inside the Collection
        CustomQueryPhysicalEntity customPE = advancedDatabaseObjectService.getCustomQueryResult(CustomQueryPhysicalEntity.class, query, parametersMap);

        assertNotNull(customPE);
        assertEquals("R-HSA-141433", customPE.getStId());
        assertEquals("UniProt", customPE.getCustomReference().getDatabase());
        assertEquals("Q9Y6D9", customPE.getCustomReference().getIdentifier());
    }

    @Test
    public void activeUnitContentTest() {
        logger.info("Started testing advancedDatabaseObjectService.activeUnitContentTest");
        long start, time;
        start = System.currentTimeMillis();
        CatalystActivity ca = advancedDatabaseObjectService.findById(5643997, 1000);
        assertEquals(ca.getActiveUnit().size(), 1, "There should be only one active unit");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        logger.info("Finished");
    }
}