package org.reactome.server.graph.service;

import org.apache.commons.lang3.StringUtils;
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
@SuppressWarnings("WeakerAccess")
public class TopLevelPathwayService {

    public final TopLevelPathwayRepository topLevelPathwayRepository;

    @Autowired
    public TopLevelPathwayService(TopLevelPathwayRepository topLevelPathwayRepository) {
        this.topLevelPathwayRepository = topLevelPathwayRepository;
    }

    public Collection<TopLevelPathway> getTopLevelPathways() {
        return topLevelPathwayRepository.getTopLevelPathways();
    }

    public Collection<TopLevelPathway> getTopLevelPathways(Object species) {
        String speciesString = species.toString();
        if (StringUtils.isNumeric(speciesString)) {
            return topLevelPathwayRepository.getTopLevelPathwaysByTaxId(speciesString);
        } else {
            return topLevelPathwayRepository.getTopLevelPathwaysByName(speciesString);
        }
    }

    public Collection<TopLevelPathway> getCuratedTopLevelPathways() {
        return topLevelPathwayRepository.getCuratedTopLevelPathways();
    }

    public Collection<TopLevelPathway> getCuratedTopLevelPathways(Object species) {
        String speciesString = species.toString();
        if (StringUtils.isNumeric(speciesString)) {
            return topLevelPathwayRepository.getCuratedTopLevelPathwaysByTaxId(speciesString);
        } else {
            return topLevelPathwayRepository.getCuratedTopLevelPathwaysByName(speciesString);
        }
    }
}
