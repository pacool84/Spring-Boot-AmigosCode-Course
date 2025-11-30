package com.amigoscode;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerService {
    private final SoftwareEngineerRepository softwareEngineerRepository; //Instance

    //Constructor
    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    public  SoftwareEngineer getSoftwareEngineerById(Integer id) {
        return softwareEngineerRepository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException("Software Engineer with id " + id + " does not exist."));
    }

    //Methods
    public List<SoftwareEngineer> getAllSoftwareEngineers() {
        return softwareEngineerRepository.findAll();
    }

    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer) {
        //TODO : Add validations
        softwareEngineerRepository.save(softwareEngineer);

    }
}
