package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused", "WeakerAccess"})
@NodeEntity
public abstract class Event extends DatabaseObject {

    @ReactomeProperty
    private String definition;
    //A simple flag to indicate if this Event object is a disease
    @ReactomeProperty
    private Boolean isInDisease;
    //A simple flag to indicate if this Event is inferred from another
    @ReactomeProperty
    private Boolean isInferred;
    @ReactomeProperty
    private List<String> name;
    @ReactomeProperty
    private String releaseDate;
    @ReactomeProperty
    private String releaseStatus;
    @ReactomeProperty
    private String speciesName;

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    private List<InstanceEdit> authored;

    @Relationship(type = "crossReference", direction = Relationship.OUTGOING)
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = "compartment", direction = Relationship.OUTGOING)
    private List<Compartment> compartment;

    @Relationship(type = "disease", direction = Relationship.OUTGOING)
    private List<Disease> disease;

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    private List<InstanceEdit> edited;

    /**
     * eventOf is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "hasEvent", direction=Relationship.INCOMING)
    private List<Event> eventOf;

    @Relationship(type = "evidenceType", direction = Relationship.OUTGOING)
    private EvidenceType evidenceType;

    @Relationship(type = "figure", direction = Relationship.OUTGOING)
    private List<Figure> figure;

    /**
     * followingEvent is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "precedingEvent", direction=Relationship.INCOMING)
    private List<Event> followingEvent;

    @Relationship(type = "goBiologicalProcess", direction = Relationship.OUTGOING)
    private GO_BiologicalProcess goBiologicalProcess;

//    InferredTo is orthologousEvents, Spring cant currently map the same thing twice
//    @Relationship(type = "inferredTo", direction = Relationship.OUTGOING)
//    private Set<Event> inferredTo;

    @ReactomeTransient
    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    private Set<Event> inferredFrom;

    @Relationship(type = "literatureReference", direction = Relationship.OUTGOING)
    private List<Publication> literatureReference;

    @ReactomeTransient
    @Relationship(type = "regulatedBy", direction = Relationship.OUTGOING)
    private List<NegativeRegulation> negativelyRegulatedBy;

    @Relationship(type = "inferredTo", direction = Relationship.OUTGOING)
    private Set<Event> orthologousEvent;

    @Relationship(type = "regulatedBy", direction = Relationship.OUTGOING)
    private List<PositiveRegulation> positivelyRegulatedBy;

    @Relationship(type = "precedingEvent", direction = Relationship.OUTGOING)
    private List<Event> precedingEvent;

    @Relationship(type = "relatedSpecies", direction = Relationship.OUTGOING)
    private List<Species> relatedSpecies;

    @ReactomeTransient
    @Relationship(type = "regulatedBy", direction = Relationship.OUTGOING)
    private List<Requirement> requirements;

    @Relationship(type = "reviewed", direction = Relationship.INCOMING)
    private List<InstanceEdit> reviewed;

    @Relationship(type = "revised", direction = Relationship.INCOMING)
    private List<InstanceEdit> revised;

    @Relationship(type = "species", direction = Relationship.OUTGOING)
    private List<Species> species;

    @Relationship(type = "summation", direction = Relationship.OUTGOING)
    private List<Summation> summation;

    public Event() {}

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Boolean getIsInDisease() {
        return isInDisease;
    }

    public void setIsInDisease(Boolean isInDisease) {
        this.isInDisease = isInDisease;
    }

    public Boolean getIsInferred() {
        return isInferred;
    }

    public void setIsInferred(Boolean isInferred) {
        this.isInferred = isInferred;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    @ReactomeSchemaIgnore
    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    public List<InstanceEdit> getAuthored() {
        return authored;
    }

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    public void setAuthored(List<InstanceEdit> authored) {
        this.authored = authored;
    }

    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

    @Relationship(type = "crossReference", direction = Relationship.OUTGOING)
    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }

    public List<Compartment> getCompartment() {
        return compartment;
    }

    @Relationship(type = "compartment", direction = Relationship.OUTGOING)
    public void setCompartment(List<Compartment> compartment) {
        this.compartment = compartment;
    }

    public List<Disease> getDisease() {
        return disease;
    }

    @Relationship(type = "disease", direction = Relationship.OUTGOING)
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

    public EvidenceType getEvidenceType() {
        return evidenceType;
    }

    @Relationship(type = "hasEvent", direction=Relationship.INCOMING)
    public List<Event> getEventOf() {
        return eventOf;
    }

    @Relationship(type = "hasEvent", direction=Relationship.INCOMING)
    public void setEventOf(List<Event> eventOf) {
        this.eventOf = eventOf;
    }

    @Relationship(type = "evidenceType", direction = Relationship.OUTGOING)
    public void setEvidenceType(EvidenceType evidenceType) {
        this.evidenceType = evidenceType;
    }

    public List<Figure> getFigure() {
        return figure;
    }

    @Relationship(type = "figure", direction = Relationship.OUTGOING)
    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }

    @Relationship(type = "precedingEvent", direction=Relationship.INCOMING)
    public List<Event> getFollowingEvent() {
        return followingEvent;
    }

    @Relationship(type = "precedingEvent", direction=Relationship.INCOMING)
    public void setFollowingEvent(List<Event> followingEvent) {
        this.followingEvent = followingEvent;
    }

    public GO_BiologicalProcess getGoBiologicalProcess() {
        return goBiologicalProcess;
    }

    @Relationship(type = "goBiologicalProcess", direction = Relationship.OUTGOING)
    public void setGoBiologicalProcess(GO_BiologicalProcess goBiologicalProcess) {
        this.goBiologicalProcess = goBiologicalProcess;
    }

    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    public Set<Event> getInferredFrom() {
        return inferredFrom;
    }

    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    public void setInferredFrom(Set<Event> inferredFrom) {
        this.inferredFrom = inferredFrom;
    }

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    @Relationship(type = "literatureReference", direction = Relationship.OUTGOING)
    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }

    public List<NegativeRegulation> getNegativelyRegulatedBy() {
        return negativelyRegulatedBy;
    }

    @Relationship(type = "regulatedBy", direction = Relationship.OUTGOING)
    public void setNegativelyRegulatedBy(List<NegativeRegulation> negativelyRegulatedBy) {
        this.negativelyRegulatedBy = negativelyRegulatedBy;
    }

    public Set<Event> getOrthologousEvent() {
        return orthologousEvent;
    }

    @Relationship(type = "inferredTo", direction = Relationship.OUTGOING)
    public void setOrthologousEvent(Set<Event> orthologousEvent) {
        this.orthologousEvent = orthologousEvent;
    }

    public List<PositiveRegulation> getPositivelyRegulatedBy() {
        return positivelyRegulatedBy;
    }

    @Relationship(type = "regulatedBy", direction = Relationship.OUTGOING)
    public void setPositivelyRegulatedBy(List<PositiveRegulation> positivelyRegulatedBy) {
        this.positivelyRegulatedBy = positivelyRegulatedBy;
    }

    public List<Event> getPrecedingEvent() {
        return precedingEvent;
    }

    @Relationship(type = "precedingEvent", direction = Relationship.OUTGOING)
    public void setPrecedingEvent(List<Event> precedingEvent) {
        this.precedingEvent = precedingEvent;
    }

    public List<Species> getRelatedSpecies() {
        return relatedSpecies;
    }

    @Relationship(type = "relatedSpecies", direction = Relationship.OUTGOING)
    public void setRelatedSpecies(List<Species> relatedSpecies) {
        this.relatedSpecies = relatedSpecies;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    @Relationship(type = "regulatedBy", direction = Relationship.OUTGOING)
    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
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

    public List<Species> getSpecies() {
        return species;
    }

    @Relationship(type = "species", direction = Relationship.OUTGOING)
    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    public List<Summation> getSummation() {
        return summation;
    }

    @Relationship(type = "summation", direction = Relationship.OUTGOING)
    public void setSummation(List<Summation> summation) {
        this.summation = summation;
    }

    // Null values will not be propagated to json, empty lists will. Thus I rather return null here.
    public List<Regulation> getRegulations() {
        List<Regulation> regulations = null;
        if (this.getPositivelyRegulatedBy() != null && !this.getPositivelyRegulatedBy().isEmpty()) {
            regulations = new ArrayList<>();
            regulations.addAll(this.getPositivelyRegulatedBy());
        }
        if (this.getNegativelyRegulatedBy() != null && !this.getNegativelyRegulatedBy().isEmpty()) {
            if (regulations == null) {
                regulations = new ArrayList<>();
            }
            regulations.addAll(this.getNegativelyRegulatedBy());
        }
        return regulations;
    }
}
