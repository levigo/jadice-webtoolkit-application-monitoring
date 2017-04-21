package com.levigo.jadice.webtoolkit.monitoring.extended_examples;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.levigo.jadice.webtoolkit.monitoring.aspect.BasicAspect;
import com.levigo.jadice.webtoolkit.monitoring.data.DurationData;

/**
 * This class is an example how to define dynamic labels. If static labels are sufficient please use
 * {@link com.jadice.web.util.instrumented.InstrumentedLabel @InstrumentedLabel} instead.
 */
@Aspect
public class CustomDynamicLabelExample extends BasicAspect {

  private long startTime = 0;

  @Override
  @Pointcut("execution(* com.levigo.jadice.web.server.DocumentDataProvider.read(..))")
  public void pointcut() {
  }

  /**
   * This method is invoked by the join point functionality.
   */
  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

    final Class<?> clazz = joinPoint.getThis().getClass();

    this.metricName = "class_path_document_provider_dynamic_labels_duration";
    this.metricDescription = "Custom description.";
    this.metricLabelAttr = "provider";
    this.metricLabelValue = clazz.getSimpleName();

    return joinPoint.proceed();
  }

  /**
   * Starts the measurement.
   */
  @Before("pointcut()")
  public void startMeasurement() {
    this.startTime = System.currentTimeMillis();
  }

  /**
   * Stops the measurement.
   */
  @After("pointcut()")
  public void stopMeasurement() {
    this.publish(new DurationData(System.currentTimeMillis() - this.startTime));
  }
}