package org.reactome.server.graph.util;


import org.neo4j.driver.summary.ResultSummary;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.Person;
import org.reactome.server.graph.domain.relationship.Has;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TestNodeService {

    private final Neo4jTemplate neo4jTemplate;
    private final Neo4jClient neo4jClient;

    private long idGenerator = 0;

    @Autowired
    public TestNodeService(Neo4jTemplate neo4jTemplate, Neo4jClient neo4jClient) {
        this.neo4jTemplate = neo4jTemplate;
        this.neo4jClient = neo4jClient;
    }

    public <T extends DatabaseObject> T saveTest(T toBeSaved) {
        return this.neo4jTemplate.save(toBeSaved);
    }

    public ResultSummary createRelationship(String stId, String connectedNodeId, String relationshipType, Integer order, Integer stoichiometry) {
        String query = "MATCH (a:DatabaseObject), (b:DatabaseObject)  \n" +
                "WHERE a.stId = $stId AND b.stId = $connectedNodeId  \n" +
                "CREATE (a)-[r:" + relationshipType + " {order:"+order+", stoichiometry:"+stoichiometry+"}]->(b)  \n" +
                "RETURN a, b, r;";

        return neo4jClient.query(query)
                .bind(stId).to("stId")
                .bind(connectedNodeId).to("connectedNodeId")
                .run();
    }

    public ResultSummary deleteTest(){
        String query = "MATCH (dbObject) WHERE dbObject.dbId < 0  DETACH DELETE dbObject;";
        return neo4jClient.query(query).run();
    }


}
