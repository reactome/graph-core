package org.reactome.server.graph.service;

import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.domain.result.ComponentOf;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.domain.result.SimpleDatabaseObject;
import org.reactome.server.graph.repository.GeneralNeo4jOperationsRepository;
import org.reactome.server.graph.repository.GeneralRepository;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@Service
public class GeneralService {

    private static final Logger logger = LoggerFactory.getLogger(GeneralService.class);

    @Autowired
    private GeneralNeo4jOperationsRepository generalNeo4jOperationsRepository;



    @Autowired
    public GeneralRepository generalRepository;

    // Find methods

//    todo could also be solved in GeneralRepository, Will not contain any relationships
//    public <T> T find(Class<T> clazz, String id) {
//        id = DatabaseObjectUtils.trimId(id);
//        if (DatabaseObjectUtils.isStId(id)) {
//            return generalNeo4jOperationsRepository.findByStableIdentifier(clazz, id);
//        } else if (DatabaseObjectUtils.isDbId(id)){
//            return generalNeo4jOperationsRepository.findByDbId(clazz, Long.parseLong(id));
//        }
//        return null;
//    }

    public <T> T findNoRelation(Class<T> clazz, String id) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return generalNeo4jOperationsRepository.findByStIdNoRelations(clazz, id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return generalNeo4jOperationsRepository.findByDbIdNoRelations(clazz, Long.parseLong(id));
        }
        return null;
    }

    public DatabaseObject find(String id, RelationshipDirection direction) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return generalNeo4jOperationsRepository.findByStId(id, direction);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return generalNeo4jOperationsRepository.findByDbId(Long.parseLong(id), direction);
        }
        return null;
    }

    public DatabaseObject find(String id, RelationshipDirection direction, String... relationships) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return generalNeo4jOperationsRepository.findByStId(id, direction, relationships);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return generalNeo4jOperationsRepository.findByDbId(Long.parseLong(id), direction, relationships);
        }
        return null;
    }

    // Find by property and depth

    public <T> T findByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return generalNeo4jOperationsRepository.findByProperty(clazz, property, value, depth);
    }

    public <T> Collection<T> findAllByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return generalNeo4jOperationsRepository.findAllByProperty(clazz, property, value, depth);
    }

    //    todo Method is currently broken report to SDN
    @Deprecated
    public <T> Collection<T> findByProperties(Class<T> clazz, String property, Collection<Object> values, Integer depth) {
        return generalNeo4jOperationsRepository.findByProperties(clazz, property, values, depth);
    }

    // Default Finder Methods

//    //    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> T findByDbId(Class<T> clazz, Long dbId) {
//        return generalNeo4jOperationsRepository.findByDbId(clazz, dbId);
//    }
//
//    //    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> T findByStableIdentifier(Class<T> clazz, String stableIdentifier) {
//        return generalNeo4jOperationsRepository.findByStableIdentifier(clazz, stableIdentifier);
//    }
//
//    //    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> Iterable<T> findByDbIds(Class<T> clazz, Collection<Long> dbIds) {
//        return generalNeo4jOperationsRepository.findByDbIds(clazz, dbIds);
//    }
//
//    //    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> Iterable<T> findByStableIdentifiers(Class<T> clazz, Collection<String> stableIdentifiers) {
//        return generalNeo4jOperationsRepository.+findByStableIdentifiers(clazz, stableIdentifiers);
//    }

//    // Finder Methods without Relationships

    public <T> T findByDbIdNoRelations(Class<T> clazz, Long dbId) {
        return generalNeo4jOperationsRepository.findByDbIdNoRelations(clazz, dbId);
    }

    public <T> T findByStIdNoRelations(Class<T> clazz, String stId) {
        return generalNeo4jOperationsRepository.findByStIdNoRelations(clazz, stId);
    }

    public <T> Iterable<T> findByDbIdsNoRelations(Class<T> clazz, Collection<Long> dbIds) {
        return generalNeo4jOperationsRepository.findByDbIdsNoRelations(clazz, dbIds);
    }

    public <T> Iterable<T> findByStIdsNoRelations(Class<T> clazz, Collection<String> stIds) {
        return generalNeo4jOperationsRepository.findByStIdsNoRelations(clazz, stIds);
    }

    // Finder Methods with RelationshipDirection and Relationships

    public DatabaseObject findByDbId(Long dbId, RelationshipDirection direction) {
        return generalNeo4jOperationsRepository.findByDbId(dbId, direction);
    }

    public DatabaseObject findByStId(String stId, RelationshipDirection direction) {
        return generalNeo4jOperationsRepository.findByStId(stId, direction);
    }

    public DatabaseObject findByDbId (Long dbId, RelationshipDirection direction, String... relationships) {
        return generalNeo4jOperationsRepository.findByDbId(dbId, direction, relationships);
    }

    public DatabaseObject findByStId (String stId, RelationshipDirection direction, String... relationships) {
        return generalNeo4jOperationsRepository.findByStId(stId, direction, relationships);
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction) {
        return generalNeo4jOperationsRepository.findByDbIds(dbIds, direction);
    }

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction) {
        return generalNeo4jOperationsRepository.findByStIds(stIds, direction);
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction, String... relationships) {
        return generalNeo4jOperationsRepository.findByDbIds(dbIds, direction, relationships);
    }

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction, String... relationships) {
        return generalNeo4jOperationsRepository.findByStIds(stIds, direction, relationships);
    }

    // Find by Class Name

    public <T> Collection<T> findObjectsByClassName(String className) {
        try {
            Class clazz = DatabaseObjectUtils.getClassForName(className);
            return generalNeo4jOperationsRepository.findObjectsByClassName(clazz);
        } catch (ClassNotFoundException e) {
            logger.warn("Class name " + className + " was not found in the DataModel");
        }
        return Collections.emptyList();
    }

    public <T> Collection<T> findObjectsByClassName(String className, Integer page, Integer offset) throws ClassNotFoundException {
        try {
            Class clazz = DatabaseObjectUtils.getClassForName(className);
            return generalNeo4jOperationsRepository.findObjectsByClassName(clazz, page, offset);
        } catch (ClassNotFoundException e) {
            logger.warn("Class name " + className + " was not found in the DataModel");
        }
        return Collections.emptyList();
    }

    public Collection<String> findSimpleReferencesByClassName(String className) {
        return generalNeo4jOperationsRepository.findSimpleReferencesByClassName(className);
    }


    // Save and Delete

    public <T extends DatabaseObject> T save(T t) {
        return generalNeo4jOperationsRepository.save(t);
    }

    public <T extends DatabaseObject> T save(T t, int depth) {
        return generalNeo4jOperationsRepository.save(t, depth);
    }

    public void delete (Object o)  {
        generalNeo4jOperationsRepository.delete(o);
    }

    public void delete(Long dbId) {
        generalNeo4jOperationsRepository.delete(dbId);
    }

    public void delete(String stId) {
        generalNeo4jOperationsRepository.delete(stId);
    }

    // Method for querying without mapping to Objects

    public Result query (String query, Map<String,Object> map) {
        return generalNeo4jOperationsRepository.query(query,map);
    }

    // Count entries of class

    public Long countEntries(Class<?> clazz){
        return generalNeo4jOperationsRepository.countEntries(clazz);
    }

    // Utility Methods used in JUnit Tests

    public void clearCache() {
        generalNeo4jOperationsRepository.clearCache();
    }

    public boolean fitForService()  {
        return generalNeo4jOperationsRepository.fitForService();
    }

    // Gets all schema classes and their counts

    public Collection<SchemaClassCount> getSchemaClassCounts() {
        return generalRepository.getSchemaClassCounts();
    }

    public Collection<Species> getAllSpecies() {
        return generalRepository.getAllSpecies();
    }

    public Collection<ReferenceEntity> getAllChemicals() {
        return generalRepository.getAllChemicals();
    }

    // Gets componentsOf relation (hasEvent, input, output, ...)

    public Collection<ComponentOf> getComponentsOf(String stId) {
        return generalRepository.getComponentsOf(stId);
    }

    public Collection<ComponentOf> getComponentsOf(Long dbId) {
        return generalRepository.getComponentsOf(dbId);
    }



    public Collection<SimpleDatabaseObject> getPathwaysFor(String stId, Long speciesId){
        return generalRepository.getPathwaysFor(stId, speciesId);
    }


    // Gets release version

    public Integer getDBVersion() {
        return generalRepository.getDBVersion();
    }

    public String getDBName() {
        return generalRepository.getDBName();
    }
}
