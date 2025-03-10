package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class DeletedInstance extends MetaDatabaseObject {

    @ReactomeProperty(originName = "class")
    private String clazz;

    @ReactomeProperty(originName = "deletedInstanceDB_ID")
    private Integer deletedInstanceDbId;

    @ReactomeProperty
    private String name;

    @ReactomeProperty(addedField = true)
    private String deletedStId;

    @Relationship(type = Relationships.SPECIES)
    private List<Taxon> species;

    public DeletedInstance() {
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Integer getDeletedInstanceDbId() {
        return deletedInstanceDbId;
    }

    public void setDeletedInstanceDbId(Integer deletedInstanceDbId) {
        this.deletedInstanceDbId = deletedInstanceDbId;
    }

    public String getDeletedStId() {
        return deletedStId;
    }

    public void setDeletedStId(String deletedStId) {
        this.deletedStId = deletedStId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Taxon> getSpecies() {
        return species;
    }

    public void setSpecies(List<Taxon> species) {
        this.species = species;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        //todo
        return "DeletedInstance";
    }
}
