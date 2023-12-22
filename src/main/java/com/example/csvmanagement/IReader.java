package com.example.csvmanagement;

import com.example.employeemanagment.EmpProjects;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface IReader {
    EmpProjects[] read(InputStream filename);
}
