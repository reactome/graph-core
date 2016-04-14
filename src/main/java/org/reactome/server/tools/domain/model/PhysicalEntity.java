package org.reactome.server.tools.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.reactome.server.tools.domain.annotations.ReactomeProperty;
import org.reactome.server.tools.domain.annotations.ReactomeTransient;
import org.reactome.server.tools.domain.relationship.HasComponent;
import org.reactome.server.tools.domain.relationship.Input;
import org.reactome.server.tools.domain.relationship.Output;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public abstract class PhysicalEntity extends DatabaseObject {

    @ReactomeProperty
    private String definition;
    @ReactomeProperty
    private List<String> name;
    @ReactomeProperty
    private String speciesName;
    @ReactomeProperty
    private String systematicName;

    // List of CatalyzedEvents filled in service layer
    @Transient
    private List<ReactionLikeEvent> catalyzedEvent;

    // List of GO_MolecularFunctions filled in service layer
    @Transient
    private List<GO_MolecularFunction> goActivity;

    // List of regulated Events filled in service layer
    @Transient
    private List<DatabaseObject> activatedEvent;
    @Transient
    private List<DatabaseObject> inhibitedEvent;
    @Transient
    private List<DatabaseObject> requiredEvent;


    @Relationship(type = "authored", direction = Relationship.INCOMING)
    private InstanceEdit authored;

    /**
     * catalystActivities is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "physicalEntity", direction = Relationship.INCOMING)
    private List<CatalystActivity> catalystActivities;

    @Relationship(type = "compartment", direction = Relationship.OUTGOING)
    private List<EntityCompartment> compartment;

    /**
     * ComponentOf is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "hasComponent", direction = Relationship.INCOMING)
    private List<HasComponent> componentOf;

    @ReactomeTransient
    @Relationship(type = "input", direction = Relationship.INCOMING)
    private List<Input> consumedByEvent;

    @Relationship(type = "crossReference", direction = Relationship.OUTGOING)
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = "disease", direction = Relationship.OUTGOING)
    private List<Disease> disease;

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    private List<InstanceEdit> edited;

    @Relationship(type = "figure", direction = Relationship.OUTGOING)
    private List<Figure> figure;

    @Relationship(type = "goCellularComponent", direction = Relationship.OUTGOING)
    private GO_CellularComponent goCellularComponent;

    @Relationship(type = "inferredTo", direction = Relationship.OUTGOING)
    private List<PhysicalEntity> inferredTo;

    @ReactomeTransient
    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    private List<PhysicalEntity> inferredFrom;

    /**
     * isRequired is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "regulator", direction = Relationship.INCOMING)
    private List<Requirement> isRequired;

    @Relationship(type = "literatureReference", direction = Relationship.OUTGOING)
    private List<Publication> literatureReference;

    /**
     * MemberOf is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "hasMember", direction = Relationship.INCOMING)
    private List<PhysicalEntity> memberOf;

    /**
     * negativelyRegulates is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "regulator", direction = Relationship.INCOMING)
    private List<NegativeRegulation> negativelyRegulates;

    /**
     * positivelyRegulates is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "regulator", direction = Relationship.INCOMING)
    private List<PositiveRegulation> positivelyRegulates;

    @ReactomeTransient
    @Relationship(type = "output", direction = Relationship.INCOMING)
    private List<Output> producedByEvent;

    @Relationship(type = "reviewed", direction = Relationship.INCOMING)
    private List<InstanceEdit> reviewed;

    @Relationship(type = "revised", direction = Relationship.INCOMING)
    private List<InstanceEdit> revised;

    @Relationship(type = "summation", direction = Relationship.OUTGOING)
    private List<Summation> summation;

    public PhysicalEntity() {}

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

    public String getSystematicName() {
        return systematicName;
    }

    public void setSystematicName(String systematicName) {
        this.systematicName = systematicName;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public List<ReactionLikeEvent> getCatalyzedEvent() {
        return catalyzedEvent;
    }

    public void setCatalyzedEvent(List<ReactionLikeEvent> catalyzedEvent) {
        this.catalyzedEvent = catalyzedEvent;
    }

    public List<GO_MolecularFunction> getGoActivity() {
        return goActivity;
    }

    public void setGoActivity(List<GO_MolecularFunction> goActivity) {
        this.goActivity = goActivity;
    }

    public List<DatabaseObject> getActivatedEvent() {
        return activatedEvent;
    }

    public void setActivatedEvent(List<DatabaseObject> activatedEvent) {
        this.activatedEvent = activatedEvent;
    }

    public List<DatabaseObject> getInhibitedEvent() {
        return inhibitedEvent;
    }

    public void setInhibitedEvent(List<DatabaseObject> inhibitedEvent) {
        this.inhibitedEvent = inhibitedEvent;
    }

    public List<DatabaseObject> getRequiredEvent() {
        return requiredEvent;
    }

    public void setRequiredEvent(List<DatabaseObject> requiredEvent) {
        this.requiredEvent = requiredEvent;
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

    public List<EntityCompartment> getCompartment() {
        return compartment;
    }

    public void setCompartment(List<EntityCompartment> compartment) {
        this.compartment = compartment;
    }

    public List<HasComponent> getComponentOf() {
        return componentOf;
    }

    public void setComponentOf(List<HasComponent> componentOf) {
        this.componentOf = componentOf;
    }

    public void setConsumedByEvent(List<Input> consumedByEvent) {
        this.consumedByEvent = consumedByEvent;
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

    public void setProducedByEvent(List<Output> producedByEvent) {
        this.producedByEvent = producedByEvent;
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


    public List<Event> getConsumedByEvent() {
        List<Event> rtn = new ArrayList<>();
        if(consumedByEvent!=null) {
            for (Input aux : consumedByEvent) {
                for (int i = 0; i < aux.getStoichiometry(); i++) {
                    rtn.add(aux.getEvent());
                }
            }
            return rtn;
        }
        return null;
    }

    public List<Event> getProducedByEvent() {
        List<Event> rtn = new ArrayList<>();
        if(producedByEvent!=null) {
            for (Output aux : producedByEvent) {
                for (int i = 0; i < aux.getStoichiometry(); i++) {
                    rtn.add(aux.getEvent());
                }
            }
            return rtn;
        }
        return null;
    }
}
