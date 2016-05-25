package org.reactome.server.graph.repository;

import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.PhysicalEntity;
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
 * @since 14.04.16.
 */
@Repository
public class DetailsRepository {

    private static final Logger logger = LoggerFactory.getLogger(DetailsRepository.class);

    @Autowired
    private Neo4jOperations neo4jTemplate;


    /**
     * Build Locations in the Pathway Browser of a given query Result.
     *
     * @param databaseObject the one where querying for
     * @param result the cypher query result
     *
     * @return PathwayBrowserNode having parents and children
     */
    private PathwayBrowserNode getLocationsInPathwayBrowserResult(DatabaseObject databaseObject, Result result) {
        PathwayBrowserNode root = createRoot(databaseObject);
        Map<String, PathwayBrowserNode> nodes = new HashMap<>();
        PathwayBrowserNode previous = root;
        nodes.put(root.getStId(), root);
        int previousSize = 0;
        for (Map<String, Object> stringObjectMap : result) {
            @SuppressWarnings("unchecked")
            ArrayList<Object>[] nodePairCollections = ((ArrayList<Object>[]) stringObjectMap.get("nodePairCollection"));
            int size = nodePairCollections.length;
            if (size > previousSize) {
                ArrayList<Object> objects = nodePairCollections[nodePairCollections.length - 1];
                previous = addNode(previous, nodes, objects);
            } else {
                previous = root;
                for (ArrayList<Object> objects : nodePairCollections) {
                    if (objects.get(0) == null) {
                        continue;
                    }
                    previous = addNode(previous, nodes, objects);
                }
            }
            previousSize = size;
        }

        return root;
    }

    /**
     * Get locations in the Pathway Browser based on Interactor. It has a different query than the normal flow.
     *
     * @return a PathwayBrowserNode.
     */
    public PathwayBrowserNode getLocationsInPathwayBrowserForInteractors(DatabaseObject databaseObject) {

        Result result;
        if (databaseObject.getStableIdentifier() != null) {
            result = getLocationsInPathwayBrowserForInteractor(databaseObject.getStableIdentifier());
        } else {
            result = getLocationsInPathwayBrowserForInteractor(databaseObject.getDbId());
        }

        return getLocationsInPathwayBrowserResult(databaseObject, result);
    }

    /**
     * Get locations in the Pathway Browser in the normal search flow.
     *
     * @return a PathwayBrowserNode.
     */
    public PathwayBrowserNode getLocationsInPathwayBrowser(DatabaseObject databaseObject) {

        Result result;
        if (databaseObject.getStableIdentifier() != null) {
            result = getLocationsInPathwayBrowser(databaseObject.getStableIdentifier());
        } else {
            result = getLocationsInPathwayBrowser(databaseObject.getDbId());
        }

        return getLocationsInPathwayBrowserResult(databaseObject, result);

    }

    /**
     * This cypher query retrieves all the information needed to build the Locations in the PWB Tree.
     * [StableIdentifier, _displayName, hasDiagram, [labels e.g DatabaseObject, Event, ReactionLikeEvent, Reactions]]
     *
     * @param stId, tree for the given StId
     * @return nodePairCollection
     */
    private Result getLocationsInPathwayBrowser(String stId) {
        String query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}})<-[r:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output|hasEvent*]-(m) Return  EXTRACT(rel IN r | [startNode(rel).stableIdentifier, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, labels(startNode(rel)) ]) as nodePairCollection";
        Map<String, Object> map = new HashMap<>();
        map.put("stableIdentifier", stId);
        return neo4jTemplate.query(query, map);
    }

    /**
     * This cypher query retrieves all the information needed to build the Locations in the PWB Tree
     * [StableIdentifier, _displayName, hasDiagram, [labels e.g DatabaseObject, Event, ReactionLikeEvent, Reactions]]
     *
     * @param dbId, tree for the given dbId
     * @return nodePairCollection
     */
    private Result getLocationsInPathwayBrowser(Long dbId) {
        String query = "Match (n:DatabaseObject{dbId:{dbId}})<-[r:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output|hasEvent*]-(m) Return  EXTRACT(rel IN r | [startNode(rel).stableIdentifier, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, labels(startNode(rel)) ]) as nodePairCollection";
        Map<String, Object> map = new HashMap<>();
        map.put("dbId", dbId);
        return neo4jTemplate.query(query, map);
    }

    /**
     * This cypher query retrieves all the information needed to build the Locations in the PWB Tree based on the Interactors.
     * It means, we are filtering the tree to show only Proteins directly associate to a Reaction, then when the user clicks on it
     * He will see the protein in the Diagram.
     * <p>
     * [StableIdentifier, _displayName, hasDiagram, [labels e.g DatabaseObject, Event, ReactionLikeEvent, Reactions]]
     *
     * @param stId, tree for the given stId
     * @return nodePairCollection
     */
    private Result getLocationsInPathwayBrowserForInteractor(String stId) {
        String query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}})<-[r:regulatedBy|regulator|physicalEntity|catalystActivity|input|output|hasEvent*]-(m) Return EXTRACT(rel IN r | [startNode(rel).stableIdentifier, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, labels(startNode(rel)) ]) as nodePairCollection";
        Map<String, Object> map = new HashMap<>();
        map.put("stableIdentifier", stId);
        return neo4jTemplate.query(query, map);
    }

    /**
     * This cypher query retrieves all the information needed to build the Locations in the PWB Tree based on the Interactors.
     * It means, we are filtering the tree to show only Proteins directly associate to a Reaction, then when the user clicks on it
     * He will see the protein in the Diagram.
     * <p>
     * [StableIdentifier, _displayName, hasDiagram, [labels e.g DatabaseObject, Event, ReactionLikeEvent, Reactions]]
     *
     * @param dbId, tree for the given dbId
     * @return nodePairCollection
     */
    private Result getLocationsInPathwayBrowserForInteractor(Long dbId) {
        String query = "Match (n:DatabaseObject{dbId:{dbId}})<-[r:regulatedBy|regulator|physicalEntity|catalystActivity|input|output|hasEvent*]-(m) Return EXTRACT(rel IN r | [startNode(rel).stableIdentifier, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, labels(startNode(rel)) ]) as nodePairCollection";
        Map<String, Object> map = new HashMap<>();
        map.put("dbId", dbId);
        return neo4jTemplate.query(query, map);
    }

    public DatabaseObject detailsPageQuery(String stId) {
        String query = "Match (n:DatabaseObject{stableIdentifier:{stableIdentifier}})-[r]->(m)" +
                "OPTIONAL MATCH (n)<-[e:inferredTo|regulator|regulatedBy]-(l)" +
//                       "OPTIONAL MATCH (m:Regulation)-[x:regulator|regulatedBy]-(y)" +
                "OPTIONAL MATCH (m:ReferenceEntity)-[t:crossReference|referenceGene|referenceTranscript]->(z)" +
                "OPTIONAL MATCH (z:AbstractModifiedResidue)-[u:psiMod|modification]-(i)" +
                "OPTIONAL MATCH (m:CatalystActivity)-[o:catalystActivity|physicalEntity|activity]-(p)" +
                "Return n,r,m,l,e,t,z,u,i,o,p";
        Map<String, Object> map = new HashMap<>();
        map.put("stableIdentifier", stId);
        Result result = neo4jTemplate.query(query, map);
        if (result != null && result.iterator().hasNext())
            return (DatabaseObject) result.iterator().next().get("n");
        return null;
    }

    private PathwayBrowserNode addNode(PathwayBrowserNode previous, Map<String, PathwayBrowserNode> nodes, ArrayList<Object> objects) {
        PathwayBrowserNode node;

        //todo fixme
        //noinspection SuspiciousMethodCalls
        if (nodes.containsKey(objects.get(0))) {
            //todo fixme
            //noinspection SuspiciousMethodCalls
            node = nodes.get(objects.get(0));
        } else {
            node = createNode(objects);
            nodes.put(node.getStId(), node);
        }

        /**
         * We do not link them in the Tree.
         */
        if (!node.getType().equals("CatalystActivity") && !node.getType().contains("Regulation") && !node.getType().equals("EntityFunctionalStatus")) {
            previous.addChild(node);
            node.addParent(previous);
            previous = node;
        }
        return previous;
    }

    /**
     * Create the root item for the Tree. This is the item we have searched.
     */
    private PathwayBrowserNode createRoot(DatabaseObject databaseObject) {
        PathwayBrowserNode node = new PathwayBrowserNode();
        node.setStId(databaseObject.getStableIdentifier());
        node.setName(databaseObject.getDisplayName());
        node.setType(databaseObject.getSchemaClass());

        /** Root by default is clickable and highlighted **/
        node.setClickable(true);
        node.setHighlighted(true);

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

    /**
     * Create a node based on the query Result
     *
     * @param nodePairCollection, the query result
     */
    private PathwayBrowserNode createNode(ArrayList<Object> nodePairCollection) {
        PathwayBrowserNode node = new PathwayBrowserNode();
        node.setStId((String) nodePairCollection.get(0));
        node.setName((String) nodePairCollection.get(1));
        node.setDiagram((Boolean) nodePairCollection.get(2));
        node.setSpecies((String) nodePairCollection.get(3));
        node.setType(DatabaseObjectUtils.getSchemaClass((Collection<String>) nodePairCollection.get(4)));

        doHighlighting(node);

        return node;
    }

    private void doHighlighting(PathwayBrowserNode node) {
        if (node.getType().equals("ToplevelPathway")) {
            node.setClickable(true);
            node.setHighlighted(false);
        }

        if (node.getType().contains("Reaction") || node.getType().equals("BlackBoxEvent") || node.getType().contains("Polymerisation")) {
            node.setClickable(true);
            node.setHighlighted(true);
        }
    }
}


//    private Result getLocationInThePathwayBrowser2(Long dbId) {
////        String query = "Match (n:DatabaseObject{dbId:199420})<-[r:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output|hasEvent*]-(m) Return n.stableIdentifier AS stId, n.displayName as name, n.hasDiagram AS diagram, n.speciesName AS species, labels(n) AS labels, Collect (EXTRACT(rel IN r | [startNode(rel).stableIdentifier, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, labels(startNode(rel)) ])) as locationsTree";
//
//        String query = "Match(n:DatabaseObject{stableIdentifier:'R-ALL-113592'})<-[r:regulatedBy|regulator|physicalEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output|hasEvent*]-(m) Return n.stableIdentifier AS stId, n.displayName as name, n.hasDiagram AS diagram, n.speciesName AS species, labels(n) AS labels, Collect (EXTRACT(rel IN r | [startNode(rel).stableIdentifier, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, labels(startNode(rel)) ])) as locationsTree";
//        Map<String,Object> map = new HashMap<>();
//        map.put("dbId", dbId);
//        return neo4jTemplate.query(query, map);
//    }
//
//    public PathwayBrowserNode getLocationsInPathwayBrowser2(Long dbId) {
//        logger.info("Started testing detailsService.findReverseReactionOrPrecedingEvent");
//        long start, time;
//        start = System.currentTimeMillis();
//
//
//        Result result = getLocationInThePathwayBrowser2(dbId);
//
//        time = System.currentTimeMillis() - start;
//
//        if (result != null && result.iterator().hasNext()) {
//
//            Map<String, Object> stringObjectMap = result.iterator().next();
//
//            PathwayBrowserNode root = createNode(stringObjectMap);
////            if (!root.getType().equals("CatalystActivity") && !root.getType().contains("Regulation") && !root.getType().equals("EntityFunctionalStatus")) {
//
//            Map<String, PathwayBrowserNode> nodes = new HashMap<>();
//            PathwayBrowserNode previous = root;
//            nodes.put(root.getStId(), root);
//
//            ArrayList<Object>[] locationsTree = (ArrayList<Object>[]) stringObjectMap.get("locationsTree");
//            int previousSize = 0;
//            for (ArrayList<Object> location : locationsTree) {
//                int size = location.size();
//                if (size > previousSize) {
//                    ArrayList<Object> entry = (ArrayList<Object>) location.get(location.size() - 1);
//                    previous = addNode(previous, nodes, entry);
//                } else {
//                    previous = root;
//                    for (Object object : location) {
//                        ArrayList<Object> entry = (ArrayList<Object>) object;
//                        if (entry.get(0) == null) {
//                            continue;
//                        }
//                        previous = addNode(previous, nodes, entry);
//                    }
//                }
//                previousSize = size;
//            }
//
//
//            return root;
//        }
//        return null;
//    }
//    private PathwayBrowserNode createNode(Map<String, Object> stringObjectMap) {
//        PathwayBrowserNode node = new PathwayBrowserNode();
//        node.setStId((String) stringObjectMap.get("stId"));
//        node.setName((String) stringObjectMap.get("name"));
//        node.setDiagram((Boolean) stringObjectMap.get("diagram"));
//        node.setSpecies((String) stringObjectMap.get("species"));
//        try {
//
//            String[] labels = (String[]) stringObjectMap.get("labels");
//            node.setType(DatabaseObjectUtils.getSchemaClass(Arrays.asList(labels)));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return node;
//    }
