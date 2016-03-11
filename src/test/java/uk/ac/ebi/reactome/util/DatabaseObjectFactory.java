package uk.ac.ebi.reactome.util;

import org.gk.model.GKInstance;
import org.gk.model.ReactomeJavaConstants;
import org.gk.persistence.MySQLAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.service.util.DatabaseObjectUtils;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

public class DatabaseObjectFactory {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseObjectFactory.class);

    /**
     * The package name is used to create Object from GkInstance by reflection
     */
    private static final String PACKAGE_NAME = "uk.ac.ebi.reactome.domain.model.";

    /**
     * Reactome MySql database adapter
     */
    @SuppressWarnings("CanBeFinal") //can not be final
    private static MySQLAdaptor dba;

    /**
     * Static initialisation block to create a connection to the Reactome MySql database.
     * Properties inside of static init block can not be loaded by Spring (with eg. @Value annotation)
     * Getting Properties as stream is the only option for retrieving properties for "static" class.
     */
    static {
        try {
            String host = System.getProperty("reactome.host");
            String port = System.getProperty("reactome.port");
            String database = System.getProperty("reactome.database");
            String user = System.getProperty("reactome.user");
            String password = System.getProperty("reactome.password");
            dba = new MySQLAdaptor(host,database,user,password,Integer.parseInt(port));
            dba.setUseCache(false);
            logger.info("Established connection to Reactome database");
        } catch (SQLException e) {
            logger.error("An error occurred while connection to the Reactome database", e);
        }
    }

    /**
     * Method used for loading all Reactome top level pathways.
     * @return List of DatabaseObject (list of top level pathways)
     */
    public static List<DatabaseObject> loadFrontPageItems() {

        List<DatabaseObject> databaseObjects = new ArrayList<>();
        try {
            Collection<?> frontPages = dba.fetchInstancesByClass(ReactomeJavaConstants.FrontPage);
            GKInstance frontPage = (GKInstance) frontPages.iterator().next();
            Collection<?> objects = frontPage.getAttributeValuesList(ReactomeJavaConstants.frontPageItem);
            for (Object object : objects) {
                GKInstance instance = (GKInstance) object;
                DatabaseObject databaseObject = createObject(instance);
                if (databaseObject == null) continue;
                databaseObjects.add(databaseObject);
            }
        } catch (Exception e) {
            logger.error("An error occurred while trying to load Reactome top level pathways", e);
        }
        return databaseObjects;
    }

    /**
     * Method to create an object from a given Reactome identifier.
     * @param identifier StableIdentifier or dbId
     * @param <T> TODO
     * @return Object that extends DatabaseObject
     */
    @SuppressWarnings("unchecked")
    public static <T extends DatabaseObject> T createObject(String identifier)  {

        try {
            GKInstance instance  = getInstance(identifier);
            DatabaseObject databaseObject = createObject(instance);
            if (databaseObject != null) {
                load(databaseObject,instance);
                return (T) databaseObject;
            }
        } catch (Exception e) {
            logger.error("An error occurred while trying to create an object from identifier " + identifier, e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static GKInstance getInstance(String identifier) throws Exception {

        identifier = identifier.trim().split("\\.")[0];
        if (identifier.startsWith("REACT")){
            return getInstance(dba.fetchInstanceByAttribute(ReactomeJavaConstants.StableIdentifier, "oldIdentifier", "=", identifier));
        }else if (identifier.startsWith("R-")) {
            return getInstance(dba.fetchInstanceByAttribute(ReactomeJavaConstants.StableIdentifier, ReactomeJavaConstants.identifier, "=", identifier));
        } else {
            return dba.fetchInstance(Long.parseLong(identifier));
        }
    }

    public static void load(DatabaseObject databaseObject, GKInstance instance) {
        try {
            fill(databaseObject,instance);
        } catch (Exception e) {
            logger.error("An error occurred while trying to fill " + databaseObject.getSchemaClass() + ": " +
                    databaseObject.getDbId() + " " + databaseObject,e);
        }
    }

    public static void clearCache() throws Exception {
        dba.refresh();
//        dba.setUseCache(false);
    }

    private static void fill(DatabaseObject databaseObject, GKInstance instance) {
        try {
            Class clazz = databaseObject.getClass();
            List<String> propNames = getJavaBeanProperties(clazz);
            if (propNames.size() == 0) return;
            String attributeName;
            for (String propName : propNames) {
                attributeName = DatabaseObjectUtils.lowerFirst(propName);
                if (instance.getSchemClass().isValidAttribute(attributeName)) {
                    List attValues = instance.getAttributeValuesList(attributeName);
                    if (attValues == null || attValues.size() == 0) continue;
                    Method setMethod = getNamedMethod(databaseObject, "set" + propName);
                    if (setMethod == null) continue;
                    Class argType = setMethod.getParameterTypes()[0];
                    if (Collection.class.isAssignableFrom(argType)) {
                        Collection<Object> caValues;
                        if (Set.class.isAssignableFrom(argType)) {
                            caValues = new HashSet<>();
                        } else {
                            caValues = new ArrayList<>();
                        }
                        for (Object value : attValues) {
                            if (value instanceof GKInstance) {
                                value = createObject((GKInstance) value);
                            }
                            caValues.add(value);
                        }
                        setMethod.invoke(databaseObject, caValues);
                    } else {
                        Object value = attValues.get(0);
                        if (value instanceof GKInstance) {
                            GKInstance gkInstance = (GKInstance) value;
                            if (gkInstance.getSchemClass().isa(ReactomeJavaConstants.StableIdentifier)) {
                                value = gkInstance.getAttributeValue(ReactomeJavaConstants.identifier);
                            } else {
                                value = createObject((GKInstance) value);
                            }
                        }
                        setMethod.invoke(databaseObject, value);
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static DatabaseObject createObject(GKInstance instance) {

        String clazzName = PACKAGE_NAME + instance.getSchemClass().getName();
        try {
            Class<DatabaseObject> clazz = (Class<DatabaseObject>) Class.forName(clazzName);
            DatabaseObject databaseObject = clazz.newInstance();
            databaseObject.setDbId(instance.getDBID());
            databaseObject.setDisplayName(instance.getDisplayName());
            return databaseObject;
        } catch (ClassNotFoundException|InstantiationException|IllegalAccessException e) {
            logger.error("Error occurred when trying to get Class for Name " + clazzName);
        }
        return null ;
    }

    private static List<String> getJavaBeanProperties(Class clazz) {
        Method[] methods = clazz.getMethods();
        String methodName;
        String propertyName;
        List<String> propertyNames = new ArrayList<>();
        for (Method method : methods) {
            methodName = method.getName();
            if (methodName.startsWith("set")) {
                propertyName = methodName.substring(3);
                propertyNames.add(propertyName);
            }
        }
        return propertyNames;
    }

    private static Method getNamedMethod(Object target, String methodName) {
        Method[] methods = target.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().equals(methodName))
                return m;
        }
        return null;
    }

    private static GKInstance getInstance(Collection<GKInstance> target) throws Exception {

        if(target==null || target.size()!=1) throw new Exception("Many options have been found fot the specified identifier");
        GKInstance stId = target.iterator().next();
        return (GKInstance) dba.fetchInstanceByAttribute(ReactomeJavaConstants.DatabaseObject, ReactomeJavaConstants.stableIdentifier, "=", stId).iterator().next();
    }
}
