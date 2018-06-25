package com.gildata.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LiChao on 2018/4/26
 */

@RestController
public class HelloController {

    @RequestMapping("/say")
    String home(){
        System.out.println("welcome");
        return "hello world!";
    }

    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name){
        return "hello " + name;
    }

}
