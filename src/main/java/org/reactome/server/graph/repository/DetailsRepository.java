package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DetailsRepository {

    private Neo4jTemplate neo4jTemplate;

    @Autowired
    public DetailsRepository(Neo4jTemplate neo4jTemplate) {
        this.neo4jTemplate = neo4jTemplate;
    }

    public DatabaseObject detailsPageQuery(String stId) {
        //language=Cypher
        String query = "" +
                "MATCH (n:DatabaseObject{stId:$stId})-[r]->(m)" +
                "OPTIONAL MATCH (n)<-[e:inferredTo|regulator|regulatedBy]-(l)" +
                "OPTIONAL MATCH (m:ReferenceEntity)-[t:crossReference|referenceGene|referenceTranscript]->(z)" +
                "OPTIONAL MATCH (m:AbstractModifiedResidue)-[u:psiMod|modification]-(i)" +
                "OPTIONAL MATCH (m:CatalystActivity)-[o:catalystActivity|physicalEntity|activity]-(p)" +
                "OPTIONAL MATCH (m:EntityFunctionalStatus)-[q:diseaseEntity|normalEntity|functionalStatus]-(s)-[a:functionalStatusType|structuralVariant]-(b)" +
                "RETURN n, COLLECT(r), COLLECT(m),COLLECT(l),COLLECT(e),COLLECT(t),COLLECT(z),COLLECT(u),COLLECT(i),COLLECT(o),COLLECT(p),COLLECT(q),COLLECT(s),COLLECT(a),COLLECT(b)";

        return neo4jTemplate.findOne(query, Map.of("stId", stId), DatabaseObject.class).orElse(null);
    }

    public DatabaseObject detailsPageQuery(Long dbId) {
        //language=Cypher
        String query = "" +
                "MATCH (n:DatabaseObject{dbId:$dbId})-[r]->(m)" +
                "OPTIONAL MATCH (n)<-[e:inferredTo|regulator|regulatedBy]-(l)" +
                "OPTIONAL MATCH (m:ReferenceEntity)-[t:crossReference|referenceGene|referenceTranscript]->(z)" +
                "OPTIONAL MATCH (m:AbstractModifiedResidue)-[u:psiMod|modification]-(i)" +
                "OPTIONAL MATCH (m:CatalystActivity)-[o:catalystActivity|physicalEntity|activity]-(p)" +
                "OPTIONAL MATCH (m:EntityFunctionalStatus)-[q:diseaseEntity|normalEntity|functionalStatus]-(s)-[a:functionalStatusType|structuralVariant]-(b)" +
                "RETURN n, COLLECT(r), COLLECT(m),COLLECT(l),COLLECT(e),COLLECT(t),COLLECT(z),COLLECT(u),COLLECT(i),COLLECT(o),COLLECT(p),COLLECT(q),COLLECT(s),COLLECT(a),COLLECT(b)";

        return neo4jTemplate.findOne(query, Map.of("dbId", dbId), DatabaseObject.class).orElse(null);
    }
}

