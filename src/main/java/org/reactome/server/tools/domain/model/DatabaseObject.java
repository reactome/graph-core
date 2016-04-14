package org.reactome.server.tools.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.tools.domain.annotations.ReactomeProperty;
import org.reactome.server.tools.domain.annotations.ReactomeTransient;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 *  DatabaseObject contains the minimum fields used to define an instance of an Reactome entry
 *
 *
 *  DO NOT USE ObjectIdGenerators.PropertyGenerator !!!!! will cause memory paging
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
    private String stableIdentifier;

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

    public String getStableIdentifier() {
        return stableIdentifier;
    }

    public void setStableIdentifier(String stableIdentifier) {
        this.stableIdentifier = stableIdentifier;
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
                (stableIdentifier == null ? "dbId=" + dbId : "") +
                (stableIdentifier != null ? ", stableIdentifier='" + stableIdentifier + '\'' : "") +
                ", displayName='" + displayName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatabaseObject that = (DatabaseObject) o;
        return dbId != null ? dbId.equals(that.dbId) : that.dbId == null && (stableIdentifier != null ? stableIdentifier.equals(that.stableIdentifier) : that.stableIdentifier == null && !(displayName != null ? !displayName.equals(that.displayName) : that.displayName != null));
    }

    @Override
    public int hashCode() {
        int result = dbId != null ? dbId.hashCode() : 0;
        result = 31 * result + (stableIdentifier != null ? stableIdentifier.hashCode() : 0);
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
