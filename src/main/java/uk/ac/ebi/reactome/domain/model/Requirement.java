package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * TODO
 */
@NodeEntity
public class Requirement extends PositiveRegulation {

    public Requirement() {}

    public Requirement(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }
}
