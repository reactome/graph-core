package org.reactome.server.graph.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logging around all Service Methods to see execution times
     * @param joinPoint loggingPointcut
     * @return Object returned by the method currently logged around
     * @throws Throwable exception when executing service methods
     */
    @Around("execution(public * org.reactome.server.graph.service.*.*(..))")
    public Object monitorExecutionTimes(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        int milliseconds = (int) elapsedTime % 1000;
        int seconds = (int) (elapsedTime / 1000) % 60;
        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
        logger.debug(joinPoint.getSignature().getDeclaringType().getSimpleName() + ":" + joinPoint.getSignature().getName() +
                " successfully executed in " + minutes + " min " + seconds + " sec " + milliseconds + " mil");
        return result;
    }
}
