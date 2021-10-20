package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 */
@SuppressWarnings("unused")
@Node
public class TopLevelPathway extends Pathway {

    public TopLevelPathway() {}

//    The idea behind having this method was to avoid having this kind of object in the final schema
//    but at the end we figure out that is useful to have it, that is why this is commented out
//    @ReactomeSchemaIgnore
//    @Override
//    public String getSchemaClass() {
//        return Pathway.class.getSimpleName();
//    }
}