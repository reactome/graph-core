package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

/**
 * A regulator that is required for an Event/CatalystActivity to happen.
 */
@SuppressWarnings("unused")
@Node
public abstract class Regulation extends DatabaseObject implements Deletable {

    @ReactomeProperty
    private String releaseDate;

    @ReactomeProperty
    @Relationship(type = Relationships.ACTIVE_UNIT)
    private List<PhysicalEntity> activeUnit;

    @ReactomeProperty
    @Relationship(type = Relationships.ACTIVITY)
    private GO_MolecularFunction activity;

    @Relationship(type = Relationships.AUTHORED, direction = Relationship.Direction.INCOMING)
    private InstanceEdit authored;

    @Relationship(type = Relationships.EDITED, direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> edited;

    @Relationship(type =  Relationships.GO_BIOLOGICAL_PROCESS)
    private GO_BiologicalProcess goBiologicalProcess;

    @Relationship(type = Relationships.INFERRED_TO)
    private List<Regulation> inferredTo;

    @ReactomeTransient
    @Relationship(type =  Relationships.INFERRED_TO, direction = Relationship.Direction.INCOMING)
    private List<Regulation> inferredFrom;

    @Relationship(type = Relationships.LITERATURE_REFERENCE)
    private List<Publication> literatureReference;

    @ReactomeTransient
    @Relationship(type = Relationships.REGULATED_BY, direction = Relationship.Direction.INCOMING)
    private List<ReactionLikeEvent> regulatedEntity;

    @Relationship(type = Relationships.REGULATOR)
    private PhysicalEntity regulator;

    @Relationship(type = Relationships.REVIEWED, direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> reviewed;

    @Relationship(type = Relationships.REVISED, direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> revised;

    @Relationship(type = Relationships.SUMMATION)
    private List<Summation> summation;

    @ReactomeTransient
    @Relationship(type = Relationships.REPLACEMENT_INSTANCES, direction = Relationship.Direction.INCOMING)
    private List<Deleted> deleted;

    public Regulation() {}

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<PhysicalEntity> getActiveUnit() {
        return activeUnit;
    }

    public void setActiveUnit(List<PhysicalEntity> activeUnit) {
        this.activeUnit = activeUnit;
    }

    public GO_MolecularFunction getActivity() {
        return activity;
    }

    public void setActivity(GO_MolecularFunction activity) {
        this.activity = activity;
    }

    public InstanceEdit getAuthored() {
        return authored;
    }

    public void setAuthored(InstanceEdit authored) {
        this.authored = authored;
    }

    public List<InstanceEdit> getEdited() {
        return edited;
    }

    public void setEdited(List<InstanceEdit> edited) {
        this.edited = edited;
    }

    public GO_BiologicalProcess getGoBiologicalProcess() {
        return goBiologicalProcess;
    }

    public void setGoBiologicalProcess(GO_BiologicalProcess goBiologicalProcess) {
        this.goBiologicalProcess = goBiologicalProcess;
    }

    public List<Regulation> getInferredTo() {
        return inferredTo;
    }

    public void setInferredTo(List<Regulation> inferredTo) {
        this.inferredTo = inferredTo;
    }

    public List<Regulation> getInferredFrom() {
        return inferredFrom;
    }

    public void setInferredFrom(List<Regulation> inferredFrom) {
        this.inferredFrom = inferredFrom;
    }

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }

    public List<ReactionLikeEvent> getRegulatedEntity() {
        return regulatedEntity;
    }

    public void setRegulatedEntity(List<ReactionLikeEvent> regulatedEntity) {
        this.regulatedEntity = regulatedEntity;
    }

    public PhysicalEntity getRegulator() {
        return regulator;
    }

    public void setRegulator(PhysicalEntity regulator) {
        this.regulator = regulator;
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

    public List<Summation> getSummation() {
        return summation;
    }

    public void setSummation(List<Summation> summation) {
        this.summation = summation;
    }

    @Override
    public List<Deleted> getDeleted() {
        return deleted;
    }

    public void setDeleted(List<Deleted> deleted) {
        this.deleted = deleted;
    }
}
