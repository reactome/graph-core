package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public abstract class DatabaseObject implements java.io.Serializable {

    public transient boolean isLoaded = false;

    @GraphId
    private Long id;

    private Long dbId;

    private String stableIdentifier;
    @Property
    private String displayName;
    @Property
    private Date timestamp;

    @Relationship(type = "created")
    private InstanceEdit created;

    @Relationship(type = "modified")
    private List<InstanceEdit> modified;

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

    public String getStableIdentifier() {
        return stableIdentifier;
    }

    public void setStableIdentifier(String stableIdentifier) {
        this.stableIdentifier = stableIdentifier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public InstanceEdit getCreated() {
        return created;
    }

    public void setCreated(InstanceEdit created) {
        this.created = created;
    }

    public List<InstanceEdit> getModified() {
        return modified;
    }

    public void setModified(List<InstanceEdit> modified) {
        this.modified = modified;
    }

    public String getSchemaClass() {
        return getClass().getSimpleName();
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                (stableIdentifier == null ? "dbId=" + dbId : "") +
                (stableIdentifier != null ? ", stableIdentifier='" + stableIdentifier + '\'' : "") +
                ", displayName='" + displayName + '\'' +
                '}';
    }

    //    public List<InstanceEdit> getModified() {
//        return modified;
//    }
//
//    public void setModified(List<InstanceEdit> modified) {
//        this.modified = modified;
//    }
//
//    public Long getDbId() {
//        return this.dbId;
//    }
//
//    public void setDbId(Long dbId) {
//        this.dbId = dbId;
//    }
//
//    @XmlElement
//    public String getSchemaClass() {
//        return getClass().getSimpleName();
//    }
//
//    public String getDisplayName() {
//        return this.displayName;
//    }
//
//    public void setDisplayName(String displayName) {
//        this.displayName = displayName;
//    }
//
//    public Date getTimestamp() {
//        return this.timestamp;
//    }
//
//    public void setTimestamp(Date timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    public InstanceEdit getCreated() {
//        return this.created;
//    }
//
//    public void setCreated(InstanceEdit created) {
//        this.created = created;
//    }
//
//    public String getStableIdentifier() {
//        return this.stableIdentifier;
//    }
//
//    public String getStId() {
//        return this.stableIdentifier;
//    }
//    public void setStableIdentifier(String stableIdentifier) {
//        this.stableIdentifier = stableIdentifier;
//    }

}
