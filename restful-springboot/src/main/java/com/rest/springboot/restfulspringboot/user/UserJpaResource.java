package com.rest.springboot.restfulspringboot.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.rest.springboot.restfulspringboot.jpa.PostRepository;
import com.rest.springboot.restfulspringboot.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	
	private UserRepository userRepository;
	private PostRepository postRepository;
	
	public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	// Get all users
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}
	
	// Get one user with id
	@GetMapping("/jpa/users/{id}")
	// Use EntityModel to wrap a domain object and add links to it
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if (user.isEmpty()) {
			throw new UserNotFoundException("User not found, id: " + id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(user.get());
		
		// Add link to the retrieveAllUsers method
		// Builder to help build Link instances pointing to a Spring MVC controller
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	// Create a user
	@PostMapping("/jpa/users")
	// With @Valid, whenever binding happens, the validations defined in the object get invoked
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) { // User will be the body of the request
		User newUser = userRepository.save(user);
		
		// Return the URI of the user with 201 status code
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newUser.getId()).toUri();
		return ResponseEntity.created(location).build(); // Build the response entity
	}
	
	// Delete a user
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) { // User will be the body of the request
		// First check if the user exists
		Optional<User> user = userRepository.findById(id);
		
		if (user.isEmpty()) {
			throw new UserNotFoundException("Cannot delete a user that does not exist, id: " + id);
		}
		
		userRepository.deleteById(id);
	}
	
	// Get posts for a user
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if (user.isEmpty()) {
			throw new UserNotFoundException("User not found, id: " + id);
		}
		
		return user.get().getPosts();
		
	}
	
	// Get all posts
	@GetMapping("/jpa/posts")
	public List<Post> retrieveAllPosts() {
		return postRepository.findAll();
	}
	
	// Get a post by id
	@GetMapping("/jpa/posts/{id}")
	public EntityModel<Post> retrievePost(@PathVariable int id) {
		Optional<Post> post = postRepository.findById(id);
		
		if (post.isEmpty()) {
			throw new UserNotFoundException("Post not found, id: " + id);
		}
		
		EntityModel<Post> entityModel = EntityModel.of(post.get());
		
		// Add link to the retrieveAllUsers method
		// Builder to help build Link instances pointing to a Spring MVC controller
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllPosts());
		entityModel.add(link.withRel("all-posts"));
		
		return entityModel;
	}
}
