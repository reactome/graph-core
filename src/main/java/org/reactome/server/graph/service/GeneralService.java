package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.DBInfo;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.repository.CRUDRepository;
import org.reactome.server.graph.repository.DBInfoRepository;
import org.reactome.server.graph.repository.GeneralTemplateRepository;
import org.reactome.server.graph.repository.SchemaRepository;
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
@SuppressWarnings("WeakerAccess")
public class GeneralService {

    private final GeneralTemplateRepository generalTemplateRepository;
    private final SchemaRepository schemaRepository;
    private final DBInfoRepository dbInfoRepository;
    private final CRUDRepository crudRepository;

    public GeneralService(GeneralTemplateRepository generalTemplateRepository, SchemaRepository schemaRepository, DBInfoRepository dbInfoRepository, CRUDRepository crudRepository) {
        this.generalTemplateRepository = generalTemplateRepository;
        this.schemaRepository = schemaRepository;
        this.dbInfoRepository = dbInfoRepository;
        this.crudRepository = crudRepository;
    }

    private static DBInfo dbInfo = null;

    public DBInfo getDBInfo() {
        if(dbInfo == null) dbInfo = dbInfoRepository.getDBInfo();
        return dbInfo;
    }

    // Gets all schema classes and their counts
    public Collection<SchemaClassCount> getSchemaClassCounts() {
        return schemaRepository.getSchemaClassCounts();
    }

    // --------------------------------------.. Generic Query Methods --------------------------------------------------

    public Collection<Map<String, Object>> query (String query, Map<String,Object> map) {
        return generalTemplateRepository.query(query,map);
    }

    // ------------------------------------------- Save and Delete -----------------------------------------------------

    public <T extends DatabaseObject> T save(T t) {
        return crudRepository.save(t);
    }

    @Deprecated
    public <T extends DatabaseObject> T save(T t, int depth) {
        return null;
    }

    public void delete(DatabaseObject o)  {
        crudRepository.delete(o);
    }

    public void delete(Object id, Class<?> _clazz)  {
        crudRepository.delete(id, _clazz);
    }

    public void deleteAll(Class<?> _clazz) {
        crudRepository.deleteAllByClass(_clazz);
    }

    public void delete(Long dbId) {
        crudRepository.delete(dbId);
    }

    public void delete(String stId) {
        crudRepository.delete(stId);
    }

    // ------------------------------------ Utility Methods for JUnit Tests --------------------------------------------

    @Deprecated
    public void clearCache() {}

}
