package com.scout24.tech.challenge;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ImmobilienScout24 {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImmobilienScout24.class);

	public static void main(String[] args) {
		try {
			SpringApplication.run(ImmobilienScout24.class, args);
		} catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LOGGER.error(sw.toString());
		}
	}

}
