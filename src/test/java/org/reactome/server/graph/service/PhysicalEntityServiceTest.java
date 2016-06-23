package org.reactome.server.graph.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.Neo4jConfig;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@ContextConfiguration(classes = {Neo4jConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PhysicalEntityServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    private static final Long dbId = 199420L;
    private static final String stId = "R-HSA-199420";

    @Autowired
    private PhysicalEntityService physicalEntityService;

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
        DatabaseObjectFactory.clearCache();
    }
//
//    @Test
//    public void testFindByDbId() throws InvocationTargetException, IllegalAccessException {
//
//        logger.info("Started testing physicalEntityService.findByDbId");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = physicalEntityService.findById(dbId);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
//        logger.info("Finished");
//    }
//
//    @Test
//    public void findByStIdTest() throws InvocationTargetException, IllegalAccessException {
//
//        logger.info("Started testing physicalEntityService.findByStId");
//        long start, time;
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = physicalEntityService.findById(stId);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(stId);
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");
//
//        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
//        logger.info("Finished");
//    }

    @Test
    public void testGetOtherFormsOfThisMoleculeByDbId() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing physicalEntityService.testGetOtherFormsOfThisMoleculeByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> otherFormsOfThisMolecule = physicalEntityService.getOtherFormsOf(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(27, otherFormsOfThisMolecule.size());
        logger.info("Finished");
    }

    @Test
    public void testGetOtherFormsOfThisMoleculeByStId() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing physicalEntityService.testGetOtherFormsOfThisMoleculeByStId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> otherFormsOfThisMolecule = physicalEntityService.getOtherFormsOf(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(27, otherFormsOfThisMolecule.size());
        logger.info("Finished");
    }

    /**
     * Execution times do not give fair estimation since GkInstance does not fill any of the legacy fields.
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
//    @Test
//    public void findByIdWithLegacyFields() throws InvocationTargetException, IllegalAccessException {
//
//        logger.info("Started testing physicalEntityService.findByIdWithLegacyFields");
//
//        DatabaseObject databaseObjectObserved = physicalEntityService.findByIdWithLegacyFields(dbId.toString());
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
//
//        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
//        logger.info("Finished");
//    }

    @Test
    public void testGetComplexSubunits(){
        logger.info("Started testing physicalEntityService.testGetComplexSubunits");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> complexSubunits = physicalEntityService.getComplexSubunits("R-HSA-5674003");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(5, complexSubunits.size());
        logger.info("Finished");
    }
}