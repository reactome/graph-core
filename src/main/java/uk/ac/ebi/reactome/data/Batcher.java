package uk.ac.ebi.reactome.data;

import org.gk.model.GKInstance;
import org.gk.model.ReactomeJavaConstants;
import org.gk.persistence.MySQLAdaptor;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.ogm.session.Session;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.ReferenceEntity;
import uk.ac.ebi.reactome.exception.ReactomeParserException;
import uk.ac.ebi.reactome.service.GenericService;
import uk.ac.ebi.reactome.service.ImportService;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 26.11.15.
 */
@Component
public class Batcher {



    /**
     * Created by flo on 29.10.15.
     *
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * At the moment derived queries do not allow you to specify a depth different from the default (1).
     *
     */



    private static MySQLAdaptor dba;

    //    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private  final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    @Autowired
    private ImportService importService;



    @Autowired
    private GenericService genericService;
    @Autowired
    private Session session;

    public Batcher() throws ReactomeParserException {
        try {
            dba = new MySQLAdaptor("localhost", "Reactome", "root", "reactome", 3306);
        } catch (SQLException e) {
            logger.error("An error occured while connection to the Reactome database", e);
            throw new ReactomeParserException("An error occured while connection to the Reactome database", e);
        }
    }

    public void loadAll() {

        try {
            Collection<?> frontPage = dba.fetchInstancesByClass(ReactomeJavaConstants.FrontPage);
            GKInstance instance1 = (GKInstance) frontPage.iterator().next();
            Collection<GKInstance> instances = instance1.getAttributeValuesList(ReactomeJavaConstants.frontPageItem);
            for (GKInstance instance : instances) {
                parseInstance(instance);
            }
        } catch (Exception e) {
            logger.error("An error occured while parsing FrontPage items", e);
        }
    }


    @Transactional
    private void parseInstance(GKInstance instance) {
        long startTime = System.currentTimeMillis();
        recursion(instance);
        long stopTime = System.currentTimeMillis();
        long ms = stopTime - startTime;
        int seconds = (int) (ms / 1000) % 60;
        int minutes = (int) ((ms / (1000 * 60)) % 60);
        logger.info(instance.getDisplayName() + " indexed in: " + minutes + "minutes " + seconds + "seconds" );
    }

    private void recursion(GKInstance instance)  {

        DatabaseObject obj = createDatabaseObjectFromGkInstance(instance);

        if (instance.getSchemClass().isa(ReactomeJavaConstants.Event)) {
            if (instance.getSchemClass().isa(ReactomeJavaConstants.Pathway)) {
                processAttributesFromInstance(instance, ReactomeJavaConstants.hasEvent);
            } else if (instance.getSchemClass().isa(ReactomeJavaConstants.ReactionlikeEvent)) {
                if(instance.getSchemClass().isa(ReactomeJavaConstants.BlackBoxEvent)){
                    processAttributesFromInstance(instance, ReactomeJavaConstants.hasEvent);
                }
                processAttributesFromInstance(instance, ReactomeJavaConstants.input);
                processAttributesFromInstance(instance, ReactomeJavaConstants.output);
            }
        }
        else if (instance.getSchemClass().isa(ReactomeJavaConstants.PhysicalEntity)) {
            if(instance.getSchemClass().isa(ReactomeJavaConstants.Complex)) {
                processAttributesFromInstance(instance, ReactomeJavaConstants.hasComponent);
            } else if(instance.getSchemClass().isa(ReactomeJavaConstants.EntitySet)) {
                processAttributesFromInstance(instance, ReactomeJavaConstants.hasMember);
                if (instance.getSchemClass().isa(ReactomeJavaConstants.CandidateSet)) {
                    processAttributesFromInstance(instance, ReactomeJavaConstants.hasCandidate);
                }
            } else if(instance.getSchemClass().isa(ReactomeJavaConstants.GenomeEncodedEntity)) {

                if (instance.getSchemClass().isa(ReactomeJavaConstants.EntityWithAccessionedSequence)) {
                    processAttributesFromInstance(instance,ReactomeJavaConstants.referenceEntity);
                }
            } else if(instance.getSchemClass().isa(ReactomeJavaConstants.OtherEntity)) {

            } else if(instance.getSchemClass().isa(ReactomeJavaConstants.Polymer)) {
                processAttributesFromInstance(instance, ReactomeJavaConstants.repeatedUnit);
            } else if(instance.getSchemClass().isa(ReactomeJavaConstants.SimpleEntity)) {

            }
        }
        else if (instance.getSchemClass().isa(ReactomeJavaConstants.ReferenceEntity)) {
        }
        else {
            //TODO
        }
    }

    private void processAttributesFromInstance(GKInstance instance, String fieldName)  {
        if ( instance.getSchemClass().isValidAttribute(fieldName)) {
            Collection<GKInstance> attributes = null;
            try {
                attributes = instance.getAttributeValuesList(fieldName);
                for (GKInstance attribute : attributes) {
                    if (importService.findByDbId(attribute.getDBID())== null) {
                        recursion(attribute);
                    }
                    Long dbId = instance.getDBID();
                    Long refDbId = attribute.getDBID();
                    if (fieldName.equals(ReactomeJavaConstants.hasEvent)) {
                        importService.createEventRelationship(dbId, refDbId);
                    } else if(fieldName.equals(ReactomeJavaConstants.input)) {
                        importService.createInputRelationship(dbId, refDbId);
                    } else if(fieldName.equals(ReactomeJavaConstants.output)) {
                        importService.createOutputRelationship(dbId, refDbId);
                    } else if(fieldName.equals(ReactomeJavaConstants.hasComponent)) {
                        importService.createComponentRelationship(dbId, refDbId);
                    } else if(fieldName.equals(ReactomeJavaConstants.hasMember)) {
                        importService.createMemberRelationship(dbId, refDbId);
                    } else if(fieldName.equals(ReactomeJavaConstants.hasCandidate)) {
                        importService.createCandidateRelationship(dbId, refDbId);
                    } else if(fieldName.equals(ReactomeJavaConstants.repeatedUnit)) {
                        importService.createRepeatedUnitRelationship(dbId, refDbId);
                    } else if(fieldName.equals(ReactomeJavaConstants.referenceEntity)) {
                        importService.createReferenceEntityRelationship(dbId, refDbId);
                    }
                }
            } catch (Exception e) {
                //TODO should never happen
                e.printStackTrace();
            }
        }
    }

    private DatabaseObject createDatabaseObjectFromGkInstance(GKInstance instance) {
        try {
            String clazzName = "uk.ac.ebi.reactome.domain.model." + instance.getSchemClass().getName();
            Class<? super DatabaseObject> clazz = (Class<DatabaseObject>) Class.forName(clazzName);
            Constructor<DatabaseObject> constructor = (Constructor<DatabaseObject>) clazz.getDeclaredConstructor(Long.class, String.class, String.class);

            Long dbId = instance.getDBID();
            String stId = getStableIdentifier(instance);
            String name = instance.getDisplayName();
            DatabaseObject databaseObject = constructor.newInstance(dbId, stId, name);


            Field[] fields = databaseObject.getClass().getDeclaredFields();
            if (instance.getSchemClass().isa(ReactomeJavaConstants.ReferenceEntity)){
                ((ReferenceEntity)databaseObject).setIdentifier(instance.getAttributeValue(ReactomeJavaConstants.identifier).toString());
            }



           BatchInserter inserter = null;
            try {

                inserter = BatchInserters.inserter(new File("/target/graph.db").getAbsolutePath());

                List<Label> labels = new ArrayList<>();
                while(clazz != null && clazz != java.lang.Object.class) {
                    labels.add(DynamicLabel.label(clazz.getName()));
                    clazz = clazz.getSuperclass();
                }

                Map<String, Object> properties = new HashMap<>();

                inserter.createNode(properties,labels.toArray(new Label[labels.size()]));

                inserter.createNode(properties, (Label[]) labels.toArray());
                Label label = DynamicLabel.label(clazzName);
                Label personLabel = DynamicLabel.label("Interactor");
                Label personLabe = DynamicLabel.label("Interactor");
                Label personLab = DynamicLabel.label("Interactor");
                // wont work anyways
//                inserter.createDeferredConstraint( personLabel ).assertPropertyIsUnique("identifier");
                inserter.createDeferredSchemaIndex( personLabel ).on("identifier" ).create();



//                    properties.put( "identifier", array[0] );
//                    b = inserter.createNode(properties, personLabel,personLabe,personLab);
//
//                inserter.

//                long a;
//                if(idmap.containsKey(array[0])){
//                    a=idmap.get(array[0]);
//                } else {
//                    Map<String, Object> properties = new HashMap<>();
//                    properties.put( "identifier", array[0] );
//                    a = inserter.createNode(properties, personLabel);
//                    properties.put( "identifier", array[1] );
//                    idmap.put(array[0],a);
//                }
//                long b;
//                if(idmap.containsKey(array[1])){
//                    b=idmap.get(array[1]);
//                } else {
//                    Map<String, Object> properties = new HashMap<>();
//                    properties.put( "identifier", array[0] );
//                    b = inserter.createNode(properties, personLabel);
//                    properties.put( "identifier", array[1] );
//                    idmap.put(array[1],b);
//                }
                RelationshipType knows = DynamicRelationshipType.withName("INTERACT");

//                inserter.createRelationship( a,  b, knows, null );

            } finally{
                if ( inserter != null ) {
                    inserter.shutdown();
                }
            }

















//            will get also relationship fields
//            for (Field field : fields) { //fields are allways empty WHY
//                if (instance.getSchemClass().isValidAttribute(field.getName())){
//                    field.set(field.getClass(),instance.getAttributeValue(field.getName()));
//                }
//            }

            return importService.getOrCreate(databaseObject);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //    private void processActivitiesAndRegulations(GKInstance instance, Long dbId, String fieldName) {
//        if ( instance.getSchemClass().isValidAttribute(fieldName)) {
//            Collection<GKInstance> attributes = null;
//            try {
//                attributes = instance.getAttributeValuesList(fieldName);
//                for (GKInstance attribute : attributes) {
////                    if (attribute.getSchemClass().isa(ReactomeJavaConstants.CatalystActivity)) {
////                        GKInstance activeUnit = (GKInstance) attribute.getAttributeValue(ReactomeJavaConstants.physicalEntity);
//////                        GKInstance goMolecularFunction = (GKInstance) attribute.getAttributeValue(ReactomeJavaConstants.activity);
////                        physicalEntityService.getOrCreate(createParticipantFromGKInstance(activeUnit));
//////                        recursion(activeUnit,);
////                        reactionLikeEventService.createCatalystRelationship(dbId, activeUnit.getDBID());
////                    }
//                }
//            } catch(Exception e){
//                //TODO should never happen
//                e.printStackTrace();
//            }
//        }
//
//    }

    private String getStableIdentifier(GKInstance instance) throws Exception {
        GKInstance stId = getGkInstance(instance, ReactomeJavaConstants.stableIdentifier);
        if (stId != null) {
            return (String) stId.getAttributeValue(ReactomeJavaConstants.identifier);
        }
        return "";
    }

    private GKInstance getGkInstance(GKInstance instance, String fieldName) throws Exception {
        if(instance.getSchemClass().isValidAttribute(fieldName)) {
            return (GKInstance) instance.getAttributeValue(fieldName);
        }
        return null;
    }

    private Collection<GKInstance> getGkInstances(GKInstance instance, String fieldName) throws Exception {
        if(instance.getSchemClass().isValidAttribute(fieldName)) {
            return (Collection<GKInstance>) instance.getAttributeValuesList(fieldName);
        }
        return null;
    }

    private GKInstance getInstance(String identifier) throws Exception {
        identifier = identifier.trim().split("\\.")[0];
        if (identifier.startsWith("REACT")){
            return getInstance(dba.fetchInstanceByAttribute(ReactomeJavaConstants.StableIdentifier, "oldIdentifier", "=", identifier));
        }else if (identifier.startsWith("R-")) {
            return getInstance(dba.fetchInstanceByAttribute(ReactomeJavaConstants.StableIdentifier, ReactomeJavaConstants.identifier, "=", identifier));
        } else {
            return dba.fetchInstance(Long.parseLong(identifier));
        }
    }

    private GKInstance getInstance(Collection<GKInstance> target) throws Exception {
        if(target == null) throw new Exception("No entity found");
        if(target.size()!=1) throw new Exception("Many options have been found for the specified identifier");
        GKInstance stId = target.iterator().next();
        return (GKInstance) dba.fetchInstanceByAttribute(ReactomeJavaConstants.DatabaseObject, ReactomeJavaConstants.stableIdentifier, "=", stId).iterator().next();
    }
}



