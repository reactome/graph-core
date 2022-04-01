package org.reactome.server.graph.repository;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.result.HierarchyBranch;
import org.reactome.server.graph.domain.result.HierarchyTreeItem;
import org.reactome.server.graph.domain.result.HierarchyWrapper;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HierarchyRepository {

    private final Neo4jClient neo4jClient;

    public HierarchyRepository(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
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
     *
     * @param pathways list of pathways (can contain Pathway objects, dbIds, stIds - or a combination of them)
     * @return a set of PathwayBrowserNode
     */
    public Set<PathwayBrowserNode> getLocationInPathwayBrowserForPathways(List<?> pathways) {
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
        if (result != null && result.iterator().hasNext())
            return parseRaw(result.iterator().next(), omitNonDisplayableItems);
        return null;
    }

    private Collection<PathwayBrowserNode> parseResults(Collection<HierarchyWrapper> result, Boolean omitNonDisplayableItems) {
        if (result != null && result.iterator().hasNext()) {
            Collection<PathwayBrowserNode> eventHierarchy = new TreeSet<>();
            for (HierarchyWrapper hierarchyWrapper : result) {
                eventHierarchy.add(parseRaw(hierarchyWrapper, omitNonDisplayableItems));
            }
            return eventHierarchy;
        }
        return null;
    }

    private PathwayBrowserNode parseRaw(HierarchyWrapper result, Boolean omitNonDisplayableItems) {
        PathwayBrowserNode root = createRootNode(result.getRoot());
        Map<String, PathwayBrowserNode> nodes = new HashMap<>();
        PathwayBrowserNode previous = root;
        nodes.put(root.getStId(), root);
        int previousSize = 0;
        Collection<Collection<HierarchyTreeItem>> resultNodes = result.getNodes();
        if (resultNodes != null && resultNodes.size() > 0) {
            for (Collection<HierarchyTreeItem> hierarchyTreeItems : resultNodes) {
                int size = hierarchyTreeItems.size();
                if (size > previousSize) {
                    ArrayList<HierarchyTreeItem> hierarchyTreeItemArrayList = (ArrayList<HierarchyTreeItem>) hierarchyTreeItems;
                    HierarchyTreeItem hierarchyTreeItem = hierarchyTreeItemArrayList.get(hierarchyTreeItems.size() - 1);
                    previous = addNode(previous, nodes, hierarchyTreeItem, omitNonDisplayableItems);
                } else {
                    previous = root;
                    for (HierarchyTreeItem hierarchyTreeItem : hierarchyTreeItems) {
                        previous = addNode(previous, nodes, hierarchyTreeItem, omitNonDisplayableItems);
                    }
                }
                previousSize = size;
            }
        }
        return root;
    }

    private Set<PathwayBrowserNode> mergeBranches(Collection<HierarchyBranch> branches) {
        Map<String, PathwayBrowserNode> nodes = new HashMap<>();

        Set<PathwayBrowserNode> rtn = new HashSet<>();
        for (HierarchyBranch branch : branches) {
            List<HierarchyTreeItem> pathways = branch.getPathways();
            HierarchyTreeItem p = pathways.get(0);
            PathwayBrowserNode node = nodes.getOrDefault(p.getStId(), createRootNode(p));
            nodes.put(node.getStId(), node);
            rtn.add(node);
            for (int i = 1; i < pathways.size(); i++) {
                HierarchyTreeItem pathway = pathways.get(i);
                PathwayBrowserNode child = nodes.getOrDefault(pathway.getStId(), createRootNode(pathway));
                nodes.put(child.getStId(), child);
                node.addChild(child);
                child.addParent(node);
                node = child;
            }
        }
        return rtn;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private PathwayBrowserNode addNode(PathwayBrowserNode previous, Map<String, PathwayBrowserNode> nodes, HierarchyTreeItem result, Boolean omitNonDisplayableItems) {
        PathwayBrowserNode node;

        if (nodes.containsKey(result.getStId())) {
            node = nodes.get(result.getStId());
        } else {
            node = createNode(result);
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
     * @param result, the query result
     */
    @SuppressWarnings("unchecked")
    private PathwayBrowserNode createNode(HierarchyTreeItem result) {
        PathwayBrowserNode node = new PathwayBrowserNode();
        node.setStId(result.getStId());
        node.setName(result.getDisplayName());
        node.setDiagram(result.isHasDiagram());
        node.setSpecies(result.getSpeciesName());
        node.setType(result.getSchemaClass());
        node.setOrder(result.getOrder());

        doHighlighting(node);

        return node;
    }

    private PathwayBrowserNode createRootNode(HierarchyTreeItem hierarchyTreeItem) {
        PathwayBrowserNode node = new PathwayBrowserNode();
        node.setStId(hierarchyTreeItem.getStId());
        node.setName(hierarchyTreeItem.getDisplayName());
        // do not use SchemaClass here
        // node.setType(hierarchyTreeItem.getClass().getSimpleName());
        node.setType(hierarchyTreeItem.getSchemaClass());
        // Root by default is clickable and highlighted
        node.setClickable(true);
        node.setHighlighted(true);

        if (hierarchyTreeItem.getLabels().contains("Event")) {
            node.setSpecies(hierarchyTreeItem.getSpeciesName());
            if (hierarchyTreeItem.getLabels().contains("Pathway")) {
                node.setDiagram(hierarchyTreeItem.isHasDiagram());
            }
        } else if (hierarchyTreeItem.getLabels().contains("PhysicalEntity")) {
            node.setSpecies(hierarchyTreeItem.getSpeciesName());
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
        //language=Cypher
        String query = "" +
                "MATCH (n:DatabaseObject{dbId:$dbId}) " +
                "OPTIONAL MATCH path=(n)-[:hasEvent|input|output|repeatedUnit|hasMember|hasCandidate|hasComponent*]->(m:DatabaseObject) " +
                "WITH *, relationships(path) AS r " +
                "RETURN [n.stId, n.displayName, n.hasDiagram, n.speciesName, n.schemaClass, labels(n), 0] AS db, " +
                "collect( [rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, endNode(rel).speciesName, endNode(rel).schemaClass, labels(endNode(rel)), rel.order ]] ) AS nodePairCollection";

        return queryHierarchyWrapper(query, Collections.singletonMap("dbId", dbId));
    }

    private Collection<HierarchyWrapper> getSubHierarchyByStIdRaw(String stId) {
        //language=Cypher
        String query = "" +
                "MATCH (n:DatabaseObject{stId:$stId}) " +
                "OPTIONAL MATCH path=(n)-[:hasEvent|input|output|repeatedUnit|hasMember|hasCandidate|hasComponent*]->(m:DatabaseObject) " +
                "WITH *, relationships(path) AS r " +
                "RETURN [n.stId, n.displayName, n.hasDiagram, n.speciesName, n.schemaClass, labels(n), 0] AS db, " +
                "collect( [rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, endNode(rel).speciesName, endNode(rel).schemaClass, labels(endNode(rel)), rel.order ]] ) AS nodePairCollection";

        return queryHierarchyWrapper(query, Collections.singletonMap("stId", stId));
    }

    // ------------------------------------------- Event Hierarchy -----------------------------------------------------

    private Collection<HierarchyWrapper> getEventHierarchyBySpeciesNameRaw(String speciesName) {
        //language=Cypher
        String query = "" +
                "MATCH path=(n:TopLevelPathway{speciesName:$speciesName})-[:hasEvent*]->(m:Event) " +
                "WITH *, relationships(path) AS r " +
                "RETURN [n.stId, n.displayName, n.hasDiagram, n.speciesName, n.schemaClass, labels(n), 0] AS db, " +
                "collect ( [rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, endNode(rel).speciesName, endNode(rel).schemaClass, labels(endNode(rel)), rel.order ]] ) AS nodePairCollection";

        return queryHierarchyWrapper(query, Collections.singletonMap("speciesName", speciesName));
    }

    private Collection<HierarchyWrapper> getEventHierarchyByTaxIdRaw(String taxId) {
        //language=Cypher
        String query = "" +
                "MATCH (s:Species{taxId:$taxId})<-[:species]-(n:TopLevelPathway)" +
                "MATCH path=(n)-[:hasEvent*]->(m:Event) " +
                "WITH *, relationships(path) AS r " +
                "RETURN [n.stId, n.displayName, n.hasDiagram, n.speciesName, n.schemaClass, labels(n), 0] AS db, " +
                "collect( [rel IN r | [endNode(rel).stId, endNode(rel).displayName, endNode(rel).hasDiagram, endNode(rel).speciesName, endNode(rel).schemaClass, labels(endNode(rel)), rel.order ]] ) AS nodePairCollection";

        return queryHierarchyWrapper(query, Collections.singletonMap("taxId", taxId));
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
                "RETURN [n.stId, n.displayName, n.hasDiagram, n.speciesName, n.schemaClass, labels(n), 0] AS db, " +
                "collect( [rel IN relationships(path)| [startNode(rel).stId, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, startNode(rel).schemaClass, labels(endNode(rel)), rel.order ]] ) AS nodePairCollection";

        return queryHierarchyWrapper(query, Collections.singletonMap("stId", stId));
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
                "RETURN [n.stId, n.displayName, n.hasDiagram, n.speciesName, n.schemaClass, labels(n), 0] AS db, " +
                "collect( [rel IN relationships(path)| [startNode(rel).stId, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, startNode(rel).schemaClass, labels(endNode(rel)), rel.order ]] ) AS nodePairCollection";

        return queryHierarchyWrapper(query, Collections.singletonMap("dbId", dbId));
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
                "RETURN [n.stId, n.displayName, n.hasDiagram, n.speciesName, n.schemaClass, labels(n), 0] AS db, " +
                "collect( [rel IN relationships(path) | [startNode(rel).stId, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, startNode(rel).schemaClass, labels(endNode(rel)), rel.order ]] ) AS nodePairCollection";

        return queryHierarchyWrapper(query, Collections.singletonMap("stId", stId));
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
                "RETURN [n.stId, n.displayName, n.hasDiagram, n.speciesName, n.schemaClass, labels(n), 0] AS db, " +
                "collect( [rel IN relationships(path) | [startNode(rel).stId, startNode(rel).displayName, startNode(rel).hasDiagram,startNode(rel).speciesName, startNode(rel).schemaClass, labels(endNode(rel)), rel.order ]] ) AS nodePairCollection";

        return queryHierarchyWrapper(query, Collections.singletonMap("dbId", dbId));
    }

    public Collection<HierarchyBranch> getLocationInPathwayBrowserForPathwaysRaw(List<?> pathways) {
        //language=Cypher
        String query = " " +
                "MATCH (p:Pathway) " +
                "WHERE p.dbId IN $dbIds OR p.stId IN $stIds " +
                "OPTIONAL MATCH path=(:TopLevelPathway)-[:hasEvent*]->(p) " +
                "WITH p, nodes(path) AS branch " +
                "RETURN " +
                "   CASE WHEN size(branch) > 0 " +
                "   THEN [ na IN branch | [ na.stId, na.displayName, na.hasDiagram, na.speciesName, na.schemaClass, labels(na), 0 ] ] " +
                "   ELSE [ [ p.stId, p.displayName, p.hasDiagram, p.speciesName, p.schemaClass, labels(p), 0 ] ]  " +
                "END AS branch";

        List<String> stIds = new ArrayList<>();
        List<Long> dbIds = new ArrayList<>();
        for (Object aux : pathways) {
            if (aux instanceof DatabaseObject) {
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

        return neo4jClient.query(query)
                .bindAll(Map.of("stIds", stIds, "dbIds", dbIds))
                .fetchAs(HierarchyBranch.class)
                .mappedBy((typeSystem, record) -> getHierarchyBranch(record)).all();

    }

    private HierarchyBranch getHierarchyBranch(Record record) {
        List<HierarchyTreeItem> events = new ArrayList<>();
        for (org.neo4j.driver.Value resultSet : record.values()) {
            for (org.neo4j.driver.Value item : resultSet.values()) {
                events.add(HierarchyTreeItem.build(item));
            }
        }
        return new HierarchyBranch(events);
    }

    private Collection<HierarchyWrapper> queryHierarchyWrapper(String query, Map<String, Object> param) {
        return neo4jClient.query(query)
                .bindAll(param)
                .fetchAs(HierarchyWrapper.class)
                .mappedBy((typeSystem, record) -> {
                    Iterator<Value> nodePairCollection = record.get("nodePairCollection").values().iterator();
                    Collection<Collection<HierarchyTreeItem>> nodeResults = new ArrayList<>();
                    while (nodePairCollection.hasNext()) {
                        Iterator<Value> nodePair = nodePairCollection.next().values().iterator();
                        Collection<HierarchyTreeItem> innerCollection = new ArrayList<>();
                        while (nodePair.hasNext()) {
                            innerCollection.add(HierarchyTreeItem.build(nodePair.next()));
                        }
//                        nodeResults.add(innerCollection.stream().sorted(Comparator.comparingInt(HierarchyTreeItem::getOrder)).collect(Collectors.toList()));
                        nodeResults.add(innerCollection);
                    }
                    return new HierarchyWrapper(HierarchyTreeItem.build(record.get("db")), nodeResults);
                }).all();
    }
}
