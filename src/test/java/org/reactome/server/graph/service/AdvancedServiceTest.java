package org.reactome.server.graph.service;

import org.apache.bcel.generic.StackInstruction;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.custom.CustomQueryComplex;
import org.reactome.server.graph.custom.CustomQueryPhysicalEntity;
import org.reactome.server.graph.custom.CustomQueryResult;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.exception.CustomQueryException;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.reactome.server.graph.util.TestNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 */
@SpringBootTest
public class AdvancedServiceTest extends BaseTest {

    @Autowired
    private AdvancedDatabaseObjectService advancedDatabaseObjectService;

    @Autowired
    private TestNodeService testNodeService;

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
        PhysicalEntity peObserved = advancedDatabaseObjectService.findEnhancedObjectById(PhysicalEntities.entityWithAccessionedSequence.getStId());

        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
        assertEquals(PhysicalEntities.positiveRegulation.getStId(), peObserved.getPositivelyRegulates().get(0).getStId());
        logger.info("Finished");
    }

    @Test
    public void findEnhancedEventByIdTest() {

        logger.info("Started testing advancedDatabaseObjectService.findEnhancedPathwayByIdTest");
        long start, time;
        start = System.currentTimeMillis();
        ReactionLikeEvent rleObserved = advancedDatabaseObjectService.findEnhancedObjectById(Events.associationReaction.getStId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(rleObserved.getInput().size() >= 1);
        logger.info("Finished");
    }

    // --------------------------------------- Limited Finder Methods --------------------------------------------------

    @Test
    public void findLimitedObjectByIdTest() {

        logger.info("Started testing advancedDatabaseObjectService.findAllByProperty");
        long start, time;
        start = System.currentTimeMillis();
        Pathway databaseObjectObserved = advancedDatabaseObjectService.findById(Events.diagramPathway.getStId(), 10);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertNotNull(databaseObjectObserved.getStId());
        assertEquals(databaseObjectObserved.getStId(), Events.diagramPathway.getStId());
        logger.info("Finished");
    }

    @Test
    public void findNonRepositoryObjectById() {
        logger.info("Started testing advancedDatabaseObjectService.findAllByProperty");


        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = advancedDatabaseObjectService.findById(PhysicalEntities.fragmentDeletionModification.getStId(), 10);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertThat(databaseObjectObserved.getClass().getName()).isEqualTo( "org.reactome.server.graph.domain.model.FragmentDeletionModification");
        logger.info("Finished");
    }


    // --------------------------------------- Generic Finder Methods --------------------------------------------------

    @Test
    public void findByPropertyTest() {

        logger.info("Started testing advancedDatabaseObjectService.findByProperty");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = advancedDatabaseObjectService.findByProperty(DatabaseObject.class, "stId", Events.diagramPathway.getStId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(Events.diagramPathway.getStId(), databaseObjectObserved.getStId());
        logger.info("Finished");
    }

    @Test
    public void findAllByPropertyTest() {

        logger.info("Started testing advancedDatabaseObjectService.findAllByProperty");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DatabaseObject> databaseObjectObserved = advancedDatabaseObjectService.findAllByProperty(DatabaseObject.class, "stId", Events.diagramPathway.getStId());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1, databaseObjectObserved.size());
        logger.info("Finished");
    }

    // ---------------------- Methods with RelationshipDirection and Relationships -------------------------------------

    @Test
    public void findByDbIdWithRelationshipDirectionTest() {
        logger.info("Started testing advancedDatabaseObjectService.findByDbIdWithRelationshipDirectionTest");
        long start, time;

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = advancedDatabaseObjectService.findById(PhysicalEntities.compartment.getStId(), RelationshipDirection.INCOMING);
        time = System.currentTimeMillis() - start;

        logger.info("GraphDb execution time: " + time + "ms");
        assertThat(databaseObjectObserved.getDbId()).isEqualTo(PhysicalEntities.compartment.getDbId());
        logger.info("Finished");
    }

    @Test
    public void findByStIdWithRelationshipDirectionTest() {
        logger.info("Started testing advancedDatabaseObjectService.findByStIdWithRelationshipDirectionTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = advancedDatabaseObjectService.findById(Events.transitionReaction.getStId(), RelationshipDirection.UNDIRECTED);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertThat(databaseObjectObserved.getStId()).isEqualTo(Events.transitionReaction.getStId());

        logger.info("Finished");
    }

    @Test
    public void findByStIdWithRelationshipDirectionAndRelationshipsTest() {
        logger.info("Started testing advancedDatabaseObjectService.findByStIdWithRelationshipDirectionAndRelationshipsTest");
        long start, time;
        start = System.currentTimeMillis();
        Pathway databaseObjectObserved = advancedDatabaseObjectService.findById(Events.diagramPathway.getStId(), RelationshipDirection.OUTGOING, "hasEvent");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertFalse(databaseObjectObserved.getHasEvent().isEmpty());
        logger.info("Finished");
    }

    @Test
    public void findByStIdsWithRelationshipDirectionTest() {
        logger.info("Started testing advancedDatabaseObjectService.findByStIdsWithRelationshipDirectionTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DatabaseObject> databaseObjectObserved = advancedDatabaseObjectService.findByStIds(Arrays.asList(PhysicalEntities.complex.getStId(), PhysicalEntities.entityWithAccessionedSequence.getStId()), RelationshipDirection.OUTGOING);
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
        Collection<DatabaseObject> databaseObjectObserved = advancedDatabaseObjectService.findByDbIds(Arrays.asList(Events.diagramPathway.getDbId(),Events.associationReaction.getDbId()), RelationshipDirection.OUTGOING, "input","output");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1, databaseObjectObserved.size());
        logger.info("Finished");
    }

    @Test
    public void findByStIdsWithRelationshipDirectionAndRelationshipsTest() {
        logger.info("Started testing advancedDatabaseObjectService.findByStIdsWithRelationshipDirectionAndRelationshipsTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<DatabaseObject> databaseObjectObserved = advancedDatabaseObjectService.findByStIds(Arrays.asList(Events.associationReaction.getStId(), PhysicalEntities.complex.getStId()), RelationshipDirection.OUTGOING, "output", "input");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(1, databaseObjectObserved.size());
        logger.info("Finished");
    }

    // ------------------------------- Methods with Custom Query -------------------------------------
    @Test
    public void customQueryWithCustomObjectTest() throws CustomQueryException {
        logger.info("Started testing advancedDatabaseObjectService.customQueryForObject");

        String query = "MATCH (p:Pathway{dbId:$dbId})-[:hasEvent]->(m) RETURN p.dbId as dbId, p.displayName as name, Collect(m.dbId) AS events, Collect(m.dbId) AS eventsPrimitiveArray, Collect(m.displayName) AS eventsArray ";
        Map<String, Object> parametersMap = new HashMap<>();

        parametersMap.put("dbId", Events.diagramPathway.getDbId());
        CustomQueryResult customQueryResult = advancedDatabaseObjectService.getCustomQueryResult(CustomQueryResult.class, query, parametersMap);
        assertEquals(9, customQueryResult.getEvents().size());
        assertEquals(9, customQueryResult.getEventsArray().length);
        assertEquals(9, customQueryResult.getEventsPrimitiveArray().length);
    }

    @Test
    public void customQueryWithEWASTest() throws CustomQueryException {
        // language=cypher
        String query = "" +
                "MATCH path=(p:Pathway{stId:$stId})-[:hasEvent*]->(rle:ReactionLikeEvent) " +
                "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
                "MATCH (rle)-[:input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(pe:EntityWithAccessionedSequence) " +
                "WITH DISTINCT pe " +
                "MATCH (pe)-[hm:hasModifiedResidue]->(tm:TranslationalModification) " +
                "RETURN DISTINCT pe, collect(hm), collect(tm) ";
        Map<String, Object> params = new HashMap<>();
        params.put("stId", "R-HSA-68877");
        Collection<EntityWithAccessionedSequence> ewass = advancedDatabaseObjectService.getCustomQueryResults(EntityWithAccessionedSequence.class, query, params);
        assertThat(ewass).allMatch(ewas -> ewas instanceof EntityWithAccessionedSequence);
    }

    @Test
    public void customQueryListOfCustomObjectTest() throws CustomQueryException {
        logger.info("Started testing advancedDatabaseObjectService.customQueryForObjects");

        String query = "MATCH (p:Pathway)-[:hasEvent]->(m) RETURN p.dbId as dbId, p.displayName as name, Collect(m.dbId) AS events, Collect(m.dbId) AS eventsPrimitiveArray, Collect(m.displayName) AS eventsArray ORDER BY p.dbId LIMIT 20";

        Collection<CustomQueryResult> customQueryResultList = advancedDatabaseObjectService.getCustomQueryResults(CustomQueryResult.class, query, null);

        assertEquals(20, customQueryResultList.size());
        assertTrue(customQueryResultList.iterator().next().getEvents().size() > 0);
        assertTrue(customQueryResultList.iterator().next().getEventsArray().length > 0);
        assertTrue(customQueryResultList.iterator().next().getEventsPrimitiveArray().length > 0);
    }

    @Test
    public void customQueryDatabaseObjectTest() throws CustomQueryException {  //TODO
        logger.info("Started testing advancedDatabaseObjectService.customQueryForDatabaseObject");

        String query = "MATCH (p:Pathway{dbId:$dbId}) RETURN p";
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("dbId", 1640170);
        Pathway pathway = advancedDatabaseObjectService.getCustomQueryResult(Pathway.class, query, parametersMap);
        assertNotNull(pathway);

        // by default, lazy loading is disabled in our tests, enable here for a particular test
        lazyFetchAspect.setEnableAOP(true);  // TODO this does not work
        assertNotNull(pathway.getHasEvent());

        // disable it for further tests in this particular test class
        lazyFetchAspect.setEnableAOP(false);
    }


    @Test
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
    public void customQueryListOfCustomObjects() throws CustomQueryException {  // TODO
        // Testing a custom query that retrieves a CustomObject and one (or more) of the attributes
        // are Lists of Number, Strings or List of Custom Objects.
        logger.info("Started testing advancedDatabaseObjectService.customQueryListOfCustomObjects");


        String query = "MATCH (pe:Complex{speciesName:$species,stId:$stId})-[:hasComponent|hasMember|hasCandidate|repeatedUnit|referenceEntity|proteinMarker|RNAMarker*]->(re) " +
                       "RETURN pe.stId AS stId, pe.displayName AS displayName, COLLECT(re.dbId) as dbIds, COLLECT(re.databaseName) as databaseNames, " +
                       "COLLECT({database:re.databaseName, identifier:re.identifier}) AS customReferences";

        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("species", "Homo sapiens");
        parametersMap.put("stId", PhysicalEntities.complex.getStId());

        // In this test case, the relationships are mapped in the object CustomQueryComplex inside the Collection
        Collection<CustomQueryComplex> customComplexes = advancedDatabaseObjectService.getCustomQueryResults(CustomQueryComplex.class, query, parametersMap);

        assertNotNull(customComplexes);
        //assertEquals(2,customComplexes);
        //assertEquals(2, customComplexes.iterator().next().getCustomReferences().size());
    }

    @Test
    public void customQueryCustomObjects() throws CustomQueryException {
        // Testing a custom query that retrieves a CustomObject and one (or more) of the attributes are Custom Objects.
        logger.info("Started testing advancedDatabaseObjectService.customQueryCustomObjects");

        String query = "MATCH (pe:PhysicalEntity{speciesName:$species, stId:$stId})-[:referenceEntity]->(re) " +
                       "RETURN pe.stId AS stId, pe.displayName AS displayName, {database:re.databaseName, identifier:re.identifier} AS customReference";

        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("species", "Homo sapiens");
        parametersMap.put("stId", PhysicalEntities.entityWithAccessionedSequence.getStId());

        // In this test case, the relationships are mapped in the object Pathway inside the Collection
        CustomQueryPhysicalEntity customPE = advancedDatabaseObjectService.getCustomQueryResult(CustomQueryPhysicalEntity.class, query, parametersMap);

        assertNotNull(customPE);
        assertEquals(PhysicalEntities.entityWithAccessionedSequence.getStId(), customPE.getStId());
    }

    @Test
    public void activeUnitContentTest() {
        logger.info("Started testing advancedDatabaseObjectService.activeUnitContentTest");
        long start, time;
        start = System.currentTimeMillis();
        CatalystActivity ca = advancedDatabaseObjectService.findById(PhysicalEntities.catalystActivity.getStId(), 1000);
        assertEquals(ca.getActiveUnit().size(), 1, "There should be only one active unit");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        logger.info("Finished");
    }
}