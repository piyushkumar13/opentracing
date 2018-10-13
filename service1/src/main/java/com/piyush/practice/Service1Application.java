package com.piyush.practice;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ProbabilisticSampler;
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

    /*

    Uncomment this tracer if you want to use the Je
    @Bean
    public Tracer jaegerTracer() {

        return new Configuration("myjaeger-tracer").getTracerBuilder().build();
    }*/

    @Bean
    public Tracer lightStepTracer() throws Exception {
        return new com.lightstep.tracer.jre.JRETracer(
                new com.lightstep.tracer.shared.Options.OptionsBuilder()
                        .withAccessToken("6fd564a1ef95843af419a0e6c289d683")
                        .withComponentName("mylightstep-tracer")
                        .withCollectorHost("localhost")
                        .withCollectorPort(8443)
                        .build()
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(Service1Application.class, args);
    }
}
