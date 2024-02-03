package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.luv2code.springboot.thymeleafdemo.dao.CustomerRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository theCustomerRepository) {
		customerRepository = theCustomerRepository;
	}
	
	@Override
	public List<Customer> findAll() {
		return customerRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public Customer findById(int theId) {
		Optional<Customer> result = customerRepository.findById(theId);
		
		Customer theCustomer = null;
		
		if (result.isPresent()) {
			theCustomer = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find employee id - " + theId);
		}
		
		return theCustomer;
	}

	@Override
	public void save(Customer theCustomer) {
		if(theCustomer!=null)
			customerRepository.save(theCustomer);
	}

	@Override
	public void deleteById(int theId) {
		customerRepository.deleteById(theId);
	}

	public Page<Customer> findCustomersWithPagination(int offset,int pageSize){
		Page<Customer> customers = customerRepository.findAll(PageRequest.of(offset,pageSize));
		return customers;
	}

	public Page<Customer> findCustomersWithPaginationByFirstName(String keyword,int offset,int pageSize){
		List<Customer> list = searchCustomersByFirstName(keyword);
		return new PageImpl<>(list, PageRequest.of(offset, pageSize), list.size());
    }

	public Page<Customer> findCustomersWithPaginationByEmail(String keyword,int offset,int pageSize){
		List<Customer> list = searchCustomersByEmail(keyword);
		return new PageImpl<>(list, PageRequest.of(offset, pageSize), list.size());
	}

	public Page<Customer> findCustomersWithPaginationByPhone(String keyword,int offset,int pageSize){
		List<Customer> list = searchCustomersByPhoneNumber(keyword);
		return new PageImpl<>(list, PageRequest.of(offset, pageSize), list.size());
	}

	public List<Customer> searchCustomersByFirstName(String keyword) {
		return customerRepository.findByFirstNameContainingIgnoreCase(keyword);
	}

	public List<Customer> searchCustomersByEmail(String keyword) {
		return customerRepository.findByEmailContainingIgnoreCase(keyword);
	}

	public List<Customer> searchCustomersByPhoneNumber(String keyword) {
		return customerRepository.findByPhoneContainingIgnoreCase(keyword);
	}

}






