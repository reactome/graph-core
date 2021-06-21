package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.reactome.server.graph.domain.result.Participant;
import org.reactome.server.graph.repository.ParticipantRepository;
import org.reactome.server.graph.repository.PhysicalEntityRepository;
import org.reactome.server.graph.repository.ReferenceEntityRepository;
import org.reactome.server.graph.service.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ReferenceEntityRepository referenceEntityRepository;
    private final PhysicalEntityRepository physicalEntityRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, ReferenceEntityRepository referenceEntityRepository, PhysicalEntityRepository physicalEntityRepository) {
        this.participantRepository = participantRepository;
        this.referenceEntityRepository = referenceEntityRepository;
        this.physicalEntityRepository = physicalEntityRepository;
    }

    public Collection<ReferenceEntity> getParticipatingReferenceEntities(String identifier) {
        return ServiceUtils.fetchById(identifier, referenceEntityRepository::getParticipatingReferenceEntities, referenceEntityRepository::getParticipatingReferenceEntities);
    }

    public Collection<PhysicalEntity> getParticipatingPhysicalEntities(String identifier) {
        return ServiceUtils.fetchById(identifier, physicalEntityRepository::getParticipatingPhysicalEntities, physicalEntityRepository::getParticipatingPhysicalEntities);
    }

    public Collection<Participant> getParticipants(String identifier) {
        return ServiceUtils.fetchById(identifier, participantRepository::getParticipants, participantRepository::getParticipants);
    }

}
