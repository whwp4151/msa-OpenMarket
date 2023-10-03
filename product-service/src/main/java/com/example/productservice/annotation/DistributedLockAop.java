package com.example.productservice.annotation;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop {

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        log.info("{} 에서 LOCK({}) 획득 시도", method.getName(), distributedLock.key());

        RLock rLock = redissonClient.getLock(distributedLock.key());  // (1)
        try {
            boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());  // (2)
            if (!available) {
                throw new IllegalStateException("[" + distributedLock.key() + "] lock 획득 실패");
            }

            log.info("{} 에서 LOCK({}) 획득", method.getName(), distributedLock.key());
            return aopForTransaction.proceed(joinPoint);  // (3)
        } finally {
            if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
            log.info("{} 에서 LOCK({}) 반납", method.getName(), distributedLock.key());
        }
    }

}
