package com.levigo.jadice.webtoolkit.monitoring;

import com.levigo.jadice.webtoolkit.monitoring.client.PrometheusAdapter;

final public class Publisher {
  
  // TODO: Get this information from a config file?
  private static final MonitorClient monitorClient = new PrometheusAdapter();

  public static void pushToAdapter(DataObject<?> data) {
    monitorClient.publish(data);
  }


}