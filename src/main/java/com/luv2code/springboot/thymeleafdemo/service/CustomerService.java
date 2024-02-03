package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {

	List<Customer> findAll();
	
	Customer findById(int theId);
	
	void save(Customer theEmployee);
	
	void deleteById(int theId);
	public Page<Customer> findCustomersWithPagination(int offset,int pageSize);

	public List<Customer> searchCustomersByFirstName(String keyword);

	public List<Customer> searchCustomersByEmail(String keyword);

	public List<Customer> searchCustomersByPhoneNumber(String keyword);
	
}
