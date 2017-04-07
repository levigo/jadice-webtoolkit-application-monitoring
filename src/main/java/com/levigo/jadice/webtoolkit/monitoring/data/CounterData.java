package com.levigo.jadice.webtoolkit.monitoring.data;

import com.levigo.jadice.webtoolkit.monitoring.AbstractDataObject;

public class CounterData extends AbstractDataObject<Long> {

  public CounterData(String metricName, String label, Long value) {
    super(metricName, label, value);
  }

}