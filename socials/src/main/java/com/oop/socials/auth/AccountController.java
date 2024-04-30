package com.oop.socials.auth;

import com.oop.socials.lib.Errors;
import com.oop.socials.user.UserDetails;
import com.oop.socials.user.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final UserRepository userRepository;

    public AccountController(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    Object login(@RequestBody Account account) {
        Optional<Account> retAccount =  accountRepository.findById(account.email);
        if (retAccount.isPresent()) {
            // user exists, now check if password matches
            if (Objects.equals(retAccount.get().password, account.password)) {
                // password is also correct
                return "Login Successful";
            } else {
                // password incorrect
                return Errors.error("Username/Password Incorrect");
            }
        } else {
            return Errors.error("User does not exist");
        }
    }

    @PostMapping("/signup")
    Object signup(@RequestBody SignupRequest signupRequest) {
        Optional<Account> retAccount = accountRepository.findById(signupRequest.email);

        if (retAccount.isEmpty()) {
            // user does not exist, can create new account
            Account newAccount = new Account(signupRequest.email, signupRequest.password);
            UserDetails usr = new UserDetails(signupRequest.name, signupRequest.email);

            userRepository.save(usr);
            accountRepository.save(newAccount);

            return "Account Creation Successful";
        } else {
            // user account already exists, forbidden
            return Errors.error("Forbidden, Account already exists");
        }
    }

}
