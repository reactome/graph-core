package org.reactome.server.graph.util;

import org.neo4j.driver.summary.ResultSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Date;

@SuppressWarnings("unchecked")
@Repository
public class TestNodeRepository {

    private final Neo4jClient neo4jClient;


    @Value("${spring.data.neo4j.database:graph.db}")
    private String database;

    @Autowired
    public TestNodeRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }




    public ResultSummary createNodeByStId(String stId){
        String dbId = stId;
        String intValue = dbId.replaceAll("[^0-9]", "");
        int dbIdInt = Integer.parseInt(intValue);

        String displayName = stId+" TestNode";
        String stIdVersion = stId+".1";
        String species = "Homo sapiens";
        String query = String.format(
                "CREATE (dbObject:DatabaseObject {stId: '%s', displayName: '%s', species: '%s', stIdVersion: '%s', dbId: %d})",
                stId, displayName, species, stIdVersion, dbIdInt
        );
        return neo4jClient.query(query).run();
    }

    public ResultSummary deleteNode(String stId){
        String query = String.format("MATCH (dbObject:DatabaseObject {stId: '%s'}) DETACH DELETE dbObject;", stId);
        return neo4jClient.query(query).run();
    }

    public ResultSummary addLabel(String stId, String label){
        String query = String.format("MATCH (dbObject {stId: '%s'}) SET dbObject:%s RETURN dbObject;", stId, label);
        return neo4jClient.query(query).run();
    }

    public ResultSummary addProperty(String stId, String property, String value){
        String query = String.format("MATCH (dbObject:DatabaseObject {stId: '%s'}) SET dbObject.%s = '%s' RETURN dbObject;", stId, property, value);
        return neo4jClient.query(query).run();
    }

    public ResultSummary addRelation(String stIdNode1, String stIdNode2, String relationName){
        String query = String.format("MATCH (a {stId: '%s'}), (b {stId: '%s'}) CREATE (a)-[:%s]->(b) RETURN a, b;", stIdNode1, stIdNode2, relationName);
        return neo4jClient.query(query).run();
    }


    /** Creates Mock component for later testing
       */
    public ResultSummary createAdvancedLinkageServiceComponent(){
        String queryString = "CREATE (dbObject:DatabaseObject {stId: 'R-HSA-TestNode1', displayName: 'Main Object', schemaClass: 'DatabaseObject'});";
        neo4jClient.query(queryString).run();

        queryString = "CREATE (dbObject:DatabaseObject {stId:'R-HSA-TestNode2', speciesName:\"Homo sapiens\", schemaClass:\"Reaction\", displayName:\"TestNode2\"});";
        neo4jClient.query(queryString).run();

        queryString =        "MATCH (n1:DatabaseObject {stId: \"R-HSA-TestNode1\"}), (n2:DatabaseObject  {stId: \"R-HSA-TestNode2\"}) CREATE (n2)-[:hasEvent]->(n1) RETURN n1,n2";
        neo4jClient.query(queryString).run();

        queryString =        "CREATE (d:DatabaseObject {stId:\"R-HSA-TestNode3\", speciesName:\"Homo sapiens\", schemaClass:\"Complex\", displayName:\"TestNode3\"})";
        neo4jClient.query(queryString).run();

        queryString =          "MATCH (n1:DatabaseObject {stId: \"R-HSA-TestNode1\"}), (n2:DatabaseObject  {stId: \"R-HSA-TestNode3\"}) CREATE (n2)-[:hasEvent]->(n1) RETURN n1,n2";
        return neo4jClient.query(queryString).run();
    }

    public ResultSummary createAdvancedLinkageServiceReferrals(){
        String queryString = "CREATE (dbObject:DatabaseObject {stId: 'R-HSA-123456', dbId: 123456, displayName: 'Main Object', schemaClass: 'DatabaseObject'});";
        neo4jClient.query(queryString).run();

        queryString = "CREATE (n:DatabaseObject:Event:Pathway:TopLevelPathway { schemaClass: \"TopLevelPathway\", displayName: \"TestNode2\", stId: \"R-HSA-78910\", dbId: 78910, speciesName: \"Homo sapiens\", stIdVersion: \"R-HSA-78910.1\"}) RETURN n;";
        neo4jClient.query(queryString).run();

        queryString ="MATCH (n1:DatabaseObject {stId: \"R-HSA-123456\"}), (n2:DatabaseObject  {stId: \"R-HSA-78910\"}) CREATE (n2)-[:hasEvent]->(n1) RETURN n1,n2";
        return neo4jClient.query(queryString).run();
    }

    public ResultSummary deleteAdvancedLinkageServiceData(String testIdentifier1, String testIdentifier2) {
        String deleteQuery = "MATCH (n) WHERE n.stId IN [\"R-HSA-TestNode1\",\"R-HSA-TestNode2\",\"R-HSA-TestNode3\"] DETACH DELETE n";
        return neo4jClient.query(deleteQuery).run();
    }

    public ResultSummary deleteAdvancedLinkageServiceDataReferrals() {
        String deleteQuery = "MATCH (n) WHERE n.dbId IN [123456,78910] DETACH DELETE n";
        return neo4jClient.query(deleteQuery).run();
    }

    public void createAdvancedServicePhysicalEntity() {
        String queryString = "CREATE (n:DatabaseObject {stId: 'R-HSA-1234567', dbId: 1234567, displayName: 'MainTestNode',oldStId: 'REACT_12345', speciesName: 'Homo sapiens'}) RETURN n;";

        neo4jClient.query(queryString).run();

         queryString =
                "CREATE (n:DatabaseObject {" +
                        "stId: 'R-HSA-678910', " +
                        "dbId: 678910, " +
                        "displayName: 'TestNode1', " +
                        "oldStId: 'REACT_67891', " +
                        "speciesName: 'Homo sapiens'" +
                        "}) " +
                        "RETURN n;";

        neo4jClient.query(queryString).run();

        queryString =        "MATCH (n1:DatabaseObject {stId: \"R-HSA-12345\"}), (n2:DatabaseObject  {stId: \"R-HSA-67891\"}) CREATE (n2)-[:species]->(n1) RETURN n1,n2";
        neo4jClient.query(queryString).run();

    }



}