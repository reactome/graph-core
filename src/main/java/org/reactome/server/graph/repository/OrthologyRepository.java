package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Repository
public interface OrthologyRepository extends GraphRepository<PhysicalEntity> {

    //The relationship do not have direction because that's what is needed in this case
    @Query(" MATCH (:DatabaseObject{dbId:{0}})<-[:inferredTo]-()-[:inferredTo]->(o:DatabaseObject)-[:species]->(:Species{dbId:{1}}) RETURN DISTINCT o " +
            "UNION " +
            "MATCH (:DatabaseObject{dbId:{0}})-[:inferredTo]-(o:DatabaseObject)-[:species]->(:Species{dbId:{1}}) RETURN DISTINCT o")
    DatabaseObject getOrthology(Long dbId, Long speciesId);

    //The relationship do not have direction because that's what is needed in this case
    @Query(" MATCH (:DatabaseObject{stId:{0}})<-[:inferredTo]-()-[:inferredTo]->(o:DatabaseObject)-[:species]->(:Species{dbId:{1}}) RETURN DISTINCT o " +
            "UNION " +
            "MATCH (:DatabaseObject{stId:{0}})-[:inferredTo]-(o:DatabaseObject)-[:species]->(:Species{dbId:{1}}) RETURN DISTINCT o")
    DatabaseObject getOrthology(String stId, Long speciesId);
}
