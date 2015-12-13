package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * DefinedSet describes PhysicalEntities where the interchangeable function is backed up by experimental data.
 */
@NodeEntity
public class DefinedSet extends EntitySet {

    public DefinedSet() {
    }

}
