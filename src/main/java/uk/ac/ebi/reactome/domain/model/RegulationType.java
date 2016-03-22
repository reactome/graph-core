package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import uk.ac.ebi.reactome.domain.annotations.ReactomeProperty;

@SuppressWarnings("unused")
@NodeEntity
public class RegulationType extends DatabaseObject {

    @ReactomeProperty
    private String name;

    public RegulationType() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
