package com.levigo.jadice.webtoolkit.monitoring.data;

import java.util.HashMap;
import java.util.Map;

/**
 * This class defines a data transfer object to make it easier for the adapter to decide which
 * metric type to use.
 *
 * @param <T> Usually a primitive data type.
 */
public abstract class DataObject<T> {

  protected String metricName = "";
  protected String metricDescription = "";
  protected Map<String, String> labels = new HashMap<>();
  protected T value;

  public DataObject(T value) {
    this.value = value;
  }

  public String getMetricName() {
    return metricName;
  }

  public void setMetricName(String metricName) {
    this.metricName = metricName;
  }

  public String getMetricDescription() {
    return metricDescription;
  }

  public void setMetricDescription(String metricDescription) {
    this.metricDescription = metricDescription;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public Map<String, String> getLabels() {
    return this.labels;
  }

  public boolean hasMetricLabel() {
    return !labels.isEmpty();
  }
}