package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.repository.PhysicalEntityRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 28.02.16.
 */
@Service
@SuppressWarnings("WeakerAccess")
public class PhysicalEntityService {

    @Autowired
    private PhysicalEntityRepository physicalEntityRepository;

    public Collection<PhysicalEntity> getOtherFormsOf(Object identifier) {

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return physicalEntityRepository.getOtherFormsOf(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return physicalEntityRepository.getOtherFormsOf(Long.parseLong(id));
        }
        return null;
    }
}
