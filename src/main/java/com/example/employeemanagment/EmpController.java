package com.example.employeemanagment;

import com.example.csvmanagement.ReaderCSV;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    // Return employees with the project id
    @GetMapping("/employees/{id}")
    public List<EmpProjects> findByProjectId(@PathVariable("id") int id){
        Iterable<EmpProjects> empProjects = this.empRepository.findAll();
        List<EmpProjects> empProjectsList = new ArrayList<>();
        for (EmpProjects item : empProjects){
            if (item.getProjectID() == id){
                empProjectsList.add(item);
            }
        }
        return empProjectsList;
    }

    // Delete employee by id
    @DeleteMapping("/deleteemployee/{id}")
    public void deleteEmployeeById(@PathVariable("id") int id){
        Iterable<EmpProjects> empProjects = this.empRepository.findAll();
        for (EmpProjects item : empProjects){
            if (item.getEmpID() == id){
                this.empRepository.delete(item);
            }
            break;
        }
    }

    // Add record
    @PostMapping("/project")
    public EmpProjects addEmployee(@RequestBody EmpProjects empProjects){
        return this.empRepository.save(empProjects);
    }

    // Update employee by id
    @PostMapping("/updateemployee")
    public EmpProjects updateEmployeeById(@RequestBody EmpProjects newEmpProjects){
        Iterable<EmpProjects> empProjects = this.empRepository.findAll();
        for(EmpProjects item: empProjects){
            if(newEmpProjects.getEmpID() == item.getEmpID() && newEmpProjects.getProjectID() == item.getProjectID())
            {
                item.setDateFrom(newEmpProjects.getDateFrom());
                item.setDateTo(newEmpProjects.getDateTo());
                return this.empRepository.save(item);
            }
        }
        return null;
    }

}
