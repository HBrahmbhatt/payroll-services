package com.payroll.payrollservices.entity;

import java.time.LocalDate;

import com.payroll.payrollservices.util.PayrollServicesConstansts;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Employee {

	@Id
	@GeneratedValue
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

}
