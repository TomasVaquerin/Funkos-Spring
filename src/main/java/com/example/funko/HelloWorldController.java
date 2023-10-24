package com.example.funko;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hola")
    public String helloWorld() {
        return "¡Hola, mundo!";
    }
}