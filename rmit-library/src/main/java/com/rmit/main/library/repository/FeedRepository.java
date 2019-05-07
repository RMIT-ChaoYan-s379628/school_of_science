package com.rmit.main.library.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rmit.main.library.enums.Department;
import com.rmit.main.library.enums.Category;
import com.rmit.main.library.feed.model.Feed;


@Repository
public interface FeedRepository extends MongoRepository<Feed, String> {
	
	public Feed findOneByFeedId(String id);
	
	public Page<Feed> findByCategoryAndDepartmentInOrderByCreatedDateDesc(Category category,Set<Department> department,Pageable pageable);
	
	public long countByCategoryAndDepartmentIn(Category category,Set<Department> department);
	
	public Page<Feed> findByCategoryOrderByCreatedDateDesc(Category category,Pageable pageable);
	
	public long countByCategory(Category category);


	public Page<Feed> findByCategoryAndDepartmentInAndIsDeletedOrderByCreatedDateDesc(Category category,Set<Department> department, Boolean deleted, Pageable pageable);

	public Page<Feed> findByCategoryAndIsDeletedOrderByCreatedDateDesc(Category category, Boolean deleted, Pageable pageable);
}
