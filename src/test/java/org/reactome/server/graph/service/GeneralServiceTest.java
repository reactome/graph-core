package org.reactome.server.graph.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

        assertTrue(schemaClassCounts.size() > 5);
        logger.info("Finished");
    }

    @Test
    public void getDBVersionTest() throws Exception {
        logger.info("Started testing genericService.getReleaseVersion");
        long start, time;
        start = System.currentTimeMillis();
        Integer neo4JReleaseNumber = generalService.getDBInfo().getReleaseNumber();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        Integer sqlReleaseNumber = DatabaseObjectFactory.getDBVersion();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertInstanceOf(Integer.class, neo4JReleaseNumber);
        assertInstanceOf(Integer.class, sqlReleaseNumber);
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

        //assertEquals(dBNameExpected, dBNameObserved);
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
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(!result.isEmpty());
        logger.info("Finished");
    }

}
