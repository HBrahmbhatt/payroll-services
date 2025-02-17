package com.payroll.payrollservices.services;

import com.payroll.payrollservices.dto.EmployeeDTO;
import com.payroll.payrollservices.util.ResponseStatus;

import jakarta.validation.Valid;

public interface PayrollServices {

//	Writing the services in a way that always a status object will be returned.

	ResponseStatus getAllEmployees();

	ResponseStatus getEmployeeById(Long id);

	ResponseStatus addEmployee(@Valid EmployeeDTO employeeDTO);

	ResponseStatus updateEmployee(@Valid EmployeeDTO employeeDTO);

	ResponseStatus deleteEmployeebyId(Long id);
}
