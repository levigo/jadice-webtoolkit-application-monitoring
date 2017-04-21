package com.levigo.jadice.webtoolkit.monitoring.client;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.levigo.jadice.webtoolkit.monitoring.data.CounterData;
import com.levigo.jadice.webtoolkit.monitoring.data.DataObject;
import com.levigo.jadice.webtoolkit.monitoring.data.DurationData;
import com.levigo.jadice.webtoolkit.monitoring.data.ReturnData;

import io.riemann.riemann.client.RiemannClient;

public class RiemannAdapter implements MonitorClient {

  private RiemannClient riemannClient;

  {
    try {
      riemannClient = RiemannClient.tcp("localhost", 5555);
      riemannClient.connect();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void publish(DataObject<?> data) {

    String tags = data.hasMetricLabel() ? data.getMetricLabelAttr() + "=\"" + data.getMetricLabelValue() + "\"" : "";

    if (data instanceof CounterData) {
      CounterData counterData = (CounterData) data; 
      fireRiemannEvent(counterData.getMetricName(), "", counterData.getValue(), tags);
      
    } else if (data instanceof DurationData) {
      DurationData durationData = (DurationData) data;
      fireRiemannEvent(durationData.getMetricName(), "", durationData.getValue(), tags);

    } else if (data instanceof ReturnData) {
      ReturnData returnData = (ReturnData) data;
      fireRiemannEvent(returnData.getMetricName(), returnData.getValue().toString(), 0, tags);
    }
  }
  
  private void fireRiemannEvent(String service, String state, double metric, String... tags) {
    try {
      riemannClient.event() //
          .service(service) //
          .state(state) //
          .metric(metric) //
          .tags(tags) //
          .send() //
          .deref(1, TimeUnit.SECONDS);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}