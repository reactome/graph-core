package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.result.ComponentOf;
import org.reactome.server.graph.domain.result.Referrals;
import org.reactome.server.graph.repository.ComponentOfLinkageRepository;
import org.reactome.server.graph.repository.ReferralsLinkageRepository;
import org.reactome.server.graph.service.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdvancedLinkageService {

    private final ComponentOfLinkageRepository componentOfLinkageRepository;
    private final ReferralsLinkageRepository referralsLinkageRepository;

    @Autowired
    public AdvancedLinkageService(ComponentOfLinkageRepository componentOfLinkageRepository, ReferralsLinkageRepository referralsLinkageRepository) {
        this.componentOfLinkageRepository = componentOfLinkageRepository;
        this.referralsLinkageRepository = referralsLinkageRepository;
    }

    public Collection<ComponentOf> getComponentsOf(Object identifier) {
        return ServiceUtils.fetchById(identifier, true, componentOfLinkageRepository::getComponentsOf, componentOfLinkageRepository::getComponentsOf);
    }

    public Collection<Referrals> getReferralsTo(Object identifier){
        return ServiceUtils.fetchById(identifier, true, referralsLinkageRepository::getReferralsTo, referralsLinkageRepository::getReferralsTo);
    }
}
