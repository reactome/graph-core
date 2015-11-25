package uk.ac.ebi.reactome.data;

import org.gk.model.GKInstance;
import org.gk.model.ReactomeJavaConstants;
import org.gk.persistence.MySQLAdaptor;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.Pathway;
import uk.ac.ebi.reactome.domain.model.Reaction;
import uk.ac.ebi.reactome.domain.model.ReferenceEntity;
import uk.ac.ebi.reactome.exception.ReactomeParserException;
import uk.ac.ebi.reactome.service.GenericService;
import uk.ac.ebi.reactome.service.ImportService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by flo on 29.10.15.
 *
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * At the moment derived queries do not allow you to specify a depth different from the default (1).
 *
 */
@Component
public class ReactomeParser {

    private static MySQLAdaptor dba;

//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
private  final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    @Autowired
    private ImportService importService;



    @Autowired
    private GenericService genericService;
    @Autowired
    private Session session;

    public ReactomeParser() throws ReactomeParserException {
        try {
            dba = new MySQLAdaptor("localhost", "Reactome", "root", "reactome", 3306);
        } catch (SQLException e) {
            logger.error("An error occured while connection to the Reactome database", e);
            throw new ReactomeParserException("An error occured while connection to the Reactome database", e);
        }
    }

    public void loadAll() {


        importService.getOrCreate(new Pathway(1L, "React_1.1", "Pathway1"));
        importService.getOrCreate(new Pathway(2L, "React_2.1", "Pathway2"));
        importService.getOrCreate(new Pathway(3L, "React_3.1", "Pathway3"));
        importService.getOrCreate(new Pathway(4L, "React_4.1", "Pathway4"));
        importService.getOrCreate(new Pathway(5L, "React_5.1", "Pathway5"));
        importService.getOrCreate(new Pathway(6L, "React_6.1", "Pathway6"));
        importService.getOrCreate(new Pathway(7L, "React_7.1", "Pathway7"));


        importService.getOrCreate(new Pathway(8L, "React_8.1", "Pathway8"));
        importService.getOrCreate(new Pathway(9L, "React_9.1", "Pathway9"));

        importService.getOrCreate(new Reaction(10L, "React_10.1", "Reaction10"));



        importService.createEventRelationship(1L, 8L);
        importService.createEventRelationship(1L, 9L);
        importService.createEventRelationship(1L, 10L);


        importService.createEventRelationship(1L, 2L);
        importService.createEventRelationship(2L, 3L);
        importService.createEventRelationship(3L, 4L);
        importService.createEventRelationship(4L, 5L);
        importService.createEventRelationship(5L, 6L);
        importService.createEventRelationship(6L, 7L);

        Integer a = 2;
//        DatabaseObject db = genericService.findByDbIdWithSession(1L, a);
        Pathway pw = session.load(Pathway.class,26663L,2);


//        DatabaseObject dbObject6 = importService.findByStId("React_1.1");
//        DatabaseObject dbObject7 = importService.find(dbObject6.getId(), 1);
//        DatabaseObject dbObject8 = importService.findOne(dbObject6.getId(), 1);

//        Pathway p = neo4jOperations.loadByProperty(Pathway.class,"dbId",1L,2);
//        neo4jOperations.loadByProperty()
        System.out.println("bla");

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

    public void load(Long dbId){
        try {
            GKInstance instance = dba.fetchInstance(dbId);
            parseInstance(instance);
        } catch (Exception e) {
            logger.error("An error occured while parsing instance with dbId" + dbId, e);
        }
    }

    public void load(String stId){
        try {
            GKInstance instance = getInstance(stId);
            parseInstance(instance);
        } catch (Exception e) {
            logger.error("An error occured while parsing instance with stId" + stId, e);
        }
    }

    public void createConstraints() {
        importService.createConstraintOnDatabaseObjectDbId();
        importService.createConstraintOnDatabaseObjectStId();
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
            Class<DatabaseObject> clazz = (Class<DatabaseObject>) Class.forName(clazzName);
            Constructor<DatabaseObject> constructor = clazz.getDeclaredConstructor(Long.class, String.class, String.class);

            Long dbId = instance.getDBID();
            String stId = getStableIdentifier(instance);
            String name = instance.getDisplayName();
            DatabaseObject databaseObject = constructor.newInstance(dbId, stId, name);


            Field[] fields = databaseObject.getClass().getDeclaredFields();
            if (instance.getSchemClass().isa(ReactomeJavaConstants.ReferenceEntity)){
                ((ReferenceEntity)databaseObject).setIdentifier(instance.getAttributeValue(ReactomeJavaConstants.identifier).toString());
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

