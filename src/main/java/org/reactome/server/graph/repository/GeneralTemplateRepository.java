////package org.reactome.server.graph.repository;
////
////import org.neo4j.ogm.model.Result;
////import org.reactome.server.graph.domain.model.DatabaseObject;
////import org.reactome.server.graph.repository.util.RepositoryUtils;
////import org.reactome.server.graph.service.helper.RelationshipDirection;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.data.neo4j.template.Neo4jOperations;
////import org.springframework.stereotype.Repository;
////
////import java.util.*;
////
/////**
//// * Created by:
//// *
//// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
//// * @since 11.11.15.
//// */
////@Repository
////@SuppressWarnings("unused")
////public class GeneralTemplateRepository {
////
////    private static final Logger logger = LoggerFactory.getLogger(GeneralTemplateRepository.class);
////
////    @Autowired
////    private Neo4jOperations neo4jTemplate;
////
////    // --------------------------------------.. Generic Query Methods --------------------------------------------------
////
////    public Result query (String query, Map<String,Object> map) {
////        return neo4jTemplate.query(query,map);
////    }
////
////    // ------------------------------------------- Save and Delete -----------------------------------------------------
////
////    public <T extends DatabaseObject> T save(T t) {
////        return neo4jTemplate.save(t);
////    }
////
////    public <T extends DatabaseObject> T save(T t, int depth) {
////        return neo4jTemplate.save(t, depth);
////    }
////
////    public void delete(Object o)  {
////        neo4jTemplate.delete(o);
////    }
////
////    public void delete(Long dbId) {
////        String query = "MATCH (n:DatabaseObject{dbId:{dbId}}) OPTIONAL MATCH (n)-[r]-() DELETE n,r";
////        Map<String,Object> map = new HashMap<>();
////        map.put("dbId", dbId);
////        neo4jTemplate.query(query, map);
////    }
////
////    public void delete(String stId) {
////        String query = "MATCH (n:DatabaseObject{stId:{stId}}) OPTIONAL MATCH (n)-[r]-() DELETE n,r";
////        Map<String,Object> map = new HashMap<>();
////        map.put("stId", stId);
////        neo4jTemplate.query(query, map);
////    }
////
////    // ------------------------------------ Utility Methods for JUnit Tests --------------------------------------------
////
////    public boolean fitForService() {
////        String query = "Match (n) Return Count(n)>0 AS fitForService";
////        try {
////            Result result = neo4jTemplate.query(query, Collections.emptyMap());
////            if (result != null && result.iterator().hasNext())
////                return (boolean) result.iterator().next().get("fitForService");
////        } catch (Exception e) {
////            logger.error("A connection with the Neo4j Graph could not be established. Tests will be skipped", e);
////        }
////        return false;
////    }
////
////    public void clearCache() {
////        neo4jTemplate.clear();
////    }
////
////}
