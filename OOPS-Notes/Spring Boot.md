# Spring Boot

- Spring Web dependency for creating API endpoints
- entry point to the application defined with the annotation `@SpringBootApplication`
- domain -> name of a machine on the internet
- localhost -> name of the your own machine
- `@RestController` -> tells Spring this class will handle HTTP requests
- `@GetMapping` -> this method will handle GET HTTP requests
  - return type of this method is the kind of object to be returned as the HTTP response
- default Spring Boot server -> Apache Tomcat
- Spring Boot by default runs on 8080 (can be modified tho)

```java
// example
@SpringBootApplication // this class is the entry point for a spring boot application
@RestController // this class will handle REST HTTP requests
class OOPSApplication {
    psvm(String args[]) {
        SpringApplication.run(HelloworldApplication.class, args);
    }

    @GetMapping("/hello") // this implies that the function sayHello will respond to GET HTTP requests on the endpoint /hello
    public String sayHello() {
        return "Hello World!"; // this return's value will be returned as response to the HTTP request
    }

    @GetMapping("hi")
    // this function will receive the value of the name parameter as in the variable name and if it is not specified, the default value will be passed
    // listens for /hi?name="something"
	public String hi(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name + "! How are you");
	}

    @GetMapping("/add")
    // get multiple request params
    // default value is string, type conversions happen automatically
	public int add(@RequestParam(value="a", defaultValue = "0") int a, @RequestParam(value = "b", defaultValue = "0") int b) {
		return a + b;
	}
}
```

- applications make GET, PUT, POST etc to the API layer
- DAO (Data Access Obj) -> interface that standarises how data is accessed using a database
- all other components should access the database using the data access object

```java
// DAO for a person class that has two fields UUID and Name

public interface PersonDAO {
    int insertPerson(UUID id, Person person);

    default int insertPerson(Person person) {
        return int insertPerson(UUID.randomUUID(), person);
    }

    List<Person> listAll();
}

// and impl this interface as

// the following annotation tells Spring that this class is a data repository and it's identifier should be PersonDAO (used to differentiate between different beans), which is the name other methods in service layer can use to refer to it
@Repository("PersonDAO")
public class PersonDAOImpl implements PersonDAO {
    // impl abstract methods here using some database
}
```

- once we have implemented the DAO, we are done with the data access layer. We now need to implement the Service layer where the business logic is present

```java
// service for the Person

@Service // this annotation is to tell Spring that this class is a service and provides some business logic
public class PersonService implements PersonDAO {
    private static List<Person> DB = new ArrayList<Person>();

    // this annotation automatically wires the needed beans (aka dependencies) into the classes as required -> no need for manual config
    @AutoWired
    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    // this qualifier annotation tells Spring to access the particular DAO with the "PersonDAO" identifier. this is required because there may be multiple impl of insertPerson and which one is this particular impl pointing to
    public int insertPerson(@Qualifier("PersonDAO") UUID id, Person person) {
        DB.add(id, person.getName());
        return 1;
    }
}
```

- after this we need to impl the API layer (or controller layer)

```java
// following annotation tells Spring to map requests for the given path to the following REST controller
@RequestMapping("/api/v1/person")
// following annotation tells Spring that the following class handle HTTP requests made to our server
@RestController
public class PersonController {
    private final PersonService personService;

    @AutoWired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // we are not specifying the path here explicitly because it has been specified in the RequestMapping above and this PostMapping now implicitly handles "POST /api/v1/person"
    @PostMapping
    // @RequestBody is used to serialise the request body into a Person object
    public void addPerson(@RequestBody Person person) {
        personService.insertPerson(person)
    }

    @GetMapping
    public List<Person> getPerson() {
       return personService.listAll();
    }
}
```

- we can annotate constructor parameters to match those in our JSON using the `@JsonProperty` annotation

```java
class Person {
    int id;
    String name;

    Person(@JsonProperty("id") int id, @JsonProperty("name") String name) {
        // here whenever deserialisation is taking place, the id and name params in the JSON will be mapped to the id and name vars resp
    }
}
```

## Stream API

- perform ops like search, filter, and map
- we can construct series of actions that resemble a SQL query
- actions can also be performed in parallel for a high level of efficiency
- Stream represents a sequence of objects
- it is _not_ a datastore itself, but _acts_ on other data stores
- methods in a stream can be either terminal or intermediate (like filtering(stateless) or sorting(stateful))
- stateful means that for one specific task we need information of previous tasks, ie, _the state_

```java
// filter example

import java.util.*;

class Person {
    int id;
    String name;

    Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Main {
    static List<Person> DB = new ArrayList<Person>();

    static Optional<Person> fn(int id) {
        return DB
                .stream() // convert the list into a stream
                .filter(p -> p.id == id) // use lambda to pass a fn to choose which elements are there in the output array
                .findFirst(); // return the first found element as an Optional
    }

    public static void main(String[] args) {
        DB.add(new Person(1, "Rigo"));
        DB.add(new Person(2, "SK"));

        if (fn(1).isPresent()) {
            System.out.println(fn(1).get().name);
        }
    }
}
```

## Optionals

- without this we used to use `null` to refer to the lack of a value but this could quickly lead to null pointer exceptions if accidentally we tried to de-reference null values
- frequent nulls checks were needed
- isPresent -> tells if a value is present
- isEmpty -> tells is value is _not_ present
- `orElse` returns the value if it is present, or else, the default value
