package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Value;

import java.util.List;

/**
 * This class is used by EventAncestors and HierarchyBranch when building Locations in the Pathway Browser Tree
 * When mapping Pathway or Reactions, more attributes are loaded and they are not needed, this class is loading
 * only essential attributes.
 */
public class EventProjection {

    private Long dbId;
    private String displayName;
    private String stId;
    private List<String> name;
    private String stIdVersion;
    private String oldStId;
    private String schemaClass;
    private String doi;
    private String speciesName;
    private String releaseDate;
    private String releaseStatus;
    private Boolean hasDiagram;
    private Boolean hasEHLD = false;
    private Integer diagramWidth;
    private Integer diagramHeight;
    private Boolean isInferred;
    private String category;
    private Boolean isInDisease;
    private String isCanonical;
    private String definition;

    public EventProjection() {

    }

    public EventProjection(Value item) {
        this.dbId = item.get(0).asLong();
        this.displayName = item.get(1).asString();
        this.name = item.get(2).asList(Value::asString);
        this.stId = item.get(3).asString();
        this.stIdVersion = item.get(4).asString(null);
        this.oldStId = item.get(5).asString(null);
        this.schemaClass = item.get(6).asString();
        this.doi = item.get(7).asString(null);
        this.speciesName = item.get(8).asString();
        this.releaseDate = item.get(9).asString(null);
        this.releaseStatus = item.get(10).asString(null);
        this.hasDiagram = item.get(11).asBoolean(false);
        this.hasEHLD = item.get(12).asBoolean(false);
        this.diagramHeight = item.get(13).asInt(0);
        this.diagramWidth = item.get(14).asInt(0);
        this.isInferred = item.get(15).asBoolean(false);
        this.category = item.get(16).asString(null);
        this.isInDisease = item.get(17).asBoolean(false);
        this.definition = item.get(18).asString(null);
        this.isCanonical = item.get(19).asString(null);
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public String getStIdVersion() {
        return stIdVersion;
    }

    public void setStIdVersion(String stIdVersion) {
        this.stIdVersion = stIdVersion;
    }

    public String getOldStId() {
        return oldStId;
    }

    public void setOldStId(String oldStId) {
        this.oldStId = oldStId;
    }

    public String getSchemaClass() {
        return schemaClass;
    }

    public void setSchemaClass(String schemaClass) {
        this.schemaClass = schemaClass;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public Boolean getHasDiagram() {
        return hasDiagram;
    }

    public void setHasDiagram(Boolean hasDiagram) {
        this.hasDiagram = hasDiagram;
    }

    public Boolean getHasEHLD() {
        return hasEHLD;
    }

    public void setHasEHLD(Boolean hasEHLD) {
        this.hasEHLD = hasEHLD;
    }

    public Integer getDiagramWidth() {
        return diagramWidth;
    }

    public void setDiagramWidth(Integer diagramWidth) {
        this.diagramWidth = diagramWidth;
    }

    public Integer getDiagramHeight() {
        return diagramHeight;
    }

    public void setDiagramHeight(Integer diagramHeight) {
        this.diagramHeight = diagramHeight;
    }

    public Boolean getInferred() {
        return isInferred;
    }

    public void setInferred(Boolean inferred) {
        isInferred = inferred;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getInDisease() {
        return isInDisease;
    }

    public void setInDisease(Boolean inDisease) {
        isInDisease = inDisease;
    }

    public String getIsCanonical() {
        return isCanonical;
    }

    public void setIsCanonical(String isCanonical) {
        this.isCanonical = isCanonical;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}

