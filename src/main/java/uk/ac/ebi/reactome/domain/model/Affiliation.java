package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;

@SuppressWarnings("unused")
@NodeEntity
public class Affiliation extends  DatabaseObject{

    private String address;
    private List<String> name;

    public Affiliation() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }
}
