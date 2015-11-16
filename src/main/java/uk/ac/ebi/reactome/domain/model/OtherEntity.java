package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * Entities that do not contain any identifier
 */
@NodeEntity
public class OtherEntity extends PhysicalEntity {

    public OtherEntity() {}

    public OtherEntity(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }
}
