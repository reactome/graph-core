package org.reactome.server.graph.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Guilherme Viteri (gviteri@ebi.ac.uk)
 * @author Florian Korninger (fkorn@ebi.ac.uk)
 */
@Aspect
@Component
public class SortingAspect {

    public Boolean enableSorting = true;

    @SuppressWarnings("unchecked")
    @Around("modelGetter()")
    public Object autoFetch(ProceedingJoinPoint pjp) throws Throwable {
        if (!enableSorting) {
            return pjp.proceed();
        }

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        /** This is the value that is returned by the intercepted getter **/
        Object returnedValue = pjp.proceed();

        DatabaseObject target = (DatabaseObject) pjp.getTarget();

        if (returnedValue == null) {
            return pjp.proceed();
        }

        List<? extends DatabaseObject> returnedValueAsList;
        if (returnedValue instanceof List) {
            returnedValueAsList = (List<? extends DatabaseObject>) returnedValue;

            /** Do not apply lambda function here. ajc won't compile **/
            Collections.sort(returnedValueAsList, new Comparator<DatabaseObject>() {
                @Override
                public int compare(DatabaseObject o1, DatabaseObject o2) {
                    return o1.getDisplayName().compareTo(o2.getDisplayName());
                }
            });

            String setterMethod = method.getName().replaceFirst("get", "set");

            /** Set collection in object, then we do not sort every execution **/
            target.getClass().getMethod(setterMethod, method.getReturnType()).invoke(target, returnedValueAsList);

            /** return the sorted list **/
            return returnedValueAsList;

        } else if (returnedValue instanceof Set) {
            /**
             * The set by default (DatabaseObject.compareTo) is sorting by dbId, in this case we want to sort by displayName.
             * Then take the Set, convert to a List, sort it and convert back to a LinkedHashSet.
             */
            Set<? extends DatabaseObject> returnedValueSet = (Set<? extends DatabaseObject>) returnedValue;

            returnedValueAsList = new ArrayList<>(returnedValueSet);

            /** Do not apply lambda function here. ajc won't compile **/
            Collections.sort(returnedValueAsList, new Comparator<DatabaseObject>() {
                @Override
                public int compare(DatabaseObject o1, DatabaseObject o2) {
                    return o1.getDisplayName().compareTo(o2.getDisplayName());
                }
            });

            String setterMethod = method.getName().replaceFirst("get", "set");

            Set<? extends DatabaseObject> returnedValueAsSet = new LinkedHashSet<>(returnedValueAsList);

            /** Set collection in object, then we do not sort every execution **/
            target.getClass().getMethod(setterMethod, method.getReturnType()).invoke(target, returnedValueAsSet);

            /** return the sorted list **/
            return returnedValueAsSet;
        }
        return returnedValue;
    }

    /**
     * AspectJ pointcut for all the getters, excluding getters in the DatabaseObject,
     * so then we can freely invoke getDbId, getStId and so on without being cut.
     * Take into account calling Pathway.getDbId won't invoke the AspectJ and this is the
     * expected behaviour.
     */
    @Pointcut("execution(public java.util.Collection<org.reactome.server.graph.domain.model.DatabaseObject+>+ org.reactome.server.graph.domain.model.*.get*(..))")
    public void modelGetter() {
    }

    public void setEnableSorting(Boolean enableSorting) {
        this.enableSorting = enableSorting;
    }
}