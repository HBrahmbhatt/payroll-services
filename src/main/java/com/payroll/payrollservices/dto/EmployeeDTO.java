package com.payroll.payrollservices.dto;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.payroll.payrollservices.entity.Employee;
import com.payroll.payrollservices.util.PayrollServicesConstansts;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO {

	private static Logger logger = LoggerFactory.getLogger(EmployeeDTO.class);
	
	private Long employeeId;
	
	@NotNull(message = "First name can't be null.")
	@Size(min = 1, max = 100, message = "First name must be between 1 to 100 characters.")
	private String firstName;

	@NotNull(message = "Last name can't be null.")
	@Size(min = 1, max = 100, message = "Last name must be between 1 to 100 characters.")
	private String lastName;

	@NotNull(message = "Date of birth cannot be null")
	@Past(message = "Date of birth must be a past date.")
	private LocalDate dob;

	@NotNull(message = "Gender cannot be null")
	@Pattern(regexp = PayrollServicesConstansts.GENDER_REGEX, message = "Gender must be 'Male', 'Female', or 'Other'")
	private String gender;

	@NotNull
	@Pattern(regexp = PayrollServicesConstansts.EMAIL_REGEX, message = "Invalid email address")
	private String email;

	@Pattern(regexp = PayrollServicesConstansts.PHONE_REGEX, message = "The phone number must be entered in format 'xxx-xxx-xxxx'.")
	private String phone;

	private String address;

	@NotNull
	private String role;

	@NotNull(message = "Date of joining cannot be null.")
	@Past(message = "Date of birth must be a past date.")
	private LocalDate joiningDate;

	// This is a business specific variable, hence it is not added in Entity
	private String experience;

	// Method to calculate experience
	private static String calculateExperience(LocalDate joiningDate) {
		try {
			if (joiningDate == null) {
				return "N/A"; // If joiningDate is not available, return "N/A"
			}
			int years = Period.between(joiningDate, LocalDate.now()).getYears();
			if (years == 0) {
				return "Less than 1 year"; // If experience is less than 1 year
			}
			return years + " years"; // Return the number of years of experience
		} catch (Exception e) {
			logger.error("An error occured while calculating the experience --> " + e.getMessage());
		}
		return "";
	}

	// Method to convert EmployeeDTO to Employee
	public static Employee convertFromEmployeeDto(@Valid EmployeeDTO employeeDTO) {
		try {
			if (employeeDTO != null) {
				return new Employee(employeeDTO.getEmployeeId(), employeeDTO.getFirstName(), employeeDTO.getLastName(), employeeDTO.getDob(),
						employeeDTO.getGender(), employeeDTO.getEmail(), employeeDTO.getPhone(),
						employeeDTO.getAddress(), employeeDTO.getRole(), employeeDTO.getJoiningDate());
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Error occured in convertFromEntity --> " + e.getMessage());
			return null;
		}
	}

	// Static method to convert Employee entity to EmployeeDTO
	public static EmployeeDTO convertFromEntity(@Valid Employee employee) {
		// Calculate the experience here
		String experience = "";
		try {
			experience = calculateExperience(employee.getJoiningDate());
			// Convert entity to DTO
			return new EmployeeDTO(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getDob(),
					employee.getGender(), employee.getEmail(), employee.getPhone(), employee.getAddress(),
					employee.getRole(), employee.getJoiningDate(), experience // Add calculated experience here
			);
		} catch (Exception e) {
			logger.error("Error occured in convertFromEntity --> " + e.getMessage());
			return null;
		}
	}

	// This method can be used for converting a list of employees to an employeeDto
	// list
	public static List<EmployeeDTO> convertToEmployeeDTOList(List<Employee> employees) {
		return employees.stream().map(EmployeeDTO::convertFromEntity) // Convert each Employee entity to EmployeeDTO
				.collect(Collectors.toList()); // Collect the results into a List<EmployeeDTO>
	}

}
