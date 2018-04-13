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

import java.util.*;

@SuppressWarnings("unused")
@NodeEntity
public abstract class PhysicalEntity extends DatabaseObject {

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

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    private InstanceEdit authored;

    /**
     * catalystActivities is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "physicalEntity", direction = Relationship.INCOMING)
    private List<CatalystActivity> catalystActivities;

    @Relationship(type = "compartment")
    private List<Compartment> compartment;

    /**
     * ComponentOf is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "hasComponent", direction = Relationship.INCOMING)
    private SortedSet<HasComponent> componentOf;

    @ReactomeTransient
    @Relationship(type = "input", direction = Relationship.INCOMING)
    private Set<Input> consumedByEvent;

    @Relationship(type = "crossReference")
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = "disease")
    private List<Disease> disease;

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    private List<InstanceEdit> edited;

    @Relationship(type = "figure")
    private List<Figure> figure;

    @Relationship(type = "goCellularComponent")
    private GO_CellularComponent goCellularComponent;

    @Relationship(type = "inferredTo")
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

    @Relationship(type = "literatureReference")
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
    private Set<RepeatedUnit> repeatedUnitOf;

    @ReactomeTransient
    @Relationship(type = "output", direction = Relationship.INCOMING)
    private Set<Output> producedByEvent;

    @Relationship(type = "reviewed", direction = Relationship.INCOMING)
    private List<InstanceEdit> reviewed;

    @Relationship(type = "revised", direction = Relationship.INCOMING)
    private List<InstanceEdit> revised;

    @Relationship(type = "summation")
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

    public List<Compartment> getCompartment() {
        return compartment;
    }

    @Relationship(type = "compartment")
    public void setCompartment(List<Compartment> compartment) {
        this.compartment = compartment;
    }

    @Relationship(type = "hasComponent", direction = Relationship.INCOMING)
    public void setComponentOf(SortedSet<HasComponent> componentOf) {
        this.componentOf = componentOf;
    }

    public void setComponentOf(List<PhysicalEntity> componentOf) {
        this.componentOf = new TreeSet<>();
        int order = 0;
        for (PhysicalEntity pe : componentOf) {
            HasComponent hc = new HasComponent();
            hc.setPhysicalEntity(this);
            hc.setComplex((Complex) pe);
            hc.setOrder(order++);
            this.componentOf.add(hc);
        }
    }

    @Relationship(type = "input", direction = Relationship.INCOMING)
    public void setConsumedByEvent(Set<Input> consumedByEvent) {
        this.consumedByEvent = consumedByEvent;
    }

    public void setConsumedByEvent(List<Event> events) {
        this.consumedByEvent = new TreeSet<>();
        for (Event e : events) {
            Input input = new Input();
            input.setEvent(e);
            input.setPhysicalEntity(this);
            input.setStoichiometry(1);
            this.consumedByEvent.add(input);
        }
    }

    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

    @Relationship(type = "crossReference")
    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }

    public List<Disease> getDisease() {
        return disease;
    }

    @Relationship(type = "disease")
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

    @Relationship(type = "figure")
    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }

    public GO_CellularComponent getGoCellularComponent() {
        return goCellularComponent;
    }

    @Relationship(type = "goCellularComponent")
    public void setGoCellularComponent(GO_CellularComponent goCellularComponent) {
        this.goCellularComponent = goCellularComponent;
    }

    @Relationship(type = "inferredTo")
    public List<PhysicalEntity> getInferredTo() {
        return inferredTo;
    }

    @Relationship(type = "inferredTo")
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
    public void setRepeatedUnitOf(Set<RepeatedUnit> repeatedUnitOf) {
        this.repeatedUnitOf = repeatedUnitOf;
    }

    public void setRepeatedUnitOf(List<PhysicalEntity> repeatedUnitOf) {
        this.repeatedUnitOf = new TreeSet<>();
        int order = 0;
        for (PhysicalEntity pe : repeatedUnitOf) {
            RepeatedUnit ru = new RepeatedUnit();
            ru.setPhysicalEntity(this);
            ru.setPolymer((Polymer) pe);
            ru.setOrder(order++);
            this.repeatedUnitOf.add(ru);
        }
    }

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    @Relationship(type = "literatureReference")
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
    public void setProducedByEvent(Set<Output> producedByEvent) {
        this.producedByEvent = producedByEvent;
    }

    public void setProducedByEvent(List<Event> events) {
        this.producedByEvent = new TreeSet<>();
        for (Event event : events) {
            Output output = new Output();
            output.setEvent(event);
            output.setPhysicalEntity(this);
            output.setStoichiometry(1);
            this.producedByEvent.add(output);
        }
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

    @Relationship(type = "summation")
    public void setSummation(List<Summation> summation) {
        this.summation = summation;
    }

    @Relationship(type = "repeatedUnit", direction = Relationship.INCOMING)
    public List<Polymer> getRepeatedUnitOf() {
        List<Polymer> rtn = new ArrayList<>();
        if(repeatedUnitOf!=null) {
            for (RepeatedUnit aux : repeatedUnitOf) {
                for (int i = 0; i < aux.getStoichiometry(); i++) {
                    rtn.add(aux.getPolymer());
                }
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
