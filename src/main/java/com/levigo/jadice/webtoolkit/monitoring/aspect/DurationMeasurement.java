package com.levigo.jadice.webtoolkit.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.jadice.web.util.instrumented.metrics.InstrumentedDuration;
import com.levigo.jadice.webtoolkit.monitoring.data.DurationData;

@Aspect
public class DurationMeasurement extends BasicAspect {

  private long startTime = 0;

  @Override
  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedDuration)")
  public void pointcut() {
  }

  /**
   * Determines the metric information. This method is invoked by the join point functionality.
   */
  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    super.determineMetricInformation(joinPoint, InstrumentedDuration.class);

    return joinPoint.proceed();
  }

  /**
   * Starts the measurement.
   */
  @Before("pointcut()")
  public void startMeasurement(ProceedingJoinPoint joinPoint) {
    this.startTime = System.currentTimeMillis();
  }

  /**
   * Stops the measurement.
   */
  @After("pointcut()")
  public void stopMeasurement() {
    super.publish(new DurationData(System.currentTimeMillis() - this.startTime));
  }
}