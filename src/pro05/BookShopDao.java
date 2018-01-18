package pro05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookShopDao {
	
	public void insert(BookVo vo) {
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
					String query = "insert into bookshop values (seq_member_id.nextval, ?, ?, ?, ?, ?)";
					pstmt = conn.prepareStatement(query);

					pstmt.setString(1, vo.getTitle());
					pstmt.setString(2, vo.getPubs());
					pstmt.setString(3, vo.getPubDate());
					pstmt.setString(4, vo.getAuthorName());
					pstmt.setString(5, vo.getStateCode());
					
					pstmt.executeUpdate();

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
	public void rent(int id) {
		// 0. import java.sql.*;
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {
					// 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");

					// 2. Connection 얻어오기
					String url = "jdbc:oracle:thin:@localhost:1521:xe";
					conn = DriverManager.getConnection(url, "webdb", "webdb");

					// 3. SQL문 준비 / 바인딩 / 실행
					String query="update bookshop set state_code='0' where id=?";
					pstmt=conn.prepareStatement(query);
				
					pstmt.setInt(1, id);
					pstmt.executeUpdate();

					query="select title from bookshop where id=?";
					pstmt=conn.prepareStatement(query);
					
					pstmt.setInt(1, id);
					rs = pstmt.executeQuery();

					while(rs.next()) {
						System.out.println(rs.getString("title") + "이(가) 대여 됐습니다.");
					}

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
	
	public List<BookVo> getListAll() {
		// 0. import java.sql.*;
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				List<BookVo> listAll = new ArrayList<BookVo>();

				try {
					// 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");

					// 2. Connection 얻어오기
					String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
					conn = DriverManager.getConnection(url, "webdb", "webdb"); 

					// 3. SQL문 준비 / 바인딩 / 실행
					String query =  " select title, " + 
									"        pubs, " + 
									"        pub_date, " + 
									"        author_name, " + 
									"        state_code " + 
									" from bookshop ";
					pstmt = conn.prepareStatement(query);

					rs = pstmt.executeQuery(); 

					// 4.결과처리
					while (rs.next()) {
						BookVo vo = new BookVo();
						
						String title = rs.getString("title");
						String pubs = rs.getString("pubs");
						String pubDate = rs.getString("pub_date");
						String authorName = rs.getString("author_name");
						String stateCode = rs.getString("state_code");
						
						vo.setTitle(rs.getString(title));
						vo.setPubs(rs.getString(pubs));
						vo.setPubDate(rs.getString(pubDate));
						vo.setAuthorName(rs.getString(authorName));
						vo.setStateCode(rs.getString(stateCode));

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
