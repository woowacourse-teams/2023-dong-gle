package org.donggle.backend.application.service.concurrent;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Order
@Aspect
@Component
public class NoConcurrentAccessAspect {
    public static final int MEMBER_ID_INDEX = 0;

    private final ConcurrentHashMap<Long, Lock> memberLock = new ConcurrentHashMap<>();

    @Around("@annotation(org.donggle.backend.application.service.concurrent.NoConcurrentExecution)")
    public Object noConcurrentAccess(final ProceedingJoinPoint joinPoint) throws Throwable {
        final NoConcurrentExecution anno = getAnnotation(joinPoint);
        final Long memberId = (Long) joinPoint.getArgs()[MEMBER_ID_INDEX];
        final Lock lock = memberLock.computeIfAbsent(memberId, k -> new ReentrantLock());
        if (!lock.tryLock()) {
            throw anno.value().getDeclaredConstructor().newInstance();
        }
        try {
            return joinPoint.proceed();
        } finally {
            lock.unlock();
            memberLock.remove(memberId);
        }
    }

    private NoConcurrentExecution getAnnotation(final ProceedingJoinPoint joinPoint) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return AnnotationUtils.findAnnotation(signature.getMethod(), NoConcurrentExecution.class);
    }
}
