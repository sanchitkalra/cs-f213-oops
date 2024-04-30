package com.oop.socials.user;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public Object getUserById(@RequestParam("userID") Integer id) {
        Optional<UserDetails> usr =  userRepository.findById(id);
        if (usr.isPresent()) {
            return usr.get();
        } else {
            return "User does not exist";
        }
    }

    @GetMapping("/users")
    public List<UserDetails> getAllUsers() {
        List<UserDetails> users = new ArrayList<UserDetails>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

}
