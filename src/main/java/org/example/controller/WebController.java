package org.example.controller;

import org.example.service.WebService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/library")
public class WebController {

    private final WebService webService;

    public WebController(WebService webService) {
        this.webService = webService;
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return webService.getMessage();
    }

    @GetMapping(value = "/hello2")
    public String hello2() {
        return webService.getRepoMessage();
    }
}
