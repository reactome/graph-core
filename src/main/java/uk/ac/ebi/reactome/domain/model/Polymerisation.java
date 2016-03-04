package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Reactions that follow the pattern: Polymer + Unit - Polymer (there may be a catalyst involved).
 * Used to describe the mechanistic detail of a polymerisation
 */
@SuppressWarnings("unused")
@NodeEntity
public class Polymerisation extends ReactionLikeEvent {

    public Polymerisation() {}

}
