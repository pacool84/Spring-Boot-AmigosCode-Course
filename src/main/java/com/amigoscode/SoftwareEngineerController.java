package com.amigoscode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // Indicates that this class is a REST controller
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    @GetMapping // Maps HTTP GET requests to this method
    public List<SoftwareEngineer> getEngineers() {
        return List.of(
            new SoftwareEngineer(1, "Alice", "Java"),
            new SoftwareEngineer(2, "Bob", "Python"),
            new SoftwareEngineer(3, "Charlie", "JavaScript")
        );
    }
}
