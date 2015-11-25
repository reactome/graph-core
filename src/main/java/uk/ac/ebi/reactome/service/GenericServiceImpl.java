package uk.ac.ebi.reactome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.reactome.repository.GenericRepository;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@Service
@Transactional(readOnly = true)
public class GenericServiceImpl implements GenericService {

    //    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private  final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    @Autowired
    private GenericRepository genericRepository;

    @Override
    public <T>T loadByProperty(Class<T> clazz, String property, Object value){
        return genericRepository.loadByProperty(clazz,property,value);
    }

    @Override
    public <T> T loadById(Class<T> clazz, Long id, Integer depth){
        return genericRepository.loadById(clazz, id, depth);
    }

    @Override
    public <T>T findByDbId(Class<T> clazz, Long dbId, Integer depth) {
        return genericRepository.findByDbId(clazz, dbId, depth);
    }

    @Override
    public <T>T  findByStId(Class<T> clazz, String stId, Integer depth) {
        return genericRepository.findByStId(clazz, stId, depth);
    }

    @Override
    public Long countEntries(Class<?> clazz){
        return genericRepository.countEntries(clazz);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void cleanDatabase() {
        genericRepository.cleanDatabase();;
        logger.info("GraphDatabase has been cleaned");
    }

}
