package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

/**
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@Node(primaryLabel = "ChemicalDrug")
public class ChemicalDrug extends Drug {

    public ChemicalDrug() {
    }

}
