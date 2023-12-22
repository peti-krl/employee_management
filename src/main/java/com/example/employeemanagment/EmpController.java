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

    @PostMapping("/csv")
    public Iterable<EmpProjects> csvRead(@RequestParam("file") MultipartFile file) throws IOException {
        EmpProjects[] projects = readerCSV.read(file.getInputStream());
        List<EmpProjects> empProjectsList = Arrays.asList(projects);
        return this.empRepository.saveAll(empProjectsList);
    }

    @GetMapping("/employees")
    public Iterable<EmpProjects> findAllEmp(){
        return this.empRepository.findAll();
    }

    @PostMapping("/project")
    public EmpProjects addEmployee(@RequestBody EmpProjects empProjects){
        return this.empRepository.save(empProjects);
    }
}
