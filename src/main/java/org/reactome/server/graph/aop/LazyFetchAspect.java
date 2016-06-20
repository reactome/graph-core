package org.reactome.server.graph.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.neo4j.ogm.annotation.NodeEntity;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.service.AdvancedDatabaseObjectService;
import org.reactome.server.graph.service.helper.RelationshipDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

@Aspect
@Component
public class LazyFetchAspect {

    @Autowired
    private AdvancedDatabaseObjectService advancedDatabaseObjectService;


    @Around("modelGetter()")
    public Object autoFetch(ProceedingJoinPoint pjp) throws Throwable {
        /**
         * Target is the whole object that originated this call.
         */
        DatabaseObject databaseObject = (DatabaseObject) pjp.getTarget();

        /**
         * Load the dbId from the target. We can't call the getter, because the AOP is intercepting
         * them. Then, the aopDbId method is a fake method that returns the dbId :)
         */
        Class<?> myClass = pjp.getStaticPart().getSignature().getDeclaringType();
        Long dbId = (Long) myClass.getMethod("aopDbId", new Class<?>[]{}).invoke(databaseObject);

        /**
         * Gathering information of the method we are invoking and it's being intercepted by AOP
         */
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Class<?> returnTypeOfPointcutMethod = method.getReturnType();

        System.out.println("METHOD NAME: " + method.getName());
        System.out.println("METHOD RETURN TYPE: " + method.getReturnType());
        System.out.println("METHOD RETURN VALUE: " + pjp.proceed());

        if (databaseObject != null) {
            if (databaseObject.getClass().isAnnotationPresent(NodeEntity.class)) {
                if (returnTypeOfPointcutMethod.getCanonicalName().equals(List.class.getCanonicalName())) {

                    /**
                     * Check whether the object has been loaded.
                     */
                    if (!databaseObject.isLoaded) {
                        String methodName = method.getName();
                        methodName = methodName.substring(3, methodName.length()); // crop, remove 'get'
                        char c[] = methodName.toCharArray();
                        c[0] = Character.toLowerCase(c[0]);
                        String relationship = new String(c);

                        Collection<DatabaseObject> bla = advancedDatabaseObjectService.findByRelationship(dbId, RelationshipDirection.OUTGOING, relationship);

                        databaseObject.isLoaded = true;

                        return bla;

                    }
                }
            }
        }

        return pjp.proceed();
    }

    @Pointcut("execution(public * org.reactome.server.graph.domain.model.*.get*(..))")
    public void modelGetter() {
    }
}