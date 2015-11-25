package uk.ac.ebi.reactome.service;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
public interface GenericService {

    <T> T loadByProperty(Class<T> clazz, String property, Object value);

    <T> T loadById(Class<T> clazz, Long id, Integer depth);

    <T> T findByDbId(Class<T> clazz, Long dbId, Integer depth);

    <T> T findByStId(Class<T> clazz, String stId, Integer depth);

    Long countEntries(Class<?> clazz);

    void cleanDatabase();
}
