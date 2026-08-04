package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@SuppressWarnings("unused")
@Node
public class GO_MolecularFunction extends GO_Term {

    // Multi-valued in the Reactome schema: a GO molecular function can map to more than one EC number.
    @ReactomeProperty
    private List<String> ecNumber;

    public GO_MolecularFunction() {}


    public List<String> getEcNumber() {
        return ecNumber;
    }

    public void setEcNumber(List<String> ecNumber) {
        this.ecNumber = ecNumber;
    }

}
