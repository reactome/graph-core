package uk.ac.ebi.reactome.data;

import org.gk.model.GKInstance;
import org.gk.model.ReactomeJavaConstants;
import org.gk.persistence.MySQLAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.repository.GenericRepository;
import uk.ac.ebi.reactome.service.DatabaseObjectService;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by reactome on 11/12/15.
 */
@Component
public class ReactomeImporter {


    private static MySQLAdaptor dba;
    private Set<Long> dbIds;

    @Autowired
    private DatabaseObjectService databaseObjectService;

    @Autowired
    private GenericRepository genericRepository;

    static {
        try {
            System.out.println("setup connection to DB");
            dba = new MySQLAdaptor("localhost", "Reactome", "root","reactome", 3306);
        } catch (SQLException e) {
//            logger.error("An error occured while connection to the Reactome database", e);
//            throw new ReactomeParserException("An error occured while connection to the Reactome database", e);
        }
    }

    public void loadObject(String identifier) throws Exception {
        dbIds = new HashSet<>();
        GKInstance instance = getInstance(identifier);
        createObject(instance,1L,"");

    }

    private void createObject(GKInstance instance, Long id, String x) throws Exception {
        System.out.println(dbIds.contains(instance.getDBID()));
        Long dbId = instance.getDBID();
        if (!dbIds.contains(dbId)) {
            dbIds.add(dbId);
            String clazzName = "uk.ac.ebi.reactome.domain.model." + instance.getSchemClass().getName();

            Class<DatabaseObject> clazz = (Class<DatabaseObject>) Class.forName(clazzName);
            if (!clazz.isEnum()) {
                DatabaseObject obj = clazz.newInstance();
                obj.setDbId(instance.getDBID());
                obj.setDisplayName(instance.getDisplayName());
                obj = databaseObjectService.save(obj);
                fill(obj, instance);
                obj = databaseObjectService.save(obj);

            }
        }
        genericRepository.addRelationship(instance.getDBID(), id,x);

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
                            createObject((GKInstance) value,instance.getDBID(),rAttName);

//                            createObject((GKInstance) value);
//                            genericRepository.addRelationship(instance.getDBID(), ((GKInstance) value).getDBID(),rAttName);

//                            instance.getDBID, ((GKInstance) value).getDBID();
                            //return value form createObject ?
                        }
                        else if (!caValues.contains(value)) {
                            caValues.add(value);
                        }
                    }
                    // Convert Set to List.
                    setMethod.invoke(obj, caValues);
                }
                else {
                    // One value is enough
                    Object value = attValues.get(0);
                    // Need to convert to caBIO type
                    if (value instanceof GKInstance) {
                        createObject((GKInstance) value,instance.getDBID(),rAttName);
//                        createObject((GKInstance) value);
//                        genericRepository.addRelationship(instance.getDBID(), ((GKInstance) value).getDBID(),rAttName);
//                        recursion here aswell
                    } else {
                        setMethod.invoke(obj, value);
                    }
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
