package com.levigo.jadice.webtoolkit.monitoring.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.jadice.web.util.instrumented.metrics.InstrumentedInvocationCount;
import com.levigo.jadice.webtoolkit.monitoring.Publisher;
import com.levigo.jadice.webtoolkit.monitoring.data.CounterData;

@Aspect
public class InvocationCounter {

  private String metricName = "";
  private String metricDescription = "";
  private String metricLabel = "";
  private long counter = 0;

  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedInvocationCount)")
  public void pointcut() {
  }

  @Around("pointcut()")
  public Object determineMetricName(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    InstrumentedInvocationCount ic = method.getAnnotation(InstrumentedInvocationCount.class);
    metricName = ic.name();
    metricDescription = ic.description();

    return joinPoint.proceed();
  }

  @After("pointcut()")
  public void incrementCounter() {
    this.counter++;

    Publisher.pushToAdapter(new CounterData(metricName, metricDescription, metricLabel, this.counter));
  }
}