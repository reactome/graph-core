package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.result.TocPathwayDTO;
import org.reactome.server.graph.repository.TocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TocService {

    private TocRepository tocRepository;

    @Autowired
    public void setTocRepository(TocRepository tocRepository) {
        this.tocRepository = tocRepository;
    }

    /**
     * Retrieve all top level pathways and the first hierarchy sub pathways, fetch all the authors, reviewers, editors are
     * related to this pathway, sub pathway and reactions.
     * Note: authors contains revised person
     */
    public Collection<TocPathwayDTO> getAllTocPathway() {
        return tocRepository.getTocPathways();
    }
}
