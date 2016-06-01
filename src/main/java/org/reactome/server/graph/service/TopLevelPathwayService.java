package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.TopLevelPathway;
import org.reactome.server.graph.repository.TopLevelPathwayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 18.05.16.
 */
@Service
public class TopLevelPathwayService {

    @Autowired
    public TopLevelPathwayRepository topLevelPathwayRepository;

    // Gets top level pathways

    public Collection<TopLevelPathway> getTopLevelPathways() {
        return topLevelPathwayRepository.getTopLevelPathways();
    }

    public Collection<TopLevelPathway> getTopLevelPathwaysById(Long speciesId) {
        return topLevelPathwayRepository.getTopLevelPathways(speciesId);
    }

    public Collection<TopLevelPathway> getTopLevelPathwaysByName(String speciesName) {
        return topLevelPathwayRepository.getTopLevelPathways(speciesName);
    }

    public Collection<TopLevelPathway> getTopLevelPathwaysByTaxId(String taxId) {
        return topLevelPathwayRepository.getTopLevelPathwaysByTaxId(taxId);
    }

    public Collection<TopLevelPathway> getCuratedTopLevelPathways() {
        return topLevelPathwayRepository.getCuratedTopLevelPathways();
    }

    public Collection<TopLevelPathway> getCuratedTopLevelPathwaysById(Long speciesId) {
        return topLevelPathwayRepository.getCuratedTopLevelPathways(speciesId);
    }

    public Collection<TopLevelPathway> getCuratedTopLevelPathwaysByName(String speciesName) {
        return topLevelPathwayRepository.getCuratedTopLevelPathways(speciesName);
    }

    public Collection<TopLevelPathway> getCuratedTopLevelPathwaysByTaxId(String taxId){
        return topLevelPathwayRepository.getCuratedTopLevelPathwaysByTaxId(taxId);
    }
}
