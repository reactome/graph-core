package org.reactome.server.graph.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.reactome.server.graph.aop.LazyFetchAspect;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.domain.relationship.Input;
import org.reactome.server.graph.domain.relationship.Output;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.reactome.server.graph.util.TestNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SpringBootTest
public abstract class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger("testLogger");
    protected @Autowired TestNodeService testService;

    protected static Species testSpecies;

    static Boolean checkedOnce = false;
    static Boolean isFit = false;

    @Autowired
    protected Neo4jClient neo4jClient;

    @Autowired
    protected LazyFetchAspect lazyFetchAspect;

    @AfterTestClass
    public void tearDownClass() {
        logger.info("\n\n");
    }


    @AfterAll
    public static void deleteTestData(@Autowired TestNodeService nodeService) {
//        nodeService.deleteTest();
    }

    @BeforeAll
    public static void createTestData(@Autowired TestNodeService testService) {

        //region Create Top Level Pathway
        TopLevelPathway topLevelPathway = new TopLevelPathway();
        topLevelPathway.setDisplayName("Test Top Level Pathway");
        topLevelPathway.setHasEHLD(true);
        //endregion

        //region Create Pathway with EHLD set true
        Pathway ehldPathway = new Pathway();
        ehldPathway.setDisplayName("Test EHLD Pathway");
        ehldPathway.setHasEHLD(true);
        topLevelPathway.setHasEvent(List.of(topLevelPathway));
        //endregion

        //region Create Pathway with diagram
        Pathway diagramPathway = new Pathway();
        diagramPathway.setDisplayName("Test Diagram Pathway");
        diagramPathway.setHasEHLD(false);
        diagramPathway.setHasDiagram(true);
        ehldPathway.setHasEvent(List.of(diagramPathway));
        //endregion

        //region Create Test Reactions
        Reaction reactionAssociation = new Reaction();
        reactionAssociation.setDisplayName("Test Reaction (Association)");
        reactionAssociation.setCategory("association");

        // Dissociation
        Reaction reactionDissociation = new Reaction();
        reactionDissociation.setDisplayName("Test Reaction (Dissociation)");
        reactionDissociation.setCategory("dissociation");

        // Transition
        Reaction reactionTransition = new Reaction();
        reactionTransition.setDisplayName("Test Reaction (Transition)");
        reactionTransition.setCategory("transition");

        // Binding
        Reaction reactionBinding = new Reaction();
        reactionBinding.setDisplayName("Test Reaction (Binding)");
        reactionBinding.setCategory("binding");

        // Polymerisation
        Polymerisation reactionPolymerisation = new Polymerisation();
        reactionPolymerisation.setDisplayName("Test Reaction (Polymerisation)");
        reactionPolymerisation.setCategory("transition");

        // Depolymerisation
        Depolymerisation reactionDepolymerisation = new Depolymerisation();
        reactionDepolymerisation.setDisplayName("Test Reaction (Depolymerisation)");
        reactionDepolymerisation.setCategory("transition");

        // BlackBoxEvent
        BlackBoxEvent reactionBlackBoxEvent = new BlackBoxEvent();
        reactionBlackBoxEvent.setDisplayName("Test Reaction (BlackBox Event)");
        reactionBlackBoxEvent.setCategory("omitted");

        // CellDevelopmentStep
        CellDevelopmentStep reactionCellDevelopmentStep = new CellDevelopmentStep();
        reactionCellDevelopmentStep.setDisplayName("Test Reaction (CellDevelopment Step)");
        reactionCellDevelopmentStep.setCategory("transition");

        // Failed Reaction
        FailedReaction reactionFailedReaction = new FailedReaction();
        reactionFailedReaction.setDisplayName("Test Reaction (FailedReaction)");
        reactionFailedReaction.setCategory("transition");

        diagramPathway.setHasEvent(List.of(reactionAssociation, reactionDissociation, reactionTransition,
                reactionBinding, reactionPolymerisation, reactionDepolymerisation, reactionBlackBoxEvent,
                reactionCellDevelopmentStep, reactionFailedReaction));
        testService.saveTest(diagramPathway);
        //endregion

        //region Create In/Output for Reaction
        PhysicalEntity inputSimpleEntity1 = new SimpleEntity();
        inputSimpleEntity1.setDisplayName("Simple Entity 1");

        PhysicalEntity inputSimpleEntity2 = new SimpleEntity();
        inputSimpleEntity2.setDisplayName("Simple Entity 2");

        PhysicalEntity outputSimpleEntity = new SimpleEntity();
        outputSimpleEntity.setDisplayName("Simple Entity 3");

        PhysicalEntity outputGenomeEntity = new GenomeEncodedEntity();
        outputGenomeEntity.setDisplayName("Simple Entity 4");

        // create complex for output
        Complex testComplex = new Complex();
        testComplex.setDisplayName("Test Complex");
        testComplex.setHasComponent(List.of(outputSimpleEntity, outputGenomeEntity));
        reactionAssociation.setOutput(List.of(testComplex));

        // create EWAS
        EntityWithAccessionedSequence testEWAS = new EntityWithAccessionedSequence();
        testEWAS.setDisplayName("Test Entity With Accessioned Sequence");
        testComplex.setHasComponent(List.of(testEWAS));
        reactionAssociation.setInput(List.of(testComplex));
        //endregion

        //region Create Catalyst Activity
        CatalystActivity testCatalystActivity = new CatalystActivity();
        testCatalystActivity.setDisplayName("Test Catalyst Activity");
        testCatalystActivity.setPhysicalEntity(testEWAS);
        testCatalystActivity.setCatalyzedEvent(List.of(reactionAssociation));
        testService.saveTest(testCatalystActivity);
        //endregion

        //region Create reference Protein
        ReferenceGeneProduct testReferenceEntity = new ReferenceGeneProduct();
        testReferenceEntity.setDisplayName("Test Refrence Entity");
        testReferenceEntity.setDatabaseName("Some protein DB");

        ReferenceDatabase referenceDatabase = new ReferenceDatabase();
        referenceDatabase.setDisplayName("Test Reference Database");
        testReferenceEntity.setReferenceDatabase(referenceDatabase);
        testEWAS.setReferenceEntity(testReferenceEntity);
        //endregion

        //region Branch CellLineage Pathway
        CellLineagePath cellLineagePath = new CellLineagePath();
        cellLineagePath.setDisplayName("Test Cell Lineage Path");

        CellDevelopmentStep cellDevelopmentStep = new CellDevelopmentStep();
        cellDevelopmentStep.setDisplayName("Test Cell Development Step");

        Cell developingCell = new Cell();
        developingCell.setDisplayName("developing cell");

        Cell developedCell = new Cell();
        developedCell.setDisplayName("developed cell");

        cellDevelopmentStep.setInput(List.of(developingCell));
        cellDevelopmentStep.setOutput(List.of(developedCell));
        cellLineagePath.setHasEvent(List.of(cellDevelopmentStep));
        topLevelPathway.setHasEvent(List.of(ehldPathway, cellLineagePath));
        //endregion

        testService.saveTest(topLevelPathway);
    }

    //EWAS proteins, genes, rna,
    private static EntityWithAccessionedSequence createEWAS(ReferenceSequence reference, Compartment compartment, Species species) {
        EntityWithAccessionedSequence physicalEntity = new EntityWithAccessionedSequence();
        physicalEntity.setReferenceEntity(reference);
        physicalEntity.setCompartment(List.of(compartment));
        physicalEntity.setSpecies(species);
        return physicalEntity;
    }

    @BeforeEach
    public void setUp() throws Exception {
        if (!checkedOnce) {
            isFit = fitForService();
            checkedOnce = true;
        }

        /* DISABLING LAZY LOADING FOR A PROPER TESTING */
        lazyFetchAspect.setEnableAOP(false);

        assumeTrue(isFit);
        DatabaseObjectFactory.clearCache();
    }

    protected final boolean fitForService() {
        String query = "MATCH (n) RETURN COUNT(n) > 0 AS fitForService";
        try {
            return neo4jClient.query(query).fetchAs(Boolean.class).first().orElse(false);
        } catch (Exception e) {
            logger.error("A connection with the Neo4j Graph could not be established. Tests will be skipped", e);
        }
        return false;
    }

}
