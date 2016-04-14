package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Two or more entities that are interchangeable in function.
 */
@SuppressWarnings("unused")
@NodeEntity
public class DefinedSet extends EntitySet {

    public DefinedSet() {}

    @Override
    public String getExplanation() {
        return "Two or more entities that are interchangeable in function";
    }
}
