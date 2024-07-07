package com.springapp.webApp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void switchToDataSource(String dataSource) {
        DataSourceContextHolder.setDataSource(dataSource);
    }

    public void clearDataSource(){
        DataSourceContextHolder.clearDataSource();
    }

}
