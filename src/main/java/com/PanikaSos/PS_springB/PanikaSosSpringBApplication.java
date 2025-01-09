package com.PanikaSos.PS_springB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PanikaSosSpringBApplication {

	public static void main(String[] args) {
		SpringApplication.run(PanikaSosSpringBApplication.class, args);
	}

}
