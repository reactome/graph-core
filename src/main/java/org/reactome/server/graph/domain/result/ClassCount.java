package org.reactome.server.graph.domain.result;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class ClassCount<S,T> {

    public S s;

    public T t;

}
