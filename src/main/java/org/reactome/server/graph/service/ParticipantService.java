package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.domain.model.ReferenceEntity;
import org.reactome.server.graph.domain.result.Participant;
import org.reactome.server.graph.repository.ParticipantRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
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
@SuppressWarnings("WeakerAccess")
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public Collection<ReferenceEntity> getParticipatingReferenceEntities(String identifier) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return participantRepository.getParticipatingReferenceEntities(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return participantRepository.getParticipatingReferenceEntities(Long.parseLong(id));
        }
        return null;
    }

    public Collection<PhysicalEntity> getParticipatingPhysicalEntities(String identifier) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return participantRepository.getParticipatingPhysicalEntities(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return participantRepository.getParticipatingPhysicalEntities(Long.parseLong(id));
        }
        return null;
    }

    public Collection<Participant> getParticipants(String identifier) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return participantRepository.getParticipants(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return participantRepository.getParticipants(Long.parseLong(id));
        }
        return null;
    }

}
