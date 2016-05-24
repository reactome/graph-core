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
public class EventHierarchyRepository {

    private static final Logger logger = LoggerFactory.getLogger(DetailsRepository.class);

    @Autowired
    private Neo4jOperations neo4jTemplate;

    public Collection<PathwayBrowserNode> getEventHierarchy(String speciesName) {

        Collection<PathwayBrowserNode> eventHierarchy = new ArrayList<>();
        Result result = getEventHierarchyRaw(speciesName);
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
    private Result getEventHierarchyRaw(String speciesName) {
        String query = "Match (n:TopLevelPathway)-[r:hasEvent*]->(m:Event) Where n.speciesName={speciesName} Return n, COLLECT(EXTRACT(rel IN r | [endNode(rel).stableIdentifier, endNode(rel).displayName, endNode(rel).hasDiagram, labels(endNode(rel)) ])) AS nodePairCollection";
        Map<String,Object> map = new HashMap<>();
        map.put("speciesName", speciesName);
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

    private PathwayBrowserNode createNode(Event event) {
        PathwayBrowserNode node = new PathwayBrowserNode();
        node.setStId(event.getStableIdentifier());
        node.setName(event.getDisplayName());
        node.setType(event.getSchemaClass());
        node.setSpecies(event.getSpeciesName());
        if (event instanceof Pathway) {
            Pathway pathway = (Pathway) event;
            node.setDiagram(pathway.getHasDiagram());
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
