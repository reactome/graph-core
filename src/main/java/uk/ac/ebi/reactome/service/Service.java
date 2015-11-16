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

    Iterable<T> findAll();

    T save(T entity);
    void delete (Long id);


}
