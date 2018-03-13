package org.reactome.server.graph.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.reactome.server.graph.util.JunitHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
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

        //*******   ENABLING LAZY LOADING FOR A PROPER TESTING  *********
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

    @Test
    @Deprecated
    @SuppressWarnings("deprecation")
    public void lazyLoadingPositiveAndNegativeRegulatorsTest() {
        logger.info("Testing Lazy Loading Positive And Negative Regulators");

        Event p = dbs.findById("R-HSA-71670");

        assumeFalse(p.getPositivelyRegulatedBy().isEmpty());
        for (DatabaseObject positiveRegulation : p.getPositivelyRegulatedBy()) {
            assumeTrue(positiveRegulation instanceof PositiveRegulation);
        }

        assumeFalse(p.getNegativelyRegulatedBy().isEmpty());
        for (DatabaseObject negativeRegulation : p.getNegativelyRegulatedBy()) {
            assumeTrue(negativeRegulation instanceof NegativeRegulation);
        }

        logger.info("Finished");
    }

    @Test
    public void lazyLoadingRepeatedUnitOfTest() {

        logger.info("Testing Lazy Loading for Polymer RepeatedUnit");

        PhysicalEntity dbObj = dbs.findByIdNoRelations("R-HSA-5682854");
        List<Polymer> targets = dbObj.getRepeatedUnitOf();

        boolean found = false;
        for (Polymer polymer : targets) {
            found |= polymer.getStId().equals("R-HSA-5682839");
        }
        assertTrue("'R-HSA-5682839' has 'R-HSA-5682854' as repeated Unit", found);

        logger.info("Finished");
    }

    @Test
    public void lazyLoadingComponentOfTest() {

        logger.info("Testing Lazy Loading for Complex ComponentOf");

        PhysicalEntity dbObj = dbs.findByIdNoRelations("R-HSA-377733");
        List<Complex> targets = dbObj.getComponentOf();

        boolean found = false;
        for (Complex complex : targets) {
            found |= complex.getStId().equals("R-HSA-375305");
        }
        assertTrue("'R-HSA-375305' has 'R-HSA-377733' as hasComponent", found);

        logger.info("Finished");
    }
}