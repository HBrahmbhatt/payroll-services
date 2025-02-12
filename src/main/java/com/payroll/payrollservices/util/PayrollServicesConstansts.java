package com.payroll.payrollservices.util;

public class PayrollServicesConstansts {

	// Success-failure codes & status
	public static final String SUCCESS_CODE = "0000";
	public static final String SUCCESS_STATUS = "Success";

	public static final String FAILURE_CODE = "9999";
	public static final String FAILURE_STATUS = "Failure";

	public static final String NO_RESULTS_CODE = "1000";
	public static final String NO_RESULTS_STATUS = "No results";

	public static final String INVALID_DATA_FORMAT_CODE = "8000";
	public static final String INVALID_DATA_FORMAT_STATUS = "Invalid Data";

	// Validation Regex
	public static final String GENDER_REGEX = "^(Male|Female|Other)$";
	public static final String PHONE_REGEX = "^\\d{3}-\\d{3}-\\d{4}$";
	public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

}
