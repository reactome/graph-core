package uk.ac.ebi.reactome.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
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
    public DatabaseObject findByDbIdWithSession(Long dbId) {
        return genericRepository.findByDbId(dbId);
    }

    @Override
    public DatabaseObject findByDbIdWithSession(Long dbId, Integer depth) {
        return genericRepository.findByDbId(dbId, depth);
    }

    @Override
    public DatabaseObject findStIdWithSession(String stId) {
        return genericRepository.findByStId(stId);
    }

    @Override
    public DatabaseObject findStIdWithSession(String stId, Integer depth) {
        return genericRepository.findByStId(stId, depth);
    }

    @Override
    public void cleanDatabase() {
        genericRepository.cleanDatabase();;
        logger.info("GraphDatabase has been cleaned");
    }

}
