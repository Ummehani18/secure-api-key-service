package com.hdev.apikeymanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class DataController {

        @GetMapping
        public String getData() {
            return "Protected API accessed successfully";
        }
}
