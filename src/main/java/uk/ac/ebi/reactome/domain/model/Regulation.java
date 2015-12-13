package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Regulation extends DatabaseObject {

    @Relationship
    private InstanceEdit authored;
    @Relationship
    private List<InstanceEdit> edited;
    @Relationship
    private List<Figure> figure;
    @Relationship
    private List<Publication> literatureReference;
    private List<String> name;
    @Relationship
    private DatabaseObject regulatedEntity;
    @Relationship
    private RegulationType regulationType;
    @Relationship
    private DatabaseObject regulator;

    private String releaseDate;
    @Relationship
    private List<InstanceEdit> reviewed;
    @Relationship
    private List<InstanceEdit> revised;
    @Relationship
    private List<Summation> summation;
    // New attribute in December, 2013
    @Relationship
    private List<Pathway> containedInPathway;
    
    public Regulation() {
    }
    
    public List<Pathway> getContainedInPathway() {
        return containedInPathway;
    }

    public void setContainedInPathway(List<Pathway> containedInPathway) {
        this.containedInPathway = containedInPathway;
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

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public DatabaseObject getRegulatedEntity() {
        return regulatedEntity;
    }

    public void setRegulatedEntity(DatabaseObject regulatedEntity) {
        if(regulatedEntity instanceof PhysicalEntity || regulatedEntity instanceof Event || regulatedEntity instanceof CatalystActivity) {
            this.regulatedEntity = regulatedEntity;
        } else {
            throw new RuntimeException(regulatedEntity + " is not a PhysicalEntity, Event or CatalystActivity");
        }
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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
