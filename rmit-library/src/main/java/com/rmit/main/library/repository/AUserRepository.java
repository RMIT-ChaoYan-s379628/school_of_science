package com.rmit.main.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rmit.main.library.enums.UserStatus;
import com.rmit.main.library.feed.model.User;


@Repository
public interface AUserRepository extends MongoRepository<User	, String>{
	
	public User findOneByUserIdAndStatus(String userId, UserStatus status);
	
	public Page<User> findAllByOrderByCreatedDateDesc(Pageable pageable);
	
	public long countByStatus(UserStatus status);
	
	public User findOneByUserId(String userId);

}
