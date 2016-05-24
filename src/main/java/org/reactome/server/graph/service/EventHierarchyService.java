package org.reactome.server.graph.service;

import org.reactome.server.graph.repository.EventHierarchyRepository;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by flo on 24/05/16.
 */
@Service
public class EventHierarchyService {

    @Autowired
    private EventHierarchyRepository eventHierarchyRepository;

    public Collection<PathwayBrowserNode> getEventHierarchy(String speciesName){
        return eventHierarchyRepository.getEventHierarchy(speciesName);
    }

}
