package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;

import java.util.List;

/**
 * A regulator that is required for an Event/CatalystActivity to happen.
 */
@SuppressWarnings("unused")
@NodeEntity
public abstract class Regulation extends DatabaseObject {

    @ReactomeProperty
    private String releaseDate;

    @ReactomeProperty
    @Relationship(type = "activeUnit")
    private List<PhysicalEntity> activeUnit;

    @ReactomeProperty
    @Relationship(type = "activity")
    private GO_MolecularFunction activity;

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    private InstanceEdit authored;

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    private List<InstanceEdit> edited;

    @Relationship(type = "goBiologicalProcess")
    private GO_BiologicalProcess goBiologicalProcess;

    @Relationship(type = "inferredTo")
    private List<Regulation> inferredTo;

    @ReactomeTransient
    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    private List<Regulation> inferredFrom;

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    @ReactomeTransient
    @Relationship(type = "regulatedBy", direction = Relationship.INCOMING)
    private ReactionLikeEvent regulatedEntity;

    @Relationship(type = "regulator")
    private PhysicalEntity regulator;

    @Relationship(type = "reviewed", direction = Relationship.INCOMING)
    private List<InstanceEdit> reviewed;

    @Relationship(type = "revised", direction = Relationship.INCOMING)
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

    @Relationship(type = "activeUnit")
    public List<PhysicalEntity> getActiveUnit() {
        return activeUnit;
    }

    @Relationship(type = "activeUnit")
    public void setActiveUnit(List<PhysicalEntity> activeUnit) {
        this.activeUnit = activeUnit;
    }

    @Relationship(type = "activity")
    public GO_MolecularFunction getActivity() {
        return activity;
    }

    @Relationship(type = "activity")
    public void setActivity(GO_MolecularFunction activity) {
        this.activity = activity;
    }

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    public InstanceEdit getAuthored() {
        return authored;
    }

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    public void setAuthored(InstanceEdit authored) {
        this.authored = authored;
    }

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    public List<InstanceEdit> getEdited() {
        return edited;
    }

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    public void setEdited(List<InstanceEdit> edited) {
        this.edited = edited;
    }

    public GO_BiologicalProcess getGoBiologicalProcess() {
        return goBiologicalProcess;
    }

    @Relationship(type = "goBiologicalProcess")
    public void setGoBiologicalProcess(GO_BiologicalProcess goBiologicalProcess) {
        this.goBiologicalProcess = goBiologicalProcess;
    }

    @Relationship(type = "inferredTo")
    public List<Regulation> getInferredTo() {
        return inferredTo;
    }

    @Relationship(type = "inferredTo")
    public void setInferredTo(List<Regulation> inferredTo) {
        this.inferredTo = inferredTo;
    }

    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    public List<Regulation> getInferredFrom() {
        return inferredFrom;
    }

    @Relationship(type = "inferredTo", direction = Relationship.INCOMING)
    public void setInferredFrom(List<Regulation> inferredFrom) {
        this.inferredFrom = inferredFrom;
    }

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    @Relationship(type = "literatureReference")
    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }

    @Relationship(type = "regulatedBy", direction = Relationship.INCOMING)
    public ReactionLikeEvent getRegulatedEntity() {
        return regulatedEntity;
    }

    @Relationship(type = "regulatedBy", direction = Relationship.INCOMING)
    public void setRegulatedEntity(ReactionLikeEvent regulatedEntity) {
        this.regulatedEntity = regulatedEntity;
    }

    public PhysicalEntity getRegulator() {
        return regulator;
    }

    @Relationship(type = "regulator")
    public void setRegulator(PhysicalEntity regulator) {
        this.regulator = regulator;
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

    public List<Summation> getSummation() {
        return summation;
    }

    @Relationship(type = "summation")
    public void setSummation(List<Summation> summation) {
        this.summation = summation;
    }
}
