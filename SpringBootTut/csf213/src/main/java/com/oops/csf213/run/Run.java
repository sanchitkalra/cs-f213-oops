package com.oops.csf213.run;

import java.time.LocalDateTime;

// record automatically has getters and setters for all properties and is immutable and has a default all args constructor
// toString and hashCode are also present
public record Run(Integer id,
                  String title,
                  LocalDateTime startedOn,
                  LocalDateTime completedOn,
                  Integer miles,
                  Location location) {}
