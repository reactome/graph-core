//package org.reactome.server.graph.service;
//
//import org.reactome.server.graph.domain.model.Interaction;
//import org.reactome.server.graph.domain.model.Pathway;
//import org.reactome.server.graph.domain.result.ClassCount;
//import org.reactome.server.graph.domain.result.DiagramOccurrences;
//import org.reactome.server.graph.repository.InteractionsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
///**
// * @author Antonio Fabregat <fabregat@ebi.ac.uk>
// */
//@Service
//public class InteractionsService {
//
//    @Autowired
//    private InteractionsRepository interactionsRepository;
//
//    /**
//     * Get all interactions of a given accession and resource
//     * @return Map of accession as key and its interactions
//     */
//    public List<Interaction> getInteractions(String acc) {
//        return getInteractions(Collections.singletonList(acc), -1, -1).get(acc);
//    }
//
//    /**
//     * Get all interactions of a given accession and resource
//     *
//     * @return Map of accession as key and its interactions
//     */
//    public Map<String, List<Interaction>> getInteractions(Collection<String> accs) {
//        return getInteractions(accs, -1, -1);
//    }
//
//    /**
//     * Get paginated interactions of a given accession and resource
//     * @return Map of accession as key and its interactions
//     */
//    public List<Interaction> getInteractions(String acc, Integer page, Integer pageSize) {
//        return getInteractions(Collections.singletonList(acc), page, pageSize).get(acc);
//    }
//
//    /**
//     * Get interactions of a given list of accession and resource
//     * @return Map of accession as key and its interactions
//     */
//    public Map<String, List<Interaction>> getInteractions(Collection<String> accs, Integer page, Integer pageSize) {
//        Map<String, List<Interaction>> interactionMaps = new HashMap<>();
//        for (String acc : accs) {
//            Collection<Interaction> interactions;
//            if(page != -1 && pageSize != -1) {
//                interactions = interactionsRepository.getByAcc(acc, page, pageSize);
//            } else {
//                interactions = interactionsRepository.getByAcc(acc);
//            }
//            interactionMaps.put(acc, new ArrayList<>(interactions));
//        }
//        return interactionMaps;
//    }
//
//    /**
//     * Count interaction by accession
//     * @return Map of accession and count
//     */
//    public Integer countInteractionsByAccession(String acc) {
//        return countInteractionsByAccessions(Collections.singletonList(acc)).get(acc);
//    }
//
//    /**
//     * Count interaction by accession list
//     * @return Map of accession and count
//     */
//    public Map<String, Integer> countInteractionsByAccessions(Collection<String> accs) {
//        Map<String, Integer> rtn = new HashMap<>();
//        for (ClassCount<String, Integer> classCount : interactionsRepository.countByAccessions(accs)) {
//            rtn.put(classCount.s, classCount.t);
//        }
//        return rtn;
//    }
//
//    public Collection<Pathway> getLowerLevelPathways(String acc, String speciesName){
//        return interactionsRepository.getLowerLevelPathways(acc, speciesName);
//    }
//
//    public Collection<Pathway> getDiagrammedLowerLevelPathways(String acc, String speciesName){
//        return interactionsRepository.getDiagrammedLowerLevelPathways(acc, speciesName);
//    }
//
//    public Collection<DiagramOccurrences> getDiagramOccurrences(String identifier){
//        return interactionsRepository.getDiagramOccurrences(identifier);
//    }
//}
