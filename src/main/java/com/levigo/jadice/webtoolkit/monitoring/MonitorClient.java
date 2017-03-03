package com.levigo.jadice.webtoolkit.monitoring;

public interface MonitorClient {

  public void publish(final Class<? extends Base> type, String name, Object value);

}