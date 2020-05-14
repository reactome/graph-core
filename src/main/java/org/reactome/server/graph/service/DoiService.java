package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.result.PathwayResult;
import org.reactome.server.graph.repository.DoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class DoiService {

    private DoiRepository doiRepository;

    @Autowired
    public void setDoiRepository(DoiRepository doiRepository) {
        this.doiRepository = doiRepository;
    }

    public Collection<PathwayResult> getAllDoiPathway() {
        Collection<PathwayResult> rtn;
        try {
            rtn = doiRepository.getDoiPathways();
        } catch (Exception e) {
            rtn = new ArrayList<>();
        }
        return rtn;
    }
}
