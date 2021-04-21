package org.reactome.server.graph.service;

import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.domain.model.DBInfo;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.SchemaClassCount;
import org.reactome.server.graph.repository.DBInfoRepository;
import org.reactome.server.graph.repository.GeneralRepository;
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

//    @Autowired
    public GeneralRepository generalRepository;

    private final GeneralTemplateRepository generalTemplateRepository;
    private final SchemaRepository schemaRepository;
    private final DBInfoRepository dbInfoRepository;

    public GeneralService(GeneralTemplateRepository generalTemplateRepository, SchemaRepository schemaRepository, DBInfoRepository dbInfoRepository) {
        this.generalTemplateRepository = generalTemplateRepository;
        this.schemaRepository = schemaRepository;
        this.dbInfoRepository = dbInfoRepository;
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

    public Result query (String query, Map<String,Object> map) {
        return generalTemplateRepository.query(query,map);
    }

    // ------------------------------------------- Save and Delete -----------------------------------------------------

    public <T extends DatabaseObject> T save(T t) {
        return generalTemplateRepository.save(t);
    }

    @Deprecated
    public <T extends DatabaseObject> T save(T t, int depth) {
        return generalTemplateRepository.save(t, depth);
    }

    // TODO Test
    public void delete(DatabaseObject o)  {
        generalTemplateRepository.delete(o);
    }

    public void delete(Object id, Class<?> _clazz)  {
        generalTemplateRepository.delete(id, _clazz);
    }

    public void deleteAll(Class<?> _clazz) {
        generalTemplateRepository.deleteAllByClass(_clazz);
    }

    public void delete(Long dbId) {
        generalTemplateRepository.delete(dbId);
    }

    public void delete(String stId) {
        generalTemplateRepository.delete(stId);
    }

    // ------------------------------------ Utility Methods for JUnit Tests --------------------------------------------

    @Deprecated
    public void clearCache() {
        generalTemplateRepository.clearCache();
    }

    public boolean fitForService()  {
        return generalTemplateRepository.fitForService();
    }

}
