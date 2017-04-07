package com.levigo.jadice.webtoolkit.monitoring.data;

public class CounterData extends AbstractDataObject<Long> {

  public CounterData(String metricName, String label, Long value) {
    super(metricName, label, value);
  }

}