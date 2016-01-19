package uk.ac.ebi.reactome.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.EntityWithAccessionedSequence;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 *
 */
@Repository
public interface DatabaseObjectRepository extends GraphRepository<DatabaseObject>{

    DatabaseObject findByDbId(Long dbId);
    DatabaseObject findByStableIdentifier(String stableIdentifier);

    @Query ("MATCH (n:DatabaseObject{dbId:{0}}) Return n")
    DatabaseObject find(Long dbId);

    @Query ("CREATE CONSTRAINT ON (n:DatabaseObject) ASSERT n.dbId is UNIQUE")
    void createConstraintOnDatabaseObjectDbId();

    @Query ("CREATE CONSTRAINT ON (n:DatabaseObject) ASSERT n.stableIdentifier is UNIQUE")
    void createConstraintOnDatabaseObjectStId();

    @Query ("MATCH (n:DatabaseObject{dbId:{0}})-[r]-() Return n,r")
    DatabaseObject find2(Long dbId);

    @Query ("MATCH (n:Pathway{dbId:{0}})-[r:hasEvent|input|output|hasMember|hasComponent|repeatedUnit*]->(m:EntityWithAccessionedSequence) RETURN DISTINCT m")
    Collection<EntityWithAccessionedSequence> getParticipating(Long dbId);
}
