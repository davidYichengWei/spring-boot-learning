package com.rest.springboot.restfulspringboot.user;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	
	private UserDaoService service;
	
	public UserResource(UserDaoService service) {
		this.service = service; // service injected by spring bean
	}
	
	// Get all users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}
	
	// Get one user with id
	@GetMapping("/users/{id}")
	// Use EntityModel to wrap a domain object and add links to it
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		if (user == null) {
			throw new UserNotFoundException("User not found, id: " + id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(user);
		
		// Add link to the retrieveAllUsers method
		// Builder to help build Link instances pointing to a Spring MVC controller
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	// Create a user
	@PostMapping("/users")
	// With @Valid, whenever binding happens, the validations defined in the object get invoked
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) { // User will be the body of the request
		User newUser = service.createUser(user);
		
		// Return the URI of the user with 201 status code
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newUser.getId()).toUri();
		return ResponseEntity.created(location).build(); // Build the response entity
	}
	
	// Delete a user
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) { // User will be the body of the request
		// First check if the user exists
		User user = service.findOne(id);
		
		if (user == null) {
			throw new UserNotFoundException("Cannot delete a user that does not exist, id: " + id);
		}
		
		service.deleteById(id);
	}
}
