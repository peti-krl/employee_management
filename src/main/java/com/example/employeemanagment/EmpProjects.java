package com.example.employeemanagment;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "employees")
public class EmpProjects {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;
    private int empID;
    private int projectID;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public EmpProjects() {}

    public EmpProjects(int empID, int projectID, String dateFrom, String dateTo){
        this.empID = empID;
        this.projectID = projectID;
        this.setDateFrom(dateFrom);
        this.setDateTo(dateTo);
    }
    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        DateTimeFormatter dfs = new DateTimeFormatterBuilder().appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy")).appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd")).appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy")).appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        this.dateFrom = LocalDate.parse(dateFrom,dfs);
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        DateTimeFormatter dfs = new DateTimeFormatterBuilder().appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy")).appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd")).appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy")).appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        if(Objects.equals(dateTo, "NULL")){
            this.dateTo = null;
        }else{
            this.dateTo = LocalDate.parse(dateTo,dfs);
        }
    }
}
