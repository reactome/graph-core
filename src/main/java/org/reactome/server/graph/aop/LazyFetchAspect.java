package org.reactome.server.graph.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.service.AdvancedDatabaseObjectService;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 */
@Aspect
@Component
public class LazyFetchAspect  {
    private Boolean enableAOP = true;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private AdvancedDatabaseObjectService advancedDatabaseObjectService;

    @Around("modelGetter()")
    public Object autoFetch(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("LazyFetchAspects => " + this.hashCode());

        if (!enableAOP) return pjp.proceed();

        // Target is the whole object that originated this pointcut.
        DatabaseObject databaseObject = (DatabaseObject) pjp.getTarget();

        // Gathering information of the method we are invoking and it's being intercepted by AOP
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        // Get the relationship that is annotated in the attribute
        Relationship relationship = getRelationship(method.getName(), databaseObject.getClass());
        boolean preventLazyLoading = databaseObject.preventLazyLoading != null && databaseObject.preventLazyLoading;
        if (relationship != null && !preventLazyLoading) { // && !databaseObject.isLoaded) {
            // pjp.proceed() has the result of the invoked method.
            Object result = pjp.proceed();
            // SDN6 now make sure all the list are EMPTY. Checking for null results as before won't be possible
            boolean proceed = result == null;
            if (!proceed) {
                if (Collection.class.isAssignableFrom(result.getClass())) {
                    // can't proceed and have to check if result is a list and list is empty.
                    proceed = ((Collection) result).isEmpty();
                }
            }
            if (proceed) {
                Long dbId = databaseObject.getDbId();
                String setterMethod = method.getName().replaceFirst("get", "set");
                Class<?> methodReturnClazz = method.getReturnType();
                if (Collection.class.isAssignableFrom(methodReturnClazz)) {
                    ParameterizedType stringListType = (ParameterizedType)  method.getGenericReturnType();
                    Class<?> type = (Class<?>) stringListType.getActualTypeArguments()[0];
                    String clazz = type.getSimpleName();

                    // DatabaseObject.isLoaded only works for OUTGOING relationships
                    //noinspection EqualsBetweenInconvertibleTypes
                    boolean isLoaded = (databaseObject.isLoaded != null && databaseObject.isLoaded) && relationship.equals(Relationship.Direction.OUTGOING);
                    // querying the graph and fill the collection if it hasn't been fully loaded before
                    Collection<DatabaseObject> lazyLoadedObjectAsCollection = isLoaded ? null : advancedDatabaseObjectService.findCollectionByRelationship(dbId, clazz, methodReturnClazz, RelationshipDirection.valueOf(relationship.direction().name()), relationship.type());
                    if (lazyLoadedObjectAsCollection == null) {
                        //If a set or list has been requested and is null, then we set empty collection to avoid requesting again
                        if (List.class.isAssignableFrom(methodReturnClazz)) lazyLoadedObjectAsCollection = new ArrayList<>();
                        if (Set.class.isAssignableFrom(methodReturnClazz)) lazyLoadedObjectAsCollection = new HashSet<>();
                    }
                    if (lazyLoadedObjectAsCollection != null) {
                        // invoke the setter in order to set the object in the target
                        databaseObject.getClass().getMethod(setterMethod, methodReturnClazz).invoke(databaseObject, lazyLoadedObjectAsCollection);
                        return lazyLoadedObjectAsCollection;
                    }
                }

                if (DatabaseObject.class.isAssignableFrom(methodReturnClazz)) {
                    String clazz = methodReturnClazz.getSimpleName();
                    // querying the graph and fill the single object
                    DatabaseObject lazyLoadedObject = advancedDatabaseObjectService.findByRelationship(dbId, clazz, RelationshipDirection.valueOf(relationship.direction().name()), relationship.type());
                    if (lazyLoadedObject != null) {
                        // invoke the setter in order to set the object in the target
                        databaseObject.getClass().getMethod(setterMethod, methodReturnClazz).invoke(databaseObject, lazyLoadedObject);
                        return lazyLoadedObject;
                    }
                }
            }
        }

        return pjp.proceed();
    }

    /**
     * AspectJ pointcut for all the getters that return a Collection of DatabaseObject
     * or instance of DatabaseObject.
     */
    @SuppressWarnings("SingleElementAnnotation")
    @Pointcut("execution(public java.util.Collection<org.reactome.server.graph.domain.model.DatabaseObject+>+ org.reactome.server.graph.domain.model.*.get*(..))" +
            "|| execution(public org.reactome.server.graph.domain.model.DatabaseObject+ org.reactome.server.graph.domain.model.*.get*(..))")
    public void modelGetter() {
    }

    /**
     * Method used to get the Relationship annotation on top of the
     * given attribute. This method looks for the annotation in the current class
     * and keep looking in the superclass.
     *
     * @return the Relationship annotation
     */
    private Relationship getRelationship(String methodName, Class<?> _clazz) {
        methodName = methodName.substring(3); // crop, remove 'get'
        char[] charArray = methodName.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]); // lower the first char
        String attribute = new String(charArray);

         // Look up for the given attribute in the class and after superclasses.
        //noinspection ClassGetClass
        while (_clazz != null && !_clazz.getClass().equals(Object.class)) {
            for (Field field : _clazz.getDeclaredFields()) {
                if (field.getAnnotation(Relationship.class) != null) {
                    if (field.getName().equals(attribute)) {
                        return field.getAnnotation(Relationship.class);
                    }
                }
            }

            // Didn't find the field in the given class. Check the Superclass.
            _clazz = _clazz.getSuperclass();
        }

        return null;
    }

    @SuppressWarnings("unused")
    public Boolean getEnableAOP() {
        return enableAOP;
    }

    public void setEnableAOP(boolean enableAOP) {
        this.enableAOP = enableAOP;
    }

}