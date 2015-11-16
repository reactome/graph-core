package uk.ac.ebi.reactome.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 11.11.15.
 */
@Repository
public interface GenericRepository {

    void cleanDatabase();

    DatabaseObject findByDbId(Long dbId);
    DatabaseObject findByDbId(Long dbId, Integer depth);

    DatabaseObject findByStId(String stId);
    DatabaseObject findByStId(String stId, Integer depth);
}
