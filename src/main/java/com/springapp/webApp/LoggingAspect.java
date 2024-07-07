package com.springapp.webApp;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.springapp.webApp.EmployeeController.connectToDatabase(..))")
    public void logBeforeConnect() {
        logger.info("Attempting to connect to the DATABASE...");
    }

    @AfterReturning("execution(* com.springapp.webApp.EmployeeController.connectToDatabase(..))")
    public void logAfterConnect() {
        logger.info("Successfully connected to the DATABASE.");
    }

    @AfterThrowing(pointcut = "execution(* com.springapp.webApp.EmployeeController.connectToDatabase(..))", throwing = "error")
    public void logAfterConnectFailure(Throwable error) {
        logger.error("Failed to connect to the DATABASE!!!", error);
    }

    @Before("execution(* com.springapp.webApp.EmployeeController.getEmployeeRows(..))")
    public void logBeforeFetchingData() {
        logger.info("Attempting to fetch EMPLOYEE DATA...");
    }

    @AfterReturning("execution(* com.springapp.webApp.EmployeeController.getEmployeeRows(..))")
    public void logAfterFetchingData() {
        logger.info("Successfully fetched EMPLOYEE DATA.");
    }

    @AfterThrowing(pointcut = "execution(* com.springapp.webApp.EmployeeController.getEmployeeRows(..))", throwing = "error")
    public void logAfterFetchingDataFailure(Throwable error) {
        logger.error("Failed to fetch EMPLOYEE DATA!!!", error);
    }
}
