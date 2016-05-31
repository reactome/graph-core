package org.reactome.server.graph.repository;

import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 24.05.16.
 */
@Repository
public class HierarchyRepository {

    private static final Logger logger = LoggerFactory.getLogger(DetailsRepository.class);

    @Autowired
    private Neo4jOperations neo4jTemplate;

    public Collection<PathwayBrowserNode> getEventHierarchy(String speciesName) {

        Collection<PathwayBrowserNode> eventHierarchy = new ArrayList<>();
        Result result = getEventHierarchyByName(speciesName);
        for (Map<String, Object> stringObjectMap : result) {
            PathwayBrowserNode root = createNode((TopLevelPathway) stringObjectMap.get("n"));

            Map<String,PathwayBrowserNode> nodes = new HashMap<>();
            PathwayBrowserNode previous = root;
            nodes.put(root.getStId(),root);
            int previousSize = 0;

            for (ArrayList<Object> nodePairCollection : ((ArrayList<Object>[])stringObjectMap.get("nodePairCollection"))) {
                int size = nodePairCollection.size();
                if (size>previousSize) {
                    ArrayList<Object> objects = (ArrayList<Object>) nodePairCollection.get(nodePairCollection.size()-1);
                    previous = addNode(previous,nodes,objects);
                } else {
                    previous = root;
                    for (Object objects : nodePairCollection) {
                        previous = addNode(previous,nodes,(ArrayList) objects);
                    }
                }
                previousSize = size;
            }
            eventHierarchy.add(root);
        }
        return eventHierarchy;
    }

    public PathwayBrowserNode getSubHierarchy(String id) {

        Result result = null;
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            result = getSubHierarchyByStId(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            result = getSubHierarchyByDbId(Long.parseLong(id));
        }
        if (result != null && result.iterator().hasNext()) {
            Map<String, Object> stringObjectMap = result.iterator().next();
            PathwayBrowserNode root = createNode((DatabaseObject)stringObjectMap.get("n"));
            Map<String,PathwayBrowserNode> nodes = new HashMap<>();
            PathwayBrowserNode previous = root;
            nodes.put(root.getStId(),root);
            int previousSize = 0;
            for (ArrayList<Object> nodePairCollection : ((ArrayList<Object>[])stringObjectMap.get("nodePairCollection"))) {
                int size = nodePairCollection.size();
                if (size>previousSize) {
                    ArrayList<Object> objects = (ArrayList<Object>) nodePairCollection.get(nodePairCollection.size()-1);
                    previous = addNode(previous,nodes,objects);
                } else {
                    previous = root;
                    for (Object objects : nodePairCollection) {
                        previous = addNode(previous,nodes,(ArrayList) objects);
                    }
                }
                previousSize = size;
            }
            return root;
        }
        return null;
    }

    private Result getSubHierarchyByDbId(Long dbId) {
        String query = "Match (n:DatabaseObject{dbId:{dbId}})-[r:hasEvent|input|output|repeatedUnit|hasMember|hasCandidate|hasComponent*]->(m:DatabaseObject) Return n, COLLECT(EXTRACT(rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, labels(endNode(rel)) ])) AS nodePairCollection";
        Map<String,Object> map = new HashMap<>();
        map.put("dbId", dbId);
        return neo4jTemplate.query(query, map);
    }

    private Result getSubHierarchyByStId(String stId) {
        String query = "Match (n:DatabaseObject{stId:{stId}})-[r:hasEvent|input|output|repeatedUnit|hasMember|hasCandidate|hasComponent*]->(m:DatabaseObject) Return n, COLLECT(EXTRACT(rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, labels(endNode(rel)) ])) AS nodePairCollection";
        Map<String,Object> map = new HashMap<>();
        map.put("stId", stId);
        return neo4jTemplate.query(query, map);
    }



    private Result getEventHierarchyByName(String speciesName) {
        String query = "Match (n:TopLevelPathway)-[r:hasEvent*]->(m:Event) Where n.speciesName={speciesName} " +
                "Return n, COLLECT(EXTRACT(rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, " +
                "labels(endNode(rel)) ])) AS nodePairCollection";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesName", speciesName);
        return neo4jTemplate.query(query, map);
    }

    private Result getEventHierarchyByDbId(Long dbId) {
        String query = "Match (s:Species{dbId:48887})-[:species]-(n:TopLevelPathway)-[r:hasEvent*]->(m:Event)" +
                "Return n, COLLECT(EXTRACT(rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, " +
                "labels(endNode(rel)) ])) AS nodePairCollection";
        Map<String,Object> map = new HashMap<>();
        map.put("dbId", dbId);
        return neo4jTemplate.query(query, map);
    }

    private Result getEventHierarchyByTaxId(Long taxId) {

        String query = "Match (s:Species{taxID:9606})-[:species]-(n:TopLevelPathway)-[r:hasEvent*]->(m:Event) " +
                "Return n, COLLECT(EXTRACT(rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, " +
                "labels(endNode(rel)) ])) AS nodePairCollection";
        Map<String,Object> map = new HashMap<>();
        map.put("taxId", taxId);
        return neo4jTemplate.query(query, map);
    }




    private PathwayBrowserNode addNode(PathwayBrowserNode previous, Map<String,PathwayBrowserNode> nodes, ArrayList<Object> objects) {
        PathwayBrowserNode node;

        //todo fixme
        //noinspection SuspiciousMethodCalls
        if (nodes.containsKey(objects.get(0))) {
            //todo fixme
            //noinspection SuspiciousMethodCalls
            node = nodes.get(objects.get(0));
        } else {
            node = createNode(objects);
            nodes.put(node.getStId(),node);
        }
        previous.addChild(node);
        previous = node;
        return previous;
    }

    /**
     * Create the root item for the Tree. This is the item we have searched.
     *
     * THIS Is DUPLICATED NOT DEPRECATED
     */
    @Deprecated
    private PathwayBrowserNode createNode(DatabaseObject databaseObject) {
        PathwayBrowserNode node = new PathwayBrowserNode();
        node.setStId(databaseObject.getStId());
        node.setName(databaseObject.getDisplayName());
        node.setType(databaseObject.getSchemaClass());
        if (databaseObject instanceof Event) {
            Event event = (Event) databaseObject;
            node.setSpecies(event.getSpeciesName());
            if (event instanceof Pathway) {
                Pathway pathway = (Pathway) event;
                node.setDiagram(pathway.getHasDiagram());
            }
        } else if (databaseObject instanceof PhysicalEntity) {
            PhysicalEntity physicalEntity = (PhysicalEntity) databaseObject;
            node.setSpecies(physicalEntity.getSpeciesName());
        } else {
            logger.error("Creating a node that is not an Event or PhysicalEntity");
        }
        return node;
    }

    private PathwayBrowserNode createNode(ArrayList<Object> nodePairCollection) {
        PathwayBrowserNode node = new PathwayBrowserNode();
        node.setStId((String) nodePairCollection.get(0));
        node.setName((String) nodePairCollection.get(1));
        node.setDiagram((Boolean) nodePairCollection.get(2));
        node.setType(DatabaseObjectUtils.getSchemaClass((Collection<String>) nodePairCollection.get(3)));
        return node;
    }
}
