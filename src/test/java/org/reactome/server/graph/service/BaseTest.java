package org.reactome.server.graph.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.reactome.server.graph.aop.LazyFetchAspect;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.domain.relationship.HasCandidate;
import org.reactome.server.graph.util.ReactionType;
import org.reactome.server.graph.domain.relationship.HasMember;
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
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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

    protected static class PhysicalEntities {
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
        public static PositiveRegulation regulation;

        public static ModifiedResidue modifiedResidue;

        public static CandidateSet candidateSet;

        public static Polymer polymer;
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

        //region Entities
        testPerson = new Person();
        testPerson.setFirstname("FName");
        testPerson.setDisplayName("FName LName");
        testPerson.setSurname("LName");
        testPerson.setOrcidId("1234-1234-1234-1234");
        testPerson.setOldStId("1234-1234-1234-1234");
        testPerson.setProject("Pathway Curation");
        testService.saveTest(testPerson);

        testPublication = new LiteratureReference();
        testPublication.setTitle("Reference Publication");
        testPublication.setDisplayName("Reference Publication Literature");
        testPublication.setJournal("Reference Publication");
        testPublication.setPages("4");
        testService.saveTest(testPublication);

        instanceEdit = new InstanceEdit();
        instanceEdit.setDisplayName("Instance Edit");
        instanceEdit.setNote("Instance Edit");
        testService.saveTest(instanceEdit);

        homoSapiensSpecies = new Species();
        homoSapiensSpecies.setDisplayName("Homo sapiens");
        homoSapiensSpecies.setName(List.of("Homo sapiens"));
        homoSapiensSpecies.setAbbreviation("HSA");
        testService.saveTest(homoSapiensSpecies);

        testSpecies = new Species();
        testSpecies.setDisplayName("Fantasy species");
        testSpecies.setName(List.of("Fantasy species"));
        testSpecies.setAbbreviation("HSA");
        testSpecies.setTaxId("1234-1234-1234-1234");
        testService.saveTest(testSpecies);

        Events.topLevelPathway = createTopLevelPathway("Test Top Level Pathway", true);
        Events.topLevelPathway.setSpeciesName("Fantasy species");
        testService.saveTest(Events.topLevelPathway);

        Events.ehldPathway = createPathway("Test Ehld Pathway", true, true);
        testService.saveTest(Events.ehldPathway);

        Events.diagramPathway = createPathway("Test Diagram Pathway", false, true);
        Events.diagramPathway.setIsInferred(true);
        Events.diagramPathway.setSpeciesName("Homo sapiens");
        testService.saveTest(Events.diagramPathway);

        Events.associationReaction = createReaction(org.reactome.server.graph.util.ReactionType.ASSOCIATION, "Test Reaction (Association)");
        Events.associationReaction.setOldStId("REACT_123");
        testService.saveTest(Events.associationReaction);

        // Dissociation
        Events.dissociationReaction = createReaction(org.reactome.server.graph.util.ReactionType.DISSOCIATION, "Test Reaction (Dissociation)");
        testService.saveTest(Events.dissociationReaction);

        // Transition
        Events.transitionReaction = createReaction(org.reactome.server.graph.util.ReactionType.TRANSITION, "Test Reaction (Transition)");
        testService.saveTest(Events.transitionReaction);

        // Binding
        Events.bindingReaction = createReaction(org.reactome.server.graph.util.ReactionType.BINDING, "Test Reaction (Binding)");
        testService.saveTest(Events.bindingReaction);

        // Polymerisation
        Events.polymerisationReaction = new Polymerisation();
        Events.polymerisationReaction.setCategory("polymerisation");
        Events.polymerisationReaction.setDisplayName("Test Reaction (Polymerisation)");
        testService.saveTest(Events.polymerisationReaction);

        // Depolymerisation
        Events.depolymerisationReaction = new Depolymerisation();
        Events.depolymerisationReaction.setDisplayName("Test Reaction (Depolymerisation)");
        Events.depolymerisationReaction.setCategory("transition");
        testService.saveTest(Events.depolymerisationReaction);

        PhysicalEntities.ewasDepolymerisation = createEwas("Test Ewas", "Homo sapiens");
        testService.saveTest(PhysicalEntities.ewasDepolymerisation);

        Events.blackBoxEvent = new BlackBoxEvent();
        Events.blackBoxEvent.setDisplayName("Test Reaction (BlackBox Event)");
        Events.blackBoxEvent.setCategory("omitted");
        testService.saveTest(Events.blackBoxEvent);

        // CellDevelopmentStep
        Events.cellDevelopmentStep = new CellDevelopmentStep();
        Events.cellDevelopmentStep.setDisplayName("Test Reaction (CellDevelopment Step)");
        Events.cellDevelopmentStep.setCategory("transition");
        testService.saveTest(Events.cellDevelopmentStep);

        Events.failedReaction = new FailedReaction();
        Events.failedReaction.setDisplayName("Test Reaction (FailedReaction)");
        Events.failedReaction.setCategory("transition");
        testService.saveTest(Events.failedReaction);

        PhysicalEntities.compartment = new Compartment();
        PhysicalEntities.compartment.setDisplayName("Test Compartment");
        testService.saveTest(PhysicalEntities.compartment);

        PhysicalEntities.complex = createComplex("Test Complex", 4);
        testService.saveTest(PhysicalEntities.complex);
        PhysicalEntities.complexInferred = createComplex("Inferred Test Complex", 8);
        testService.saveTest(PhysicalEntities.complexInferred);

        PhysicalEntities.entityWithAccessionedSequence = createEwas("Test Entity With Accessioned Sequence");
        PhysicalEntities.entityWithAccessionedSequence.setSpeciesName("Homo sapiens");
        testService.saveTest(PhysicalEntities.entityWithAccessionedSequence);

        PhysicalEntities.positiveRegulation = new PositiveRegulation();
        PhysicalEntities.positiveRegulation.setDisplayName("Test Positive Regulation");
        testService.saveTest(PhysicalEntities.positiveRegulation);

        PhysicalEntities.regulation = new PositiveRegulation();
        PhysicalEntities.regulation.setDisplayName("Test Regulation (Positive Regulation)");
        testService.saveTest(PhysicalEntities.regulation);

        PhysicalEntities.catalystActivity = createTestCatalystActivity("Test Catalyst", PhysicalEntities.entityWithAccessionedSequence);
        testService.saveTest(PhysicalEntities.catalystActivity);

        PhysicalEntities.fragmentDeletionModification = createFragmentModification("Test Fragment Deletion Modification", FragmentModificationType.DELETION);
        testService.saveTest(PhysicalEntities.fragmentDeletionModification);

        PhysicalEntities.referenceSequence = createReferenceSequence("Test Ref");
        PhysicalEntities.referenceSequence.setIdentifier("TestIdentifier");
        testService.saveTest(PhysicalEntities.referenceSequence);

        PhysicalEntities.referenceDatabase = new ReferenceDatabase();
        PhysicalEntities.referenceDatabase.setDisplayName("Test Reference Database");
        testService.saveTest(PhysicalEntities.referenceDatabase);

        PhysicalEntities.modifiedResidue = new ModifiedResidue();
        PhysicalEntities.modifiedResidue.setDisplayName("Modified residue");
        PhysicalEntities.modifiedResidue.setLabel("R");
        testService.saveTest(PhysicalEntities.modifiedResidue);
        PhysicalEntities.polymer = new Polymer();
        PhysicalEntities.polymer.setDisplayName("Polymer");
        testService.saveTest(PhysicalEntities.polymer);

        // Cell Linaege Pathway Data
        Events.cellLineagePathway = createCellLineagePath();
        Events.cellLineagePathway.setIsInferred(true);
        Events.cellLineagePathway.setSpeciesName("Homo sapiens");
        testService.saveTest(Events.cellLineagePathway);

        deletedInstance = createDeletedInstance();
        testService.saveTest(deletedInstance);

        deleted = createDelete();
        testService.saveTest(deleted);

        PhysicalEntities.negativeRegulation = new NegativeRegulation();
        PhysicalEntities.negativeRegulation.setDisplayName("Test Negative Regulation");
        testService.saveTest(PhysicalEntities.negativeRegulation);

        // Reference and Interactions
        PhysicalEntities.referenceEntityInteractor = createReferenceEntity("Test Reference Entity", "Protein Test DB", "PROTTESTDB");
        testService.saveTest(PhysicalEntities.referenceEntityInteractor);

        PhysicalEntities.referenceEntityInteraction = createReferenceEntity("Interaction Ref Entity", "Prot Test DB", "PROTTESTDB");
        testService.saveTest(PhysicalEntities.referenceEntityInteraction);

        Events.undirectedInteraction = createInteraction(List.of(PhysicalEntities.referenceEntityInteractor, PhysicalEntities.referenceEntityInteraction));
        testService.saveTest(Events.undirectedInteraction);

        PhysicalEntities.interactionEWAS = createEwas("SomeEWAS", "Homo sapiens");
        testService.saveTest(PhysicalEntities.interactionEWAS);

        PhysicalEntities.interactionEWAS.setReferenceEntity(PhysicalEntities.referenceSequence);
        PhysicalEntities.candidateSet = new CandidateSet();
        PhysicalEntities.candidateSet.setDisplayName("Test CandidateSet");
        Drug chemicalDrug = new ChemicalDrug();
        chemicalDrug.setDisplayName("Drug");
        HasCandidate hasCandidate = new HasCandidate();
        hasCandidate.setPhysicalEntity(chemicalDrug);

        Drug chemicalDrug2 = new ChemicalDrug();
        chemicalDrug2.setDisplayName("Drug 2");
        HasCandidate hasCandidate2 = new HasCandidate();
        hasCandidate2.setPhysicalEntity(chemicalDrug2);

        SortedSet<HasCandidate>candidateSets = new TreeSet<>();
        candidateSets.add(hasCandidate);
        candidateSets.add(hasCandidate2);
        PhysicalEntities.candidateSet.setCandidates(candidateSets);
        SortedSet<HasMember>membersSet = new TreeSet<>();
        HasMember hasMember = new HasMember();
        hasMember.setPhysicalEntity(chemicalDrug);
        HasMember hasMember2 = new HasMember();
        hasMember.setPhysicalEntity(chemicalDrug2);

        membersSet.add(hasMember);
        membersSet.add(hasMember2);

        PhysicalEntities.candidateSet.setMembers(membersSet);
        testService.saveTest(PhysicalEntities.candidateSet);

        // add another ewas
        PhysicalEntities.entityWithAccessionedSequence2 = new EntityWithAccessionedSequence();
        PhysicalEntities.entityWithAccessionedSequence2.setDisplayName("Test Entity With Accessioned Sequence 2");
        testService.saveTest(PhysicalEntities.entityWithAccessionedSequence2);

        //Update Tracker
        testUpdateTracker = new UpdateTracker();
        testUpdateTracker.setDisplayName("Test Update Tracker");
        Release release = new Release();
        release.setReleaseNumber(1);
        testUpdateTracker.setRelease(release);
        testService.saveTest(testUpdateTracker);

        //endregion Entities

        //region Relationships
        testService.createRelationship(testPerson.getStId(), testPublication.getStId(), Relationships.AUTHOR, 1,1);
        testService.createRelationship(testPerson.getStId(), instanceEdit.getStId(), Relationships.AUTHOR,1,1);

        testService.createRelationship(Events.topLevelPathway.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.topLevelPathway.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.topLevelPathway.getStId(), Events.ehldPathway.getStId(), Relationships.HAS_EVENT,1,1);

        testService.createRelationship(Events.diagramPathway.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.diagramPathway.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);

        testService.createRelationship(Events.ehldPathway.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.ehldPathway.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.ehldPathway.getStId(), Events.diagramPathway.getStId(), Relationships.HAS_EVENT,1,1);

        testService.createRelationship(Events.associationReaction.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.associationReaction.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.associationReaction.getStId(), PhysicalEntities.positiveRegulation.getStId(), Relationships.REGULATED_BY,1,1);
        testService.createRelationship(Events.diagramPathway.getStId(), Events.associationReaction.getStId(), Relationships.HAS_EVENT,1,1);

        testService.createRelationship(Events.dissociationReaction.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.dissociationReaction.getStId(), testSpecies.getStId(),  Relationships.SPECIES,1,1);
        testService.createRelationship(Events.diagramPathway.getStId(), Events.dissociationReaction.getStId(), Relationships.HAS_EVENT,1,1);

        testService.createRelationship(Events.transitionReaction.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.transitionReaction.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.diagramPathway.getStId(), Events.transitionReaction.getStId(), Relationships.HAS_EVENT,1,1);

        testService.createRelationship(Events.bindingReaction.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.bindingReaction.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.diagramPathway.getStId(), Events.bindingReaction.getStId(), Relationships.HAS_EVENT,1,1);

        testService.createRelationship(Events.polymerisationReaction.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.polymerisationReaction.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.diagramPathway.getStId(), Events.polymerisationReaction.getStId(), Relationships.HAS_EVENT,1,1);

        testService.createRelationship(Events.depolymerisationReaction.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.depolymerisationReaction.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.diagramPathway.getStId(), Events.depolymerisationReaction.getStId(), Relationships.HAS_EVENT,1,1);

        testService.createRelationship(Events.depolymerisationReaction.getStId(), PhysicalEntities.ewasDepolymerisation.getStId(), Relationships.INPUT,1,1);
        testService.createRelationship(Events.blackBoxEvent.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.blackBoxEvent.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.diagramPathway.getStId(), Events.blackBoxEvent.getStId(), Relationships.HAS_EVENT,1,1);

        testService.createRelationship(Events.cellDevelopmentStep.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.cellDevelopmentStep.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);

        testService.createRelationship(Events.diagramPathway.getStId(), Events.cellDevelopmentStep.getStId(), Relationships.HAS_EVENT,1,1);
        testService.createRelationship(Events.diagramPathway.getStId(), Events.cellDevelopmentStep.getStId(), Relationships.INFERRED_TO,1,1);

        testService.createRelationship(Events.diagramPathway.getStId(), Events.failedReaction.getStId(), Relationships.HAS_EVENT,1,1);
        testService.createRelationship(Events.failedReaction.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.failedReaction.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);

        testService.createRelationship(Events.dissociationReaction.getStId(), PhysicalEntities.compartment.getStId(), Relationships.COMPARTMENT,1,1);
        testService.createRelationship(Events.associationReaction.getStId(), PhysicalEntities.complex.getStId(), Relationships.OUTPUT,1,1);
        testService.createRelationship(Events.associationReaction.getStId(), PhysicalEntities.complex.getStId(), Relationships.INPUT,1,1);

        testService.createRelationship(PhysicalEntities.complex.getStId(), PhysicalEntities.complexInferred.getStId(), Relationships.INFERRED_TO,1,1);
        testService.createRelationship(PhysicalEntities.complex.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(PhysicalEntities.complex.getStId(), testSpecies.getStId(), Relationships.SPECIES,1,1);

        testService.createRelationship(PhysicalEntities.entityWithAccessionedSequence.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(PhysicalEntities.complex.getStId(), PhysicalEntities.entityWithAccessionedSequence.getStId(), Relationships.HAS_COMPONENT,1,1);
        testService.createRelationship(PhysicalEntities.positiveRegulation.getStId(), PhysicalEntities.entityWithAccessionedSequence.getStId(), Relationships.REGULATOR,1,1);

        testService.createRelationship(PhysicalEntities.catalystActivity.getStId(), PhysicalEntities.entityWithAccessionedSequence.getStId(), Relationships.ACTIVE_UNIT,1,1);
        testService.createRelationship(Events.associationReaction.getStId(), PhysicalEntities.catalystActivity.getStId(), Relationships.CATALYST_ACTIVITY,1,1);

        testService.createRelationship(PhysicalEntities.referenceSequence.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(PhysicalEntities.fragmentDeletionModification.getStId(), PhysicalEntities.referenceSequence.getStId(), Relationships.REFERENCE_SEQUENCE,1,1);

        testService.createRelationship(PhysicalEntities.referenceSequence.getStId(), PhysicalEntities.referenceDatabase.getStId(), Relationships.REFERENCE_DATABASE,1,1);

        testService.createRelationship(Events.depolymerisationReaction.getStId(), PhysicalEntities.regulation.getStId(), Relationships.REGULATED_BY,1,1);
        testService.createRelationship(Events.transitionReaction.getStId(), PhysicalEntities.regulation.getStId(), Relationships.REGULATED_BY,1,1);

        testService.createRelationship(PhysicalEntities.positiveRegulation.getStId(), PhysicalEntities.entityWithAccessionedSequence.getStId(), Relationships.REPEATED_UNIT,0,1);
        testService.createRelationship(PhysicalEntities.entityWithAccessionedSequence.getStId(),PhysicalEntities.modifiedResidue.getStId(),Relationships.HAS_MODIFIED_RESIDUE,0,1);
        testService.createRelationship(PhysicalEntities.polymer.getStId(),PhysicalEntities.entityWithAccessionedSequence.getStId(),Relationships.REPEATED_UNIT,0,1);
        testService.createRelationship(Events.transitionReaction.getStId(), Events.cellLineagePathway.getStId(), Relationships.INFERRED_TO,1,1);
        testService.createRelationship(Events.cellLineagePathway.getStId(), homoSapiensSpecies.getStId(), Relationships.SPECIES,1,1);
        testService.createRelationship(Events.topLevelPathway.getStId(), Events.cellLineagePathway.getStId(), Relationships.HAS_EVENT,1,1);

        testService.createRelationship(deleted.getStId(), deletedInstance.getStId(), Relationships.DELETED_INSTANCE,1,1);
        testService.createRelationship(deleted.getStId(), PhysicalEntities.negativeRegulation.getStId(), Relationships.REPLACEMENT_INSTANCES,1,1);

        testService.createRelationship(PhysicalEntities.interactionEWAS.getStId(), PhysicalEntities.referenceSequence.getStId(), Relationships.REFERENCE_ENTITY,1,1);
        testService.createRelationship(PhysicalEntities.interactionEWAS.getStId(), PhysicalEntities.referenceEntityInteraction.getStId(), Relationships.REFERENCE_ENTITY,1,1);


        testService.createRelationship(PhysicalEntities.complex.getStId(), PhysicalEntities.referenceEntityInteractor.getStId(), Relationships.REFERENCE_ENTITY,1,1);
        testService.createRelationship(PhysicalEntities.ewasDepolymerisation.getStId(), PhysicalEntities.referenceSequence.getStId(), Relationships.REFERENCE_ENTITY,1,1);
        testService.createRelationship(testPerson.getStId(), instanceEdit.getStId(), Relationships.AUTHOR,1,1);
        testService.createRelationship(instanceEdit.getStId(), Events.diagramPathway.getStId(), Relationships.AUTHORED,1,1);
        testService.createRelationship(instanceEdit.getStId(), Events.depolymerisationReaction.getStId(), Relationships.AUTHORED,1,1);
        testService.createRelationship(instanceEdit.getStId(), Events.diagramPathway.getStId(), Relationships.REVIEWED,1,1);
        testService.createRelationship(instanceEdit.getStId(), Events.depolymerisationReaction.getStId(), Relationships.REVIEWED,1,1);
        testService.createRelationship(instanceEdit.getStId(), Events.associationReaction.getStId(), Relationships.AUTHORED,1,1);
        testService.createRelationship(Events.associationReaction.getStId(), testPublication.getStId(), Relationships.LITERATURE_REFERENCE,0,1);
        testService.createRelationship(instanceEdit.getStId(), Events.associationReaction.getStId(), Relationships.CREATED,0,1);
        testService.createRelationship(instanceEdit.getStId(), Events.associationReaction.getStId(), Relationships.MODIFIED,0,1);
        testService.createRelationship(PhysicalEntities.referenceDatabase.getStId(), PhysicalEntities.referenceSequence.getStId(), Relationships.REFERENCE_DATABASE,1,1);

        testService.createRelationship(PhysicalEntities.entityWithAccessionedSequence2.getStId(), PhysicalEntities.referenceSequence.getStId(), Relationships.REFERENCE_ENTITY,1,1);
        testService.createRelationship(PhysicalEntities.candidateSet.getStId(), PhysicalEntities.entityWithAccessionedSequence2.getStId(), Relationships.HAS_MEMBER,0,1);
        testService.createRelationship(testUpdateTracker.getStId(), Events.topLevelPathway.getStId(), Relationships.UPDATED_INSTANCES,1,1);
        //endregion Relationships

    }

    private static ReferenceSequence createReferenceEntity(String testReferenceEntity, String proteinTestDb, String prottestdb) {
        ReferenceSequence referenceEntity = new ReferenceGeneProduct();
        referenceEntity.setDisplayName(testReferenceEntity);
        referenceEntity.setDatabaseName(proteinTestDb);
        referenceEntity.setIdentifier(prottestdb);
        return referenceEntity;
    }

    protected static Deleted createDelete() {
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

    protected static CellLineagePath createCellLineagePath() {
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

    protected static CatalystActivity createTestCatalystActivity(String displayName, EntityWithAccessionedSequence ewas) {
        CatalystActivity catalystActivity = new CatalystActivity();
        catalystActivity.setDisplayName(displayName);
        catalystActivity.setPhysicalEntity(ewas);
        return catalystActivity;
    }

    protected static ReferenceSequence createReferenceSequence(String displayName) {
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

    protected static EntityWithAccessionedSequence createEwas(String displayName) {
        EntityWithAccessionedSequence testEWAS = new EntityWithAccessionedSequence();
        testEWAS.setDisplayName(displayName);

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

    protected static EntityWithAccessionedSequence createEwas(String displayName, String speciesName) {
        EntityWithAccessionedSequence testEWAS = new EntityWithAccessionedSequence();
        testEWAS.setDisplayName(displayName);
        testEWAS.setSpeciesName(speciesName);
        return testEWAS;
    }

    protected static UndirectedInteraction createInteraction(List<ReferenceEntity> referenceEntities) {
        UndirectedInteraction undirectedInteraction = new UndirectedInteraction();
        undirectedInteraction.setDisplayName("Test Interaction");
        undirectedInteraction.setInteractor(referenceEntities);
        undirectedInteraction.setDatabaseName("PROTTESTDB");
        return undirectedInteraction;
    }

    protected static Complex createComplex(String displayName, int noSimpleEntities) {
        List<PhysicalEntity> simpleEntities = new ArrayList<>();
        for (int i = 0; i < noSimpleEntities; i++) {
            PhysicalEntity simpleEntity = new SimpleEntity();
            simpleEntity.setDisplayName("Simple Entity " + i);
            simpleEntities.add(simpleEntity);
        }
        Complex complex = new Complex();
        complex.setDisplayName(displayName);
        complex.setHasComponent(simpleEntities);
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

    protected static void createPathwayWithReferences(TestNodeService testService) {
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
