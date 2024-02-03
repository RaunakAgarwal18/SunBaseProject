package com.luv2code.springboot.thymeleafdemo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Entity tells that this class is connected to a table in the database
@Entity

//Connect this class to the table named "employees"
@Table(name="customer")
public class Customer {

	// define fields
	
	@Id  // Represent that this field is the primary key of the table
	@GeneratedValue(strategy=GenerationType.IDENTITY) // Generated an Id which is increment by 1 
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	@JsonProperty("first_name")
	private String firstName;
	
	@Column(name="last_name")
	@JsonProperty("last_name")
	private String lastName;

	@Column(name="street")
	@JsonProperty("street")
	private String street;

	@Column(name="address")
	@JsonProperty("street")
	private String address;

	@Column(name="city")
	@JsonProperty("city")
	private String city;

	@Column(name="state")
	@JsonProperty("state")
	private String state;

	@Column(name="email")
	@JsonProperty("email")
	private String email;

	@Column(name="phone")
	@JsonProperty("phone")
	private String phone;
		
	// define constructors
	
	public Customer() {
		
	}
	
	// This constructor is used when Id is provided


	public Customer(int id, String firstName, String lastName, String street, String address, String city, String state, String email, String phone) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.address = address;
		this.city = city;
		this.state = state;
		this.email = email;
		this.phone = phone;
	}

	// This constructor is used when Id is not provided and the Id is generated

	public Customer(String firstName, String lastName, String street, String address, String city, String state, String email, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.address = address;
		this.city = city;
		this.state = state;
		this.email = email;
		this.phone = phone;
	}


	// define getter/setter


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", street='" + street + '\'' +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}
}











