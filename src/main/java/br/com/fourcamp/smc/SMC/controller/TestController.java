package br.com.fourcamp.smc.SMC.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for testing the application's availability.
 */
@RestController
public class TestController {

    /**
     * Endpoint to test if the application is running.
     *
     * @return a string message indicating that the application is running
     */
    @GetMapping("/test")
    public String test() {
        return "Application is running";
    }
}
