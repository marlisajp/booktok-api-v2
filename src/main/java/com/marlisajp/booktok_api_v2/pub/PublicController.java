package com.marlisajp.booktok_api_v2.pub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
