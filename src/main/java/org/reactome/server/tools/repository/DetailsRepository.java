package org.reactome.server.tools.repository;

import org.neo4j.ogm.model.Result;
import org.reactome.server.tools.domain.model.DatabaseObject;
import org.reactome.server.tools.domain.model.Event;
import org.reactome.server.tools.domain.model.PhysicalEntity;
import org.reactome.server.tools.service.helper.PBNode;
import org.reactome.server.tools.service.helper.RelationshipDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.04.16.
 */
@Repository
public class DetailsRepository {

    @Autowired
    private Neo4jOperations neo4jTemplate;


    public DatabaseObject findByDbId(Long dbId, RelationshipDirection direction) {
        String query;
        if (direction.equals(RelationshipDirection.INCOMING)) {
            query = "Match (n:DatabaseObject{dbId:{dbId}})<-[r]-(m) RETURN n,r,m";
        } else {
            query = "Match (n:DatabaseObject{dbId:{dbId}})-[r]->(m) RETURN n,r,m";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("dbId", dbId);
        Result result =  neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }


    public DatabaseObject findByStableIdentifier(String stId, RelationshipDirection direction) {
        String query;

        switch (direction) {
            case OUTGOING:
                query = "Match (n:DatabaseObject{stableIdentifier:{stId}})-[r]->(m) RETURN n,r,m";
                break;
            case INCOMING:
                query = "Match (n:DatabaseObject{stableIdentifier:{stId}})<-[r]-(m) RETURN n,r,m";
                break;
            default: // UNDIRECTED
                query = "Match (n:DatabaseObject{stableIdentifier:{stId}})-[r]-(m) RETURN n,r,m";
        }

        Map<String,Object> map = new HashMap<>();
        map.put("stId", stId);
        Result result =  neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }

    public <T> Collection<T> getObjectsByClassName(Class<T> clazz, Integer page, Integer offset) {
        String query = "MATCH (n:" +
                clazz.getSimpleName() +
                ") RETURN n ORDER BY n.displayName SKIP {skip} LIMIT {limit}";

        Map<String,Object> map = new HashMap<>();
        map.put("limit", offset);
        map.put("skip", (page-1) * offset);
        return (Collection<T>) neo4jTemplate.queryForObjects(clazz, query, map);
    }

    public Result getLocationsInPathwayBrowser(String stId) {
        String query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}})<-[r:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output|hasEvent*]-(m) Return  EXTRACT(rel IN r | [startNode(rel).stableIdentifier, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, labels(startNode(rel)) ]) as nodePairCollection";
        Map<String,Object> map = new HashMap<>();
        map.put("stableIdentifier", stId);
        return neo4jTemplate.query(query, map);
    }

//    public Map<String, List<Pair<String, String>>> getComponentsOf(String stId) {
//        String query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}})-[r:hasEvent|input|output|hasComponent|hasMember|hasCandidate|repeatedUnit]-(m) Return DISTINCT(type(r)) AS type, Collect(m.displayName) AS name, Collect(m.stableIdentifier) AS stId";
//        Map<String,Object> map = new HashMap<>();
//        map.put("stableIdentifier", stId);
//        Result result =  neo4jTemplate.query(query, map);
//        Map<String,List<Pair<String,String>>> componentOf = new TreeMap<>();
//        for (Map<String, Object> stringObjectMap : result) {
//            String[] names = (String[]) stringObjectMap.get("name");
//            String[] stIds = (String[]) stringObjectMap.get("stId");
//            for (int i = 0; i < stIds.length; i++) {
//                Pair<String,String> pair = new Pair<>(names[i],stIds[i]);
//                componentOf.computeIfAbsent((String) stringObjectMap.get("type"), l -> new ArrayList<>()).add(pair);
//            }
//        }
//        return componentOf;
//    }


    public PBNode getLocationsInPathwayBrowserTree(DatabaseObject databaseObject) {

        Result result = getLocationsInPathwayBrowser(databaseObject.getStableIdentifier());

        PBNode root = createNode(databaseObject);
        Map<String,PBNode> nodes = new HashMap<>();
        PBNode previous = root;
        nodes.put(root.getStId(),root);

        int previousSize = 0;
        for (Map<String, Object> stringObjectMap : result) {
            ArrayList<Object>[] nodePairCollections = ((ArrayList<Object>[])stringObjectMap.get("nodePairCollection"));
            int size = nodePairCollections.length;
            if (size>previousSize) {
                ArrayList<Object> objects = nodePairCollections[nodePairCollections.length-1];
                previous = addNode(previous,nodes,objects);
            } else {
                previous = root;
                for (ArrayList<Object> objects : nodePairCollections) {
                    if (objects.get(0) == null ){
                        continue;
                    }
                    previous = addNode(previous,nodes,objects);
                }
            }
            previousSize = size;
        }
        return  root ;
    }



    private PBNode addNode(PBNode previous, Map<String,PBNode> nodes, ArrayList<Object> objects) {
        PBNode node;


        if (nodes.containsKey(objects.get(0))) {
            node = nodes.get(objects.get(0));
        } else {
            node = createNode(objects);
            nodes.put(node.getStId(),node);
        }
        if (!node.getType().equals("CatalystActivity") && !node.getType().contains("Regulation") && !node.getType().equals("EntityFunctionalStatus")) {
            previous.addChild(node);
            node.addParent(previous);
            previous = node;
        }
        return previous;
    }

    private PBNode createNode(DatabaseObject databaseObject) {
        PBNode node = new PBNode();
        node.setStId(databaseObject.getStableIdentifier());
        node.setName(databaseObject.getDisplayName());
        node.setType(databaseObject.getSchemaClass());

        if (databaseObject instanceof Event) {
            Event event = (Event) databaseObject;
            node.setSpecies(event.getSpeciesName());
        } else if (databaseObject instanceof PhysicalEntity) {
            PhysicalEntity physicalEntity = (PhysicalEntity) databaseObject;
            node.setSpecies(physicalEntity.getSpeciesName());
        } else {
//            logger.error
            return null;
        }
        return node;
    }

    private PBNode createNode(ArrayList<Object> nodePairCollection) {
        PBNode node = new PBNode();
        node.setStId((String) nodePairCollection.get(0));
        node.setName((String) nodePairCollection.get(1));
        node.setDiagram((Boolean) nodePairCollection.get(2));
        node.setSpecies((String) nodePairCollection.get(3));
        List xx = (List) nodePairCollection.get(4);
        Class<?> lowestClass = Object.class;

        for (Object o1 : xx) {
            try {
                Class clazz = Class.forName("org.reactome.server.tools.domain.model." + o1.toString());
                if (lowestClass.isAssignableFrom(clazz)) {
                    lowestClass = clazz;
                }
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
                //todo toplevel pathway!!
                node.setType("Pathway");
            }
        }
        node.setType(lowestClass.getSimpleName());
        return node;
    }
}