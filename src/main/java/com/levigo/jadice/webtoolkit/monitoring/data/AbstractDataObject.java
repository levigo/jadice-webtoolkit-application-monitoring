package com.levigo.jadice.webtoolkit.monitoring.data;

import com.levigo.jadice.webtoolkit.monitoring.DataObject;

public abstract class AbstractDataObject<T> implements DataObject<T> {

  protected String metricName;
  protected String label;
  protected T value;
  
  public AbstractDataObject(String metricName, String label, T value) {
    this.metricName = metricName;
    this.label = label;
    this.value = value;
  }

  @Override
  public String getMetricName() {
    return metricName;
  }

  @Override
  public String getMetricLabel() {
    return label;
  }

  @Override
  public T getValue() {
    return value;
  }

}
