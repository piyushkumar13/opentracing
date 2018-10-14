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
                        .withAccessToken("<project access api>")
                        .withComponentName("mylightstep-tracer")
                        .withCollectorHost("localhost")
                        .withCollectorPort(80)
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