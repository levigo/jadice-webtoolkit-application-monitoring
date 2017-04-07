package com.levigo.jadice.webtoolkit.monitoring;

import com.levigo.jadice.webtoolkit.monitoring.client.PrometheusAdapter;

final public class Publisher {

  // TODO Get this information from a config file?
  private static final MonitorClient monitorClient = new PrometheusAdapter();

  // public Object determineMetricName(ProceedingJoinPoint joinPoint, InstrumentedDuration id)
  // throws Throwable {
  //
  // if (metricName.length() < 1) {
  // metricName = id.value();
  // }
  //
  // return joinPoint.proceed();
  // }

  public static void pushToAdapter(DataObject<?> data) {
    monitorClient.publish(data);
  }

}