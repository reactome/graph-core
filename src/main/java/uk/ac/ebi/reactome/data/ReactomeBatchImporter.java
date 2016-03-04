package uk.ac.ebi.reactome.data;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.IllegalClassException;
import org.gk.model.GKInstance;
import org.gk.model.ReactomeJavaConstants;
import org.gk.pathwaylayout.DiagramGeneratorFromDB;
import org.gk.persistence.MySQLAdaptor;
import org.gk.schema.InvalidClassException;
import org.neo4j.graphdb.*;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.reactome.domain.model.*;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;

/**
 * This component is used to batch import Reactome data into neo4j.
 * This importer utilizes the Neo4j BatchInserter and the Reactome MySql adapter.
 * WARNING: The BatchInserter is not thread save, not transactional, and can not enforce any constraints
 *          while inserting data.
 * WARNING: DATA_DIR folder will be deleted at the start of data import
 *
 * Created by:
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 16.01.16.
 */
public class ReactomeBatchImporter {

    private static final Logger errorLogger = LoggerFactory.getLogger("importErrorLogger");
    private static final Logger importLogger = LoggerFactory.getLogger("importLogger");

    private static MySQLAdaptor dba;
    private static BatchInserter batchInserter;
    private static String DATA_DIR;

    private static final String DBID = "dbId";
    private static final String STID = "stableIdentifier";
    private static final String ACCESSION = "identifier";
    private static final String NAME = "displayName";

    private static final String STOICHIOMETRY = "stoichiometry";

    private static final Map<Class, List<String>> primitiveAttributesMap = new HashMap<>();
    private static final Map<Class, List<String>> primitiveListAttributesMap = new HashMap<>();
    private static final Map<Class, List<String>> relationAttributesMap = new HashMap<>();

    private static final Map<Class, Label[]> labelMap = new HashMap<>();
    private static final Map<Long, Long> dbIds = new HashMap<>();
    private static final Map<Long, Long> reverseReactions = new HashMap<>();
    private static final Map<Long, Long> equivalentTo = new HashMap<>();

    private static final int width = 100;
    private static int total;

    public ReactomeBatchImporter(String host, String database, String user, String password, Integer port, String dir) {
        try {
            dba = new MySQLAdaptor(host,database,user,password,port);
            DATA_DIR = dir;
            total = (int) dba.getClassInstanceCount(ReactomeJavaConstants.DatabaseObject);
            total = total - (int) dba.getClassInstanceCount(ReactomeJavaConstants.StableIdentifier);
            total = total - (int) dba.getClassInstanceCount(ReactomeJavaConstants.PathwayDiagramItem);
            total = total - (int) dba.getClassInstanceCount(ReactomeJavaConstants.ReactionCoordinates);
            importLogger.info("Established connection to Reactome database");
        } catch (SQLException|InvalidClassException e) {
            importLogger.error("An error occurred while connection to the Reactome database", e);
        }
    }

    public void importAll() throws IOException {
        prepareDatabase();
        try {
            Collection<?> frontPages = dba.fetchInstancesByClass(ReactomeJavaConstants.FrontPage);
            GKInstance frontPage = (GKInstance) frontPages.iterator().next();
            Collection<?> objects = frontPage.getAttributeValuesList(ReactomeJavaConstants.frontPageItem);
            importLogger.info("Started importing " + objects.size() + " top level pathways");
            System.out.println("Started importing " + objects.size() + " top level pathways");
            for (Object object : objects) {
                long start = System.currentTimeMillis();
                GKInstance instance = (GKInstance) object;
                importGkInstance((GKInstance) object);
                long elapsedTime = System.currentTimeMillis() - start;
                int ms = (int) elapsedTime % 1000;
                int sec = (int) (elapsedTime / 1000) % 60;
                int min = (int) ((elapsedTime / (1000 * 60)) % 60);
                importLogger.info(instance.getDisplayName() + " was processed within: " + min + " min " + sec + " sec " + ms + " ms");
            }
            importLogger.info("All top level pathways have been imported to Neo4j");
            System.out.println("\nAll top level pathways have been imported to Neo4j");
        } catch (Exception e) {
            e.printStackTrace();
        }
        batchInserter.shutdown();
    }

    /**
     * Imports one single GkInstance into neo4j. When iterating through the relationAttributes it is possible to
     * go deeper into the GkInstance hierarchy (eg hasEvents)
     * @param instance GkInstance
     * @return Neo4j native id (generated by the BatchInserter)
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    private Long importGkInstance(GKInstance instance) throws ClassNotFoundException {

        if (dbIds.size() % 100 == 0 ) {
            updateProgressBar(dbIds.size(),total);
        }

        String clazzName = DatabaseObject.class.getPackage().getName() + "." + instance.getSchemClass().getName();
        Class clazz = Class.forName(clazzName);
        setUpMethods(clazz);
        Long id = saveDatabaseObject(instance, clazz);
        dbIds.put(instance.getDBID(), id);

        if(relationAttributesMap.containsKey(clazz)) {
            for (String attribute :  relationAttributesMap.get(clazz)) {

                switch (attribute) {
                    case "regulatedBy":
                    case "positivelyRegulatedBy":
                        saveRelationships(id, getCollectionFromGkInstanceReferrals(instance, ReactomeJavaConstants.regulatedEntity), "regulatedBy");
                        break;
                    case "inferredTo":
                        /**
                         * only one type of regulation is needed here, In the native data only regulatedBy exists
                         * since the type of regulation is later determined by the Object Type we can only save one
                         * otherwise relationships will be duplicated
                         * if event will break otherwise (physical entity will fall to default
                         */
                        if (instance.getSchemClass().isa(ReactomeJavaConstants.Event)) {
                            GKInstance species = (GKInstance) getObjectFromGkInstance(instance, ReactomeJavaConstants.species);
                            if (species == null) continue;
                            if (species.getDBID().equals(48887L)) {
                                Collection inferredTo = getCollectionFromGkInstance(instance, ReactomeJavaConstants.orthologousEvent);
                                if (inferredTo != null && !inferredTo.isEmpty()) {
                                    saveRelationships(id, inferredTo, "inferredTo");
                                } else {
                                    Collection referrers = getCollectionFromGkInstanceReferrals(instance, ReactomeJavaConstants.orthologousEvent);
                                    if (referrers != null && !referrers.isEmpty()) {
                                        saveRelationships(id, referrers, "inferredTo");
                                        errorLogger.error("Entry has referred orthologous but no attribute orthologous: " +
                                                instance.getDBID() + " " + instance.getDisplayName());
                                    }
                                }
                            }
                            break;
                        }
                    default:
                        if (isValidGkInstanceAttribute(instance, attribute)) {
                            saveRelationships(id, getCollectionFromGkInstance(instance, attribute), attribute);
                        }
                        break;
                }
            }
        }
        instance.deflate(); //will ensure that the use of the GkInstance does not end in an OutOfMemory exception
        return id;
    }

    /**
     * Saves one single GkInstance to neo4j. Only primitive attributes will be saved (Attributes that are not reference
     * to another GkInstance eg values like Strings)
     * Get the attributes map and check null is slightly faster than contains.
     * @param instance GkInstance
     * @param clazz Clazz of object that will result form converting the instance (eg Pathway, Reaction)
     * @return Neo4j native id (generated by the BatchInserter)
     */
    private Long saveDatabaseObject(GKInstance instance, Class clazz) throws IllegalArgumentException{

        Label[] labels = getLabels(clazz);
        Map<String, Object> properties = new HashMap<>();
        properties.put(DBID, instance.getDBID());
        if (instance.getDisplayName() != null) {
            properties.put(NAME, instance.getDisplayName());
        } else {
            errorLogger.error("Found an entry without display name! dbId: " + instance.getDBID());
        }

        if (primitiveAttributesMap.containsKey(clazz)) {
            for (String attribute : primitiveAttributesMap.get(clazz)) {
                switch (attribute) {
                    case STID:
                        GKInstance stableIdentifier = (GKInstance) getObjectFromGkInstance(instance, attribute);
                        if (stableIdentifier == null) continue;
                        String identifier = (String) getObjectFromGkInstance(stableIdentifier, ReactomeJavaConstants.identifier);
                        if (identifier == null) continue;
                        properties.put(attribute, identifier);
                        break;
                    case "hasDiagram":
                        if (instance.getDbAdaptor() instanceof MySQLAdaptor) {
                            try {
                                DiagramGeneratorFromDB diagramHelper = new DiagramGeneratorFromDB();
                                diagramHelper.setMySQLAdaptor((MySQLAdaptor) instance.getDbAdaptor());
                                GKInstance diagram = diagramHelper.getPathwayDiagram(instance);
                                properties.put(attribute, diagram != null);
                            } catch (Exception e) {
                                errorLogger.error("An exception occurred while trying to retrieve a diagram from entry with dbId: " +
                                        instance.getDBID() + "and name: " + instance.getDisplayName());
                            }
                        }
                        break;
                    case "isInDisease":
                        GKInstance disease = (GKInstance) getObjectFromGkInstance(instance, ReactomeJavaConstants.disease);
                        properties.put(attribute, disease != null);
                        break;
                    case "isInferred":
                        GKInstance isInferredFrom = (GKInstance) getObjectFromGkInstance(instance, ReactomeJavaConstants.inferredFrom);
                        properties.put(attribute, isInferredFrom != null);
                        break;
                    case "referenceType":
                        GKInstance referenceEntity = (GKInstance) getObjectFromGkInstance(instance, ReactomeJavaConstants.referenceEntity);
                        if (referenceEntity == null) continue;
                        properties.put(attribute, referenceEntity.getSchemClass().getName());
                        break;
                    case "speciesName":
                        List speciesList = (List) getCollectionFromGkInstance(instance, ReactomeJavaConstants.species);
                        if (speciesList == null || speciesList.isEmpty()) continue;
                        GKInstance species = (GKInstance) speciesList.get(0);
                        properties.put(attribute, species.getDisplayName());
                        break;
                    case "url":
                        if (instance.getSchemClass().isa(ReactomeJavaConstants.ReferenceDatabase) ||
                                instance.getSchemClass().isa(ReactomeJavaConstants.Figure)) continue;
                        GKInstance referenceDatabase = (GKInstance) getObjectFromGkInstance(instance, ReactomeJavaConstants.referenceDatabase);
                        if (referenceDatabase == null) continue;
                        identifier = (String) getObjectFromGkInstance(instance, ReactomeJavaConstants.identifier);
                        String url = (String) getObjectFromGkInstance(referenceDatabase, ReactomeJavaConstants.url);
                        if (url == null || identifier == null) continue;
                        properties.put(attribute, url.replace("###ID###", identifier));
                        break;
                    default:
                        if (isValidGkInstanceAttribute(instance, attribute)) {
                            Object value = getObjectFromGkInstance(instance, attribute);
                            if (value == null) continue;
                            properties.put(attribute, value);
                        }
                        break;
                }
            }
        }

        if (primitiveListAttributesMap.containsKey(clazz)) {
            for (String attribute : primitiveListAttributesMap.get(clazz)) {
                if (isValidGkInstanceAttribute(instance, attribute)) {
                    Collection values = getCollectionFromGkInstance(instance, attribute);
                    if (values == null) continue;
                    properties.put(attribute, values.toArray(new String[values.size()]));
                }
            }
        }
        try {
            return batchInserter.createNode(properties, labels);
        } catch (IllegalArgumentException e) {
            throw new IllegalClassException("A problem occurred when trying to save entry to the Graph :" + instance.getDisplayName() + ":" + instance.getDBID());
        }
    }

    /**
     * Creating a relationships between the old instance (using oldId) and its children (List objects).
     * Relationships will be created depth first, if new instance does not already exist recursion will begin
     * (newId = importGkInstance)
     * Every relationship entry will have a stoichiometry attribute, which is used as a counter. The same input of a Reaction
     * for example can be present multiple times. Instead of saving a lot of relationships we just set a counter to indicate
     * this behaviour. Since we can not query using the Batch inserter we have to iterate the collection of relationships
     * first to identify the duplicates.
     * The stoichiometry map has to utilize a helperObject because the GkInstance does not implement Comparable and
     * comparing instances will not work. In the helperObject the instance and a counter will be saved. Counter is used
     * to set stoichiometry of a relationship.
     * @param oldId Old native neo4j id, used for saving a relationship to neo4j.
     * @param objects New list of GkInstances that have relationship to the old Instance (oldId).
     * @param relationName Name of the relationship.
     * @throws ClassNotFoundException
     */
    private void saveRelationships(Long oldId, Collection objects, String relationName) throws ClassNotFoundException {
        if (objects == null || objects.isEmpty()) return;

        Map<Long, GkInstanceStoichiometryHelper> stoichiometryMap = new HashMap<>();
        for (Object object : objects) {
            if (object instanceof GKInstance) {
                GKInstance instance = (GKInstance) object;
                if(stoichiometryMap.containsKey(instance.getDBID())){
                    stoichiometryMap.get(instance.getDBID()).increment();
                } else {
                    stoichiometryMap.put(instance.getDBID(), new GkInstanceStoichiometryHelper(instance, 1));
                }
            }
        }
        for (Long dbId : stoichiometryMap.keySet()) {

            GKInstance instance = stoichiometryMap.get(dbId).getInstance();
            Long newId;
            if (!dbIds.containsKey(dbId)) {
                newId = importGkInstance(instance);
                instance.deflate();
            } else {
                newId = dbIds.get(dbId);
            }
            Map<String, Object> properties = new HashMap<>();
            properties.put(STOICHIOMETRY,stoichiometryMap.get(dbId).getCount());
            RelationshipType relationshipType = DynamicRelationshipType.withName(relationName);
            saveRelationship(newId,oldId,relationshipType,properties);
        }
    }

    private void saveRelationship(Long newId, Long oldId, RelationshipType relationshipType, Map<String, Object> properties) {
        String relationName = relationshipType.name();
        switch (relationName) {
            case "reverseReaction":
                if (!(reverseReactions.containsKey(oldId) && reverseReactions.containsValue(newId)) &&
                        !(reverseReactions.containsKey(newId) && reverseReactions.containsValue(oldId))) {
                    batchInserter.createRelationship(oldId, newId, relationshipType, properties);
                    reverseReactions.put(oldId, newId);
                }
                break;
            case "equivalentTo":
                if (!(equivalentTo.containsKey(oldId) && equivalentTo.containsValue(newId)) &&
                        !(equivalentTo.containsKey(newId) && equivalentTo.containsValue(oldId))) {
                    batchInserter.createRelationship(oldId, newId, relationshipType, properties);
                    equivalentTo.put(oldId, newId);
                }
                break;
            case "author":
            case "authored":
            case "created":
            case "edited":
            case "modified":
            case "revised":
            case "reviewed":
                batchInserter.createRelationship(newId, oldId, relationshipType, properties);
                break;
            default:
                batchInserter.createRelationship(oldId, newId, relationshipType, properties);
                break;
        }
    }

    /**
     * Cleaning the old database folder, instantiate BatchInserter, create Constraints for the new DB
     */
    private void prepareDatabase() throws IOException {

        File file = cleanDatabase();
        batchInserter = BatchInserters.inserter(file);
        createConstraints();
    }

    /**
     * Creating uniqueness constraints for the new DB.
     * WARNING: Constraints can not be enforced while importing, only after batchInserter.shutdown()
     */
    private void createConstraints() {

        createSchemaConstraint(DynamicLabel.label(DatabaseObject.class.getSimpleName()), DBID);
        createSchemaConstraint(DynamicLabel.label(DatabaseObject.class.getSimpleName()), STID);

        createSchemaConstraint(DynamicLabel.label(Event.class.getSimpleName()),DBID);
        createSchemaConstraint(DynamicLabel.label(Event.class.getSimpleName()),STID);

        createSchemaConstraint(DynamicLabel.label(Pathway.class.getSimpleName()),DBID);
        createSchemaConstraint(DynamicLabel.label(Pathway.class.getSimpleName()),STID);

        createSchemaConstraint(DynamicLabel.label(ReactionLikeEvent.class.getSimpleName()),DBID);
        createSchemaConstraint(DynamicLabel.label(ReactionLikeEvent.class.getSimpleName()),STID);

        createSchemaConstraint(DynamicLabel.label(Reaction.class.getSimpleName()),DBID);
        createSchemaConstraint(DynamicLabel.label(Reaction.class.getSimpleName()),STID);

        createSchemaConstraint(DynamicLabel.label(PhysicalEntity.class.getSimpleName()),DBID);
        createSchemaConstraint(DynamicLabel.label(PhysicalEntity.class.getSimpleName()),STID);

        createSchemaConstraint(DynamicLabel.label(Complex.class.getSimpleName()),DBID);
        createSchemaConstraint(DynamicLabel.label(Complex.class.getSimpleName()),STID);

        createSchemaConstraint(DynamicLabel.label(EntitySet.class.getSimpleName()),DBID);
        createSchemaConstraint(DynamicLabel.label(EntitySet.class.getSimpleName()),STID);

        createSchemaConstraint(DynamicLabel.label(GenomeEncodedEntity.class.getSimpleName()),DBID);
        createSchemaConstraint(DynamicLabel.label(GenomeEncodedEntity.class.getSimpleName()),STID);

        createSchemaConstraint(DynamicLabel.label(ReferenceEntity.class.getSimpleName()),DBID);
        createSchemaConstraint(DynamicLabel.label(ReferenceEntity.class.getSimpleName()),STID);

        batchInserter.createDeferredSchemaIndex(DynamicLabel.label(ReferenceEntity.class.getSimpleName())).on(ACCESSION);
    }

    /**
     * Simple wrapper for creating a isUnique constraint
     * @param label Label (of specific Class)
     * @param name fieldName
     */
    private static void createSchemaConstraint(Label label, String name) {
        try {
            batchInserter.createDeferredConstraint(label).assertPropertyIsUnique(name).create();
        } catch (ConstraintViolationException e) {
            importLogger.warn("Could not create Constraint on " + label + " " + name);
        }
    }

    /**
     * Simple method that prints a progress bar to command line
     * @param done Number of entries added to the graph
     * @param total Total number of entries to be importet
     */
    private void updateProgressBar(int done, int total) {
        String format = "\r%3d%% %s %c";
        char[] rotators = {'|', '/', '-', '\\'};
        int percent = (++done * 100) / total;
        StringBuilder progress = new StringBuilder(width);
        progress.append('|');
        int i = 0;
        for (; i < percent; i++)
            progress.append("=");
        for (; i < width; i++)
            progress.append(" ");
        progress.append('|');
        System.out.printf(format, percent, progress, rotators[(((done - 1) % (rotators.length * 100)) /100)]);
    }

    /**
     * Cleaning the Neo4j data directory
     */
    private File cleanDatabase() {

        File dir = new File(DATA_DIR);
        try {
            if(dir.exists()) {
                FileUtils.cleanDirectory(dir);
            } else {
                FileUtils.forceMkdir(dir);
            }
        } catch (IOException | IllegalArgumentException e) {
            importLogger.warn("An error occurred while cleaning the old database");
        }
        return dir;
    }

    /**
     * Getting all SimpleNames as neo4j labels, for given class.
     * @param clazz Clazz of object that will result form converting the instance (eg Pathway, Reaction)
     * @return Array of Neo4j LabelsCount
     */
    private Label[] getLabels(Class clazz) {

        if(!labelMap.containsKey(clazz)) {
            Label[] labels = getAllClassNames(clazz);
            labelMap.put(clazz, labels);
            return labels;
        } else {
            return labelMap.get(clazz);
        }
    }

    /**
     * Getting all SimpleNames as neo4j labels, for given class.
     * @param clazz Clazz of object that will result form converting the instance (eg Pathway, Reaction)
     * @return Array of Neo4j LabelsCount
     */
    private Label[] getAllClassNames(Class clazz) {
        List<?> superClasses = ClassUtils.getAllSuperclasses(clazz);
        List<Label> labels = new ArrayList<>();
        labels.add(DynamicLabel.label(clazz.getSimpleName()));
        for (Object object : superClasses) {
            Class superClass = (Class) object;
            if(!superClass.equals(Object.class)) {
                labels.add(DynamicLabel.label(superClass.getSimpleName()));
            }
        }
        return labels.toArray(new Label[labels.size()]);
    }

    @SuppressWarnings("wrong")
    /**
     * Gets all Fields for specific Class in order to create attribute map.
     * Annotations are used to differentiate attributes:
     *      @Relationship is used to indicate a relationship that should be saved to the graph
     *      @Transient is used for relationships that should not be persisted by the graph
     *      @ReactomeTransient is used for all entries that can not be filled by the GkInstance automatically
     *      Not annotated fields will be treated as primitive attributes (String, Long, List<String>...)
     *      Twice annotated fields will not be filled by the GkInstance
     * @param clazz Clazz of object that will result form converting the instance (eg Pathway, Reaction)
     */
    private void setUpMethods(Class clazz) {
        if(!relationAttributesMap.containsKey(clazz) && !primitiveAttributesMap.containsKey(clazz)) {
            List<Field> fields = getAllFields(new ArrayList<Field>(), clazz);
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                String fieldName = field.getName();
                if (annotations.length == 0) {
                    if (Collection.class.isAssignableFrom(field.getType())) {
                        addFields(primitiveListAttributesMap, clazz, fieldName);
                    } else {
                        addFields(primitiveAttributesMap, clazz, fieldName);
                    }
                } else if (annotations.length == 1) {
                    Class annotationType = annotations[0].annotationType();
                    if (annotationType.equals(Relationship.class)) {
                        addFields(relationAttributesMap, clazz, fieldName);
                    }
                }
            }
        }
    }

    /**
     * Method used to get all fields for given class, event inherited fields
     * @param fields List of fields for storing fields during recursion
     * @param type Current class
     * @return inherited and declared fields
     */
    public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null && !type.getSuperclass().equals(Object.class)) {
            fields = getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }

    /**
     * Put Attribute name into map.
     * @param map attribute map
     * @param clazz Clazz of object that will result form converting the instance (eg Pathway, Reaction)
     */
    private void addFields (Map<Class,List<String>> map, Class clazz, String fieldName) {
        if(map.containsKey(clazz)) {
            (map.get(clazz)).add(fieldName);
        } else {
            List<String> methodList = new ArrayList<>();
            methodList.add(fieldName);
            map.put(clazz, methodList);
        }
    }

    /**
     * Checks if an attributeName is a valid attribute for a specific instance
     * @param instance GkInstance
     * @param attribute FieldName
     * @return boolean
     */
    private boolean isValidGkInstanceAttribute(GKInstance instance, String attribute) {
        if(instance.getSchemClass().isValidAttribute(attribute)) {
            return true;
        }
        errorLogger.warn(attribute + " is not a valid attribute for instance " + instance.getSchemClass());
        return false;
    }

    /**
     * A simple wrapper of the GkInstance.getAttributeValue Method used for error handling
     * @param instance GkInstance
     * @param attribute FieldName
     * @return Object
     */
    private Object getObjectFromGkInstance(GKInstance instance, String attribute) {
        if (isValidGkInstanceAttribute(instance,attribute)) {
            try {
                return instance.getAttributeValue(attribute);
            } catch (Exception e) {
                errorLogger.error("An error occurred when trying to retrieve an attribute from instance with DbId:"
                        + instance.getDBID() + " and Name:" + instance.getDisplayName(),e);
            }
        }
        return null;
    }

    /**
     * A simple wrapper of the GkInstance.getAttributeValueList Method used for error handling
     * @param instance GkInstance
     * @param attribute FieldName
     * @return Object
     */
    private Collection getCollectionFromGkInstance(GKInstance instance, String attribute) {
        if (isValidGkInstanceAttribute(instance,attribute)) {
            try {
                return instance.getAttributeValuesList(attribute);
            } catch (Exception e) {
                errorLogger.error("An error occurred when trying to retrieve attributes from instance with DbId:"
                        + instance.getDBID() + " and Name:" + instance.getDisplayName(),e);
            }
        }
        return null;
    }

    /**
     * A simple wrapper of the GkInstance.getReferrers Method used for error handling
     * @param instance GkInstance
     * @param attribute FieldName
     * @return Object
     */
    private Collection getCollectionFromGkInstanceReferrals(GKInstance instance, String attribute) {
        try {
            return instance.getReferers(attribute);
        } catch (Exception e) {
            errorLogger.error("An error occurred when trying to retrieve referrals from instance with DbId:"
                    + instance.getDBID() + " and Name:" + instance.getDisplayName(),e);
        }
        return null;
    }
}