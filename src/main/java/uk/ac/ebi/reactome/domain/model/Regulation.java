package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.domain.annotations.ReactomeProperty;
import uk.ac.ebi.reactome.domain.annotations.ReactomeTransient;

import java.util.List;

/**
 * A regulator that is required for an Event/CatalystActivity to happen.
 */
@SuppressWarnings("unused")
@NodeEntity
public class Regulation extends DatabaseObject {

    @ReactomeProperty
    private String releaseDate;
    @ReactomeProperty
    private List<String> name;

    @Relationship(type = "authored", direction = Relationship.INCOMING)
    private InstanceEdit authored;

    @Relationship(type = "containedInPathway", direction = Relationship.OUTGOING)
    private List<Pathway> containedInPathway;

    @Relationship(type = "edited", direction = Relationship.INCOMING)
    private List<InstanceEdit> edited;

    @Relationship(type = "figure", direction = Relationship.OUTGOING)
    private List<Figure> figure;

    @Relationship(type = "literatureReference", direction = Relationship.OUTGOING)
    private List<Publication> literatureReference;

    @ReactomeTransient
    @Relationship(type = "regulatedBy", direction = Relationship.INCOMING)
    private DatabaseObject regulatedEntity;

    @Relationship(type = "regulationType", direction = Relationship.OUTGOING)
    private RegulationType regulationType;

    @Relationship(type = "regulator", direction = Relationship.OUTGOING)
    private DatabaseObject regulator;

    @Relationship(type = "reviewed", direction = Relationship.INCOMING)
    private List<InstanceEdit> reviewed;

    @Relationship(type = "revised", direction = Relationship.INCOMING)
    private List<InstanceEdit> revised;

    @Relationship(type = "summation", direction = Relationship.OUTGOING)
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

    public InstanceEdit getAuthored() {
        return authored;
    }

    public void setAuthored(InstanceEdit authored) {
        this.authored = authored;
    }

    public List<Pathway> getContainedInPathway() {
        return containedInPathway;
    }

    public void setContainedInPathway(List<Pathway> containedInPathway) {
        this.containedInPathway = containedInPathway;
    }

    public List<InstanceEdit> getEdited() {
        return edited;
    }

    public void setEdited(List<InstanceEdit> edited) {
        this.edited = edited;
    }

    public List<Figure> getFigure() {
        return figure;
    }

    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }

    public List<Publication> getLiteratureReference() {
        return literatureReference;
    }

    public void setLiteratureReference(List<Publication> literatureReference) {
        this.literatureReference = literatureReference;
    }

    public DatabaseObject getRegulatedEntity() {
        return regulatedEntity;
    }

    public void setRegulatedEntity(DatabaseObject regulatedEntity) {
        this.regulatedEntity = regulatedEntity;
    }

    public RegulationType getRegulationType() {
        return regulationType;
    }

    public void setRegulationType(RegulationType regulationType) {
        this.regulationType = regulationType;
    }

    public DatabaseObject getRegulator() {
        return regulator;
    }

    public void setRegulator(DatabaseObject regulator) {
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
