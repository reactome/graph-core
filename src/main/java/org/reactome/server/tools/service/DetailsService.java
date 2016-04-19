package org.reactome.server.tools.service;

import org.reactome.server.tools.domain.model.*;
import org.reactome.server.tools.repository.DetailsRepository;
import org.reactome.server.tools.service.helper.ContentDetails;
import org.reactome.server.tools.service.helper.PathwayBrowserNode;
import org.reactome.server.tools.service.helper.RelationshipDirection;
import org.reactome.server.tools.service.util.PathwayBrowserLocationsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
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
    private ObjectService objectService;

    @Autowired
    private PhysicalEntityService physicalEntityService;

    @Transactional
    public ContentDetails contentDetails(String id) {
        ContentDetails contentDetails = new ContentDetails();

        DatabaseObject databaseObject = generalService.getById(id, RelationshipDirection.OUTGOING);
        contentDetails.setDatabaseObject(databaseObject);


        Set<PathwayBrowserNode> leaves = getLocationsInPathwayBrowserHierarchy(databaseObject);
        leaves = PathwayBrowserLocationsUtils.removeOrphans(leaves);
        contentDetails.setLeaves(PathwayBrowserLocationsUtils.buildTreesFromLeaves(leaves));


        contentDetails.setComponentOf(objectService.getComponentsOf(databaseObject.getStableIdentifier()));

        if (databaseObject instanceof Reaction) {
            generalService.getByDbId(databaseObject.getDbId(), RelationshipDirection.UNDIRECTED, "reverseReaction");
        }
        if (databaseObject instanceof EntityWithAccessionedSequence || databaseObject instanceof SimpleEntity || databaseObject instanceof OpenSet) {
            contentDetails.setOtherFormsOfThisMolecule(physicalEntityService.getOtherFormsOfThisMolecule(databaseObject.getDbId()));
            if (databaseObject instanceof EntityWithAccessionedSequence) {
                EntityWithAccessionedSequence ewas = (EntityWithAccessionedSequence) databaseObject;
                generalService.getByDbId(ewas.getDbId(), RelationshipDirection.OUTGOING, "referenceGene", "referenceTranscript", "crossReference");
                if (ewas.getHasModifiedResidue() != null && !ewas.getHasModifiedResidue().isEmpty()) {
                    Collection dbIds = new ArrayList<>();
                    for (AbstractModifiedResidue abstractModifiedResidue : ewas.getHasModifiedResidue()) {
                        dbIds.add(abstractModifiedResidue.getDbId());
                    }
                    generalService.getByDbIds(dbIds, RelationshipDirection.OUTGOING, "psiMod", "modification");
                }
                generalService.getByDbId(ewas.getReferenceEntity().getDbId(), RelationshipDirection.OUTGOING, "referenceGene", "referenceTranscript", "crossReference");
            } else if (databaseObject instanceof SimpleEntity) {
                SimpleEntity simpleEntity = (SimpleEntity) databaseObject;
                generalService.getByDbId(simpleEntity.getReferenceEntity().getDbId(), RelationshipDirection.OUTGOING, "crossReference");
            } else if (databaseObject instanceof OpenSet) {
                OpenSet openSet = (OpenSet) databaseObject;
                generalService.getByDbId(openSet.getReferenceEntity().getDbId(), RelationshipDirection.OUTGOING, "crossReference");
            }
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
