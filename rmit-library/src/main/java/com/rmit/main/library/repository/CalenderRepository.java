package com.rmit.main.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rmit.main.library.feed.model.CalenderEvents;

@Repository
public interface CalenderRepository extends MongoRepository<CalenderEvents, String>{
	

}
