package com.oops.csf213.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

// this class will not work as a controller without relevant annotations
// annotations are basically add functionalities and behaviours to classes/methods
@RestController // saying this class will have REST methods
@RequestMapping("/api/runs") // this saves us from writing the whole "/api/runs" everytime, all routes in this class will run on "/api/routes"
public class RunController {

    private final RunRepository runRepository;

    /*
    * IMP Points
    * 1. We are not creating our own instance of runRepository, it is bad because
    *   a. Spring probably already has an instance that it also init with the PostConstruct annotated method
    *   b. It is not feasible to create new instances for each new request that this controller is going to handle
    * 2. We simply ask the Spring framework for it's instance of runRepository by making an param for it in the constructor
    * 3. This works because RunRepository is marked as a @Repository
    * */
    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    // @RequestMapping // General way to say this method handles requests
    @GetMapping("/hello") // Handle GET methods on "/hello"
    String home() {
        return "Hello Runners!";
    }

    @GetMapping("")
    List<Run> findAll() {
        return runRepository.findAll();
    }

    @GetMapping("/{id}") // id is now a dynamic path parameter, and it is passed to the method
    Run findByID(@PathVariable Integer id) { // this annotation is used to get the variable passed as a path parameter
        Optional<Run> run =  runRepository.findByID(id);
        if (run.isEmpty()) {
            throw new RunNotFoundException();
        }

        return run.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Run run) {
        // @RequestBody tells Spring that the argument to this method will come from the HTTP request body
        // @Valid runs the validation checks written using the Validation annotations in the Run class. They will not be run on every instantiation but only required using the @Valid annotation
        runRepository.create(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Run run, @PathVariable Integer id) {
        runRepository.update(run, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        runRepository.delete(id);
    }

}
