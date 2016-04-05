package org.reactome.server.tools.repository;

import org.reactome.server.tools.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

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

}