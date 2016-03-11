package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import uk.ac.ebi.reactome.domain.annotations.ReactomeTransient;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@NodeEntity
public abstract class Event extends DatabaseObject {

    private Boolean _doRelease;
    private String definition;
    //A simple flag to indicate if this Event object is a disease
    private Boolean isInDisease;
    //A simple flag to indicate if this Event is inferred from another
    private Boolean isInferred;
    private List<String> name;
    private String releaseDate;
    private String releaseStatus;
    private String speciesName;

    /**
     * graph contains only relationships to Regulation
     * positive and negativeRegulators have to be filled in service
     */
    @Transient
    private List<DatabaseObject> negativeRegulators;
    @Transient
    private List<DatabaseObject> positiveRegulators;

    /**
     * inferred and orthologous contain the same dataimport, in the Graph representation only inferredTo exists
     * orthologous events will be filled in service
     */
    @Transient
    private Set<Event> orthologousEvent;

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

    @Relationship(type = "evidenceType", direction = Relationship.OUTGOING)
    private EvidenceType evidenceType;

    @Relationship(type = "figure", direction = Relationship.OUTGOING)
    private List<Figure> figure;

    @ReactomeTransient
    @Relationship(type = "precedingEvent", direction=Relationship.INCOMING)
    private List<Event> followingEvent;

    @Relationship(type = "goBiologicalProcess", direction = Relationship.OUTGOING)
    private GO_BiologicalProcess goBiologicalProcess;

    @Relationship(type = "inferredTo", direction = Relationship.OUTGOING)
    private Set<Event> inferredTo;

    @ReactomeTransient
    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    private Set<Event> inferredFrom;

    @Relationship(type = "literatureReference", direction = Relationship.OUTGOING)
    private List<Publication> literatureReference;

    @ReactomeTransient
    @Relationship(type = "regulatedBy", direction = Relationship.OUTGOING)
    private List<NegativeRegulation> negativelyRegulatedBy;

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

    public Boolean get_doRelease() {
        return _doRelease;
    }

    public void set_doRelease(Boolean _doRelease) {
        this._doRelease = _doRelease;
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

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public List<DatabaseObject> getNegativeRegulators() {
        return negativeRegulators;
    }

    public void setNegativeRegulators(List<DatabaseObject> negativeRegulators) {
        this.negativeRegulators = negativeRegulators;
    }

    public List<DatabaseObject> getPositiveRegulators() {
        return positiveRegulators;
    }

    public void setPositiveRegulators(List<DatabaseObject> positiveRegulators) {
        this.positiveRegulators = positiveRegulators;
    }

    public Set<Event> getOrthologousEvent() {
        return orthologousEvent;
    }

    public void setOrthologousEvent(Set<Event> orthologousEvent) {
        this.orthologousEvent = orthologousEvent;
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
        return compartment;
    }

    public void setCompartment(List<Compartment> compartment) {
        this.compartment = compartment;
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

    public Set<Event> getInferredTo() {
        return inferredTo;
    }

    public void setInferredTo(Set<Event> inferredTo) {
        this.inferredTo = inferredTo;
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

    public List<NegativeRegulation> getNegativelyRegulatedBy() {
        return negativelyRegulatedBy;
    }

    public void setNegativelyRegulatedBy(List<NegativeRegulation> negativelyRegulatedBy) {
        this.negativelyRegulatedBy = negativelyRegulatedBy;
    }

    public List<PositiveRegulation> getPositivelyRegulatedBy() {
        return positivelyRegulatedBy;
    }

    public void setPositivelyRegulatedBy(List<PositiveRegulation> positivelyRegulatedBy) {
        this.positivelyRegulatedBy = positivelyRegulatedBy;
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

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
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
}
