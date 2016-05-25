package org.reactome.server.graph.service.util;

import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.model.TopLevelPathway;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Please bear in mind here that we are building the Locations in the Pathway Browser tree from the LEAVES.
 * In the graph query we get the TREE from EWAS (root) to TOPLevelPathway (leaves). In this class we are
 * rotating the tree and building the links from Leaves to Root.
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.04.16.
 */
public abstract class PathwayBrowserLocationsUtils {

    private static final Logger logger = LoggerFactory.getLogger(PathwayBrowserLocationsUtils.class);

    private static final String PATHWAY_BROWSER_URL = "/PathwayBrowser/#/";
    private static final String SEL = "&amp;SEL=";
    private static final String PATH = "&amp;PATH=";

    public static Set<PathwayBrowserNode> buildTreesFromLeaves(Set<PathwayBrowserNode> leaves) {
        Set<PathwayBrowserNode> topLvlTrees = new TreeSet<>();
        for (PathwayBrowserNode leaf : leaves) {
            leaf.setClickable(true);
            PathwayBrowserNode tree = getTreeFromGraphLeaf(leaf, "", "", "", "");
            if (tree != null) {
                topLvlTrees.add(tree);
            } else {
                logger.error("Could no process tree for " + leaf.getName());
            }
        }

        return topLvlTrees;
    }

    public static Set<PathwayBrowserNode> removeOrphans(Set<PathwayBrowserNode> leaves) {
        Iterator<PathwayBrowserNode> it = leaves.iterator();
        while (it.hasNext()) {
            PathwayBrowserNode node = it.next();
            if (node.getType() == null || !node.getType().equals(TopLevelPathway.class.getSimpleName())) {
                it.remove();
            }
        }
        return leaves;
    }

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
    private static PathwayBrowserNode getTreeFromGraphLeaf(PathwayBrowserNode leaf, String sel, String path, String shortPath, String lastNodeWithDiagram) {
        /* */
        PathwayBrowserNode tree = new PathwayBrowserNode();
        tree.setStId(leaf.getStId());
        tree.setName(leaf.getName());
        tree.setSpecies(leaf.getSpecies());
        tree.setType(leaf.getType());
        tree.setClickable(leaf.isClickable());
        tree.setHighlighted(leaf.getHighlighted());

        boolean isPathway = leaf.getType().equals(Pathway.class.getSimpleName()) || leaf.getType().equals(TopLevelPathway.class.getSimpleName());
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
        Set<PathwayBrowserNode> children = leaf.getChildren();
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
        Set<PathwayBrowserNode> parents = leaf.getParent();
        if (parents != null) {
            for (PathwayBrowserNode node : parents) {
                tree.addChild(getTreeFromGraphLeaf(node, sel, path, shortPath, lastNodeWithDiagram));
            }
        }

        return tree;
    }
}
