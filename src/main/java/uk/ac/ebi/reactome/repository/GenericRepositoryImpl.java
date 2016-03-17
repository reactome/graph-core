package uk.ac.ebi.reactome.repository;

import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.reactome.domain.model.Pathway;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 11.11.15.
 */
@SuppressWarnings("unused")
@Repository
public class GenericRepositoryImpl implements GenericRepository {

    private static final Logger logger = LoggerFactory.getLogger(GenericRepository.class);

    @Autowired
    private Session session;

    @Autowired
    private Neo4jOperations neo4jTemplate;

    @Override
    public Object findByPropertyWithRelations (String property, Object value, String... relationships) {
        String query = "MATCH (n:DatabaseObject{" + property + ":{" + property + "}})-[r";
        query += getRelationshipAsString(relationships);
        query += "]->(m) RETURN n,r,m";
        Map<String,Object> map = new HashMap<>();
        map.put(property,value);
        Result result =  neo4jTemplate.query(query,map);
        if (result != null && result.iterator().hasNext())
            return result.iterator().next().get("n");
        return null;
    }

    @Override
    public Object findByPropertyWithoutRelations (String property, Object value, String... relationships) {
        String query = "MATCH (n:DatabaseObject{" + property + ":{" + property + "}})-[r]->(m) WHERE NOT (n)-[r";
        query += getRelationshipAsString(relationships);
        query += "]->(m) RETURN n,r,m";
        Map<String,Object> map = new HashMap<>();
        map.put(property,value);
        Result result =  neo4jTemplate.query(query,map);
        if (result != null && result.iterator().hasNext())
            return  result.iterator().next().get("n");
        return null;
    }

    public <T> Collection<T> getObjectsByClassName(Class<T> clazz, Integer page, Integer offset) {
        String query = "MATCH (n:" +
                clazz.getSimpleName() +
                ") RETURN n ORDER BY n.displayName SKIP {skip} LIMIT {limit}";

        Map<String,Object> map = new HashMap<>();
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return (Collection<T>) neo4jTemplate.queryForObjects(clazz, query, map);
    }

    @Override
    public <T> T findByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return neo4jTemplate.loadByProperty(clazz, property, value, depth);
    }

    @Override
    public <T> T findById(Class<T> clazz, Long id, Integer depth) {
        return neo4jTemplate.load(clazz, id, depth);
    }

    @Override
    public <T> T findByDbId(Class<T> clazz, Long dbId, Integer depth) {
        Collection<T> collection = session.loadAll(clazz, new Filter("dbId", dbId), depth);
        if (collection != null && !collection.isEmpty()) {
            return collection.iterator().next();
        }
        return null;
    }

    @Override
    public <T> T findByStableIdentifier(Class<T> clazz, String stableIdentifier, Integer depth) {
        Collection<T> collection = session.loadAll(clazz, new Filter("stableIdentifier", stableIdentifier), depth);
        if (collection != null && !collection.isEmpty()) {
            return collection.iterator().next();
        }
        return null;
    }

    @Override
    public Collection<Pathway> findTopLevelPathways() {
        String query = "Match (n:TopLevelPathway) RETURN n";
        return (Collection<Pathway>) neo4jTemplate.queryForObjects(Pathway.class, query, Collections.<String,Object>emptyMap());
    }

    @Override
    public Collection<Pathway> findTopLevelPathways(Long speciesId) {
        String query = "Match (n:TopLevelPathway)-[:species]-(s) Where s.dbId = {speciesId} RETURN n";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesId", speciesId);
        return (Collection<Pathway>) neo4jTemplate.queryForObjects(Pathway.class, query, map);
    }

    @Override
    public Collection<Pathway> findTopLevelPathways(String speciesName) {
        String query = "Match (n:TopLevelPathway)-[:species]-(s) Where s.displayName = {speciesName} RETURN n";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesName", speciesName);
        return (Collection<Pathway>) neo4jTemplate.queryForObjects(Pathway.class, query, map);
    }

    @Override
    public Result query (String query, Map<String,Object> map) {
        return neo4jTemplate.query(query,map);
    }

    @Override
    public Long countEntries(Class<?> clazz) {
        return neo4jTemplate.count(clazz);
    }

    @Override
    public boolean fitForService() {
        String query = "Match (n) Return Count(n)>0 AS fitForService";
        try {
            Result result = neo4jTemplate.query(query, Collections.<String,Object>emptyMap());
            if (result != null && result.iterator().hasNext())
                return (boolean) result.iterator().next().get("fitForService");
        } catch (Exception e) {
            logger.error("A connection with the Neo4j Graph could not be established. Tests will be skipped");
        }
        return false;
    }

    @Override
    public void clearCache() {
        neo4jTemplate.clear();
    }

    private String getRelationshipAsString (String... relationships) {
        String result = "";
        if (relationships != null && relationships.length > 0) {
            String pipe = ":";
            for (String relationship : relationships) {
                result += pipe + relationship;
                pipe = "|";
            }
        }
        return result;
    }
}
