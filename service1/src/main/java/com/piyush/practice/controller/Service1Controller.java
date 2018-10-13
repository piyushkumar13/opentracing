package com.piyush.practice.controller;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Piyush Kumar.
 * @since 10/13/18.
 */

@RestController
public class Service1Controller {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping(path = "/svc1/forward")
    public String forwardReq(){

         return restTemplate.getForEntity("http://localhost:8081/svc2/hello",String.class).getBody();
    }

}
