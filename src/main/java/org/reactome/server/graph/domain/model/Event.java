package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;
import org.reactome.server.graph.domain.relationship.HasCompartment;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;

@SuppressWarnings("unused")

@Node
public abstract class Event extends DatabaseObject implements Trackable, Deletable {

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

    @Relationship(type = "authored", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> authored;

    @Relationship(type = "crossReference")
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = "compartment")
    private SortedSet<HasCompartment> compartment;

    @Relationship(type = "disease")
    private List<Disease> disease;

    @Relationship(type = "edited", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> edited;

    /**
     * eventOf is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "hasEvent", direction=Relationship.Direction.INCOMING)
    private List<Pathway> eventOf;

    @Relationship(type = "evidenceType")
    private EvidenceType evidenceType;

    @Relationship(type = "figure")
    private List<Figure> figure;

    @Relationship(type = "precedingEvent")
    private List<Event> precedingEvent;

    /**
     * followingEvent is not a field of the previous RestfulApi and will be ignored until needed
     */
    @JsonIgnore
    @ReactomeTransient
    @Relationship(type = "precedingEvent", direction=Relationship.Direction.INCOMING)
    private List<Event> followingEvent;

    @Relationship(type = "goBiologicalProcess")
    private GO_BiologicalProcess goBiologicalProcess;

    @Relationship(type = "inferredTo", direction = Relationship.Direction.INCOMING)
    private Set<Event> inferredFrom;

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    @Relationship(type = "inferredTo")
    private Set<Event> orthologousEvent;

    @Relationship(type = "relatedSpecies")
    private List<Species> relatedSpecies;

    @Relationship(type = "reviewed", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> reviewed;

    @Relationship(type = "revised", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> revised;

    @Relationship(type = "species")
    private List<Species> species;

    @Relationship(type = "summation")
    private List<Summation> summation;

    @Relationship(type = "negativePrecedingEvent")
    private List<NegativePrecedingEvent> negativePrecedingEvent;

    /**
     * For ReviewStatus-based star system
     */
    @Relationship(type = "reviewStatus")
    private ReviewStatus reviewStatus;

    @Relationship(type = "previousReviewStatus")
    private ReviewStatus previousReviewStatus;

    @Relationship(type = "internalReviewed", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> internalReviewed;

    @Relationship(type = "structureModified", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> structureModified;

    @ReactomeTransient
    @Relationship(type = "replacementInstances", direction = Relationship.Direction.INCOMING)
    private List<Deleted> deleted;

    @ReactomeTransient
    @Relationship(type = "updatedInstance", direction = Relationship.Direction.INCOMING)
    private List<UpdateTracker> updateTrackers;


    public Event() {}

    public Event(Long dbId) {
        super(dbId);
    }

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

    public List<InstanceEdit> getAuthored() {
        return authored;
    }

    public void setAuthored(List<InstanceEdit> authored) {
        this.authored = authored;
    }

    public List<DatabaseIdentifier> getCrossReference() {
        return crossReference;
    }

    public void setCrossReference(List<DatabaseIdentifier> crossReference) {
        this.crossReference = crossReference;
    }

    public List<Compartment> getCompartment() {
        if(compartment == null) return null;
        List<Compartment> rtn = new ArrayList<>();
        for (HasCompartment c : compartment) {
            rtn.add(c.getCompartment());
        }
        return rtn;
    }

    // TODO This setCompartment break the reflection for testing against Relational DB. Renaming it fix the test, check impact
    // TODO Check setHasCompartment -> setCompartment impact
    public void setCompartment(SortedSet<HasCompartment> compartment) {
        this.compartment = compartment;
    }

    public void setCompartment(List<Compartment> compartment) {
        this.compartment = new TreeSet<>();
        int order = 0;
        for (Compartment c : compartment) {
            HasCompartment hc = new HasCompartment();
//            hc.setSource(this);
            hc.setCompartment(c);
            hc.setOrder(order++);
            this.compartment.add(hc);
        }
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

    public EvidenceType getEvidenceType() {
        return evidenceType;
    }

    public List<Pathway> getEventOf() {
        return eventOf;
    }

    public void setEventOf(List<Pathway> eventOf) {
        this.eventOf = eventOf;
    }

    public void setEvidenceType(EvidenceType evidenceType) {
        this.evidenceType = evidenceType;
    }

    public List<Figure> getFigure() {
        return figure;
    }

    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }

    public List<Event> getFollowingEvent() {
        return followingEvent;
    }

    public void setFollowingEvent(List<Event> followingEvent) {
        this.followingEvent = followingEvent;
    }

    public GO_BiologicalProcess getGoBiologicalProcess() {
        return goBiologicalProcess;
    }

    public void setGoBiologicalProcess(GO_BiologicalProcess goBiologicalProcess) {
        this.goBiologicalProcess = goBiologicalProcess;
    }

    public Set<Event> getInferredFrom() {
        return inferredFrom;
    }

    public void setInferredFrom(Set<Event> inferredFrom) {
        this.inferredFrom = inferredFrom;
    }

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }

    public Set<Event> getOrthologousEvent() {
        return orthologousEvent;
    }

    public void setOrthologousEvent(Set<Event> orthologousEvent) {
        this.orthologousEvent = orthologousEvent;
    }

    public List<Event> getPrecedingEvent() {
        return precedingEvent;
    }

    public void setPrecedingEvent(List<Event> precedingEvent) {
        this.precedingEvent = precedingEvent;
    }

    public List<Species> getRelatedSpecies() {
        return relatedSpecies;
    }

    public void setRelatedSpecies(List<Species> relatedSpecies) {
        this.relatedSpecies = relatedSpecies;
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

    public List<Species> getSpecies() {
        return species;
    }

    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    public List<Summation> getSummation() {
        return summation;
    }

    public void setSummation(List<Summation> summation) {
        this.summation = summation;
    }

    public List<NegativePrecedingEvent> getNegativePrecedingEvent() {
        return negativePrecedingEvent;
    }

    public void setNegativePrecedingEvent(List<NegativePrecedingEvent> negativePrecedingEvent) {
        this.negativePrecedingEvent = negativePrecedingEvent;
    }

    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public ReviewStatus getPreviousReviewStatus() {
        return previousReviewStatus;
    }

    public void setPreviousReviewStatus(ReviewStatus previousReviewStatus) {
        this.previousReviewStatus = previousReviewStatus;
    }

    public List<InstanceEdit> getInternalReviewed() {
        return internalReviewed;
    }

    public void setInternalReviewed(List<InstanceEdit> internalReviewed) {
        this.internalReviewed = internalReviewed;
    }

    public List<InstanceEdit> getStructureModified() {
        return structureModified;
    }

    public void setStructureModified(List<InstanceEdit> structureModified) {
        this.structureModified = structureModified;
    }

    @Override
    public List<Deleted> getDeleted() {
        return deleted;
    }

    public void setDeletedList(List<Deleted> deleted) {
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
