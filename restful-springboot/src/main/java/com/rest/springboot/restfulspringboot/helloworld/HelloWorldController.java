package com.rest.springboot.restfulspringboot.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

// DispatcherServlet at path / gets auto-configured by Auto Configuration,
// and redirects the request to the controller with the request mapping path

@RestController
// RestController has the annotation @ResponseBody, which returns the 
// return values as response body
public class HelloWorldController {

	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	// When a bean is returned, the default message conversion JacksonHttpMessageConverters
	// configured by Auto Configuration converts it to JSON format
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	// Use PathVariable to customize the API endpoint path
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
}
