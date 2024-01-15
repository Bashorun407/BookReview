//package com.akinnova.BookReviewGrad.controller;
//
//import com.akinnova.BookReviewGrad.service.JpaUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class GreetingController {
//
//    @Autowired
//    private JpaUserDetailsService jpaUserDetailsService;
//    @GetMapping("/hello/{username}")
//    public String hello(@PathVariable String username){
//        jpaUserDetailsService.loadUserByUsername( username);
//        return "Hello friend!!";
//    }
//}
