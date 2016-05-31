package org.reactome.server.graph.service;

import org.reactome.server.graph.repository.HierarchyRepository;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by flo on 24/05/16.
 */
@Service
public class HierarchyService {

    @Autowired
    private HierarchyRepository hierarchyRepository;

    public Collection<PathwayBrowserNode> getEventHierarchy(String speciesName){
        return hierarchyRepository.getEventHierarchy(speciesName);
    }



    public PathwayBrowserNode getSubHierarchy(String id) {
        return hierarchyRepository.getSubHierarchy(id);
    }
}
