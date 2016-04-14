package org.reactome.server.tools.service;

import org.neo4j.ogm.model.Result;
import org.reactome.server.tools.domain.model.*;
import org.reactome.server.tools.repository.GenericRepository;
import org.reactome.server.tools.service.helper.PBNode;
import org.reactome.server.tools.service.helper.RelationshipDirection;
import org.reactome.server.tools.service.util.DatabaseObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 15.11.15.
 */
@Service
public class GenericServiceImpl implements GenericService {

//    private static final Logger logger = LoggerFactory.getLogger(GenericServiceImpl.class);

    @Autowired
    private GenericRepository genericRepository;


//    @Override
//    public DatabaseObject findById(String id, RelationshipDirection direction) {
//        id = DatabaseObjectUtils.trimId(id);
//        if (DatabaseObjectUtils.isStId(id)) {
//            return genericRepository.findByStableIdentifier(id, direction);
//        } else if (DatabaseObjectUtils.isDbId(id)){
//            return genericRepository.findByDbId(Long.parseLong(id), direction);
//        }
//        return null;
//    }

    @Override
    public Object findByPropertyWithRelations (String property, Object value, String... relationships){
        return genericRepository.findByPropertyWithRelations(property, value, relationships);
    }

    @Override
    public Object findByPropertyWithoutRelations (String property, Object value, String... relationships){
        return genericRepository.findByPropertyWithoutRelations(property, value, relationships);
    }

    //    TODO fix warning
//    @SuppressWarnings("unchecked")
//    @Override
//    public <T>Collection<T> getObjectsByClassName(String className, Integer page, Integer offset) throws ClassNotFoundException {
//        return genericRepository.getObjectsByClassName(DatabaseObjectUtils.getClassForName(className),page,offset);
//    }

    @Override
    public <T>T findByProperty(Class<T> clazz, String property, Object value, Integer depth){
        return genericRepository.findByProperty(clazz, property, value, depth);
    }

    @Override
    public <T> T findById(Class<T> clazz, Long id, Integer depth){
        return genericRepository.findById(clazz, id, depth);
    }

    @Override
    public <T>T findByDbId(Class<T> clazz, Long dbId, Integer depth) {
        return genericRepository.findByDbId(clazz, dbId, depth);
    }

    @Override
    public <T>T  findByStableIdentifier(Class<T> clazz, String stableIdentifier, Integer depth) {
        return genericRepository.findByStableIdentifier(clazz, stableIdentifier, depth);
    }

    @Override
    public Collection<Pathway> getTopLevelPathways() {
        return genericRepository.getTopLevelPathways();
    }

    @Override
    public Collection<Pathway> getTopLevelPathways(Long speciesId) {
        return genericRepository.getTopLevelPathways(speciesId);
    }

    @Override
    public Collection<Pathway> getTopLevelPathways(String speciesName) {
        return genericRepository.getTopLevelPathways(speciesName);
    }

    @Override
    public Pathway getEventHierarchy(Long dbId) {
        return genericRepository.getEventHierarchy(dbId);
    }


    public DatabaseObject getLocationsHierarchy(String stId){
        return genericRepository.getLocationsHierarchy(stId);
    }

    public DatabaseObject getReferral(Long dbId, String relationshipName) {
        return genericRepository.getReferral(dbId,relationshipName);
    }

    public Collection<DatabaseObject> getReferrals(Long dbId, String relationshipName){
        return genericRepository.getReferrals(dbId, relationshipName);
    }

//    public Set<PBNode> getLocationsInPathwayBrowserHierarchy(DatabaseObject databaseObject) {
//        return getLocationsInPathwayBrowserTree(databaseObject).getLeaves();
//    }
//
//    public PBNode getLocationsInPathwayBrowserTree(DatabaseObject databaseObject) {
//
//        Result result = genericRepository.getLocationsInPathwayBrowser(databaseObject.getStableIdentifier());
//
//        PBNode root = createNode(databaseObject);
//        Map<String,PBNode> nodes = new HashMap<>();
//        PBNode previous = root;
//        nodes.put(root.getStId(),root);
//
//        int previousSize = 0;
//        for (Map<String, Object> stringObjectMap : result) {
//            ArrayList<Object>[] nodePairCollections = ((ArrayList<Object>[])stringObjectMap.get("nodePairCollection"));
//            int size = nodePairCollections.length;
//            if (size>previousSize) {
//                ArrayList<Object> objects = nodePairCollections[nodePairCollections.length-1];
//                previous = addNode(previous,nodes,objects);
//            } else {
//                previous = root;
//                for (ArrayList<Object> objects : nodePairCollections) {
//                    if (objects.get(0) == null ){
//                        continue;
//                    }
//                    previous = addNode(previous,nodes,objects);
//                }
//            }
//            previousSize = size;
//        }
//        return  root ;
//    }

//    private PBNode addNode(PBNode previous, Map<String,PBNode> nodes, ArrayList<Object> objects) {
//        PBNode node;
//
//
//        if (nodes.containsKey(objects.get(0))) {
//            node = nodes.get(objects.get(0));
//        } else {
//            node = createNode(objects);
//            nodes.put(node.getStId(),node);
//        }
//        if (!node.getType().equals("CatalystActivity") && !node.getType().contains("Regulation") && !node.getType().equals("EntityFunctionalStatus")) {
//            previous.addChild(node);
//            node.addParent(previous);
//            previous = node;
//        }
//        return previous;
//    }
//
//    private PBNode createNode(DatabaseObject databaseObject) {
//        PBNode node = new PBNode();
//        node.setStId(databaseObject.getStableIdentifier());
//        node.setName(databaseObject.getDisplayName());
//        node.setType(databaseObject.getSchemaClass());
//
//        if (databaseObject instanceof Event) {
//            Event event = (Event) databaseObject;
//            node.setSpecies(event.getSpeciesName());
//        } else if (databaseObject instanceof PhysicalEntity) {
//            PhysicalEntity physicalEntity = (PhysicalEntity) databaseObject;
//            node.setSpecies(physicalEntity.getSpeciesName());
//        } else {
////            logger.error
//            return null;
//        }
//        return node;
//    }
//
//    private PBNode createNode(ArrayList<Object> nodePairCollection) {
//        PBNode node = new PBNode();
//        node.setStId((String) nodePairCollection.get(0));
//        node.setName((String) nodePairCollection.get(1));
//        node.setDiagram((Boolean) nodePairCollection.get(2));
//        node.setSpecies((String) nodePairCollection.get(3));
//        List xx = (List) nodePairCollection.get(4);
//        Class<?> lowestClass = Object.class;
//
//        for (Object o1 : xx) {
//            try {
//                Class clazz = Class.forName("org.reactome.server.tools.domain.model." + o1.toString());
//                if (lowestClass.isAssignableFrom(clazz)) {
//                    lowestClass = clazz;
//                }
//            } catch (ClassNotFoundException e) {
////                e.printStackTrace();
//                //todo toplevel pathway!!
//                node.setType("Pathway");
//            }
//        }
//        node.setType(lowestClass.getSimpleName());
//        return node;
//    }


    @Override
    public Collection<Species> getSpecies() {
        return genericRepository.getSpecies();
    }

    @Override
    public Result query (String query, Map<String,Object> map) {
        return genericRepository.query(query,map);
    }

    @Override
    public Long countEntries(Class<?> clazz){
        return genericRepository.countEntries(clazz);
    }

    @Override
    public void clearCache() {
        genericRepository.clearCache();
    }

    @Override
    public boolean fitForService()  {
        return genericRepository.fitForService();
    }
}
