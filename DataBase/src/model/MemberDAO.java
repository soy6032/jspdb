package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {

	/*
	 * String id = "system"; 
	 * String pass = "123456"; 
	 * String url = "jdbc:oracle:thin:@localhost:1521:XE";
	 */
	
	Connection con;// 데이터베이스에 접근할 수 있도록 설정
	PreparedStatement pstmt; // 데이터베이스에서 쿼리를 실행시켜주는 객체
	ResultSet rs;//데이터베이스에 테이블의결과을 리턴받아 자바에 저장해주는 객체 
	
	//데이터베이스에 접근을 할 수 있도록 도와주는 메소드
	public void getCon() {
		//커넥션풀을 이용하여 데이터 베이스에 접근하는 소스
		
		try {
			//외부에서 네이터를 읽어들여야하기에
			Context initctx = new InitialContext();
			//톰캣 서버에 정보를 담아 놓은 곳이로 이동
			Context envctx = (Context) initctx.lookup("java:comp/env");
			//데이터 소스 객체 선언
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			//데티어 소스를 기준으로 커넥션을 연결함
			con = ds.getConnection();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*
		 * try { //해당 데이터베이스를 사용한다고 선언 Class.forName("oracle.jdbc.driver.OracleDriver");
		 * //해당 데이터베이스에 접속 con = DriverManager.getConnection(url,id,pass); }
		 * catch(Exception e) { e.printStackTrace(); }
		 */
	}
	
	public void insertMember(MemberBean mbean) {
		try {
			getCon();
			
			String sql = "insert into member values(?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mbean.getId());
			pstmt.setString(2, mbean.getPass1());
			pstmt.setString(3, mbean.getEmail());
			pstmt.setString(4, mbean.getTel());
			pstmt.setString(5, mbean.getHobby());
			pstmt.setString(6, mbean.getJob());
			pstmt.setString(7, mbean.getAge());
			pstmt.setString(8, mbean.getInfo());
			pstmt.executeUpdate();// 쿼리를 실행(insert, update, delete 시 사용)
			
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//모든 회원을 정보를 리턴 시켜주는 메소드 
	public Vector<MemberBean> allSelectMember(){
		//가변 길이로 데이터를 저장
		Vector<MemberBean> v = new Vector<>();
		try {//무조건 데이터베이스는 예외처리를 반드시 해야함.
			getCon();
			//쿼리준비
			String sql = "select * from member";
			//쿼리를 실행시켜주는 객체선언
			pstmt = con.prepareStatement(sql);
			//쿼리를 실행시킨 결과를 리턴해서 받아줌(오라틀 테이블의 검색된 결과를 자바 객체에 저장)
			rs = pstmt.executeQuery();
			//반복문을 사용해서 rs에 저장된 데이터를 추출해놓아야함
			while(rs.next()) {//rs.next() --> 저장된 데이터 만큼까지 돌리겠다는 뜻
				MemberBean bean = new MemberBean();//컬럼으로 나뉘어진 데이터를 빈 클래스에 저장
				bean.setId(rs.getString(1));
				bean.setPass1(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setTel(rs.getString(4));
				bean.setHobby(rs.getString(5));
				bean.setJob(rs.getString(6));
				bean.setAge(rs.getString(7));
				bean.setInfo(rs.getString(8));
				//패키징된 MemberBean 클래스를 벡터에 저장
				v.add(bean);//0번지부터 순서대로 데이터가 저장
			}//자원반납
			con.close();
		} 
		catch (Exception e) {	
		}
		//저장된 벤터를 리턴
		return v;
	}
	
	public MemberBean oneSelectMember(String id) {
		//한사람에 대한 정보만 리턴하기에 빈클래스 객체 생성
		MemberBean bean = new MemberBean();
		
		try {
			getCon();
			String sql = "select * from member where id=?";
			pstmt = con.prepareStatement(sql);
			//?의 값을 맵핑
			pstmt.setString(1, id);
			//쿼리실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {//레코드가 있다면
				bean.setId(rs.getString(1));
				bean.setPass1(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setTel(rs.getString(4));
				bean.setHobby(rs.getString(5));
				bean.setJob(rs.getString(6));
				bean.setAge(rs.getString(7));
				bean.setInfo(rs.getString(8));
			}
			//자원반납
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return bean;
	}
	
	//한회원의 패스워드값을 리턴하는 메소드 작성
	
	public String getpass(String id) {

		String pass = "";
		
		try {
			
			getCon();
			String sql = "select pass1 from member where id=?";
			pstmt = con.prepareStatement(sql);
			//?의 값을 맵핑
			pstmt.setString(1, id);
			//쿼리 실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pass = rs.getString(1);//패스워드값이 저장된 컬럼 인덱스
			}
			//자원반납
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//결과를 리턴
		return pass;
	}
	
	//한 회원의 정보를 수정하는 메소드
	public void updateMember(MemberBean bean) {
		
		getCon();
		
		try {
			//쿼리준비
			String sql = "update member set email=?,tel=? where id=?";
			//쿼리 실행객체 선언
			pstmt = con.prepareStatement(sql);
			//?의 값을 맵핑
			pstmt.setString(1, bean.getEmail());
			pstmt.setString(2, bean.getTel());
			pstmt.setString(3, bean.getId());
			//쿼리실행
			pstmt.executeUpdate();
			//자원반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//한 회원을 삭제하는 메소드
	public void deleteMember(String id) {
		
		getCon();
		
		try {
			
			String sql = "delete from member where id=?";
			pstmt = con.prepareStatement(sql);
			//?의 값을 맵핑
			pstmt.setString(1, id);
			//쿼리실행
			pstmt.executeUpdate();
			//자원반납
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
