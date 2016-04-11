package org.reactome.server.tools.service;

import org.neo4j.ogm.model.Result;
import org.reactome.server.tools.domain.model.DatabaseObject;
import org.reactome.server.tools.domain.model.Pathway;
import org.reactome.server.tools.domain.model.Species;
import org.reactome.server.tools.repository.GenericRepository;
import org.reactome.server.tools.service.helper.PBNode;
import org.reactome.server.tools.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@Service
public class GenericServiceImpl implements GenericService {

//    private static final Logger logger = LoggerFactory.getLogger(GenericServiceImpl.class);

    @Autowired
    private GenericRepository genericRepository;

    @Override
    public Object findByPropertyWithRelations (String property, Object value, String... relationships){
        return genericRepository.findByPropertyWithRelations(property, value, relationships);
    }

    @Override
    public Object findByPropertyWithoutRelations (String property, Object value, String... relationships){
        return genericRepository.findByPropertyWithoutRelations(property, value, relationships);
    }

    //    TODO fix warning
    @SuppressWarnings("unchecked")
    @Override
    public <T>Collection<T> getObjectsByClassName(String className, Integer page, Integer offset) throws ClassNotFoundException {
        return genericRepository.getObjectsByClassName(DatabaseObjectUtils.getClassForName(className),page,offset);
    }

    @Override
    public <T>T findByProperty(Class<T> clazz, String property, Object value, Integer depth){
        return genericRepository.findByProperty(clazz, property, value, depth);
    }

    @Override
    public <T> T findById(Class<T> clazz, Long id, Integer depth){
        return genericRepository.findById(clazz, id, depth);
    }

    @Override
    public <T>T findByDbId(Class<T> clazz, Long dbId, Integer depth) {
        return genericRepository.findByDbId(clazz, dbId, depth);
    }

    @Override
    public <T>T  findByStableIdentifier(Class<T> clazz, String stableIdentifier, Integer depth) {
        return genericRepository.findByStableIdentifier(clazz, stableIdentifier, depth);
    }

    @Override
    public Collection<Pathway> getTopLevelPathways() {
        return genericRepository.getTopLevelPathways();
    }

    @Override
    public Collection<Pathway> getTopLevelPathways(Long speciesId) {
        return genericRepository.getTopLevelPathways(speciesId);
    }

    @Override
    public Collection<Pathway> getTopLevelPathways(String speciesName) {
        return genericRepository.getTopLevelPathways(speciesName);
    }

    @Override
    public Pathway getEventHierarchy(Long dbId) {
        return genericRepository.getEventHierarchy(dbId);
    }


    public DatabaseObject getLocationsHierarchy(String stId){
        return genericRepository.getLocationsHierarchy(stId);
    }




    public DatabaseObject getReferral(Long dbId, String relationshipName) {
        return genericRepository.getReferral(dbId,relationshipName);
    }

    public Collection<DatabaseObject> getReferrals(Long dbId, String relationshipName){
        return genericRepository.getReferrals(dbId, relationshipName);
    }


    public void tree() {
//        Collection<DatabaseObject> eventHierarchy = genericRepository.getReferrals(445133L,"hasMember");
        genericRepository.getLocationsHierarchy();
    }

    private Map<String,PBNode> nodes = new HashMap<>();

    @Override
    public void getLocationsHierarchy()  {
        Result result = genericRepository.getLocationsHierarchy();
        PBNode root = new PBNode();
        root.setName("PTCH1");
        root.setStId("R-HSA-445133");
        root.setSpecies("Homo Sapiens");
        root.setType("EntityWithAccessionedSequence");
        PBNode previous = root;
        nodes.put(root.getStId(),root);
        int previousSize = 0;
        for (Map<String, Object> stringObjectMap : result) {
            ArrayList<Object>[] xx = ((ArrayList<Object>[])stringObjectMap.get("nodePairCollection"));
            int size = xx.length;
            if (size>=previousSize) {
                ArrayList<Object> objects = xx[xx.length-1];
                PBNode node;
                if (nodes.containsKey(objects.get(0))) {
                    node = nodes.get(objects.get(0));
                } else {
                    node = createNode(objects);
                    nodes.put(node.getStId(),node);
                }

                previous.addChild(node);
                node.addParent(previous);
                previous = node;


            } else {
                previous = root;
                for (ArrayList<Object> objects : xx) {

                    PBNode node;
                    if (nodes.containsKey(objects.get(0))) {
                        node = nodes.get(objects.get(0));
                    } else {
                        node = createNode(objects);
                        nodes.put(node.getStId(),node);
                    }

                    if (previous.getChildren().contains(node)) {
                        for (PBNode pbNode : previous.getChildren()) {
                            if (pbNode.getStId().equals(node.getStId())) {
                                previous = pbNode;
                            }
                        }
                    } else {
                        previous.addChild(node);
                        node.addParent(previous);
                        previous = node;
                    }
                }
                System.out.println();
            }
            previousSize = size;

        }
        Set<PBNode> leaves = root.getLeaves();
        Set<PBNode> bla = buildTreesFromLeaves(leaves);
        System.out.println();
    }

    private Set<PBNode> buildTreesFromLeaves(Set<PBNode> leaves) {
        Set<PBNode> topLvlTrees = new TreeSet<>();
        for (PBNode leaf : leaves) {
            PBNode tree = getTreeFromGraphLeaf(leaf, "", "", "", "");
            if (tree != null) {
                topLvlTrees.add(tree);
            } else {
//                logger.error("Could no process tree for " + leaf.getName());
            }
        }

        return topLvlTrees;
    }

    private static final String PATHWAY_BROWSER_URL = "/PathwayBrowser/#/";
    private static final String SEL = "&amp;SEL=";
    private static final String PATH = "&amp;PATH=";

    /**
     * Generating individual Trees from a leaf
     * Url linking to the Pathway browser will be set
     * URL consists of 3 Attributes PATH, SEL, MAIN
     * MAIN = main URL parameter (required)
     *
     * @param leaf                of the Graph represent the TopLevelPathways in Reactome
     * @param sel                 URL parameter to select Reactions or Physical Entities (optional)
     * @param path                URL parameter to identify a unique "Path" to this entry
     * @param shortPath           URL parameter to identify a unique "Path" to this entry
     * @param lastNodeWithDiagram saves STID of the Last Pathway in the Diagram
     * @return generated Tree
     */
    private PBNode getTreeFromGraphLeaf(PBNode leaf, String sel, String path, String shortPath, String lastNodeWithDiagram) {
        /* */
        PBNode tree = new PBNode();
        tree.setStId(leaf.getStId());
        tree.setName(leaf.getName());
        tree.setSpecies(leaf.getSpecies());
        tree.setType(leaf.getType());

        boolean isPathway = leaf.getType().equals("Pathway");
        boolean hasDiagram = leaf.hasDiagram();
        leaf.setUnique(false);

        /*Setting main Url attributes*/
        String main;
        if (isPathway) {
            main = leaf.getStId();
        } else {
            sel = leaf.getStId();
            main = lastNodeWithDiagram;
        }

        /*Check if Pathway is a unique pathway*/
        Set<PBNode> children = leaf.getChildren();
        if (isPathway) {
            if (children == null) {
                leaf.setUnique(true);
            } else if (children.size() == 1) {
                if (children.iterator().next().isUnique()) {
                    leaf.setUnique(true);
                }
            }
        }

        /*Building the Url for the current entry*/
        StringBuilder url = new StringBuilder();
        url.append(PATHWAY_BROWSER_URL);
        if (leaf.isUnique()) {
            url.append(leaf.getStId());
        } else {
            url.append(main);
            if (!sel.isEmpty()) {
                url.append(SEL);
                url.append(sel);
            }

            if (isPathway) {
                if (!path.isEmpty()) {
                    url.append(PATH);
                    url.append(path);
                } else {
                    url.append(path);
                }
            } else {
                if (!shortPath.isEmpty()) {
                    url.append(PATH);
                    url.append(shortPath);
                } else {
                    url.append(shortPath);
                }
            }
        }
        tree.setUrl(url.toString());

        /*Building Path for next entry*/
        if (isPathway) {
            if (hasDiagram) {
                if (shortPath.isEmpty()) {
                    shortPath += lastNodeWithDiagram;
                } else {
                    shortPath += "," + lastNodeWithDiagram;
                }
            } else {
                if (path.isEmpty()) {
                    path += leaf.getStId();
                } else {
                    path += "," + leaf.getStId();
                }
            }
        }
        if (hasDiagram) {
            lastNodeWithDiagram = leaf.getStId();
        }

        /*Continue in the recursion */
        Set<PBNode> parents = leaf.getParent();
        if (parents != null) {
            for (PBNode node : parents) {
                tree.addChild(getTreeFromGraphLeaf(node, sel, path, shortPath, lastNodeWithDiagram));
            }
        }
        return tree;
    }

    //    private void
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
                node.setType("Pathway");
            }

        }
        node.setType(lowestClass.getSimpleName());
        return node;
    }


    @Override
    public Collection<Species> getSpecies() {
        return genericRepository.getSpecies();
    }

    @Override
    public Result query (String query, Map<String,Object> map) {
        return genericRepository.query(query,map);
    }

    @Override
    public Long countEntries(Class<?> clazz){
        return genericRepository.countEntries(clazz);
    }

    @Override
    public void clearCache() {
        genericRepository.clearCache();
    }

    @Override
    public boolean fitForService()  {
        return genericRepository.fitForService();
    }
}
