package org.reactome.server.graph.domain.model;

import org.reactome.server.graph.domain.annotations.ReactomeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.04.16.
 */
@SuppressWarnings("unused")
@Node
public abstract class GO_Term extends ExternalOntology {

    public GO_Term() {}
    
    public GO_Term(Long dbId) {
        super();
        setDbId(dbId);
    }


    /**
     * Keep this method for backward compatibility.
     * @return
     */
    public String getAccession() {
        return getIdentifier();
    }

    public void setAccession(String accession) {
        setIdentifier(accession);
    }

}

