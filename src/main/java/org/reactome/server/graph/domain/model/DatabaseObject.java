package org.reactome.server.graph.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.reactome.server.graph.domain.annotations.ReactomeTransient;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 *  DatabaseObject contains the minimum fields used to define an instance of an Reactome entry
 *
 *
 *  For the JsonIdentityInfo, when assigning generator as ObjectIdGenerators.PropertyGenerator could
 *  slow down the json serialisation due to a paging problem. Right now the @JsonIgnore annotations
 *  have been added to avoid serialising the not necessary attributes, but in case those are removed
 *  the best thing is to remove the mentioned property
 */
@SuppressWarnings("unused")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="dbId")
@NodeEntity
public abstract class DatabaseObject implements Serializable, Comparable<DatabaseObject> {

    @ReactomeTransient
    public transient boolean isLoaded = false;

    @JsonIgnore
    @GraphId
    private Long id;

    @ApiModelProperty(value = "This is the main internal identifier of a Reactome entry", required = true)
    private Long dbId;

    @ApiModelProperty(value = "This is the main name of a Reactome entry", required = true)
    private String displayName;

    @ApiModelProperty(value = "This is the main external identifier of a Reactome entry")
    @ReactomeProperty
    private String stId;

    @ApiModelProperty(value = "Instance that created this entry")
    @Relationship(type = "created", direction = Relationship.INCOMING)
    private InstanceEdit created;

    @ApiModelProperty(value = "Last instance that modified this entry")
    @Relationship(type = "modified", direction = Relationship.INCOMING)
    private InstanceEdit modified;

    public DatabaseObject() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public InstanceEdit getCreated() {
        return created;
    }

    public void setCreated(InstanceEdit created) {
        this.created = created;
    }

    public InstanceEdit getModified() {
        return modified;
    }

    public void setModified(InstanceEdit modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                (stId == null ? "dbId=" + dbId : "") +
                (stId != null ? ", stId='" + stId + '\'' : "") +
                ", displayName='" + displayName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatabaseObject that = (DatabaseObject) o;
        return dbId != null ? dbId.equals(that.dbId) : that.dbId == null && (stId != null ? stId.equals(that.stId) : that.stId == null && !(displayName != null ? !displayName.equals(that.displayName) : that.displayName != null));
    }

    @Override
    public int hashCode() {
        int result = dbId != null ? dbId.hashCode() : 0;
        result = 31 * result + (stId != null ? stId.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@Nonnull DatabaseObject o) {
        return this.dbId.compareTo(o.dbId);
    }

    public String getSchemaClass() {
        return getClass().getSimpleName();
    }

    public static DatabaseObject emptyObject() {
        return new Pathway();
    }

    @JsonIgnore
    public String getExplanation() {
        return "Not available";
    }

    @JsonIgnore
    public String getClassName() {
        return getClass().getSimpleName().replaceAll("([A-Z])", " $1");
    }
}
