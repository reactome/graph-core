package org.reactome.server.graph.repository;

import org.neo4j.driver.Record;
import org.reactome.server.graph.domain.result.EventProjection;
import org.reactome.server.graph.domain.result.EventProjectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Repository
public class EventAncestorsRepository {

    private final Neo4jClient neo4jClient;

    @Autowired
    public EventAncestorsRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    public Collection<EventProjectionWrapper> getEventAncestorsByStId(@Param("stId") String stId) {
        //language=Cypher
        String query = " " +
                "MATCH (n:TopLevelPathway{stId:$stId}) " +
                "WITH *, [n] as nodes " +
                "RETURN [na IN nodes | [na.dbId, na.displayName, na.name, na.stId, na.stIdVersion, na.oldStId, na.schemaClass, na.doi, na.speciesName, na.releaseDate, na.releaseStatus, na.hasDiagram, na.hasEHLD, na.diagramHeight, na.diagramWidth, na.isInferred, na.category, na.isInDisease, na.definition,na.isCanonical] ] AS result " +
                "UNION " +
                "MATCH ancestors=((n:Event{stId:$stId})<-[:hasEvent*]-(:TopLevelPathway)) " +
                "WITH *, NODES(ancestors) AS nodes " +
                "RETURN [na IN nodes | [na.dbId, na.displayName, na.name, na.stId, na.stIdVersion, na.oldStId, na.schemaClass, na.doi, na.speciesName, na.releaseDate, na.releaseStatus, na.hasDiagram, na.hasEHLD, na.diagramHeight, na.diagramWidth, na.isInferred, na.category, na.isInDisease, na.definition,na.isCanonical] ] AS result";

        return neo4jClient.query(query)
                .in(databaseName)
                .bindAll(Map.of("stId", stId))
                .fetchAs(EventProjectionWrapper.class)
                .mappedBy((typeSystem, record) -> getEventProjectionWrapper(record)).all();
    }

    public Collection<EventProjectionWrapper> getEventAncestorsByDbId(@Param("dbId") Long dbId) {
        //language=Cypher
        String query = " " +
                "MATCH (n:TopLevelPathway{dbId:$dbId}) " +
                "WITH *, [n] as nodes " +
                "RETURN [na IN nodes | [na.dbId, na.displayName, na.name, na.stId, na.stIdVersion, na.oldStId, na.schemaClass, na.doi, na.speciesName, na.releaseDate, na.releaseStatus, na.hasDiagram, na.hasEHLD, na.diagramHeight, na.diagramWidth, na.isInferred, na.category, na.isInDisease, na.definition,na.isCanonical] ] AS result " +
                "UNION " +
                "MATCH ancestors=((n:Event{dbId:$dbId})<-[:hasEvent*]-(:TopLevelPathway)) " +
                "WITH *, NODES(ancestors) AS nodes " +
                "RETURN [na IN nodes | [na.dbId, na.displayName, na.name, na.stId, na.stIdVersion, na.oldStId, na.schemaClass, na.doi, na.speciesName, na.releaseDate, na.releaseStatus, na.hasDiagram, na.hasEHLD, na.diagramHeight, na.diagramWidth, na.isInferred, na.category, na.isInDisease, na.definition,na.isCanonical] ] AS result";

        return neo4jClient.query(query)
                .in(databaseName)
                .bindAll(Collections.singletonMap("dbId", dbId))
                .fetchAs(EventProjectionWrapper.class)
                .mappedBy((typeSystem, record) -> getEventProjectionWrapper(record)).all();
    }

    private EventProjectionWrapper getEventProjectionWrapper(Record record) {
        Collection<EventProjection> events = new ArrayList<>();
        for (org.neo4j.driver.Value resultSet : record.values()) {
            for (org.neo4j.driver.Value item : resultSet.values()) {
                events.add(new EventProjection(item));
            }
        }
        return new EventProjectionWrapper(events);
    }
}
