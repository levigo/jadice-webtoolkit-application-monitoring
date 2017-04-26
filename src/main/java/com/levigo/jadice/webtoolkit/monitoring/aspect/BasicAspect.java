package com.levigo.jadice.webtoolkit.monitoring.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.jadice.web.util.instrumented.InstrumentedLabel;
import com.jadice.web.util.instrumented.InstrumentedLabels;
import com.levigo.jadice.webtoolkit.monitoring.Publisher;
import com.levigo.jadice.webtoolkit.monitoring.data.DataObject;

/**
 * This class provides a basic functionality to feed a publisher with data. Therefore the method
 * {@link #determineMetricInformation(ProceedingJoinPoint, Class)} collects all necessary
 * information from the annotation(s). The method {@link #publish(DataObject)} sends the data to the
 * publisher.
 *
 */
@Aspect
public abstract class BasicAspect {

  private Publisher publisher = Publisher.getInstance();

  @Pointcut
  public abstract void pointcut();

  /**
   * This method needs to be called to set metric name and metric description as well label
   * attribute and label value for this object. The proceeding join point is given by an around
   * ({@link org.aspectj.lang.annotation.Around @Around(..)}) advice.
   * 
   * @param joinPoint The {@link ProceedingJoinPoint} of an around advice.
   */
  protected void determineMetricInformation(DataObject<?> data, ProceedingJoinPoint joinPoint,
      Class<? extends Annotation> clazz) {

    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    Annotation annotation = method.getAnnotation(clazz);

    // Get the metric name
    try {
      Method name = annotation.getClass().getMethod("name");
      data.setMetricName((String) name.invoke(annotation));
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    }

    // Get the metric description
    try {
      Method description = annotation.getClass().getMethod("description");
      data.setMetricDescription((String) description.invoke(annotation));
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    }

    InstrumentedLabels ils = method.getAnnotation(InstrumentedLabels.class);
    if (null != ils) {
      for (InstrumentedLabel label : ils.value()) {
        data.getLabels().put(label.attr(), label.value());
      }
    }

    InstrumentedLabel il = method.getAnnotation(InstrumentedLabel.class);
    if (null != il) {
      data.getLabels().put(il.attr(), il.value());
    }
  }

  /**
   * Publishes the given data.
   * 
   * @param data The data object.
   */
  protected void publish(DataObject<?> data) {
    this.publisher.pushToAdapter(data);
  }
}