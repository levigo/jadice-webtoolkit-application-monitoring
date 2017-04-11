package com.levigo.jadice.webtoolkit.monitoring.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.jadice.web.util.instrumented.metrics.InstrumentedDuration;
import com.levigo.jadice.webtoolkit.monitoring.Publisher;
import com.levigo.jadice.webtoolkit.monitoring.data.DurationData;

@Aspect
public class DurationMeasurement {

  private String metricName = "";
  private String metricDescription = "";
  private String metricLabel = "";
  private long startTime = 0;

  @Pointcut("execution(* *(..)) && @annotation(com.jadice.web.util.instrumented.metrics.InstrumentedDuration)")
  public void pointcut() {
  }

  @Around("pointcut()")
  public Object determineMetricName(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    InstrumentedDuration id = method.getAnnotation(InstrumentedDuration.class);
    metricName = id.name();
    metricDescription = id.description();

    return joinPoint.proceed();
  }

  @Before("pointcut()")
  public void startMeasurement(ProceedingJoinPoint joinPoint) {
    this.startTime = System.currentTimeMillis();
  }

  @After("pointcut()")
  public void stopMeasurement() {
    Publisher.pushToAdapter(new DurationData(metricName, metricDescription, metricLabel,
        System.currentTimeMillis() - startTime));
  }
}