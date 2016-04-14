package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * This describes an Event/CatalystActivity that is negatively regulated by the Regulator (e.g., allosteric inhibition, competitive inhibition.
 */
@SuppressWarnings("unused")
@NodeEntity
public class NegativeRegulation extends Regulation {

    public NegativeRegulation() {}

    @Override
    public String getExplanation() {
        return "This describes an Event/CatalystActivity that is negatively regulated by the Regulator (e.g., allosteric inhibition, competitive inhibition";
    }
}
