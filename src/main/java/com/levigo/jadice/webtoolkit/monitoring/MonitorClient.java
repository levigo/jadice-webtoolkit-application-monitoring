package com.levigo.jadice.webtoolkit.monitoring;

public interface MonitorClient {

  public void publish(DataObject<?> data);

}