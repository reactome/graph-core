package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;
import org.reactome.server.graph.domain.relationship.HasCompartment;

import java.util.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@NodeEntity
public abstract class Event extends DatabaseObject {

    @ReactomeProperty
    private String definition;
    //A simple flag to indicate if this Event object is a disease
    @ReactomeProperty(addedField = true)
    private Boolean isInDisease;
    //A simple flag to indicate if this Event is inferred from another
    @ReactomeProperty(addedField = true)
    private Boolean isInferred;
    @ReactomeProperty
    private List<String> name;
    @ReactomeProperty
    private String releaseDate;
    @ReactomeProperty
    private String releaseStatus;
    @ReactomeProperty(addedField = true)
    private String speciesName;

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    private List<InstanceEdit> authored;

    @Relationship(type = "crossReference")
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = "compartment")
    private SortedSet<HasCompartment<Event>> compartment;

    @Relationship(type = "disease")
    private List<Disease> disease;

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    private List<InstanceEdit> edited;

    /**
     * eventOf is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "hasEvent", direction=Relationship.INCOMING)
    private List<Pathway> eventOf;

    @Relationship(type = "evidenceType")
    private EvidenceType evidenceType;

    @Relationship(type = "figure")
    private List<Figure> figure;

    /**
     * followingEvent is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "precedingEvent", direction=Relationship.INCOMING)
    private List<Event> followingEvent;

    @Relationship(type = "goBiologicalProcess")
    private GO_BiologicalProcess goBiologicalProcess;

    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    private Set<Event> inferredFrom;

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    @Relationship(type = "inferredTo")
    private Set<Event> orthologousEvent;

    @Relationship(type = "precedingEvent")
    private List<Event> precedingEvent;

    @Relationship(type = "relatedSpecies")
    private List<Species> relatedSpecies;

    @Relationship(type = "reviewed", direction = Relationship.INCOMING)
    private List<InstanceEdit> reviewed;

    @Relationship(type = "revised", direction = Relationship.INCOMING)
    private List<InstanceEdit> revised;

    @Relationship(type = "species")
    private List<Species> species;

    @Relationship(type = "summation")
    private List<Summation> summation;

    @Relationship(type = "negativePrecedingEvent")
    private List<NegativePrecedingEvent> negativePrecedingEvent;

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

    @Relationship(type = "crossReference")
    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }

    public List<Compartment> getCompartment() {
        if(compartment == null) return null;
        List<Compartment> rtn = new ArrayList<>();
        for (HasCompartment<Event> c : compartment) {
            rtn.add(c.getCompartment());
        }
        return rtn;
    }

    @Relationship(type = "compartment")
    public void setCompartment(SortedSet<HasCompartment<Event>> compartment) {
        this.compartment = compartment;
    }

    public void setCompartment(List<Compartment> compartment) {
        this.compartment = new TreeSet<>();
        int order = 0;
        for (Compartment c : compartment) {
            HasCompartment<Event> hc = new HasCompartment<>();
            hc.setSource(this);
            hc.setCompartment(c);
            hc.setOrder(order++);
            this.compartment.add(hc);
        }
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

    public EvidenceType getEvidenceType() {
        return evidenceType;
    }

    @Relationship(type = "hasEvent", direction=Relationship.INCOMING)
    public List<Pathway> getEventOf() {
        return eventOf;
    }

    @Relationship(type = "hasEvent", direction=Relationship.INCOMING)
    public void setEventOf(List<Pathway> eventOf) {
        this.eventOf = eventOf;
    }

    @Relationship(type = "evidenceType")
    public void setEvidenceType(EvidenceType evidenceType) {
        this.evidenceType = evidenceType;
    }

    public List<Figure> getFigure() {
        return figure;
    }

    @Relationship(type = "figure")
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

    @Relationship(type = "goBiologicalProcess")
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

    @Relationship(type = "literatureReference")
    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    @Relationship(type = "literatureReference")
    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }

    @Relationship(type = "inferredTo")
    public Set<Event> getOrthologousEvent() {
        return orthologousEvent;
    }

    @Relationship(type = "inferredTo")
    public void setOrthologousEvent(Set<Event> orthologousEvent) {
        this.orthologousEvent = orthologousEvent;
    }

    @Relationship(type = "precedingEvent")
    public List<Event> getPrecedingEvent() {
        return precedingEvent;
    }

    @Relationship(type = "precedingEvent")
    public void setPrecedingEvent(List<Event> precedingEvent) {
        this.precedingEvent = precedingEvent;
    }

    public List<Species> getRelatedSpecies() {
        return relatedSpecies;
    }

    @Relationship(type = "relatedSpecies")
    public void setRelatedSpecies(List<Species> relatedSpecies) {
        this.relatedSpecies = relatedSpecies;
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

    @Relationship(type = "species")
    public List<Species> getSpecies() {
        return species;
    }

    @Relationship(type = "species")
    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    @Relationship(type = "summation")
    public List<Summation> getSummation() {
        return summation;
    }

    @Relationship(type = "summation")
    public void setSummation(List<Summation> summation) {
        this.summation = summation;
    }

    @Relationship(type = "negativePrecedingEvent")
    public List<NegativePrecedingEvent> getNegativePrecedingEvent() {
        return negativePrecedingEvent;
    }

    @Relationship(type = "negativePrecedingEvent")
    public void setNegativePrecedingEvent(List<NegativePrecedingEvent> negativePrecedingEvent) {
        this.negativePrecedingEvent = negativePrecedingEvent;
    }
}
