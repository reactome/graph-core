package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.management.relation.Relation;
import java.util.List;

@Node
public class Deleted extends MetaDatabaseObject{

    @ReactomeProperty
    private String curatorComment;

    @Relationship(type = Relationships.DELETED_INSTANCE)
    private List<DeletedInstance> deletedInstance;

    @Relationship(type= Relationships.REASON)
    private DeletedControlledVocabulary reason;

    @Relationship(type=Relationships.REPLACEMENT_INSTANCES)
    private List<Deletable> replacementInstances;

    @Deprecated
    @ReactomeProperty(originName = "deletedInstanceDB_ID")
    private List<Integer> deletedInstanceDbId;

    public Deleted() {
    }

    public String getCuratorComment() {
        return curatorComment;
    }

    public void setCuratorComment(String curatorComment) {
        this.curatorComment = curatorComment;
    }

    public List<DeletedInstance> getDeletedInstance() {
        return deletedInstance;
    }

    public void setDeletedInstance(List<DeletedInstance> deletedInstance) {
        this.deletedInstance = deletedInstance;
    }

    public DeletedControlledVocabulary getReason() {
        return reason;
    }

    public void setReason(DeletedControlledVocabulary reason) {
        this.reason = reason;
    }

    public List<Deletable> getReplacementInstances() {
        return replacementInstances;
    }

    public void setReplacementInstances(List<Deletable> replacementInstances) {
        this.replacementInstances = replacementInstances;
    }

    @Deprecated
    public List<Integer> getDeletedInstanceDbId() {
        return deletedInstanceDbId;
    }

    @Deprecated
    public void setDeletedInstanceDbId(List<Integer> deletedInstanceDbId) {
        this.deletedInstanceDbId = deletedInstanceDbId;
    }

    @ReactomeSchemaIgnore
    @Override
    @JsonIgnore
    public String getExplanation() {
        //todo
        return "Deleted";
    }
}
