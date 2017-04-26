package com.levigo.jadice.webtoolkit.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.jadice.web.util.instrumented.InstrumentedInvocationCount;
import com.levigo.jadice.webtoolkit.monitoring.data.CounterData;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicLong;

/**
 * This aspect handles all methods annotated by {@link InstrumentedInvocationCount}.
 */
@Aspect
public class InvocationCounter extends BasicAspect {

  private AtomicLong counter = new AtomicLong();

  @Override
  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.InstrumentedInvocationCount)")
  public void pointcut() {
  }

  /**
   * Determines the metric information. This method is invoked by the join point functionality.
   */
  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    
    CounterData counterData = new CounterData(counter.incrementAndGet());
    
    super.determineMetricInformation(counterData, joinPoint, InstrumentedInvocationCount.class);
    super.publish(counterData);

    return joinPoint.proceed();
  }
}