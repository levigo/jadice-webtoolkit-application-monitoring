package com.levigo.jadice.webtoolkit.monitoring.client;

import com.levigo.jadice.webtoolkit.monitoring.Base;
import com.levigo.jadice.webtoolkit.monitoring.MonitorClient;
import com.levigo.jadice.webtoolkit.monitoring.aspect.DurationMeasurement;
import com.levigo.jadice.webtoolkit.monitoring.aspect.InvocationCounter;
import com.levigo.jadice.webtoolkit.monitoring.aspect.MethodResult;

public class PrometheusAdapter implements MonitorClient {

  @Override
  public void publish(Class<? extends Base> type, String name, Object value) {
    if (type == DurationMeasurement.class) {

    } else if (type == InvocationCounter.class) {

    } else if (type == MethodResult.class) {

    }

    // TODO: REMOVE
    System.out.println("PrometheusAdapter: [metric: " + name + ", value: " + value + "]");
  }
}