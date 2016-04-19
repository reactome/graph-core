package org.reactome.server.tools.repository;

import org.neo4j.ogm.model.Result;
import org.reactome.server.tools.domain.model.DatabaseObject;
import org.reactome.server.tools.domain.model.Event;
import org.reactome.server.tools.domain.model.PhysicalEntity;
import org.reactome.server.tools.service.helper.PathwayBrowserNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.04.16.
 */
@Repository
public class DetailsRepository {

    private static final Logger logger = LoggerFactory.getLogger(DetailsRepository.class);

    @Autowired
    private Neo4jOperations neo4jTemplate;

    public PathwayBrowserNode getLocationsInPathwayBrowser(DatabaseObject databaseObject) {
        Result result = getLocationsInPathwayBrowser(databaseObject.getStableIdentifier());
        PathwayBrowserNode root = createNode(databaseObject);
        Map<String,PathwayBrowserNode> nodes = new HashMap<>();
        PathwayBrowserNode previous = root;
        nodes.put(root.getStId(),root);
        int previousSize = 0;
        for (Map<String, Object> stringObjectMap : result) {
            @SuppressWarnings("unchecked")
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

    private Result getLocationsInPathwayBrowser(String stId) {
        String query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}})<-[r:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output|hasEvent*]-(m) Return  EXTRACT(rel IN r | [startNode(rel).stableIdentifier, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, labels(startNode(rel)) ]) as nodePairCollection";
        Map<String,Object> map = new HashMap<>();
        map.put("stableIdentifier", stId);
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
        if (!node.getType().equals("CatalystActivity") && !node.getType().contains("Regulation") && !node.getType().equals("EntityFunctionalStatus")) {
            previous.addChild(node);
            node.addParent(previous);
            previous = node;
        }
        return previous;
    }

    private PathwayBrowserNode createNode(DatabaseObject databaseObject) {
        PathwayBrowserNode node = new PathwayBrowserNode();
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
            logger.error("Creating a node that is not an Event or PhysicalEntity");
        }
        return node;
    }

    private PathwayBrowserNode createNode(ArrayList<Object> nodePairCollection) {
        PathwayBrowserNode node = new PathwayBrowserNode();
        node.setStId((String) nodePairCollection.get(0));
        node.setName((String) nodePairCollection.get(1));
        node.setDiagram((Boolean) nodePairCollection.get(2));
        node.setSpecies((String) nodePairCollection.get(3));
        List labels = (List) nodePairCollection.get(4);
        Class<?> lowestClass = Object.class;
        for (Object label : labels) {
            try {
                Class clazz = Class.forName("org.reactome.server.tools.domain.model." + label.toString());
                if (lowestClass.isAssignableFrom(clazz)) {
                    lowestClass = clazz;
                }
            } catch (ClassNotFoundException e) {
                logger.error("Class specified could not be found", e);
            }
        }
        node.setType(lowestClass.getSimpleName());
        return node;
    }
}