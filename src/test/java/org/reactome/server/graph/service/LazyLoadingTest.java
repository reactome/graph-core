package org.reactome.server.graph.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

public class LazyLoadingTest extends BaseTest {

    private ReactionLikeEvent rle = null;
    private static final String stId = "R-HSA-446203";

    @Autowired
    private DatabaseObjectService dbs;

    @BeforeTestClass
    public  void setUpClass() {
        logger.info(" --- !!! Running " + LazyLoadingTest.class.getName() + "!!! --- \n");
    }

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = fitForService();
            checkedOnce = true;
        }

        //*******   ENABLING LAZY LOADING FOR A PROPER TESTING  *********
        lazyFetchAspect.setEnableAOP(true);

        assertTrue(isFit);
        DatabaseObjectFactory.clearCache();
    }

    @Test
    public void lazyLoadingTest() throws InvocationTargetException, IllegalAccessException {
        logger.info("Testing Lazy Loading.");

        DatabaseObject databaseObjectObserved = dbs.findByIdNoRelations(stId);
        DatabaseObject databaseObjectExpected = DatabaseObjectFactory.createObject(stId);

        //getters will be automatically called by the Assertion test
        assertThat(databaseObjectObserved).isEqualTo(databaseObjectExpected);
//        JunitHelper.assertDatabaseObjectsEqual(databaseObjectExpected, databaseObjectObserved);
        logger.info("Finished");
    }

    @Test
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
        assertTrue(positiveRegulations.size() >= 1);
        assertTrue(negativeRegulations.size() >= 3);

        logger.info("Finished");
    }

    @Test
    public void lazyLoadingRepeatedUnitOfTest() {
        logger.info("Testing Lazy Loading for Polymer RepeatedUnit");
        PhysicalEntity dbObj = dbs.findByIdNoRelations("R-HSA-202447");
        List<Polymer> targets = dbObj.getRepeatedUnitOf();
        assertThat(targets).contains(new Polymer(2685702L));
        logger.info("Finished");
    }

    @Test
    public void lazyLoadingComponentOfTest() {
        logger.info("Testing Lazy Loading for Complex ComponentOf");
        PhysicalEntity dbObj = dbs.findByIdNoRelations("R-HSA-377733");
        List<Complex> targets = dbObj.getComponentOf();
        assertThat(targets).contains(new Complex(375305L));
        logger.info("Finished");
    }

    @Test
    public void lazyLoadingConsumedByEventTest() {
        logger.info("Testing Lazy Loading for Complex ComponentOf");
        PhysicalEntity dbObj = dbs.findByIdNoRelations("R-HSA-375305");
        List<ReactionLikeEvent> targets = dbObj.getConsumedByEvent();
        assertThat(targets).contains(new Reaction(141409L));
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

        assertFalse(rle.getEventOf().isEmpty(), "'R-HSA-5205661 is 'at least' an event of 'R-HSA-5205647'");
        logger.info("Finished");
    }

    public void preloadReactionLikeEvent(){
        rle = dbs.findByIdNoRelations("R-HSA-3234081");
    }

    @Test
    public void testRLEOutputs() {
        if (rle == null) preloadReactionLikeEvent();
        List<PhysicalEntity> outputs = rle.getOutput();
        assertThat(outputs).containsExactlyInAnyOrder(
                new Complex(8865819L),
                new EntityWithAccessionedSequence(912481L),
                new EntityWithAccessionedSequence(912481L)
        );
    }

    @Test
    public void testRLEInputs() {
        if (rle == null) preloadReactionLikeEvent();
        List<PhysicalEntity> inputs = rle.getInput();
        assertThat(inputs).containsExactlyInAnyOrder(
                new Complex(2993783L),
                new Complex(2993783L),
                new Complex(8865818L)
        );
    }

    @Test
    public void testRLECatalystActivity() {
        if (rle == null) preloadReactionLikeEvent();
        List<CatalystActivity> catact = rle.getCatalystActivity();
        assertThat(catact).containsExactlyInAnyOrder(
                new CatalystActivity(2997650L)
        );
    }

    @Test
    public void testRLEAuthored() {
        if (rle == null) preloadReactionLikeEvent();
        List<InstanceEdit> authors = rle.getAuthored();
        assertThat(authors).containsExactlyInAnyOrder(
                new InstanceEdit(3232167L)
        );
    }

    @Test
    public void testRLEEventOf() {
        if (rle == null) preloadReactionLikeEvent();
        List<Pathway> events = rle.getEventOf();
        assertThat(events).containsExactlyInAnyOrder(
                new Pathway(3232118L),
                new Pathway(8866904L)
        );
    }

    @Test
    public void testRLELiteratureReference() {
        if (rle == null) preloadReactionLikeEvent();
        List<Publication> pubs = rle.getLiteratureReference();
        assertThat(pubs).containsExactlyInAnyOrder(
                new LiteratureReference(3234063L),
                new LiteratureReference(8874763L),
                new LiteratureReference(3234089L),
                new LiteratureReference(5626900L)
        );
        for (Publication pub : pubs) {
            pub.getAuthor();
        }
    }

    @Test
    public void testRLECreated() {
        if (rle == null) preloadReactionLikeEvent();
        InstanceEdit created = rle.getCreated();
        assertThat(created).isEqualTo(new InstanceEdit(3234098L));
    }

    @Test
    public void testRLEModified() {
        if (rle == null) preloadReactionLikeEvent();
        InstanceEdit modified = rle.getModified();
        assertThat(modified).isEqualTo(new InstanceEdit(9644119L));
    }

    @Test
    public void testCandidateSet(){
        CandidateSet candidateSet = dbs.findByIdNoRelations("R-ALL-9687688");
        assertTrue(candidateSet.getHasCandidate().size() >= 15);
        assertTrue(candidateSet.getHasMember().size() >= 2);
        assertNotEquals(candidateSet.getConsumedByEvent().size(), 0);
        assertThat(candidateSet.getCompartment()).contains(new Compartment(70101L));
    }

}