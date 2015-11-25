package uk.ac.ebi.reactome.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;

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

    DatabaseObject findByStId(String stId);

    /*
     Methods for adding Relationships. This Methods are in this Repository to reduce the total amount of Repositories.
    */
    @Query("MATCH(a:ReactionLikeEvent {dbId:{0}}),(b:PhysicalEntity {dbId:{1}}) " +
            "MERGE (a)-[r:HAS_INPUT]->(b) " +
            "ON CREATE SET r =  {cardinality:1} " +
            "ON MATCH  SET r += {cardinality:(r.cardinality+1)} " +
            "RETURN COUNT(r)=1")
    boolean createInputRelationship(Long dbIdA, Long dbIdB);

    @Query ("MATCH(a:ReactionLikeEvent {dbId:{0}}),(b:PhysicalEntity {dbId:{1}}) " +
            "MERGE (a)-[r:HAS_OUTPUT]->(b) " +
            "ON CREATE SET r =  {cardinality:1} " +
            "ON MATCH  SET r += {cardinality:(r.cardinality+1)} " +
            "RETURN COUNT(r)=1")
    boolean createOutputRelationship(Long dbIdA, Long dbIdB);

    @Query ("MATCH(a:ReactionLikeEvent {dbId:{0}}),(b:CatalystActivity {dbId:{1}}) " +
            "MERGE (a)<-[r:CATALYSES]-(b) " +
            "RETURN COUNT(r)=1")
    boolean createCatalystRelationship(Long dbIdA, Long dbIdB);

    @Query ("MATCH(a:CandidateSet {dbId:{0}}),(b:PhysicalEntity {dbId:{1}}) " +
            "MERGE (a)-[r:HAS_CANDIDATE]->(b) " +
            "RETURN COUNT(r)=1")
    boolean createCandidateRelationship(Long dbIdA, Long dbIdB);

    @Query ("MATCH (a:Complex {dbId:{0}}),(b:PhysicalEntity{dbId:{1}}) " +
            "MERGE (a)-[r:HAS_COMPONENT]->(b) " +
            "ON CREATE SET r =  {cardinality:1} " +
            "ON MATCH  SET r += {cardinality:(r.cardinality+1)} " +
            "RETURN COUNT(r)=1")
    boolean createComponentRelationship(Long dbIdA, Long dbIdB);


    @Query ("MATCH (a:EntitySet {dbId:{0}}),(b:PhysicalEntity{dbId:{1}}) " +
            "MERGE (a)-[r:HAS_MEMBER]->(b) " +
            "RETURN COUNT(r)=1")
    boolean createMemberRelationship(Long dbIdA, Long dbIdB);

    @Query ("MATCH(a:Event {dbId:{0}}),(b:Event {dbId:{1}}) " +
            "MERGE (a)-[r:HAS_EVENT]->(b) " +
            "RETURN COUNT(r)=1")
    boolean createEventRelationship(Long dbIdA, Long dbIdB);

    @Query ("MATCH(a:Polymer {dbId:{0}}),(b:PhysicalEntity {dbId:{1}}) " +
            "MERGE (a)-[r:HAS_REPEATED_UNIT]->(b) " +
            "ON CREATE SET r =  {cardinality:1} " +
            "ON MATCH  SET r += {cardinality:(r.cardinality+1)} " +
            "RETURN COUNT(r)=1")
    boolean createRepeatedUnitRelationship(Long dbIdA, Long dbIdB);

    @Query ("MATCH(a:PhysicalEntity {dbId:{0}}),(b:ReferenceSequence {dbId:{1}}) " +
            "MERGE (a)-[r:HAS_REFERENCE_ENTITY]->(b) " +
            "RETURN COUNT(r)=1")
    boolean createReferenceEntityRelationship(Long dbIdA, Long dbIdB);

//    will result in an Neo.ClientError.Statement.InvalidSyntax
//    @Query ("MATCH(a {dbId:{0}}),(b {dbId:{1}}) " +
//            "MERGE (a)<-[r:{2}]-(b) " +
//            "RETURN COUNT(r)=1")
//    boolean createIncomingRelationship(Long dbIdA, Long dbIdB, String relationshipType);


    /*
     Methods for creating an Index. This Methods are in this Repository to reduce the total amount of Repositories.
    */

//    @Query ("CREATE CONSTRAINT ON (n:{0}) ASSERT n.dbId is UNIQUE")
//    void createConstraintOnDatabaseObject(String classname);


    @Query ("CREATE CONSTRAINT ON (n:DatabaseObject) ASSERT n.dbId is UNIQUE")
    void createConstraintOnDatabaseObjectDbId();

    @Query ("CREATE CONSTRAINT ON (n:DatabaseObject) ASSERT n.stId is UNIQUE")
    void createConstraintOnDatabaseObjectStId();

}
