package br.com.fourcamp.smc.SMC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.fourcamp.smc.SMC")
public class SmcApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmcApplication.class, args);
	}
}
