package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.Pathway;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Repository
public interface EventsRepository extends GraphRepository<DatabaseObject> {

    @Query("MATCH ancestors=(n:Event{stId:{0}})<-[r:hasEvent*]-(:TopLevelPathway) RETURN Nodes(ancestors)")
    Collection<Collection<Pathway>> getEventAncestorsByStId(String stId);

    @Query("MATCH ancestors=(n:Event{dbId:{0}})<-[r:hasEvent*]-(:TopLevelPathway) RETURN Nodes(ancestors)")
    Collection<Collection<Pathway>> getEventAncestorsByDbId(Long dbId);

}
