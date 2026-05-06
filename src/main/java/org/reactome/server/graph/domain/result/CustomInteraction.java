package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;

import java.util.Collections;
import java.util.List;

/**
 *  Using this result for rendering the interactor table in Angular PWB
 */
public class CustomInteraction {

    Long dbId;
    String identifier;
    Double score;
    Long evidenceCount;
    String url;
    String evidenceURL;
    List<String> geneName;
    String databaseName;
    Long entitiesCount;
    //Species species;
    String speciesName;
    String displayName;
    String variantIdentifier; //variantIdentifier: "Q99IB8-PRO_0000045599"



    public CustomInteraction(Record record) {
        dbId = record.get("dbId").asLong();
        identifier = record.get("identifier").asString();
        score = record.get("score").asDouble();
        evidenceCount = record.get("evidenceCount").asLong();
        url = record.get("url").asString();
        evidenceURL = record.get("evidenceURL").asString();
        geneName = !record.get("geneName").isNull()? record.get("geneName").asList(Value::asString) : Collections.emptyList();
        databaseName = record.get("databaseName").asString();
        entitiesCount = record.get("entitiesCount").asLong();
       // species = !record.get("species").isNull() ? ReflectionUtils.build(new Species(), record.get("species")) : null;
        speciesName = record.get("speciesName").asString(null);
        displayName = record.get("displayName").asString();
        variantIdentifier = record.get("variantIdentifier").asString(null);
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getEvidenceCount() {
        return evidenceCount;
    }

    public void setEvidenceCount(Long evidenceCount) {
        this.evidenceCount = evidenceCount;
    }

    public String getEvidenceURL() {
        return evidenceURL;
    }

    public void setEvidenceURL(String evidenceURL) {
        this.evidenceURL = evidenceURL;
    }

    public List<String> getGeneName() {
        return geneName;
    }

    public void setGeneName(List<String> geneName) {
        this.geneName = geneName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Long getEntitiesCount() {
        return entitiesCount;
    }

    public void setEntitiesCount(Long entitiesCount) {
        this.entitiesCount = entitiesCount;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getVariantIdentifier() {
        return variantIdentifier;
    }

    public void setVariantIdentifier(String variantIdentifier) {
        this.variantIdentifier = variantIdentifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
