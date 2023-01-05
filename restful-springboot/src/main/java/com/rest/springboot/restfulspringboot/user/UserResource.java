package com.rest.springboot.restfulspringboot.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public User retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		if (user == null) {
			throw new UserNotFoundException("User not found, id: " + id);
		}
		
		return user;
	}
	
	// Create a user
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) { // User will be the body of the request
		User newUser = service.createUser(user);
		
		// Return the URI of the user with 201 status code
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newUser.getId()).toUri();
		return ResponseEntity.created(location).build(); // Build the response entity
	}
}
