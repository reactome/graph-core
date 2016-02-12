package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class PhysicalEntity extends DatabaseObject {

    private String definition;
//    private String shortName;
    private String systematicName;

    ///NOT FILLED BY GRAPH
    /**
     * List of Events catalysed by this PE
     */
    private List<Event> catalyzedEvent;

    /**
     * List of GO MF related to this PE via CatalystActivity
     */
    private List<CatalystActivity> goActivity;

    /**
     * list of regulated Events filled in service
     */
    private List<Event> inhibitedEvent;
    private List<Event> activatedEvent;
    private List<Event> requiredEvent;
    private List<Event> producedByEvent;
    private List<Event> consumedByEvent;


    @Relationship(type = "authored")
    private InstanceEdit authored;

    @Relationship(type = "goCellularComponent")
    private GO_CellularComponent goCellularComponent;

    @Relationship(type = "inferredTo", direction = Relationship.OUTGOING)
    private List<PhysicalEntity> inferredTo;

    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    private List<PhysicalEntity> inferredFrom;

    @Relationship(type = "figure")
    private List<Figure> figure;

    @Relationship(type = "summation")
    private List<Summation> summation;

    @Relationship(type = "edited")
    private List<InstanceEdit> edited;

    @Relationship(type = "reviewed")
    private List<InstanceEdit> reviewed;

    @Relationship(type = "revised")
    private List<InstanceEdit> revised;

    @Relationship(type = "name")
    private List<String> name;

    @Relationship(type = "compartment")
    private List<EntityCompartment> compartment;

    @Relationship(type = "crossReference")
    private List<DatabaseIdentifier> crossReference;

    @Relationship(type = "disease")
    private List<Disease> disease;

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    @Relationship(type = "physicalEntity", direction = Relationship.INCOMING)
    private List<CatalystActivity> catalystActivities;

    @Relationship(type = "regulator", direction = Relationship.INCOMING)
    private List<Regulation> regulations;


    public PhysicalEntity() {
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getSystematicName() {
        return systematicName;
    }

    public void setSystematicName(String systematicName) {
        this.systematicName = systematicName;
    }

    public List<Event> getCatalyzedEvent() {
        return catalyzedEvent;
    }

    public void setCatalyzedEvent(List<Event> catalyzedEvent) {
        this.catalyzedEvent = catalyzedEvent;
    }

    public List<CatalystActivity> getGoActivity() {
        return goActivity;
    }

    public void setGoActivity(List<CatalystActivity> goActivity) {
        this.goActivity = goActivity;
    }

    public List<Event> getInhibitedEvent() {
        return inhibitedEvent;
    }

    public void setInhibitedEvent(List<Event> inhibitedEvent) {
        this.inhibitedEvent = inhibitedEvent;
    }

    public List<Event> getActivatedEvent() {
        return activatedEvent;
    }

    public void setActivatedEvent(List<Event> activatedEvent) {
        this.activatedEvent = activatedEvent;
    }

    public List<Event> getRequiredEvent() {
        return requiredEvent;
    }

    public void setRequiredEvent(List<Event> requiredEvent) {
        this.requiredEvent = requiredEvent;
    }

    public List<Event> getProducedByEvent() {
        return producedByEvent;
    }

    public void setProducedByEvent(List<Event> producedByEvent) {
        this.producedByEvent = producedByEvent;
    }

    public List<Event> getConsumedByEvent() {
        return consumedByEvent;
    }

    public void setConsumedByEvent(List<Event> consumedByEvent) {
        this.consumedByEvent = consumedByEvent;
    }

    public InstanceEdit getAuthored() {
        return authored;
    }

    public void setAuthored(InstanceEdit authored) {
        this.authored = authored;
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

    public List<Figure> getFigure() {
        return figure;
    }

    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }

    public List<Summation> getSummation() {
        return summation;
    }

    public void setSummation(List<Summation> summation) {
        this.summation = summation;
    }

    public List<InstanceEdit> getEdited() {
        return edited;
    }

    public void setEdited(List<InstanceEdit> edited) {
        this.edited = edited;
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

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<EntityCompartment> getCompartment() {
        return compartment;
    }

    public void setCompartment(List<EntityCompartment> compartment) {
        this.compartment = compartment;
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

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }

    public List<CatalystActivity> getCatalystActivities() {
        return catalystActivities;
    }

    public void setCatalystActivities(List<CatalystActivity> catalystActivities) {
        this.catalystActivities = catalystActivities;
    }

    public List<Regulation> getRegulations() {
        return regulations;
    }

    public void setRegulations(List<Regulation> regulations) {
        this.regulations = regulations;
    }
}
