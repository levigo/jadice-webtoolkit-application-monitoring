package com.levigo.jadice.webtoolkit.monitoring;

import com.levigo.jadice.webtoolkit.monitoring.clients.PrometheusAdapter;

public class Base {

  // TODO Get this information from a config file?
  protected final MonitorClient monitorClient = new PrometheusAdapter();

}