package com.levigo.jadice.webtoolkit.monitoring;

import org.aspectj.lang.ProceedingJoinPoint;

import com.jadice.web.util.instrumented.metrics.InstrumentedDuration;
import com.levigo.jadice.webtoolkit.monitoring.client.PrometheusAdapter;

public abstract class Base {

  // TODO Get this information from a config file?
  private final MonitorClient monitorClient = new PrometheusAdapter();

  protected String metricName = "";

  protected Base() {

  }

  public Object determineMetricName(ProceedingJoinPoint joinPoint, InstrumentedDuration id) throws Throwable {

    if (metricName.length() < 1) {
      metricName = id.value();
    }

    return joinPoint.proceed();
  }

  protected void publish(Class<? extends Base> type, Object value) {
    monitorClient.publish(type, metricName, value);
  }

}