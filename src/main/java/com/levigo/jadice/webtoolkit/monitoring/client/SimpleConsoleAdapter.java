package com.levigo.jadice.webtoolkit.monitoring.client;

import java.util.Map;

import com.levigo.jadice.webtoolkit.monitoring.data.DataObject;

public class SimpleConsoleAdapter implements MonitorClient {

  @Override
  public void publish(DataObject<?> data) {

    String labels = "";
    for (Map.Entry<String, String> entry : data.getLabels().entrySet()) {
      labels += entry.getKey() + "=\"" + entry.getValue() + "\" ";
    }
    labels = labels.trim();

    System.out.println(
        "SimpleConsoleAdapter: [metric: " + data.getMetricName() + "{" + labels + "}, value: " + data.getValue() + "]");
  }
}