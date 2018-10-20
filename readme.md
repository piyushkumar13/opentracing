## Setup Lightstep Satellite (If using Lightstep tracer)
Lightstep Satellite runs on your premise and act as the proxy. Whenever your application produces any traces, it will 
first sends it to Satellite which then sends it to the Lightstep UI(SAAS).
You can configure the lightstep satellite using command : 

```bash
docker run -p 80:80 -p8000:8000 -e COLLECTOR_API_KEY={your_API_key} lightstep/collector:latest
```
This satellite will work only for **Go, JS, Objc, PHP, Python, Ruby**.

For **Java and all other languages mentioned above** use following command :
 
```bash
docker run -e COLLECTOR_BABYSITTER_PORT=8000  -p 8000:8000 -e COLLECTOR_PLAIN_PORT=8383 -p 8383:8383 -e COLLECTOR_ADMIN_PLAIN_PORT=8080 -p 8080:8080 -e COLLECTOR_API_KEY=<Account_Level_API_KEY> -e COLLECTOR_HTTP_PLAIN_PORT=8181 -p 8181:8181 -e COLLECTOR_GRPC_PLAIN_PORT=8282 -p 8282:8282 lightstep/collector:latest
``` 

## Setup Jaeger (If using Jaeger tracer)
Use following docker command to run Jaeger locally : 
```bash
docker run -d --name jaeger \
  -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 \
  -p 5775:5775/udp \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 14268:14268 \
  -p 9411:9411 \
  jaegertracing/all-in-one:1.7
```

## Project Setup
This project contains the example which shows how to use Opentracing with Spring boot application.
I am using two opentracing implementations : 
* Uber Jaeger
* Lightstep

The project contains two spring boot modules :
* **Service1** - Runs at server port 8080(default). It exposes a rest api i.e **/svc1/forward**. It is HTTP GET request.
This endpoint makes a call to another endpoint i.e **/svc2/hello which is exposed by service2 module.
* **Service2** - Runs at server port 8081. It exposes a rest api i.e **/svc2/hello**. It is HTTP GET request.
This endpoint is called by service1.

### Running instructions
1. Uncomment any of the following tracers in Service1Controller in service1 module
    ```java
    /*
    Uncomment this tracer if you want to use the Jaeger tracer
    @Bean
    public Tracer jaegerTracer() {

        return new Configuration("myjaeger-tracer").getTracerBuilder().build();
    }*/

   /*
   Uncomment this tracer if you want to use the Lightstep tracer
    @Bean
    public Tracer lightStepTracer() throws Exception {
        return new com.lightstep.tracer.jre.JRETracer(
                new com.lightstep.tracer.shared.Options.OptionsBuilder()
                        .withAccessToken("<project_level_access_token>")
                        .withComponentName("mylightstep-tracer")
                        .withCollectorHost("localhost")
                        .withCollectorPort(8181) // HTTP port used when configuring Lightstep satellite in docker command.
                        .withCollectorProtocol("http")
                        .build()
        );
    }
    */
    ```
2. Run service1 at port 8080.
3. Run service2 at port 8081.
4. Setup and run [Jaeger](https://www.jaegertracing.io/docs/1.7/getting-started/#AllinOne) or [Lightstep](https://docs.lightstep.com/docs/satellite-setup) as docker container on your local. Depending on your choice.
5. Hit endpoint http://localhost:8080/forward.
6. Observe the traces in the Tracer UI (Jaeger or Lightstep)