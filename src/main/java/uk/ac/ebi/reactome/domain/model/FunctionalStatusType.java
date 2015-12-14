package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;

@NodeEntity
public class FunctionalStatusType extends DatabaseObject {

    private String definition;
    private List<String> name;

    public FunctionalStatusType() {}

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

}
