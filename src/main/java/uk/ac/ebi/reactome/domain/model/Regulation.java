package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Regulation extends DatabaseObject {

    private String releaseDate;
    private List<String> name;

    @Relationship(type = "authored")
    private InstanceEdit authored;

    @Relationship(type = "edited")
    private List<InstanceEdit> edited;

    @Relationship(type = "figure")
    private List<Figure> figure;

    @Relationship(type = "literatureReference")
    private List<Publication> literatureReference;

    @Relationship(type = "regulatedEntity")
    private DatabaseObject regulatedEntity;

    @Relationship(type = "regulationType")
    private RegulationType regulationType;

    @Relationship(type = "regulator")
    private DatabaseObject regulator; // regulator

    @Relationship(type = "reviewed")
    private List<InstanceEdit> reviewed;

    @Relationship(type = "revised")
    private List<InstanceEdit> revised;

    @Relationship(type = "summation")
    private List<Summation> summation;

    @Relationship(type = "containedInPathway")
    private List<Pathway> containedInPathway;
    
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

//    public Regulator getRegulator() {
//        return regulator;
//    }
//
//    public void setRegulator(Regulator regulator) {
//        this.regulator = regulator;
//    }


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

    public List<Pathway> getContainedInPathway() {
        return containedInPathway;
    }

    public void setContainedInPathway(List<Pathway> containedInPathway) {
        this.containedInPathway = containedInPathway;
    }
}
