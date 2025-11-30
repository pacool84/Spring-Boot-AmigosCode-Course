package com.amigoscode;

import org.springframework.web.bind.annotation.*;

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

    // Get softwareEngineer by id
    @GetMapping("{id}")
    public SoftwareEngineer getEngineerById(@PathVariable Integer id) {
        return softwareEngineerService.getSoftwareEngineerById(id);
    }

    // Add new software engineer
    @PostMapping
    public void addNewSoftwareEngineer(@RequestBody SoftwareEngineer softwareEngineer){
        softwareEngineerService.insertSoftwareEngineer(softwareEngineer);
    }
}
