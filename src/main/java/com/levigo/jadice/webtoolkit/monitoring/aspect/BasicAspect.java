package com.levigo.jadice.webtoolkit.monitoring.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.jadice.web.util.instrumented.metrics.InstrumentedLabel;
import com.levigo.jadice.webtoolkit.monitoring.Publisher;
import com.levigo.jadice.webtoolkit.monitoring.data.DataObject;

@Aspect
public abstract class BasicAspect {

  protected String metricName = "";
  protected String metricDescription = "";
  protected String metricLabelAttr = "";
  protected String metricLabelValue = "";

  private Publisher publisher = Publisher.getInstance();

  @Pointcut
  public abstract void pointcut();

  /**
   * This method needs to bee called to set metric name, description as well label attribute and
   * value for this object. The proceeding join point is given by an around
   * ({@link org.aspectj.lang.annotation.Around @Around(..)}) advice.
   * 
   * @param joinPoint The {@link ProceedingJoinPoint} of an around advice.
   */
  protected void determineMetricInformation(ProceedingJoinPoint joinPoint, Class<? extends Annotation> clazz) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    Annotation annotation = method.getAnnotation(clazz);

    // Get the metric name
    try {
      Method name = annotation.getClass().getMethod("name");
      this.metricName = (String) name.invoke(annotation);
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    }

    // Get the metric description
    try {
      Method description = annotation.getClass().getMethod("description");
      this.metricDescription = (String) description.invoke(annotation);
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    }

    InstrumentedLabel il = method.getAnnotation(InstrumentedLabel.class);
    if (null != il) {
      this.metricLabelAttr = il.attr();
      this.metricLabelValue = il.value();
    }
  }

  protected void publish(DataObject<?> data) {

    data.setMetricName(this.metricName);
    data.setMetricDescription(this.metricDescription);
    data.setMetriclabelAttr(this.metricLabelAttr);
    data.setMetriclabelValue(this.metricLabelValue);

    this.publisher.pushToAdapter(data);
  }

}