package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class PhysicalEntity extends DatabaseObject implements Regulator {

    @Relationship
    private InstanceEdit authored;
    private String definition;
    @Relationship

    private GO_CellularComponent goCellularComponent;
    private String shortName;
    @Relationship
    private List<PhysicalEntity> inferredFrom;
    @Relationship
    private List<PhysicalEntity> inferredTo;
    @Relationship
    private List<Figure> figure;
    @Relationship
    private List<Summation> summation;
    @Relationship
    private List<InstanceEdit> edited;
    @Relationship
    private List<InstanceEdit> reviewed;
    @Relationship
    private List<InstanceEdit> revised;
    @Relationship
    private List<String> name;
    @Relationship
    private List<EntityCompartment> compartment;
    @Relationship
    private List<DatabaseIdentifier> crossReference;
    @Relationship
    private List<Disease> disease;
    @Relationship
    private List<Publication> literatureReference;
    // The following properties are used for detailed view
    @Relationship
    private List<Event> catalyzedEvent; // List of Events catalysed by this PE
    @Relationship
    private List<GO_MolecularFunction> goActivity; // List of GO MF related to this PE via CatalystActivity
    @Relationship
    private List<Event> inhibitedEvent;
    @Relationship
    private List<Event> activatedEvent;
    @Relationship
    private List<Event> requiredEvent;
    @Relationship
    private List<Event> producedByEvent;
    @Relationship
    private List<Event> consumedByEvent;
    private String systematicName;
//  @Relationship
//  private List<Domain> hasDomain;
    
    public PhysicalEntity() {
        
    }

    public String getSystematicName() {
        return systematicName;
    }

    public void setSystematicName(String systematicName) {
        this.systematicName = systematicName;
    }

    public InstanceEdit getAuthored() {
        return authored;
    }

    public void setAuthored(InstanceEdit authored) {
        this.authored = authored;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public GO_CellularComponent getGoCellularComponent() {
        return goCellularComponent;
    }

    public void setGoCellularComponent(GO_CellularComponent goCellularComponent) {
        this.goCellularComponent = goCellularComponent;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<PhysicalEntity> getInferredFrom() {
        return inferredFrom;
    }

    public void setInferredFrom(List<PhysicalEntity> inferredFrom) {
        this.inferredFrom = inferredFrom;
    }

    public List<PhysicalEntity> getInferredTo() {
        return inferredTo;
    }

    public void setInferredTo(List<PhysicalEntity> inferredTo) {
        this.inferredTo = inferredTo;
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
    
    public void addCrossReference(DatabaseIdentifier dbi) {
        if (crossReference == null)
            crossReference = new ArrayList<>();
        // Avoid duplication
        for (DatabaseIdentifier tmp : crossReference) {
            if (tmp.getDbId().equals(dbi.getDbId()))
                return;
        }
        crossReference.add(dbi);
    }

    public List<Event> getCatalyzedEvent() {
        return catalyzedEvent;
    }

    public void setCatalyzedEvent(List<Event> catalyzedEvent) {
        this.catalyzedEvent = catalyzedEvent;
    }

    public List<GO_MolecularFunction> getGoActivity() {
        return goActivity;
    }

    public void setGoActivity(List<GO_MolecularFunction> goActivity) {
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

//	public List<Domain> getHasDomain() {
//		return hasDomain;
//	}
//
//	public void setHasDomain(List<Domain> hasDomain) {
//		this.hasDomain = hasDomain;
//	}
    
}
