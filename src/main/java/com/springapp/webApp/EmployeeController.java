package com.springapp.webApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/*
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public String viewEmployees() {
        return "employees"; // returns the main page with the button and empty table
    }

    @GetMapping("/employees/rows")
    @ResponseBody
    public List<Employee> getEmployeeRows() {
        return employeeService.getAllEmployees();
    }
}*/
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @GetMapping("/employees")
    public String viewEmployees() {
        return "employees"; // returns the main page with the button and empty table
    }

    @GetMapping("/employees/table")
    @ResponseBody
    public List<Employee> getEmployeeRows() {
        try {
            return employeeService.getAllEmployees();
        } finally {
            employeeService.clearDataSource();
        }
    }

    @PostMapping("/connect")
    @ResponseBody
    public String connectToDatabase(@RequestParam("db") String db) {
        employeeService.switchToDataSource(db);
        return "Connected to " + db;
    }
}