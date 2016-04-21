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

    @Autowired
    private DatabaseObjectRepository repository;


    @Transactional
    public ContentDetails getContentDetails(String id) {

        ContentDetails contentDetails = new ContentDetails();

        DatabaseObject databaseObject = generalService.find(id, RelationshipDirection.OUTGOING);
        contentDetails.setDatabaseObject(databaseObject);

        Set<PathwayBrowserNode> leaves = getLocationsInPathwayBrowserHierarchy(databaseObject);
        leaves = PathwayBrowserLocationsUtils.removeOrphans(leaves);
        contentDetails.setLeaves(PathwayBrowserLocationsUtils.buildTreesFromLeaves(leaves));

        contentDetails.setComponentOf(generalService.getComponentsOf(databaseObject.getStableIdentifier()));

        if (databaseObject instanceof Event) {
            Event event = (Event) databaseObject;
            eventService.addRegulators(event);
            if (event instanceof ReactionLikeEvent) {
                ReactionLikeEvent reactionLikeEvent = (ReactionLikeEvent) event;
                eventService.loadCatalysts(reactionLikeEvent);
            }
        } else if (databaseObject instanceof PhysicalEntity) {
            PhysicalEntity physicalEntity = (PhysicalEntity) databaseObject;
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
        } else {

        }




        return contentDetails;
    }

    public PathwayBrowserNode getLocationsInThePathwayBrowser(DatabaseObject databaseObject) {
        return detailsRepository.getLocationsInPathwayBrowser(databaseObject);
    }

    public Set<PathwayBrowserNode> getLocationsInPathwayBrowserHierarchy(DatabaseObject databaseObject) {
        return detailsRepository.getLocationsInPathwayBrowser(databaseObject).getLeaves();
    }

}
