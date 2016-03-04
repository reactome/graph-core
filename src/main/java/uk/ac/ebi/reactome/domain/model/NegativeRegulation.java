package uk.ac.ebi.reactome.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * This describes an Event/CatalystActivity that is negatively regulated by the Regulator (e.g., allosteric inhibition, competitive inhibition.
 */
@SuppressWarnings("unused")
@NodeEntity
public class NegativeRegulation extends Regulation {

    public NegativeRegulation() {}

}
