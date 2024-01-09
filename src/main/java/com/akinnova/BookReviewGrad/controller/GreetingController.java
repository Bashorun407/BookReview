package com.akinnova.BookReviewGrad.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class GreetingController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello friend!!";
    }
}
