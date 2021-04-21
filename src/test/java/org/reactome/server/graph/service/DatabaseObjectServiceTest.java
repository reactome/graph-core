package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.repository.AdvancedDatabaseObjectRepository;
import org.reactome.server.graph.repository.DatabaseObjectRepository;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.reactome.server.graph.util.JunitHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Arrays;
import java.util.List;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 *         507868 Will test wrong. Difference is that duplications are removed in the graph
 */
public class DatabaseObjectServiceTest extends BaseTest {

    private static final Long homoSapiens = 48887L;
    private static final Long dbId = 5205685L;
    private static final String stId = "R-HSA-5205685";

    private static final List<Long> dbIds = Arrays.asList(1640170L, 73886L, 1500620L);
    private static final List<String> stIds = Arrays.asList("R-HSA-1640170", "R-HSA-73886", "R-HSA-5672710");
    private static final List<Object> ids = Arrays.asList(1640170L, 73886L, 1500620L, "R-HSA-199420");

    @Autowired
    private AdvancedDatabaseObjectRepository advancedDatabaseObjectRepository;

    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    @Test
    public void sillyTest () {
        PhysicalEntity ss = advancedDatabaseObjectRepository.queryRelationshipTypesByDbId(189102L, "PhysicalEntity", RelationshipDirection.UNDIRECTED, "output");

        System.out.println(ss.getProducedByEvent());

        DatabaseObject sas = databaseObjectRepository.findByDbId(189102L);
        System.out.println(sas);
    }

//    @Autowired
//    private DatabaseObjectService databaseObjectService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + DatabaseObjectServiceTest.class.getName() + "!!! --- \n");
    }

        @Test
    public void findByDbIdTest() throws Exception {

        logger.info("Started testing databaseObjectService.findByDbIdTest");
        long start, time;
        start = System.currentTimeMillis();
        Complex databaseObjectObserved = databaseObjectRepository.findByDbId(5672710L);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

    @Test
    public void findByDbIdTest2() throws Exception {

        logger.info("Started testing databaseObjectService.findByDbIdTest");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectRepository.findByDbId(dbId);
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
//    public void findHomoSapiensTest() throws Exception {
//
//        logger.info("Started testing databaseObjectService.findHomoSapiensTest");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = advancedDatabaseObjectRepository.findById(homoSapiens, RelationshipDirection.OUTGOING);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(homoSapiens.toString());
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        assertEquals(databaseObjectExpected.getDisplayName(), databaseObjectObserved.getDisplayName());
//        logger.info("Finished");
//    }
//    @Test
//    public void findByStIdTest() throws InvocationTargetException, IllegalAccessException {
//
//        logger.info("Started testing databaseObjectService.findByStIdTest");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = databaseObjectService.findById(stId);
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
//    @Test
//    public void findByDbIdNoRelationsTest() {
//
//        logger.info("Started testing databaseObjectService.findByDbIdNoRelationsTest");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = databaseObjectService.findByIdNoRelations(dbId);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        assertEquals(databaseObjectExpected, databaseObjectObserved);
//        logger.info("Finished");
//    }
//
//    @Test
//    public void findByStIdRelationsTest() {
//
//        logger.info("Started testing databaseObjectService.findByStIdRelationsTest");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = databaseObjectService.findByIdNoRelations(stId);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        assertEquals(databaseObjectExpected, databaseObjectObserved);
//        logger.info("Finished");
//    }
//
//    @Test
//    public void testFindByDbIds() throws Exception {
//
//        logger.info("Started testing databaseObjectService.findByDbId");
//        long start, time;
//        start = System.currentTimeMillis();
//
//        Collection<DatabaseObject> databaseObjectsObserved = databaseObjectService.findByIdsNoRelations(dbIds);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(3, databaseObjectsObserved.size());
//        logger.info("Finished");
//    }
//
//    @Test
//    public void findByStIdsTest() {
//
//        logger.info("Started testing databaseObjectService.findByStIds");
//        long start, time;
//        start = System.currentTimeMillis();
//        Collection<DatabaseObject> databaseObjectsObserved = databaseObjectService.findByIdsNoRelations(stIds);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(3, databaseObjectsObserved.size());
//        logger.info("Finished");
//    }
//
//    @Test
//    public void findByIdsTest() {
//
//        logger.info("Started testing databaseObjectService.findByIdsTest");
//        long start, time;
//        start = System.currentTimeMillis();
//        Collection<DatabaseObject> databaseObjectsObserved = databaseObjectService.findByIdsNoRelations(ids);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(4, databaseObjectsObserved.size());
//        logger.info("Finished");
//    }
//
//    @Test
//    public void useOldStableIdentifier() {
//        logger.info("Started testing databaseObjectService.useOldStableIdentifier");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObject = databaseObjectService.findById("REACT_13");
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals("The old StId for R-HSA-71291 is REACT_13. Wrong one found " + databaseObject.getStId(), "R-HSA-71291", databaseObject.getStId());
//        logger.info("Finished");
//    }
}