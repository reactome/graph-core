package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.ComponentOf;
import org.reactome.server.graph.domain.result.Referrals;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@Deprecated
/**
 * AdvancedLinkageRepository is deprecated and won't work
 * @see ComponentOfLinkageRepository
 * @see ReferralsLinkageRepository
 */
public interface AdvancedLinkageRepository extends Neo4jRepository<DatabaseObject, Long> {

    @Query(" MATCH (n:DatabaseObject{stId:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) " +
            "RETURN DISTINCT(TYPE(r)) AS type, " +
            "       COLLECT(COALESCE(m.schemaClass, '')) AS schemaClasses, " +
            "       COLLECT(COALESCE(m.displayName, '')) AS names, " +
            "       COLLECT(COALESCE(m.stId, '')) AS stIds, " +
            "       COLLECT(COALESCE(m.speciesName, '')) as species")
    @Deprecated
    Collection<ComponentOf> getComponentsOf(String stId);

    @Query(" MATCH (n:DatabaseObject{dbId:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) " +
            "RETURN DISTINCT(TYPE(r)) AS type, " +
            "       COLLECT(COALESCE(m.schemaClass, '')) AS schemaClasses, " +
            "       COLLECT(COALESCE(m.displayName, '')) AS names, " +
            "       COLLECT(COALESCE(m.stId, '')) AS stIds, " +
            "       COLLECT(COALESCE(m.speciesName, '')) as species")
    @Deprecated
    Collection<ComponentOf> getComponentsOf(Long dbId);

    @Query(" MATCH (d:DatabaseObject{stId:{0}})<-[rel]-(ref) " +
            "WHERE NOT ref:InstanceEdit " +
            "      AND NOT (d)<-[:species|compartment|includedLocation|referenceDatabase|evidenceType]-(ref) " +
            "RETURN DISTINCT TYPE(rel) AS referral, " +
            "       COLLECT(ref) AS objects " +
            "LIMIT 1000")
    @Deprecated
    Collection<Referrals> getReferralsTo(String stId);

    @Query(" MATCH (d:DatabaseObject{dbId:{0}})<-[rel]-(ref) " +
            "WHERE NOT ref:InstanceEdit " +
            "      AND NOT (d)<-[:species|compartment|includedLocation|referenceDatabase|evidenceType]-(ref) " +
            "RETURN DISTINCT TYPE(rel) AS referral, " +
            "       COLLECT(ref) AS objects " +
            "LIMIT 1000")
    @Deprecated
    Collection<Referrals> getReferralsTo(Long dbId);
}
