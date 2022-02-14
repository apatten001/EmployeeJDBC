package com.hcl.jbdc;

import java.io.IOException;
import java.sql.Connection;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;import java.util.stream.Collectors;

public class EmployeeCreate {

	Scanner sc = new Scanner(System.in);
	static Logger log = Logger.getLogger(EmployeeCreate.class.getName());

	/**
	 * TABLE ATTRIBUTES EmpID,EmpName,DOB,Salary,Age
	 */

	private static final String CREATEEMPLOYEETABLE = "CREATE TABLE EMPLOYEE (EmpId int primary key,"
			+ " EmpName VARCHAR(30), DOB VARCHAR(25), SALARY int, Age int )";

	private static final String INSERT2TABLE = "INSERT INTO EMPLOYEE (EmpId, EmpName, DOB,"
			+ "Salary, Age) VALUES (?,?,?,?,?); ";

	private static final String QUERYALLRecords = "SELECT * FROM EMPLOYEE;";

	private static final String QUERY = "SELECT * FROM EMPLOYEE WHERE Empid = ?;";

	private static final String UPDATERECORDBYEMPID = "UPDATE EMPLOYEE SET EmpName = ? WHERE EmpId = ?;";

	private static final String DELETERECORDBYEMPID = "DELETE FROM EMPLOYEE WHERE EmpId = ?;";

	public static void main(String[] args) throws IOException{

		EmployeeCreate newEmployeeTable = new EmployeeCreate();
		try (Connection connection = H2JDBCUtils.getConnection()) {

			// Create table for H2JDBC
			newEmployeeTable.createTable(connection);
			System.out.println();
			log.debug("createTable method called");
			log.info("Creating a new Employee table");  
			log.error("Could be an Input otput error");

			// Insert multiple employees data into the database
			newEmployeeTable.insertMultipleEmployees(connection);
			System.out.println();
			log.debug("insertMultipleEmployees method called");
			log.info("Inserting a list of employees into the Employee table");  
			log.error("Please enter one or more employees into the employee list");

			// Select all the records
			newEmployeeTable.selectAllRecord(connection);
			System.out.println();
			log.debug("selectAllRecord method called");
			log.info("Record must be selected by id");  
			log.error("Please enter one id to search");
			newEmployeeTable.insertRecord(connection);
			System.out.println();
			log.debug("insertRecord method called");
			log.info("Inserting an employee into the database");  
			log.error("Please enter all values for the employee");
			newEmployeeTable.selectRecord(connection);
			System.out.println();
			newEmployeeTable.updateRecord(connection);
			System.out.println();
			log.debug("updateRecord method called");
			log.info("Updating a specified employee attribute into the database");  
			log.error("Please enter all values  to update for the employee");
			newEmployeeTable.selectAllRecord(connection);
			System.out.println();
			newEmployeeTable.deleteRecord(connection);
			System.out.println();
			
			log.debug("deleteRecord method called");
			log.info("deleting a specified employee by id from the database");  
			log.error("Please enter a valid employee id");
			newEmployeeTable.selectAllRecord(connection);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void createTable(Connection connection) throws SQLException {

		System.out.println(CREATEEMPLOYEETABLE);
		// Step 1: Establishing a Connection
		try {
			// Step 2:Create a statement using connection object
			Statement statement = connection.createStatement();
			{

				// Step 3: Execute the query or update query
				statement.execute(CREATEEMPLOYEETABLE);
			}
		} catch (SQLException e) {
			// print SQL exception information
			H2JDBCUtils.printSQLException(e);
		}
	}

	public void insertMultipleEmployees(Connection connection) throws SQLException {
		// System.out.println(INSERT2TABLE);
		// Step 1: Establishing a Connection
		try {
			List<Employee> empList = new ArrayList<>();

			Employee emp1 = new Employee(1, "John", "07/05", 50000, 27);
			Employee emp2 = new Employee(2, "Johnny", "10/05", 60000, 65);
			Employee emp3 = new Employee(3, "James", "12/15", 65000, 56);
			Employee emp4 = new Employee(4, "Roy", "01/23", 63000, 64);
			Employee emp5 = new Employee(5, "Arnold", "08/25", 50000, 23);

			empList.add(emp1);
			empList.add(emp2);
			empList.add(emp3);
			empList.add(emp4);
			empList.add(emp5);

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT2TABLE);
			for (Employee emp : empList) {

				preparedStatement.setInt(1, emp.empId);
				preparedStatement.setString(2, emp.empName);
				preparedStatement.setString(3, emp.dob);
				preparedStatement.setInt(4, emp.salary);
				preparedStatement.setInt(5, emp.age);
				System.out.println(preparedStatement);
				// Step 3: Execute the query or update query
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {

			// print SQL exception information
			H2JDBCUtils.printSQLException(e);
		}

		// Step 4: try-with-resource statement will auto close the connection.
	}

	public void insertRecord(Connection connection) throws SQLException {
		   System.out.println(INSERT2TABLE);
		// Step 1: Establishing a Connection
		try {
			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT2TABLE);

			System.out.println("ENTER Employee Id:");
			preparedStatement.setInt(1, sc.nextInt());
			System.out.println("ENTER Employee Name:");
			preparedStatement.setString(2, sc.next());
			System.out.println("ENTER Employee Date of Birth:");
			preparedStatement.setString(3, sc.next());
			System.out.println("ENTER Employee Salary:");
			preparedStatement.setInt(4, sc.nextInt());
			System.out.println("ENTER Employee Age:");
			preparedStatement.setInt(5, sc.nextInt());

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			// print SQL exception information
			H2JDBCUtils.printSQLException(e);
		}

		// Step 4: try-with-resource statement will auto close the connection.
	}

	// selects a record based on a condition
	public Employee selectRecord(Connection connection) throws SQLException {
		try {

			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY);

			System.out.println("Enter an employee id to search: ");
			preparedStatement.setInt(1, sc.nextInt());

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			
			
			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int empid = rs.getInt("Empid");
				String name = rs.getString("EmpName");
				String dob = rs.getString("DOB");
				int salary = rs.getInt("Salary");
				int age = rs.getInt("Age");
				Employee employee  = new Employee(empid,name,dob,salary, age);
				System.out.println("[" + empid + "," + name + "," + dob + "," + salary + "," + age + "]");
				return employee;
			}
		} catch (SQLException e) {
			H2JDBCUtils.printSQLException(e);
		}
		
		return new Employee();
	}

	// prints all the selected records
	public void selectAllRecord(Connection connection) throws SQLException {
		try {
			List<Employee> empList = new ArrayList<>();
			// Step 1:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(QUERYALLRecords);

			System.out.println(preparedStatement);
			// Step 2: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 3: Process the ResultSet object.
			while (rs.next()) {
				int empid = rs.getInt("Empid");
				String name = rs.getString("EmpName");
				String dob = rs.getString("DOB");
				int salary = rs.getInt("Salary");
				int age = rs.getInt("Age");
				Employee emp = new Employee(empid, name, dob, salary, age);
				empList.add(emp);
			}

			// Retrieve names and Id number employees
			Map<Integer, String> list = empList.stream().collect(Collectors.toMap(emp -> emp.empId, emp -> emp.empName));
			System.out.print(list);
			;
		} catch (SQLException e) {
			H2JDBCUtils.printSQLException(e);
		}
	}

	public void updateRecord(Connection connection) throws SQLException {
		System.out.println(UPDATERECORDBYEMPID);
		// Step 1: Establishing a Connection
		try {
			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATERECORDBYEMPID);
			{

				System.out.println("Please enter the name you want to change: ");
				preparedStatement.setString(1, sc.next());
				System.out.println("Please enter the id of the employee you want to change their name: ");
				preparedStatement.setInt(2, sc.nextInt());
				// System.out.println("Executed query is as ==> " + UPDATERECORDBYEMPID);
				// Step 3: Execute the query or update query
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {

			// print SQL exception information
			H2JDBCUtils.printSQLException(e);
		}

		// Step 4: try-with-resource statement will auto close the connection.
	}

	public void deleteRecord(Connection connection) throws SQLException {
		System.out.println(DELETERECORDBYEMPID);

		try {
			// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(DELETERECORDBYEMPID);
			{

				System.out.println("Please enter the id of the employee you want to delete: ");
				preparedStatement.setInt(1, sc.nextInt());
				// System.out.println("Executed query is as ==> " + UPDATERECORDBYEMPID);
				// Step 3: Execute the query or update query
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {

			// print SQL exception information
			H2JDBCUtils.printSQLException(e);
		}

		// Step 4: try-with-resource statement will auto close the connection.
	}

}
