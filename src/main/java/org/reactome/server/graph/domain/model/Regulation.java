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
public abstract class Regulation extends DatabaseObject {

    @ReactomeProperty
    private String releaseDate;

    @ReactomeProperty
    @Relationship(type = "activeUnit")
    private List<PhysicalEntity> activeUnit;

    @ReactomeProperty
    @Relationship(type = "activity")
    private GO_MolecularFunction activity;

    @Relationship(type = "authored", direction = Relationship.Direction.INCOMING)
    private InstanceEdit authored;

    @Relationship(type = "edited", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> edited;

    @Relationship(type = "goBiologicalProcess")
    private GO_BiologicalProcess goBiologicalProcess;

    @Relationship(type = "inferredTo")
    private List<Regulation> inferredTo;

    @ReactomeTransient
    @Relationship(type = "inferredTo", direction = Relationship.Direction.INCOMING)
    private List<Regulation> inferredFrom;

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    @ReactomeTransient
    @Relationship(type = "regulatedBy", direction = Relationship.Direction.INCOMING)
    private List<ReactionLikeEvent> regulatedEntity;

    @Relationship(type = "regulator")
    private PhysicalEntity regulator;

    @Relationship(type = "reviewed", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> reviewed;

    @Relationship(type = "revised", direction = Relationship.Direction.INCOMING)
    private List<InstanceEdit> revised;

    @Relationship(type = "summation")
    private List<Summation> summation;

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
}
