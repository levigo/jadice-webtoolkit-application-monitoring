package com.levigo.jadice.webtoolkit.monitoring.data;

import com.levigo.jadice.webtoolkit.monitoring.AbstractDataObject;

public class ReturnData extends AbstractDataObject<Object> {

  public ReturnData(String metricName, String label, Object value) {
    super(metricName, label, value);
  }

}