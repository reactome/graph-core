package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.repository.DetailsRepository;
import org.reactome.server.graph.service.helper.ContentDetails;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.reactome.server.graph.service.util.PathwayBrowserLocationsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 14.04.16.
 */
@Service
public class DetailsService {

    private static final Logger logger = LoggerFactory.getLogger(DetailsService.class);

    @Autowired
    private DetailsRepository detailsRepository;
    @Autowired
    private GeneralService generalService;
    @Autowired
    private PhysicalEntityService physicalEntityService;

//    Todo rename
    @Transactional
    public ContentDetails getContentDetails(String id, boolean interactors) {

        ContentDetails contentDetails = new ContentDetails();
        DatabaseObject databaseObject;
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            databaseObject = detailsRepository.detailsPageQuery(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            databaseObject = detailsRepository.detailsPageQuery(Long.parseLong(id));
        } else {
//            todo decide return empty or null or throw
            return null;
        }

        contentDetails.setDatabaseObject(databaseObject);
        if (databaseObject instanceof Event || databaseObject instanceof PhysicalEntity || databaseObject instanceof Regulation) {
            Set<PathwayBrowserNode> leaves = getLocationsInThePathwayBrowserHierarchy(databaseObject, interactors);
            leaves = PathwayBrowserLocationsUtils.removeOrphans(leaves);
            contentDetails.setLeaves(PathwayBrowserLocationsUtils.buildTreesFromLeaves(leaves));
            contentDetails.setComponentOf(generalService.getComponentsOf(databaseObject.getStId()));
            contentDetails.setOtherFormsOfThisMolecule(physicalEntityService.getOtherFormsOfThisMolecule(databaseObject.getDbId()));
        }
        return contentDetails;
    }


    public Set<PathwayBrowserNode> getLocationsInThePathwayBrowserHierarchy(DatabaseObject databaseObject, boolean interactors) {
        return getLocationsInThePathwayBrowser(databaseObject, interactors).getLeaves();
    }

    public Set<PathwayBrowserNode> getLocationsInThePathwayBrowserHierarchy(String id, boolean interactors) {
        return getLocationsInThePathwayBrowser(id, interactors).getLeaves();
    }






    public PathwayBrowserNode getLocationsInThePathwayBrowser(DatabaseObject databaseObject, boolean interactors) {
        if (databaseObject == null) return null;

        PathwayBrowserNode node;
        if (interactors) {
            node = detailsRepository.getLocationsInPathwayBrowserForInteractors(databaseObject);
        } else {
            node = detailsRepository.getLocationsInPathwayBrowser(databaseObject);
        }

        if (databaseObject instanceof Regulation) {
            DatabaseObject regulator = ((Regulation) databaseObject).getRegulator();
            node.setName(regulator.getDisplayName());
            node.setStId(regulator.getStId());
            node.setType(regulator.getSchemaClass());

            if (regulator instanceof Event) {
                Event event = (Event) regulator;
                node.setSpecies(event.getSpeciesName());
                if (event instanceof Pathway) {
                    Pathway pathway = (Pathway) event;
                    node.setDiagram(pathway.getHasDiagram());
                }
            } else if (regulator instanceof PhysicalEntity) {
                PhysicalEntity physicalEntity = (PhysicalEntity) regulator;
                node.setSpecies(physicalEntity.getSpeciesName());
            }
            else {
                logger.error("Regulator must be either an Event or PhysicalEntity");
            }
        }

        if (databaseObject instanceof CatalystActivity) {
            PhysicalEntity physicalEntity = ((CatalystActivity) databaseObject).getPhysicalEntity();
            node.setName(physicalEntity.getDisplayName());
            node.setStId(physicalEntity.getStId());
            node.setType(physicalEntity.getSchemaClass());
        }

        return node;
    }

    //todo find other method
    public PathwayBrowserNode getLocationsInThePathwayBrowser(String id, boolean interactors) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return getLocationsInThePathwayBrowser(generalService.findByStIdNoRelations(DatabaseObject.class, id), interactors);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return getLocationsInThePathwayBrowser(generalService.findByDbIdNoRelations(DatabaseObject.class, Long.valueOf(id)), interactors);
        }
        return null;
    }
}
