package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.result.DiagramOccurrences;
import org.reactome.server.graph.domain.result.DiagramResult;
import org.reactome.server.graph.repository.DiagramRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**

 */
@Service
public class DiagramService {

    @Autowired
    private DiagramRepository diagramRepository;

    public DiagramResult getDiagramResult(Object identifier){
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return diagramRepository.getDiagramResult(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return diagramRepository.getDiagramResult(Long.parseLong(id));
        }
        return null;
    }

    public Collection<DiagramOccurrences> getDiagramOccurrences(Object identifier){
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return diagramRepository.getDiagramOccurrences(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return diagramRepository.getDiagramOccurrences(Long.parseLong(id));
        }
        return null;
    }
}
