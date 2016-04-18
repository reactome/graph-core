package org.reactome.server.tools.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.tools.config.MyConfiguration;
import org.reactome.server.tools.domain.model.*;
import org.reactome.server.tools.domain.result.ComponentOf;
import org.reactome.server.tools.domain.result.LabelsCount;
import org.reactome.server.tools.domain.result.Participant;
import org.reactome.server.tools.util.DatabaseObjectFactory;
import org.reactome.server.tools.util.JunitHelper;
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
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 *
 * 507868 Will test wrong. Difference is that duplications are removed in the graph
 *
 */
@ContextConfiguration(classes = { MyConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseObjectServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

    private static final Long dbId = 5205685L;
    private static final String stId = "R-HSA-5205685";

    private static Boolean checkedOnce = false;
    private static Boolean isFit = false;

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @Autowired
    private GenericService genericService;

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
            isFit = genericService.fitForService();
            checkedOnce = true;
        }
        assumeTrue(isFit);
        genericService.clearCache();
        DatabaseObjectFactory.clearCache();
    }

    @Test
    public void testFindByDbId() throws Exception {

        logger.info("Started testing databaseObjectService.findByDbId");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved =  databaseObjectService.findByDbId(dbId);
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
    public void testFindByDbIdNoRelations() {

        logger.info("Started testing databaseObjectService.findByDbIdNoRelations");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectService.findByDbIdNoRelations(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertEquals(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

    @Test
    public void testFindByStableIdentifier() throws InvocationTargetException, IllegalAccessException {

        logger.info("Started testing databaseObjectService.findByStableIdentifier");
        long start, time;
        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectService.findByStableIdentifier(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

    /**
     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
     * possibly change when content is added to reactome. This method will provide all participating ReferenceEntities
     * (even if the tests participatingMolecules 2 and 3 will provide 23, in this casee 22 is the correct number)
     */
    @Test
    public void testGetParticipatingMolecules() {

        logger.info("Started testing databaseObjectService.getParticipatingMolecules");
        long start, time;
        start = System.currentTimeMillis();
        Collection<ReferenceEntity> participants = databaseObjectService.getParticipatingMolecules(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(22,participants.size());
        logger.info("Finished");
    }

    /**
     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
     * possibly change when content is added to reactome. This method will provide all participating Ewases
     * of an Event and their ReferenceEntities dbIds and names.
     */
    @Test
    public void testGetParticipatingMolecules2() {

        logger.info("Started testing databaseObjectService.getParticipatingMolecules2");
        long start, time;
        start = System.currentTimeMillis();
        Collection<Participant> participants = databaseObjectService.getParticipatingMolecules2(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(23, participants.size());
        logger.info("Finished");
    }

//    /**
//     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
//     * possibly change when content is added to reactome. This method will provide all participating Ewases
//     * of an Event and their ReferenceEntities.
//     */
//    @ComponentOf
//    public void testGetParticipatingMolecules3() {
//
//        logger.info("Started testing databaseObjectService.getParticipatingMolecules3");
//        long start, time;
//        start = System.currentTimeMillis();
//        Collection<Participant> participants = databaseObjectService.getParticipatingMolecules3(dbId);
//        time = System.currentTimeMillis() - start;
//        logger.info("GraphDb execution time: " + time + "ms");
//
//        assertEquals(23, participants.size());
//        logger.info("Finished");
//    }

    /**
     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
     * possibly change when content is added to reactome. This method will provide all participating PhysicalEntities
     * of an Event
     */
    @Test
    public void testGetParticipatingMolecules3() {

        logger.info("Started testing databaseObjectService.getParticipatingMolecules4");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> participants = databaseObjectService.getParticipatingMolecules3(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(22, participants.size());
        logger.info("Finished");
    }


    /**
     * This method can hardly be tested. GkInstance does not provide any comparison and the static number will
     * possibly change when content is added to reactome. This method provides all different labels used in the
     * graph paired with the numbers of entries belonging to these labels.
     * @throws ClassNotFoundException
     */
    @Test
    public void testGetLabelsCount() throws ClassNotFoundException {

        logger.info("Started testing databaseObjectService.getLabelsCount");
        long start, time;
        start = System.currentTimeMillis();
        Collection<LabelsCount> labelsCounts = databaseObjectService.getLabelsCount();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        assertEquals(59, labelsCounts.size());
        logger.info("Finished");
    }



    @Test
    public void testOtherFormsOfThisMolecule() throws ClassNotFoundException {

        logger.info("Started testing databaseObjectService.getLabelsCount");
        long start, time;
        start = System.currentTimeMillis();
        Collection<PhysicalEntity> otherFormsOfThisMolecule = databaseObjectService.getOtherFormsOfThisMolecule(199420l);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        logger.info("Finished");
    }





    @Test
    public void testTests() throws ClassNotFoundException {

        logger.info("Started testing databaseObjectService.getLabelsCount");
        long start, time;
        start = System.currentTimeMillis();
        Collection<ComponentOf> tests = databaseObjectService.getComponentsOf("R-HSA-162629");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        logger.info("Finished");
    }


}