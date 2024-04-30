package com.oop.socials.post;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<PostDetails, Integer> {
    List<PostDetails> findByOrderByPostIDDesc();
}
