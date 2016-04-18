package org.reactome.server.tools.service;

import org.reactome.server.tools.domain.model.*;
import org.reactome.server.tools.service.helper.PBNode;
import org.reactome.server.tools.repository.DetailsRepository;
import org.reactome.server.tools.repository.GenericRepository;
import org.reactome.server.tools.service.helper.ContentDetails;
import org.reactome.server.tools.service.helper.RelationshipDirection;
import org.reactome.server.tools.service.util.DatabaseObjectUtils;
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
    private GenericRepository genericRepository;

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @Transactional
    public ContentDetails contentDetails(String id) {
        ContentDetails contentDetails = new ContentDetails();

        DatabaseObject databaseObject = findReverseReactionOrPrecedingEvent(id, "reverseReaction", "precedingEvent");
        contentDetails.setDatabaseObject(databaseObject);


        Set<PBNode> leaves = getLocationsInPathwayBrowserHierarchy(databaseObject);
        leaves = PathwayBrowserLocationsUtils.removeOrphans(leaves);
        contentDetails.setLeaves(PathwayBrowserLocationsUtils.buildTreesFromLeaves(leaves));


        contentDetails.setComponentOf(databaseObjectService.getComponentsOf(databaseObject.getStableIdentifier()));

        if (databaseObject instanceof EntityWithAccessionedSequence || databaseObject instanceof SimpleEntity || databaseObject instanceof OpenSet) {
            contentDetails.setOtherFormsOfThisMolecule(databaseObjectService.getOtherFormsOfThisMolecule(databaseObject.getDbId()));

            if (databaseObject instanceof EntityWithAccessionedSequence) {

                EntityWithAccessionedSequence ewas = (EntityWithAccessionedSequence) databaseObject;
//                genericRepository.load(ewas.getId());
                genericRepository.findByPropertyWithRelations("dbId",ewas.getReferenceEntity().getDbId(), "referenceGene", "referenceTranscript", "crossReference");
                if (ewas.getHasModifiedResidue() != null && !ewas.getHasModifiedResidue().isEmpty()) {
                    Collection dbIds = new ArrayList<>();
                    for (AbstractModifiedResidue abstractModifiedResidue : ewas.getHasModifiedResidue()) {
                        dbIds.add(abstractModifiedResidue.getDbId());
                    }
                    genericRepository.findCollectionByPropertyWithRelationships("dbId", dbIds, "psiMod", "modification");
                }
                
                genericRepository.findByPropertyWithRelations("dbId",ewas.getReferenceEntity().getDbId(), "referenceGene", "referenceTranscript", "crossReference");
            } else if (databaseObject instanceof SimpleEntity) {
                SimpleEntity simpleEntity = (SimpleEntity) databaseObject;
                genericRepository.findByPropertyWithRelations("dbId",simpleEntity.getReferenceEntity().getDbId(), "crossReference");
            } else if (databaseObject instanceof OpenSet) {
                OpenSet openSet = (OpenSet) databaseObject;
                genericRepository.findByPropertyWithRelations("dbId",openSet.getReferenceEntity().getDbId(), "crossReference");
            }
        }
        return contentDetails;
    }

    public DatabaseObject findById(String id, RelationshipDirection direction) {
        id = DatabaseObjectUtils.trimId(id);
        if (DatabaseObjectUtils.isStId(id)) {
            return detailsRepository.findByStableIdentifier(id, direction);
        } else if (DatabaseObjectUtils.isDbId(id)){
            return detailsRepository.findByDbId(Long.parseLong(id), direction);
        }
        return null;
    }

    public <T>Collection<T> getObjectsByClassName(String className, Integer page, Integer offset) throws ClassNotFoundException {
        return detailsRepository.getObjectsByClassName(DatabaseObjectUtils.getClassForName(className),page,offset);
    }

    public Set<PBNode> getLocationsInPathwayBrowserHierarchy(DatabaseObject databaseObject) {
        return detailsRepository.getLocationsInPathwayBrowserTree(databaseObject).getLeaves();
    }

    public PBNode getLocationsInThePathwayBrowserTree(DatabaseObject databaseObject) {
        return detailsRepository.getLocationsInPathwayBrowserTree(databaseObject);
    }

    public DatabaseObject findReverseReactionOrPrecedingEvent(String dbId, String... direction){
        return detailsRepository.findGuiFirstMethod(dbId, direction);
    }


}
