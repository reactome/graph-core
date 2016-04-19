package org.reactome.server.tools.repository;

import org.reactome.server.tools.domain.model.PhysicalEntity;
import org.reactome.server.tools.domain.model.ReferenceEntity;
import org.reactome.server.tools.domain.result.Participant;
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

    PhysicalEntity findByDbId(Long dbId);
    PhysicalEntity findByStableIdentifier(String stableIdentifier);

    @Query("Match (n:PhysicalEntity{dbId:{0}})-[:referenceEntity]->(m:ReferenceEntity)<-[:referenceEntity]-(k) Where NOT n=k RETURN k")
    Collection<PhysicalEntity> getOtherFormsOfThisMolecule(Long dbId);

    @Query("Match (n:PhysicalEntity{stableIdentifier:{0}})-[:referenceEntity]->(m:ReferenceEntity)<-[:referenceEntity]-(k) Where NOT n=k RETURN k")
    Collection<PhysicalEntity> getOtherFormsOfThisMolecule(String stableIdentifier);

}