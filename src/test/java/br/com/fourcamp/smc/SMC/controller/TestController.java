package br.com.fourcamp.smc.SMC.controller;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/customException")
    public void customException() {
        throw new CustomException(ErrorMessage.ACCESS_DENIED);
    }

    @GetMapping("/generalException")
    public void generalException() {
        throw new RuntimeException("Internal Server Error");
    }
}
