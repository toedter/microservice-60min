package com.toedter.ms60min.thing;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ThingRepositoryTest {

	@Autowired
	ThingRepository thingRepository;

	@Test
	@DisplayName("should finds all things")
	public void shouldFindsAllThings() {
		Iterable<Thing> things = thingRepository.findAll();
		assertThat(things, is(not(emptyIterable())));
	}

	@Test
	@DisplayName("should create new thing")
	public void shouldCreatesNewThing() {
		Long before = thingRepository.count();

		Thing thing = thingRepository.save(createThing());

		Iterable<Thing> result = thingRepository.findAll();
		assertThat(result, is(Matchers.<Thing>iterableWithSize(before.intValue() + 1)));
		assertThat(result, hasItem(thing));
	}

	public static Thing createThing() {
		Thing testThing = new Thing("Test", "Black");
		return testThing;
	}
}
