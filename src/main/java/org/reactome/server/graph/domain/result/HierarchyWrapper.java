package org.reactome.server.graph.domain.result;

import java.util.Collection;

/**
 * HierarchyWrapper is projecting a giving DatabaseObject and its give Pathway Hierarchy Tree.
 * The reasons for a Collection of Collection is that the result comes as Path toward the TopLevelPathway
 * and many instances are present in more the one branch or a tree e.g. Proteins.
 */
public class HierarchyWrapper {

    private HierarchyTreeItem root;
    private Collection<Collection<HierarchyTreeItem>> nodes;

    public HierarchyWrapper(HierarchyTreeItem root, Collection<Collection<HierarchyTreeItem>> nodes) {
        this.root = root;
        this.nodes = nodes;
    }

    public HierarchyTreeItem getRoot() {
        return root;
    }

    public void setRoot(HierarchyTreeItem databaseObject) {
        this.root = root;
    }

    public Collection<Collection<HierarchyTreeItem>> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<Collection<HierarchyTreeItem>> nodes) {
        this.nodes = nodes;
    }
}

