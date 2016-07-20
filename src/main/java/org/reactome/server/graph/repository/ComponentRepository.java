package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.ComponentOf;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by flo on 06/06/16.
 */
@Repository
public interface ComponentRepository extends GraphRepository<DatabaseObject> {

    @Query("Match (n:DatabaseObject{stId:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) Return DISTINCT(type(r)) AS type, Collect(m.simpleLabel) AS schemaClasses, Collect(m.displayName) AS names, Collect(m.stId) AS stIds")
    Collection<ComponentOf> getComponentsOf(String stId);

    @Query("Match (n:DatabaseObject{dbId:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) Return DISTINCT(type(r)) AS type, Collect(m.simpleLabel) AS schemaClasses, Collect(m.displayName) AS names, Collect(m.stId) AS stIds")
    Collection<ComponentOf> getComponentsOf(Long dbId);

}
