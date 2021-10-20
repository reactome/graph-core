package org.reactome.server.graph.custom;

import org.neo4j.driver.Record;
import org.reactome.server.graph.domain.result.CustomQuery;

/**
 * POJO for testing the Custom Cypher Queries
 *
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SuppressWarnings("unused")
public class CustomQueryPhysicalEntity implements CustomQuery {

    private String stId;
    private String displayName;
    private CustomReference customReference;

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public CustomReference getCustomReference() {
        return customReference;
    }

    public void setCustomReference(CustomReference customReference) {
        this.customReference = customReference;
    }

    @Override
    public CustomQuery build(Record r) {
        this.setStId(r.get("stId").asString());
        this.setDisplayName(r.get("displayName").asString());
        this.setCustomReference(new CustomReference(r.get("customReference").get("database").asString(), r.get("customReference").get("identifier").asString()));
        return this;
    }
}
