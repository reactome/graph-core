package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.ComponentOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

@Repository
public class ComponentOfLinkageRepository {

    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public ComponentOfLinkageRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public Collection<ComponentOf> getComponentsOf(String stId) {
        String query = " " +
                "MATCH (n:DatabaseObject{stId:$stId})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) " +
                "RETURN DISTINCT(TYPE(r)) AS type, " +
                "       COLLECT(COALESCE(m.schemaClass, '')) AS schemaClasses, " +
                "       COLLECT(COALESCE(m.displayName, '')) AS names, " +
                "       COLLECT(COALESCE(m.stId, '')) AS stIds, " +
                "       COLLECT(COALESCE(m.speciesName, '')) as species";
        return neo4jClient.query(query).in(databaseName).bindAll(Collections.singletonMap("stId", stId)).fetchAs(ComponentOf.class).mappedBy( (t, record) -> ComponentOf.build(record)).all();

    }

    public Collection<ComponentOf> getComponentsOf(Long dbId) {
        String query = " " +
                "MATCH (n:DatabaseObject{dbId:$dbId})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) " +
                "RETURN DISTINCT(TYPE(r)) AS type, " +
                "       COLLECT(COALESCE(m.schemaClass, '')) AS schemaClasses, " +
                "       COLLECT(COALESCE(m.displayName, '')) AS names, " +
                "       COLLECT(COALESCE(m.stId, '')) AS stIds, " +
                "       COLLECT(COALESCE(m.speciesName, '')) as species";
        return neo4jClient.query(query).in(databaseName).bindAll(Collections.singletonMap("dbId", dbId)).fetchAs(ComponentOf.class).mappedBy( (t, record) -> ComponentOf.build(record)).all();

    }


}
