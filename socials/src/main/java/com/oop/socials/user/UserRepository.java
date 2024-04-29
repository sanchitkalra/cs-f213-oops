package com.oop.socials.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDetails, Integer> {
}
