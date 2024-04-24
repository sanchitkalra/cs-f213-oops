package com.oops.csf213;

import org.springframework.stereotype.Component;

@Component // => this class is available to string
public class WelcomeMessage {
    public String getWelcomeMessage() {
        return "Hello World!";
    }
}
