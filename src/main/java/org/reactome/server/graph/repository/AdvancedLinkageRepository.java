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

    @Query("Match (n:DatabaseObject{stId:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) Return DISTINCT(type(r)) AS type, Collect(m.simpleLabel) AS schemaClasses, Collect(m.displayName) AS names, Collect(m.stId) AS stIds")
    Collection<ComponentOf> getComponentsOf(String stId);

    @Query("Match (n:DatabaseObject{dbId:{0}})<-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) Return DISTINCT(type(r)) AS type, Collect(m.simpleLabel) AS schemaClasses, Collect(m.displayName) AS names, Collect(m.stId) AS stIds")
    Collection<ComponentOf> getComponentsOf(Long dbId);

    @Query("MATCH (:DatabaseObject{stId:{0}})<-[rel]-(ref) WHERE NOT ref:InstanceEdit RETURN DISTINCT TYPE(rel) AS referral, COLLECT(ref) AS objects LIMIT 1000")
    Collection<Referrals> getReferralsTo(String stId);

    @Query("MATCH (:DatabaseObject{dbId:{0}})<-[rel]-(ref) WHERE NOT ref:InstanceEdit RETURN DISTINCT TYPE(rel) AS referral, COLLECT(ref) AS objects LIMIT 1000")
    Collection<Referrals> getReferralsTo(Long dbId);
}
