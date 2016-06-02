package org.reactome.server.graph.service;

import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.reactome.server.graph.domain.model.Species;
import org.reactome.server.graph.domain.result.ComponentOf;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.repository.GeneralTemplateRepository;
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
@SuppressWarnings("WeakerAccess")
public class GeneralService {

    private static final Logger logger = LoggerFactory.getLogger(GeneralService.class);

    @Autowired
    public GeneralRepository generalRepository;

    @Autowired
    private GeneralTemplateRepository generalTemplateRepository;

    // --------------------------------------------- General Repository ------------------------------------------------

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

    public Collection<ComponentOf> getComponentsOf(Object identifier) {

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return generalRepository.getComponentsOf(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return generalRepository.getComponentsOf(Long.parseLong(id));
        }
        return null;
    }

    // Gets release version

    public Integer getDBVersion() {
        return generalRepository.getDBVersion();
    }

    public String getDBName() {
        return generalRepository.getDBName();
    }


    // -------------------------------------- General Template Repository ----------------------------------------------

    // --------------------------------------- Generic Finder Methods --------------------------------------------------

    public <T> T findByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return generalTemplateRepository.findByProperty(clazz, property, value, depth);
    }

    public <T> Collection<T> findAllByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return generalTemplateRepository.findAllByProperty(clazz, property, value, depth);
    }

    // --------------------------------------- Enhanced Finder Methods -------------------------------------------------

    public DatabaseObject findEnhancedObjectById(Object identifier) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return generalTemplateRepository.findEnhancedObjectById(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return generalTemplateRepository.findEnhancedObjectById(Long.parseLong(id));
        }
        return null;
    }

    // ---------------------- Methods with RelationshipDirection and Relationships -------------------------------------

    public DatabaseObject findById (Object identifier, RelationshipDirection direction) {

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return generalTemplateRepository.findById(id, direction);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return generalTemplateRepository.findById(Long.parseLong(id), direction);
        }
        return null;
    }

    public DatabaseObject findById (Object identifier, RelationshipDirection direction, String... relationships) {

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return generalTemplateRepository.findById(id, direction, relationships);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return generalTemplateRepository.findById(Long.parseLong(id), direction, relationships);
        }
        return null;
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction) {
        return generalTemplateRepository.findByDbIds(dbIds, direction);
    }

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction) {
        return generalTemplateRepository.findByStIds(stIds, direction);
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction, String... relationships) {
        return generalTemplateRepository.findByDbIds(dbIds, direction, relationships);
    }

    public Collection<DatabaseObject> findByStIds(Collection<String> stIds, RelationshipDirection direction, String... relationships) {
        return generalTemplateRepository.findByStIds(stIds, direction, relationships);
    }

    // ---------------------------------------- Class Level Operations -------------------------------------------------

    //todo what do we return
    public <T> Collection<T> findObjectsByClassName(String className) {
        try {
            Class clazz = DatabaseObjectUtils.getClassForName(className);
            return generalTemplateRepository.findObjectsByClassName(clazz);
        } catch (ClassNotFoundException e) {
            logger.warn("Class name " + className + " was not found in the DataModel");
        }
        return Collections.emptyList();
    }

    public <T> Collection<T> findObjectsByClassName(String className, Integer page, Integer offset) {
        try {
            Class clazz = DatabaseObjectUtils.getClassForName(className);
            return generalTemplateRepository.findObjectsByClassName(clazz, page, offset);
        } catch (ClassNotFoundException e) {
            logger.warn("Class name " + className + " was not found in the DataModel");
        }
        return Collections.emptyList();
    }

    public Collection<String> findSimpleReferencesByClassName(String className) {
        return generalTemplateRepository.findSimpleReferencesByClassName(className);
    }

    public Long countEntries(Class<?> clazz){
        return generalTemplateRepository.countEntries(clazz);
    }

    // --------------------------------------.. Generic Query Methods --------------------------------------------------

    public Result query (String query, Map<String,Object> map) {
        return generalTemplateRepository.query(query,map);
    }

    // ------------------------------------------- Save and Delete -----------------------------------------------------

    @SuppressWarnings("UnusedReturnValue")
    public <T extends DatabaseObject> T save(T t) {
        return generalTemplateRepository.save(t);
    }

    @SuppressWarnings("UnusedReturnValue")
    public <T extends DatabaseObject> T save(T t, int depth) {
        return generalTemplateRepository.save(t, depth);
    }

    public void delete (Object o)  {
        generalTemplateRepository.delete(o);
    }

    public void delete(Long dbId) {
        generalTemplateRepository.delete(dbId);
    }

    public void delete(String stId) {
        generalTemplateRepository.delete(stId);
    }

    // ------------------------------------ Utility Methods for JUnit Tests --------------------------------------------

    public void clearCache() {
        generalTemplateRepository.clearCache();
    }

    public boolean fitForService()  {
        return generalTemplateRepository.fitForService();
    }

}


// Find methods

//    todo could also be solved in GeneralRepository, Will not contain any relationships
//    public <T> T find(Class<T> clazz, String id) {
//        id = DatabaseObjectUtils.trimId(id);
//        if (DatabaseObjectUtils.isStId(id)) {
//            return generalTemplateRepository.findByStableIdentifier(clazz, id);
//        } else if (DatabaseObjectUtils.isDbId(id)){
//            return generalTemplateRepository.findByDbId(clazz, Long.parseLong(id));
//        }
//        return null;
//    }

//    public <T> T findNoRelation(Class<T> clazz, String id) {
//        id = DatabaseObjectUtils.trimId(id);
//        if (DatabaseObjectUtils.isStId(id)) {
//            return generalTemplateRepository.findByStIdNoRelations(clazz, id);
//        } else if (DatabaseObjectUtils.isDbId(id)){
//            return generalTemplateRepository.findByDbIdNoRelations(clazz, Long.parseLong(id));
//        }
//        return null;
//    }
// Default Finder Methods

//    //    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> T findByDbId(Class<T> clazz, Long dbId) {
//        return generalTemplateRepository.findByDbId(clazz, dbId);
//    }
//
//    //    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> T findByStableIdentifier(Class<T> clazz, String stableIdentifier) {
//        return generalTemplateRepository.findByStableIdentifier(clazz, stableIdentifier);
//    }
//
//    //    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> Iterable<T> findByDbIds(Class<T> clazz, Collection<Long> dbIds) {
//        return generalTemplateRepository.findByDbIds(clazz, dbIds);
//    }
//
//    //    todo could also be solved in GeneralRepository, Will not contain any relationships
//    @Deprecated
//    public <T> Iterable<T> findByStableIdentifiers(Class<T> clazz, Collection<String> stableIdentifiers) {
//        return generalTemplateRepository.+findByStableIdentifiers(clazz, stableIdentifiers);
//    }






//    // Finder Methods without Relationships

//    public <T> T findByDbIdNoRelations(Class<T> clazz, Long dbId) {
//        return generalTemplateRepository.findByDbIdNoRelations(clazz, dbId);
//    }
//
//    public <T> T findByStIdNoRelations(Class<T> clazz, String stId) {
//        return generalTemplateRepository.findByStIdNoRelations(clazz, stId);
//    }
//
//    public <T> Iterable<T> findByDbIdsNoRelations(Class<T> clazz, Collection<Long> dbIds) {
//        return generalTemplateRepository.findByDbIdsNoRelations(clazz, dbIds);
//    }
//
//    public <T> Iterable<T> findByStIdsNoRelations(Class<T> clazz, Collection<String> stIds) {
//        return generalTemplateRepository.findByStIdsNoRelations(clazz, stIds);
//    }

