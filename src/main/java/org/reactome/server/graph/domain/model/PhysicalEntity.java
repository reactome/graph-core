package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;
import org.reactome.server.graph.domain.relationship.HasComponent;
import org.reactome.server.graph.domain.relationship.Input;
import org.reactome.server.graph.domain.relationship.Output;
import org.reactome.server.graph.domain.relationship.RepeatedUnit;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public abstract class PhysicalEntity extends DatabaseObject {

    @ReactomeProperty
    private String definition;
    //A simple flag to indicate if this PhysicalEntity object is a disease
    @ReactomeProperty
    private Boolean isInDisease;
    @ReactomeProperty
    private List<String> name;
    @ReactomeProperty
    private String speciesName;
    @ReactomeProperty
    private String systematicName;

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

    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "repeatedUnit", direction = Relationship.INCOMING)
    private List<RepeatedUnit> repeatedUnitOf;

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

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    public InstanceEdit getAuthored() {
        return authored;
    }

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    public void setAuthored(InstanceEdit authored) {
        this.authored = authored;
    }

    @Relationship(type = "physicalEntity", direction = Relationship.INCOMING)
    public List<CatalystActivity> getCatalystActivities() {
        return catalystActivities;
    }

    @Relationship(type = "physicalEntity", direction = Relationship.INCOMING)
    public void setCatalystActivities(List<CatalystActivity> catalystActivities) {
        this.catalystActivities = catalystActivities;
    }

    public List<EntityCompartment> getCompartment() {
        return compartment;
    }

    public void setCompartment(List<EntityCompartment> compartment) {
        this.compartment = compartment;
    }

    @Relationship(type = "hasComponent", direction = Relationship.INCOMING)
    public void setComponentOf(List<HasComponent> componentOf) {
        this.componentOf = componentOf;
    }

    @Relationship(type = "input", direction = Relationship.INCOMING)
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

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    public List<InstanceEdit> getEdited() {
        return edited;
    }

    @Relationship(type = "edited", direction = Relationship.INCOMING)
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

    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    public List<PhysicalEntity> getInferredFrom() {
        return inferredFrom;
    }

    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    public void setInferredFrom(List<PhysicalEntity> inferredFrom) {
        this.inferredFrom = inferredFrom;
    }

    @Relationship(type = "regulator", direction = Relationship.INCOMING)
    public List<Requirement> getIsRequired() {
        return isRequired;
    }

    @Relationship(type = "regulator", direction = Relationship.INCOMING)
    public void setIsRequired(List<Requirement> isRequired) {
        this.isRequired = isRequired;
    }

    @Relationship(type = "hasMember", direction = Relationship.INCOMING)
    public List<PhysicalEntity> getMemberOf() {
        return memberOf;
    }

    @Relationship(type = "hasMember", direction = Relationship.INCOMING)
    public void setMemberOf(List<PhysicalEntity> memberOf) {
        this.memberOf = memberOf;
    }

    @Relationship(type = "repeatedUnit", direction = Relationship.INCOMING)
    public void setRepeatedUnitOf(List<RepeatedUnit> repeatedUnitOf) {
        this.repeatedUnitOf = repeatedUnitOf;
    }

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }

    @Relationship(type = "regulator", direction = Relationship.INCOMING)
    public List<NegativeRegulation> getNegativelyRegulates() {
        return negativelyRegulates;
    }

    @Relationship(type = "regulator", direction = Relationship.INCOMING)
    public void setNegativelyRegulates(List<NegativeRegulation> negativelyRegulates) {
        this.negativelyRegulates = negativelyRegulates;
    }

    @Relationship(type = "regulator", direction = Relationship.INCOMING)
    public List<PositiveRegulation> getPositivelyRegulates() {
        return positivelyRegulates;
    }

    @Relationship(type = "regulator", direction = Relationship.INCOMING)
    public void setPositivelyRegulates(List<PositiveRegulation> positivelyRegulates) {
        this.positivelyRegulates = positivelyRegulates;
    }

    @Relationship(type = "output", direction = Relationship.INCOMING)
    public void setProducedByEvent(List<Output> producedByEvent) {
        this.producedByEvent = producedByEvent;
    }

    @Relationship(type = "reviewed", direction = Relationship.INCOMING)
    public List<InstanceEdit> getReviewed() {
        return reviewed;
    }

    @Relationship(type = "reviewed", direction = Relationship.INCOMING)
    public void setReviewed(List<InstanceEdit> reviewed) {
        this.reviewed = reviewed;
    }

    @Relationship(type = "revised", direction = Relationship.INCOMING)
    public List<InstanceEdit> getRevised() {
        return revised;
    }

    @Relationship(type = "revised", direction = Relationship.INCOMING)
    public void setRevised(List<InstanceEdit> revised) {
        this.revised = revised;
    }

    public List<Summation> getSummation() {
        return summation;
    }

    public void setSummation(List<Summation> summation) {
        this.summation = summation;
    }

    @Relationship(type = "repeatedUnit", direction = Relationship.INCOMING)
    public List<Polymer> getRepeatedUnitOf() {
        List<Polymer> rtn = new ArrayList<>();
        if(repeatedUnitOf!=null) {
            for (RepeatedUnit aux : repeatedUnitOf) {
                    rtn.add(aux.getPolymer());
            }
            return rtn;
        }
        return null;
    }

    @Relationship(type = "hasComponent", direction = Relationship.INCOMING)
    public List<Complex> getComponentOf() {
        List<Complex> rtn = new ArrayList<>();
        if(componentOf!=null) {
            for (HasComponent aux : componentOf) {
                    rtn.add(aux.getComplex());
            }
            return rtn;
        }
        return null;
    }

    @Relationship(type = "input", direction = Relationship.INCOMING)
    public List<Event> getConsumedByEvent() {
        List<Event> rtn = new ArrayList<>();
        if(consumedByEvent!=null) {
            for (Input aux : consumedByEvent) {
                    rtn.add(aux.getEvent());
            }
            return rtn;
        }
        return null;
    }

    @Relationship(type = "output", direction = Relationship.INCOMING)
    public List<Event> getProducedByEvent() {
        List<Event> rtn = new ArrayList<>();
        if(producedByEvent!=null) {
            for (Output aux : producedByEvent) {
                    rtn.add(aux.getEvent());
            }
            return rtn;
        }
        return null;
    }
}
