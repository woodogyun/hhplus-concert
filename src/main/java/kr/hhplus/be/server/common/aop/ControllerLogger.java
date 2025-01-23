package kr.hhplus.be.server.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ControllerLogger {

    private static final Logger log = LoggerFactory.getLogger(ControllerLogger.class);

    @Pointcut("@within(kr.hhplus.be.server.common.annotation.ApiLog) || @annotation(kr.hhplus.be.server.common.annotation.ApiLog)")
    public void ApiLog() {}

    @Around("ApiLog()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Exception in method: {}", methodName, throwable);
            throw throwable;
        }

        long endTime = System.currentTimeMillis();
        log.info("Method: {} executed with request: {}, response: {} . Execution time: {} ms",
                methodName, Arrays.toString(args), result != null ? result.toString() : "null", (endTime - startTime));

        return result;
    }
}