package org.reactome.server.graph.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.reactome.server.graph.aop.LazyFetchAspect;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.domain.relationship.AuthorPublication;
import org.reactome.server.graph.domain.relationship.PublicationAuthor;
import org.reactome.server.graph.util.DatabaseObjectFactory;
import org.reactome.server.graph.util.TestNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assumptions.assumeTrue;


/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SpringBootTest
public abstract class BaseTest {

        protected static class Events {
            public static TopLevelPathway topLevelPathway;
            public static Pathway ehldPathway;
            public static Pathway diagramPathway;

            public static CellLineagePath cellLineagePathway;

            public static Reaction associationReaction;
            public static Reaction dissociationReaction;
            public static Reaction transitionReaction;
            public static Reaction bindingReaction;

            public static Polymerisation polymerisationReaction;
            public static Depolymerisation depolymerisationReaction;

            public static BlackBoxEvent blackBoxEvent;
            public static CellDevelopmentStep cellDevelopmentStep;
            public static FailedReaction failedReaction;

            public static UndirectedInteraction undirectedInteraction;
        }

        protected static class PhysicalEntities{
            public static Complex complex;
            public static Complex complexInferred;
            public static EntityWithAccessionedSequence entityWithAccessionedSequence;
            public static CatalystActivity catalystActivity;
            public static PositiveRegulation positiveRegulation;
            public static NegativeRegulation negativeRegulation;
            public static Compartment compartment;

            public static FragmentModification fragmentDeletionModification;
            public static ReferenceSequence referenceSequence;

            public static ReferenceSequence referenceEntityInteraction;
            public static ReferenceSequence referenceEntityInteractor;

            public static EntityWithAccessionedSequence interactionEWAS;

            public static EntityWithAccessionedSequence ewasDepolymerisation;
            public static EntityWithAccessionedSequence entityWithAccessionedSequence2;

            public static ReferenceDatabase referenceDatabase;
        }

        protected static Person testPerson;
        protected static LiteratureReference testPublication;
        protected static InstanceEdit instanceEdit;

        protected static DeletedInstance deletedInstance;
        protected static Deleted deleted;
        protected static Species homoSapiensSpecies;
        protected static Species testSpecies;

        protected static UpdateTracker testUpdateTracker;

    protected static final Logger logger = LoggerFactory.getLogger("testLogger");

    @Autowired
    protected TestNodeService testService;

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
        nodeService.deleteTest();
    }

    @BeforeAll
    public static void createTestData(@Autowired TestNodeService testService) {

        //region
        testPerson = new Person();
        testPerson.setFirstname("FName");
        testPerson.setDisplayName("FName LName");
        testPerson.setSurname("LName");
        testPerson.setOrcidId("1234-1234-1234-1234");
        testPerson.setOldStId("1234-1234-1234-1234");
        testPerson.setProject("Pathway Curation");

        testPublication = new LiteratureReference();
        testPublication.setTitle("Reference Publication");
        testPublication.setDisplayName("Reference Publication Literature");
        testPublication.setJournal("Reference Publication");
        testPublication.setPages("4");
        testPublication.setAuthor(List.of(testPerson));
        instanceEdit = new InstanceEdit();
        instanceEdit.setDisplayName("Instance Edit");
        instanceEdit.setNote("Instance Edit");
        testService.saveTest(instanceEdit);
        testService.saveTest(testPublication);
        //endregion

        homoSapiensSpecies = new Species();
        homoSapiensSpecies.setDisplayName("Homo sapiens");
        homoSapiensSpecies.setName(List.of("Homo sapiens"));
        homoSapiensSpecies.setAbbreviation("HSA");

        testSpecies  = new Species();
        testSpecies.setDisplayName("Fantasy species");
        testSpecies.setName(List.of("Fantasy species"));
        testSpecies.setAbbreviation("HSA");

        //region Create Top Level Pathway
        Events.topLevelPathway = createTopLevelPathway("Test Top Level Pathway", true);
        Events.topLevelPathway.setSpecies(List.of(homoSapiensSpecies));
        Events.topLevelPathway.setSpeciesName("Homo sapiens");
        //endregion

        //region Create Pathway with EHLD set true
        Events.ehldPathway = createPathway("Test Ehld Pathway", true, true);
        Events.topLevelPathway.setHasEvent(List.of(Events.ehldPathway));
        Events.ehldPathway.setSpecies(List.of(homoSapiensSpecies));
        Events.ehldPathway.setSpeciesName("Homo sapiens");
        //endregion

        //region Create Pathway with diagram
        Events.diagramPathway = createPathway("Test Diagram Pathway",false,true);
        Events.ehldPathway.setHasEvent(List.of(Events.diagramPathway));
        Events.diagramPathway.setSpecies(List.of(homoSapiensSpecies));
        Events.diagramPathway.setSpeciesName("Homo sapiens");
        Events.diagramPathway.setIsInferred(true);
        //endregion

        //region Create Test Reactions
        Events.associationReaction = createReaction(ReactionType.ASSOCIATION,"Test Reaction (Association)");
        Events.associationReaction.setSpecies(List.of(homoSapiensSpecies));
        // Dissociation
        Events.dissociationReaction = createReaction(ReactionType.DISSOCIATION, "Test Reaction (Dissociation)");
        Events.dissociationReaction.setSpecies(List.of(homoSapiensSpecies));
        // Transition
        Events.transitionReaction = createReaction(ReactionType.TRANSITION, "Test Reaction (Transition)");
        Events.transitionReaction.setSpecies(List.of(homoSapiensSpecies));

        // Binding
        Events.bindingReaction = createReaction(ReactionType.BINDING, "Test Reaction (Binding)");
        Events.bindingReaction.setSpecies(List.of(homoSapiensSpecies));
        Events.transitionReaction.setInferredFrom(Set.of(Events.transitionReaction));

        // Polymerisation
        Events.polymerisationReaction = new Polymerisation();
        Events.polymerisationReaction.setCategory("polymerisation");
        Events.polymerisationReaction.setDisplayName("Test Reaction (Polymerisation)");
        Events.polymerisationReaction.setSpecies(List.of(homoSapiensSpecies));
        // Depolymerisation
        Events.depolymerisationReaction = new Depolymerisation();
        Events.depolymerisationReaction.setDisplayName("Test Reaction (Depolymerisation)");
        Events.depolymerisationReaction.setCategory("transition");
        Events.depolymerisationReaction.setSpecies(List.of(homoSapiensSpecies));
        PhysicalEntities.ewasDepolymerisation = createEwas("Test Ewas", "Homo sapiens");
        Events.depolymerisationReaction.setInput(List.of(PhysicalEntities.ewasDepolymerisation));
        Events.depolymerisationReaction.setSpecies(List.of(homoSapiensSpecies));


        // BlackBoxEvent
        Events.blackBoxEvent = new BlackBoxEvent();
        Events.blackBoxEvent.setDisplayName("Test Reaction (BlackBox Event)");
        Events.blackBoxEvent.setCategory("omitted");
        Events.blackBoxEvent.setSpecies(List.of(homoSapiensSpecies));
        // CellDevelopmentStep
        Events.cellDevelopmentStep = new CellDevelopmentStep();
        Events.cellDevelopmentStep.setDisplayName("Test Reaction (CellDevelopment Step)");
        Events.cellDevelopmentStep.setCategory("transition");
        Events.cellDevelopmentStep.setSpecies(List.of(homoSapiensSpecies));
        Events.diagramPathway.setInferredFrom(Set.of(Events.cellDevelopmentStep));

        // Failed Reaction
        Events.failedReaction = new FailedReaction();
        Events.failedReaction.setDisplayName("Test Reaction (FailedReaction)");
        Events.failedReaction.setCategory("transition");
        Events.failedReaction.setSpecies(List.of(homoSapiensSpecies));

        Events.diagramPathway.setHasEvent(List.of(Events.associationReaction, Events.dissociationReaction, Events.transitionReaction,
                Events.bindingReaction,Events.polymerisationReaction,Events.depolymerisationReaction,Events.blackBoxEvent,
                Events.cellDevelopmentStep,Events.failedReaction));

        PhysicalEntities.compartment = new Compartment();
        PhysicalEntities.compartment.setDisplayName("Test Compartment");
        Events.dissociationReaction.setCompartment(List.of(PhysicalEntities.compartment));
        //endregion

        //region Create In/Output for Reaction
        //create Complex
        PhysicalEntities.complex = createComplex("Test Complex", 4, List.of(homoSapiensSpecies));
        Events.associationReaction.setOutput(List.of(PhysicalEntities.complex));
        PhysicalEntities.complexInferred = createComplex("Inferred Test Complex", 8, List.of(homoSapiensSpecies));
        PhysicalEntities.complex.setInferredTo(List.of(PhysicalEntities.complexInferred));
        PhysicalEntities.complex.setSpecies(List.of(homoSapiensSpecies, testSpecies));

        // create EWAS
        PhysicalEntities.entityWithAccessionedSequence = createEwas("Test Entity With Accessioned Sequence", homoSapiensSpecies);
        PhysicalEntities.entityWithAccessionedSequence.setSpecies(homoSapiensSpecies);
        PhysicalEntities.complex.setHasComponent(List.of(PhysicalEntities.entityWithAccessionedSequence));
        Events.associationReaction.setInput(List.of(PhysicalEntities.complex));
        //endregion

        //region Create Positive Regulation
        PhysicalEntities.positiveRegulation = new PositiveRegulation();
        PhysicalEntities.positiveRegulation.setDisplayName("Test Positive Regulation");
        PhysicalEntities.entityWithAccessionedSequence.setPositivelyRegulates(List.of(PhysicalEntities.positiveRegulation));
        //endregion

        //region Create Catalyst Activity
        PhysicalEntities.catalystActivity = createTestCatalystActivity("Test Catalyst", PhysicalEntities.entityWithAccessionedSequence);
        PhysicalEntities.catalystActivity.setActiveUnit(Set.of(PhysicalEntities.entityWithAccessionedSequence));
        PhysicalEntities.entityWithAccessionedSequence.setCatalystActivities(List.of(PhysicalEntities.catalystActivity));
        Set<PhysicalEntity>set = PhysicalEntities.catalystActivity.getActiveUnit();
        Events.associationReaction.setCatalystActivity(List.of(PhysicalEntities.catalystActivity));
        //endregion

        PhysicalEntities.fragmentDeletionModification = createFragmentModification("Test Fragment Deletion Modification", FragmentModificationType.DELETION);
        PhysicalEntities.referenceSequence = createReferenceSequence("Test Ref");
        PhysicalEntities.referenceSequence.setIdentifier("TestIdentifier");
        PhysicalEntities.referenceSequence.setSpecies(homoSapiensSpecies);
        PhysicalEntities.fragmentDeletionModification.setReferenceSequence(PhysicalEntities.referenceSequence);
        testService.saveTest(PhysicalEntities.fragmentDeletionModification);
        PhysicalEntities.referenceDatabase = new ReferenceDatabase();
        PhysicalEntities.referenceDatabase.setDisplayName("Test Reference Database");
        PhysicalEntities.referenceSequence.setReferenceDatabase(PhysicalEntities.referenceDatabase);

        //region Branch CellLineage Pathway
        Events.cellLineagePathway = createCellLineagePath();
        Events.topLevelPathway.setHasEvent(List.of(Events.ehldPathway, Events.cellLineagePathway));
        Events.cellLineagePathway.setIsInferred(true);
        Events.cellLineagePathway.setInferredFrom(Set.of(Events.transitionReaction));
        Events.cellLineagePathway.setSpeciesName("Homo sapiens");
        Events.cellLineagePathway.setSpecies(List.of(homoSapiensSpecies));
        //endregion

        deletedInstance = createDeletedInstance();
        deleted = createDelete();
        deleted.setDeletedInstance(List.of(deletedInstance));

        PhysicalEntities.negativeRegulation = new NegativeRegulation();
        PhysicalEntities.negativeRegulation.setDisplayName("Test Negative Regulation");
        deleted.setReplacementInstances(List.of(PhysicalEntities.negativeRegulation));

        // Reference and Interactions
        PhysicalEntities.referenceEntityInteractor = createReferenceEntity("Test Reference Entity","Protein Test DB", "PROTTESTDB");
        PhysicalEntities.referenceEntityInteraction = createReferenceEntity("Interaction Ref Entity", "Prot Test DB", "PROTTESTDB");
        Events.undirectedInteraction = createInteraction(List.of(PhysicalEntities.referenceEntityInteractor, PhysicalEntities.referenceEntityInteraction));
        PhysicalEntities.interactionEWAS = createEwas("SomeEWAS", "Homo sapiens");
        PhysicalEntities.interactionEWAS.setReferenceEntity(PhysicalEntities.referenceSequence);
        testService.saveTest(Events.undirectedInteraction);
        //PhysicalEntities.interactionEWAS.setReferenceEntity(PhysicalEntities.referenceEntityInteraction);
        testService.saveTest(PhysicalEntities.interactionEWAS);
        testService.createRelationship(PhysicalEntities.interactionEWAS.getStId(), PhysicalEntities.referenceEntityInteraction.getStId(), "referenceEntity");

        testService.saveTest(deleted);
        testService.saveTest(Events.topLevelPathway);
        testService.createRelationship(PhysicalEntities.complex.getStId(), PhysicalEntities.referenceEntityInteractor.getStId(), "referenceEntity");
        testService.createRelationship( PhysicalEntities.ewasDepolymerisation.getStId(),  PhysicalEntities.referenceSequence.getStId(), "referenceEntity");
        testService.createRelationship(testPerson.getStId(), instanceEdit.getStId(), "author");
        testService.createRelationship(instanceEdit.getStId(),Events.diagramPathway.getStId(),"authored");
        testService.createRelationship(instanceEdit.getStId(), Events.depolymerisationReaction.getStId(),"authored");
        testService.createRelationship(instanceEdit.getStId(),Events.diagramPathway.getStId(),"reviewed");
        testService.createRelationship(instanceEdit.getStId(),Events.depolymerisationReaction.getStId(),"reviewed");
        testService.createRelationship(PhysicalEntities.referenceDatabase.getStId(),PhysicalEntities.referenceSequence.getStId(), "referenceDatabase");

        // add another ewas
        PhysicalEntities.entityWithAccessionedSequence2 = new EntityWithAccessionedSequence();
        PhysicalEntities.entityWithAccessionedSequence2.setDisplayName("Test Entity With Accessioned Sequence 2");
        testService.saveTest(PhysicalEntities.entityWithAccessionedSequence2);
        testService.createRelationship(PhysicalEntities.entityWithAccessionedSequence2.getStId(), PhysicalEntities.referenceSequence.getStId(), "referenceEntity");

        //Update Tracker
        testUpdateTracker = new UpdateTracker();
        testUpdateTracker.setDisplayName("Test Update Tracker");
        Release release = new Release();
        release.setReleaseNumber(1);
        testUpdateTracker.setRelease(release);
        testService.saveTest(testUpdateTracker);
        testService.createRelationship(testUpdateTracker.getStId(), Events.topLevelPathway.getStId(),"updatedInstance");
    }

    private static ReferenceSequence createReferenceEntity(String testReferenceEntity, String proteinTestDb, String prottestdb) {
        ReferenceSequence referenceEntity = new ReferenceGeneProduct();
        referenceEntity.setDisplayName(testReferenceEntity);
        referenceEntity.setDatabaseName(proteinTestDb);
        referenceEntity.setIdentifier(prottestdb);
        return referenceEntity;
    }

    protected static Deleted createDelete(){
        Deleted deleted = new Deleted();
        deleted.setDisplayName("Test Deleted");
        deleted.setCuratorComment("Test Comment");
        return deleted;
    }

    protected static DeletedInstance createDeletedInstance() {
        DeletedInstance deletedInstance = new DeletedInstance();
        deletedInstance.setName("Test Deleted Instance");
        deletedInstance.setClazz("Pathway");
        deletedInstance.setDeletedInstanceDbId(123450);
        deletedInstance.setDeletedStId("R-HSA-123450");
        return deletedInstance;
    }

    protected static FragmentModification createFragmentModification(String displayName, FragmentModificationType fragmentModificationType) {
        FragmentModification fragmentModification;

        switch (fragmentModificationType) {
            case DELETION:
                fragmentModification = new FragmentDeletionModification();
                break;
            case INSERTION:
                fragmentModification = new FragmentInsertionModification();
                break;
            case REPLACEMENT:
                fragmentModification = new FragmentReplacedModification();
                break;
            default:
                throw new IllegalArgumentException("Unsupported modification type: " + fragmentModificationType);
        }

        fragmentModification.setDisplayName(displayName);
        fragmentModification.setEndPositionInReferenceSequence(719);
        fragmentModification.setStartPositionInReferenceSequence(800);

        return fragmentModification;
    }

    protected static CellLineagePath createCellLineagePath(){
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
        return cellLineagePath;
    }

    protected static CatalystActivity createTestCatalystActivity(String displayName, EntityWithAccessionedSequence ewas){
        CatalystActivity catalystActivity = new CatalystActivity();
        catalystActivity.setDisplayName(displayName);
        ///catalystActivity.setActiveUnit(Set.of(ewas));
        catalystActivity.setPhysicalEntity(ewas);
        return catalystActivity;
    }

    protected static ReferenceSequence createReferenceSequence(String displayName){
        ReferenceSequence referenceSequence = new ReferenceRNASequence();
        referenceSequence.setDisplayName(displayName);
        referenceSequence.setDatabaseName(displayName);
        referenceSequence.setName(List.of(displayName));
        referenceSequence.setIdentifier(displayName);

        ReferenceDatabase referenceDatabase = new ReferenceDatabase();
        referenceDatabase.setDisplayName(displayName);
        referenceDatabase.setName(List.of(displayName));
        referenceSequence.setReferenceDatabase(referenceDatabase);
        return referenceSequence;
    }

    protected static EntityWithAccessionedSequence createEwas(String displayName, Species species){
        EntityWithAccessionedSequence testEWAS = new EntityWithAccessionedSequence();
        testEWAS.setDisplayName(displayName);
        testEWAS.setSpecies(species);
        testEWAS.setSpeciesName(species.getDisplayName());

        ReferenceSequence referenceSequence = new ReferenceRNASequence();
        referenceSequence.setDisplayName("Test Reference Database");
        referenceSequence.setDatabaseName("Some protein DB");
        referenceSequence.setIdentifier("Some protein ID");

        ReferenceDatabase referenceDatabase = new ReferenceDatabase();
        referenceDatabase.setDisplayName("Test Reference Database");
        referenceSequence.setReferenceDatabase(referenceDatabase);
        Interaction interaction = new UndirectedInteraction();
        interaction.setDisplayName("Test Interaction");
        interaction.setReferenceDatabase(referenceDatabase);

        testEWAS.setReferenceEntity(referenceSequence);
        return testEWAS;
    }

    protected static EntityWithAccessionedSequence createEwas(String displayName, String speciesName){
        EntityWithAccessionedSequence testEWAS = new EntityWithAccessionedSequence();
        testEWAS.setDisplayName(displayName);
        testEWAS.setSpeciesName(speciesName);
        return testEWAS;
    }

    protected static UndirectedInteraction createInteraction(List<ReferenceEntity> referenceEntities){
        UndirectedInteraction undirectedInteraction = new UndirectedInteraction();
        undirectedInteraction.setDisplayName("Test Interaction");
        undirectedInteraction.setInteractor(referenceEntities);
        undirectedInteraction.setDatabaseName("PROTTESTDB");
        return undirectedInteraction;
    }

    protected static Complex createComplex(String displayName, int noSimpleEntities, List<Species> species){
        List<PhysicalEntity>simpleEntities = new ArrayList<>();
        for (int i = 0; i < noSimpleEntities; i++) {
            PhysicalEntity simpleEntity = new SimpleEntity();
            simpleEntity.setDisplayName("Simple Entity " + i);
            simpleEntities.add(simpleEntity);
        }
        Complex complex = new Complex();
        complex.setDisplayName(displayName);
        complex.setHasComponent(simpleEntities);
        complex.setSpecies(species);
        return complex;
    }

    protected static Reaction createReaction(ReactionType reactionType, String displayName) {
        Reaction reaction = new Reaction();
        reaction.setDisplayName(displayName);
        switch (reactionType) {
            case ASSOCIATION:
                reaction.setCategory("association");
                break;
            case DISSOCIATION:
                reaction.setCategory("dissociation");
                break;
            case TRANSITION:
                reaction.setCategory("transition");
                break;
            case BINDING:
                reaction.setCategory("binding");
                break;
        }
        return reaction;
    }

    protected static Pathway createPathway(String displayName, Boolean ehld, Boolean diagram) {
        Pathway pathway = new Pathway();
        pathway.setDisplayName(displayName);
        pathway.setHasEHLD(ehld);
        pathway.setHasDiagram(diagram);
        pathway.setDiagramHeight(5);
        pathway.setDiagramWidth(5);
        pathway.setDoi("123.22.444");
        pathway.setSpeciesName("Homo Sapiens");
        return pathway;
    }

    protected static void createPathwayWithReferences(TestNodeService testService){
        Species testSpecies = new Species();
        testSpecies.setDisplayName("Test species");
        testSpecies.setName(List.of("Test species"));
        testSpecies.setAbbreviation("TST");
        testSpecies.setTaxId("12301");

        Pathway pathway = new Pathway();
        pathway.setHasEHLD(true);
        pathway.setHasDiagram(true);
        pathway.setSpecies(List.of(testSpecies));
        pathway.setDoi("123.22.444");
        pathway.setDisplayName("Test Pathway");

        ReactionLikeEvent reactionLikeEvent = new Reaction();
        reactionLikeEvent.setCategory("transition");
        reactionLikeEvent.setDisplayName("Display name");
        reactionLikeEvent.setSpecies(List.of(testSpecies));
        reactionLikeEvent.setIsChimeric(true);
        reactionLikeEvent.setSystematicName("Transition test");

        pathway.setHasEvent(List.of(reactionLikeEvent));

        SimpleEntity physicalEntity = new SimpleEntity();
        physicalEntity.setDisplayName("Physical Entity");
        physicalEntity.setDefinition("Display name");
        //physicalEntity.setSpecies(testSpecies);
        reactionLikeEvent.setInput(List.of(physicalEntity));

        ReferenceMolecule referenceEntity = new ReferenceMolecule();
        referenceEntity.setDisplayName("Molecule");
        referenceEntity.setDatabaseName("Test");
        referenceEntity.setIdentifier("123123123");
        physicalEntity.setReferenceEntity(referenceEntity);

        ReferenceDatabase referenceDatabase = new ReferenceDatabase();
        referenceDatabase.setDisplayName("Test");
        referenceDatabase.setName(List.of("Test"));
        referenceDatabase.setResourceIdentifier("12345");
        referenceEntity.setReferenceDatabase(referenceDatabase);

        testService.saveTest(pathway);
    }

    protected static void createSpecies(TestNodeService testService, String name){
        Species species = new Species();
        species.setDisplayName(name);
        species.setName(List.of(name));
        species.setAbbreviation("HSA");
        species.setTaxId("123");
        testService.saveTest(species);
    }

    protected static TopLevelPathway createTopLevelPathway(String displayName, Boolean ehld) {
        TopLevelPathway pathway = new TopLevelPathway();
        pathway.setDisplayName(displayName);
        pathway.setName(List.of(displayName));
        pathway.setStIdVersion("TST");
        pathway.setOldStId("REACT_TST");
        pathway.setDoi("123.22.444");
        //pathway.setSpecies(homoSapiensSpecies);
        pathway.setReleaseDate(LocalDate.now().toString());
        pathway.setReleaseStatus("Released");
        pathway.setHasDiagram(true);
        pathway.setHasEHLD(ehld);
        pathway.setDiagramHeight(5);
        pathway.setDiagramWidth(5);
        pathway.setIsInferred(true);
        pathway.setIsInDisease(true);
        pathway.setDefinition("Test top Level Pathway");
        pathway.setSpeciesName("Homo Sapiens");
        //pathway.setIsCanonical();
        return pathway;
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
