package com.payroll.payrollservices;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.payroll.payrollservices.dao.PayrollRepository;
import com.payroll.payrollservices.entity.Employee;

@SpringBootApplication
public class PayrollServicesApplication {
	
	@Autowired
	PayrollRepository payrollRepository;

	public static void main(String[] args) {
		SpringApplication.run(PayrollServicesApplication.class, args);
	}
	
	@Bean
    CommandLineRunner initDatabase(PayrollRepository repository) {
        return args -> {
            Employee emp1 = new Employee(null, "John", "Doe", LocalDate.of(1990, 5, 10), "Male", "john.doe@example.com", "123-456-7890", "123 Elm St", "Developer", LocalDate.of(2015, 3, 15));
            Employee emp2 = new Employee(null, "Jane", "Smith", LocalDate.of(1985, 8, 20), "Female", "jane.smith@example.com", "987-654-3210", "456 Oak St", "Manager", LocalDate.of(2010, 7, 22));
            Employee emp3 = new Employee(null, "Alice", "Johnson", LocalDate.of(1992, 11, 25), "Female", "alice.johnson@example.com", "555-555-5555", "789 Pine St", "HR", LocalDate.of(2018, 5, 10));
            
			payrollRepository.saveAll(Arrays.asList(emp1, emp2, emp3));
        };
    }
}
