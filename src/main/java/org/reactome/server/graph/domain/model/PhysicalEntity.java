package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;
import org.reactome.server.graph.domain.annotations.StoichiometryView;
import org.reactome.server.graph.domain.relationship.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.management.relation.Relation;
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

    @Relationship(type = Relationships.AUTHORED, direction = Relationship.Direction.INCOMING)
    private InstanceEdit authored;

    /**
     * catalystActivities is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = Relationships.PHYSICAL_ENTITY, direction = Relationship.Direction.INCOMING)
    private List<CatalystActivity> catalystActivities;

    @Relationship(type = Relationships.COMPARTMENT)
    private SortedSet<HasCompartment> compartment;

    /**
     * ComponentOf is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = Relationships.HAS_COMPONENT, direction = Relationship.Direction.INCOMING)
    private SortedSet<HasComponentForComplex> componentOf;

    @Relationship(type = Relationships.CROSS_REFERENCE)
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = Relationships.DISEASE)
    private List<Disease> disease;

    @Relationship(type = Relationships.EDITED, direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> edited;

    @Relationship(type = Relationships.FIGURE)
    private List<Figure> figure;

    @Relationship(type = Relationships.GO_CELLULAR_COMPONENT)
    private GO_CellularComponent goCellularComponent;

    @Relationship(type =  Relationships.INFERRED_TO)
    private List<PhysicalEntity> inferredTo;

    @ReactomeTransient
    @Relationship(type = Relationships.INFERRED_TO, direction = Relationship.Direction.INCOMING)
    private List<PhysicalEntity> inferredFrom;

    /**
     * isRequired is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = Relationships.REGULATOR, direction = Relationship.Direction.INCOMING)
    private List<Requirement> isRequired;

    @Relationship(type = Relationships.LITERATURE_REFERENCE)
    private List<Publication> literatureReference;

    /**
     * MemberOf is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = Relationships.HAS_MEMBER, direction = Relationship.Direction.INCOMING)
    private List<PhysicalEntity> memberOf;

    /**
     * negativelyRegulates is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = Relationships.REGULATOR, direction = Relationship.Direction.INCOMING)
    private List<NegativeRegulation> negativelyRegulates;

    /**
     * positivelyRegulates is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = Relationships.REGULATOR, direction = Relationship.Direction.INCOMING)
    private List<PositiveRegulation> positivelyRegulates;

    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = Relationships.REPEATED_UNIT, direction = Relationship.Direction.INCOMING)
    private Set<RepeatedUnitForPhysicalEntity> repeatedUnitOf;

    @ReactomeTransient
    @Relationship(type = Relationships.INPUT, direction = Relationship.Direction.INCOMING)
    private List<InputForReactionLikeEvent> consumedByEvent;

    @ReactomeTransient
    @Relationship(type = Relationships.OUTPUT, direction = Relationship.Direction.INCOMING)
    private List<OutputForReactionLikeEvent> producedByEvent;

    @Relationship(type = Relationships.REVIEWED, direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> reviewed;

    @Relationship(type = Relationships.REVISED, direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> revised;

    @Relationship(type =  Relationships.SUMMATION)
    private List<Summation> summation;

    @Relationship(type = Relationships.CELL_TYPE)
    private List<CellType> cellType;

    @ReactomeTransient
    @Relationship(type = Relationships.MARKER, direction = Relationship.Direction.INCOMING)
    private List<MarkerReference> markingReferences;

    @ReactomeTransient
    @Relationship(type = Relationships.REPLACEMENT_INSTANCES, direction = Relationship.Direction.INCOMING)
    private List<Deleted> deleted;

    @ReactomeTransient
    @Relationship(type = Relationships.UPDATED_INSTANCES, direction = Relationship.Direction.INCOMING)
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

    public List<Compartment> getCompartment() {
       return Has.Util.expandStoichiometry(compartment);
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    public void setCompartments(SortedSet<HasCompartment> compartment) {
        this.compartment = compartment;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public void setCompartment(List<Compartment> compartment) {
        this.compartment = Has.Util.aggregateStoichiometry(compartment, HasCompartment::new);
    }

    @JsonIgnore
    public void setComponentOf(SortedSet<HasComponentForComplex> componentOf) {
        this.componentOf = componentOf;
    }

    @JsonIgnore
    public void setComponentOf(List<Complex> componentOf) {
        this.componentOf = Has.Util.aggregateStoichiometry(componentOf, HasComponentForComplex::new);
    }

    public void setConsumedByEvent(List<InputForReactionLikeEvent> consumedByEvent) {
        this.consumedByEvent = consumedByEvent;
    }


    public void setProducedByEvent(List<OutputForReactionLikeEvent> producedByEvent) {
        this.producedByEvent = producedByEvent;
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

    public List<PhysicalEntity> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(List<PhysicalEntity> memberOf) {
        this.memberOf = memberOf;
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

    @JsonIgnore
    public List<Polymer> getRepeatedUnitOf() {
        return Has.Util.expandStoichiometry(repeatedUnitOf);
    }

    @JsonIgnore
    public List<Complex> getComponentOf() {
        return Has.Util.expandStoichiometry(componentOf);
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<ReactionLikeEvent> getConsumedByEvent() {
        return Has.Util.expandStoichiometry(consumedByEvent);
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    public List<InputForReactionLikeEvent> getInputFor() {
        return consumedByEvent;
    }

    @JsonView(StoichiometryView.Flatten.class)
    public List<ReactionLikeEvent> getProducedByEvent() {
        return Has.Util.expandStoichiometry(producedByEvent);
    }

    @ReactomeSchemaIgnore
    @JsonView(StoichiometryView.Nested.class)
    public List<OutputForReactionLikeEvent> getOutputFor() {
        return producedByEvent;
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
