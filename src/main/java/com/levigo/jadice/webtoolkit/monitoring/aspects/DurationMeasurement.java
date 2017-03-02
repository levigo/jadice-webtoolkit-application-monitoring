package com.levigo.jadice.webtoolkit.monitoring.aspects;

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
  private String metricName = "";

  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedDuration)")
  protected void measureDuration() {
  }

  @Around("measureDuration()")
  public Object aroundDuration(ProceedingJoinPoint joinPoint, InstrumentedDuration id) throws Throwable {
    
    if (metricName.length() < 1) {
       metricName =  id.value();
    }
    
    return joinPoint.proceed();
  }

  @Before("measureDuration()")
  public void startMeasurement(ProceedingJoinPoint joinPoint) {

    startTime = System.currentTimeMillis();
  }

  @After("measureDuration()")
  public void stopMeasurement() {
    monitorClient.publish(metricName, System.currentTimeMillis() - startTime);
  }
}