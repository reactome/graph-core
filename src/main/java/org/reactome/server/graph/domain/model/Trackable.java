package org.reactome.server.graph.domain.model;


import org.reactome.server.graph.domain.result.DatabaseObjectLike;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@Node
public interface Trackable extends DatabaseObjectLike {

    List<UpdateTracker> getUpdateTrackers();
}
