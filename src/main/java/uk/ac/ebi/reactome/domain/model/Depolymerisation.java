package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Reactions that follow the pattern: Polymer - Polymer + Unit  (there may be a catalyst involved). Used to describe the mechanistic detail of depolymerisation
 */
@SuppressWarnings("unused")
@NodeEntity
public class Depolymerisation extends ReactionLikeEvent {

    public Depolymerisation() {}

}
