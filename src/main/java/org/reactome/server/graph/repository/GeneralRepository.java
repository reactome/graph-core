package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.domain.result.ComponentOf;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.04.16.
 */
@Repository
public interface GeneralRepository extends GraphRepository<DatabaseObject> {

    @Query("MATCH (n) RETURN DISTINCT LABELS(n) AS labels, Count(n) AS count")
    Collection<SchemaClassCount> getSchemaClassCounts();

    @Query("Match (n:Species) RETURN n")
    Collection<Species> getAllSpecies();

    @Query("Match (n:SimpleEntity)-[:referenceEntity]-(m:ReferenceEntity) RETURN Distinct(m)")
    Collection<ReferenceEntity> getAllChemicals();

    @Query("Match (n:DatabaseObject{stId:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) Return DISTINCT(type(r)) AS type, Collect(m.displayName) AS names, Collect(m.stId) AS stIds")
    Collection<ComponentOf> getComponentsOf(String stId);

    @Query("Match (n:DatabaseObject{dbId:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) Return DISTINCT(type(r)) AS type, Collect(m.displayName) AS names, Collect(m.stId) AS stIds")
    Collection<ComponentOf> getComponentsOf(Long dbId);

    @Query("MATCH (n:DatabaseObject{stId:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output*]-()<-[:hasEvent]-(m:Pathway), (m)-[:species]->(s:Species{dbId:{1}}) RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels UNION MATCH(n:ReactionLikeEvent{stId:{0}})<-[:hasEvent]-(m:Pathway), (m)-[:species]->(s:Species{dbId:{1}}) RETURN Distinct(m.dbId) as dbId, m.stId as stId, m.displayName as displayName, labels(m) as labels UNION MATCH(p:Pathway{stId:{0}}), (p)-[:species]->(s:Species{dbId:{1}}) RETURN Distinct(p.dbId) as dbId, p.stId as stId, p.displayName as displayName, labels(p) as labels")
//    @Query("MATCH (n:DatabaseObject{stableIdentifier:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output*]-()<-[:hasEvent]-(m:Pathway) RETURN Distinct(m.dbId) as dbId, m.stableIdentifier as stableIdentifier, m.displayName as displayName")
    Collection<SimpleDatabaseObject> getPathwaysFor(String stId, Long speciesId);




    @Query("Match (n:DBInfo) RETURN n.version LIMIT 1")
    Integer getDBVersion();

    @Query("Match (n:DBInfo) RETURN n.name LIMIT 1")
    String getDBName();




}
