package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Entities that we are unable or unwilling to describe in chemical detail and cannot be put in any other class. Can be used to represent complex structures in the cell that take part in a reaction but which we cannot or do not want to define molecularly, e.g. cell membrane,
 */
@SuppressWarnings("unused")
@NodeEntity
public class OtherEntity extends PhysicalEntity {

    public OtherEntity() {}

    @Override
    public String getExplanation() {
        return "Entities that we are unable or unwilling to describe in chemical detail and cannot be put in any other class. " +
                "Can be used to represent complex structures in the cell that take part in a reaction but which we cannot or do not want to define molecularly, e.g. cell membrane, Holliday structure";
    }
}
