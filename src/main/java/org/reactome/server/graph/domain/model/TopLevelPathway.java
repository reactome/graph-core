package org.reactome.server.graph.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@SuppressWarnings("unused")
@NodeEntity
public class TopLevelPathway extends Pathway{

    public TopLevelPathway() {}

    @Override
    public String getSchemaClass() {
        return Pathway.class.getSimpleName();
    }
}