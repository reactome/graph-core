package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.result.DiagramResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Repository
public interface DiagramRepository extends GraphRepository<PhysicalEntity> {

    @Query(" MATCH (e:Pathway{dbId:{0}, hasDiagram:True}) " +
            "RETURN e.stId As diagramStId, [] AS events, e.diagramWidth AS width, e.diagramHeight AS height  " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(:Pathway{dbId:{0}, hasDiagram:False})-[:hasEvent*]->(r:ReactionLikeEvent) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "RETURN d.stId as diagramStId, COLLECT(DISTINCT r.stId) AS events, d.diagramWidth AS width, d.diagramHeight AS height " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(r:ReactionLikeEvent{dbId:{0}}) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "RETURN d.stId as diagramStId, [{0}] AS events, d.diagramWidth AS width, d.diagramHeight AS height")
    DiagramResult getDiagramResult(Long dbId);

    @Query(" MATCH (e:Pathway{stId:{0}, hasDiagram:True}) " +
            "RETURN e.stId As diagramStId, [] AS events, e.diagramWidth AS width, e.diagramHeight AS height " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(:Pathway{stId:{0}, hasDiagram:False})-[:hasEvent*]->(r:ReactionLikeEvent) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "RETURN d.stId as diagramStId, COLLECT(DISTINCT r.stId) AS events, d.diagramWidth AS width, d.diagramHeight AS height " +
            "UNION " +
            "MATCH path=(d:Pathway{hasDiagram:True})-[:hasEvent*]->(r:ReactionLikeEvent{stId:{0}}) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway) AND x.hasDiagram) " +
            "RETURN d.stId as diagramStId, [{0}] AS events, d.diagramWidth AS width, d.diagramHeight AS height")
    DiagramResult getDiagramResult(String stId);
}
