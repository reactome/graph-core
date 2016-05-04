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
    @Autowired
    private EventService eventService;

    @Transactional
    public ContentDetails getContentDetails(String id) {

        ContentDetails contentDetails = new ContentDetails();
        DatabaseObject databaseObject = generalService.find(id, RelationshipDirection.OUTGOING);
        contentDetails.setDatabaseObject(databaseObject);

        if (databaseObject instanceof Event || databaseObject instanceof PhysicalEntity || databaseObject instanceof Regulation) {

            Set<PathwayBrowserNode> leaves = getLocationsInThePathwayBrowserHierarchy(databaseObject);
            leaves = PathwayBrowserLocationsUtils.removeOrphans(leaves);
            contentDetails.setLeaves(PathwayBrowserLocationsUtils.buildTreesFromLeaves(leaves));
            contentDetails.setComponentOf(generalService.getComponentsOf(databaseObject.getStableIdentifier()));
            generalService.findByDbId(databaseObject.getDbId(), RelationshipDirection.INCOMING, "inferredTo");
            if (databaseObject instanceof Event) {
                Event event = (Event) databaseObject;
                loadEventProperties(event);
            } else if (databaseObject instanceof PhysicalEntity) {
                PhysicalEntity physicalEntity = (PhysicalEntity) databaseObject;
                loadPhysicalEntityProperties(physicalEntity, contentDetails);

            } else if (databaseObject instanceof Regulation) {
                generalService.findByDbId(databaseObject.getDbId(), RelationshipDirection.INCOMING, "regulatedBy");
            } else {
                logger.error("This method is retrieving the data for the details method and should be used for Events, PhysicalEntities and Regulations only");
            }
        }
        return contentDetails;
    }

    public Set<PathwayBrowserNode> getLocationsInThePathwayBrowserHierarchy(DatabaseObject databaseObject) {
        return getLocationsInThePathwayBrowser(databaseObject).getLeaves();
    }

    public Set<PathwayBrowserNode> getLocationsInThePathwayBrowserHierarchy(String id) {
        return getLocationsInThePathwayBrowser(id).getLeaves();
    }

    public PathwayBrowserNode getLocationsInThePathwayBrowser(DatabaseObject databaseObject) {
        if (databaseObject == null) return null;
        PathwayBrowserNode node = detailsRepository.getLocationsInPathwayBrowser(databaseObject);

        if (databaseObject instanceof Regulation) {
            DatabaseObject regulator = ((Regulation) databaseObject).getRegulator();
            node.setName(regulator.getDisplayName());
            node.setStId(regulator.getStableIdentifier());
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
            node.setStId(physicalEntity.getStableIdentifier());
            node.setType(physicalEntity.getSchemaClass());
        }

        return node;
    }

    //todo find other method
    public PathwayBrowserNode getLocationsInThePathwayBrowser(String id) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return getLocationsInThePathwayBrowser(generalService.findByStableIdentifierNoRelations(DatabaseObject.class, id));
        } else if (DatabaseObjectUtils.isDbId(id)){
            return getLocationsInThePathwayBrowser(generalService.findByDbIdNoRelations(DatabaseObject.class, Long.valueOf(id)));
        }
        return null;
    }

    private void loadEventProperties(Event event) {
        eventService.addRegulators(event);
        if (event instanceof ReactionLikeEvent) {
            ReactionLikeEvent reactionLikeEvent = (ReactionLikeEvent) event;
            eventService.loadCatalysts(reactionLikeEvent);
        }
    }

    private void loadPhysicalEntityProperties(PhysicalEntity physicalEntity, ContentDetails contentDetails) {

        //if(physicalEntity instanceof EntityWithAccessionedSequence||physicalEntity instanceof SimpleEntity||physicalEntity instanceof OpenSet) {
            contentDetails.setOtherFormsOfThisMolecule(physicalEntityService.getOtherFormsOfThisMolecule(physicalEntity.getDbId()));
        //}
        generalService.findByDbId(physicalEntity.getDbId(),RelationshipDirection.INCOMING,"regulator");
        physicalEntityService.addCatalyzedEvents(physicalEntity);
        physicalEntityService.addRegulatedEvents(physicalEntity);
        if (physicalEntity instanceof EntityWithAccessionedSequence) {
            EntityWithAccessionedSequence ewas = (EntityWithAccessionedSequence) physicalEntity;
            generalService.findByDbId(ewas.getReferenceEntity().getDbId(), RelationshipDirection.OUTGOING, "referenceGene", "referenceTranscript", "crossReference");
            if (ewas.getHasModifiedResidue() != null && !ewas.getHasModifiedResidue().isEmpty()) {
                List<Long> dbIds = new ArrayList<>();
                for (AbstractModifiedResidue abstractModifiedResidue : ewas.getHasModifiedResidue()) {
                    dbIds.add(abstractModifiedResidue.getDbId());
                }
                generalService.findByDbIds(dbIds, RelationshipDirection.OUTGOING, "psiMod", "modification");
            }
            generalService.findByDbId(ewas.getReferenceEntity().getDbId(), RelationshipDirection.OUTGOING, "referenceGene", "referenceTranscript", "crossReference");
        } else if (physicalEntity instanceof SimpleEntity) {
            SimpleEntity simpleEntity = (SimpleEntity) physicalEntity;
            generalService.findByDbId(simpleEntity.getReferenceEntity().getDbId(), RelationshipDirection.OUTGOING, "crossReference");
        } else if (physicalEntity instanceof OpenSet) {
            OpenSet openSet = (OpenSet) physicalEntity;
            generalService.findByDbId(openSet.getReferenceEntity().getDbId(), RelationshipDirection.OUTGOING, "crossReference");
        }
    }


}
