package com.levigo.jadice.webtoolkit.monitoring.data;

public class DurationData extends DataObject<Long> {

  public DurationData(long value) {
    super(value);
  }

  public DurationData() {
    super(System.currentTimeMillis());
  }

  public DataObject<?> end() {
    value = System.currentTimeMillis() - value; 
    return this;
  }

}