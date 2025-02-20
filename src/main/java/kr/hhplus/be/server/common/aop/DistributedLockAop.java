package kr.hhplus.be.server.common.aop;

import kr.hhplus.be.server.common.annotation.DistributedLock;
import kr.hhplus.be.server.common.parser.CustomSpringELParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @DistributedLock 선언 시 수행되는 Aop class
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE-1)
@Slf4j
@RequiredArgsConstructor
public class DistributedLockAop {
    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;

    @Around("@annotation(kr.hhplus.be.server.common.annotation.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();Method method = signature.getMethod();
        DistributedLock distributeLock = method.getAnnotation(DistributedLock.class);     // 해당 메서드의 어노테이션을 가져온다
        String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributeLock.key());    // DistributeLock에 전달한 key와 메서드 인자를 통한 Lock key값생성(Spring EL파싱)

        RLock rLock = redissonClient.getLock(key);

        try {
            boolean available = rLock.tryLock(distributeLock.waitTime(), distributeLock.leaseTime(), distributeLock.timeUnit());    // 락 획득 시도
            if (!available) {
                return false;
            }
            log.info("get lock success {}" , key);
            return joinPoint.proceed();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new InterruptedException();
        } finally {
            rLock.unlock();    // 종료 혹은 예외 발생시 Lock 해제
        }
    }
}