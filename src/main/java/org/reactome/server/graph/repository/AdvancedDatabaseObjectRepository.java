package org.reactome.server.graph.repository;

import org.neo4j.driver.types.MapAccessor;
import org.neo4j.driver.types.TypeSystem;
import org.reactome.server.graph.domain.ReflectionUtils;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.CustomQuery;
import org.reactome.server.graph.domain.result.QueryResultWrapper;
import org.reactome.server.graph.exception.CustomQueryException;
import org.reactome.server.graph.repository.util.RepositoryUtils;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 */
@SuppressWarnings("unchecked")
@Repository
public class AdvancedDatabaseObjectRepository {

    private final Neo4jClient neo4jClient;
    private final Neo4jTemplate neo4jTemplate;
    private final Neo4jMappingContext neo4jMappingContext;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    private static final String CYPHER_RETURN = "" +
            "WITH n, r, m " +
            "ORDER BY TYPE(r) ASC, r.order ASC " +
            "RETURN n, COLLECT(r), COLLECT(m)";

    private static final String CYPHER_RETURN_LIMIT = CYPHER_RETURN + " " + "LIMIT $limit";

    @Autowired
    public AdvancedDatabaseObjectRepository(Neo4jClient neo4jClient, Neo4jTemplate neo4jTemplate, Neo4jMappingContext neo4jMappingContext) {
        this.neo4jClient = neo4jClient;
        this.neo4jTemplate = neo4jTemplate;
        this.neo4jMappingContext = neo4jMappingContext;
    }

    // --------------------------------------- Generic Finder Methods --------------------------------------------------

    @Deprecated
    public <T extends DatabaseObject> DatabaseObject findByProperty(Class<T> clazz, String property, Object value) {
        String query = "" +
                "MATCH (n:" + clazz.getSimpleName() + ") " +
                "WHERE n." + property + " = $param " +
                "OPTIONAL MATCH (n)-[r]-(m) " +
                "RETURN n, COLLECT(r), COLLECT(m)";

        return neo4jTemplate.findOne(query, Map.of("param", value), DatabaseObject.class).orElse(null);
    }

    @Deprecated
    public <T extends DatabaseObject> Collection<DatabaseObject> findAllByProperty(Class<T> clazz, String property, Object value) {
        // It turned out that depth 2 takes ages to run
        String query = "" +
                "MATCH (n:" + clazz.getSimpleName() + ") " +
                "WHERE n." + property + " = $param " +
                "OPTIONAL MATCH (n)-[r]-(m) " +
                "RETURN n, COLLECT(r), COLLECT(m)";

        return neo4jTemplate.findAll(query, Map.of("param", value), DatabaseObject.class);
    }

    // --------------------------------------- Limited Finder Methods --------------------------------------------------

    public <T extends DatabaseObject> T findById(Long dbId, Integer limit) {
        String query = "" +
                "MATCH (n:DatabaseObject{dbId:$dbId}) " +
                "OPTIONAL MATCH (n)-[r]-(m) " +
                CYPHER_RETURN_LIMIT;

        return (T) neo4jTemplate.findOne(query, Map.of("dbId", dbId, "limit", limit), DatabaseObject.class).orElse(null);
    }

    public <T extends DatabaseObject> T findById(String stId, Integer limit) {
        String query = "" +
                "MATCH (n:DatabaseObject{stId:$stId}) " +
                "OPTIONAL MATCH (n)-[r]-(m) " +
                CYPHER_RETURN_LIMIT;

        return (T) neo4jTemplate.findOne(query, Map.of("stId", stId, "limit", limit), DatabaseObject.class).orElse(null);
    }

    // --------------------------------------- Enhanced Finder Methods -------------------------------------------------

    public <T extends DatabaseObject> T findEnhancedObjectById(Long dbId) {
        String query = "" +
                "MATCH (n:DatabaseObject{dbId:$dbId}) " +
                "OPTIONAL MATCH (n)-[r1]-(m) " +
                "OPTIONAL MATCH (m)-[r2:species]->(s) " +
                "OPTIONAL MATCH (m)-[r3:regulator|regulatedBy|physicalEntity|crossReference|referenceGene|literatureReference]-(o) " +
                "RETURN n, COLLECT(r1), COLLECT(m), COLLECT(r2), COLLECT(s), COLLECT(r3), COLLECT(o) ";

        return (T) neo4jTemplate.findOne(query, Map.of("dbId", dbId), DatabaseObject.class).orElse(null);
    }

    public <T extends DatabaseObject> T findEnhancedObjectById(String stId) {
        String query = "" +
                "MATCH (n:DatabaseObject{stId:$stId}) " +
                "OPTIONAL MATCH (n)-[r1]-(m) " +
                "OPTIONAL MATCH (m)-[r2:species]->(s) " +
                "OPTIONAL MATCH (m)-[r3:regulator|regulatedBy|physicalEntity|crossReference|referenceGene|literatureReference]-(o) " +
                "RETURN n, COLLECT(r1), COLLECT(m), COLLECT(r2), COLLECT(s), COLLECT(r3), COLLECT(o) ";

        return (T) neo4jTemplate.findOne(query, Map.of("stId", stId), DatabaseObject.class).orElse(null);
    }

    // ---------------------- Methods with RelationshipDirection and Relationships -------------------------------------

    public <T extends DatabaseObject> T findById(Long dbId, RelationshipDirection direction) {
        // The previous implementation was querying everything and then returning only the first. LIMIT 1 will be forced in this query.
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject{dbId:$dbId}) OPTIONAL MATCH (n)-[r]->(m) " + CYPHER_RETURN;
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject{dbId:$dbId}) OPTIONAL MATCH (n)<-[r]-(m) " + CYPHER_RETURN;
                break;
            default: // UNDIRECTED
                query = "MATCH (n:DatabaseObject{dbId:$dbId}) OPTIONAL MATCH (n)-[r]-(m) " + CYPHER_RETURN;
        }

        return (T) neo4jTemplate.findOne(query, Map.of("dbId", dbId), DatabaseObject.class).orElse(null);
    }

    public <T extends DatabaseObject> T findById(String stId, RelationshipDirection direction) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject{stId:$stId}) OPTIONAL MATCH (n)-[r]->(m) " + CYPHER_RETURN;
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject{stId:$stId}) OPTIONAL MATCH (n)<-[r]-(m) " + CYPHER_RETURN;
                break;
            default: // UNDIRECTED
                query = "MATCH (n:DatabaseObject{stId:$stId}) OPTIONAL MATCH (n)-[r]-(m) " + CYPHER_RETURN;
        }

        return (T) neo4jTemplate.findOne(query, Map.of("stId", stId), DatabaseObject.class).orElse(null);
    }

    public <T extends DatabaseObject> T findById(Long dbId, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject{dbId:$dbId})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) " + CYPHER_RETURN;
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject{dbId:$dbId})<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) " + CYPHER_RETURN;
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject{dbId:$dbId})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) " + CYPHER_RETURN;
                break;
        }

        return (T) neo4jTemplate.findOne(query, Map.of("dbId", dbId), DatabaseObject.class).orElse(null);
    }

    public <T extends DatabaseObject> T findById(String stId, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject{stId:$stId})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) " + CYPHER_RETURN;
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject{stId:$stId})<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) " + CYPHER_RETURN;
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject{stId:$stId})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) " + CYPHER_RETURN;
                break;
        }

        return (T) neo4jTemplate.findOne(query, Map.of("stId", stId), DatabaseObject.class).orElse(null);
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) " +
                        "WHERE n.dbId IN $dbIds " + CYPHER_RETURN;
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject)<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) " +
                        "WHERE n.dbId IN $dbIds " + CYPHER_RETURN;
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) " +
                        "WHERE n.dbId IN $dbIds " + CYPHER_RETURN;
                break;
        }

        return neo4jTemplate.findAll(query, Map.of("dbIds", dbIds), DatabaseObject.class);
    }

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m) " +
                        "WHERE n.stId IN $stIds " + CYPHER_RETURN;
                break;
            case INCOMING:
                query = "MATCH (n:DatabaseObject)<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) " +
                        "WHERE n.stId IN $stIds " + CYPHER_RETURN;
                break;
            default: //UNDIRECTED
                query = "MATCH (n:DatabaseObject)-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m) " +
                        "WHERE n.stId IN $stIds " + CYPHER_RETURN;
                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("stIds", stIds);
        return neo4jTemplate.findAll(query, map, DatabaseObject.class);
    }

    public Collection<DatabaseObject> findCollectionByRelationship(Long dbId, String clazz, Class<?> collectionClass, RelationshipDirection direction, String... relationships) {
        Collection<QueryResultWrapper> list = queryRelationshipTypesByDbId(dbId, clazz, direction, relationships);
        Collection<DatabaseObject> databaseObjects;
        if (collectionClass.getName().equals(Set.class.getName())) {
            databaseObjects = list.stream().map(QueryResultWrapper::getDatabaseObject).collect(Collectors.toSet());
        } else {
            databaseObjects = new ArrayList<>(list.size());
            for (QueryResultWrapper wrapper : list) {
                //Here stoichiometry has to be taken into account
                for (int i = 0; i <  wrapper.getStoichiometry(); ++i) {
                    databaseObjects.add(wrapper.getDatabaseObject());
                }
            }
        }
        return databaseObjects.isEmpty() ? null : databaseObjects;
    }

    public <T extends DatabaseObject> T findByRelationship(Long dbId, String clazz, RelationshipDirection direction, String... relationships) {
        Collection<QueryResultWrapper> wrappers = queryRelationshipTypesByDbId(dbId, clazz, direction, relationships);
        if (wrappers != null && wrappers.size() == 1) {
            return (T) wrappers.iterator().next().getDatabaseObject();
        }
        return null;
    }

    /**
     * During the Lazy-Loading strategy, we need to query pointing to a relationship type and either gets back a single
     * DatabaseObject or a Collection of DatabaseObject.
     * This method queries the Graph and returns it as a Result object that will be parsed in the findByRelationship
     * and findCollectionByRelationship accordingly.
     */
    public Collection<QueryResultWrapper> queryRelationshipTypesByDbId(Long dbId, String clazz, RelationshipDirection direction, String... relationships) {
        String query;
        switch (direction) {
            case OUTGOING:
                query = "MATCH (a:DatabaseObject{dbId:$dbId})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]->(m:" + clazz + ") RETURN m, r.stoichiometry as n";
                break;
            case INCOMING:
                query = "MATCH (a:DatabaseObject{dbId:$dbId})<-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m:" + clazz + ") RETURN m, r.stoichiometry as n";
                break;
            default: //UNDIRECTED
                query = "MATCH (a:DatabaseObject{dbId:$dbId})-[r" + RepositoryUtils.getRelationshipAsString(relationships) + "]-(m:" + clazz + ") RETURN m, r.stoichiometry as n";
                break;
        }

        BiFunction<TypeSystem, MapAccessor, DatabaseObject> mappingFunction = neo4jMappingContext.getRequiredMappingFunctionFor(DatabaseObject.class);
        return neo4jClient.query(query)
                        .bindAll(Map.of("dbId", dbId))
                        .fetchAs(QueryResultWrapper.class)
                        .mappedBy((typeSystem, record) -> {
                            DatabaseObject databaseObject = mappingFunction.apply(typeSystem, record.get("m"));
                            return new QueryResultWrapper(databaseObject, record.get("n").asInt());
                        }).all();
    }

    // ----------------------------------------- Custom Query Methods --------------------------------------------------

    public void customQuery(String query, Map<String, Object> parameters){
        neo4jClient.query(query).bindAll(parameters).run();
    }

    public <T> T customQueryResult(Class<T> clazz, String query, Map<String, Object> parameters) throws CustomQueryException {
        try {
            if (parameters == null) parameters = Collections.EMPTY_MAP;

            if (DatabaseObject.class.isAssignableFrom(clazz)) {
                return (T) neo4jTemplate.findOne(query, parameters, DatabaseObject.class).orElse(null);
            } else if (CustomQuery.class.isAssignableFrom(clazz)) {
                CustomQuery customQuery = (CustomQuery) Arrays.stream(clazz.getConstructors()).findFirst().get().newInstance();
                return (T) neo4jClient.query(query).in(databaseName).bindAll(parameters).fetchAs(CustomQuery.class).mappedBy((t, r) -> customQuery.build(r)).one().orElse(null);
            } else if (Number.class.isAssignableFrom(clazz) || String.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz)) {
                return neo4jClient.query(query).in(databaseName).bindAll(parameters).fetchAs(clazz).one().orElse(null);
            }

            Constructor<T> constructor = (Constructor<T>) Arrays.stream(clazz.getConstructors()).findFirst().get();
            constructor.setAccessible(true);

            return neo4jClient.query(query).in(databaseName).bindAll(parameters).fetchAs(clazz)
                    .mappedBy( (t,r) -> {
                        try {
                            T tt = constructor.newInstance();
                            return ReflectionUtils.build(tt, r);
                        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                            return null;
                        }
                    }).one().orElse(null);

        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new CustomQueryException(e);
        }
    }

    public <T> Collection<T> customQueryResults(Class<T> clazz, String query, Map<String, Object> parameters) throws CustomQueryException {
        try {
            if (parameters == null) parameters = Collections.EMPTY_MAP;

            if (DatabaseObject.class.isAssignableFrom(clazz)) {
                return (Collection<T>) neo4jTemplate.findAll(query, parameters, DatabaseObject.class);
            } else if (CustomQuery.class.isAssignableFrom(clazz)) {
                CustomQuery customQuery = (CustomQuery) Arrays.stream(clazz.getConstructors()).findFirst().get().newInstance();
                return (Collection<T>) neo4jClient.query(query).in(databaseName).bindAll(parameters).fetchAs(CustomQuery.class).mappedBy((t, r) -> customQuery.build(r)).all();
            } else if (Number.class.isAssignableFrom(clazz) || String.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz)) {
                return neo4jClient.query(query).in(databaseName).bindAll(parameters).fetchAs(clazz).all();
            }

            Constructor<T> constructor = (Constructor<T>) Arrays.stream(clazz.getConstructors()).findFirst().get();
            constructor.setAccessible(true);

            return neo4jClient.query(query).in(databaseName).bindAll(parameters).fetchAs(clazz)
                    .mappedBy( (t,r) -> {
                        try {
                            T tt = constructor.newInstance();
                            return ReflectionUtils.build(tt, r);
                        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                            return null;
                        }
                    }).all();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new CustomQueryException(e);
        }
    }
}
