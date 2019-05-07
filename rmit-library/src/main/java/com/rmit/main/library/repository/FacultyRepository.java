package com.rmit.main.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rmit.main.library.enums.UserStatus;
import com.rmit.main.library.feed.model.Faculty;


@Repository
public interface FacultyRepository extends MongoRepository<Faculty, String>{
	
	public Faculty findOneByUserIdAndStatus(String userId, UserStatus status);
	
	public Page<Faculty> findAllByOrderByCreatedDateDesc(Pageable pageable);
	
	public long countByStatus(UserStatus status);
	
	public Faculty findOneByUserId(String userId);

	public void deleteFacultyByUserId(String userId);

}
