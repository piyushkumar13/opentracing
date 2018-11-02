package com.piyush.practice;

import io.opentracing.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Service1Application {

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }

//    Todo: Uncomment this tracer if you want to use the Jaeger tracer
//    @Bean
//    public Tracer jaegerTracer() {
//
//        return new Configuration("myjaeger-svc1-tracer").getTracerBuilder().build();
//    }
//
//   Todo: Uncomment this tracer if you want to use the Lightstep tracer to send traces over HTTP
//    @Bean
//    public Tracer lightStepTracer() throws Exception {
//        return new com.lightstep.tracer.jre.JRETracer(
//                new com.lightstep.tracer.shared.Options.OptionsBuilder()
//                        .withAccessToken("<project level access token>")
//                        .withComponentName("mylightstep-tracer-svc1-on-http")
//                        .withCollectorHost("localhost")
//                        .withCollectorPort(8181)
//                        .withCollectorProtocol("http")
//                        .build()
//        );
//    }
//
//
//    Todo: Uncomment this tracer if you want to use the Lightstep tracer to send traces over GRPC
//    @Bean
//    public Tracer lightStepTracer() throws Exception {
//        return new com.lightstep.tracer.jre.JRETracer(
//                new com.lightstep.tracer.shared.Options.OptionsBuilder()
//                        .withAccessToken("project level token")
//                        .withComponentName("mylightstep-tracer-svc1-on-grpc")
//                        .withCollectorHost("localhost")
//                        .withCollectorPort(8282)
//                        .withCollectorProtocol("http")
//                       .build()
//       );
//    }


    public static void main(String[] args) {
        SpringApplication.run(Service1Application.class, args);
    }
}
