package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings("unused")
@NodeEntity
public class TopLevelPathway extends Pathway{

    public TopLevelPathway() {}

    @ReactomeSchemaIgnore
    @Override
    public String getSchemaClass() {
        return Pathway.class.getSimpleName();
    }
}