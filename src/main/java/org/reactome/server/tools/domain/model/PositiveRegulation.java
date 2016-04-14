package org.reactome.server.tools.domain.model;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * This describes an Event/CatalystActivity that is positively regulated by the Regulator (e.g., allosteric activation).
 */
@SuppressWarnings("unused")
@NodeEntity
public class PositiveRegulation extends Regulation {

    public PositiveRegulation() {}

    @Override
    public String getExplanation() {
        return  "This describes an Event/CatalystActivity that is positively regulated by the Regulator (e.g., allosteric activation)";
    }
}
