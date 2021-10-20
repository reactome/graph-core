package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.TocPathwayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class TocRepository {

    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public TocRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public Collection<TocPathwayDTO> getTocPathways() {
        String query =
                "MATCH (p:TopLevelPathway{isInferred:false}) " +
                "MATCH (p)-[:hasEvent]->(subs:Pathway) " +
                "WITH  COLLECT(DISTINCT subs) AS subPathways, p " +
                "MATCH (p)-[:hasEvent*]->(otherp:Event) " +
                "OPTIONAL MATCH (p)<-[:revised]-(re:InstanceEdit) " +
                "OPTIONAL MATCH (otherp)<-[:authored | :revised]-(:InstanceEdit)<-[:author]-(atrs:Person) " +
                "OPTIONAL MATCH (otherp)<-[:reviewed]-(:InstanceEdit)<-[:author]-(rvwd:Person) " +
                "OPTIONAL MATCH (otherp)<-[:edited]-(:InstanceEdit)<-[:author]-(edtd:Person) " +
                "OPTIONAL MATCH (p)<-[:authored | :revised]-(:InstanceEdit)<-[:author]-(tatrs:Person) " +
                "OPTIONAL MATCH (p)<-[:reviewed]-(:InstanceEdit)<-[:author]-(trvwd:Person) " +
                "OPTIONAL MATCH (p)<-[:edited]-(:InstanceEdit)<-[:author]-(tedtd:Person) " +
                "WITH COLLECT(DISTINCT tatrs) + COLLECT(DISTINCT atrs) AS allAuthors,  " +
                "COLLECT(DISTINCT trvwd) + COLLECT(DISTINCT rvwd) AS allReviewers,  " +
                "COLLECT(DISTINCT tedtd) + COLLECT(DISTINCT edtd) AS allEditors,  " +
                "p, re, subPathways  " +
                "UNWIND allAuthors AS totalAtrs " +
                "UNWIND allReviewers AS totalRvwd " +
                "UNWIND allEditors AS totalEdtd " +
                "RETURN p.stId AS stId, p.displayName AS displayName, p.doi AS doi, p.speciesName AS species, " +
                "p.releaseDate AS releaseDate, " +
                "MAX(re.dateTime) AS reviseDate, " +
                "COLLECT(DISTINCT totalRvwd) AS reviewers, " +
                "COLLECT(DISTINCT totalEdtd) AS editors, " +
                "COLLECT(DISTINCT totalAtrs) AS authors, " +
                "p.releaseStatus AS releaseStatus, " +
                "subPathways " +
                "ORDER BY toLower(p.displayName)";

        return neo4jClient.query(query).in(databaseName).fetchAs(TocPathwayDTO.class).mappedBy((typeSystem, record) -> new TocPathwayDTO(record)).all();
    }
}
