package org.reactome.server.graph.custom;

/**
 * POJO for testing the Custom Cypher Queries
 *
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */
@SuppressWarnings("unused")
public class CustomQueryPhysicalEntity {

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
}
