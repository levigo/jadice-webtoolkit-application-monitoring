package com.levigo.jadice.webtoolkit.monitoring.data;

public class DurationData extends AbstractDataObject<Long> {

  public DurationData(String metricName, String metricDescription, String label, long value) {
    super(metricName, metricDescription, label, value);
  }

}