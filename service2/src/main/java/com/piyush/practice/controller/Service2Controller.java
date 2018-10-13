package com.piyush.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Piyush Kumar.
 * @since 10/13/18.
 */

@RestController
public class Service2Controller {

    @GetMapping(path = "/svc2/hello")
    public String sayHello(){
        return "Hello";
    }
}
