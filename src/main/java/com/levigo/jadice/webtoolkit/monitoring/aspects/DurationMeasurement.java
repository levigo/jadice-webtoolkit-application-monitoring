package com.levigo.jadice.webtoolkit.monitoring.aspects;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.jadice.web.util.instrumented.metrics.Duration;
import com.levigo.jadice.webtoolkit.monitoring.Base;

@Aspect
public abstract class DurationMeasurement extends Base {

  private long startTime = 0;
  private String mertricName = "";

  @Pointcut("execution(@Instrumented * *.*(..)) && @Duration * *.*(..)) && !within(AspectBase)")
  protected abstract void measureDuration();

  @Around("measureDuration()")
  public void procede(ProceedingJoinPoint call) throws Throwable {
    MethodSignature signature = (MethodSignature) call.getSignature();
    Method method = signature.getMethod();

    try {
      this.mertricName = (method.getAnnotation(Duration.class)).metricName();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Before("measureDuration()")
  public void startMeasurement() {
    startTime = System.currentTimeMillis();
  }

  @After("measureDuration()")
  public void stopMeasurement() {
    monitorClient.publish(this.mertricName, startTime);
  }
}