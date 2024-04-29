package com.oop.springbootwithdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("demo")
public class MainController {

    @Autowired
    private  UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addUser(@RequestParam("name") String name, @RequestParam("email") String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
        return "Saved";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/id")
    public @ResponseBody User getUserById(@RequestParam("id") int id) {
        try {
            return userRepository.findById(id).get();
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    @DeleteMapping("/delete")
    public @ResponseBody String deleteUserById(@RequestParam("id") int id) {
        try {
            userRepository.deleteById(id);
            return "Deleted user id: " + id;
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }
}
