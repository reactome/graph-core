package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * PhysicalEntity is a container class. It represents individual molecules or multi-molecular complexes
 */
@NodeEntity
public abstract class PhysicalEntity extends DatabaseObject{

    public PhysicalEntity(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }
}

