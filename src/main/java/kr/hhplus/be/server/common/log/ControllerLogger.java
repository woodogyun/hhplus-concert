package kr.hhplus.be.server.common.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLogger {

    private static final Logger log = LoggerFactory.getLogger(ControllerLogger.class);

    // LogExecutionTime 애너테이션이 붙은 메서드 또는 클래스에 대한 포인트컷 정의
    @Pointcut("@within(kr.hhplus.be.server.common.annotation.LogExecutionTime) || @annotation(kr.hhplus.be.server.common.annotation.LogExecutionTime)")
    public void logExecutionTime() {}

    @Around("logExecutionTime()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();

        log.info("Entering method: {} at {}", methodName, startTime);

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Exception in method: {}", methodName, throwable);
            throw throwable;
        }

        long endTime = System.currentTimeMillis();
        log.info("Exiting method: {} with result: {} at {}. Execution time: {} ms",
                methodName, result, endTime, (endTime - startTime));

        return result;
    }
}