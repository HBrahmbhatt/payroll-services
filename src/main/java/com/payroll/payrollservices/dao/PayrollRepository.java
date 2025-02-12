package com.payroll.payrollservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payroll.payrollservices.entity.Employee;

@Repository
public interface PayrollRepository extends JpaRepository<Employee, Long> {

}
