package com.levigo.jadice.webtoolkit.monitoring;

public interface DataObject<T> {
  
  public String getMetricName();
  
  public String getMetricDescription();
  
  public String getMetricLabel();
  
  public T getValue();
  
}