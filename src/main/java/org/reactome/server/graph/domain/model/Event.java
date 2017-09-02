package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonGetter;
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

    @Relationship(type = "crossReference")
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = "compartment")
    private List<Compartment> compartment;

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
    private List<Event> eventOf;

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

    /**
     * regulatedBy is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @Relationship(type = "regulatedBy")
    private List<Regulation> regulatedBy;

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
        return compartment;
    }

    @Relationship(type = "compartment")
    public void setCompartment(List<Compartment> compartment) {
        this.compartment = compartment;
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
    public List<Event> getEventOf() {
        return eventOf;
    }

    @Relationship(type = "hasEvent", direction=Relationship.INCOMING)
    public void setEventOf(List<Event> eventOf) {
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

    @Relationship(type = "regulatedBy")
    public List<Regulation> getRegulatedBy() {
        return regulatedBy;
    }

    @Relationship(type = "regulatedBy")
    public void setRegulatedBy(List<Regulation> regulatedBy) {
        this.regulatedBy = regulatedBy;
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


    @ReactomeSchemaIgnore
    @JsonGetter("positivelyRegulatedBy")
    public List<PositiveRegulation> getPositivelyRegulatedBy() {
        List<PositiveRegulation> rtn = new ArrayList<>();
        try {
            for (Regulation regulation : getRegulatedBy()) {
                if (regulation instanceof PositiveRegulation && !(regulation instanceof Requirement)) {
                    rtn.add((PositiveRegulation) regulation);
                }
            }
        } catch (NullPointerException ex) {
            //Nothing here;
        }
        return rtn.isEmpty() ? null : rtn;
    }

    @ReactomeSchemaIgnore
    @JsonGetter("requirements")
    public List<Requirement> getRequirements() {
        List<Requirement> rtn = new ArrayList<>();
        try {
            for (Regulation regulation : getRegulatedBy()) {
                if (regulation instanceof Requirement) {
                    rtn.add((Requirement) regulation);
                }
            }
        } catch (NullPointerException ex) {
            //Nothing here
        }
        return rtn.isEmpty() ? null : rtn;
    }

    @ReactomeSchemaIgnore
    @JsonGetter("negativelyRegulatedBy")
    public List<NegativeRegulation> getNegativelyRegulatedBy() {
        List<NegativeRegulation> rtn = new ArrayList<>();
        try {
            for (Regulation regulation : getRegulatedBy()) {
                if (regulation instanceof NegativeRegulation) {
                    rtn.add((NegativeRegulation) regulation);
                }
            }
        } catch (NullPointerException ex){
            //Nothing here
        }
        return rtn.isEmpty() ? null : rtn;
    }
}
