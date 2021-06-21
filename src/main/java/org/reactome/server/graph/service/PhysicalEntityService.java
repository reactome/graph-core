package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.PhysicalEntity;
import org.reactome.server.graph.repository.PhysicalEntityRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.reactome.server.graph.service.util.ServiceUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)

 */
@Service
public class PhysicalEntityService {

    private final PhysicalEntityRepository physicalEntityRepository;

    public PhysicalEntityService(PhysicalEntityRepository physicalEntityRepository) {
        this.physicalEntityRepository = physicalEntityRepository;
    }

    public Collection<PhysicalEntity> getOtherFormsOf(Object identifier) {
        return ServiceUtils.fetchById(identifier, physicalEntityRepository::getOtherFormsOf, physicalEntityRepository::getOtherFormsOf);
    }

    public Collection<Complex> getComplexesFor(String identifier, String resource){
        return physicalEntityRepository.getComplexesFor(identifier, resource);
    }

    public Collection<PhysicalEntity> getPhysicalEntitySubunits(Object identifier) {
        return ServiceUtils.fetchById(identifier, physicalEntityRepository::getPhysicalEntitySubunits, physicalEntityRepository::getPhysicalEntitySubunits);
    }

    public Collection<PhysicalEntity> getPhysicalEntitySubunitsNoStructures(Object identifier) {
        return ServiceUtils.fetchById(identifier, physicalEntityRepository::getPhysicalEntitySubunitsNoStructures, physicalEntityRepository::getPhysicalEntitySubunitsNoStructures);
    }
}
