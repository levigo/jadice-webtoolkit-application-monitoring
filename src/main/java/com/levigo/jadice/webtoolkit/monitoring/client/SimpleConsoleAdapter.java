package com.levigo.jadice.webtoolkit.monitoring.client;

import com.levigo.jadice.webtoolkit.monitoring.data.DataObject;

public class SimpleConsoleAdapter implements MonitorClient {

  @Override
  public void publish(DataObject<?> data) {
    System.out.println("SimpleConsoleAdapter: [metric: " + data.getMetricName() + "{" + data.getMetricLabelAttr()
        + "=\"" + data.getMetricLabelValue() + "\"}, value: " + data.getValue() + "]");
  }
}