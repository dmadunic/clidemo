package com.ag04.clidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ClidemoApplication {
	private static final Logger log = LoggerFactory.getLogger(ClidemoApplication.class);

	public static void main(String[] args) {
		//ConfigurableApplicationContext context = SpringApplication.run(ClidemoApplication.class, args);
		SpringApplication app = new SpringApplication(ClidemoApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        Environment env = app.run(args).getEnvironment();
	}

}

