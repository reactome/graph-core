package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 09.11.15.
 *
 * Failed reaction represents all reactions that cannot fulfill their original duty, this occurs mostly due to disease.
 */
@NodeEntity
public class FailedReaction extends ReactionLikeEvent{

    public FailedReaction() {}

    public FailedReaction(Long dbId, String stId, String name) {
        super(dbId, stId, name);
    }
}
