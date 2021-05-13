package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Event;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventsRepository extends Neo4jRepository<Event, Long> {

    @Query(" MATCH (n:TopLevelPathway{stId:$stId}) " +
            "RETURN [n] as nodes " +
            "UNION " +
            "MATCH ancestors=((n:Event{stId:$stId})<-[r:hasEvent*]-(:Pathway)) " +
            "RETURN NODES(ancestors) AS nodes LIMIT 4")
    Collection<Collection<Event>> getEventAncestorsByStIdMODIFIED(@Param("stId") String stId);

    @Query(" MATCH (n:TopLevelPathway{stId:$stId}) " +
            "RETURN [n] as nodes " +
            "UNION " +
            "MATCH ancestors=((n:Event{stId:$stId})<-[r:hasEvent*]-(:TopLevelPathway)) " +
            "RETURN NODES(ancestors)")
    Collection<Collection<Event>> getEventAncestorsByStId(@Param("stId") String stId);

    @Query(" MATCH (n:TopLevelPathway{dbId:$dbId}) " +
            "RETURN [n] as nodes " +
            "UNION " +
            "MATCH ancestors=((n:Event{dbId:$dbId})<-[r:hasEvent*]-(:TopLevelPathway)) " +
            "RETURN NODES(ancestors) AS nodes")
    Collection<Collection<Event>> getEventAncestorsByDbId(@Param("dbId") Long dbId);
}
// TODO WORK IN PROGRESS - waiting for community input
//public class EventsRepository {
//
//    private Neo4jTemplate neo4jTemplate;
//    private Neo4jClient client;
//
//    @Autowired
//    public EventsRepository(Neo4jClient client) {
//        this.client = client;
//    }
//
//
//    public Collection<Collection<Event>> getEventAncestorsByStId(String stId) {
//        String query = " MATCH (n:TopLevelPathway{stId:$stId}) " +
//                "RETURN [n] as nodes " +
//                "UNION " +
//                "MATCH ancestors=((n:Event{stId:$stId})<-[r:hasEvent*]-(:TopLevelPathway)) " +
//                "RETURN NODES(ancestors) AS nodes";
//
//        Map<String, Object> map = Map.of("stId", stId);
//        return (Collection) neo4jTemplate.findAll(query, map, Collection.class);
//    }


//    public Collection<Collection<Event>> getEventAncestorsByStId(String stId) {
//        String query = " MATCH (n:TopLevelPathway{stId:$stId}) " +
//                "RETURN [n] as nodes " +
//                "UNION " +
//                "MATCH ancestors=((n:Event{stId:$stId})<-[r:hasEvent*]-(:TopLevelPathway)) " +
//                "RETURN NODES(ancestors) AS nodes";
//
//        Map<String, Object> map = Map.of("stId", stId);
//        client.query(query).bindAll(map).fetch().all();
////        .mappedBy((a,r) -> {
////            r.get("nodes");
////        });
//        return null;
//    }
//
//    public Collection<Collection<Event>> getEventAncestorsByDbId(Long dbId) {
//        String query = " MATCH (n:TopLevelPathway{dbId:$dbId}) " +
//                "RETURN [n] as nodes " +
//                "UNION " +
//                "MATCH ancestors=((n:Event{dbId:$dbId})<-[r:hasEvent*]-(:TopLevelPathway)) " +
//                "RETURN NODES(ancestors) AS nodes";
//        Map<String, Object> map = Map.of("dbId", dbId);
//        return (Collection) neo4jTemplate.findAll(query, map, Collection.class);
//
//
//    }
//}
