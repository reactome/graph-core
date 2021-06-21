package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.result.DiagramOccurrences;
import org.reactome.server.graph.domain.result.DiagramResult;
import org.reactome.server.graph.repository.DiagramRepository;
import org.reactome.server.graph.service.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 *
 */
@Service
public class DiagramService {

    private final DiagramRepository diagramRepository;

    @Autowired
    public DiagramService(DiagramRepository diagramRepository) {
        this.diagramRepository = diagramRepository;
    }

    public DiagramResult getDiagramResult(Object identifier) {
        return ServiceUtils.fetchById(identifier, diagramRepository::getDiagramResult, diagramRepository::getDiagramResult);
    }

    public Collection<DiagramOccurrences> getDiagramOccurrences(Object identifier) {
        return ServiceUtils.fetchById(identifier, diagramRepository::getDiagramOccurrences, diagramRepository::getDiagramOccurrences);
    }
}
