package com.example.employeemanagment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmpRepository extends CrudRepository<EmpProjects, Integer> {
    @Query(
            value = "select id, empid, projectid, date_from, date_to from employees order by empid", nativeQuery = true)
    public Iterable<EmpProjects> findAllOrderedByEmpId();
}
