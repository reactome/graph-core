package org.reactome.server.graph.domain.model;

import java.util.List;
import org.reactome.server.graph.domain.result.DatabaseObjectLike;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public interface Trackable extends DatabaseObjectLike {

    List<UpdateTracker> getUpdateTrackers();
}
