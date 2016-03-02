package uk.ac.ebi.reactome.service;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.reactome.config.MyConfiguration;
import uk.ac.ebi.reactome.data.DatabaseObjectFactory;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.ReferenceEntity;
import uk.ac.ebi.reactome.domain.result.LabelsCount;
import uk.ac.ebi.reactome.domain.result.Participant;
import uk.ac.ebi.reactome.domain.result.Participant2;
import uk.ac.ebi.reactome.service.helper.Node;
import uk.ac.ebi.reactome.service.util.DatabaseObjectUtils;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

//    private static final Long dbId = 7130561L; //381283L; // 75949L;
//    private static final String stId = "R-HSA-5205685";

//    private static final Long dbId = 5205685L;
//    private static final String stId = "R-HSA-5205685";

    private static final Long dbId = 70655L;//1368140l;//507868L;
    private static final String stId = "R-HSA-507868";

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @Autowired
    private GenericService genericService;

    @BeforeClass
    public static void setUpClass () {
        logger.info(" --- !!! Running DatabaseObjectServiceTests !!! --- \n");
    }

    @Before
    public void setUp() throws Exception {
        genericService.findByDbId(DatabaseObject.class,1l,0);
        genericService.clearCache();
        DatabaseObjectFactory.createObject("1");
        DatabaseObjectFactory.clearCache();
    }

    @After
    public void tearDown() {}

    @Test
    public void testFindByDbId() throws Exception {

        logger.info("Started testing databaseObjectService.findByDbId");
        long start, time;

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectService.findByDbId(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        DatabaseObjectFactory.clearCache();

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
        databaseObjectExpected.load();
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
    }
    @Test
    public void testFindByDbIdNoRelations() {

//        uk.ac.ebi.reactome.domain.result.Test t = databaseObjectRepository.findByDbIdNoRelations2(dbId);


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

        assertEquals(databaseObjectExpected.getDbId(), databaseObjectObserved.getDbId());
//        assertEquals(databaseObjectExpected.getDisplayName(),databaseObjectObserved.getDisplayName());
    }

    @Test
    public void testFindByStableIdentifier() {

        logger.info("Started testing databaseObjectService.findByStableIdentifier");
        long start, time;

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = databaseObjectService.findByStableIdentifier(stId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(stId);
        databaseObjectExpected.load();
        time = System.currentTimeMillis() - start;
        logger.info("GkInstance execution time: " + time + "ms");

        assertEquals(databaseObjectExpected.getDbId(), databaseObjectObserved.getDbId());
        assertEquals(databaseObjectExpected.getDisplayName(), databaseObjectObserved.getDisplayName());
    }

    @Test
    public void testGetParticipatingMolecules() {

        logger.info("Started testing databaseObjectService.getParticipatingMolecules");
        long start, time;

        start = System.currentTimeMillis();
        Collection<ReferenceEntity> participants = databaseObjectService.getParticipatingMolecules(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

//        assertEquals(participants.size(),15);
//        TODO add actual tests

    }

    @Test
    public void testGetParticipatingMolecules2() {

        logger.info("Started testing databaseObjectService.getParticipatingMolecules2");
        long start, time;

        start = System.currentTimeMillis();
        Collection<Participant> participants = databaseObjectService.getParticipatingMolecules2(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

//        assertEquals(participants.size(), 21);
//        TODO add actual tests

    }

    @Test
    public void testGetParticipatingMolecules3() {

        logger.info("Started testing databaseObjectService.getParticipatingMolecules3");
        long start, time;

        start = System.currentTimeMillis();
        Collection<Participant2> participants = databaseObjectService.getParticipatingMolecules3(dbId);
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

//        assertEquals(participants.size(), 21);
//        TODO add actual tests

    }

    @Test
    public void testGetLabelsCount() throws ClassNotFoundException {

        logger.info("Started testing databaseObjectService.getLabelsCout");
        long start, time;

        start = System.currentTimeMillis();
        Collection<LabelsCount> labelsCounts = databaseObjectService.getLabelsCount();
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
//        assertEquals(labelsCount.size(), 29);
//        TODO add actual tests
    }

    @Test
    public void testGetGraphModelTree() throws ClassNotFoundException {

        logger.info("Started testing databaseObjectService.getLabelsCout");
        long start, time;

        start = System.currentTimeMillis();

        Node root = DatabaseObjectUtils.getGraphModelTree(databaseObjectService.getLabelsCount());
        int x = root.findMaxPage("Pathway",25);


        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
//        assertEquals(root.getChildren().size(), 29);
//        TODO add actual tests
    }

    @Test
    public void test () throws ClassNotFoundException {

        logger.info("Started testing database");
        long start, time;

        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectObserved = databaseObjectService.findByIdFillLegacyRelations(dbId.toString());
//        databaseObjectService.getAttributeTable(Reaction.class.getSimpleName());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");
    }

}