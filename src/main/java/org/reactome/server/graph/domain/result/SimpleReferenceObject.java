package org.reactome.server.graph.domain.result;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 05.06.16.
 */
@QueryResult
@SuppressWarnings("unused")
public class SimpleReferenceObject {

    private Long dbId;
    private String identifier;
    private String databaseName;

    public SimpleReferenceObject() {
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
