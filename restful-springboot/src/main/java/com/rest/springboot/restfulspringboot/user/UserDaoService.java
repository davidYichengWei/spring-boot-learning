package com.rest.springboot.restfulspringboot.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

// DAO: Data Access Object, provide access to data
@Component
// @Component is an annotation that allows Spring to automatically detect our custom beans.
//In other words, without having to write any explicit code, Spring will:
	// Scan our application for classes annotated with @Component
	// Instantiate them and inject any specified dependencies into them
	// Inject them wherever needed
public class UserDaoService {
	
	// Later, we will use JPA/Hibernate to connect to the Database
	// For now, we will just use a static list for data
	private static int usersCount = 0;
	
	private static List<User> users = new ArrayList<>();
	static {
		users.add(new User(++usersCount, "Adam", LocalDate.now().minusYears(30)));
		users.add(new User(++usersCount, "Eve", LocalDate.now().minusYears(25)));
		users.add(new User(++usersCount, "Jim", LocalDate.now().minusYears(20)));
	}
	
	
	// Retrieve all users
	public List<User> findAll() {
		return users;
	}
	
	// Retrieve one user
	public User findOne(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().get();
	}
	
	// Create one user
	public User createUser(User user) {
		user.setId(++usersCount);
		users.add(user);
		
		return user;
	}
}
