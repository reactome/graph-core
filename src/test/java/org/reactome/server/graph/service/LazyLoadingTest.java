package org.reactome.server.graph.service;

import org.gk.model.GKInstance;
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
    public void lazyLoadingTest() throws Exception {
        logger.info("Testing Lazy Loading.");

        DatabaseObject databaseObjectObserved = dbs.findByIdNoRelations(PhysicalEntities.complex.getStId());
        GKInstance databaseObjectExpected = DatabaseObjectFactory.createGKInstance(PhysicalEntities.complex);

        //getters will be automatically called by the Assertion test
        assertEquals(databaseObjectExpected.getDBID(), databaseObjectObserved.getDbId());
        logger.info("Finished");
    }

    @Test
    public void lazyLoadingRegulationsTest() {
        logger.info("Testing Lazy Loading Positive And Negative Regulators");

        //ReactionLikeEvent rle = dbs.findById("R-HSA-71670");
        ReactionLikeEvent rle = dbs.findByIdNoRelations(Events.transitionReaction.getStId());

        assumeFalse(rle.getRegulatedBy().isEmpty());
        List<PositiveRegulation> positiveRegulations = new ArrayList<>();
        for (Regulation regulation : rle.getRegulatedBy()) {
            if(regulation instanceof  PositiveRegulation){
                positiveRegulations.add((PositiveRegulation) regulation);
            }
        }
        assertFalse(positiveRegulations.isEmpty());

        logger.info("Finished");
    }

    @Test
    public void lazyLoadingRepeatedUnitOfTest() {
        logger.info("Testing Lazy Loading for Polymer RepeatedUnit");
        PhysicalEntity dbObj = dbs.findByIdNoRelations(PhysicalEntities.entityWithAccessionedSequence.getStId());

        assumeFalse(dbObj.getRepeatedUnitOf().isEmpty());
        List<Polymer> targets = dbObj.getRepeatedUnitOf();
        assertFalse(targets.isEmpty());
        logger.info("Finished");
    }

    @Test
    public void lazyLoadingComponentOfTest() {
        logger.info("Testing Lazy Loading for Complex ComponentOf");
        PhysicalEntity dbObj = dbs.findByIdNoRelations(PhysicalEntities.entityWithAccessionedSequence.getStId());
        List<Complex> targets = dbObj.getComponentOf();
        assertThat(targets).contains(new Complex(PhysicalEntities.complex.getDbId()));
        logger.info("Finished");
    }

    @Test
    public void lazyLoadingConsumedByEventTest() {
        logger.info("Testing Lazy Loading for Complex ComponentOf");
        PhysicalEntity dbObj = dbs.findByIdNoRelations(PhysicalEntities.complex.getStId());
        List<ReactionLikeEvent> targets = dbObj.getConsumedByEvent();
        assertThat(targets).contains(new Reaction(Events.associationReaction.getDbId()));
        logger.info("Finished");
    }

    @Test
    public void lazyLoadingHasModifiedResidueTest(){
        logger.info("Testing Lazy Loading for EWAS HasModifiedResidue");

        long start, time;
        start = System.currentTimeMillis();
        EntityWithAccessionedSequence ewas = dbs.findByIdNoRelations(PhysicalEntities.entityWithAccessionedSequence.getStId());
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
        ReactionLikeEvent rle = dbs.findById(Events.associationReaction.getStId());
        logger.info("GraphDb execution time: " + (System.currentTimeMillis() - start) + "ms");

        assertFalse(rle.getEventOf().isEmpty(), "'R-HSA-5205661 is 'at least' an event of an association reaction");
        logger.info("Finished");
    }

    public void preloadReactionLikeEvent(){
        rle = dbs.findByIdNoRelations(Events.associationReaction.getStId());
    }

    @Test
    public void testRLEOutputs() {
        if (rle == null) preloadReactionLikeEvent();
        List<PhysicalEntity> outputs = rle.getOutput();
        assertThat(outputs).containsExactlyInAnyOrder(
                new Complex(PhysicalEntities.complex.getDbId())
        );
    }

    @Test
    public void testRLEInputs() {
        if (rle == null) preloadReactionLikeEvent();
        List<PhysicalEntity> inputs = rle.getInput();
        assertThat(inputs).containsExactlyInAnyOrder(
                new Complex(PhysicalEntities.complex.getDbId())
        );
    }

    @Test
    public void testRLECatalystActivity() {
        if (rle == null) preloadReactionLikeEvent();
        List<CatalystActivity> catact = rle.getCatalystActivity();
        assertThat(catact).containsExactlyInAnyOrder(
                new CatalystActivity(PhysicalEntities.catalystActivity.getDbId())
        );
    }

    @Test
    public void testRLEAuthored() {
        if (rle == null) preloadReactionLikeEvent();
        List<InstanceEdit> authors = rle.getAuthored();
        assertThat(authors).containsExactlyInAnyOrder(
                new InstanceEdit(instanceEdit.getDbId())
        );
    }

    @Test
    public void testRLEEventOf() {
        if (rle == null) preloadReactionLikeEvent();
        List<Pathway> events = rle.getEventOf();
        assertThat(events).containsExactlyInAnyOrder(
                new Pathway(Events.diagramPathway.getDbId())
        );
    }

    @Test
    public void testRLELiteratureReference() {
        if (rle == null) preloadReactionLikeEvent();
        List<Publication> pubs = rle.getLiteratureReference();
        assertThat(pubs).containsExactlyInAnyOrder(
                new LiteratureReference(testPublication.getDbId())
        );
        for (Publication pub : pubs) {
            pub.getAuthor();
        }
    }

    @Test
    public void testRLECreated() {
        if (rle == null) preloadReactionLikeEvent();
        InstanceEdit created = rle.getCreated();
        assertThat(created).isEqualTo(new InstanceEdit(instanceEdit.getDbId()));
    }

    @Test
    public void testRLEModified() {
        if (rle == null) preloadReactionLikeEvent();
        InstanceEdit modified = rle.getModified();
        assertThat(modified).isEqualTo(new InstanceEdit(instanceEdit.getDbId()));
    }

    @Test
    public void testCandidateSet(){
        CandidateSet candidateSet = dbs.findByIdNoRelations(PhysicalEntities.candidateSet.getStId());
        assertFalse(candidateSet.getHasCandidate().isEmpty());
        assertFalse(candidateSet.getHasMember().isEmpty());
    }

}