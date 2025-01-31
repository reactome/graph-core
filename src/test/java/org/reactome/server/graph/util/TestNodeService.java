package org.reactome.server.graph.util;


import org.neo4j.driver.summary.ResultSummary;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.EntityWithAccessionedSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestNodeService {
//
//    @Autowired
//    private TestNodeRepository testNodeRepository;


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

    public ResultSummary deleteTest(){
        String query = "MATCH (dbObject) WHERE dbObject.dbId < 0  DETACH DELETE dbObject;";
        return neo4jClient.query(query).run();
    }


}
