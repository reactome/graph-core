package org.reactome.server.graph.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.model.Disease;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.Species;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.reactome.server.graph.domain.result.SimpleReferenceObject;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 04.06.16.
 */
public class SchemaServiceTest extends BaseTest {

    @Autowired
    private SchemaService schemaService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + SchemaServiceTest.class.getName() + " !!! --- \n");
    }

    // ---------------------------------------- Query by Class --------------------------------------------------

    @Test
    public void getByClassTest() {

        logger.info("Started testing schemaService.getByClassTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Species> species = schemaService.getByClass(Species.class);
        Set observedSpecies = new HashSet<>(species);
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
    public void getByClassAndSpeciesTest() {

        logger.info("Started testing schemaService.getByClassAndSpeciesTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = schemaService.getByClass(Pathway.class, 9606);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 1991);
        logger.info("Finished");
    }

    @Test
    public void getByClassNameTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getByClassName");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Disease> diseases = schemaService.getByClassName("Disease");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(281, diseases.size());
        logger.info("Finished");
    }

    @Test
    public void getByClassNameAndSpeciesTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getByClassNameAndSpeciesTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = schemaService.getByClassName("Pathway", 9606);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 1991);
        logger.info("Finished");
    }

    // ------------------------------------ Query by Class (pageing) -----------------------------------------------

    @Test
    public void getByClassWithPagingTest() {

        logger.info("Started testing schemaService.getByClassWithPagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = schemaService.getByClass(Pathway.class, 1, 25);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(25, pathways.size());
        logger.info("Finished");
    }

    @Test
    public void getByClassAndSpeciesWithPagingTest() {

        logger.info("Started testing schemaService.getByClassAndSpeciesWithPagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = schemaService.getByClass(Pathway.class, 9606, 1, 25);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(25, pathways.size());
        logger.info("Finished");
    }

    @Test
    public void getByClassNameWithPagingTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getByClassNameWithPagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = schemaService.getByClassName("Pathway", 1, 25);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(25, pathways.size());
        logger.info("Finished");
    }

    @Test
    public void getByClassNameAndSpeciesWithPagingTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getByClassNameAndSpeciesTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Pathway> pathways = schemaService.getByClassName("Pathway", 9606, 1, 25);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(25, pathways.size());
        logger.info("Finished");
    }

    // ---------------------------------------- Query by Class for SimpleObject ------------------------------------------------

    @Test
    public void getSimpleDatabaseObjectByClassTest() {

        logger.info("Started testing schemaService.getSimpleDatabaseObjectByClass");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = schemaService.getSimpleDatabaseObjectByClass(Pathway.class);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 21191);
        logger.info("Finished");
    }

    @Test
    public void getSimpleDatabaseObjectByClassAndSpeciesTest() {

        logger.info("Started testing schemaService.getSimpleDatabaseObjectByClassAndSpeciesTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = schemaService.getSimpleDatabaseObjectByClass(Pathway.class, 9606);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 1991);
        logger.info("Finished");
    }

    @Test
    public void getSimpleDatabaseObjectByClassNameTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getSimpleDatabaseObjectByClass");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = schemaService.getSimpleDatabaseObjectByClassName("Pathway");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 21191);
        logger.info("Finished");
    }

    @Test
    public void getSimpleDatabaseObjectByClassNameAndSpeciesTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getSimpleDatabaseObjectByClassNameAndSpeciesTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = schemaService.getSimpleDatabaseObjectByClassName("Pathway", "Homo sapiens");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 1991);
        logger.info("Finished");
    }

    // ---------------------------------------- Query by Class for SimpleObject (paging) ------------------------------------------------

    @Test
    public void getSimpleDatabaseObjectByClassWithPagingTest() {

        logger.info("Started testing schemaService.getSimpleDatabaseObjectByClassWithPagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = schemaService.getSimpleDatabaseObjectByClass(Pathway.class, 1, 20000);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 20000);
        logger.info("Finished");
    }

    @Test
    public void getSimpleDatabaseObjectByClassAndSpeciesWithPagingTest() {

        logger.info("Started testing schemaService.getSimpleDatabaseObjectByClassAndSpeciesWithPagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = schemaService.getSimpleDatabaseObjectByClass(Pathway.class, 9606, 1, 2000);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 1991);
        logger.info("Finished");
    }

    @Test
    public void getSimpleDatabaseObjectByClassNameWithPagingTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getSimpleDatabaseObjectByClassNameWithPagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = schemaService.getSimpleDatabaseObjectByClassName("Pathway", 1, 20000);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(20000, pathways.size());
        logger.info("Finished");
    }

    @Test
    public void getSimpleDatabaseObjectByClassNameAndSpeciesWithPagingTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getSimpleDatabaseObjectByClassNameAndSpeciesWithPagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleDatabaseObject> pathways = schemaService.getSimpleDatabaseObjectByClassName("Pathway", "Homo sapiens", 1, 20000);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(pathways.size() >= 1991);
        logger.info("Finished");
    }

    // ---------------------------------------- Query by Class for SimpleReferenceObject ------------------------------------------------

    @Test
    public void getSimpleReferencesObjectsByClassTest() {

        logger.info("Started testing schemaService.getSimpleReferencesObjectsByClassTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleReferenceObject> diseases = schemaService.getSimpleReferencesObjectsByClass(Disease.class);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(281, diseases.size());
        logger.info("Finished");
    }

    @Test
    public void getSimpleReferencesObjectsByClassPagingTest() {

        logger.info("Started testing schemaService.getSimpleReferencesObjectsByClassPagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleReferenceObject> diseases = schemaService.getSimpleReferencesObjectsByClass(Disease.class, 1, 200);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(200, diseases.size());
        logger.info("Finished");
    }

    @Test
    public void getSimpleReferencesObjectsByClassNameTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getSimpleReferencesObjectsByClassNameTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleReferenceObject> diseases = schemaService.getSimpleReferencesObjectsByClassName("Disease");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(281, diseases.size());
        logger.info("Finished");
    }

    @Test
    public void getSimpleReferencesObjectsByClassNamePagingTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getSimpleReferencesObjectsByClassNamePagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<SimpleReferenceObject> diseases = schemaService.getSimpleReferencesObjectsByClassName("Disease", 1, 200);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(200, diseases.size());
        logger.info("Finished");
    }

    // ---------------------------------------- Query by Class for single value ------------------------------------------------

    @Test
    public void getStIdsByClassTest() {

        logger.info("Started testing schemaService.getSimpleReferencesObjectsByClassNamePagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<String> stIds = schemaService.getStIdsByClass(Pathway.class);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(stIds.size() >= 21191L);
        logger.info("Finished");
    }

    @Test
    public void getStIdsByClassNameTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getSimpleReferencesObjectsByClassNamePagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<String> stIds = schemaService.getStIdsByClass("Pathway");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(stIds.size() >= 21191L);
        logger.info("Finished");
    }

    @Test
    public void getDbIdsByClassTest() throws Exception {

        logger.info("Started testing schemaService.getSimpleReferencesObjectsByClassNamePagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Set<Long> dbIds = new HashSet<>(schemaService.getDbIdsByClass(Pathway.class));
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(dbIds.size() >= 21191L);
        logger.info("Finished");
    }

    @Test
    public void getDbIdsByClassNameTest() throws ClassNotFoundException {

        logger.info("Started testing schemaService.getSimpleReferencesObjectsByClassNamePagingTest");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Long> dbIds = schemaService.getDbIdsByClass("Pathway");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(dbIds.size() >= 21191L);
        logger.info("Finished");
    }

    // ---------------------------------------- Count by Class ------------------------------------------------

    @Test
    public void countEntriesTest() {
        logger.info("Started testing schemaService.countEntriesTest");
        long start, time;
        start = System.currentTimeMillis();
        long count = schemaService.countEntries(Pathway.class);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(count >= 21191L);
        logger.info("Finished");
    }

    @Test
    public void countEntriesWithSpeciesTest() {
        logger.info("Started testing schemaService.countEntriesWithSpeciesTest");
        long start, time;
        start = System.currentTimeMillis();
        long count = schemaService.countEntries(Pathway.class, "9606");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(count >= 1991L);
        logger.info("Finished");
    }

    @Test
    public void countEntriesByClassNameTest() throws ClassNotFoundException {
        logger.info("Started testing schemaService.countEntriesByClassNameTest");
        long start, time;
        start = System.currentTimeMillis();
        long count = schemaService.countEntries("Pathway");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(count >= 21191L);
        logger.info("Finished");
    }

    @Test
    public void countEntriesByClassNameWithSpeciesTest() throws ClassNotFoundException {
        logger.info("Started testing schemaService.countEntriesByClassNameWithSpeciesTest");
        long start, time;
        start = System.currentTimeMillis();
        long count = schemaService.countEntries("Pathway", "Homo sapiens");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(count >= 1991L);
        logger.info("Finished");
    }

}
