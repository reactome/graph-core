package org.reactome.server.graph.domain.result;

public interface DatabaseObjectLike {
    Long getDbId();
    String getStId();
    String getDisplayName();
    String getSchemaClass();
}
