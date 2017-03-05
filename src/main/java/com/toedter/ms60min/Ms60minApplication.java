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
		SpringApplication.run(Ms60minApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ThingRepository thingRepository) {
		return args -> {
			thingRepository.save(new Thing("1", "Hammer", "Black"));
			thingRepository.save(new Thing("2", "Flute", "Silver"));
			thingRepository.save(new Thing("3", "Car", "Blue"));
		};
	}
}
