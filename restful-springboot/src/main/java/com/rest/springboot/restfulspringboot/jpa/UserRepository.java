package com.rest.springboot.restfulspringboot.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.springboot.restfulspringboot.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
