package uk.ac.ebi.reactome.service;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.11.15.
 */
@SuppressWarnings("SameParameterValue")
public interface Service<T> {

    T find(Long id);
    T find(Long id, Integer depth);
    @SuppressWarnings("UnusedReturnValue")
    T save(T entity);
    @SuppressWarnings("UnusedReturnValue")
    T save(T entity, Integer depth);
    void delete (Long id);
}
