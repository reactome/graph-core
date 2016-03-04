package uk.ac.ebi.reactome.service;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.reactome.config.MyConfiguration;
import uk.ac.ebi.reactome.data.DatabaseObjectFactory;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;

import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeTrue;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 03.03.16.
 */
@ContextConfiguration(classes = { MyConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class EventServiceTest {

    private static final Logger logger = LoggerFactory.getLogger("testLogger");

//    private static final Long dbId = 7130561L; //381283L; // 75949L;
//    private static final String stId = "R-HSA-5205685";

//    private static final Long dbId = 5205685L;
//    private static final String stId = "R-HSA-5205685";

    private static final Long dbId = 442726l;//1368140l;//507868L;
//    private static final String stId = "R-HSA-507868";

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EventService eventService;

    @Autowired
    private GenericService genericService;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running DatabaseObjectServiceTests !!! --- \n");
    }

    @Before
    public void setUp() throws Exception {
        assumeTrue(genericService.fitForService());
        genericService.clearCache();
        DatabaseObjectFactory.clearCache();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFindByDbId() throws Exception {
        logger.info("Started testing databaseObjectService.findByDbId");
        long start, time;

        start = System.currentTimeMillis();
        DatabaseObject databaseObjectObserved = eventService.findByIdWithLegacyFields(dbId.toString());
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

//        DatabaseObjectFactory.clearCache();
//
//        start = System.currentTimeMillis();
//        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(dbId.toString());
//        databaseObjectExpected.load();
//        time = System.currentTimeMillis() - start;
//        logger.info("GkInstance execution time: " + time + "ms");

//        assertTrue(databaseObjectExpected.equals(databaseObjectObserved));
    }
}
