package com.rest.springboot.restfulspringboot.user;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "user_details") // Need to rename because user is a key word in h2
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	// Validations
	@Size(min = 2, message = "Name should have at least 2 characters")
	// @JsonProperty("user_name")
	private String name;
	
	@Past(message = "Birth data should be in the past")
	// @JsonProperty("birth_date")
	private LocalDate birthDate;
	
	// Default constructor required for JPA
	public User() {
		
	}
	
	public User(Integer id, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
	
	
	
}
