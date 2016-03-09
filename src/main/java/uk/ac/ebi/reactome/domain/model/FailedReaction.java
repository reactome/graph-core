package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Defines an event where genetic mutations in the nucleotide sequence produces a protein with a very little or no activity. The consequence of this is that substrates are not converted to products and can therefore build up to cause pathological conditions. It could also mean entities are not moved between compartments again causing imbalances in entity concentrations which can lead to pathological conditions.
 */
@SuppressWarnings("unused")
@NodeEntity
public class FailedReaction extends ReactionLikeEvent {

    public FailedReaction() {}

}
