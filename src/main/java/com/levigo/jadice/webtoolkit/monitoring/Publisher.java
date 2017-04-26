package com.levigo.jadice.webtoolkit.monitoring;

import com.levigo.jadice.webtoolkit.monitoring.client.MonitorClient;
import com.levigo.jadice.webtoolkit.monitoring.client.SimpleConsoleAdapter;
import com.levigo.jadice.webtoolkit.monitoring.data.DataObject;

/**
 * Publisher connects aspects to a respective adaptor (client) that was designed to send or provide
 * data to/for a specific monitoring system.
 *
 */
final public class Publisher {

  private static final MonitorClient monitorClient = new SimpleConsoleAdapter();
  private static Publisher instance;

  /**
   * Returns the one and only instance of Publisher.
   * 
   * @return The instance of Publisher.
   */
  public static Publisher getInstance() {
    if (null == instance) {
      instance = new Publisher();
    }

    return instance;
  }

  /**
   * Private constructor to prevent from instantiation.
   */
  private Publisher() {
  };

  /**
   * Pushes the given data to the publisher.
   */
  public void pushToAdapter(DataObject<?> data) {
    monitorClient.publish(data);
  }
}