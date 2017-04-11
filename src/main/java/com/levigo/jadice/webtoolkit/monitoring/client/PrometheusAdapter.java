package com.levigo.jadice.webtoolkit.monitoring.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;

import com.levigo.jadice.webtoolkit.monitoring.DataObject;
import com.levigo.jadice.webtoolkit.monitoring.MonitorClient;
import com.levigo.jadice.webtoolkit.monitoring.data.CounterData;
import com.levigo.jadice.webtoolkit.monitoring.data.DurationData;
import com.levigo.jadice.webtoolkit.monitoring.data.ReturnData;

import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.SimpleCollector;
import io.prometheus.client.exporter.MetricsServlet;

public class PrometheusAdapter implements MonitorClient, ServletContextListener {

  private static final String contextPath = "/metrics";

  private CollectorRegistry collectorRegistry = CollectorRegistry.defaultRegistry;
  private Map<String, Collector> collectors = new ConcurrentHashMap<>();

  // TODO: Port
  // Server server = new Server(port);

  @Override
  public void publish(DataObject<?> data) {

    // TODO: Remove!
    System.out.println("PrometheusAdapter: [metric: " + data.getMetricName() + ", value: " + data.getValue() + "]");

    if (data instanceof DurationData) {
      DurationData dd = (DurationData) data;

      Gauge gauge = getCollector(Gauge.build(), dd.getMetricName(), dd.getMetricDescription());
      gauge.set((dd.getValue()));

    } else if (data instanceof CounterData) {
      CounterData cd = (CounterData) data;

      Counter counter = getCollector(Counter.build(), cd.getMetricName(), cd.getMetricDescription());
      counter.clear();
      counter.inc(cd.getValue());

    } else if (data instanceof ReturnData) {
      // not supported by Prometheus
      // https://prometheus.io/docs/concepts/metric_types/
    }
  }

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();

    ServletRegistration.Dynamic dynamic = context.addServlet("MetricsServlet", new MetricsServlet(collectorRegistry));
    dynamic.addMapping(contextPath);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // nothing to do here
  }

  private <C extends SimpleCollector<?>, B extends SimpleCollector.Builder<B, C>> C getCollector(
      SimpleCollector.Builder<B, C> builder, String metricName, String metricDescription) {
    @SuppressWarnings("unchecked")
    C collector = (C) collectors.get(metricName);
    if (null == collector) {
      collector = builder.name(metricName).help(metricDescription).register();
      collectors.put(metricName, collector);
    }

    return collector;
  }
}