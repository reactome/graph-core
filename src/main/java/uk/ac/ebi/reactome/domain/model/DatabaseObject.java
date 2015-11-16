package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * DatabaseObject is a wrapper class. All Objects in the model extend this class and therefor contain id, dbId, stId
 * and a name.
 */
@NodeEntity
public abstract class DatabaseObject {

    //id is a necessary unique identifier for Neo4j. It will be generated automatically.
    @SuppressWarnings("unused")
    @GraphId
    private Long id;

    private Long dbId;
    private String stId;
    private String name;

    public DatabaseObject(Long dbId, String stId, String name) {
        this.dbId = dbId;
        this.stId = stId;
        this.name = name;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
