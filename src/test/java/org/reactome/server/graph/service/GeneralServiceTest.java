package org.reactome.server.graph.service;

import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 05.06.16.
 */
public class GeneralServiceTest extends BaseTest {

    @Autowired private GeneralService generalService;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + GeneralServiceTest.class.getName() + " !!! --- \n");
    }

    // --------------------------------------------- General Repository ------------------------------------------------

    /**
     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
     * possibly change when content is added to reactome. This method provides all different labels used in the
     * graph paired with the numbers of entries belonging to these labels.
     */
    @Test
    public void testGetSchemaClassCountsTest() {

        logger.info("Started testing databaseObjectService.getLabelsCount");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SchemaClassCount> schemaClassCounts = generalService.getSchemaClassCounts();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(schemaClassCounts.size() >= 60);
        logger.info("Finished");
    }

    @Test
    public void getDBVersionTest() throws Exception {
        logger.info("Started testing genericService.getReleaseVersion");
        long start, time;
        start = System.currentTimeMillis();
        Integer dBVersionObserved = generalService.getDBInfo().getVersion();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        Integer dBVersionExpected = DatabaseObjectFactory.getDBVersion();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(dBVersionExpected, dBVersionObserved);
        logger.info("Finished");
    }

    @Test
    public void getDBNameTest() {
        logger.info("Started testing genericService.getReleaseVersion");
        long start, time;
        start = System.currentTimeMillis();
        String dBNameObserved = generalService.getDBInfo().getName();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        String dBNameExpected = DatabaseObjectFactory.getDBName();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(dBNameExpected, dBNameObserved);
        logger.info("Finished");
    }

    // --------------------------------------.. Generic Query Methods --------------------------------------------------

    @Test
    public void queryTest() {
        logger.info("Started testing generalService.countEntries");
        long start, time;
        start = System.currentTimeMillis();
        String query =  " MATCH (n:Person) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.displayName AS Name, m.displayName AS Modified " +
                "ORDER BY Modified, dbId";
        Collection<Map<String,Object>> result = generalService.query(query, Collections.emptyMap());
        System.out.println(result);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        //assertTrue(((Integer) result.iterator().next().get("COUNT(n)")) >= 1266096);
        logger.info("Finished");
    }

    // ------------------------------------------- Save and Delete -----------------------------------------------------

//    @Test
//    public void saveAndDeleteTest() {
//        Pathway pathway = new Pathway();
//        pathway.setDbId(111111111111L);
//        pathway.setStId("R-HSA-111111111111");
//        pathway.setDisplayName("TestPathway");
//
//        long count = schemaService.countEntries(Pathway.class);
//        generalService.save(pathway);
//        long countAfterSave = schemaService.countEntries(Pathway.class);
//        assertEquals(count + 1, countAfterSave);
//
//        generalService.delete(111111111111L);
//
//        long countAfterDelete = schemaService.countEntries(Pathway.class);
//        assertEquals(count, countAfterDelete);
//    }
//
//    @Test
//    public void saveAndDeleteWithDepthTest() {
//        Pathway pathway = new Pathway();
//        pathway.setDbId(111111111111L);
//        pathway.setStId("R-HSA-111111111111");
//        pathway.setDisplayName("TestPathway");
//
//        Pathway pathway2 = new Pathway();
//        pathway2.setDbId(111111111112L);
//        pathway2.setStId("R-HSA-111111111112");
//        pathway2.setDisplayName("TestPathway2");
//
//        Pathway pathway3 = new Pathway();
//        pathway3.setDbId(111111111113L);
//        pathway3.setStId("R-HSA-111111111113");
//        pathway3.setDisplayName("TestPathway3");
//
//        List<Event> hasEvent = new ArrayList<>();
//        hasEvent.add(pathway2);
//        hasEvent.add(pathway3);
//        pathway.setHasEvent(hasEvent);
//
//        long count = schemaService.countEntries(Pathway.class);
//        generalService.save(pathway, 1);
//        long countAfterSave = schemaService.countEntries(Pathway.class);
//        assertEquals(count + 3, countAfterSave);
//        // delete will delete all relationships, nevertheless the other Nodes will still be present
//        generalService.delete(111111111111L);
//        generalService.delete(111111111112L);
//        generalService.delete(111111111113L);
//        long countAfterDelete = schemaService.countEntries(Pathway.class);
//        assertEquals(count, countAfterDelete);
//    }
}
