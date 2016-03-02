package uk.ac.ebi.reactome.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.gk.model.GKInstance;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.data.DatabaseObjectFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 */
@SuppressWarnings("unused")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="dbId")
@NodeEntity
public abstract class DatabaseObject implements Serializable, Comparable<DatabaseObject> {

    public transient boolean isLoaded = false;

    @GraphId
    private Long id;
    @ReactomeTransient
    private Long dbId;
    @ReactomeTransient
    private String displayName;
    private String stableIdentifier;
    @ReactomeTransient
    private Date timestamp;

    @Relationship(type = "created", direction = Relationship.INCOMING)
    private InstanceEdit created;

    @Relationship(type = "modified", direction = Relationship.INCOMING)
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStableIdentifier() {
        return stableIdentifier;
    }

    // The logic in this method is only required for using the Gk-Instance during testing phase.
    public void setStableIdentifier(StableIdentifier stableIdentifier) {
        if (stableIdentifier == null) return;
        stableIdentifier.load();
        this.stableIdentifier = stableIdentifier.getIdentifier();
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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                (stableIdentifier == null ? "dbId=" + dbId : "") +
                (stableIdentifier != null ? ", stableIdentifier='" + stableIdentifier + '\'' : "") +
                ", displayName='" + displayName + '\'' +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof DatabaseObject)) return false;
//        DatabaseObject that = (DatabaseObject) o;
//        return getDbId().equals(that.getDbId());
//
//    }

    /**
     * In version 4, the dependency of the framework upon a particular style of equals() or hashCode()
     implementation has been removed. The graph-id field is directly checked to see if two entities
     represent the same node and a 64-bit hash code is used for dirty checking, so you’re not forced to write
     your code in a certain way!
     * @param o
     * @return
     */

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        DatabaseObject that = (DatabaseObject) o;
//
//        return dbId.equals(that.dbId);
//
//    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatabaseObject that = (DatabaseObject) o;

        if (dbId != null ? !dbId.equals(that.dbId) : that.dbId != null) return false;
        if (stableIdentifier != null ? !stableIdentifier.equals(that.stableIdentifier) : that.stableIdentifier != null)
            return false;
        return !(displayName != null ? !displayName.equals(that.displayName) : that.displayName != null);

    }


    @Override
    public int hashCode() {
        int result = dbId != null ? dbId.hashCode() : 0;
        result = 31 * result + (stableIdentifier != null ? stableIdentifier.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(DatabaseObject o) {
        return this.dbId.compareTo(o.dbId);
    }

    // Only needed during testing
    public String getSchemaClass() {
        return getClass().getSimpleName();
    }

    // Loads the content from the Reactome relational database (MySQL). This is only used during testing phase
    @SuppressWarnings("unchecked")
    public <T extends DatabaseObject> T load() {
        if (!isLoaded) {
            GKInstance instance = null;
            try {
                instance = DatabaseObjectFactory.getInstance(dbId);
                DatabaseObjectFactory.load(this, instance);
                isLoaded = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (instance != null) {
                    instance.deflate();
                }
            }
        }
        return (T) this;
    }

}
