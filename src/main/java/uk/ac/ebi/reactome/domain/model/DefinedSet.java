package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * DefinedSet describes PhysicalEntities where the interchangeable function is backed up by experimental data.
 */
@NodeEntity
public class DefinedSet extends EntitySet {

    public DefinedSet() {}

    public DefinedSet(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }
}
