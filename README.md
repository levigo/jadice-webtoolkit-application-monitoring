# jadice web toolkit - Application Monitoring
The jadice web toolkit (JWT) - Application Monitoring project is a aspect oriented way to monitor server side functionality of the JWT.
It is designed as a basic approach with adaption examples for two different monitoring systems, Prometheus and Riemann, you can use for your own integration.

## Features
- aspect orientated
- annotation based
- two integrated monitor clients as a working example

## License
## Support
Support is available via issue tracker. Commercial support is available. For inquiries please [contact us](mailto:solutions@levigo.de).

## Usage
### Introduction
The section below will guide you through the configuration process of each use case. 

### Getting started
This guide leads you through the default integration process.
To make the example as simple as possible the metrics will be displayed in the console.
Therefore the _SimpleConsoleAdapter_ is set as default adapter.
1. Download this project and import it as existing Maven project.
2. Download _AspectJ Weaver_ from https://mvnrepository.com/artifact/org.aspectj/aspectjweaver/1.8.10
3. Go to _pom.xml_ and replace the JWT-version under `<properties>` if it's necessary.
4. Open your JWT-Project and add the aop.xml to `src/main/resources/META-INF`.
5. Edit your _Run Configurations_ and set the following _VM argument_: `-javaagent:<PATH-TO-WEAVER>/aspectjweaver-1.8.10.jar`

After these steps your JWT-Application is ready to be monitored and you should see console outputs like:
```
ConsoleAdapter: [metric: basic_jadice_service_get_tile_image_duration{=""}, value: 12]
```

### Run application monitoring with Prometheus


### Define your own adapter