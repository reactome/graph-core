//package uk.ac.ebi.reactome.aop;
//
//import org.apache.log4j.Logger;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//
///**
// * Created by:
// *
// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
// * @since 14.12.15.
// */
//@Aspect
//public class DomainAspect {
//
//    private static final Logger logger = Logger.getLogger(DomainAspect.class);
//
//    private static class MyAspectHolder {
//        static final DomainAspect instance = new DomainAspect();
//    }
//
//    public static DomainAspect aspectOf() {return MyAspectHolder.instance;}
//
//    @Pointcut("execution(* uk.ac.ebi.reactome.domain.model.*.*(..))")
//    public void domainPointcut() {}
//
//
//    @Around("domainPointcut()")
//    public Object monitorExecutionTimes(ProceedingJoinPoint joinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        Object result = joinPoint.proceed();
//        long elapsedTime = System.currentTimeMillis() - start;
//        int milliseconds = (int) elapsedTime % 1000;
//        int seconds = (int) (elapsedTime / 1000) % 60;
//        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
//        logger.info(joinPoint.getSignature().getDeclaringType().getSimpleName() + ":" + joinPoint.getSignature().getName() +
//                " successfully executed in " + minutes + " min " + seconds + " sec " + milliseconds + " mil");
//        return result;
//    }
//
//
//}
