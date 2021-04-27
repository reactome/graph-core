package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvancedRepository extends CrudRepository<DatabaseObject, Long> {

    @Query("MATCH (:DatabaseObject{dbId:189102})-[r:output]->(m:PhysicalEntity) RETURN m, r.stoichiometry AS n ORDER BY TYPE(r) ASC, r.order ASC")
    DatabaseObjectDTOProjection<DatabaseObject> findDTOProjectionsWithCustomQuery();
}
