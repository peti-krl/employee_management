package com.example.employeemanagment;

import com.example.csvmanagement.ReaderCSV;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class EmpController {
    private final EmpRepository empRepository;
    ReaderCSV readerCSV = new ReaderCSV();

    public EmpController(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    // Reading from csv file provided as a request parameter and writing it to the database
    @PostMapping("/csv")
    public Iterable<EmpProjects> csvRead(@RequestParam("file") MultipartFile file) throws IOException {
        EmpProjects[] projects = readerCSV.read(file.getInputStream());
        List<EmpProjects> empProjectsList = Arrays.asList(projects);
        return this.empRepository.saveAll(empProjectsList);
    }

    // Read all records
    @GetMapping("/employees")
    public Iterable<EmpProjects> findAllEmp(){
        return this.empRepository.findAll();
    }

    // Add record
    @PostMapping("/project")
    public EmpProjects addEmployee(@RequestBody EmpProjects empProjects){
        return this.empRepository.save(empProjects);
    }
}
