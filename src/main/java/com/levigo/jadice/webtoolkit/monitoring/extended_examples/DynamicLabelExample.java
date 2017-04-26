package com.levigo.jadice.webtoolkit.monitoring.extended_examples;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.levigo.jadice.webtoolkit.monitoring.aspect.BasicAspect;
import com.levigo.jadice.webtoolkit.monitoring.data.DurationData;

/**
 * This class is an example how to define dynamic labels. If static labels are sufficient please use
 * {@link com.jadice.web.util.instrumented.InstrumentedLabel @InstrumentedLabel} instead.
 */
@Aspect
public class DynamicLabelExample extends BasicAspect {

  @Override
  @Pointcut("execution(* com.levigo.jadice.web.server.DocumentDataProvider.read(..))")
  public void pointcut() {
  }

  /**
   * This method is invoked by the join point functionality.
   */
  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

    DurationData measurement = new DurationData();

    final Class<?> clazz = joinPoint.getThis().getClass();

    measurement.setMetricName("dynamic_labels_example_duration");
    measurement.setMetricDescription("Time in milliseconds the data provider needs to read.");
    measurement.getLabels().put("provider", clazz.getSimpleName());

    try {
      return joinPoint.proceed();
    } finally {
      this.publish(measurement.end());
    }
  }
}