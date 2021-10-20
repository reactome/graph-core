package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;
import org.reactome.server.graph.domain.relationship.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;

@SuppressWarnings("unused")
@Node
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

    @Relationship(type = "authored", direction = Relationship.Direction.INCOMING)
    private InstanceEdit authored;

    /**
     * catalystActivities is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "physicalEntity", direction = Relationship.Direction.INCOMING)
    private List<CatalystActivity> catalystActivities;

    @Relationship(type = "compartment")
    private SortedSet<HasCompartment> compartment;

    /**
     * ComponentOf is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "hasComponent", direction = Relationship.Direction.INCOMING)
    private SortedSet<HasComponentForComplex> componentOf;

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

    /**
     * isRequired is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "regulator", direction = Relationship.Direction.INCOMING)
    private List<Requirement> isRequired;

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    /**
     * MemberOf is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "hasMember", direction = Relationship.Direction.INCOMING)
    private List<PhysicalEntity> memberOf;

    /**
     * negativelyRegulates is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "regulator", direction = Relationship.Direction.INCOMING)
    private List<NegativeRegulation> negativelyRegulates;

    /**
     * positivelyRegulates is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "regulator", direction = Relationship.Direction.INCOMING)
    private List<PositiveRegulation> positivelyRegulates;

    @JsonIgnore
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
        if (compartment == null) return null;
        List<Compartment> rtn = new ArrayList<>();
        for (HasCompartment c : compartment) {
            rtn.add(c.getCompartment());
        }
        return rtn;
    }

    public void setCompartment(SortedSet<HasCompartment> compartment) {
        this.compartment = compartment;
    }

    public void setCompartment(List<Compartment> compartment) {
        this.compartment = new TreeSet<>();
        int order = 0;
        for (Compartment c : compartment) {
            HasCompartment hc = new HasCompartment();
            hc.setCompartment(c);
            hc.setOrder(order++);
            this.compartment.add(hc);
        }
    }

    public void setComponentOf(SortedSet<HasComponentForComplex> componentOf) {
        this.componentOf = componentOf;
    }

    public void setComponentOf(List<PhysicalEntity> componentOf) {
        this.componentOf = new TreeSet<>();
        int order = 0;
        for (PhysicalEntity pe : componentOf) {
            HasComponentForComplex hc = new HasComponentForComplex();
//            hc.setPhysicalEntity(this);
            hc.setComplex((Complex) pe);
            hc.setOrder(order++);
            this.componentOf.add(hc);
        }
    }

    public void setConsumedByEvent(List<InputForReactionLikeEvent> consumedByEvent) {
        this.consumedByEvent = consumedByEvent;
    }

//    public void setConsumedByEvent(List<ReactionLikeEvent> events) {
//        this.consumedByEvent = new TreeSet<>();
//        for (ReactionLikeEvent rle : events) {
//            Input input = new Input();
//            input.setReactionLikeEvent(rle);
//            input.setPhysicalEntity(this);
//            input.setStoichiometry(1);
//            this.consumedByEvent.add(input);
//        }
//    }

    public void setProducedByEvent(List<OutputForReactionLikeEvent> producedByEvent) {
        this.producedByEvent = producedByEvent;
    }

//    public void setProducedByEvent(List<ReactionLikeEvent> events) {
//        this.producedByEvent = new TreeSet<>();
//        for (ReactionLikeEvent rle : events) {
//            Output output = new Output();
//            output.setReactionLikeEvent(rle);
//            output.setPhysicalEntity(this);
//            output.setStoichiometry(1);
//            this.producedByEvent.add(output);
//        }
//    }

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

    public void setRepeatedUnitOf(Set<RepeatedUnitForPhysicalEntity> repeatedUnitOf) {
        this.repeatedUnitOf = repeatedUnitOf;
    }

    public void setRepeatedUnitOf(List<PhysicalEntity> repeatedUnitOf) {
        this.repeatedUnitOf = new TreeSet<>();
        int order = 0;
        for (PhysicalEntity pe : repeatedUnitOf) {
            RepeatedUnitForPhysicalEntity ru = new RepeatedUnitForPhysicalEntity();
//            ru.setPhysicalEntity(this);
            ru.setPolymer((Polymer) pe);
            ru.setOrder(order++);
            this.repeatedUnitOf.add(ru);
        }
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

//    public Set<RepeatedUnit> getRepeatedUnitOf(){
//        return repeatedUnitOf;
//    }
    public List<Polymer> getRepeatedUnitOf() {
        List<Polymer> rtn = new ArrayList<>();
        if (repeatedUnitOf != null) {
            for (RepeatedUnitForPhysicalEntity aux : repeatedUnitOf) {
                for (int i = 0; i < aux.getStoichiometry(); i++) {
//                    rtn.add(aux.getPhysicalEntity())
                    rtn.add(aux.getPolymer());
                }
            }
            return rtn;
        }
        return null;
    }

    public List<Complex> getComponentOf() {
        List<Complex> rtn = new ArrayList<>();
        if (componentOf != null) {
            for (HasComponentForComplex aux : componentOf) {
                rtn.add(aux.getComplex());
//                rtn.add(aux.getComplex());
            }
            return rtn;
        }
        return null;
    }

//    public List<InputForReactionLikeEvent> getConsumedByEvent() {
//        return consumedByEvent;
//    }

    public List<ReactionLikeEvent> getConsumedByEvent() {
        List<ReactionLikeEvent> rtn = new ArrayList<>();
        if (consumedByEvent != null) {
            for (InputForReactionLikeEvent aux : consumedByEvent) {
                rtn.add(aux.getReactionLikeEvent());
            }
            return rtn;
        }
        return null;
    }

//    public List<OutputForReactionLikeEvent> getProducedByEvent() {
//        return producedByEvent;
//    }

    public List<ReactionLikeEvent> getProducedByEvent() {
        List<ReactionLikeEvent> rtn = new ArrayList<>();
        if (producedByEvent != null) {
            for (OutputForReactionLikeEvent aux : producedByEvent) {
                rtn.add(aux.getReactionLikeEvent());
            }
            return rtn;
        }
        return null;
    }

    public List<CellType> getCellType() {
        return cellType;
    }

    public void setCellType(List<CellType> cellType) {
        this.cellType = cellType;
    }
}
