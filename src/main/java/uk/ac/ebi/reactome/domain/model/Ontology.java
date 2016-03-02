package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@Deprecated
@SuppressWarnings("unused")
@NodeEntity
public class Ontology extends DatabaseObject {

    private byte[] ontology;

    public Ontology() {}

    public Ontology(byte[] ontology) {
        this.ontology = ontology;
    }

    public byte[] getOntology() {
        return this.ontology;
    }

    public void setOntology(byte[] ontology) {
        this.ontology = ontology;
    }

}
