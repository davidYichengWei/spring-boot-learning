package com.rest.springboot.restfulspringboot.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.springboot.restfulspringboot.user.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
