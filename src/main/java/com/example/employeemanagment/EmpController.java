package com.example.employeemanagment;

import com.example.csvmanagement.ReaderCSV;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

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

    // Update employee
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

    // Identifies the pair of employees who
    // have worked together on common projects for the longest period
    // of time and the time for each of those projects
    @GetMapping("/besties")
    public String getCommonProjects(){
        Iterable<EmpProjects> empProjects = this.empRepository.findAllOrderedByEmpId();
        HashMap<String, Long> result = new HashMap<String, Long>();

        for(EmpProjects project1 : empProjects){
            for(EmpProjects project2 : empProjects){
                if(project2.getEmpID() > project1.getEmpID() && project2.getProjectID() == project1.getProjectID()){
                    if(project2.getDateTo() == null){
                        project2.setDateTo(LocalDate.now());
                    }
                    if(project1.getDateTo() == null){
                        project1.setDateTo(LocalDate.now());
                    }
                    LocalDate overlapStart = project1.getDateFrom().compareTo(project2.getDateFrom()) > 0 ?
                            project1.getDateFrom() : project2.getDateFrom();

                    LocalDate overlapEnd = project1.getDateTo().compareTo(project2.getDateTo()) < 0 ?
                            project1.getDateTo() : project2.getDateTo();

                    String key = String.valueOf(project1.getEmpID()) + " " + String.valueOf(project2.getEmpID());
                    if (overlapStart.compareTo(overlapEnd) < 0){
                        long days = Duration.between(overlapStart.atStartOfDay(), overlapEnd.atStartOfDay()).toDays();
                        if(!result.containsKey(key)){
                            result.put(key, (long)0);
                        }
                        result.put(key, result.get(key) + days);
                    }
                }
            }
        }
        String pair = "";
        long maxDays = 0;
        for (HashMap.Entry<String, Long> entry : result.entrySet()){
            if(entry.getValue() > maxDays){
                maxDays = entry.getValue();
                pair = entry.getKey();
            }
        }

        String[] arr = pair.split("\\s+");
        int firstId = Integer.parseInt(arr[0]);
        int secondId = Integer.parseInt(arr[1]);

        pair += " " + String.valueOf(maxDays) + "\n";

        for(EmpProjects project1 : empProjects) {
            for (EmpProjects project2 : empProjects) {
                if (project1.getEmpID() == firstId && project2.getEmpID() == secondId && project1.getProjectID() == project2.getProjectID()) {
                    LocalDate overlapStart = project1.getDateFrom().compareTo(project2.getDateFrom()) > 0 ?
                            project1.getDateFrom() : project2.getDateFrom();

                    LocalDate overlapEnd = project1.getDateTo().compareTo(project2.getDateTo()) < 0 ?
                            project1.getDateTo() : project2.getDateTo();

                    String key = String.valueOf(project1.getEmpID()) + " " + String.valueOf(project2.getEmpID());
                    if (overlapStart.compareTo(overlapEnd) < 0) {
                        long days = Duration.between(overlapStart.atStartOfDay(), overlapEnd.atStartOfDay()).toDays();
                        pair += String.valueOf(project1.getProjectID()) + " " + String.valueOf(days) + "\n";
                    }
                }
            }
        }
        return pair;
    }
}
