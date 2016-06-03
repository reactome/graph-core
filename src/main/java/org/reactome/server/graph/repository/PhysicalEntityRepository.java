package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.02.16.
 */
@Repository
public interface PhysicalEntityRepository extends GraphRepository<PhysicalEntity> {

    @Query("Match (n:PhysicalEntity{dbId:{0}})-[:referenceEntity]->(m:ReferenceEntity)<-[:referenceEntity]-(k) Where NOT n=k RETURN k")
    Collection<PhysicalEntity> getOtherFormsOf(Long dbId);

    @Query("Match (n:PhysicalEntity{stId:{0}})-[:referenceEntity]->(m:ReferenceEntity)<-[:referenceEntity]-(k) Where NOT n=k RETURN k")
    Collection<PhysicalEntity> getOtherFormsOf(String stId);

}