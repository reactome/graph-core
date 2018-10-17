package org.reactome.server.graph.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.graph.config.Neo4jConfig;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
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
    private GeneralService generalService;

    @Autowired
    private DatabaseObjectService dos;

    @Autowired
    private PhysicalEntityService physicalEntityService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + DetailsServiceTest.class.getName() + " !!! --- \n");
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

    @Test
    public void testGetOtherFormsOfThisMoleculeByDbId() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing physicalEntityService.testGetOtherFormsOfThisMoleculeByDbId");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> otherFormsOfThisMolecule = physicalEntityService.getOtherFormsOf(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(otherFormsOfThisMolecule.size() >= 27);
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

        assertTrue(otherFormsOfThisMolecule.size() >= 27);
        logger.info("Finished");
    }

    @Test
    public void testGetOtherFormsOfThisMoleculeByPhysicalEntity() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing physicalEntityService.testGetOtherFormsOfThisMoleculeByStId");
        long start, time;
        start = System.currentTimeMillis();
        PhysicalEntity pe = dos.findById(stId);
        Collection<PhysicalEntity> otherFormsOfThisMolecule = physicalEntityService.getOtherFormsOf(pe);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(otherFormsOfThisMolecule.size() >= 27);
        logger.info("Finished");
    }

    @Test
    public void testGetComplexesFor(){
        logger.info("Started testing physicalEntityService.testGetComplexesFor");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Complex> complexes = physicalEntityService.getComplexesFor("P00533", "UniProt");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(complexes.size() >= 5);
        logger.info("Finished");
    }

    @Test
    public void testGetComplexSubunits(){
        logger.info("Started testing physicalEntityService.testGetComplexSubunits");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> complexSubunits = physicalEntityService.getPhysicalEntitySubunits("R-HSA-5674003");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertTrue(complexSubunits.size() >= 5);
        logger.info("Finished");
    }
}