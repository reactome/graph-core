package org.reactome.server.tools.service;

import org.reactome.server.tools.domain.model.PhysicalEntity;
import org.reactome.server.tools.domain.model.ReferenceEntity;
import org.reactome.server.tools.domain.result.Participant;
import org.reactome.server.tools.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.04.16.
 */
@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public Collection<ReferenceEntity> getParticipatingReferenceEntities(Long dbId) {
        return participantRepository.getParticipatingReferenceEntities(dbId);
    }

    public Collection<ReferenceEntity> getParticipatingReferenceEntities(String stableIdentifier) {
        return participantRepository.getParticipatingReferenceEntities(stableIdentifier);
    }

    public Collection<PhysicalEntity> getParticipatingPhysicalEntities(Long dbId) {
        return participantRepository.getParticipatingPhysicalEntities(dbId);
    }

    public Collection<PhysicalEntity> getParticipatingPhysicalEntities(String stableIdentifier) {
        return participantRepository.getParticipatingPhysicalEntities(stableIdentifier);
    }

    public Collection<Participant> getParticipants(Long dbId) {
        return participantRepository.getParticipants(dbId);
    }

    public Collection<Participant> getParticipants(String stableIdentifier) {
        return participantRepository.getParticipants(stableIdentifier);
    }
}
