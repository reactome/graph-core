package org.reactome.server.graph.domain.result;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 03.02.16.
 *
 * Object for retrieving Ewases and their ReferenceEntities for a given Pathway Id
 */
@SuppressWarnings("unused")
@QueryResult
public class Participant {

    private Long peDbId;
    private String displayName;
    private Collection<Object> refEntities;

    public Participant() {
    }

    public Long getPeDbId() {
        return peDbId;
    }

    public void setPeDbId(Long peDbId) {
        this.peDbId = peDbId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Collection<Object> getRefEntities() {
        return refEntities;
    }

    public void setRefEntities(Collection<Object> refEntities) {
        this.refEntities = refEntities;
    }
}
