package com.example.camundaservice;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Deployment(resources = "classpath:ImageProcess.bpmn")
public class CamundaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamundaServiceApplication.class, args);
	}

}
