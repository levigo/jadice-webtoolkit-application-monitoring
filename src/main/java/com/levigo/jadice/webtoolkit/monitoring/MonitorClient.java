package com.levigo.jadice.webtoolkit.monitoring;

public interface MonitorClient {
  
  public void publish(final String metric, final Object value);

}