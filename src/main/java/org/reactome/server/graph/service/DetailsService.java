package org.reactome.server.graph.service;

import org.reactome.server.graph.domain.model.*;
import org.reactome.server.graph.repository.DetailsRepository;
import org.reactome.server.graph.service.helper.ContentDetails;
import org.reactome.server.graph.service.helper.PathwayBrowserNode;
import org.reactome.server.graph.service.util.DatabaseObjectUtils;
import org.reactome.server.graph.service.util.PathwayBrowserLocationsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @author Antonio Fabregat (fabregat@ebi.ac.uk)
 */
@Service
@SuppressWarnings("WeakerAccess")
public class DetailsService {

    @Autowired
    private DetailsRepository detailsRepository;
    @Autowired
    private AdvancedLinkageService advancedLinkageService;
    @Autowired
    private PhysicalEntityService physicalEntityService;
    @Autowired
    private HierarchyService hierarchyService;

//    @Transactional
    public ContentDetails getContentDetails(Object identifier, Boolean directParticipants) {

        ContentDetails contentDetails = new ContentDetails();
        DatabaseObject databaseObject;
        String id = DatabaseObjectUtils.getIdentifier(identifier);
        if (DatabaseObjectUtils.isStId(id)) {
            databaseObject = detailsRepository.detailsPageQuery(id);
        } else if (DatabaseObjectUtils.isDbId(id)){
            databaseObject = detailsRepository.detailsPageQuery(Long.parseLong(id));
        } else {
            return null;
        }
        contentDetails.setDatabaseObject(databaseObject);
        if (databaseObject instanceof Event || databaseObject instanceof PhysicalEntity || databaseObject instanceof Regulation) {
            if (directParticipants == null) directParticipants = false;
            Set<PathwayBrowserNode> leaves = getLocationsInThePathwayBrowserHierarchy(databaseObject, directParticipants);
            contentDetails.setNodes(leaves);
            contentDetails.setComponentOf(advancedLinkageService.getComponentsOf(databaseObject.getStId()));
            contentDetails.setOtherFormsOfThisMolecule(physicalEntityService.getOtherFormsOf(databaseObject.getDbId()));
        }
        return contentDetails;
    }

    private Set<PathwayBrowserNode> getLocationsInThePathwayBrowserHierarchy(DatabaseObject databaseObject, boolean directParticipants) {
        PathwayBrowserNode root = getLocationsInThePathwayBrowser(databaseObject, directParticipants);
        if (root!=null) {
            Set<PathwayBrowserNode> leaves = root.getLeaves();
            leaves = PathwayBrowserLocationsUtils.removeOrphans(leaves);
            return PathwayBrowserLocationsUtils.buildTreesFromLeaves(leaves);
        }
        return null;
    }

    private PathwayBrowserNode getLocationsInThePathwayBrowser(DatabaseObject databaseObject, boolean directParticipants) {
        if (databaseObject == null) return null;

        Object id = databaseObject.getStId();
        if (databaseObject.getStId() == null) id = databaseObject.getDbId();

        PathwayBrowserNode node;
        node = hierarchyService.getLocationsInPathwayBrowser(id, directParticipants, true);

        DatabaseObject leaf = null;
        if (databaseObject instanceof CatalystActivity) {
            leaf = ((CatalystActivity) databaseObject).getPhysicalEntity();
        } else if (databaseObject instanceof EntityFunctionalStatus) {
            leaf = ((EntityFunctionalStatus) databaseObject).getPhysicalEntity();
        } else if (databaseObject instanceof Regulation) {
            leaf = ((Regulation) databaseObject).getRegulator();
        }

        if(leaf!=null){
            node.setName(leaf.getDisplayName());
            node.setStId(leaf.getStId());
            node.setType(leaf.getSchemaClass());
        }
        return node;
    }
}
