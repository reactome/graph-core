package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NodeEntity
public abstract class Event extends DatabaseObject implements Regulator {

    private Boolean _doRelease;
    @Relationship
    private List<InstanceEdit> authored;
    @Relationship
    private List<InstanceEdit> edited;
    @Relationship
    private List<InstanceEdit> revised;
    @Relationship
    private List<InstanceEdit> reviewed;
    @Relationship
    private List<Species> species;
    private String speciesName;
    @Relationship
    private List<Species> relatedSpecies;
    private String definition;
    @Relationship
    private EvidenceType evidenceType;
    @Relationship
    private GO_BiologicalProcess goBiologicalProcess;
    private String releaseDate;
    private String keywords;
    @Relationship
    private List<Summation> summation;
    @Relationship
    private String releaseStatus;
    @Relationship
    private List<Figure> figure;
    @Relationship
    private List<Event> precedingEvent;
    @Relationship
    private List<Event> followingEvent;
    @Relationship
    private List<Publication> literatureReference;
    // Regulation related attributes
    @Relationship
    private List<DatabaseObject> negativeRegulators;
    // Regulators in PositiveRegulations but not Requirements.
    // Note: Requirement is a subclass to PositiveRegulation.
    @Relationship
    private List<DatabaseObject> positiveRegulators;
    @Relationship
    private List<DatabaseObject> requirements;
    @Relationship
    private List<DatabaseIdentifier> crossReference;
    @Relationship
    private List<Disease> disease;
    // A simple label to indicate if this Event object is a disease
    private Boolean isInDisease;
    @Relationship
    private List<Event> inferredFrom;
    // A simple flag to indicate if this Event is inferred from another
    private Boolean isInferred;
    @Relationship
    private List<String> name;
    @Relationship
    private List<Event> orthologousEvent;
    @Relationship
    private List<Compartment> compartment;

    public Event() {
    }

    public Boolean getIsInferred() {
        return isInferred;
    }

    public void setIsInferred(Boolean isInferred) {
        this.isInferred = isInferred;
    }

    public Boolean getIsInDisease() {
        return isInDisease;
    }

    public void setIsInDisease(Boolean isInDisease) {
        this.isInDisease = isInDisease;
    }

    public Boolean get_doRelease() {
        return _doRelease;
    }

    public void set_doRelease(Boolean _doRelease) {
        this._doRelease = _doRelease;
    }

    public List<InstanceEdit> getAuthored() {
        return authored;
    }

    public void setAuthored(List<InstanceEdit> authored) {
        this.authored = authored;
    }

    public List<InstanceEdit> getEdited() {
        return edited;
    }

    public void setEdited(List<InstanceEdit> edited) {
        this.edited = edited;
    }

    public List<InstanceEdit> getRevised() {
        return revised;
    }

    public void setRevised(List<InstanceEdit> revised) {
        this.revised = revised;
    }

    public List<InstanceEdit> getReviewed() {
        return reviewed;
    }

    public void setReviewed(List<InstanceEdit> reviewed) {
        this.reviewed = reviewed;
    }

    public List<Species> getSpecies() {
        return species;
    }

    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String species) {
        this.speciesName = species;
    }

    public List<Species> getRelatedSpecies() {
        return relatedSpecies;
    }

    public void setRelatedSpecies(List<Species> relatedSpecies) {
        this.relatedSpecies = relatedSpecies;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public EvidenceType getEvidenceType() {
        return evidenceType;
    }

    public void setEvidenceType(EvidenceType evidenceType) {
        this.evidenceType = evidenceType;
    }

    public GO_BiologicalProcess getGoBiologicalProcess() {
        return goBiologicalProcess;
    }

    public void setGoBiologicalProcess(GO_BiologicalProcess goBiologicalProcess) {
        this.goBiologicalProcess = goBiologicalProcess;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<Summation> getSummation() {
        return summation;
    }

    public void setSummation(List<Summation> summation) {
        this.summation = summation;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public List<Figure> getFigure() {
        return figure;
    }

    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }

    public List<Event> getPrecedingEvent() {
        return precedingEvent;
    }

    public void setPrecedingEvent(List<Event> precedingEvent) {
        this.precedingEvent = precedingEvent;
    }

    public List<Event> getFollowingEvent() {
        return followingEvent;
    }

    public void setFollowingEvent(List<Event> followingEvent) {
        this.followingEvent = followingEvent;
    }

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
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

    public List<DatabaseObject> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<DatabaseObject> requirements) {
        this.requirements = requirements;
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

    public List<Event> getInferredFrom() {
        return inferredFrom;
    }

    public void setInferredFrom(List<Event> inferredFrom) {
        this.inferredFrom = inferredFrom;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Event> getOrthologousEvent() {
        return orthologousEvent;
    }

    public void setOrthologousEvent(List<Event> orthologousEvent) {
        this.orthologousEvent = orthologousEvent;
    }

    public List<Compartment> getCompartment() {
        return compartment;
    }

    public void setCompartment(List<Compartment> compartment) {
        this.compartment = compartment;
    }

}
