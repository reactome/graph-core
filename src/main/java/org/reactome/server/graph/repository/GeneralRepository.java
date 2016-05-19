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

    @Query("Match (n:DatabaseObject{stableIdentifier:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) Return DISTINCT(type(r)) AS type, Collect(m.displayName) AS names, Collect(m.stableIdentifier) AS stIds")
    Collection<ComponentOf> getComponentsOf(String stableIdentifier);

    @Query("Match (n:DatabaseObject{dbId:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) Return DISTINCT(type(r)) AS type, Collect(m.displayName) AS names, Collect(m.stableIdentifier) AS stIds")
    Collection<ComponentOf> getComponentsOf(Long dbId);

    @Query("MATCH (n:DatabaseObject{stableIdentifier:{0}})<-[:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output*]-()<-[:hasEvent]-(m:Pathway), (m)-[:species]->(s:Species{dbId:{1}}) RETURN Distinct(m.dbId) as dbId, m.stableIdentifier as stableIdentifier, m.displayName as displayName, labels(m) as labels UNION MATCH(n:ReactionLikeEvent{stableIdentifier:{0}})<-[:hasEvent]-(m:Pathway), (m)-[:species]->(s:Species{dbId:{1}}) RETURN Distinct(m.dbId) as dbId, m.stableIdentifier as stableIdentifier, m.displayName as displayName, labels(m) as labels")
    Collection<SimpleDatabaseObject> getPathwaysFor(String stableIdentifier, Long speciesId);

    @Query("Match (n:Person) Where n.surname =~ {0} OR n.firstname =~ {0} RETURN n")
    Collection<Person> findPersonByName(String name);

    @Query("Match (n:Person{dbId:{0}})-[r:author]->(m:InstanceEdit)-[e:authored]->(k:Pathway) RETURN k")
    Collection<Pathway> findAuthoredPathways(Long dbId);

    @Query("Match (n:DBInfo) RETURN n.version LIMIT 1")
    Integer getDBVersion();

    @Query("Match (n:DBInfo) RETURN n.name LIMIT 1")
    String getDBName();

}
