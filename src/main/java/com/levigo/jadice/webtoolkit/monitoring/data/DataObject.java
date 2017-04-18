package com.levigo.jadice.webtoolkit.monitoring.data;

/**
 * This class defines a data transfer object to make it easier for the adapter to decide which
 * metric type to use.
 *
 * @param <T> Usually a primitive data type.
 */
public abstract class DataObject<T> {

  protected String metricName = "";
  protected String metricDescription = "";
  protected String metricLabelAttr = "";
  protected String metricLabelValue = "";
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

  public String getMetricLabelAttr() {
    return metricLabelAttr;
  }

  public void setMetriclabelAttr(String metriclabelAttr) {
    this.metricLabelAttr = metriclabelAttr;
  }

  public String getMetricLabelValue() {
    return metricLabelValue;
  }

  public void setMetriclabelValue(String metriclabelValue) {
    this.metricLabelValue = metriclabelValue;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public boolean hasMetricLabel() {
    return this.metricLabelAttr.length() > 0 && this.metricLabelValue.length() > 0;
  }
}