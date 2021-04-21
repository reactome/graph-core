package org.reactome.server.graph.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected static final Logger logger = LoggerFactory.getLogger("infoLogger");

    private static boolean enableSorting = false;

    @SuppressWarnings("unchecked")
    @Around("modelGetter()")
    public Object autoFetch(ProceedingJoinPoint pjp) throws Throwable {
        if (!SortingAspect.enableSorting) {
            return pjp.proceed();
        }

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        // This is the value that is returned by the intercepted getter
        Object returnedValue = pjp.proceed();

        DatabaseObject target = (DatabaseObject) pjp.getTarget();

        if (returnedValue == null) {
            return pjp.proceed();
        }

        List<? extends DatabaseObject> returnedValueAsList;
        if (returnedValue instanceof List) {
            returnedValueAsList = (List<? extends DatabaseObject>) returnedValue;

            //noinspection Convert2Lambda, Do not apply lambda function here. ajc won't compile
            returnedValueAsList.sort(new Comparator<DatabaseObject>() {
                @Override
                public int compare(DatabaseObject o1, DatabaseObject o2) {
                    return o1.getDisplayName().compareTo(o2.getDisplayName());
                }
            });

            String setterMethod = method.getName().replaceFirst("get", "set");
            try {
                // Check if the setter exists. e.g getRegulation, which wraps Positive and Negative Regulation,
                // does not have a setter, in this case we are going to return the list but won't set the target object.
                Method setter = target.getClass().getMethod(setterMethod, method.getReturnType());

                // Set collection in object, then we do not sort every execution.
                setter.invoke(target, returnedValueAsList);
            } catch (NoSuchMethodException e) {
                logger.warn("Could not find method " + setterMethod + " in the class " + target.getClass().getName());
            }

            // return the sorted list
            return returnedValueAsList;

        } else if (returnedValue instanceof Set) {
            // The set by default (DatabaseObject.compareTo) is sorting by dbId, in this case we want to sort by displayName.
            // Then take the Set, convert to a List, sort it and convert back to a LinkedHashSet.
            Set<? extends DatabaseObject> returnedValueSet = (Set<? extends DatabaseObject>) returnedValue;
            returnedValueAsList = new ArrayList<>(returnedValueSet);

            //noinspection Convert2Lambda, Do not apply lambda function here. ajc won't compile
            returnedValueAsList.sort(new Comparator<DatabaseObject>() {
                @Override
                public int compare(DatabaseObject o1, DatabaseObject o2) {
                    return o1.getDisplayName().compareTo(o2.getDisplayName());
                }
            });

            Set<? extends DatabaseObject> returnedValueAsSet = new LinkedHashSet<>(returnedValueAsList);
            String setterMethod = method.getName().replaceFirst("get", "set");
            try {
                // Check if the setter exists. e.g getRegulation, which wraps Positive and Negative Regulation,
                // does not have a setter, in this case we are going to return the list but won't set the target object.
                Method setter = target.getClass().getMethod(setterMethod, method.getReturnType());

                // Set collection in object, then we do not sort every execution.
                setter.invoke(target, returnedValueAsSet);
            } catch (NoSuchMethodException e) {
                logger.warn("Could not find method " + setterMethod + " in the class " + target.getClass().getName());
            }

            // return the sorted list
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

    public Boolean getEnableSorting() {
        return SortingAspect.enableSorting;
    }

    public void setEnableSorting(boolean enableSorting) {
        SortingAspect.enableSorting = enableSorting;
    }
}