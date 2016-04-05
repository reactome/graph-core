package org.reactome.server.tools.service;

import org.neo4j.ogm.model.Result;
import org.reactome.server.tools.domain.model.DatabaseObject;
import org.reactome.server.tools.domain.model.Pathway;
import org.reactome.server.tools.domain.model.Species;

import java.util.Collection;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@SuppressWarnings("SameParameterValue")
public interface GenericService {

    <T> T findById(Class<T> clazz, Long id, Integer depth);
    <T> T findByProperty(Class<T> clazz, String property, Object value, Integer depth);
    <T> T findByDbId(Class<T> clazz, Long dbId, Integer depth);
    <T> T findByStableIdentifier(Class<T> clazz, String stableIdentifier, Integer depth);

    <T> Collection<T> getObjectsByClassName(String className, Integer page, Integer offset) throws ClassNotFoundException;

    Object findByPropertyWithRelations (String property, Object value, String... relationships);
    Object findByPropertyWithoutRelations (String property, Object value, String... relationships);

    Collection<Pathway> getTopLevelPathways();
    Collection<Pathway> getTopLevelPathways(Long speciesId);
    Collection<Pathway> getTopLevelPathways(String speciesName);

    Pathway getEventHierarchy(Long dbId);
    DatabaseObject getLocationsHierarchy(String stId);
    DatabaseObject getReferral(Long dbId, String relationshipName);
    Collection<DatabaseObject> getReferrals(Long dbId, String relationshipName);


    Collection<Species> getSpecies();

    Result query (String query, Map<String,Object> map);
    Long countEntries(Class<?> clazz);

    boolean fitForService() ;
    void clearCache();

}
