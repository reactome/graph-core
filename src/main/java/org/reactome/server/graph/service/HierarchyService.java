package org.reactome.server.graph.service;

import org.reactome.server.graph.repository.HierarchyRepository;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 31.05.16.
 */
@Service
@SuppressWarnings("WeakerAccess")
public class HierarchyService {

    @Autowired
    private HierarchyRepository hierarchyRepository;

    // -------------------------------- Locations in the Pathway Browser -----------------------------------------------

    public PathwayBrowserNode getLocationsInPathwayBrowser(Object identifier, boolean showDirectParticipants) {

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            if (showDirectParticipants) {
                return hierarchyRepository.getLocationsInPathwayBrowserDirectParticipants(id);
            }
            return hierarchyRepository.getLocationsInPathwayBrowser(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            if (showDirectParticipants) {
                return hierarchyRepository.getLocationsInPathwayBrowserDirectParticipants(Long.parseLong(id));
            }
            return hierarchyRepository.getLocationsInPathwayBrowser(Long.parseLong(id));
        }
        return null;
    }

    // --------------------------------------------- Sub Hierarchy -----------------------------------------------------


    public PathwayBrowserNode getSubHierarchy(Object identifier) {

        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            return hierarchyRepository.getSubHierarchy(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return hierarchyRepository.getSubHierarchy(Long.parseLong(id));
        }
        return null;
    }

    // ------------------------------------------- Event Hierarchy -----------------------------------------------------

    public Collection<PathwayBrowserNode> getEventHierarchyBySpeciesName(String speciesName) {
        return hierarchyRepository.getEventHierarchyBySpeciesName(speciesName);
    }

    public Collection<PathwayBrowserNode> getEventHierarchyByTaxId(String taxId) {
        return hierarchyRepository.getEventHierarchyByTaxId(taxId);
    }

    public Collection<PathwayBrowserNode> getEventHierarchyByDbId(Long dbId) {
        return hierarchyRepository.getEventHierarchyByDbId(dbId);
    }
}
