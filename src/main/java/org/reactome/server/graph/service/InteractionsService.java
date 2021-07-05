package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Interaction;
import org.reactome.server.graph.domain.model.Pathway;
import org.reactome.server.graph.domain.result.DiagramOccurrences;
import org.reactome.server.graph.domain.result.InteractorsCount;
import org.reactome.server.graph.repository.DiagramRepository;
import org.reactome.server.graph.repository.InteractionsRepository;
import org.reactome.server.graph.repository.InteractorCountRepository;
import org.reactome.server.graph.repository.PathwayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InteractionsService {

    private final InteractionsRepository interactionsRepository;
    private final InteractorCountRepository interactorCountRepository;
    private final PathwayRepository pathwayRepository;
    private final DiagramRepository diagramRepository;

    @Autowired
    public InteractionsService(InteractionsRepository interactionsRepository, InteractorCountRepository interactorCountRepository, PathwayRepository pathwayRepository, DiagramRepository diagramRepository) {
        this.interactionsRepository = interactionsRepository;
        this.interactorCountRepository = interactorCountRepository;
        this.pathwayRepository = pathwayRepository;
        this.diagramRepository = diagramRepository;
    }

    /**
     * Get all interactions of a given accession and resource
     * @return Map of accession as key and its interactions
     */
    public List<Interaction> getInteractions(String acc) {
        return getInteractions(Collections.singletonList(acc), -1, -1).get(acc);
    }

    /**
     * Get all interactions of a given accession and resource
     * @return Map of accession as key and its interactions
     */
    public Map<String, List<Interaction>> getInteractions(Collection<String> accs) {
        return getInteractions(accs, -1, -1);
    }

    /**
     * Get paginated interactions of a given accession and resource
     * @return Map of accession as key and its interactions
     */
    public List<Interaction> getInteractions(String acc, Integer page, Integer pageSize) {
        return getInteractions(Collections.singletonList(acc), page, pageSize).get(acc);
    }

    /**
     * Get interactions of a given list of accession and resource
     * @return Map of accession as key and its interactions
     */
    public Map<String, List<Interaction>> getInteractions(Collection<String> accs, Integer page, Integer pageSize) {
        Map<String, List<Interaction>> interactionMaps = new HashMap<>();
        for (String acc : accs) {
            Collection<Interaction> interactions;
            if(page != -1 && pageSize != -1) {
                interactions = interactionsRepository.getByAcc(acc, page, pageSize);
            } else {
                interactions = interactionsRepository.getByAcc(acc);
            }
            interactionMaps.put(acc, new ArrayList<>(interactions));
        }
        return interactionMaps;
    }

    public Interaction getSingleInteractionDetails(String acc, String accB) {
        return interactionsRepository.getInteractorByAcc(acc, accB);
    }

    /**
     * Count interaction by accession
     * @return Map of accession and count
     */
    public Integer countInteractionsByAccession(String acc) {
        Map<String, Integer> map = countInteractionsByAccessions(Collections.singletonList(acc));
        if (acc.contains("-")) {
            // When the given accession is a ReferenceIsoform, the query retrieves the normal identifier
            // in which .get(acc) will retrieve null. To prevent that, we are getting the accession instead
            acc = acc.substring(0, acc.indexOf("-"));
        }
        return map.get(acc);
    }

    /**
     * Count interaction by accession list
     * @return Map of accession and count
     */
    public Map<String, Integer> countInteractionsByAccessions(Collection<String> accs) {
        Map<String, Integer> rtn = new HashMap<>();
        for (InteractorsCount interactorsCount : interactorCountRepository.countByAccessions(accs)) {
            rtn.put(interactorsCount.getAcc(), interactorsCount.getCount());
        }
        return rtn;
    }

    public Collection<Pathway> getLowerLevelPathways(String acc, String speciesName){
        return pathwayRepository.getLowerLevelPathways(acc, speciesName);
    }

    public Collection<Pathway> getDiagrammedLowerLevelPathways(String acc, String speciesName){
        return pathwayRepository.getDiagrammedLowerLevelPathways(acc, speciesName);
    }

    public Collection<DiagramOccurrences> getDiagramOccurrences(String identifier){
        return diagramRepository.getDiagramOccurrencesWithInteractions(identifier);
    }
}
