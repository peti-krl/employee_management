package com.example.employeemanagment;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;
import java.util.Date;


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

    public EmpProjects(int empID, int projectID, LocalDate dateFrom, LocalDate dateTo){
        this.empID = empID;
        this.projectID = projectID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
