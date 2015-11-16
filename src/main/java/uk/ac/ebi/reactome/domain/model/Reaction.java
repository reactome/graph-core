package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * Reaction contains all common reactions with balanced inputs and outputs
 */
@NodeEntity
public class Reaction extends ReactionLikeEvent{

    public Reaction() {}

    public Reaction(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }
}
