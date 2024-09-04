package org.reactome.server.graph.domain.model;


import org.reactome.server.graph.domain.result.DatabaseObjectLike;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@Node
public interface Deletable extends DatabaseObjectLike {
    List<Deleted> getDeleted();
}
