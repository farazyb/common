package hello.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogExecuteTimeAspect {

    @Around("@annotation(hello.annotation.LogExecutionTime)")
    public Object calculateExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println(joinPoint.getSignature() + " executed in " + (endTime - startTime) + " milliseconds.");
        return result;
    }
    @Around("@annotation(hello.annotation.Tof)")
    public Object calculateExecutionTime1(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println(joinPoint.getSignature() + " executed in " + (endTime - startTime) + " milliseconds.");
        return result;
    }
}
