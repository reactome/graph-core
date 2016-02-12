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
 * how to generally manage a doubly-linked list using SDN 4, specifically for the case where the underlying graph uses a single relationship type between nodes, e.g.

 (post:Post)-[:NEXT]->(post:Post)

 What you can't do

 Due to limitations in the mapping framework, it is not possible to reliably declare the same relationship type twice in two different directions in your object model, i.e. this (currently) will not work:

 class Post {
@Relationship(type="NEXT", direction=Relationship.OUTGOING)
Post next;

@Relationship(type="NEXT", direction=Relationship.INCOMING)
Post previous;
}

 What you can do

 Instead we can combine the @Transient annotation with the use of annotated setter methods to obtain the desired result:

 class Post {
 Post next;

 @Transient Post previous;

 @Relationship(type="NEXT", direction=Relationship.OUTGOING)
 public void setNext(Post next) {
 this.next = next;
 if (next != null) {
 next.previous = this;
 }
 }
 }

 As a final point, if you then wanted to be able to navigate forwards and backwards through the entire list of Posts from any starting Post without having to continually refetch them from the database, you can set the fetch depth to -1 when you load the post, e.g:

 findOne(post.getId(), -1);

 Bear in mind that an infinite depth query will fetch every reachable object in the graph from the matched one, so use it with care!
 */
@SuppressWarnings("unused")
@NodeEntity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="dbId")
public abstract class DatabaseObject implements Serializable, Comparable<DatabaseObject> {

    public transient boolean isLoaded = false;

    @GraphId
    private Long id;

    private Long dbId;
    private String stableIdentifier;
    private String displayName;
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

    public void setStableIdentifier(StableIdentifier stableIdentifier) {
        if (stableIdentifier == null) return;
        stableIdentifier.load();
        this.stableIdentifier = stableIdentifier.getIdentifier();
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

    /**
     * Loads the content from the Reactome relational database (MySQL)
     */
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
                instance.deflate();
                instance = null;
            }
        }
        return (T) this;
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

    /**
     * sorting needed for junit tests
     * @param o
     * @return
     */
    @Override
    public int compareTo(DatabaseObject o) {
        return this.dbId.compareTo(o.dbId);
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
