package org.reactome.server.graph.repository;

import org.neo4j.driver.Record;
import org.reactome.server.graph.domain.result.EventProjection;
import org.reactome.server.graph.domain.result.EventProjectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Repository
public class EventsRepository {

    private final Neo4jClient neo4jClient;
    private final Neo4jMappingContext neo4jMappingContext;

    @Autowired
    public EventsRepository(Neo4jClient neo4jClient, Neo4jMappingContext neo4jMappingContext) {
        this.neo4jClient = neo4jClient;
        this.neo4jMappingContext = neo4jMappingContext;
    }

    @Value("${spring.data.neo4j.database}")
    private String databaseName;

    public Collection<EventProjectionWrapper> getEventAncestorsByStId(@Param("stId") String stId) {
        //language=Cypher
        String query = " " +
                "MATCH (n:TopLevelPathway{stId:$stId}) " +
                "WITH *, [n] as nodes " +
                "RETURN [na IN nodes | [na.dbId, na.displayName, na.name, na.stId, na.stIdVersion, na.oldStId, na.schemaClass, na.doi, na.speciesName, na.releaseDate, na.releaseStatus, na.hasDiagram, na.hasEHLD, na.diagramHeight, na.diagramWidth, na.isInferred, na.category, na.isInDisease, na.definition,na.isCanonical] ] AS result " +
                "UNION " +
                "MATCH ancestors=((n:Event{stId:$stId})<-[:hasEvent*]-(remove:TopLevelPathway)) " +
                "WITH *, NODES(ancestors) AS nodes " +
                "RETURN [na IN nodes | [na.dbId, na.displayName, na.name, na.stId, na.stIdVersion, na.oldStId, na.schemaClass, na.doi, na.speciesName, na.releaseDate, na.releaseStatus, na.hasDiagram, na.hasEHLD, na.diagramHeight, na.diagramWidth, na.isInferred, na.category, na.isInDisease, na.definition,na.isCanonical] ] AS result";

        return neo4jClient.query(query)
                .in(databaseName)
                .bindAll(Collections.singletonMap("stId", stId))
                .fetchAs(EventProjectionWrapper.class)
                .mappedBy((typeSystem, record) -> getEventProjectionWrapper(record)).all();
    }

    public Collection<EventProjectionWrapper> getEventAncestorsByDbId(@Param("dbId") Long dbId) {
        //language=Cypher
        String query = " " +
                "MATCH (n:TopLevelPathway{dbId:dbId}) " +
                "WITH *, [n] as nodes " +
                "RETURN [na IN nodes | [na.dbId, na.displayName, na.name, na.stId, na.stIdVersion, na.oldStId, na.schemaClass, na.doi, na.speciesName, na.releaseDate, na.releaseStatus, na.hasDiagram, na.hasEHLD, na.diagramHeight, na.diagramWidth, na.isInferred, na.category, na.isInDisease, na.definition,na.isCanonical] ] AS result " +
                "UNION " +
                "MATCH ancestors=((n:Event{dbId:dbId})<-[:hasEvent*]-(remove:TopLevelPathway)) " +
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
