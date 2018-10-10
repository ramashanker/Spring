package com.tanu.methods.models;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>{

	public Customer findByName(String name);
	public Customer findBycustId(String custId);
	public void deleteByname(String name);
	
}
