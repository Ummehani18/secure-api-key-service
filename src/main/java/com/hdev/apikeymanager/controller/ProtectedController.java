package com.hdev.apikeymanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class ProtectedController {

    @GetMapping("/secure")
    public String secureEndpoint(){
        return "You are authenticated";
    }
}
