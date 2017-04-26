package com.levigo.jadice.webtoolkit.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.jadice.web.util.instrumented.InstrumentedDuration;
import com.levigo.jadice.webtoolkit.monitoring.data.DurationData;

@Aspect
public class DurationMeasurement extends BasicAspect {

  @Override
  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.InstrumentedDuration)")
  public void pointcut() {
  }

  /**
   * Determines the metric information. This method is invoked by the join point functionality.
   */
  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    DurationData durationData = new DurationData();

    super.determineMetricInformation(durationData, joinPoint, InstrumentedDuration.class);
    try {
      return joinPoint.proceed();
    } finally {
      super.publish(durationData.end());
    }
  }
}