package com.rmit.main.library.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rmit.main.library.enums.Department;
import com.rmit.main.library.feed.model.Contacts;

@Repository
public interface ContactsRepository extends MongoRepository<Contacts, String> {

	public Contacts findOneByContactId(String id);

	public Page<Contacts> findByDepartmentOrderByCreatedDateDesc(Department department, Pageable pageable);
	
	public Page<Contacts> findByDepartmentInOrderByCreatedDateDesc(Set<Department> department,Pageable pageable);
	
	long countByDepartmentIn(Set<Department> department);

	public Page<Contacts> findAllByOrderByCreatedDateDesc(Pageable pageable);

	long countByDepartment(Department department);

}
