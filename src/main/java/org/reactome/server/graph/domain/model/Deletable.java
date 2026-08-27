package org.reactome.server.graph.domain.model;


import org.reactome.server.graph.domain.result.DatabaseObjectLike;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

/**
 * This interface is not used any more because it limits the scope of replacementInstances in Deleted, which is supposed to
 * cover all different types of DatabaseObjects. It is kept for backward compatibility with the old code since the nodes have
 * been already created in the database. The interface may not be removed in the future, but should never be used in new code.
 */
@Node
@Deprecated
public interface Deletable extends DatabaseObjectLike {
    List<Deleted> getDeleted();
}
