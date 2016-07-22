package org.reactome.server.graph.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.reactome.server.graph.util.JunitHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assume.assumeTrue;

/**
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 * @since 14.06.16.
 */
public class LazyLoadingTest extends BaseTest {

    private static final String stId = "R-HSA-446203";

    @Autowired
    private DatabaseObjectService dbs;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + LazyLoadingTest.class.getName() + "!!! --- \n");
    }

    @Override
    @Before
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = generalService.fitForService();
            checkedOnce = true;
        }

        /********   ENABLING LAZY LOADING FOR A PROPER TESTING  *********/
        lazyFetchAspect.setEnableAOP(true);

        assumeTrue(isFit);
        DatabaseObjectFactory.clearCache();
    }

    @Test
    public void lazyLoadingTest() throws InvocationTargetException, IllegalAccessException {

        logger.info("Testing Lazy Loading.");

        DatabaseObject databaseObjectObserved = dbs.findByIdNoRelations(stId);
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(stId);

        //getters will be automatically called by the Assertion test
        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

}