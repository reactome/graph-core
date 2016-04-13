package org.reactome.server.tools.service.util;

import org.reactome.server.tools.service.helper.PBNode;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 13.04.16.
 */
public abstract class PathwayBrowserLocationsUtils {

    private static final String PATHWAY_BROWSER_URL = "/PathwayBrowser/#/";
    private static final String SEL = "&amp;SEL=";
    private static final String PATH = "&amp;PATH=";

    public static Set<PBNode> buildTreesFromLeaves(Set<PBNode> leaves) {
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
    private static PBNode getTreeFromGraphLeaf(PBNode leaf, String sel, String path, String shortPath, String lastNodeWithDiagram) {
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
}
