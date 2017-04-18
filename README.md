# jadice web toolkit - Application Monitoring
The jadice web toolkit (JWT) - Application Monitoring project is a aspect oriented way to monitor server side functionality of the JWT.
It is designed as a basic approach with adaption examples for two different monitoring systems, Prometheus and Riemann, you can use for your own integration.

## Features
- aspect orientated
- annotation based
- two integrated monitor clients as a working example

Please notice that all solutions, using a third-party monitoring system, do not provide any security functionality - so every one can grab your metrics as long as you don't define any access restrictions by your own.

## License
The JWT - Application Monitoring project is licensed under the BSD 3-Clause License. For other licensing options please [contact us].
## Support
Support is available via issue tracker. Commercial support is available. For inquiries please [contact us].

## Usage
### Introduction
The section below will guide you through the configuration process of each use case. 

### Getting started
This guide leads you through the default integration process.
To make the example as simple as possible the metrics will be displayed in the console.
Therefore the _SimpleConsoleAdapter_ is set as default adapter.
1. Download this project and import it as existing Maven project.
2. Download _AspectJ Weaver_ from <https://mvnrepository.com/artifact/org.aspectj/aspectjweaver/1.8.10> or use it as a Maven dependency (doesn't matter in which project - you will need that for step no. 6).
3. Go to _pom.xml_ and replace the JWT-version under `<properties>` if it's necessary.
4. Open the _pom.xml_ of **your** JWT-Project and add this project as provided dependency (make sure you then add the compiled version to your project):
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
5. Open **your** JWT-Project and add the aop.xml to `src/main/resources/META-INF`.
6. Edit the _Run Configurations_ of **your** JWT-Project and set the following _VM argument_:
	```
	-javaagent:<PATH-TO-WEAVER>/aspectjweaver-1.8.10.jar
	```

After the steps above your JWT-Application is ready to be monitored and you should see console outputs like:
```
SimpleConsoleAdapter: [metric: basic_jadice_service_get_tile_image_duration{=""}, value: 12]
```

### Run application monitoring with Prometheus
This guide leads you through the integration process for the provided Prometheus adaption example.
If you already followed the steps described in [Getting started](#getting-started) you just continue like below:
1. Open the file _Publisher.java_ in `src/main/java/com/levigo/jadice/webtoolkit/monitoring` and set `new PrometheusAdapter()` (in line 14) as monitor client.
2. Start Prometheus and make it collect metric data from your host (eg. 127.0.0.1/metrics).

### Define your own adapter
To define your own Adapter following the steps below:
1. Open `src/main/java/com/levigo/jadice/webtoolkit/monitoring/client`.
2. Create a new Java-Class and implement the interface `MonitorClient`.
3. Open the file _Publisher.java_ in `src/main/java/com/levigo/jadice/webtoolkit/monitoring` and set your adapter (in line 14) as monitor client.

[contact us]: mailto:solutions@levigo.de