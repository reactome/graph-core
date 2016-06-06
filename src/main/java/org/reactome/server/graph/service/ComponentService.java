package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.result.ComponentOf;
import org.reactome.server.graph.repository.ComponentRepository;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 05.06.16.
 */
@Service
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    public Collection<ComponentOf> getComponentsOf(Object identifier) {

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return componentRepository.getComponentsOf(id);
        } else if (DatabaseObjectUtils.isDbId(id)) {
            return componentRepository.getComponentsOf(Long.parseLong(id));
        }
        return null;
    }
}
