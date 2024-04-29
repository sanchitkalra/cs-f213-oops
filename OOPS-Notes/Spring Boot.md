# Spring Boot

- entry point to the application defined with the annotation `@SpringBootApplication`
- domain -> name of a machine on the internet
- localhost -> name of the your own machine
- `@RestController` -> tells Spring this class will handle HTTP requests
- `@GetMapping` -> this method will handle GET HTTP requests
  - return type of this method is the kind of object to be returned as the HTTP response
- Spring Boot by default runs on 8080 (can be modified tho)

```java
// example
@SpringBootApplication // this class is the entry point for a spring boot application
@RestController // this class will handle REST HTTP requests
class OOPSApplication {
    psvm(String args[]) {
        ... // auto-gen code
    }

    @GetMapping("/hello") // this implies that the function sayHello will respond to GET HTTP requests on the endpoint /hello
    public String sayHello() {
        return "Hello World!";
    }

    @PostMapping()
}
```

-
