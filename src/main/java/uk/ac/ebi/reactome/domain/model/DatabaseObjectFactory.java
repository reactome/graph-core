package uk.ac.ebi.reactome.domain.model;

import org.gk.model.GKInstance;
import org.gk.model.ReactomeJavaConstants;
import org.gk.persistence.MySQLAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.reactome.service.DatabaseObjectService;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

@Component
@SuppressWarnings("Duplicates")
public class DatabaseObjectFactory {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseObjectFactory.class);

    private static DatabaseObjectService databaseObjectService;

    @Autowired
    public void setDatabaseObjectService(DatabaseObjectService databaseObjectService){
        DatabaseObjectFactory.databaseObjectService = databaseObjectService;
    }

    private static Set<Long> dbIds = new HashSet<>();
    private static MySQLAdaptor dba;
//    You can also construct a similar block
//    This gets called everytime the class is constructed. The static block only gets called once, no matter how many objects of that type you create.
    static {
        try {
            System.out.println("setup connection to DB");
            dba = new MySQLAdaptor("localhost", "reactome", "root","reactome", 3306);
        } catch (SQLException e) {
            logger.error("An error occured while connection to the Reactome database", e);
//            throw new ReactomeParserException("An error occured while connection to the Reactome database", e);
        }
    }

    public static <T extends DatabaseObject> T createObject(String identifier) throws Exception {
        GKInstance instance = getInstance(identifier);
        DatabaseObject object = createObject(instance).load();
        //noinspection unchecked
        return (T) object;
    }

//    public static DatabaseObject createObject(String identifier) throws Exception {
//        GKInstance instance = getInstance(identifier);
//        DatabaseObject object = createObject(instance).load();
////        fill(object, instance);
//        return object;
//    }


    public static DatabaseObject createObject(GKInstance instance) throws Exception {

        if (!dbIds.contains(instance.getDBID())) {
            dbIds.add(instance.getDBID());

            DatabaseObject obj = null;

            String clsName = DatabaseObjectFactory.class.getPackage().getName() + "." + instance.getSchemClass().getName();
            //noinspection unchecked
            Class<DatabaseObject> cls = (Class<DatabaseObject>) Class.forName(clsName);
            if (!cls.isEnum()) {
                obj = cls.newInstance();
                obj.setDbId(instance.getDBID());
                obj.setDisplayName(instance.getDisplayName());
//            fill(obj,instance);
                try {
                    if (!obj.isLoaded) {
                        obj.load();
                        databaseObjectService.save(obj);
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
//            ReactomeModelPostMapper mapper = postMapperFactory.getPostMapper(instance);
//            if (mapper != null)
//                mapper.postShellProcess(instance, obj);
            }

            return obj;

        }
        return null;
    }

//    public static DatabaseObject fetchInstance(String id) throws Exception {
//        GKInstance instance = getInstance(id);
//        if (instance == null) {
//            throw new Exception(id + " not found");
//        }
//        return createObject(instance);
//    }

    @SuppressWarnings("unchecked")
    public static GKInstance getInstance(String identifier) throws Exception {
        identifier = identifier.trim().split("\\.")[0];
        if (identifier.startsWith("REACT")){
            return getInstance(dba.fetchInstanceByAttribute(ReactomeJavaConstants.StableIdentifier, "oldIdentifier", "=", identifier));
        }else if (identifier.startsWith("R-")) {
            return getInstance(dba.fetchInstanceByAttribute(ReactomeJavaConstants.StableIdentifier, ReactomeJavaConstants.identifier, "=", identifier));
        } else {
            return dba.fetchInstance(Long.parseLong(identifier));
        }
    }

    private static GKInstance getInstance(Collection<GKInstance> target) throws Exception {
        if(target==null || target.size()!=1) throw new Exception("Many options have been found fot the specified identifier");
        GKInstance stId = target.iterator().next();
        return (GKInstance) dba.fetchInstanceByAttribute(ReactomeJavaConstants.DatabaseObject, ReactomeJavaConstants.stableIdentifier, "=", stId).iterator().next();
    }

    public static void fill(DatabaseObject obj, GKInstance instance) throws Exception {
        if (!dbIds.contains(instance.getDBID())) {
            dbIds.add(instance.getDBID());
        if (obj == null) return;
        Class cls = obj.getClass();
        // Get all properties via setXXX methods
        List<String> propNames = getJavaBeanProperties(cls);
        if (propNames == null || propNames.size() == 0) return; // No writable properties are defined.
        String rAttName = null;
        for (String propName : propNames) {
            rAttName = lowerFirst(propName);

            // Always use a simple mapper first
            if (rAttName != null && instance.getSchemClass().isValidAttribute(rAttName)) {
                Collection x =  instance.getSchemaAttributes();
                Collection y =  instance.getSchemClass().getAttributes();
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
                            try {
                                value = createObject((GKInstance) value);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
//                            do the recursion here
                        } /*else*/ if (!caValues.contains(value)) {
                            caValues.add(value);
                        }
                    }
                    // Convert Set to List.
                    setMethod.invoke(obj, new Object[]{caValues});
                }
                else {
                    // One value is enough
                    Object value = attValues.get(0);
                    // Need to convert to caBIO type
                    if (value instanceof GKInstance) {
                        value = createObject((GKInstance) value);
//                        recursion here aswell
                    }
                    // All other types can be converted safely
                    setMethod.invoke(obj, value);
//                    try {
//
//                    } catch (IllegalArgumentException e) {
//                        logger.error(obj.getClass().getName() + "." + setMethod.getName() + " using " + value, e);
//                    }
                }
            }
        }
        } else {
        }
//        ReactomeModelPostMapper mapper = postMapperFactory.getPostMapper(instance);
//        if (mapper != null)
//            mapper.postProcess(instance,
//                    obj,
//                    this);
    }


    private static Method getNamedMethod(Object target, String methodName) {
        Method[] methods = target.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().equals(methodName))
                return m;
        }
        return null;
    }


    private static String lowerFirst(String propName) {
        return propName.substring(0, 1).toLowerCase() + propName.substring(1);
    }

    private static String upperFirst(String propName) {
        return propName.substring(0, 1).toUpperCase() + propName.substring(1);
    }

    private static List<String> getJavaBeanProperties(Class cls) {
        Method[] methods = cls.getMethods();
        // Check method name
        String methodName;
        String propName;
        List<String> propNames = new ArrayList<>();
        for (Method m : methods) {
            methodName = m.getName();
            if (methodName.startsWith("get")) {
                propName = methodName.substring(3);
                if (propName.length() > 0) {
                    // Have to lower the first case
                    propNames.add(propName);
                }
            }
        }
        return propNames;
    }
}
