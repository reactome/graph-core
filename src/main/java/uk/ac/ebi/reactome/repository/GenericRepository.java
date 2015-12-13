package uk.ac.ebi.reactome.repository;

import org.springframework.stereotype.Repository;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 11.11.15.
 */
@Repository
public interface GenericRepository {

    <T> T loadByProperty(Class<T> clazz, String property, Object value);
    <T> T loadById(Class<T> clazz, Long id, Integer depth);
    <T> T findByDbId(Class<T> clazz, Long dbId, Integer depth);
    <T> T findByStId(Class<T> clazz, String stId, Integer depth);

    Long countEntries(Class<?> clazz);
    void cleanDatabase();



    public boolean addRelationship(Long dbIdA, Long dbIdB, String relationshipName);
}
