package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@SuppressWarnings("unused")
@NodeEntity
public class RegulationType extends DatabaseObject {

    private String name;

    public RegulationType() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
