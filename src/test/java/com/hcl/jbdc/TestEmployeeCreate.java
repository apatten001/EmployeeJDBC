package com.hcl.jbdc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
// import static org.junit.Assert.assertFalse;

import java.sql.Connection;

public class TestEmployeeCreate {

	EmployeeCreate empcreate = new EmployeeCreate();

	void testDeleteRecord() {
		try (Connection connection = H2JDBCUtils.getConnection()) {

			Employee employee = new Employee(9, "Arnold", "0705", 80000, 18);
			assertEquals(employee.empId, empcreate.selectRecord(connection).empId);
			assertEquals(employee.dob, empcreate.selectRecord(connection).dob);
			assertEquals(employee.empName, empcreate.selectRecord(connection).empName);
			assertEquals(employee.salary, empcreate.selectRecord(connection).salary);
			assertEquals(employee.age, empcreate.selectRecord(connection).age);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	void testInsertReocrd() {
		try (Connection connection = H2JDBCUtils.getConnection()) {

			Employee employee = new Employee(9, "Arnold", "0705", 80000, 18);
			assertNotEquals(employee.empId, empcreate.selectRecord(connection).empId);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	void testUpdateRecord() {
		try (Connection connection = H2JDBCUtils.getConnection()) {

			Employee employee = new Employee(1, "Arnold", "0705", 80000, 18);
			assertNotEquals(employee.empName, empcreate.selectRecord(connection).empName);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	void testSelectRecord() {
		try (Connection connection = H2JDBCUtils.getConnection()) {

			Employee employee = new Employee(1, "Arnold", "0705", 80000, 18);
			assertEquals(employee.empId, empcreate.selectRecord(connection).empId);
			assertEquals(employee.dob, empcreate.selectRecord(connection).dob);
			assertEquals(employee.empName, empcreate.selectRecord(connection).empName);
			assertEquals(employee.salary, empcreate.selectRecord(connection).salary);
			assertEquals(employee.age, empcreate.selectRecord(connection).age);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
