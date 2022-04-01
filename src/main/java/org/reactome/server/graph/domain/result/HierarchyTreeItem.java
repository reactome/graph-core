package org.reactome.server.graph.domain.result;

import org.neo4j.driver.Value;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.List;

/**
 * HierarchyTreeItem is a minimalist object that is used to build the Pathway Browser Tree
 *
 * The HierarchyBranch is comprised of a Collection of PathwayResult
 */
public class HierarchyTreeItem {
    @Id private Long id;
    private String stId;
    private String displayName;
    private boolean hasDiagram;
    private String speciesName;
    private String schemaClass;
    private List<String> labels;
    private Integer order;

    public HierarchyTreeItem() { }

    public static HierarchyTreeItem build(Value valueNode) {
        HierarchyTreeItem hierarchyTreeItem = new HierarchyTreeItem();
        hierarchyTreeItem.setStId(valueNode.get(0).asString(null));
        hierarchyTreeItem.setDisplayName(valueNode.get(1).asString(null));
        hierarchyTreeItem.setHasDiagram(valueNode.get(2).asBoolean(false));
        hierarchyTreeItem.setSpeciesName(valueNode.get(3).asString(null));
        hierarchyTreeItem.setSchemaClass(valueNode.get(4).asString(null));
        hierarchyTreeItem.setLabels(valueNode.get(5).asList(Value::asString));
        hierarchyTreeItem.setOrder(valueNode.get(6).asInt());
        return hierarchyTreeItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isHasDiagram() {
        return hasDiagram;
    }

    public void setHasDiagram(boolean hasDiagram) {
        this.hasDiagram = hasDiagram;
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

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "PathwayResult{" +
                "stId='" + stId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", hasDiagram=" + hasDiagram +
                ", speciesName='" + speciesName + '\'' +
                ", schemaClass='" + schemaClass + '\'' +
                '}';
    }
}

