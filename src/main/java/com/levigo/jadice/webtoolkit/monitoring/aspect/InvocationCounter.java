package com.levigo.jadice.webtoolkit.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.jadice.web.util.instrumented.metrics.InstrumentedInvocationCount;
import com.levigo.jadice.webtoolkit.monitoring.Publisher;
import com.levigo.jadice.webtoolkit.monitoring.data.CounterData;

@Aspect
public class InvocationCounter {

  private String metricName = "";
  private String label = "";
  private long counter = 0;

  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedInvocationCount)")
  protected void pointcut() {
  }

  @Around("pointcut()")
  public Object determineMetricName(ProceedingJoinPoint joinPoint, InstrumentedInvocationCount ic) throws Throwable {
    metricName = ic.value();
    return joinPoint.proceed();
  }

  @After("pointcut()")
  public void incrementCounter() {
    Publisher.pushToAdapter(new CounterData(metricName, label, ++counter));
  }
}