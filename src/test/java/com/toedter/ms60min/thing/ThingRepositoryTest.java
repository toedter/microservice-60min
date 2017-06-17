package com.toedter.ms60min.thing;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThingRepositoryTest {

	@Autowired
	ThingRepository thingRepository;

	@Test
	public void shouldFindsAllThings() {
		Iterable<Thing> things = thingRepository.findAll();
		assertThat(things, is(not(emptyIterable())));
	}

	@Test
	public void shouldCreatesNewThing() {
		Long before = thingRepository.count();

		Thing thing = thingRepository.save(createThing());

		Iterable<Thing> result = thingRepository.findAll();
		assertThat(result, is(Matchers.<Thing>iterableWithSize(before.intValue() + 1)));
		assertThat(result, hasItem(thing));
	}

	public static Thing createThing() {
		Thing testThing = new Thing("testThing", "Test", "Black");
		return testThing;
	}
}
