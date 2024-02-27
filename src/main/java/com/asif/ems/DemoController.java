package com.asif.ems;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping("/okay")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from the other world!");
    }
}
