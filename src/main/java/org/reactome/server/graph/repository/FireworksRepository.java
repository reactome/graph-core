package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Repository
public interface FireworksRepository extends GraphRepository<DatabaseObject> {

    @Query("MATCH (n:DatabaseObject{stId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output*]-()<-[:hasEvent]-(m:Pathway), (m)-[:species]->(s:Species{dbId:{1}}) RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels UNION MATCH(n:ReactionLikeEvent{stId:{0}})<-[:hasEvent]-(m:Pathway), (m)-[:species]->(s:Species{dbId:{1}}) RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels UNION MATCH(p:Pathway{stId:{0}}), (p)-[:species]->(s:Species{dbId:{1}}) RETURN Distinct(p.dbId) as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels")
    Collection<SimpleDatabaseObject> getPathwaysFor(String stId, Long speciesId);

    @Query("MATCH (:PhysicalEntity{stId:{0}})-[:referenceEntity]->(re:ReferenceEntity) WITH re MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway), (p)-[:species]->(s:Species{dbId:48887}) RETURN Distinct(p.dbId) as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels")
    Collection<SimpleDatabaseObject> getPathwaysForAllFormsOf(String stId, Long speciesId);
}
