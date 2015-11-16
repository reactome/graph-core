package uk.ac.ebi.reactome.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.reactome.domain.model.Interactor;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@Repository
public interface InteractorRepository extends GraphRepository<Interactor>{

    @Query ("MERGE (n:INTERACTOR {id:{0}}) " +
            "ON CREATE SET n =  {id:{0},name:{1}} " +
            "ON MATCH  SET n += {name:{1}} " +
            "RETURN n")
    Interactor merge(String id, String name);

    @Query ("MATCH(a:INTERACTOR {id:{0}}),(b:INTERACTOR {id:{1}}) " +
            "MERGE (a)-[r:INTERACTS_WITH{score:{2}}]-(b) " +
            "RETURN COUNT(r)=1")
    boolean createInteraction(String idA, String idB, Double score);

}
