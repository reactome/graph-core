package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public abstract class Event extends DatabaseObject implements Regulator {

    private Boolean _doRelease;
    private String releaseDate;
    private String keywords;
    private String speciesName;
    private String definition;
    /**
     * A simple label to indicate if this Event object is a disease
     */
    private Boolean isInDisease;
    /**
     * A simple flag to indicate if this Event is inferred from another
     */
    private Boolean isInferred;
    
    @Relationship(type = "authored")
    private List<InstanceEdit> authored;
    
    @Relationship(type = "edited")
    private List<InstanceEdit> edited;
    
    @Relationship(type = "revised")
    private List<InstanceEdit> revised;
    
    @Relationship(type = "reviewed")
    private List<InstanceEdit> reviewed;
    
    @Relationship(type = "species")
    private List<Species> species;
    
    @Relationship(type = "relatedSpecies")
    private List<Species> relatedSpecies;
    
    @Relationship(type = "evidenceType")
    private EvidenceType evidenceType;
    
    @Relationship(type = "goBiologicalProcess")
    private GO_BiologicalProcess goBiologicalProcess;

    @Relationship(type = "summation")
    private List<Summation> summation;
    
    @Relationship(type = "releaseStatus")
    private String releaseStatus;
    
    @Relationship(type = "figure")
    private List<Figure> figure;
    
    @Relationship(type = "precedingEvent")
    private List<Event> precedingEvent;
    
    @Relationship(type = "followingEvent")
    private List<Event> followingEvent;
    
    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    /**
     * Regulation related attributes
     */
    @Relationship(type = "negativeRegulators")
    private List<DatabaseObject> negativeRegulators;

    /**
     * Regulators in PositiveRegulations but not Requirements. Requirement is a subclass to PositiveRegulation.
     */
    @Relationship(type = "positiveRegulators")
    private List<DatabaseObject> positiveRegulators;
    
    @Relationship(type = "requirements")
    private List<DatabaseObject> requirements;
    
    @Relationship(type = "crossReference")
    private List<DatabaseIdentifier> crossReference;
    
    @Relationship(type = "disease")
    private List<Disease> disease;
    
    @Relationship(type = "inferredFrom")
    private List<Event> inferredFrom;

    @Relationship(type = "name")
    private List<String> name;
    
    @Relationship(type = "orthologousEvent")
    private List<Event> orthologousEvent;
    
    @Relationship(type = "compartment")
    private List<Compartment> compartment;

    public Event() {}

    public Boolean get_doRelease() {
        return _doRelease;
    }

    public void set_doRelease(Boolean _doRelease) {
        this._doRelease = _doRelease;
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

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
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

    public List<Species> getRelatedSpecies() {
        return relatedSpecies;
    }

    public void setRelatedSpecies(List<Species> relatedSpecies) {
        this.relatedSpecies = relatedSpecies;
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
