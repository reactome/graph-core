package uk.ac.ebi.reactome.repository;

import org.neo4j.ogm.model.Result;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.reactome.domain.model.Pathway;

import java.util.Collection;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 11.11.15.
 */
@Repository
public interface GenericRepository {

    <T> T findByPropertyIncludingSecondSteps(String property, Object value, String... relationships);
    <T> T findByPropertyWithRelations (String property, Object value, String... relationships);
    <T> T findByPropertyWithoutRelations (String property, Object value, String... relationships);
    <T> Collection<T> getObjectsByClassName(Class<T> clazz, Integer page, Integer offset);

    <T> T findByProperty(Class<T> clazz, String property, Object value, Integer depth);
    <T> T findById(Class<T> clazz, Long id, Integer depth);
    <T> T findByDbId(Class<T> clazz, Long dbId, Integer depth);
    <T> T findByStableIdentifier(Class<T> clazz, String stableIdentifier, Integer depth);

    Collection<Pathway> findTopLevelPathways();
    Collection<Pathway> findTopLevelPathways(Long speciesId);
    Collection<Pathway> findTopLevelPathways(String speciesName);

    Result query (String query, Map<String,Object> map);
    Long countEntries(Class<?> clazz);

    boolean fitForService();

    void clearCache();
}
