package org.reactome.server.graph.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;


@Aspect
@Component
public class LazyFetchAspect {

    @Autowired
    private Neo4jOperations neo4jTemplate;

    @Around("modelGetter()")
    public Object autoFetch(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        System.out.println(method.getReturnType().getName());

        Class<?> returnTypeOfPointcutMethod = method.getReturnType();

        if (returnTypeOfPointcutMethod.getCanonicalName().equals(Long.class.getCanonicalName())) {


//            System.out.println( " ##################### I AM HEREEEEE AS NODEENTITYssss ##############################");


        } else if (returnTypeOfPointcutMethod.getCanonicalName().equals(Collection.class.getCanonicalName())) {

        }


//        Object o = pjp.getThis();
//        if(o != null) {
//            System.out.println(o.getClass());
//            if(o.getClass().isAnnotationPresent(NodeEntity.class)) {
//                //o.getClass().get
//
//                if(o instanceof BaseObject) {
//
//
//                    System.out.println( " ##################### I AM HEREEEEE AS NODEENTITYssss ##############################");
//
////                    DatabaseObject<?> bo = (DatabaseObject<?>)o;
////                    if(bo.getId() != null && !bo.isFetched()) {
////                        return template.fetch(o);
////                    }
////                    return o;
//                }
////                try {
////                    return template.fetch(o);
////                } catch(MappingException me) {
////                    me.printStackTrace();
////                }
//            }
//        }
        return pjp.proceed();
    }

    @Pointcut("execution(public * org.reactome.server.graph.domain.model.*.get*(..))")
    public void modelGetter() {}

}