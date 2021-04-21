package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;

@SuppressWarnings("unused")
@Node
public class GO_MolecularFunction extends GO_Term {

    @ReactomeProperty
    private String ecNumber;

    public GO_MolecularFunction() {}


    public String getEcNumber() {
        return ecNumber;
    }

    public void setEcNumber(String ecNumber) {
        this.ecNumber = ecNumber;
    }

}
