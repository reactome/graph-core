package org.reactome.server.tools.service;

import org.neo4j.ogm.model.Result;
import org.reactome.server.tools.domain.model.DatabaseObject;
import org.reactome.server.tools.domain.model.Pathway;
import org.reactome.server.tools.domain.model.Species;
import org.reactome.server.tools.repository.GenericRepository;
import org.reactome.server.tools.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@Service
public class GenericServiceImpl implements GenericService {

//    private static final Logger logger = LoggerFactory.getLogger(GenericServiceImpl.class);

    @Autowired
    private GenericRepository genericRepository;

    @Override
    public Object findByPropertyWithRelations (String property, Object value, String... relationships){
        return genericRepository.findByPropertyWithRelations(property, value, relationships);
    }

    @Override
    public Object findByPropertyWithoutRelations (String property, Object value, String... relationships){
        return genericRepository.findByPropertyWithoutRelations(property, value, relationships);
    }

//    TODO fix warning
    @SuppressWarnings("unchecked")
    @Override
    public <T>Collection<T> getObjectsByClassName(String className, Integer page, Integer offset) throws ClassNotFoundException {
        return genericRepository.getObjectsByClassName(DatabaseObjectUtils.getClassForName(className),page,offset);
    }

    @Override
    public <T>T findByProperty(Class<T> clazz, String property, Object value, Integer depth){
        return genericRepository.findByProperty(clazz, property, value, depth);
    }

    @Override
    public <T> T findById(Class<T> clazz, Long id, Integer depth){
        return genericRepository.findById(clazz, id, depth);
    }

    @Override
    public <T>T findByDbId(Class<T> clazz, Long dbId, Integer depth) {
        return genericRepository.findByDbId(clazz, dbId, depth);
    }

    @Override
    public <T>T  findByStableIdentifier(Class<T> clazz, String stableIdentifier, Integer depth) {
        return genericRepository.findByStableIdentifier(clazz, stableIdentifier, depth);
    }

    @Override
    public Collection<Pathway> getTopLevelPathways() {
        return genericRepository.getTopLevelPathways();
    }

    @Override
    public Collection<Pathway> getTopLevelPathways(Long speciesId) {
        return genericRepository.getTopLevelPathways(speciesId);
    }

    @Override
    public Collection<Pathway> getTopLevelPathways(String speciesName) {
        return genericRepository.getTopLevelPathways(speciesName);
    }

    @Override
    public Pathway getEventHierarchy(Long dbId) {
        return genericRepository.getEventHierarchy(dbId);
    }


    public DatabaseObject getLocationsHierarchy(String stId){
        return genericRepository.getLocationsHierarchy(stId);
    }


    public DatabaseObject getReferral(Long dbId, String relationshipName) {
        return genericRepository.getReferral(dbId,relationshipName);
    }

    public Collection<DatabaseObject> getReferrals(Long dbId, String relationshipName){
        return genericRepository.getReferrals(dbId, relationshipName);
    }

    @Transactional
    public void tree() {
        Collection<DatabaseObject> eventHierarchy = genericRepository.getReferrals(445133L,"hasMember");
    }

//    private void



    @Override
    public Collection<Species> getSpecies() {
        return genericRepository.getSpecies();
    }

    @Override
    public Result query (String query, Map<String,Object> map) {
        return genericRepository.query(query,map);
    }

    @Override
    public Long countEntries(Class<?> clazz){
        return genericRepository.countEntries(clazz);
    }

    @Override
    public void clearCache() {
        genericRepository.clearCache();
    }

    @Override
    public boolean fitForService()  {
        return genericRepository.fitForService();
    }
}
