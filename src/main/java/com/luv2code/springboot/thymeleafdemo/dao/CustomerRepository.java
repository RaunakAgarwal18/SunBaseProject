package com.luv2code.springboot.thymeleafdemo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springboot.thymeleafdemo.entity.Customer;
import org.springframework.data.jpa.repository.Query;

// Employee represents the type of data we are using and Integer represents the data type of the primary key
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	// that's it ... no need to write any code LOL!
	// Sort the list by last name
	public List<Customer> findAllByOrderByLastNameAsc();

	public List<Customer> findByFirstNameContainingIgnoreCase(String keyword);

	public List<Customer> findByEmailContainingIgnoreCase(String keyword);

	public List<Customer> findByPhoneContainingIgnoreCase(String keyword);
}
