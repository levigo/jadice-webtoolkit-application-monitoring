package com.levigo.jadice.webtoolkit.monitoring.client;

import com.levigo.jadice.webtoolkit.monitoring.data.DataObject;

public interface MonitorClient {

  public void publish(DataObject<?> data);

}