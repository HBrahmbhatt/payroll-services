package com.payroll.payrollservices.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payroll.payrollservices.dto.EmployeeDTO;
import com.payroll.payrollservices.services.PayrollServicesImpl;
import com.payroll.payrollservices.util.PayrollServicesConstansts;
import com.payroll.payrollservices.util.ResponseStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class PayrollController {

	private static Logger logger = LoggerFactory.getLogger(PayrollController.class);

	@Autowired
	private PayrollServicesImpl payrollServices;

	@GetMapping("/test")
	public String sayHello() {
		logger.info("Inside PayrollController's sayHello method...");
		return "Hello, World!";
	}

	@GetMapping("/all-employees")
	public ResponseEntity<ResponseStatus> findAllEmployees() {
		try {
			ResponseStatus response = payrollServices.getAllEmployees();

			if (PayrollServicesConstansts.NO_RESULTS_CODE.equals(response.getStatusCode())) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error in findAllEmployees: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE,
							PayrollServicesConstansts.FAILURE_STATUS, "Internal Server Error", null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStatus> getEmployeeById(@PathVariable Long id) {
		try {
			ResponseStatus response = payrollServices.getEmployeeById(id);

			if (PayrollServicesConstansts.NO_RESULTS_CODE.equals(response.getStatusCode())) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error in getEmployeeById: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE,
							PayrollServicesConstansts.FAILURE_STATUS, "Internal Server Error", List.of()));
		}
	}

	@PostMapping("/add-employee")
	public ResponseEntity<ResponseStatus> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		try {
			ResponseStatus response = payrollServices.addEmployee(employeeDTO);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE,
					PayrollServicesConstansts.FAILURE_STATUS, "Error adding employee", List.of()));
		}
	}
	
	
	@PutMapping("/update-employee")
	public ResponseEntity<ResponseStatus> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		try {
			ResponseStatus response = payrollServices.updateEmployee(employeeDTO);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE,
					PayrollServicesConstansts.FAILURE_STATUS, "Error adding employee", List.of()));
		}
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStatus> addEmployee(@PathVariable Long id) {
		try {
			ResponseStatus response = payrollServices.deleteEmployeebyId(id);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE,
					PayrollServicesConstansts.FAILURE_STATUS, "Error adding employee", List.of()));
		}
	}
}