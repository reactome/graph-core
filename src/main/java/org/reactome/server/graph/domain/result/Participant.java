package org.reactome.server.graph.domain.result;



import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 03.02.16.
 *
 * Object for retrieving Ewas and their ReferenceEntities for a given Pathway Id
 */
@SuppressWarnings("unused")

public class Participant {

    private Long peDbId;
    private String displayName;
    private String schemaClass;
    private String icon;
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

    public String getSchemaClass() {
        return schemaClass;
    }

    public void setSchemaClass(String schemaClass) {
        this.schemaClass = schemaClass;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Collection<Object> getRefEntities() {
        return refEntities;
    }

    public void setRefEntities(Collection<Object> refEntities) {
        this.refEntities = refEntities;
    }
}
