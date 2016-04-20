package org.reactome.server.tools.service;

import org.neo4j.ogm.model.Result;
import org.reactome.server.tools.domain.model.DatabaseObject;
import org.reactome.server.tools.domain.model.ReferenceEntity;
import org.reactome.server.tools.domain.model.Species;
import org.reactome.server.tools.domain.model.TopLevelPathway;
import org.reactome.server.tools.domain.result.ComponentOf;
import org.reactome.server.tools.domain.result.SchemaClassCount;
import org.reactome.server.tools.repository.GeneralNeo4jOperationsRepository;
import org.reactome.server.tools.repository.GeneralRepository;
import org.reactome.server.tools.repository.TopLevelPathwayRepository;
import org.reactome.server.tools.service.helper.RelationshipDirection;
import org.reactome.server.tools.service.util.DatabaseObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
    public TopLevelPathwayRepository topLevelPathwayRepository;

    @Autowired
    public GeneralRepository generalRepository;

    // Find methods

    public <T> T find(Class<T> clazz, String id) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return generalNeo4jOperationsRepository.findByStableIdentifier(clazz, id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return generalNeo4jOperationsRepository.findByDbId(clazz, Long.parseLong(id));
        }
        return null;
    }

    public <T> T findNoRelation(Class<T> clazz, String id) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return generalNeo4jOperationsRepository.findByStableIdentifierNoRelation(clazz, id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return generalNeo4jOperationsRepository.findByDbIdNoRelation(clazz, Long.parseLong(id));
        }
        return null;
    }

    public DatabaseObject find(String id, RelationshipDirection direction) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return generalNeo4jOperationsRepository.findByStableIdentifier(id, direction);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return generalNeo4jOperationsRepository.findByDbId(Long.parseLong(id), direction);
        }
        return null;
    }

    public DatabaseObject find(String id, RelationshipDirection direction, String... relationships) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return generalNeo4jOperationsRepository.findByStableIdentifier(id, direction, relationships);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return generalNeo4jOperationsRepository.findByDbId(Long.parseLong(id), direction, relationships);
        }
        return null;
    }

    // Find by property and depth

    public <T> T findByProperty(Class<T> clazz, String property, Object value, Integer depth) {
        return generalNeo4jOperationsRepository.findByProperty(clazz, property, value, depth);
    }

    public <T> Collection<T> findByProperties(Class<T> clazz, String property, Collection<Object> values, Integer depth) {
        return generalNeo4jOperationsRepository.findByProperties(clazz, property, values, depth);
    }

    // Default Finder Methods

    public <T> T findByDbId(Class<T> clazz, Long dbId) {
        return generalNeo4jOperationsRepository.findByDbId(clazz, dbId);
    }

    public <T> T findByStableIdentifier(Class<T> clazz, String stableIdentifier) {
        return generalNeo4jOperationsRepository.findByStableIdentifier(clazz, stableIdentifier);
    }

    public <T> Iterable<T> findByDbIds(Class<T> clazz, Collection<Long> dbIds) {
        return generalNeo4jOperationsRepository.findByDbIds(clazz, dbIds);
    }

    public <T> Iterable<T> findByStableIdentifiers(Class<T> clazz, Collection<String> stableIdentifiers) {
        return generalNeo4jOperationsRepository.findByStableIdentifiers(clazz, stableIdentifiers);
    }

    // Finder Methods without Relationships

    public <T> T findByDbIdNoRelation(Class<T> clazz, Long dbId) {
        return generalNeo4jOperationsRepository.findByDbIdNoRelation(clazz, dbId);
    }

    public <T> T findByStableIdentifierNoRelation(Class<T> clazz, String stableIdentifier) {
        return generalNeo4jOperationsRepository.findByStableIdentifierNoRelation(clazz, stableIdentifier);
    }

    public <T> Iterable<T> findByDbIdsNoRelations(Class<T> clazz, Collection<Long> dbIds) {
        return generalNeo4jOperationsRepository.findByDbIdsNoRelations(clazz, dbIds);
    }

    public <T> Iterable<T> findByStableIdentifiersNoRelations(Class<T> clazz, Collection<String> stableIdentifiers) {
        return generalNeo4jOperationsRepository.findByStableIdentifiersNoRelations(clazz, stableIdentifiers);
    }

    // Finder Methods with RelationshipDirection and Relationships

    public DatabaseObject findByDbId(Long dbId, RelationshipDirection direction) {
        return generalNeo4jOperationsRepository.findByDbId(dbId, direction);
    }

    public DatabaseObject findByStableIdentifier(String stableIdentifier, RelationshipDirection direction) {
        return generalNeo4jOperationsRepository.findByStableIdentifier(stableIdentifier, direction);
    }

    public DatabaseObject findByDbId (Long dbId, RelationshipDirection direction, String... relationships) {
        return generalNeo4jOperationsRepository.findByDbId(dbId, direction, relationships);
    }

    public DatabaseObject findByStableIdentifier (String stableIdentifier, RelationshipDirection direction, String... relationships) {
        return generalNeo4jOperationsRepository.findByStableIdentifier(stableIdentifier, direction, relationships);
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction) {
        return generalNeo4jOperationsRepository.findByDbIds(dbIds, direction);
    }

    public Collection<DatabaseObject> findByStableIdentifiers(Collection<String> stableIdentifiers, RelationshipDirection direction) {
        return generalNeo4jOperationsRepository.findByStableIdentifiers(stableIdentifiers, direction);
    }

    public Collection<DatabaseObject> findByDbIds(Collection<Long> dbIds, RelationshipDirection direction, String... relationships) {
        return generalNeo4jOperationsRepository.findByDbIds(dbIds, direction, relationships);
    }

    public Collection<DatabaseObject> findByStableIdentifiers(Collection<String> stableIdentifiers, RelationshipDirection direction, String... relationships) {
        return generalNeo4jOperationsRepository.findByStableIdentifiers(stableIdentifiers, direction, relationships);
    }

    // Find by Class Name

    public <T> Collection<T> findObjectsByClassName(String className, Integer page, Integer offset) throws ClassNotFoundException {
        Class clazz = DatabaseObjectUtils.getClassForName(className);
        return generalNeo4jOperationsRepository.findObjectsByClassName(clazz, page, offset);
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

    public Collection<ComponentOf> getComponentsOf(String stableIdentifier) {
        return generalRepository.getComponentsOf(stableIdentifier);
    }

    public Collection<ComponentOf> getComponentsOf(Long dbId) {
        return generalRepository.getComponentsOf(dbId);
    }

    // Gets top level pathways

    public Collection<TopLevelPathway> getTopLevelPathways() {
        return topLevelPathwayRepository.getTopLevelPathways();
    }

    public Collection<TopLevelPathway> getTopLevelPathways(Long speciesId) {
        return topLevelPathwayRepository.getTopLevelPathways(speciesId);
    }

    public Collection<TopLevelPathway> getTopLevelPathways(String speciesName) {
        return topLevelPathwayRepository.getTopLevelPathways(speciesName);
    }
}
