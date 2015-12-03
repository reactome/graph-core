package uk.ac.ebi.reactome.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 30.11.15.
 */
@Aspect
public class DomainAspect {


    private static final Logger logger = Logger.getLogger(DomainAspect.class);

    @Pointcut("within(uk.ac.ebi.reactome..*)")
    public void loggingPointcut() {}


    @Around("loggingPointcut()")
    public Object monitorExecutionTimes(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = joinPoint.proceed();

        logger.info(joinPoint.getSignature().getDeclaringType().getSimpleName() + ":" + joinPoint.getSignature().getName() +
                " LOGGED !!!!!!!!!! ");
        return result;
    }

}
