package com.toedter.ms60min;

import com.toedter.ms60min.thing.Thing;
import com.toedter.ms60min.thing.ThingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.DefaultCurieProvider;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Ms60minApplication implements WebMvcConfigurer {

    public @Bean
    CurieProvider curieProvider() {
        return new DefaultCurieProvider("ms60min", UriTemplate.of("/docs/html5/{rel}.html"));
    }

    public static void main(String[] args) {
        // when deployed as a docker container to Heroku
        // Heroku sets the PORT environment variable
        // The DYNO environment variable is just to make sure to run in an Heroku environment
        String herokuPort = System.getenv().get("PORT");
        String herokuDyno = System.getenv().get("DYNO");
        if (herokuPort != null && herokuDyno != null) {
            System.getProperties().put("server.port", herokuPort);
        }
        SpringApplication.run(Ms60minApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ThingRepository thingRepository) {
        List<String> webColors = Arrays.asList("Red", "DarkBlue", "Gold", "LightBlue", "Purple",
                "White", "Silver", "Gray", "Black", "Orange", "Maroon", "Green", "Olive");
        List<String> things = Arrays.asList("Hammer", "Car", "Violin", "Heating", "Valve", "Trumpet",
                "Piano", "Computer", "Stone", "Building");
        Random random = new Random();
        int colorsSize = webColors.size();
        int thingsSize = things.size();

        return args -> {
            for (int i = 0; i < 100; i++) {
                thingRepository.save(new Thing(
                        things.get(random.nextInt(thingsSize)),
                        webColors.get(random.nextInt(colorsSize))));
            }
        };
    }
}
