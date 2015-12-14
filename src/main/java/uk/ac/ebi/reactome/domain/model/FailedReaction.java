package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * FailedReaction that should not have any output. However, the output has been kept here to
 * avoid any runtime error.
 */
@NodeEntity
public class FailedReaction extends ReactionLikeEvent {

    public FailedReaction() {}

}
