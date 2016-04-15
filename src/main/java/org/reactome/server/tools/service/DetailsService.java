package org.reactome.server.tools.service;

import org.neo4j.ogm.model.Result;
import org.reactome.server.tools.domain.model.*;
import org.reactome.server.tools.repository.DetailsRepository;
import org.reactome.server.tools.repository.GenericRepository;
import org.reactome.server.tools.service.helper.ContentDetails;
import org.reactome.server.tools.service.helper.PBNode;
import org.reactome.server.tools.service.helper.RelationshipDirection;
import org.reactome.server.tools.service.util.DatabaseObjectUtils;
import org.reactome.server.tools.service.util.PathwayBrowserLocationsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

        DatabaseObject databaseObject = findById(id, RelationshipDirection.OUTGOING);
        contentDetails.setDatabaseObject(databaseObject);

        Set<PBNode> leafs = getLocationsInPathwayBrowserHierarchy(databaseObject);
        leafs = PathwayBrowserLocationsUtils.removeOrphans(leafs);
        contentDetails.setLeafs(PathwayBrowserLocationsUtils.buildTreesFromLeaves(leafs));

        if (databaseObject instanceof EntityWithAccessionedSequence || databaseObject instanceof SimpleEntity || databaseObject instanceof OpenSet) {
            contentDetails.setOtherFormsOfThisMolecule(databaseObjectService.getOtherFormsOfThisMolecule(databaseObject.getDbId()));
            if (databaseObject instanceof EntityWithAccessionedSequence) {
                EntityWithAccessionedSequence ewas = (EntityWithAccessionedSequence) databaseObject;
                genericRepository.findByPropertyWithRelations("dbId",ewas.getReferenceEntity().getDbId(), "referenceGene", "referenceTranscript");

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
        return getLocationsInPathwayBrowserTree(databaseObject).getLeaves();
    }

    public PBNode getLocationsInPathwayBrowserTree(DatabaseObject databaseObject) {

        Result result = detailsRepository.getLocationsInPathwayBrowser(databaseObject.getStableIdentifier());

        PBNode root = createNode(databaseObject);
        Map<String,PBNode> nodes = new HashMap<>();
        PBNode previous = root;
        nodes.put(root.getStId(),root);

        int previousSize = 0;
        for (Map<String, Object> stringObjectMap : result) {
            ArrayList<Object>[] nodePairCollections = ((ArrayList<Object>[])stringObjectMap.get("nodePairCollection"));
            int size = nodePairCollections.length;
            if (size>previousSize) {
                ArrayList<Object> objects = nodePairCollections[nodePairCollections.length-1];
                previous = addNode(previous,nodes,objects);
            } else {
                previous = root;
                for (ArrayList<Object> objects : nodePairCollections) {
                    if (objects.get(0) == null ){
                        continue;
                    }
                    previous = addNode(previous,nodes,objects);
                }
            }
            previousSize = size;
        }
        return  root ;
    }

    private PBNode addNode(PBNode previous, Map<String,PBNode> nodes, ArrayList<Object> objects) {
        PBNode node;


        if (nodes.containsKey(objects.get(0))) {
            node = nodes.get(objects.get(0));
        } else {
            node = createNode(objects);
            nodes.put(node.getStId(),node);
        }
        if (!node.getType().equals("CatalystActivity") && !node.getType().contains("Regulation") && !node.getType().equals("EntityFunctionalStatus")) {
            previous.addChild(node);
            node.addParent(previous);
            previous = node;
        }
        return previous;
    }

    private PBNode createNode(DatabaseObject databaseObject) {
        PBNode node = new PBNode();
        node.setStId(databaseObject.getStableIdentifier());
        node.setName(databaseObject.getDisplayName());
        node.setType(databaseObject.getSchemaClass());

        if (databaseObject instanceof Event) {
            Event event = (Event) databaseObject;
            node.setSpecies(event.getSpeciesName());
        } else if (databaseObject instanceof PhysicalEntity) {
            PhysicalEntity physicalEntity = (PhysicalEntity) databaseObject;
            node.setSpecies(physicalEntity.getSpeciesName());
        } else {
//            logger.error
            return null;
        }
        return node;
    }

    private PBNode createNode(ArrayList<Object> nodePairCollection) {
        PBNode node = new PBNode();
        node.setStId((String) nodePairCollection.get(0));
        node.setName((String) nodePairCollection.get(1));
        node.setDiagram((Boolean) nodePairCollection.get(2));
        node.setSpecies((String) nodePairCollection.get(3));
        List xx = (List) nodePairCollection.get(4);
        Class<?> lowestClass = Object.class;

        for (Object o1 : xx) {
            try {
                Class clazz = Class.forName("org.reactome.server.tools.domain.model." + o1.toString());
                if (lowestClass.isAssignableFrom(clazz)) {
                    lowestClass = clazz;
                }
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
                //todo toplevel pathway!!
                node.setType("Pathway");
            }
        }
        node.setType(lowestClass.getSimpleName());
        return node;
    }

}
