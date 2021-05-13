package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.Pathway;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PathwayRepository extends Neo4jRepository<Pathway, Long> {

    @Query(" MATCH (:DatabaseObject{stId:$stId})<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-()<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p " +
            "UNION " +
            "MATCH (:ReactionLikeEvent{stId:$stId})<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p " +
            "UNION " +
            "MATCH (p:Pathway{stId:$stId}) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getPathwaysForByStId(@Param("stId") String stId);

    @Query(" MATCH (:DatabaseObject{stId:$stId})<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-()<-[:hasEvent]-(p:Pathway)-[:species]->(:Species{taxId:$taxId}) " +
            "RETURN DISTINCT p " +
            "UNION " +
            "MATCH (:ReactionLikeEvent{stId:$stId})<-[:hasEvent]-(p:Pathway)-[:species]->(:Species{taxId:$taxId}) " +
            "RETURN DISTINCT p " +
            "UNION " +
            "MATCH (p:Pathway{stId:$stId})-[:species]->(:Species{taxId:$taxId}) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getPathwaysForByStIdAndSpeciesTaxId(@Param("stId") String stId, @Param("taxId") String taxId);


    @Query(" MATCH (:DatabaseObject{dbId:$dbId})<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-()<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p " +
            "UNION " +
            "MATCH (:ReactionLikeEvent{dbId:$dbId})<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p " +
            "UNION " +
            "MATCH (p:Pathway{dbId:$dbId}) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getPathwaysForByDbId(@Param("dbId") Long dbId);

    @Query(" MATCH (:DatabaseObject{dbId:$dbId})<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-()<-[:hasEvent]-(p:Pathway)-[:species]->(:Species{taxId:$taxId}) " +
            "RETURN DISTINCT p " +
            "UNION " +
            "MATCH (:ReactionLikeEvent{dbId:$dbId})<-[:hasEvent]-(p:Pathway)-[:species]->(:Species{taxId:$taxId}) " +
            "RETURN DISTINCT p " +
            "UNION " +
            "MATCH (p:Pathway{dbId:$dbId})-[:species]->(:Species{taxId:$taxId}) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getPathwaysForByDbIdAndSpeciesTaxId(@Param("dbId") Long dbId, @Param("taxId") String taxId);


    @Query(" MATCH (:DatabaseObject{stId:$stId})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getPathwaysForAllFormsOfByStId(@Param("stId") String stId);

    @Query(" MATCH (:DatabaseObject{stId:$stId})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(:Species{taxId:$taxId}) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getPathwaysForAllFormsOfByStIdAndSpeciesTaxId(@Param("stId") String stId, @Param("taxId") String taxId);

    @Query(" MATCH (:DatabaseObject{dbId:$dbId})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getPathwaysForAllFormsOfByDbId(@Param("dbId") Long dbId);

    @Query(" MATCH (:DatabaseObject{dbId:$dbId})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(:Species{taxId:$taxId}) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getPathwaysForAllFormsOfByDbIdAndSpeciesTaxId(@Param("dbId") Long dbId, @Param("taxId") String taxId);


    @Query(" MATCH (:PhysicalEntity{stId:$stId})<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m " +
            "UNION " +
            "MATCH (r:ReactionLikeEvent{stId:$stId})<-[:hasEvent]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m " +
            "UNION " +
            "MATCH (o:Pathway{stId:$stId})-[:hasEvent*]->(p:Pathway{hasDiagram:True}) " +
            "WITH o, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m")
    Collection<Pathway> getPathwaysWithDiagramForByStId(@Param("stId") String stId);

    @Query(" MATCH (:PhysicalEntity{stId:$stId})<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{taxId:$taxId}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m " +
            "UNION " +
            "MATCH (r:ReactionLikeEvent{stId:$stId})<-[:hasEvent]-(p:Pathway{hasDiagram:True})-[:species]->(s:Species{taxId:$taxId}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m " +
            "UNION " +
            "MATCH (o:Pathway{stId:$stId})-[:hasEvent*]->(p:Pathway{hasDiagram:True})-[:species]->(s:Species{taxId:$taxId}) " +
            "WITH o, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m")
    Collection<Pathway> getPathwaysWithDiagramForByStIdAndSpeciesTaxId(@Param("stId") String stId, @Param("taxId") String taxId);


    @Query(" MATCH (:PhysicalEntity{dbId:$dbId})<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m " +
            "UNION " +
            "MATCH (r:ReactionLikeEvent{dbId:$dbId})<-[:hasEvent]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m " +
            "UNION " +
            "MATCH (o:Pathway{dbId:$dbId})-[:hasEvent*]->(p:Pathway{hasDiagram:True}) " +
            "WITH o, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m")
    Collection<Pathway> getPathwaysWithDiagramForByDbId(@Param("dbId") Long dbId);

    @Query(" MATCH (:PhysicalEntity{dbId:$dbId})<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{taxId:$taxId}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m " +
            "UNION " +
            "MATCH (r:ReactionLikeEvent{dbId:$dbId})<-[:hasEvent]-(p:Pathway{hasDiagram:True})-[:species]->(s:Species{taxId:$taxId}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m " +
            "UNION " +
            "MATCH (o:Pathway{dbId:$dbId})-[:hasEvent*]->(p:Pathway{hasDiagram:True})-[:species]->(s:Species{taxId:$taxId}) " +
            "WITH o, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m")
    Collection<Pathway> getPathwaysWithDiagramForByDbIdAndSpeciesTaxId(@Param("dbId") Long dbId, @Param("taxId") String taxId);


    @Query(" MATCH (:PhysicalEntity{stId:$stId})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m")
    Collection<Pathway> getPathwaysWithDiagramForAllFormsOfByStId(@Param("stId") String stId);

    @Query(" MATCH (:PhysicalEntity{stId:$stId})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{taxId:$taxId}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m")
    Collection<Pathway> getPathwaysWithDiagramForAllFormsOfByStIdAndSpeciesTaxId(@Param("stId") String stId, @Param("taxId") String taxId);


    @Query(" MATCH (:PhysicalEntity{dbId:$dbId})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m")
    Collection<Pathway> getPathwaysWithDiagramForAllFormsOfByDbId(@Param("dbId") Long dbId);

    @Query(" MATCH (:PhysicalEntity{dbId:$dbId})-[:regulator|hasComponent|repeatedUnit|hasMember|hasCandidate|referenceEntity*]->(re:ReferenceEntity) " +
            "WITH re " +
            "MATCH (re)<-[:referenceEntity]-(:PhysicalEntity)<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(r:ReactionLikeEvent)<-[:hasEvent*]-(p:Pathway{hasDiagram:True})-[:species]->(Species{taxId:$taxId}) " +
            "WITH r, HEAD(COLLECT(p)) AS m " +
            "RETURN DISTINCT m")
    Collection<Pathway> getPathwaysWithDiagramForAllFormsOfByDbIdAndSpeciesTaxId(@Param("dbId") Long dbId, @Param("taxId") String taxId);


    @Query(" MATCH (rd:ReferenceDatabase)<--(n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene*]-(pe:PhysicalEntity) " +
            "WHERE n.identifier = $identifier OR $identifier IN n.name OR $identifier IN n.geneName " +
            "WITH DISTINCT pe " +
            "MATCH (pe)<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p " +
            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
            "MATCH (pe:PhysicalEntity{stId:$stId})<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getLowerLevelPathwaysForIdentifier(@Param("identifier") String identifier);

    @Query(" MATCH (rd:ReferenceDatabase)<--(n)<-[:referenceEntity|referenceSequence|crossReference|referenceGene*]-(pe:PhysicalEntity) " +
            "WHERE n.identifier = $identifier OR $identifier IN n.name OR $identifier IN n.geneName " +
            "WITH DISTINCT pe " +
            "MATCH (pe)<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(s:Species{taxId:$taxId}) " +
            "RETURN DISTINCT p " +
            "UNION " + //The second part is for the cases when identifier is STABLE_IDENTIFIER
            "MATCH (pe:PhysicalEntity{stId:$identifier})<-[:regulatedBy|regulator|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasMember|hasCandidate|hasComponent|repeatedUnit|input|output*]-(:ReactionLikeEvent)<-[:hasEvent]-(p:Pathway)-[:species]->(s:Species{taxId:$taxId}) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getLowerLevelPathwaysForIdentifierAndSpeciesTaxId(@Param("identifier") String identifier, @Param("taxId") String taxId);

    @Query("OPTIONAL MATCH path=(p1:Pathway)-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(:DatabaseObject{dbId:$dbId}) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway))  " +
            "OPTIONAL MATCH (p2:Pathway{dbId:$dbId})  " +
            "WITH COLLECT(DISTINCT p1) + COLLECT(DISTINCT p2) AS ps " +
            "UNWIND ps AS p " +
            "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent]->(p) " +
            "WITH ps + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all AS p " +
            "RETURN DISTINCT p")
    Collection<Pathway> getLowerLevelPathwaysIncludingEncapsulation(@Param("dbId") Long dbId);

    @Query("OPTIONAL MATCH path=(p1:Pathway)-[:hasEvent|input|output|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|regulatedBy|regulator|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(:DatabaseObject{stId:$stId}) " +
            "WHERE SINGLE(x IN NODES(path) WHERE (x:Pathway))  " +
            "OPTIONAL MATCH (p2:Pathway{stId:$stId})  " +
            "WITH COLLECT(DISTINCT p1) + COLLECT(DISTINCT p2) AS ps " +
            "UNWIND ps AS p " +
            "OPTIONAL MATCH (cep:Pathway)-[:hasEncapsulatedEvent]->(p) " +
            "WITH ps + COLLECT(DISTINCT cep) AS all " +
            "UNWIND all AS p " +
            "RETURN DISTINCT p")
    Collection<Pathway> getLowerLevelPathwaysIncludingEncapsulation(@Param("stId") String stId);

    /* ------------------------------------------------------------------*/
    /* ------------------------- INTERACTORS ----------------------------*/
    /* ------------------------------------------------------------------*/

    @Query(" MATCH (a:ReferenceEntity)<-[:interactor]-()-[:interactor]->(b:ReferenceEntity) " +
            "WHERE a.identifier = $acc OR a.variantIdentifier = $acc " +
            "MATCH path=(p:Pathway)-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(pe:PhysicalEntity)-[:referenceEntity]->(b) " +
            "WHERE p.speciesName = $speciesName AND SINGLE(e IN NODES(path) WHERE (e:Pathway)) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getLowerLevelPathways(@Param("acc") String acc, @Param("speciesName") String speciesName);

    @Query(" MATCH (a:ReferenceEntity)<-[:interactor]-()-[:interactor]->(b:ReferenceEntity) " +
            "WHERE a.identifier = $acc OR a.variantIdentifier = $acc " +
            "MATCH path=(p:Pathway{hasDiagram:True})-[:hasEvent|input|output|catalystActivity|physicalEntity|regulatedBy|regulator*]->(pe:PhysicalEntity)-[:referenceEntity]->(b) " +
            "WHERE p.speciesName = $speciesName AND SINGLE(e IN NODES(path) WHERE (e:Pathway) AND e.hasDiagram) " +
            "RETURN DISTINCT p")
    Collection<Pathway> getDiagrammedLowerLevelPathways(@Param("acc") String acc, @Param("speciesName") String speciesName);

}
