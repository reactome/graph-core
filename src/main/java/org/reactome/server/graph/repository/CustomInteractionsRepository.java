package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.result.CustomInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

@Repository
public class CustomInteractionsRepository {

    private final Neo4jClient neo4jClient;

    @Value("${spring.data.neo4j.database:graph.db}")
    private String databaseName;

    @Autowired
    public CustomInteractionsRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public Collection<CustomInteraction> getCustomInteractionsByAcc(String identifier) {
        //language=cypher
        String query =
                "MATCH (t:ReferenceEntity)<-[:interactor]-(in:Interaction)-[ir:interactor]->(re:ReferenceEntity) " +
                        "WHERE t.variantIdentifier = $identifier OR (t.variantIdentifier IS NULL AND t.identifier = $identifier) " +
                        "OPTIONAL MATCH (pe:PhysicalEntity)-[r:referenceEntity]->(re) " +
                        "OPTIONAL MATCH (se:Species)<-[s:species]-(re) " +
                        "WITH in, re, se, pe, t " +
                        "ORDER BY in.score DESC " +
                        "RETURN DISTINCT in.dbId AS dbId, in.score AS score, SIZE(in.accession) AS evidenceCount, re.identifier AS identifier, re.databaseName AS databaseName, re.displayName AS displayName, re.variantIdentifier as variantIdentifier, re.geneName AS geneName, re.url as url, in.url AS evidenceURL,  se.displayName AS speciesName, COUNT(pe) AS entitiesCount";

        return neo4jClient.query(query).in(databaseName).bindAll(Collections.singletonMap("identifier", identifier)).fetchAs(CustomInteraction.class).mappedBy((typeSystem, record) -> new CustomInteraction(record)).all();
    }

}
