package org.reactome.server.graph.domain.model;


import org.reactome.server.graph.domain.result.DatabaseObjectLike;

import java.util.List;

public interface Trackable extends DatabaseObjectLike {

    List<UpdateTracker> getUpdateTrackers();
}
