package com.oops.csf213.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

// record automatically has getters and setters for all properties and is immutable and has a default all args constructor
// toString and hashCode are also present
public record Run(Integer id,
                  @NotEmpty // validation constraints
                  String title,
                  LocalDateTime startedOn,
                  LocalDateTime completedOn,
                  @Positive // validation constraints
                  Integer miles,
                  Location location) {

    public Run {
        // Run on each instantiation of Run
        // validation without annotations
        // this can also be written as a custom constraint
        // and vice-versa, all validations done above can be done here too!
        if (!completedOn.isAfter(startedOn)) {
            throw new IllegalArgumentException("Completed On must be after Started On");
        }
    }

}
