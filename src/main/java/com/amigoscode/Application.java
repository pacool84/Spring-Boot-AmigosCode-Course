package com.amigoscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController /*Para indicar que esta clase se debe de comportar como un controlador */
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @GetMapping // Rest Endpoint, Es el end point que se puede consultar con un metodo get en la ruta /
   public String helloWorld() {
       java.time.ZonedDateTime now = java.time.ZonedDateTime.now(java.time.ZoneId.systemDefault());
       java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
       return String.format("Hello — My Friend Spring Boot service. Server time: %s", now.format(fmt));
   }

}
