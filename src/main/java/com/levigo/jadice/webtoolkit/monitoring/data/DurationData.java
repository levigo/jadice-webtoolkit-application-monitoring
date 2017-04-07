package com.levigo.jadice.webtoolkit.monitoring.data;

import com.levigo.jadice.webtoolkit.monitoring.AbstractDataObject;

public class DurationData extends AbstractDataObject<Double> {

  public DurationData(String metricName, String label, Double value) {
    super(metricName, label, value);
  }

}