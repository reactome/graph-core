package org.reactome.server.graph.repository;

import org.neo4j.driver.Record;
import org.neo4j.driver.types.MapAccessor;
import org.neo4j.driver.types.TypeSystem;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.result.EventAncestorsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.BiFunction;

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

    public Collection<Collection<Event>> getEventAncestorsByStId(@Param("stId") String stId) {
        String query = " " +
                "MATCH (n:Pathway{stId:$stId}) " +
                "RETURN [n] as nodes " +
                "UNION " +
                "MATCH ancestors=((n:Event{stId:$stId})<-[:hasEvent*]-(remove:Pathway)) " +
                "WHERE NOT remove.schemaClass=\"TopLevelPathway\" " + // TODO remove this condition...
                "RETURN NODES(ancestors) AS nodes";

        BiFunction<TypeSystem, MapAccessor, Event> mappingFunction = neo4jMappingContext.getRequiredMappingFunctionFor(Event.class);
        Collection<EventAncestorsWrapper> wrapper =
                neo4jClient.query(query)
                        .in(databaseName)
                        .bindAll(Collections.singletonMap("stId", stId))
                        .fetchAs(EventAncestorsWrapper.class)
                        .mappedBy((typeSystem, record) -> getEventAncestorsWrapper(mappingFunction, typeSystem, record))
                .all();

        Collection<Collection<Event>> ret = new ArrayList<>(wrapper.size());
        for (EventAncestorsWrapper eventAncestorsWrapper : wrapper) {
            ret.add(eventAncestorsWrapper.getNodes());
        }

        return ret;
    }

    public Collection<Collection<Event>> getEventAncestorsByDbId(@Param("dbId") Long dbId) {
        String query = " " +
                "MATCH (n:Pathway{dbId:dbId}) " +
                "RETURN [n] as nodes " +
                "UNION " +
                "MATCH ancestors=((n:Event{dbId:dbId})<-[:hasEvent*]-(remove:Pathway)) " +
                "WHERE NOT remove.schemaClass=\"TopLevelPathway\" " + // TODO remove this condition...
                "RETURN NODES(ancestors) AS nodes";

        BiFunction<TypeSystem, MapAccessor, Event> mappingFunction = neo4jMappingContext.getRequiredMappingFunctionFor(Event.class);
        Collection<EventAncestorsWrapper> wrapper =
                neo4jClient.query(query)
                        .in(databaseName)
                        .bindAll(Collections.singletonMap("dbId", dbId))
                        .fetchAs(EventAncestorsWrapper.class)
                        .mappedBy((typeSystem, record) -> getEventAncestorsWrapper(mappingFunction, typeSystem, record))
                        .all();

        Collection<Collection<Event>> ret = new ArrayList<>(wrapper.size());
        for (EventAncestorsWrapper eventAncestorsWrapper : wrapper) {
            ret.add(eventAncestorsWrapper.getNodes());
        }

        return ret;
    }

    private EventAncestorsWrapper getEventAncestorsWrapper(BiFunction<TypeSystem, MapAccessor, Event> mappingFunction, TypeSystem typeSystem, Record record){
        Iterator<org.neo4j.driver.Value> values =  record.get("nodes").values().iterator();
        Collection<Event> events = new ArrayList<>();
        while(values.hasNext()){
            events.add(mappingFunction.apply(typeSystem, values.next()));
        }
        return new EventAncestorsWrapper(events);
    }
}
