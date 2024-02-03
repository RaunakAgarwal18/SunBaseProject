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
								Model model) {
		List<Customer> searchResults = new ArrayList<>();
		int pageSize = 5;
		if ("firstName".equals(criteria)) {
			searchResults = customerService.searchCustomersByFirstName(keyword);
		} else if ("email".equals(criteria)) {
			searchResults = customerService.searchCustomersByEmail(keyword);
		} else if ("phoneNumber".equals(criteria)) {
			searchResults = customerService.searchCustomersByPhoneNumber(keyword);
		}
		int fromIndex = page * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, searchResults.size());
		List<Customer> paginatedList = searchResults.subList(fromIndex, toIndex);

		// Add attributes to the model
		model.addAttribute("customers", paginatedList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", (int) Math.ceil((double) searchResults.size() / pageSize));
		int idx = page*pageSize;
		model.addAttribute("size", idx);
		return "/customers/list-customers"; // Return the name of your Thymeleaf template
	}

	@GetMapping("/saveNewCustomer")
	public String saveNewCustomers(){
		AuthenticateController authenticateController = new AuthenticateController();
		List<CustomerResponse> newCustomers = authenticateController.authenticate();
		for(int i = 0 ; i<newCustomers.size(); i++){
			Customer customer = new Customer(newCustomers.get(i).getFirstName(),
					newCustomers.get(i).getLastName(),
					newCustomers.get(i).getStreet(),
					newCustomers.get(i).getAddress(),
					newCustomers.get(i).getCity(),
					newCustomers.get(i).getState(),
					newCustomers.get(i).getEmail(),
					newCustomers.get(i).getPhone());
			customerService.save(customer);
		}
		return "redirect:/0";
	}
}









