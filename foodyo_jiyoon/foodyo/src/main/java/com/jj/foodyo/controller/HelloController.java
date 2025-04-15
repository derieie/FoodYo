package com.jj.foodyo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

        @GetMapping("/ ")
        public String hello() {
            return  "<html><body><h1>네이버 제목</h1></body></html>";
        }
}