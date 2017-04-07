package com.levigo.jadice.webtoolkit.monitoring;

import com.levigo.jadice.webtoolkit.monitoring.client.PrometheusAdapter;

final public class Publisher {

  // TODO: Get this information from a config file?
  private static final MonitorClient monitorClient = new PrometheusAdapter();
  private static final int port = 9095;

  public static void pushToAdapter(DataObject<?> data) {
    monitorClient.publish(data);
  }

}