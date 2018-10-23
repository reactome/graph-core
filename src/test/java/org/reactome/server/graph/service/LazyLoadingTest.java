package org.reactome.server.graph.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.reactome.server.graph.util.JunitHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
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
    public void lazyLoadingRegulationsTest() {
        logger.info("Testing Lazy Loading Positive And Negative Regulators");

        ReactionLikeEvent rle = dbs.findById("R-HSA-71670");

        assumeFalse(rle.getRegulatedBy().isEmpty());
        List<PositiveRegulation> positiveRegulations = new ArrayList<>();
        List<NegativeRegulation> negativeRegulations = new ArrayList<>();
        for (Regulation regulation : rle.getRegulatedBy()) {
            if(regulation instanceof  PositiveRegulation){
                positiveRegulations.add((PositiveRegulation) regulation);
            } else {
                negativeRegulations.add((NegativeRegulation) regulation);
            }
        }
        assumeFalse(positiveRegulations.isEmpty());
        assumeFalse(negativeRegulations.isEmpty());

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

    @Test
    public void lazyLoadingConsumedByEventTest() {

        logger.info("Testing Lazy Loading for Complex ComponentOf");

        PhysicalEntity dbObj = dbs.findByIdNoRelations("R-HSA-375305");
        List<Event> targets = dbObj.getConsumedByEvent();

        boolean found = false;
        for (Event event : targets) {
            found |= event.getStId().equals("R-HSA-141409");
        }
        assertTrue("'R-HSA-375305' has 'R-HSA-141409' as input", found);

        logger.info("Finished");
    }

    @Test
    public void lazyLoadingHasModifiedResidueTest(){
        logger.info("Testing Lazy Loading for EWAS HasModifiedResidue");

        long start, time;
        start = System.currentTimeMillis();
        EntityWithAccessionedSequence ewas = dbs.findByIdNoRelations ("R-HSA-507936");
        time = System.currentTimeMillis() - start;
        logger.info("GraphDb execution time: " + time + "ms");

        List<AbstractModifiedResidue> amrs = ewas.getHasModifiedResidue();
        assertNotNull(amrs);
        assertFalse(amrs.isEmpty());

        logger.info("Finished");
    }

    @Test
    public void lazyLoadingEventOf(){
        logger.info("Started testing databaseObjectService.lazyLoadingEventOf");
        long start = System.currentTimeMillis();
        ReactionLikeEvent rle = dbs.findById("R-HSA-5205661");
        logger.info("GraphDb execution time: " + (System.currentTimeMillis() - start) + "ms");

        assertFalse("'R-HSA-5205661 is 'at least' an event of 'R-HSA-5205647'", rle.getEventOf().isEmpty());
        logger.info("Finished");
    }
}