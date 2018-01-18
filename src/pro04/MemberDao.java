package pro04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {
	// MemberDao 를 작성합니다.

	public void insertMember(MemberVo vo) {
		// 0. import java.sql.*;
				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					// 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");

					// 2. Connection 얻어오기
					String url = "jdbc:oracle:thin:@localhost:1521:xe";
					conn = DriverManager.getConnection(url, "webdb", "webdb");

					// 3. SQL문 준비 / 바인딩 / 실행
					String query = "INSERT INTO member VALUES (seq_member_id.nextval, ?, ?, ?, ?)";
					pstmt = conn.prepareStatement(query);

					pstmt.setString(1, vo.getName()); 
					pstmt.setString(2, vo.getEmail()); 
					pstmt.setString(3, vo.getPassword());
					pstmt.setString(4, vo.getGender());
					
					int count = pstmt.executeUpdate();

					// 4.결과처리

				} catch (ClassNotFoundException e) {
					System.out.println("error: 드라이버 로딩 실패 - " + e);
				} catch (SQLException e) {
					System.out.println("error:" + e);
				} finally {
					// 5. 자원정리
					try {
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

	public void updatePassword(MemberVo vo) {
		// 0. import java.sql.*;
				Connection conn = null; // 연결 잘됬는지 여부 때문에 필요
				PreparedStatement pstmt = null; // 쿼리문 관련

				try {
					// 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");

					// 2. Connection 얻어오기
					String url = "jdbc:oracle:thin:@localhost:1521:xe";
					conn = DriverManager.getConnection(url, "webdb", "webdb");

					// 3. SQL문 준비 / 바인딩 / 실행
					String query = "update member set password=? where email=?";
					pstmt = conn.prepareStatement(query);
					
					pstmt.setString(1, vo.getPassword());
					pstmt.setString(2, vo.getEmail());

					int count = pstmt.executeUpdate(); // 따로 적어준 값들을 조합해주어, DB로 날려주게 됨.
					// 4.결과처리
					
				} catch (ClassNotFoundException e) {
					System.out.println("error: 드라이버 로딩 실패 - " + e);
				} catch (SQLException e) {
					System.out.println("error:" + e);
				} finally {
					// 5. 자원정리
					try {
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

	public void deleteMember(String email) {
		// 0. import java.sql.*;
				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					// 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");

					// 2. Connection 얻어오기
					String url = "jdbc:oracle:thin:@localhost:1521:xe";
					conn = DriverManager.getConnection(url, "webdb", "webdb");

					// 3. SQL문 준비 / 바인딩 / 실행
					String query = "delete from member where email=?";
					pstmt = conn.prepareStatement(query);
					
					pstmt.setString(1, email);

					int count = pstmt.executeUpdate();

					// 4.결과처리

				} catch (ClassNotFoundException e) {
					System.out.println("error: 드라이버 로딩 실패 - " + e);
				} catch (SQLException e) {
					System.out.println("error:" + e);
				} finally {
					// 5. 자원정리
					try {
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

	
	public List<MemberVo> getListAll() {
		// 0. import java.sql.*;
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				List<MemberVo> listAll = new ArrayList<MemberVo>();

				try {
					// 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");

					// 2. Connection 얻어오기
					String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
					conn = DriverManager.getConnection(url, "webdb", "webdb"); 

					// 3. SQL문 준비 / 바인딩 / 실행
					String query =  " select id, " +
					        			" name, " +
					        			" email, " +
					        			" password, " +
					        			" gender " +
					        			" from member ";
					pstmt = conn.prepareStatement(query);

					rs = pstmt.executeQuery(); 

					// 4.결과처리
					while (rs.next()) {
						MemberVo vo = new MemberVo();
						int id = rs.getInt("id");
						String name = rs.getString("name");
						String email = rs.getString("email");
						String password = rs.getString("password");
						String gender = rs.getString("gender");
						
						vo.setId(id);
						vo.setName(name);
						vo.setEmail(email);
						vo.setPassword(password);
						vo.setGender(gender);

						listAll.add(vo); // 리스트와 각 객체를 연결

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
				return listAll;
	}
}
