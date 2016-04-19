package org.reactome.server.tools.service;

import org.reactome.server.tools.domain.model.ReferenceEntity;
import org.reactome.server.tools.domain.model.Species;
import org.reactome.server.tools.domain.result.ComponentOf;
import org.reactome.server.tools.domain.result.SchemaClassCount;
import org.reactome.server.tools.repository.ObjectRepository;
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
public class ObjectService {

    @Autowired
    private ObjectRepository objectRepository;


    public Collection<SchemaClassCount> getLabelsCount() {
        return objectRepository.getSchemaClassCounts();
    }

    public Collection<Species> getAllSpecies() {
        return objectRepository.getAllSpecies();
    }

    public Collection<ReferenceEntity> getAllChemicals() {
        return objectRepository.getAllChemicals();
    }

    public Collection<ComponentOf> getComponentsOf(String stableIdentifier) {
        return objectRepository.getComponentsOf(stableIdentifier);
    }
}
