package org.reactome.server.graph.repository;

import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.04.16.
 */
@Repository
public class DetailsRepository {

    private Neo4jTemplate neo4jTemplate;

    @Autowired
    public DetailsRepository(Neo4jTemplate neo4jTemplate) {
        this.neo4jTemplate = neo4jTemplate;
    }

    //    public DatabaseObject detailsPageQuery(String stId) {
//        String query = "MATCH (n:DatabaseObject{stId:{stId}})-[r]->(m)" +
//                "OPTIONAL MATCH (n)<-[e:inferredTo|regulator|regulatedBy]-(l)" +
//                "OPTIONAL MATCH (m:ReferenceEntity)-[t:crossReference|referenceGene|referenceTranscript]->(z)" +
//                "OPTIONAL MATCH (m:AbstractModifiedResidue)-[u:psiMod|modification]-(i)" +
//                "OPTIONAL MATCH (m:CatalystActivity)-[o:catalystActivity|physicalEntity|activity]-(p)" +
//                "OPTIONAL MATCH (m:EntityFunctionalStatus)-[q:diseaseEntity|normalEntity|functionalStatus]-(s)-[a:functionalStatusType|structuralVariant]-(b)" +
//                "RETURN n,r,m,l,e,t,z,u,i,o,p,q,s,a,b";
//        Map<String, Object> map = new HashMap<>();
//        map.put("stId", stId);
//        Result result = neo4jTemplate.query(query, map);
//        if (result != null && result.iterator().hasNext()) {
//            DatabaseObject n = (DatabaseObject) result.iterator().next().get("n");
//            return n;
//        }
//        return null;
//    }
//
//    public DatabaseObject detailsPageQuery(Long dbId) {
//        String query = "MATCH (n:DatabaseObject{dbId:{dbId}})-[r]->(m)" +
//                "OPTIONAL MATCH (n)<-[e:inferredTo|regulator|regulatedBy]-(l)" +
//                "OPTIONAL MATCH (m:ReferenceEntity)-[t:crossReference|referenceGene|referenceTranscript]->(z)" +
//                "OPTIONAL MATCH (m:AbstractModifiedResidue)-[u:psiMod|modification]-(i)" +
//                "OPTIONAL MATCH (m:CatalystActivity)-[o:catalystActivity|physicalEntity|activity]-(p)" +
//                "OPTIONAL MATCH (m:EntityFunctionalStatus)-[q:diseaseEntity|normalEntity|functionalStatus]-(s)-[a:functionalStatusType|structuralVariant]-(b)" +
//                "RETURN n,r,m,l,e,t,z,u,i,o,p,q,s,a,b";
//        Map<String, Object> map = new HashMap<>();
//        map.put("dbId", dbId);
//        Result result = neo4jTemplate.query(query, map);
//        if (result != null && result.iterator().hasNext())
//            return (DatabaseObject) result.iterator().next().get("n");
//        return null;
//    }

    public DatabaseObject detailsPageQuery(Long dbId) {
        String query = "" +
                "MATCH (n:DatabaseObject{dbId:$dbId})-[r]->(m)" +
                "OPTIONAL MATCH (n)<-[e:inferredTo|regulator|regulatedBy]-(l)" +
                "OPTIONAL MATCH (m:ReferenceEntity)-[t:crossReference|referenceGene|referenceTranscript]->(z)" +
                "OPTIONAL MATCH (m:AbstractModifiedResidue)-[u:psiMod|modification]-(i)" +
                "OPTIONAL MATCH (m:CatalystActivity)-[o:catalystActivity|physicalEntity|activity]-(p)" +
                "OPTIONAL MATCH (m:EntityFunctionalStatus)-[q:diseaseEntity|normalEntity|functionalStatus]-(s)-[a:functionalStatusType|structuralVariant]-(b)" +
                "RETURN n,collect(r),collect(m),collect(l),collect(e),collect(t),collect(z),collect(u),collect(i),collect(o),collect(p),collect(q),collect(s),collect(a),collect(b)";

        Map<String, Object> map = Map.of("dbId", dbId);
        return neo4jTemplate.findOne(query, map, DatabaseObject.class).orElse(null);
    }

}

