package com.payroll.payrollservices.util;

import java.util.List;

import com.payroll.payrollservices.dto.EmployeeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStatus {
	String statusCode;
	String status;
	String statusDescription;
	List<EmployeeDTO> employeeDto; // This can be empty depending on the type of object returned
}