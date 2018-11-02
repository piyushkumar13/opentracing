## Setup LightStep Satellite (If using LightStep tracer)
LightStep Satellite runs on your premise and act as the proxy. Whenever your application produces any traces, it will 
first sends it to Satellite which then sends it to the LightStep [X]PM UI(SAAS).
You can configure the LightStep satellite using command : 

```bash
docker run -p 80:80 -p8000:8000 -e COLLECTOR_API_KEY={your_API_key} LightStep/collector:latest
```
**Note** :- This satellite will work only for **Go, JS, Objc, PHP, Python, Ruby**.

For **Java and all other languages mentioned above** use following command :
 
```bash
docker run -e COLLECTOR_BABYSITTER_PORT=8000  -p 8000:8000 -e COLLECTOR_PLAIN_PORT=8383 -p 8383:8383 -e COLLECTOR_ADMIN_PLAIN_PORT=8080 -p 8080:8080 -e COLLECTOR_API_KEY=<Account_Level_API_KEY> -e COLLECTOR_HTTP_PLAIN_PORT=8181 -p 8181:8181 -e COLLECTOR_GRPC_PLAIN_PORT=8282 -p 8282:8282 LightStep/collector:latest
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
* LightStep

The project contains three spring boot modules :
* **Service1** - Runs at server port 8888. It exposes two rest apis i.e **/svc1/forward** and **/svc1/forward-far**. These are HTTP GET requests.<br/>
**/svc1/forward** endpoint makes a call to **/svc2/hello** rest endpoint which is exposed by service2 module.<br/>
**/svc1/forward-far** makes call to rest endpoint **/svc2/forward** in service2 which in turn makes a call to **/svc3/hello** rest endpoint in service3.<br/>

* **Service2** - Runs at server port 8081. It exposes rest apis i.e **/svc2/hello** and **/svc2/forward**. These are HTTP GET request.<br/>
Endpoint **/svc2/hello** just returns the "Hello" string to service1 endpoint **/svc1/forward**.<br/>
Endpoint **/svc2/forward** forwards the request to service2 rest endpoint **/svc3/hello**.<br/>

* **Service3** - Runs at server port 8082. It exposes rest api i.e **/svc3/hello**. This api just return "Hello Message" string.

### Running instructions
1. Uncomment any of the following tracers in Service1, Service2 and Service3 Controllers.
    ```java
    /*
     Todo: Uncomment this tracer if you want to use the Jaeger tracer
     @Bean
     public Tracer jaegerTracer() {
 
         return new Configuration("myjaeger-svc1-tracer").getTracerBuilder().build();
     }
 
    Todo: Uncomment this tracer if you want to use the Lightstep tracer to send traces over HTTP
     @Bean
     public Tracer lightStepTracer() throws Exception {
         return new com.lightstep.tracer.jre.JRETracer(
                 new com.lightstep.tracer.shared.Options.OptionsBuilder()
                         .withAccessToken("<project level access token>")
                         .withComponentName("mylightstep-tracer-svc1-on-http")
                         .withCollectorHost("localhost")
                         .withCollectorPort(8181)
                         .withCollectorProtocol("http")
                         .build()
         );
     }
 
 
     Todo: Uncomment this tracer if you want to use the Lightstep tracer to send traces over GRPC
     @Bean
     public Tracer lightStepTracer() throws Exception {
         return new com.lightstep.tracer.jre.JRETracer(
                 new com.lightstep.tracer.shared.Options.OptionsBuilder()
                         .withAccessToken("project level token")
                         .withComponentName("mylightstep-tracer-svc1-on-grpc")
                         .withCollectorHost("localhost")
                         .withCollectorPort(8282)
                         .withCollectorProtocol("http")
                        .build()
        );
     }
    */
    Also follow the comments in modules pom.xml to uncomment dependencies based on the type of transport you want to use for LightStep.
    ```
2. Run service1 at port 8888.
3. Run service2 at port 8081.
4. Run service3 at port 8082.
4. Setup and run [Jaeger](https://www.jaegertracing.io/docs/1.7/getting-started/#AllinOne) or [LightStep](https://docs.LightStep.com/docs/satellite-setup) as docker container on your local. Depending on your choice.
5. Hit endpoint http://localhost:8888/svc1/forward or http://localhost:8888/svc1/forward-far
6. Observe the traces in the Tracer UI (Jaeger or LightStep)