package com.amigoscode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // Indicates that this class is a REST controller
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    //Instance of the software engineer service
    private final SoftwareEngineerService softwareEngineerService;

    //Constructor of the software engineer controller
    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    @GetMapping // Maps HTTP GET requests to this method
    public List<SoftwareEngineer> getEngineers() {
        return softwareEngineerService.getAllSoftwareEngineers();
    }
}
