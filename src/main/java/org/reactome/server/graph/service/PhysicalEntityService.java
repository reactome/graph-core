package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.repository.PhysicalEntityRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
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

    public Collection<PhysicalEntity> getComplexSubunits(Object identifier) {
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return physicalEntityRepository.getComplexSubunits(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return physicalEntityRepository.getComplexSubunits(Long.parseLong(id));
        }
        return null;
    }
}
