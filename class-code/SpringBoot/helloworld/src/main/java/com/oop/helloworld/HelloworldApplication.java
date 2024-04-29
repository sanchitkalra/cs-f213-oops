package com.oop.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

import java.util.Date;

@SpringBootApplication
@RestController
public class HelloworldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloworldApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello World";
	}

	@GetMapping("hi")
		public String hi(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name + "! How are you");
	}

	@GetMapping("/add")
	public int add(@RequestParam(value="a", defaultValue = "0") int a, @RequestParam(value = "b", defaultValue = "0") int b) {
		return a + b;
	}

	@GetMapping("/today")
	public String today() {
		//return new LocalDate.now();
		return "<html> <body> <font color='red'> The date and time now is: " + new Date().toString() + "</font> </body> </html>";
	}

//	@GetMapping("/customer")
//	public Customer customer() {
//
//	}
}
