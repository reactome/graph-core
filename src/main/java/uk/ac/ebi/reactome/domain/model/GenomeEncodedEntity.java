package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * GEE contains proteins and nucleic acids with unknown sequences
 */
@NodeEntity
public class GenomeEncodedEntity extends PhysicalEntity {

    public GenomeEncodedEntity() {}

    public GenomeEncodedEntity(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }
}
