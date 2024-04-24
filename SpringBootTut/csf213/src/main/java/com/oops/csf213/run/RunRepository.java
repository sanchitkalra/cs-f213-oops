package com.oops.csf213.run;

import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// this will hold the data for our runs in memory
// we are basically following the repository pattern for accessing data
@org.springframework.stereotype.Repository // now this class is in the Spring context and is managed by the Spring framework
public class RunRepository {
    private List<Run> runs = new ArrayList<Run>();

    // we can use some annotations in the lifecycle of construction of a class
    @PostConstruct // used on a method that needs to be exec after dependency injection is done for init purposes
    private void init() {
        runs.add(new Run(1, "", LocalDateTime.now(), LocalDateTime.now(), 10, Location.INDOOR));
    }

    List<Run> findAll() {
        return runs;
    }

    // we're making this an optional
    // optionals may contain a non-null value. check for value presence with isPresent()
    Optional<Run> findByID(int Id) {
        return runs
                .stream()
                .filter(run -> run.id() == Id)
                .findFirst();
    }

    void create(Run run) {
        runs.add(run);
    }

    void update(Run run, Integer id) {
        Optional<Run> existingRun = findByID(id);
        existingRun.ifPresent(value -> runs.set(runs.indexOf((value)), run));
    }

    void delete(Integer id) {
        runs.removeIf(run -> run.id().equals(id));
    }
}
