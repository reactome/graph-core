package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.DoiPathwayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class DoiRepository {

    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public DoiRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public Collection<DoiPathwayDTO> doiPathwayDTO() {
        String query =
                "MATCH (p:Pathway) " +
                        "WHERE p.doi IS NOT NULL " +
                        "OPTIONAL MATCH (p)<-[:revised]-(re:InstanceEdit) " +
                        "OPTIONAL MATCH (p)<-[:authored | revised]-(:InstanceEdit)<-[:author]-(patrs:Person) " +
                        "OPTIONAL MATCH (p)<-[:reviewed]-(:InstanceEdit)<-[:author]-(prvwd:Person) " +
                        "OPTIONAL MATCH (p)<-[:edited]-(:InstanceEdit)<-[:author]-(pedtd:Person) " +
                        "OPTIONAL MATCH (p)-[:hasEvent*]->(e:Event) " +
                        "OPTIONAL MATCH (e)<-[:authored]-(:InstanceEdit)<-[:author]-(rlea:Person) " +
                        "OPTIONAL MATCH (e)<-[:reviewed]-(:InstanceEdit)<-[:author]-(rler:Person) " +
                        "OPTIONAL MATCH (e)<-[:edited]-(:InstanceEdit)<-[:author]-(rlee:Person) " +
                        "WITH COLLECT(DISTINCT patrs) + COLLECT(distinct rlea) AS allAuthors, " +
                        "COLLECT(DISTINCT prvwd) + COLLECT(distinct rler) AS allReviewers, " +
                        "COLLECT(DISTINCT pedtd) + COLLECT(distinct rlee) AS allEditors, p, re " +
                        "UNWIND (CASE allAuthors WHEN [] THEN [null] ELSE allAuthors END) AS totalAtrs " +
                        "UNWIND (CASE allReviewers WHEN [] THEN [null] ELSE allReviewers END) AS totalRvwd " +
                        "UNWIND (CASE allEditors WHEN [] THEN [null] ELSE allEditors END) AS totalEdtd " +
                        "RETURN p.displayName AS displayName, " +
                        "       p.doi AS doi, " +
                        "       p.stId AS stId, " +
                        "       p.speciesName AS species, " +
                        "       p.releaseDate AS releaseDate, " +
                        "       p.releaseStatus AS releaseStatus, " +
                        "       MAX(re.dateTime) AS reviseDate, " +
                        "       COLLECT(DISTINCT totalAtrs) AS authors, " +
                        "       COLLECT(DISTINCT totalRvwd) AS reviewers, " +
                        "       COLLECT(DISTINCT totalEdtd) AS editors " +
                        "ORDER BY toLower(p.displayName) ";

        return neo4jClient.query(query).in(databaseName).fetchAs(DoiPathwayDTO.class).mappedBy((typeSystem, record) -> new DoiPathwayDTO(record)).all();
    }

}
