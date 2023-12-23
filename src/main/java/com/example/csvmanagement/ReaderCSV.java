package com.example.csvmanagement;
import com.example.employeemanagment.*;
import com.example.employeemanagment.EmpProjects;
import com.opencsv.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ReaderCSV implements IReader{
    @Override
    public EmpProjects[] read(InputStream filename) {
       try{
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(filename, "UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            List<EmpProjects> projects = new ArrayList<EmpProjects>();

            for (CSVRecord csvRecord : csvRecords) {
                int empId = Integer.parseInt(csvRecord.get(0));
                int projectId = Integer.parseInt(csvRecord.get(1));



                EmpProjects project = new EmpProjects(empId, projectId, csvRecord.get(2), csvRecord.get(3));
                projects.add(project);
            }

            EmpProjects[] arr = new EmpProjects[projects.size()];
            projects.toArray(arr);
            return arr;

        } catch (Exception e) {
           e.printStackTrace();
            return new EmpProjects[0];
        }
    }
}
