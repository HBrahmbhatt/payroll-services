package com.payroll.payrollservices.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payroll.payrollservices.dao.PayrollRepository;
import com.payroll.payrollservices.dto.EmployeeDTO;
import com.payroll.payrollservices.entity.Employee;
import com.payroll.payrollservices.util.PayrollServicesConstansts;
import com.payroll.payrollservices.util.ResponseStatus;

import jakarta.validation.Valid;

//Put Service annotation on implementation and not in interface as spring's component scan won't pick it up

@Service
public class PayrollServicesImpl implements PayrollServices {

	private static Logger logger = LoggerFactory.getLogger(PayrollServicesImpl.class);

	@Autowired
	PayrollRepository payrollRepository;

	@Override
	public ResponseStatus getAllEmployees() {
		logger.info("Entered getAllEmployees method in PayrollServicesImpl");

		try {
			List<Employee> allEmployees = payrollRepository.findAll();

			if (allEmployees.isEmpty()) {
				logger.info("No employees found.");

				return new ResponseStatus(PayrollServicesConstansts.NO_RESULTS_CODE,
						PayrollServicesConstansts.NO_RESULTS_STATUS, "No employees found", null);
			}
			logger.info("Successfully retrieved {} employees", allEmployees.size());
			return new ResponseStatus(PayrollServicesConstansts.SUCCESS_CODE, PayrollServicesConstansts.SUCCESS_STATUS,
					"Employees retrieved successfully", EmployeeDTO.convertToEmployeeDTOList(allEmployees));

		} catch (Exception e) {
			logger.error("Error retrieving employees: {}", e.getMessage(), e);
			return new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE, PayrollServicesConstansts.FAILURE_STATUS,
					"Technical Failure occured", null);
		}
	}

	@Override
	public ResponseStatus getEmployeeById(Long id) {
		logger.info("Fetching employee with ID: {}", id);
		try {

			Optional<Employee> employeeOpt = payrollRepository.findById(id);
			if (employeeOpt.isPresent()) {
				Employee employee = employeeOpt.get(); // Get the employee if found
				EmployeeDTO employeeDto = EmployeeDTO.convertFromEntity(employee); // Convert entity to DTO
				if (employeeDto != null) {
					return new ResponseStatus(PayrollServicesConstansts.SUCCESS_CODE,
							PayrollServicesConstansts.SUCCESS_STATUS, "Retrieved employee with id " + id,
							List.of(employeeDto));
				}
				return new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE,
						PayrollServicesConstansts.FAILURE_STATUS,
						"Issue occured while processing employeeDto object" + id, List.of(employeeDto));
			} else {
				// Employee not found, return a "not found" status
				logger.error("No employee with Id {} found", id);
				return new ResponseStatus(PayrollServicesConstansts.NO_RESULTS_CODE,
						PayrollServicesConstansts.NO_RESULTS_STATUS, "No employee with id " + id, List.of());
			}

		} catch (Exception e) {
			logger.error("Error retrieving employee with ID {}: {}", id, e.getMessage(), e);
			return new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE, PayrollServicesConstansts.FAILURE_STATUS,
					"Technical error occurred", List.of());
		}
	}

	@Override
	public ResponseStatus addEmployee(@Valid EmployeeDTO employeeDTO) {
		try {
			Employee employee = EmployeeDTO.convertFromEmployeeDto(employeeDTO);
			if (employee != null) {
				payrollRepository.save(employee);
				return new ResponseStatus(PayrollServicesConstansts.SUCCESS_CODE,
						PayrollServicesConstansts.SUCCESS_STATUS, "Added employee to the database",
						List.of(EmployeeDTO.convertFromEntity(employee)));
			}
			return new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE, PayrollServicesConstansts.FAILURE_STATUS,
					"Employee info is null", List.of());
		} catch (Exception e) {
			logger.error("Error saving employee", e.getMessage(), e);
			return new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE, PayrollServicesConstansts.FAILURE_STATUS,
					"Technical error occurred", List.of());
		}
	}

	@Override
	public ResponseStatus updateEmployee(@Valid EmployeeDTO employeeDTO) {
		try {
			if (employeeDTO != null && employeeDTO.getEmployeeId() != null) {
				Optional<Employee> employeeOpt = payrollRepository.findById(employeeDTO.getEmployeeId());
				logger.info("Request received for updating an Employee data...");
				if (employeeOpt.isPresent()) {
					Employee employee = EmployeeDTO.convertFromEmployeeDto(employeeDTO);
					payrollRepository.save(employee);
					return new ResponseStatus(PayrollServicesConstansts.SUCCESS_CODE,
							PayrollServicesConstansts.SUCCESS_STATUS,
							"Updated employee with id " + employeeDTO.getEmployeeId(),
							List.of(EmployeeDTO.convertFromEntity(employee)));
				}

			}
			logger.error("No employee with Id {} found", employeeDTO.getEmployeeId());
			return new ResponseStatus(PayrollServicesConstansts.NO_RESULTS_CODE,
					PayrollServicesConstansts.NO_RESULTS_STATUS, "No employee with id " + employeeDTO.getEmployeeId(),
					List.of());
			
		} catch (Exception e) {
			logger.error("Error saving employee", e.getMessage(), e);
			return new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE, PayrollServicesConstansts.FAILURE_STATUS,
					"Technical error occurred", List.of());
		}
	}

	@Override
	public ResponseStatus deleteEmployeebyId(Long id) {
		logger.info("Fetching employee with ID: {}", id);
		try {

			Optional<Employee> employeeOpt = payrollRepository.findById(id);
			if (employeeOpt.isPresent()) {
				payrollRepository.deleteById(id);
				return new ResponseStatus(PayrollServicesConstansts.SUCCESS_CODE,
						PayrollServicesConstansts.SUCCESS_STATUS, "Deleted employee with id " + id, List.of());
			} else {
				// Employee not found, return a "not found" status
				logger.error("No employee with Id {} found", id);
				return new ResponseStatus(PayrollServicesConstansts.NO_RESULTS_CODE,
						PayrollServicesConstansts.NO_RESULTS_STATUS, "No employee with id " + id, List.of());
			}

		} catch (Exception e) {
			logger.error("Error deleting employee with ID {}: {}", id, e.getMessage(), e);
			return new ResponseStatus(PayrollServicesConstansts.FAILURE_CODE, PayrollServicesConstansts.FAILURE_STATUS,
					"Technical error occurred", List.of());
		}
	}

}
