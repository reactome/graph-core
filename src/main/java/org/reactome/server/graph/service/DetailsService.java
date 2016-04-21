package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.repository.DatabaseObjectRepository;
import org.reactome.server.graph.repository.DetailsRepository;
import org.reactome.server.graph.service.helper.ContentDetails;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.reactome.server.graph.service.util.PathwayBrowserLocationsUtils;
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


            Set<PathwayBrowserNode> leaves = getLocationsInThePathwayBrowserHierarchy(databaseObject);
            leaves = PathwayBrowserLocationsUtils.removeOrphans(leaves);
            contentDetails.setLeaves(PathwayBrowserLocationsUtils.buildTreesFromLeaves(leaves));




        contentDetails.setComponentOf(generalService.getComponentsOf(databaseObject.getStableIdentifier()));

        generalService.findByDbId(databaseObject.getDbId(),RelationshipDirection.INCOMING, "inferredTo");

        if (databaseObject instanceof Event) {
            Event event = (Event) databaseObject;
            eventService.addRegulators(event);
            if (event instanceof ReactionLikeEvent) {
                ReactionLikeEvent reactionLikeEvent = (ReactionLikeEvent) event;
                eventService.loadCatalysts(reactionLikeEvent);
            }
        } else if (databaseObject instanceof PhysicalEntity) {
            PhysicalEntity physicalEntity = (PhysicalEntity) databaseObject;
            generalService.findByDbId(physicalEntity.getDbId(),RelationshipDirection.INCOMING, "regulator");
            physicalEntityService.addCatalyzedEvents(physicalEntity);
            physicalEntityService.addRegulatedEvents(physicalEntity);
            if (physicalEntity instanceof EntityWithAccessionedSequence || physicalEntity instanceof SimpleEntity || physicalEntity instanceof OpenSet) {
                contentDetails.setOtherFormsOfThisMolecule(physicalEntityService.getOtherFormsOfThisMolecule(physicalEntity.getDbId()));
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
                } else if (databaseObject instanceof SimpleEntity) {
                    SimpleEntity simpleEntity = (SimpleEntity) databaseObject;
                    generalService.findByDbId(simpleEntity.getReferenceEntity().getDbId(), RelationshipDirection.OUTGOING, "crossReference");
                } else {
                    OpenSet openSet = (OpenSet) databaseObject;
                    generalService.findByDbId(openSet.getReferenceEntity().getDbId(), RelationshipDirection.OUTGOING, "crossReference");
                }
            }
        } else if (databaseObject instanceof Regulation) {
            generalService.findByDbId(databaseObject.getDbId(), RelationshipDirection.INCOMING, "regulatedBy");
        } else {
             //todo log error
        }




        return contentDetails;
    }

    public PathwayBrowserNode getLocationsInThePathwayBrowser(DatabaseObject databaseObject) {
        PathwayBrowserNode node = detailsRepository.getLocationsInPathwayBrowser(databaseObject);

        if (databaseObject instanceof Regulation) {
            DatabaseObject regulator;
            if (databaseObject instanceof PositiveRegulation) {
                PositiveRegulation positiveRegulation = (PositiveRegulation) databaseObject;
                regulator = positiveRegulation.getRegulator();
            } else  {
                NegativeRegulation negativeRegulation = (NegativeRegulation) databaseObject;
                regulator = negativeRegulation.getRegulator();
            }
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
            } else {
//               todo logger.error("Creating a node that is not an Event or PhysicalEntity");
            }

        }
        return node;
    }

    public Set<PathwayBrowserNode> getLocationsInThePathwayBrowserHierarchy(DatabaseObject databaseObject) {
        return getLocationsInThePathwayBrowser(databaseObject).getLeaves();

    }

}
