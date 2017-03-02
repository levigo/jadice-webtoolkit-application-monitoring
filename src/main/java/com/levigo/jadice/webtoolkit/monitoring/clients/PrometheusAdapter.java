package com.levigo.jadice.webtoolkit.monitoring.clients;

import com.levigo.jadice.webtoolkit.monitoring.MonitorClient;

public class PrometheusAdapter implements MonitorClient {

  public void publish(String metric, Object value) {
    System.out.println("PrometheusAdapter: [metric: " + metric + ", value: " + value + "]");
  }

}