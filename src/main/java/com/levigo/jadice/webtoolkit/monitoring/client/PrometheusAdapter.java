package com.levigo.jadice.webtoolkit.monitoring.client;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;

import com.levigo.jadice.webtoolkit.monitoring.data.CounterData;
import com.levigo.jadice.webtoolkit.monitoring.data.DataObject;
import com.levigo.jadice.webtoolkit.monitoring.data.DurationData;
import com.levigo.jadice.webtoolkit.monitoring.data.ReturnData;

import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import io.prometheus.client.SimpleCollector;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;

public class PrometheusAdapter implements MonitorClient, ServletContextListener {

  private static final String contextPath = "/metrics";

  private CollectorRegistry collectorRegistry = CollectorRegistry.defaultRegistry;
  private Map<String, Collector> collectors = new ConcurrentHashMap<>();

  @Override
  public void publish(DataObject<?> data) {

    String[] labelValues = mapToArray(data.getLabels().values());

    if (data instanceof DurationData) {
      DurationData dd = (DurationData) data;

      // Gauge gauge = getCollector(Gauge.build(), dd);
      Histogram hist = getCollector(Histogram.build(), dd);

      if (dd.hasMetricLabel()) {
        // gauge.labels(dd.getMetricLabelValue()).set(dd.getValue());
        hist.labels(labelValues).observe(dd.getValue());
      } else {
        // gauge.set((dd.getValue()));
        hist.observe(dd.getValue());
      }

    } else if (data instanceof CounterData) {
      CounterData cd = (CounterData) data;

      Counter counter = getCollector(Counter.build(), cd);
      counter.clear();

      if (cd.hasMetricLabel()) {
        counter.labels(labelValues).inc(cd.getValue());
      } else {
        counter.inc(cd.getValue());
      }

    } else if (data instanceof ReturnData) {
      // not supported by Prometheus
      // https://prometheus.io/docs/concepts/metric_types/
    }
  }

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();

    DefaultExports.initialize();
    ServletRegistration.Dynamic dynamic = context.addServlet("MetricsServlet", new MetricsServlet(collectorRegistry));
    dynamic.addMapping(contextPath);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // nothing to do here
  }

  private String[] mapToArray(Collection<String> collection) {
    return collection.toArray(new String[collection.size()]);
  }

  private <C extends SimpleCollector<?>, B extends SimpleCollector.Builder<B, C>> C getCollector(
      SimpleCollector.Builder<B, C> builder, DataObject<?> data) {
    @SuppressWarnings("unchecked")
    C collector = (C) collectors.get(data.getMetricName());
    String[] labelAttributes = mapToArray(data.getLabels().keySet());

    if (null == collector) {
      if (data.hasMetricLabel()) {
        collector = builder.name(data.getMetricName()).help(data.getMetricDescription()).labelNames(
            labelAttributes).register();
      } else {
        collector = builder.name(data.getMetricName()).help(data.getMetricDescription()).register();
      }
      collectors.put(data.getMetricName(), collector);
    }

    return collector;
  }
}
