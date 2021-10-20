package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

@Deprecated
@SuppressWarnings("unused")
@Node
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
