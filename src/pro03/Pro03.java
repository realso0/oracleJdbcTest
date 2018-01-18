package pro03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pro03 {

	public static void main(String[] args) {
		// 0. import java.sql.*;
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				System.out.println("Employee_Id\t"+"LastName\t"+"Email\t"+"jobTitle\t"+"DepartmentName\t"+"\tCity");
				try {
					// 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");

					// 2. Connection 얻어오기
					String url = "jdbc:oracle:thin:@localhost:1521:xe";
					conn = DriverManager.getConnection(url, "hr", "hr");

					// 3. SQL문 준비 / 바인딩 / 실행
					String query = " select employee_id, " + 
							"        last_name, " + 
							"        email, " + 
							"        job_title, " + 
							"        department_name, " + 
							"        city " + 
							" from employees emp, departments dep, locations loc, jobs jo " + 
							" where emp.department_id=dep.department_id " + 
							" and dep.location_id=loc.location_id " + 
							" and emp.job_id=jo.job_id " + 
							" and loc.city like 'Seattle' " + 
							" and jo.job_id like 'PU_CLERK' " + 
							" order by employee_id desc "; 
																												 
					pstmt = conn.prepareStatement(query);

					rs = pstmt.executeQuery(); 

					// 4.결과처리
					while (rs.next()) { 
						int employeeId=rs.getInt("employee_id");
						String lastName = rs.getString("last_name");
						String email = rs.getString("email");
						String jobTitle=rs.getString("job_title");
						String departmentName=rs.getString("department_name");
						String city=rs.getString("city");

						System.out.println(employeeId + "\t" + lastName + "\t" + email + "\t" + jobTitle + "\t" + departmentName + "\t"
								+ city);
					}

				} catch (ClassNotFoundException e) {
					System.out.println("error: 드라이버 로딩 실패 - " + e);
				} catch (SQLException e) {
					System.out.println("error:" + e);
				} finally {
					// 5. 자원정리
					try {
						if (rs != null) {
							rs.close();
						}
						if (pstmt != null) {
							pstmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e) {
						System.out.println("error:" + e);
					}

				}


    }


}
