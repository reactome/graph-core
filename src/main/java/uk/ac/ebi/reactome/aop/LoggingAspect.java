//package uk.ac.ebi.reactome.aop;
//
//
//import org.apache.log4j.Logger;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
//
///**
// * Created by:
// *
// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
// * @since 19.11.15.
// *
// * Logging aspect, used for logging all methods in the service level.
// */
//@Aspect
////@Component
//public class LoggingAspect {
//
////    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private static final Logger logger = Logger.getLogger(LoggingAspect.class);
//    /**
//     *     Defining a shared logging pointcut
//     *     Every method of classes within service package
//     */
////    @Pointcut("within(uk.ac.ebi.reactome.data..*) || within(uk.ac.ebi.reactome.service..*) " +
////            "&& !within(uk.ac.ebi.reactome.service.ImportServiceImpl) " +
////            "&& !within(uk.ac.ebi.reactome.service.InteractorServiceImpl)")
////    public void loggingPointcut() {}
//
//    @Pointcut("within(uk.ac.ebi.reactome..*)")
//    public void loggingPointcut() {}
//
//
//    /**
//     * Logging around all Service Methods to see execution times
//     * @param joinPoint loggingPointcut
//     * @return Object returned by the method currently logged around
//     * @throws Throwable
//     */
//    @Around("loggingPointcut()")
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
//    /**
//     * Logging after an exception is thrown
//     * @param joinPoint Every method of classes within package reactome (entire project)
//     * @param e Exception type
//     */
//    @AfterThrowing(pointcut = "within(uk.ac.ebi.reactome..*)", throwing = "e")
//    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
//        logger.error(joinPoint.getSignature().getDeclaringType().getSimpleName() + ":" + joinPoint.getSignature().getName() +
//                " failed with message " + e.getMessage());
//    }
//
//
//}
