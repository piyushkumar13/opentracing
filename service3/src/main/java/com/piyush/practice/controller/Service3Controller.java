package com.piyush.practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Piyush Kumar.
 * @since 10/23/18.
 */

@RestController
public class Service3Controller {

    @GetMapping(path = "/svc3/hello")
    public String getMsg() {
        return "Hello Message";
    }
}
