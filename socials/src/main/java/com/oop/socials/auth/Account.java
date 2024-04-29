package com.oop.socials.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    String email;
    String password;

    public Account() {}

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
