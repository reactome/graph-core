package org.reactome.server.graph.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.aop.SortingAspect;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class SortingAspectsTest extends BaseTest {

    private static final String stId = "R-HSA-2466381";

    @Autowired
    private DatabaseObjectService dbs;

    @Autowired
    private SortingAspect sortingAspect;

    @BeforeTestClass
    public void setUpClass() {
        logger.info(" --- !!! Running " + SortingAspectsTest.class.getName() + "!!! --- \n");
    }

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = fitForService();
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

    @AfterEach
    public void setDown(){
        sortingAspect.setEnableSorting(false);
    }
}