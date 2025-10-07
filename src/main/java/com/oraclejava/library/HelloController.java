package com.oraclejava.library;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class HelloController {

    @GetMapping(value="/hello")
    public String hello() {
        log.info("hello controller");
        return "hello";
    }
    
}
