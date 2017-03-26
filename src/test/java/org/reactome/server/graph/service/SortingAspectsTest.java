package org.reactome.server.graph.service;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.aop.SortingAspect;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 * @since 14.06.16.
 */
public class SortingAspectsTest extends BaseTest {

    private static final String stId = "R-HSA-2466381";

    @Autowired
    private DatabaseObjectService dbs;

    @Autowired
    private SortingAspect sortingAspect;

    @BeforeClass
    public static void setUpClass() {
        logger.info(" --- !!! Running " + SortingAspectsTest.class.getName() + "!!! --- \n");
    }

    @Override
    @Before
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = generalService.fitForService();
            checkedOnce = true;
        }

        //********   ENABLING SORTING ASPECT FOR A PROPER TESTING  *********
        sortingAspect.setEnableSorting(true);

        assumeTrue(isFit);
        DatabaseObjectFactory.clearCache();
    }

    @Test
    public void sortingTest() throws InvocationTargetException, IllegalAccessException {
        logger.info("Testing AOP Sorting");
        Complex databaseObjectObserved =  dbs.findById(stId);
        assertTrue(isSorted(databaseObjectObserved.getHasComponent()));
        logger.info("Finished");
    }

    private boolean isSorted(Collection<? extends DatabaseObject> databaseObjects) {
        DatabaseObject prev = null;
        for (DatabaseObject elem : databaseObjects) {
            if (prev != null && prev.getDisplayName().compareTo(elem.getDisplayName()) > 0) {
                return false;
            }
            prev = elem;
        }
        return true;
    }

    @After
    public void setDown(){
        sortingAspect.setEnableSorting(false);
    }
}