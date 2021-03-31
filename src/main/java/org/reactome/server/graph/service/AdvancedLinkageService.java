//package org.reactome.server.graph.service;
//
//import org.reactome.server.graph.domain.result.ComponentOf;
//import org.reactome.server.graph.domain.result.Referrals;
//import org.reactome.server.graph.repository.AdvancedLinkageRepository;
//import org.reactome.server.graph.service.util.DatabaseObjectUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//
///**
// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
// * @author Antonio Fabregat (fabregat@ebi.ac.uk)
// */
//@SuppressWarnings("WeakerAccess")
//@Service
//public class AdvancedLinkageService {
//
//    @Autowired
//    private AdvancedLinkageRepository advancedLinkageRepository;
//
//    public Collection<ComponentOf> getComponentsOf(Object identifier) {
//        String id = DatabaseObjectUtils.getIdentifier(identifier);
//        if (DatabaseObjectUtils.isStId(id)) {
//            return advancedLinkageRepository.getComponentsOf(id);
//        } else if (DatabaseObjectUtils.isDbId(id)) {
//            return advancedLinkageRepository.getComponentsOf(Long.parseLong(id));
//        }
//        return null;
//    }
//
//    public Collection<Referrals> getReferralsTo(Object identifier){
//        String id = DatabaseObjectUtils.getIdentifier(identifier);
//        if (DatabaseObjectUtils.isStId(id)) {
//            return advancedLinkageRepository.getReferralsTo(id);
//        } else if (DatabaseObjectUtils.isDbId(id)) {
//            return advancedLinkageRepository.getReferralsTo(Long.parseLong(id));
//        }
//        return null;
//    }
//}
