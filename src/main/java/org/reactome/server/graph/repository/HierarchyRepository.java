package org.reactome.server.graph.repository;

import org.neo4j.driver.Value;
import org.neo4j.driver.types.MapAccessor;
import org.neo4j.driver.types.TypeSystem;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.model.Event;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.result.HierarchyWrapper;
import org.reactome.server.graph.domain.result.PathwayResult;
import org.reactome.server.graph.exception.CustomQueryException;
import org.reactome.server.graph.repository.util.HierarchyBranch;
import org.reactome.server.graph.service.AdvancedDatabaseObjectService;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.BiFunction;

@Repository
public class HierarchyRepository {

    private final AdvancedDatabaseObjectService advancedDatabaseObjectService;
    private final Neo4jClient neo4jClient;
    private final Neo4jMappingContext neo4jMappingContext;

    @Autowired
    public HierarchyRepository(AdvancedDatabaseObjectService advancedDatabaseObjectService, Neo4jClient neo4jClient, Neo4jMappingContext neo4jMappingContext) {
        this.advancedDatabaseObjectService = advancedDatabaseObjectService;
        this.neo4jClient = neo4jClient;
        this.neo4jMappingContext = neo4jMappingContext;
    }

    // -------------------------------- Locations in the Pathway Browser -----------------------------------------------

    /**
     * Get locations in the Pathway Browser in the normal search flow.
     *
     * @return a PathwayBrowserNode.
     */
    public PathwayBrowserNode getLocationsInPathwayBrowser(String stId, Boolean omitNonDisplayableItems) {
        Collection<HierarchyWrapper> result = getLocationsInPathwayBrowserByStIdRaw(stId);
        return parseResult(result, omitNonDisplayableItems);
    }

    public PathwayBrowserNode getLocationsInPathwayBrowser(Long dbId, Boolean omitNonDisplayableItems) {
        Collection<HierarchyWrapper> result = getLocationsInPathwayBrowserByDbIdRaw(dbId);
        return parseResult(result, omitNonDisplayableItems);
    }

    /**
     * Get locations in the Pathway Browser based on Interactor. It has a different query than the normal flow.
     *
     * @return a PathwayBrowserNode.
     */
    public PathwayBrowserNode getLocationsInPathwayBrowserDirectParticipants(String stId, Boolean omitNonDisplayableItems) {
        Collection<HierarchyWrapper> result = getLocationsInPathwayBrowserForInteractorByStIdRaw(stId);
        return parseResult(result, omitNonDisplayableItems);
    }

    public PathwayBrowserNode getLocationsInPathwayBrowserDirectParticipants(Long dbId, Boolean omitNonDisplayableItems) {
        Collection<HierarchyWrapper> result = getLocationsInPathwayBrowserForInteractorByDbIdRaw(dbId);
        return parseResult(result, omitNonDisplayableItems);
    }

    /**
     * Used to build the locations in the Pathway Browser for the EHLD containing a given icon
     * @param pathways list of pathways (can contain Pathway objects, dbIds, stIds - or a combination of them)
     * @return a set of PathwayBrowserNode
     */
    public Set<PathwayBrowserNode> getLocationInPathwayBrowserForPathways(List<?> pathways){
        Collection<HierarchyBranch> branches = getLocationInPathwayBrowserForPathwaysRaw(pathways);
        return mergeBranches(branches);
    }

    // --------------------------------------------- Sub Hierarchy -----------------------------------------------------

    public PathwayBrowserNode getSubHierarchy(String stId) {
        Collection<HierarchyWrapper> result = getSubHierarchyByStIdRaw(stId);
        return parseResult(result, false);
    }

    public PathwayBrowserNode getSubHierarchy(Long dbId) {
        Collection<HierarchyWrapper> result = getSubHierarchyByDbIdRaw(dbId);
        return parseResult(result, false);
    }

    // ------------------------------------------- Event Hierarchy -----------------------------------------------------

    public Collection<PathwayBrowserNode> getEventHierarchyBySpeciesName(String speciesName) {
        Collection<HierarchyWrapper> result = getEventHierarchyBySpeciesNameRaw(speciesName);
        return parseResults(result, false);
    }

    public Collection<PathwayBrowserNode> getEventHierarchyByTaxId(String taxId) {
        Collection<HierarchyWrapper> result = getEventHierarchyByTaxIdRaw(taxId);
        return parseResults(result, false);
    }


    /**
     * Build Locations in the Pathway Browser of a given query Result.
     * <p>
     * //     * @param id the one where querying for
     *
     * @param result the cypher query result
     * @return PathwayBrowserNode having parents and children
     */
    private PathwayBrowserNode parseResult(Collection<HierarchyWrapper> result, Boolean omitNonDisplayableItems) {

        // TODO uncomment here
        if (result != null && result.iterator().hasNext()) {
            Map<String, Object> stringObjectMap = null;//result.iterator().next();
            return parseRaw(stringObjectMap, omitNonDisplayableItems);
        }
        return null;
    }


    private Collection<PathwayBrowserNode> parseResults(Collection<HierarchyWrapper> result, Boolean omitNonDisplayableItems) {

        if (result != null && result.iterator().hasNext()) {
            Collection<PathwayBrowserNode> eventHierarchy = new ArrayList<>();

            // TODO uncomment here
//            for (Map<String, Object> stringObjectMap : result) {
//                eventHierarchy.add(parseRaw(stringObjectMap, omitNonDisplayableItems));
//            }
            return eventHierarchy;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private PathwayBrowserNode parseRaw(Map<String, Object> stringObjectMap, Boolean omitNonDisplayableItems) {
        PathwayBrowserNode root = createNode((DatabaseObject) stringObjectMap.get("n"));
        Map<String, PathwayBrowserNode> nodes = new HashMap<>();
        PathwayBrowserNode previous = root;
        nodes.put(root.getStId(), root);
        int previousSize = 0;
        Object[] nodePairCollections = (Object[]) stringObjectMap.get("nodePairCollection");
        if (nodePairCollections != null && nodePairCollections.length > 0) {
            for (ArrayList<Object> nodePairCollection : ((ArrayList<Object>[]) nodePairCollections)) {
                int size = nodePairCollection.size();
                if (size > previousSize) {
                    ArrayList<Object> objects = (ArrayList<Object>) nodePairCollection.get(nodePairCollection.size() - 1);
                    previous = addNode(previous, nodes, objects, omitNonDisplayableItems);
                } else {
                    previous = root;
                    for (Object objects : nodePairCollection) {
                        previous = addNode(previous, nodes, (ArrayList) objects, omitNonDisplayableItems);
                    }
                }
                previousSize = size;
            }
        }
        return root;
    }

    private Set<PathwayBrowserNode> mergeBranches(Collection<HierarchyBranch> branches){
        Map<String, PathwayBrowserNode> nodes = new HashMap<>();

        Set<PathwayBrowserNode> rtn = new HashSet<>();
        for (HierarchyBranch branch : branches) {
            List<Pathway> pathways = branch.getPathways();
            Pathway p = pathways.get(0);
            PathwayBrowserNode node = nodes.getOrDefault(p.getStId(), createNode(p));
            nodes.put(node.getStId(), node);
            rtn.add(node);
            for (int i = 1 ; i < pathways.size(); i++) {
                Pathway pathway = pathways.get(i);
                PathwayBrowserNode child = nodes.getOrDefault(pathway.getStId(), createNode(pathway));
                nodes.put(child.getStId(), child);
                node.addChild(child);
                child.addParent(node);
                node = child;
            }
        }
        return rtn;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private PathwayBrowserNode addNode(PathwayBrowserNode previous, Map<String, PathwayBrowserNode> nodes, ArrayList<Object> objects, Boolean omitNonDisplayableItems) {
        PathwayBrowserNode node;

        if (nodes.containsKey(objects.get(0))) {
            node = nodes.get(objects.get(0));
        } else {
            node = createNode(objects);
            nodes.put(node.getStId(), node);
        }

        //We do not link them in the Tree.
        if (!omitNonDisplayableItems || (!node.getType().equals("CatalystActivity") && !node.getType().contains("Regulation") && !node.getType().equals("Requirement") && !node.getType().equals("EntityFunctionalStatus"))) {
            previous.addChild(node);
            node.addParent(previous);
            previous = node;
        }
        return previous;
    }

    /**
     * Create a node based on the query Result
     *
     * @param nodePairCollection, the query result
     */
    @SuppressWarnings("unchecked")
    private PathwayBrowserNode createNode(ArrayList<Object> nodePairCollection) {
        PathwayBrowserNode node = new PathwayBrowserNode();
        node.setStId((String) nodePairCollection.get(0));
        node.setName((String) nodePairCollection.get(1));
        node.setDiagram((Boolean) nodePairCollection.get(2));
        node.setSpecies((String) nodePairCollection.get(3));
        node.setType((String) nodePairCollection.get(4));

        doHighlighting(node);

        return node;
    }

    private PathwayBrowserNode createNode(DatabaseObject databaseObject) {
        PathwayBrowserNode node = new PathwayBrowserNode();
        node.setStId(databaseObject.getStId());
        node.setName(databaseObject.getDisplayName());
        // do not use SchemaClass here
        node.setType(databaseObject.getClass().getSimpleName());

        // Root by default is clickable and highlighted
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
        }

        return node;
    }


    private void doHighlighting(PathwayBrowserNode node) {
        if (node.getType().equals("TopLevelPathway")) {
            node.setClickable(true);
            node.setHighlighted(false);
        }

        if (node.getType().contains("Reaction") || node.getType().equals("BlackBoxEvent") || node.getType().contains("Polymerisation")) {
            node.setClickable(true);
            node.setHighlighted(true);
        }
    }

    // --------------------------------------------- Sub Hierarchy -----------------------------------------------------

    public Collection<HierarchyWrapper> getSubHierarchyByDbIdRaw(Long dbId) {
        String query = "" +
                "MATCH (n:DatabaseObject{dbId:$dbId}) " +
                "OPTIONAL MATCH path=(n)-[:hasEvent|input|output|repeatedUnit|hasMember|hasCandidate|hasComponent*]->(m:DatabaseObject) " +
                "WITH *, relationships(path) as r " +
                "RETURN n, " +
                "COLLECT( [rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, endNode(rel).speciesName, endNode(rel).schemaClass ]] ) AS nodePairCollection";


        BiFunction<TypeSystem, MapAccessor, DatabaseObject> mappingFunction = neo4jMappingContext.getRequiredMappingFunctionFor(DatabaseObject.class);
        Collection<HierarchyWrapper> wrapper = neo4jClient.query(query)
                .bindAll(Collections.singletonMap("dbId", dbId))
                .fetchAs(HierarchyWrapper.class)
                .mappedBy((typeSystem, record) -> {
                    Iterator<Value> values =  record.get("nodePairCollection").values().iterator();
                    Collection<Collection<PathwayResult>> pathwayResults = new ArrayList<>();
                    while(values.hasNext()){
                        Iterator<org.neo4j.driver.Value> whatDoICallYou = values.next().values().iterator();
                        while(whatDoICallYou.hasNext()) {
                            Iterator<org.neo4j.driver.Value> innerValues = whatDoICallYou.next().values().iterator();
                            while (innerValues.hasNext()) {
                                Value v = innerValues.next();
                                System.out.println(v);
                            }
                        }
                        //
                    }

                    DatabaseObject databaseObject = mappingFunction.apply(typeSystem, record.get("n"));
                    return new HierarchyWrapper(databaseObject, pathwayResults);
                }).all();

        return wrapper;
    }

    private Collection<HierarchyWrapper> getSubHierarchyByStIdRaw(String stId) {
        //language=Cypher
        String query = "" +
                "MATCH (n:DatabaseObject{stId:$stId}) " +
                "OPTIONAL MATCH path=(n)-[:hasEvent|input|output|repeatedUnit|hasMember|hasCandidate|hasComponent*]->(m:DatabaseObject) " +
                "WITH *, relationships(path) as r " +
                "RETURN n, " +
                "COLLECT( [rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, endNode(rel).speciesName, endNode(rel).schemaClass ]] ) AS nodePairCollection";
        Collections.singletonMap("stId", stId);
        return null;
    }

    // ------------------------------------------- Event Hierarchy -----------------------------------------------------

    private Collection<HierarchyWrapper> getEventHierarchyBySpeciesNameRaw(String speciesName) {
        //language=Cypher
        String query = "" +
                "MATCH path=(n:TopLevelPathway{speciesName:$speciesName})-[:hasEvent*]->(m:Event) " +
                "WITH *, relationships(path) as r " +
                "RETURN n, " +
                "COLLECT( [rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, endNode(rel).speciesName, endNode(rel).schemaClass ]] ) AS nodePairCollection";
        Collections.singletonMap("speciesName", speciesName);

        return null;
    }

    private Collection<HierarchyWrapper> getEventHierarchyByTaxIdRaw(String taxId) {
        //language=Cypher
        String query = "" +
                "MATCH (s:Species{taxId:$taxId})<-[:species]-(n:TopLevelPathway)" +
                "MATCH path=(n)-[:hasEvent*]->(m:Event) " +
                "WITH *, relationships(path) as r " +
                "RETURN n, COLLECT( [rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, endNode(rel).speciesName, endNode(rel).schemaClass ]] ) AS nodePairCollection";
        Collections.singletonMap("taxId", taxId);
        return null;
    }

    // -------------------------------- Locations in the Pathway Browser -----------------------------------------------

    /**
     * This cypher query retrieves all the information needed to build the Locations in the PWB Tree.
     * [StableIdentifier, _displayName, hasDiagram, [labels e.g DatabaseObject, Event, ReactionLikeEvent, Reactions]]
     *
     * @param stId, tree for the given StId
     * @return nodePairCollection
     */
    private Collection<HierarchyWrapper> getLocationsInPathwayBrowserByStIdRaw(String stId) {
        //language=Cypher
        String query = "" +
                "MATCH (n:DatabaseObject{stId:$stId}) " +
                "OPTIONAL MATCH path=(n)<-[:regulatedBy|regulator|physicalEntity|requiredInputComponent|diseaseEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output|hasEvent*]-() " +
                "RETURN n, COLLECT( [rel IN relationships(path)| [startNode(rel).stId, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, startNode(rel).schemaClass ]] ) as nodePairCollection";
        Collections.singletonMap("stId", stId);
        return null;
    }

    /**
     * This cypher query retrieves all the information needed to build the Locations in the PWB Tree
     * [StableIdentifier, _displayName, hasDiagram, [labels e.g DatabaseObject, Event, ReactionLikeEvent, Reactions]]
     *
     * @param dbId, tree for the given dbId
     * @return nodePairCollection
     */
    private Collection<HierarchyWrapper> getLocationsInPathwayBrowserByDbIdRaw(Long dbId) {
        //language=Cypher
        String query = "" +
                "MATCH (n:DatabaseObject{dbId:$dbId}) " +
                "OPTIONAL MATCH path=(n)<-[:regulatedBy|regulator|physicalEntity|requiredInputComponent|diseaseEntity|entityFunctionalStatus|activeUnit|catalystActivity|repeatedUnit|hasMember|hasCandidate|hasComponent|input|output|hasEvent*]-() " +
                "RETURN n, COLLECT( [rel IN relationships(path)| [startNode(rel).stId, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, startNode(rel).schemaClass ]] ) as nodePairCollection";
        Collections.singletonMap("dbId", dbId);
        return null;
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
    private Collection<HierarchyWrapper> getLocationsInPathwayBrowserForInteractorByStIdRaw(String stId) {
        //language=Cypher
        String query = "" +
                "MATCH (n:DatabaseObject{stId:$stId}) " +
                "OPTIONAL MATCH path=(n)<-[:regulatedBy|regulator|physicalEntity|catalystActivity|requiredInputComponent|diseaseEntity|entityFunctionalStatus|input|output|hasEvent*]-() " +
                "RETURN n, COLLECT( [rel IN relationships(path) | [startNode(rel).stId, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, startNode(rel).schemaClass ]] ) as nodePairCollection";
        Collections.singletonMap("stId", stId);
        return null;
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
    private Collection<HierarchyWrapper> getLocationsInPathwayBrowserForInteractorByDbIdRaw(Long dbId) {
        //language=Cypher
        String query = "" +
                "MATCH (n:DatabaseObject{dbId:$dbId}) " +
                "OPTIONAL MATCH path=(n)<-[:regulatedBy|regulator|physicalEntity|catalystActivity|requiredInputComponent|diseaseEntity|entityFunctionalStatus|input|output|hasEvent*]-() " +
                "RETURN n, COLLECT( [rel IN relationships(path) | [startNode(rel).stId, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, startNode(rel).schemaClass ]] ) as nodePairCollection";
        Collections.singletonMap("dbId", dbId);
        //return neo4jTemplate.query(query, map);
        return null;
    }

    private Collection<HierarchyBranch> getLocationInPathwayBrowserForPathwaysRaw(List<?> pathways) {
        //language=Cypher
        String query = "" +
                "MATCH (p:Pathway) " +
                "WHERE p.dbId IN $dbIds OR p.stId IN $stIds " +
                "OPTIONAL MATCH path=(:TopLevelPathway)-[:hasEvent*]->(p) " +
                "WITH p, NODES(path) AS branch " +
                "RETURN CASE WHEN SIZE(branch) > 0 THEN branch ELSE [p] END AS branch";

        List<String> stIds = new ArrayList<>();
        List<Long> dbIds = new ArrayList<>();
        for (Object aux : pathways) {
            if (aux instanceof DatabaseObject){
                dbIds.add(((DatabaseObject) aux).getDbId());
            } else {
                String identifier = String.valueOf(aux);
                if (DatabaseObjectUtils.isStId(identifier)) {
                    stIds.add(identifier);
                } else {
                    dbIds.add(Long.valueOf(identifier));
                }
            }
        }

        try {
            return advancedDatabaseObjectService.getCustomQueryResults(HierarchyBranch.class, query, Map.of("stIds", stIds, "dbIds", dbIds));
        } catch (CustomQueryException ex){
            return null;
        }
    }
}
