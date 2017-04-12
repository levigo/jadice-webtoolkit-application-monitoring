package com.levigo.jadice.webtoolkit.monitoring;

import com.levigo.jadice.webtoolkit.monitoring.client.MonitorClient;
import com.levigo.jadice.webtoolkit.monitoring.client.PrometheusAdapter;
import com.levigo.jadice.webtoolkit.monitoring.data.DataObject;

final public class Publisher {
  
  // TODO: Get this information from a config file?
  private static final MonitorClient monitorClient = new PrometheusAdapter();
  
  private static Publisher instance;
  
  public static Publisher getInstance() {
    if (null == instance) {
      instance = new Publisher();
    }
    
    return instance;
  }

  private Publisher(){};
  
  public void pushToAdapter(DataObject<?> data) {
    monitorClient.publish(data);
  }

}