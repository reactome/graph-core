package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.result.DoiPathwayDTO;
import org.reactome.server.graph.repository.DoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DoiService {

    private DoiRepository doiRepository;

    @Autowired
    public void setDoiRepository(DoiRepository doiRepository) {
        this.doiRepository = doiRepository;
    }

    /**
     * Retrieve all pathways which the doi value is not null, and all the authors, reviewers, editors are
     * related to this pathway, sub pathway and reactions.
     */
    public Collection<DoiPathwayDTO> getAllDoiPathway() {
        return doiRepository.doiPathwayDTO();
    }

}
