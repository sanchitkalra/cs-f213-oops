package com.oop.socials.post;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostDetails, Integer> {
}
