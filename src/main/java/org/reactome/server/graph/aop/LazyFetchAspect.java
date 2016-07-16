package org.reactome.server.graph.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.neo4j.ogm.annotation.Relationship;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.service.AdvancedDatabaseObjectService;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 */
@Aspect
@Component
public class LazyFetchAspect {

    public Boolean enableAOP = true;

    @Autowired
    private AdvancedDatabaseObjectService advancedDatabaseObjectService;

    @Around("modelGetter()")
    public Object autoFetch(ProceedingJoinPoint pjp) throws Throwable {
        if(!enableAOP){
            return pjp.proceed();
        }

        /**
         * Target is the whole object that originated this pointcut.
         */
        DatabaseObject databaseObject = (DatabaseObject) pjp.getTarget();

        /**
         * Gathering information of the method we are invoking and it's being intercepted by AOP
         */
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        /**
         * Get the relationship that is annotated in the attribute
         */
        Relationship relationship = getRelationship(method.getName(), databaseObject.getClass());
        if (relationship != null) {
            /**
             * Check whether the object has been loaded.
             * pjp.proceed() has the result of the invoked method.
             */
            if (pjp.proceed() == null && !databaseObject.isLoaded) {
                Long dbId = databaseObject.getDbId();
                String setterMethod = method.getName().replaceFirst("get", "set");
                Class<?> methodReturnClazz = method.getReturnType();

                if (Collection.class.isAssignableFrom(method.getReturnType())) {
                    /** querying the graph and fill the collection **/
                    Collection<DatabaseObject> lazyLoadedObjectAsCollection = advancedDatabaseObjectService.findCollectionByRelationship(dbId, methodReturnClazz, RelationshipDirection.valueOf(relationship.direction()), relationship.type());
                    if (lazyLoadedObjectAsCollection != null) {
                        /** invoke the setter in order to set the object in the target **/
                        databaseObject.getClass().getMethod(setterMethod, methodReturnClazz).invoke(databaseObject, lazyLoadedObjectAsCollection);
                        return lazyLoadedObjectAsCollection;
                    }
                }

                if (DatabaseObject.class.isAssignableFrom(method.getReturnType())) {
                    /** querying the graph and fill the single object **/
                    DatabaseObject lazyLoadedObject = advancedDatabaseObjectService.findByRelationship(dbId, RelationshipDirection.valueOf(relationship.direction()), relationship.type());
                    if (lazyLoadedObject != null) {
                        /** invoke the setter in order to set the object in the target **/
                        databaseObject.getClass().getMethod(setterMethod, methodReturnClazz).invoke(databaseObject, lazyLoadedObject);
                        return lazyLoadedObject;
                    }
                }
            }
        }

        return pjp.proceed();
    }

    /**
     * AspectJ pointcut for all the getters, excluding getters in the DatabaseObject,
     * so then we can freely invoke getDbId, getStId and so on without being cut.
     * Take into account calling Pathway.getDbId won't invoke the AspectJ and this is the
     * expected behaviour.
     */
    @Pointcut("execution(public * org.reactome.server.graph.domain.model.*.get*(..))" +
            " && ! execution(public * org.reactome.server.graph.domain.model.DatabaseObject.*Id(..))" +
            " && ! execution(public * org.reactome.server.graph.domain.model.DatabaseObject.getSchemaClass(..))" +
            " && ! execution(public * org.reactome.server.graph.domain.model.DatabaseObject.*Name(..))")
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
        methodName = methodName.substring(3, methodName.length()); // crop, remove 'get'
        char c[] = methodName.toCharArray();
        c[0] = Character.toLowerCase(c[0]); // lower the first char

        String attribute = new String(c);

        /**
         * Look up for the given attribute in the class and after superclasses.
         */
        while (_clazz != null && !_clazz.getClass().equals(Object.class)) {
            for (Field field : _clazz.getDeclaredFields()) {
                if (field.getAnnotation(Relationship.class) != null) {
                    if (field.getName().equals(attribute)) {
                        return field.getAnnotation(Relationship.class);
                    }
                }
            }

            /** Didn't find the field in the given class. Check the Superclass. **/
            _clazz = _clazz.getSuperclass();
        }

        return null;
    }

    public void setEnableAOP(boolean enableAOP) {
        this.enableAOP = enableAOP;
    }

}