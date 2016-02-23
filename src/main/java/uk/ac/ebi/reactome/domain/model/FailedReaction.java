package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

@SuppressWarnings("unused")
@NodeEntity
public class FailedReaction extends ReactionLikeEvent {

    public FailedReaction() {}

}
