package com.example.employeemanagment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmpRepository extends CrudRepository<EmpProjects, Integer> {
}
