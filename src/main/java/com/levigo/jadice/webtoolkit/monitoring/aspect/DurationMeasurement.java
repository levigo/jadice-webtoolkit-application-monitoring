package com.levigo.jadice.webtoolkit.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.jadice.web.util.instrumented.metrics.InstrumentedDuration;
import com.levigo.jadice.webtoolkit.monitoring.Base;

@Aspect
public class DurationMeasurement extends Base {

  private long startTime = 0;

  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedDuration)")
  protected void pointcut() {
  }

  @Override
  @Around("pointcut()")
  public Object determineMetricName(ProceedingJoinPoint joinPoint, InstrumentedDuration id) throws Throwable {
    return super.determineMetricName(joinPoint, id);
  }

  @Before("pointcut()")
  public void startMeasurement(ProceedingJoinPoint joinPoint) {
    startTime = System.currentTimeMillis();
  }

  @After("pointcut()")
  public void stopMeasurement() {
    super.publish(getClass(), System.currentTimeMillis() - startTime);
  }
}