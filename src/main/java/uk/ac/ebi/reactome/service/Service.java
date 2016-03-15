package uk.ac.ebi.reactome.service;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.11.15.
 */
public interface Service<T> {

    T find(Long id);
    T find(Long id, Integer depth);
    T save(T entity);
    T save(T entity, Integer depth);
    void delete (Long id);
}
