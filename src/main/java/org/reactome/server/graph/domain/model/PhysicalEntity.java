package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.reactome.server.graph.domain.annotations.*;
import org.reactome.server.graph.domain.relationship.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@SuppressWarnings("unused")
@Node
public abstract class PhysicalEntity extends DatabaseObject implements Trackable, Deletable {

    @ReactomeProperty
    private String definition;
    //A simple flag to indicate if this PhysicalEntity object is a disease
    @ReactomeProperty(addedField = true)
    private Boolean isInDisease;
    @ReactomeProperty
    private List<String> name;
    @ReactomeProperty(addedField = true)
    private String speciesName;
    @ReactomeProperty
    private String systematicName;

    @Relationship(type = "authored", direction = Relationship.Direction.INCOMING)
    private InstanceEdit authored;

    @ReactomeTransient
    @Relationship(type = "physicalEntity", direction = Relationship.Direction.INCOMING)
    private List<CatalystActivity> catalystActivities;

    @Relationship(type = "compartment")
    private SortedSet<HasCompartment> compartment;

    @ReactomeTransient
    @Relationship(type = "hasComponent", direction = Relationship.Direction.INCOMING)
    private SortedSet<ComponentOf> componentOf;

    @Relationship(type = "crossReference")
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = "disease")
    private List<Disease> disease;

    @Relationship(type = "edited", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> edited;

    @Relationship(type = "figure")
    private List<Figure> figure;

    @Relationship(type = "goCellularComponent")
    private GO_CellularComponent goCellularComponent;

    @Relationship(type = "inferredTo")
    private List<PhysicalEntity> inferredTo;

    @ReactomeTransient
    @Relationship(type = "inferredTo", direction = Relationship.Direction.INCOMING)
    private List<PhysicalEntity> inferredFrom;

    @ReactomeTransient
    @Relationship(type = "regulator", direction = Relationship.Direction.INCOMING)
    private List<Requirement> isRequired;

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    @ReactomeTransient
    @Relationship(type = "hasMember", direction = Relationship.Direction.INCOMING)
    private SortedSet<MemberOf> memberOf;

    @ReactomeTransient
    @Relationship(type = "hasCandidate", direction = Relationship.Direction.INCOMING)
    private SortedSet<CandidateOf> candidateOf;

    /**
     * negativelyRegulates is not a field of the previous RestfulApi and will be ignored until needed
     */
    @ReactomeTransient
    @Relationship(type = "regulator", direction = Relationship.Direction.INCOMING)
    private List<NegativeRegulation> negativelyRegulates;


    @ReactomeTransient
    @Relationship(type = "regulator", direction = Relationship.Direction.INCOMING)
    private List<PositiveRegulation> positivelyRegulates;

    @ReactomeTransient
    @Relationship(type = "repeatedUnit", direction = Relationship.Direction.INCOMING)
    private Set<RepeatedUnitForPhysicalEntity> repeatedUnitOf;

    @ReactomeTransient
    @Relationship(type = "input", direction = Relationship.Direction.INCOMING)
    private List<InputForReactionLikeEvent> consumedByEvent;

    @ReactomeTransient
    @Relationship(type = "output", direction = Relationship.Direction.INCOMING)
    private List<OutputForReactionLikeEvent> producedByEvent;

    @Relationship(type = "reviewed", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> reviewed;

    @Relationship(type = "revised", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> revised;

    @Relationship(type = "summation")
    private List<Summation> summation;

    @Relationship(type = "cellType")
    private List<CellType> cellType;

    @ReactomeTransient
    @Relationship(type = "marker", direction = Relationship.Direction.INCOMING)
    private List<MarkerReference> markingReferences;

    @ReactomeTransient
    @Relationship(type = "replacementInstances", direction = Relationship.Direction.INCOMING)
    private List<Deleted> deleted;

    @ReactomeTransient
    @Relationship(type = "updatedInstance", direction = Relationship.Direction.INCOMING)
    private List<UpdateTracker> updateTrackers;

    public PhysicalEntity() {}

    public PhysicalEntity(Long dbId) {
        super(dbId);
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public Boolean getInDisease() {
        return isInDisease;
    }

    public void setInDisease(Boolean inDisease) {
        isInDisease = inDisease;
    }

    public String getSystematicName() {
        return systematicName;
    }

    public void setSystematicName(String systematicName) {
        this.systematicName = systematicName;
    }

    @ReactomeSchemaIgnore
    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public InstanceEdit getAuthored() {
        return authored;
    }

    public void setAuthored(InstanceEdit authored) {
        this.authored = authored;
    }

    public List<CatalystActivity> getCatalystActivities() {
        return catalystActivities;
    }

    public void setCatalystActivities(List<CatalystActivity> catalystActivities) {
        this.catalystActivities = catalystActivities;
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    @ReactomeProjectedRelationship("getCompartment")
    public SortedSet<HasCompartment> getHasCompartment() {
        return this.compartment;
    }

    @JsonView(StoichiometryView.Nested.class)
    public void setHasCompartment(SortedSet<HasCompartment> compartment) {
        this.compartment = compartment;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<Compartment> getCompartment() {
        return Has.Util.expandStoichiometry(compartment);
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setCompartment(List<Compartment> compartment) {
        this.compartment = Has.Util.aggregateStoichiometry(compartment, HasCompartment::new);
    }

    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }

    public List<Disease> getDisease() {
        return disease;
    }

    public void setDisease(List<Disease> disease) {
        this.disease = disease;
    }

    public List<InstanceEdit> getEdited() {
        return edited;
    }

    public void setEdited(List<InstanceEdit> edited) {
        this.edited = edited;
    }

    public List<Figure> getFigure() {
        return figure;
    }

    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }

    public GO_CellularComponent getGoCellularComponent() {
        return goCellularComponent;
    }

    public void setGoCellularComponent(GO_CellularComponent goCellularComponent) {
        this.goCellularComponent = goCellularComponent;
    }

    public List<PhysicalEntity> getInferredTo() {
        return inferredTo;
    }

    public void setInferredTo(List<PhysicalEntity> inferredTo) {
        this.inferredTo = inferredTo;
    }

    public List<PhysicalEntity> getInferredFrom() {
        return inferredFrom;
    }

    public void setInferredFrom(List<PhysicalEntity> inferredFrom) {
        this.inferredFrom = inferredFrom;
    }

    public List<Requirement> getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(List<Requirement> isRequired) {
        this.isRequired = isRequired;
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    public void setRepeatedUnitOf(Set<RepeatedUnitForPhysicalEntity> repeatedUnitOf) {
        this.repeatedUnitOf = repeatedUnitOf;
    }
    @JsonView(StoichiometryView.Flatten.class)
    public void setRepeatedUnitOf(List<Polymer> repeatedUnitOf) {
        this.repeatedUnitOf = Has.Util.aggregateStoichiometry(repeatedUnitOf, RepeatedUnitForPhysicalEntity::new);
    }

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }

    public List<NegativeRegulation> getNegativelyRegulates() {
        return negativelyRegulates;
    }

    public void setNegativelyRegulates(List<NegativeRegulation> negativelyRegulates) {
        this.negativelyRegulates = negativelyRegulates;
    }

    public List<PositiveRegulation> getPositivelyRegulates() {
        return positivelyRegulates;
    }

    public void setPositivelyRegulates(List<PositiveRegulation> positivelyRegulates) {
        this.positivelyRegulates = positivelyRegulates;
    }

    public List<InstanceEdit> getReviewed() {
        return reviewed;
    }

    public void setReviewed(List<InstanceEdit> reviewed) {
        this.reviewed = reviewed;
    }

    public List<InstanceEdit> getRevised() {
        return revised;
    }

    public void setRevised(List<InstanceEdit> revised) {
        this.revised = revised;
    }

    public List<Summation> getSummation() {
        return summation;
    }

    public void setSummation(List<Summation> summation) {
        this.summation = summation;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<Polymer> getRepeatedUnitOf() {
        return Has.Util.expandStoichiometry(repeatedUnitOf);
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    @ReactomeProjectedRelationship("getRepeatedUnitOf")
    public Set<RepeatedUnitForPhysicalEntity> getRepeatedUnitOfPolymers() {
        return repeatedUnitOf;
    }

    // ComponentOf
    @JsonView(StoichiometryView.Flatten.class)
    public List<Complex> getComponentOf() {
        return Has.Util.expandStoichiometry(componentOf);
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    @ReactomeProjectedRelationship("getComponentOf")
    public SortedSet<ComponentOf> getComponentOfComplexes() {
        return componentOf;
    }

    @JsonView(StoichiometryView.Nested.class)
    public void setComponentOfComplexes(SortedSet<ComponentOf> componentOf) {
        this.componentOf = componentOf;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setComponentOf(List<Complex> componentOf) {
        this.componentOf = Has.Util.aggregateStoichiometry(componentOf, ComponentOf::new);
    }

    // MemberOf
    @JsonView(StoichiometryView.Flatten.class)
    public List<EntitySet> getMemberOf() {
        return Has.Util.expandStoichiometry(memberOf);
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    @ReactomeProjectedRelationship("getMemberOf")
    public SortedSet<MemberOf> getMemberOfSet() {
        return memberOf;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setMemberOf(List<EntitySet> memberOf) {
        this.memberOf = Has.Util.aggregateStoichiometry(memberOf, MemberOf::new);
    }

    @JsonView(StoichiometryView.Nested.class)
    public void setMemberOfSet(SortedSet<MemberOf> memberOf) {
        this.memberOf = memberOf;
    }

    // CandidateOf
    @JsonView(StoichiometryView.Flatten.class)
    public List<CandidateSet> getCandidateOf() {
        return Has.Util.expandStoichiometry(candidateOf);
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    @ReactomeProjectedRelationship("getCandidateOf")
    public SortedSet<CandidateOf> getCandidateOfSet() {
        return candidateOf;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setCandidateOf(List<CandidateSet> candidateOf) {
        this.candidateOf = Has.Util.aggregateStoichiometry(candidateOf, CandidateOf::new);
    }

    @JsonView(StoichiometryView.Nested.class)
    public void setCandidateOfSet(SortedSet<CandidateOf> candidateOf) {
        this.candidateOf = candidateOf;
    }

    // Inputs / Outputs

    @JsonView(StoichiometryView.Flatten.class)
    public List<ReactionLikeEvent> getConsumedByEvent() {
        return Has.Util.expandStoichiometry(consumedByEvent);
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    public void setConsumedByEvent(List<ReactionLikeEvent> consumedByEvent) {
        this.consumedByEvent = new ArrayList<>(Has.Util.aggregateStoichiometry(consumedByEvent, InputForReactionLikeEvent::new));
    }


    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    @ReactomeProjectedRelationship("getConsumedByEvent")
    public List<InputForReactionLikeEvent> getInputFor() {
        return consumedByEvent;
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    public void setInputFor(List<InputForReactionLikeEvent> consumedByEvent) {
        this.consumedByEvent = consumedByEvent;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<ReactionLikeEvent> getProducedByEvent() {
        return Has.Util.expandStoichiometry(producedByEvent);
    }

    public void setProducedByEvent(List<ReactionLikeEvent> producedByEvent) {
        this.producedByEvent = new ArrayList<>(Has.Util.aggregateStoichiometry(producedByEvent, OutputForReactionLikeEvent::new));
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    @ReactomeProjectedRelationship("getProducedByEvent")
    public List<OutputForReactionLikeEvent> getOutputFor() {
        return producedByEvent;
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    public void setOutputFor(List<OutputForReactionLikeEvent> producedByEvent) {
        this.producedByEvent = producedByEvent;
    }

    public List<MarkerReference> getMarkingReferences() {
        return markingReferences != null ? new ArrayList<>(markingReferences) : null;
    }

    public void setMarkingReferences(List<MarkerReference> markingReferences) {
        this.markingReferences = markingReferences;
    }

    public List<CellType> getCellType() {
        return cellType;
    }

    public void setCellType(List<CellType> cellType) {
        this.cellType = cellType;
    }

    @Override
    public List<Deleted> getDeleted() {
        return deleted;
    }

    public void setDeleted(List<Deleted> deleted) {
        this.deleted = deleted;
    }

    @Override
    public List<UpdateTracker> getUpdateTrackers() {
        return updateTrackers;
    }

    public void setUpdateTrackers(List<UpdateTracker> updateTrackers) {
        this.updateTrackers = updateTrackers;
    }
}
