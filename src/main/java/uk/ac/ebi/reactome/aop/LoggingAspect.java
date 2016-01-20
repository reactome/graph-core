package uk.ac.ebi.reactome.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 19.11.15.
 *
 * Logging aspect, used for logging all methods in the service level.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = Logger.getLogger(LoggingAspect.class);

    @Pointcut("within(uk.ac.ebi.reactome.service..*) || within(uk.ac.ebi.reactome.data.DatabaseObjectFactory.*)")
    public void loggingPointcut() {}

    /**
     * Logging around all Service Methods to see execution times
     * @param joinPoint loggingPointcut
     * @return Object returned by the method currently logged around
     * @throws Throwable
     */
    @Around("loggingPointcut()")
    public Object monitorExecutionTimes(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        int milliseconds = (int) elapsedTime % 1000;
        int seconds = (int) (elapsedTime / 1000) % 60;
        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
        logger.info(joinPoint.getSignature().getDeclaringType().getSimpleName() + ":" + joinPoint.getSignature().getName() +
                " successfully executed in " + minutes + " min " + seconds + " sec " + milliseconds + " mil");
        return result;
    }

//    @AfterThrowing(pointcut = "within(uk.ac.ebi.reactome..*)", throwing = "e")
//    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
//        logger.error(joinPoint.getSignature().getDeclaringType().getSimpleName() + ":" + joinPoint.getSignature().getName() +
//                " failed with message " + e.getMessage());
//
//        MailUtil mail = new MailUtil("localhost", 9999);
//        StringBuilder body = new StringBuilder();
//        body.append("An exception has occured at\n\n");
//        body.append("Class: " + joinPoint.getSignature().getDeclaringTypeName());
//        body.append("\n");
//        body.append("Method: " + joinPoint.getSignature().getName());
//        body.append("\n");
//        body.append("Exception: " + e.toString());
//        body.append("\n");
//        mail.send("reactome-indexer@reactome.org", "fkorninger@yahoo.de", "An exception has occured", body.toString());
//    }
}
