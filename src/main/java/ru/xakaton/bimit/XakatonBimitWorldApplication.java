package ru.xakaton.bimit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class XakatonBimitWorldApplication extends SpringBootServletInitializer {

	// for war
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(XakatonBimitWorldApplication.class);
	}

	// for jar
	public static void main(String[] args) {
		SpringApplication.run(XakatonBimitWorldApplication.class, args);
	}

}
