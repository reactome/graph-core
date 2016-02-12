package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public abstract class Event extends DatabaseObject {

    private Boolean _doRelease;
    private String releaseDate;
    private String releaseStatus;

//    This is not longer used (TODO: Check it out)
//    private String keywords;

    private String speciesName;
    private String definition;
    private List<String> name;
    /**
     * A simple label to indicate if this Event object is a disease
     */
    private Boolean isInDisease;
    /**
     * A simple flag to indicate if this Event is inferred from another
     */
    private Boolean isInferred;

    /**
     * graph contains only relationships to Regulation
     * positive and negativeRegulators have to be filled in service
     */
    private List<PhysicalEntity> positiveRegulators;
    private List<PhysicalEntity> negativeRegulators;

    /**
     * inferred and orthologous contain the same data - error in data
     * now only inferredTo will exist in the graph
     * inferredFrom will be filled by the incoming relationship
     * orthologous events will be filled in service
     */
    private List<Event> orthologousEvent;

    @Relationship(type = "evidenceType")
    private EvidenceType evidenceType;

    @Relationship(type = "goBiologicalProcess")
    private GO_BiologicalProcess goBiologicalProcess;

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

    @Relationship(type = "summation")
    private List<Summation> summation;

    @Relationship(type = "figure")
    private List<Figure> figure;

    @Relationship(type = "precedingEvent")
    private List<Event> precedingEvent;

    @Relationship(type = "precedingEvent", direction=Relationship.INCOMING)
    private List<Event> followingEvent;

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    @Relationship(type = "regulatedBy")
    private List<NegativeRegulation> negativeRegulations;

    @Relationship(type = "regulatedBy")
    private List<PositiveRegulation> positiveRegulations;

    @Relationship(type = "regulatedBy")
    private List<Requirement> requirements;

    @Relationship(type = "crossReference")
    private List<DatabaseIdentifier> crossReference;
    
    @Relationship(type = "disease")
    private List<Disease> disease;

    @Relationship(type = "inferredTo")
    private List<Event> inferredTo;

    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    private List<Event> inferredFrom;

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

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
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

    public List<PhysicalEntity> getPositiveRegulators() {
        return positiveRegulators;
    }

    public void setPositiveRegulators(List<PhysicalEntity> positiveRegulators) {
        this.positiveRegulators = positiveRegulators;
    }

    public List<PhysicalEntity> getNegativeRegulators() {
        return negativeRegulators;
    }

    public void setNegativeRegulators(List<PhysicalEntity> negativeRegulators) {
        this.negativeRegulators = negativeRegulators;
    }

    public List<Event> getOrthologousEvent() {
        return orthologousEvent;
    }

    public void setOrthologousEvent(List<Event> orthologousEvent) {
        this.orthologousEvent = orthologousEvent;
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

    public List<NegativeRegulation> getNegativeRegulations() {
        return negativeRegulations;
    }

    public void setNegativeRegulations(List<NegativeRegulation> negativeRegulations) {
        this.negativeRegulations = negativeRegulations;
    }

    public List<PositiveRegulation> getPositiveRegulations() {
        return positiveRegulations;
    }

    public void setPositiveRegulations(List<PositiveRegulation> positiveRegulations) {
        this.positiveRegulations = positiveRegulations;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
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

    public List<Event> getInferredTo() {
        return inferredTo;
    }

    public void setInferredTo(List<Event> inferredTo) {
        this.inferredTo = inferredTo;
    }

    public List<Event> getInferredFrom() {
        return inferredFrom;
    }

    public void setInferredFrom(List<Event> inferredFrom) {
        this.inferredFrom = inferredFrom;
    }

    public List<Compartment> getCompartment() {
        return compartment;
    }

    public void setCompartment(List<Compartment> compartment) {
        this.compartment = compartment;
    }
}
