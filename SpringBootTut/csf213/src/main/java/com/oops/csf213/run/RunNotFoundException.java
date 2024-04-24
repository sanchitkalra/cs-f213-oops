package com.oops.csf213.run;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // we're saying that when this exception is to be returned, set the status code to 404
public class RunNotFoundException extends RuntimeException{
    public RunNotFoundException() {
        super("Run not found!");
    }
}
