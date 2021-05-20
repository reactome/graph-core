package org.reactome.server.graph.domain.result;

import java.util.List;

public class HierarchyBranch {

//    List<EventProjection> branch;
//
//    public List<EventProjection> getPathways(){
//        return branch;
//    }
//
//    public HierarchyBranch(List<EventProjection> branch) {
//        this.branch = branch;
//    }

    List<HierarchyTreeItem> branch;

    public List<HierarchyTreeItem> getPathways(){
        return branch;
    }

    public HierarchyBranch(List<HierarchyTreeItem> branch) {
        this.branch = branch;
    }

}
