package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.result.PathwayResult;
import org.reactome.server.graph.repository.TocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class TocService {

    private TocRepository tocRepository;

    @Autowired
    public void setTocRepository(TocRepository tocRepository) {
        this.tocRepository = tocRepository;
    }

    public Collection<PathwayResult> getAllTocPathways() {
        Collection<PathwayResult> rtn;
        try {
            rtn = tocRepository.getTocPathways();
        } catch (Exception e) {
            rtn = new ArrayList<>();
        }
        return rtn;
    }
}
