package org.reactome.server.graph.repository.util;

import org.reactome.server.graph.domain.model.Pathway;

import java.util.List;

public class HierarchyBranch {

    List<Pathway> branch;

    public List<Pathway> getPathways(){
        return branch;
    }
}
