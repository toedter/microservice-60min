package com.toedter.ms60min.thing;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ThingRepository extends PagingAndSortingRepository<Thing, String> {
}
