package uk.ac.ebi.reactome.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.reactome.repository.GenericRepository;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@Service
public class GenericServiceImpl implements GenericService {

    private static final Logger logger = LoggerFactory.getLogger(GenericServiceImpl.class);

    @Autowired
    private GenericRepository genericRepository;

    @Override
    public <T> T findByPropertyIncludingSecondSteps(String property, Object value, String... relationships) {
        return genericRepository.findByPropertyIncludingSecondSteps(property,value,relationships);
    }

    @Override
    public <T> T findByPropertyWithRelations (String property, Object value, String... relationships){
        return genericRepository.findByPropertyWithRelations(property, value, relationships);
    }

    @Override
    public <T> T findByPropertyWithoutRelations (String property, Object value, String... relationships){
        return genericRepository.findByPropertyWithoutRelations(property, value, relationships);
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
    public Long countEntries(Class<?> clazz){
        return genericRepository.countEntries(clazz);
    }

    @Override
    public void clear() {
        genericRepository.clear();
    }

}
