package com.example.employeemanagment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpController {
    private final EmpRepository empRepository;

    public EmpController(EmpRepository empRepository) {
        this.empRepository = empRepository;
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
