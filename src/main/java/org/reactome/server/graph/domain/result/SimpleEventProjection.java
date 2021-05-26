package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.List;

/**
 * SimpleEventProjection is a minimalist object that is used to build Pathway tables with minimum information required.
 * e.g Authored Pathways or Reviewed Reactions. Those table can be extremely heavy to load an object.
 */
public class SimpleEventProjection {
    @Id private Long dbId;
    private String stId;
    private String displayName;
    private String speciesName;
    private String schemaClass;
    private String dateTime;
    private Long authorDbId;
    private String doi;
    private List<String> labels;

    private SimpleEventProjection() { }

    public static SimpleEventProjection build(Record valueNode) {
        SimpleEventProjection simpleEventProjection = new SimpleEventProjection();
        simpleEventProjection.setDbId(valueNode.get("dbId").asLong(0));
        simpleEventProjection.setStId(valueNode.get("stId").asString(null));
        simpleEventProjection.setDisplayName(valueNode.get("displayName").asString(null));
        simpleEventProjection.setSchemaClass(valueNode.get("schemaClass").asString(null));
        simpleEventProjection.setSpeciesName(valueNode.get("speciesName").asString(null));
        simpleEventProjection.setDateTime(valueNode.get("dateTime").asString(null));
        simpleEventProjection.setAuthorDbId(valueNode.get("authorDbId").asLong(0));
        simpleEventProjection.setDoi(valueNode.get("doi").asString(null));
        simpleEventProjection.setLabels(valueNode.get("labels").asList(Value::asString));
        return simpleEventProjection;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getSchemaClass() {
        return schemaClass;
    }

    public void setSchemaClass(String schemaClass) {
        this.schemaClass = schemaClass;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Long getAuthorDbId() {
        return authorDbId;
    }

    public void setAuthorDbId(Long authorDbId) {
        this.authorDbId = authorDbId;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

}

