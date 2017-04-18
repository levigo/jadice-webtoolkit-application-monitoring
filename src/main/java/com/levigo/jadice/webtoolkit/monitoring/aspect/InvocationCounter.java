package com.levigo.jadice.webtoolkit.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.jadice.web.util.instrumented.metrics.InstrumentedInvocationCount;
import com.levigo.jadice.webtoolkit.monitoring.data.CounterData;

/**
 * This aspect handles all methods annotated by {@link InstrumentedInvocationCount}.
 */
@Aspect
public class InvocationCounter extends BasicAspect {

  private long counter = 0;

  @Override
  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedInvocationCount)")
  public void pointcut() {
  }

  /**
   * Determines the metric information. This method is invoked by the join point functionality.
   */
  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    super.determineMetricInformation(joinPoint, InstrumentedInvocationCount.class);

    return joinPoint.proceed();
  }

  /**
   * Increments the counter and publishes the value.
   */
  @After("pointcut()")
  public void incrementCounter() {
    this.counter++;

    super.publish(new CounterData(this.counter));
  }
}