package uk.ac.ebi.reactome.data;

import org.apache.log4j.Logger;
import org.gk.model.GKInstance;
import org.gk.model.ReactomeJavaConstants;
import org.gk.persistence.MySQLAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.service.DatabaseObjectService;
import uk.ac.ebi.reactome.service.GenericService;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by reactome on 11/12/15.
 */
@Component
public class ReactomeImporter {

    private static final Logger logger = Logger.getLogger(ReactomeImporter.class);

    private static MySQLAdaptor dba;
    private Map<Long,DatabaseObject> dbIds;

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @Autowired
    private GenericService genericService;

    static {
        try {
            System.out.println("setup connection to DB");
            dba = new MySQLAdaptor("localhost", "Reactome", "root","reactome", 3306);

        } catch (SQLException e) {
//            logger.error("An error occured while connection to the Reactome database", e);
//            throw new ReactomeParserException("An error occured while connection to the Reactome database", e);
        }
    }

    public DatabaseObject loadObject(String identifier) throws Exception {
        dbIds = new HashMap<>();
        GKInstance instance = getInstance(identifier);
        return createObject(instance,1L,"");

    }


    public void loadAll() {
        try {
            dbIds = new HashMap<>();
            Collection<?> frontPage = dba.fetchInstancesByClass(ReactomeJavaConstants.FrontPage);
            GKInstance instance1 = (GKInstance) frontPage.iterator().next();
            Collection<GKInstance> instances = instance1.getAttributeValuesList(ReactomeJavaConstants.frontPageItem);
            for (GKInstance instance : instances) {
                DatabaseObject obj = createObject(instance,1L,"");

            }
        } catch (Exception e) {
            logger.error("An error occured while parsing FrontPage items", e);
        }
    }
    @Transactional
    private void parseInstance(GKInstance instance) throws Exception {
        long startTime = System.currentTimeMillis();
        createObject(instance, 1L, "");
        long stopTime = System.currentTimeMillis();
        long ms = stopTime - startTime;
        int seconds = (int) (ms / 1000) % 60;
        int minutes = (int) ((ms / (1000 * 60)) % 60);
        logger.info(instance.getDisplayName() + " indexed in: " + minutes + "minutes " + seconds + "seconds" );
    }

    private DatabaseObject createObject(GKInstance instance, Long id, String x) throws Exception {
//        System.out.println(dbIds.contains(instance.getDBID()));
        Long dbId = instance.getDBID();
        if (!dbIds.containsKey(dbId)) {
            DatabaseObject obj = null;
            String clazzName = "uk.ac.ebi.reactome.domain.model." + instance.getSchemClass().getName();

            Class<DatabaseObject> clazz = (Class<DatabaseObject>) Class.forName(clazzName);
            if (!clazz.isEnum()) {
                obj = clazz.newInstance();
                obj.setDbId(instance.getDBID());
                obj.setDisplayName(instance.getDisplayName());

//                obj = databaseObjectService.save(obj);
                dbIds.put(dbId,obj);
                fill(obj, instance);

            }


            return obj;

        }
        return dbIds.get(dbId);
//        genericRepository.addRelationship(instance.getDBID(), id,x);

    }

    private List<String> getJavaBeanProperties(Class clazz) {
        Method[] methods = clazz.getMethods();
        String methodName;
        String propertyName;
        List<String> propertyNames = new ArrayList<>();
        for (Method method : methods) {
            methodName = method.getName();
            if (methodName.startsWith("get")) {
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
    private void fill(DatabaseObject obj, GKInstance instance) throws Exception {
        Class clazz = obj.getClass();
        List<String> propNames = getJavaBeanProperties(clazz);
        if (propNames.size() == 0) return;
        String rAttName = null;
        for (String propName : propNames) {
            rAttName = lowerFirst(propName);

            // Always use a simple mapper first
            if (instance.getSchemClass().isValidAttribute(rAttName)) {
                List attValues = instance.getAttributeValuesList(rAttName);
                if (attValues == null || attValues.size() == 0)
                    continue;
                // Check the required type is a list or just a value
                Method setMethod = getNamedMethod(obj, "set" + propName);
                Class argType = setMethod.getParameterTypes()[0]; // Only the first matters
                if (argType.equals(List.class)) {
                    // Need the whole list
                    // During reflection, the generic type in the list is gone.
                    // Use a really generic List type to avoid the warding from the compiler
                    List<Object> caValues = new ArrayList<Object>();
                    for (Object value : attValues) {
                        if (value instanceof GKInstance) {
                            value = createObject((GKInstance) value,instance.getDBID(),rAttName);

//                            createObject((GKInstance) value);
//                            genericRepository.addRelationship(instance.getDBID(), ((GKInstance) value).getDBID(),rAttName);

//                            instance.getDBID, ((GKInstance) value).getDBID();
                            //return value form createObject ?
                        }
//                        if (!caValues.contains(value)) {
                            caValues.add(value);
//                        }
                    }
                    // Convert Set to List.
                    setMethod.invoke(obj, caValues);
                }
                else {
                    // One value is enough
                    Object value = attValues.get(0);
                    // Need to convert to caBIO type
                    if (value instanceof GKInstance) {
                        value = createObject((GKInstance) value,instance.getDBID(),rAttName);
//                        createObject((GKInstance) value);
//                        genericRepository.addRelationship(instance.getDBID(), ((GKInstance) value).getDBID(),rAttName);
//                        recursion here aswell
                    }
//                    else {
                        setMethod.invoke(obj, value);
//                    }
                    // All other types can be converted safely

//                    try {
//
//                    } catch (IllegalArgumentException e) {
//                        logger.error(obj.getClass().getName() + "." + setMethod.getName() + " using " + value, e);
//                    }
                }
            }
        }
    }


    private static String lowerFirst(String propName) {
        return propName.substring(0, 1).toLowerCase() + propName.substring(1);
    }

    private static String upperFirst(String propName) {
        return propName.substring(0, 1).toUpperCase() + propName.substring(1);
    }

    @SuppressWarnings("unchecked")
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
    private  GKInstance getInstance(Collection<GKInstance> target) throws Exception {
        if(target==null || target.size()!=1) throw new Exception("Many options have been found fot the specified identifier");
        GKInstance stId = target.iterator().next();
        return (GKInstance) dba.fetchInstanceByAttribute(ReactomeJavaConstants.DatabaseObject, ReactomeJavaConstants.stableIdentifier, "=", stId).iterator().next();
    }

}
