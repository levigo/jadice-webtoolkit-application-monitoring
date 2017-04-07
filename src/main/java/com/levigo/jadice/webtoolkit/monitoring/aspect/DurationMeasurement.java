package com.levigo.jadice.webtoolkit.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.jadice.web.util.instrumented.metrics.InstrumentedDuration;
import com.levigo.jadice.webtoolkit.monitoring.Publisher;
import com.levigo.jadice.webtoolkit.monitoring.data.DurationData;

@Aspect
public class DurationMeasurement {

  private String metricName = "";
  private String label = "";
  private double startTime = 0;

  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedDuration)")
  protected void pointcut() {
  }

  @Around("pointcut()")
  public Object determineMetricName(ProceedingJoinPoint joinPoint, InstrumentedDuration id) throws Throwable {
    metricName = id.value();
    return joinPoint.proceed();
  }

  @Before("pointcut()")
  public void startMeasurement(ProceedingJoinPoint joinPoint) {
    startTime = System.currentTimeMillis();
  }

  @After("pointcut()")
  public void stopMeasurement() {
    Publisher.pushToAdapter(new DurationData(metricName, label, System.currentTimeMillis() - startTime));
  }
}