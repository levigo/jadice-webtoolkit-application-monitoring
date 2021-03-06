# jadice web toolkit - Application Monitoring
The _jadice web toolkit (JWT) - Application Monitoring_ project is an aspect oriented way using load time weaving to monitor server side functionality of the JWT.
It is designed as a basic approach with adaption examples for two different monitoring systems, [Prometheus] and [Riemann]. Both you can use for your own integration.

## Features
- aspect oriented
- annotation based
- two integrated monitor clients as a working example

Please notice that all solutions using a third-party monitoring system, do not provide any security functionality - this means every one can grab your metrics as long as you don't define any access restrictions by your own.

## License
The _JWT - Application Monitoring_ project is licensed under the _BSD 3-Clause License_. For other licensing options please [contact us].

## Support
Support is available via issue tracker. Commercial support is available. For inquiries please [contact us].

## Usage
### Introduction
The section below will guide you through the configuration process of each use case.
Please make sure you only use the required dependencies in the pom.xml.

### Getting started
This guide leads you through the default integration process.
To make the getting started example as simple as possible the metrics will be displayed in the console.
Therefore the _SimpleConsoleAdapter_ is set as default adapter.
1. Download this project and import it as existing Maven project.
2. Download _AspectJ Weaver_ from <https://mvnrepository.com/artifact/org.aspectj/aspectjweaver/1.8.10> or use it as a Maven dependency (doesn't matter in which project - you will need that for step no. 6).
3. Compile the project.
4. Go to _pom.xml_ and replace the JWT-version under `<properties>` if it's necessary.
5. Open the _pom.xml_ of **your** JWT-Project and add this project as provided dependency (make sure you then add the compiled version to your project):
	```xml
	<dependencies>
	  ...
	  <dependency>
	    <groupId>com.levigo.jadice.webtoolkit</groupId>
	    <artifactId>webtoolkit-application-monitoring</artifactId>
	    <version>0.0.1-SNAPSHOT</version>
	    <scope>provided</scope>
	  </dependency>
	  ...
	</dependencies>
	```
6. Open **your** JWT-Project and add _aop.xml_ to `src/main/resources/META-INF`.
7. Edit the _Run Configurations_ of **your** JWT-Project and set the following _VM argument_:
	```
	-javaagent:<PATH-TO-WEAVER>/aspectjweaver-1.8.10.jar
	```

After the steps above your JWT-Application is ready to be monitored and you should see console outputs like:
```
SimpleConsoleAdapter: [metric: basic_jadice_service_get_tile_image_duration{=""}, value: 12]
```

### Run application monitoring with Prometheus
This guide leads you through the integration process for the provided Prometheus adaption example.
If you already followed the steps described in [Getting started], you just can continue like below:
1. Open the file _Publisher.java_ in `src/main/java/com/levigo/jadice/webtoolkit/monitoring` and set `new PrometheusAdapter()` (at line 14) as monitor client.
2. Compile the project.
3. Add PrometheusAdapter as listener to your _web.xml_ (in `src/main/webapp/WEB-INF/`):
	```xml
	<web-app ...>
	  ...
	  <listener>
	    <listener-class>com.levigo.jadice.webtoolkit.monitoring.client.PrometheusAdapter</listener-class>
	  </listener>
	  ...
	</web-app>
	```
	If your runtime environment supports annotations you also can annotate `PrometheusAdapter` with `@WebListener` instead of modifying your _web.xml_.
4. Start Prometheus and make it collect metric data from your host (eg. http://127.0.0.1/metrics).

### Define your own adapter
First of all make sure you've set up your project and the Monitoring Project like it is described in [Getting started].
Then, to define your own Adapter following the steps below:
1. Open `src/main/java/com/levigo/jadice/webtoolkit/monitoring/client`.
2. Create a new Java-Class and implement the interface `MonitorClient`.
3. Open the file _Publisher.java_ in `src/main/java/com/levigo/jadice/webtoolkit/monitoring` and set your adapter (at line 14) as monitor client.
4. Remove not needed dependencies in _pom.xml_.
5. Compile the project.

## Extended Examples
All the extended examples are located in `src/main/java/com/levigo/jadice/webtoolkit/monitoring/extended_examples`. For now there is one more extended example available. There will be more in future.

### Dynamic Label
The _Dynamic Label Example_ is based on the idea that static labels (`@InstrumentedLabel`) are not sufficient for your use case.
This example has a specific pointcut. It becomes active when the method `read(..)` of the interface `com.levigo.jadice.web.server.DocumentDataProvider` is executed.
The Metric name, description and label attribute name are fix and must be set before `DynamicLabelExample` is compiled.
The Label value is determined during runtime. It represents the class name of the currently executing object.

To see the example in action first follow the [Getting started] guide, and then the steps below:
1. Open aop.xml you've copied into **your** project before.
2. Toggle the comment on line including _CustomDynamicLabelExample_.
3. Compile the project.

After the steps above your JWT-Application should produce outputs like:
```
SimpleConsoleAdapter: [metric: dynamic_labels_example_duration{provider="SimpleDocumentProvider"}, value: 38]
```


[Prometheus]: https://prometheus.io
[Riemann]: http://riemann.io
[contact us]: mailto:solutions@levigo.de
[Getting started]: #getting-started
