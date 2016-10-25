package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Repository
public interface ReferenceEntityRepository extends GraphRepository<ReferenceEntity> {


    @Query(" MATCH (rd:ReferenceDatabase)<--(n{identifier:{0}})<-[:referenceEntity|referenceSequence|crossReference|referenceGene*]-(pe:PhysicalEntity) " +
            "WITH DISTINCT pe " +
            "MATCH (pe)-[:referenceEntity]->(n:ReferenceEntity)" +
            "RETURN DISTINCT n")
    Collection<ReferenceEntity> getReferenceEntitiesFor(String identifier);
}
