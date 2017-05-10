package com.toedter.ms60min;

import com.toedter.ms60min.thing.Thing;
import com.toedter.ms60min.thing.ThingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Ms60minApplication {

	public static void main(String[] args) {
		// when deployed as a docker container to Heroku
		// Heroku sets the PORT environment variable
		// The DYNO environment variable is just to make sure to run in an Heroku environment
		String ENV_PORT = System.getenv().get("PORT");
		String ENV_DYNO = System.getenv().get("DYNO");
		if(ENV_PORT != null && ENV_DYNO != null) {
			System.getProperties().put("server.port", ENV_PORT);
		}

		SpringApplication.run(Ms60minApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ThingRepository thingRepository) {
		return args -> {
			thingRepository.save(new Thing("1", "VW", "Black"));
			thingRepository.save(new Thing("2", "Porsche", "Red"));
			thingRepository.save(new Thing("3", "Mercedes", "Gold"));
		};
	}
}
