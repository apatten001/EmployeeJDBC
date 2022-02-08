package com.hcl.jbdc;

public class Employee {
	
	/**
	 * TABLE ATTRIBUTES EmpID,EmpName,DOB,Salary,Age
	 */
	
	int empId;
	String empName;
	String dob;
	int salary;
	int age;
	
	Employee(){}
	
	Employee(int empId, String empName, String dob, int salary, int age){
		
		this.empId = empId;
		this.empName = empName;
		this.dob = dob; 
		this.salary = salary;
		this.age = age; 
		
	}

}
