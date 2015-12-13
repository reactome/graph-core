package uk.ac.ebi.reactome.domain.model;

import org.gk.model.GKInstance;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import uk.ac.ebi.reactome.service.DatabaseObjectService;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;
import java.util.List;

//@Component
@NodeEntity
public abstract class DatabaseObject implements java.io.Serializable {

    public boolean isLoaded = false;

    @GraphId
    private Long id;

    private Long dbId;

    private String stableIdentifier;

    private String displayName;

    private Date timestamp;

    @Relationship
    private InstanceEdit created;

    @Relationship
    private List<InstanceEdit> modified;

    public DatabaseObject() {
    }

    public List<InstanceEdit> getModified() {
        return modified;
    }

    public void setModified(List<InstanceEdit> modified) {
        this.modified = modified;
    }

    public Long getDbId() {
        return this.dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    @XmlElement
    public String getSchemaClass() {
        return getClass().getSimpleName();
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public InstanceEdit getCreated() {
        return this.created;
    }

    public void setCreated(InstanceEdit created) {
        this.created = created;
    }

    public String getStableIdentifier() {
        return this.stableIdentifier;
    }

    public String getStId() {
        return this.stableIdentifier;
    }

    public void setStableIdentifier(StableIdentifier stableIdentifier) {
        stableIdentifier.load();
        this.stableIdentifier = stableIdentifier.getIdentifier();
    }

//    public void setStableIdentifier(String stableIdentifier) {
//        this.stableIdentifier = stableIdentifier;
//    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                (stableIdentifier == null ? "dbId=" + dbId : "") +
                (stableIdentifier != null ? ", stableIdentifier='" + stableIdentifier + '\'' : "") +
                ", displayName='" + displayName + '\'' +
                '}';
    }

//    @Autowired DatabaseObjectFactory databaseObjectFactory;

    /**
     * Loads the content from the Reactome relational database (MySQL)
     *
     * @return
     */
    public <T extends DatabaseObject> T load() {
        if (!isLoaded) {
            try {
                GKInstance instance = DatabaseObjectFactory.getInstance(dbId + "");
                DatabaseObjectFactory.fill(this, instance);
                isLoaded = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) this;
    }

//     @Autowired DatabaseObjectService databaseObjectService;
//
//    /**
//     * Saves the content to the Reactome graph database (Neo4J)
//     */
    public void save(DatabaseObjectService databaseObjectService) {
        databaseObjectService.save(this);
    }
}
