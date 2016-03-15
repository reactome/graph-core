package uk.ac.ebi.reactome.service;

import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.11.15.
 */
public abstract class ServiceImpl<T> implements Service<T> {

    protected abstract GraphRepository<T> getRepository();

    @Override
    public T find(Long id){
        return getRepository().findOne(id);
    }

    @Override
    public T find(Long id, Integer depth) {
        return getRepository().findOne(id, depth);
    }

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T save(T entity, Integer depth) {
        return getRepository().save(entity, depth);
    }

    @Override
    public void delete(Long id) {
        getRepository().delete(id);
    }
}
