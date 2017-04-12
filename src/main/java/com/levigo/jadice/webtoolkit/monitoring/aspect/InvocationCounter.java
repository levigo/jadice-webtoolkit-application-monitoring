package com.levigo.jadice.webtoolkit.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.jadice.web.util.instrumented.metrics.InstrumentedInvocationCount;
import com.levigo.jadice.webtoolkit.monitoring.data.CounterData;

@Aspect
public class InvocationCounter extends BasicAspect {

  private long counter = 0;

  @Override
  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedInvocationCount)")
  public void pointcut() {
  }

  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    super.determineMetricInformation(joinPoint, InstrumentedInvocationCount.class);
    
    return joinPoint.proceed();
  }

  @After("pointcut()")
  public void incrementCounter() {
    this.counter++;

    super.publish(new CounterData(this.counter));
  }
}