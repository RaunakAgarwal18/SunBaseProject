package com.luv2code.springboot.thymeleafdemo.controller;
import com.luv2code.springboot.thymeleafdemo.entity.Customer;
import com.luv2code.springboot.thymeleafdemo.entity.CustomerResponse;
import com.luv2code.springboot.thymeleafdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	// Constructor injection for Employee Service
	public CustomerController(CustomerService theeCustomerService){
		customerService = theeCustomerService;
	}

	public HashMap<String,Integer> map = new HashMap<>();
	@GetMapping("/")
	public String test(){
		return "redirect:/0";
	}
	// add mapping for "/list"

	@GetMapping("/{page}")
	public String listCustomers(@PathVariable("page") Integer page, Model theModel) {
		int size = 5;
		Page<Customer> customers = customerService.findCustomersWithPagination(page,size);
		theModel.addAttribute("customers", customers);
		theModel.addAttribute("currentPage",page);
		theModel.addAttribute("totalPages",customers.getTotalPages());
		int idx = page*size;
		theModel.addAttribute("size", idx);
		return "customers/list-customers";   // path of the html page
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel){
		Customer theCustomer = new Customer();
		theModel.addAttribute("customer", theCustomer);
		return "customers/customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId){
		customerService.deleteById(theId);
		return "redirect:/0";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel){
		Customer theCustomer = customerService.findById(theId);
		theModel.addAttribute("customer", theCustomer);
		return "customers/customer-form";
	}
	@PostMapping("/save")
	// This employee comes from the form
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer){
		customerService.save(theCustomer);
		return "redirect:/0";
	}

	@GetMapping("/search")
	public String searchCustomers(@RequestParam("criteria") String criteria,
								@RequestParam("keyword") String keyword,
								@RequestParam(defaultValue = "0") int page,
								Model theModel) {
		if(keyword.length()==0){
			// path of the html page
			return "redirect:/0";
		}
		Page<Customer> searchResults = null;
		if ("firstName".equals(criteria)) {
			searchResults = customerService.findCustomersWithPaginationByFirstName(keyword,0,100);
		} else if ("email".equals(criteria)) {
			searchResults = customerService.findCustomersWithPaginationByEmail(keyword,0,100);
		} else if ("phoneNumber".equals(criteria)) {
			searchResults = customerService.findCustomersWithPaginationByPhone(keyword,0,100);
		}
		theModel.addAttribute("customers", searchResults);
		theModel.addAttribute("currentPage",page);
		theModel.addAttribute("totalPages",searchResults.getTotalPages());
		int idx = page*5;
		theModel.addAttribute("size", idx);
		return "customers/list-customers";   // path of the html page
	}

	@GetMapping("/saveNewCustomer")
	public String saveNewCustomers(){
		AuthenticateController authenticateController = new AuthenticateController();
		List<CustomerResponse> newCustomers = authenticateController.authenticate();
		for(int i = 0 ; i<newCustomers.size(); i++){
			Customer customer = null;
			if(!map.containsKey(newCustomers.get(i).getUuid())) {
				customer = new Customer(newCustomers.get(i).getFirstName(),
						newCustomers.get(i).getLastName(),
						newCustomers.get(i).getStreet(),
						newCustomers.get(i).getAddress(),
						newCustomers.get(i).getCity(),
						newCustomers.get(i).getState(),
						newCustomers.get(i).getEmail(),
						newCustomers.get(i).getPhone());
				customerService.save(customer);
				map.put(newCustomers.get(i).getUuid(),customer.getId());
			}else {
				customer = customerService.findById(map.get(newCustomers.get(i).getUuid()));
				customer.setFirstName(newCustomers.get(i).getFirstName());
				customer.setLastName(newCustomers.get(i).getLastName());
				customer.setStreet(newCustomers.get(i).getStreet());
				customer.setAddress(newCustomers.get(i).getAddress());
				customer.setCity(newCustomers.get(i).getCity());
				customer.setState(newCustomers.get(i).getState());
				customer.setEmail(newCustomers.get(i).getEmail());
				customer.setPhone(newCustomers.get(i).getPhone());
				customerService.save(customer);
			}
		}
		return "redirect:/0";
	}
}









