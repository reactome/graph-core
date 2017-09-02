package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeAllowedClasses;
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
    private List<String> name;

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    private InstanceEdit authored;

    @Relationship(type = "containedInPathway")
    private List<Pathway> containedInPathway;

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    private List<InstanceEdit> edited;

    @Relationship(type = "figure")
    private List<Figure> figure;

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
    @ReactomeAllowedClasses(allowed = {CatalystActivity.class, Event.class})
    private DatabaseObject regulatedEntity;

    @Relationship(type = "regulator")
    @ReactomeAllowedClasses(allowed = {CatalystActivity.class, Event.class, PhysicalEntity.class})
    private DatabaseObject regulator;

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

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    public InstanceEdit getAuthored() {
        return authored;
    }

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    public void setAuthored(InstanceEdit authored) {
        this.authored = authored;
    }

    public List<Pathway> getContainedInPathway() {
        return containedInPathway;
    }

    @Relationship(type = "containedInPathway")
    public void setContainedInPathway(List<Pathway> containedInPathway) {
        this.containedInPathway = containedInPathway;
    }

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    public List<InstanceEdit> getEdited() {
        return edited;
    }

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    public void setEdited(List<InstanceEdit> edited) {
        this.edited = edited;
    }

    public List<Figure> getFigure() {
        return figure;
    }

    @Relationship(type = "figure")
    public void setFigure(List<Figure> figure) {
        this.figure = figure;
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
    @ReactomeAllowedClasses(allowed = {CatalystActivity.class, Event.class})
    public DatabaseObject getRegulatedEntity() {
        return regulatedEntity;
    }

    @Relationship(type = "regulatedBy", direction = Relationship.INCOMING)
    public void setRegulatedEntity(DatabaseObject regulatedEntity) {
        this.regulatedEntity = regulatedEntity;
    }

    @ReactomeAllowedClasses(allowed = {CatalystActivity.class, Event.class, PhysicalEntity.class})
    public DatabaseObject getRegulator() {
        return regulator;
    }

    @Relationship(type = "regulator")
    public void setRegulator(DatabaseObject regulator) {
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
