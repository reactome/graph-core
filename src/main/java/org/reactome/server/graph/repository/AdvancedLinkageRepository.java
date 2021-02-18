package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.ComponentOf;
import org.reactome.server.graph.domain.result.Referrals;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@Repository
public interface AdvancedLinkageRepository extends GraphRepository<DatabaseObject> {

    @Query(" MATCH (n:DatabaseObject{stId:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) " +
            "RETURN DISTINCT(TYPE(r)) AS type, " +
            "       COLLECT(m.schemaClass) AS schemaClasses, " +
            "       COLLECT(m.displayName) AS names, " +
            "       COLLECT(m.stId) AS stIds," +
            "       COLLECT(m.speciesName) as species")
    Collection<ComponentOf> getComponentsOf(String stId);

    @Query(" MATCH (n:DatabaseObject{dbId:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) " +
            "RETURN DISTINCT(TYPE(r)) AS type, " +
            "       COLLECT(m.schemaClass) AS schemaClasses, " +
            "       COLLECT(m.displayName) AS names, " +
            "       COLLECT(m.stId) AS stIds," +
            "       COLLECT(m.speciesName) as species")
    Collection<ComponentOf> getComponentsOf(Long dbId);

    @Query(" MATCH (d:DatabaseObject{stId:{0}})<-[rel]-(ref) " +
            "WHERE NOT ref:InstanceEdit " +
            "      AND NOT (d)<-[:species|compartment|includedLocation|referenceDatabase|evidenceType]-(ref) " +
            "RETURN DISTINCT TYPE(rel) AS referral, " +
            "       COLLECT(ref) AS objects " +
            "LIMIT 1000")
    Collection<Referrals> getReferralsTo(String stId);

    @Query(" MATCH (d:DatabaseObject{dbId:{0}})<-[rel]-(ref) " +
            "WHERE NOT ref:InstanceEdit " +
            "      AND NOT (d)<-[:species|compartment|includedLocation|referenceDatabase|evidenceType]-(ref) " +
            "RETURN DISTINCT TYPE(rel) AS referral, " +
            "       COLLECT(ref) AS objects " +
            "LIMIT 1000")
    Collection<Referrals> getReferralsTo(Long dbId);
}
