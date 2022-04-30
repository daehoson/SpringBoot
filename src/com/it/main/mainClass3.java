package com.it.main;
/* 
 *	자바 웹 개발
 *	-------- 반복문 , 조건문 , 메소드 , 데이터베이스 연결
 *								 -------------
 *			 파일 업로드 , 파일 다운로드 
 *	형식)
 *		조건문 
 *		if(조건문)
 *		{
 *			조건문이 true 일 때
 *			실행문장 => 에러방지 , 오류방지(유효성 검사)
 *		}
 *
 *		if(조건문)
 *		{
 *			조건이 true
 *		}
 * 		else
 *		{
 *			조건이 false
 *		}
 * 
 * 		if(조건)
 *		{
 *			조건이 true  ==> 문장을 수행 => 종료
 *			조건이 false ==> 밑에 있는 조건으로 이동
 *		}
 * 		else if(조건)
 *		{
 *			조건이 true  ==> 문장을 수행 => 종료
 *			조건이 false ==> 밑에 있는 조건으로 이동
 *		}
 *		else
 *		{
 *			조건이 없는 경우에 처리되는 문장
 *		}
 *		---------------------------------------------------
 *		예외처리 : 목적(비정상 종료를 방지하고 정상상태 유지)
 *				=> 사전에 에러를 방지할 목적
 *		예외처리방법 : 예외 복구(직접처리) => try ~ catch ~ finally
 *				     예외 회피(간접처리) => throws 
 *
 *				     상속도
 *					Object : 자바의 모든 클래스 (라이브러리 , 사용자정의) 상위 클래스
 *						|
 *					Throwable
 *						|
 *					----------
 *					|		|
 *				Error		Exception(가벼운에러) => 소스에서 수정이 가능한 에러
 *							|
 *						-----------------
 *						|				|
 *					CheckException   UnCheckException (실행시에러)
 *					컴파일시에 JVM이 예외처리를 확인		= RuntimeException - ArrayIndexOutOfBoundsException
 *																   - NullPointerException
 *																   - NumberFormatException 문자를 숫자로 변경 불가할때
 *																   - ClassCastException 형변환이 안된는데 형변환 할 때
 *
 *						= IOException 파일명 , 경로명 틀리면
 *						= InterruptException 스레드관련(Thread충돌방지)
 *						= SQLException 서버주소,SQL문장오류, 데이터베이스 풀렸을때
 *						= ClassNotFoundException
 *						= MalpormedURLException URL이 틀릴 경우
 *					-----------------------------------------------
 *					 예외처리 필요 : java.io, java.net , java.sql
 *			예외처리 형식
 *			try
 *			{
 *				정상적으로 수행하는 문장
 *			}catch(예외처리 종류){ (작은 순서대로 수행)
 *				예외복구
 *			}catch(예외처리 종류){
 *				예외복구
 *			}catch(예외처리 종류){
 *				예외복구
 *			}finally{
 *				정상/에러 상관없이 무조건 수행하는 문장
 *				데이터베이스/서버/파일 닫기 등 무조건 필요 (커넥션이 누적이 됨. 과부하. 디도스같은 공격) DBCP가 기본	
 *			}
 */

import java.sql.*;
/*
 * 1. MySql연결 => Connection
 * 2. SQL문장 전송 => Statement
 * 3. 결과값을 읽어 온다 => Statement
 * 4. 결과값을 저장 => ResultSet
 */

class Database 
{
	private Connection conn; // mysql 연결
	private PreparedStatement ps; // SQL 문장 전송
	private final String URL = "jdbc:mysql://localhost:3306/db0416?serverTimezone=UTC"; // mysql주소
	// 드라이버 등록 : 한번만 등록 => 생성자(초기화)
	public Database()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver"); // 연결만 => thin (DI방식)
		}catch(Exception ex) {}
	}
	// 연결
	public void getConnection()
	{
		try {
			conn = DriverManager.getConnection(URL, "root", "1111");
		}catch(Exception ex) {}
	}
	// 해제
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close(); // 통신
			if(conn!=null) conn.close(); // 연결기기 => TCP(Socket)
		}catch(Exception ex) {}
	}
	// 기능
	public void studentListDate()
	{
		try
		{
			//정상
			//1.MySQL연결
			getConnection();
			//2.SQL문장 제작
			String sql = "SELECT * FROM student";
			//3.MySQL로 SQL문장 전송
			ps=conn.prepareStatement(sql);
			//4.실행결과를 받아온다
			ResultSet rs=ps.executeQuery();
			//5.결과를 출력
			/*
			 * 문자열 : String => getString()
			 * 정수 : getInt()
			 * 실수 : getDouble()
			 * 날짜 : getDate
			 * */
			while(rs.next()) { //데이터가 없을때까지 읽어라 => 출력의 첫번째 위치에 커서 이동
				System.out.println(rs.getInt(1)+" "
						+rs.getString(2)+" "
						+rs.getInt(3)+" "
						+rs.getInt(4)+" "
						+rs.getInt(5)+" ");
			}
			rs.close();
		}catch(Exception ex)
		{
			//에러발생시 처리
			ex.printStackTrace();
			/*
			 * 에러 발생시 확인
			 * printStackTrace() : 실행과정 확인 => 에러 위치
			 * getMessage()	: 에러메세지만 확인
			 */			
		}
		finally
		{
			//try , catch 상관없이 무조건 수행하는 영역 => 서버 닫기
			disConnection(); // 닫기
		}
	}
	// Commit => AutoCommit => CURD (insert , update , delete)
	// 추가
	public void studentInsert(String name, int kor, int eng, int math) // catch , finally 는 항상 똑같은 함수
	{
		try 
		{
			//연결
			getConnection();
			//SQL문장
			String sql = "INSERT INTO student(name, kor, eng, math)"
					+"VALUES(?,?,?,?)";
			/*
			 * String sql = "INSERT INTO student(name, kor, eng, math)" +"VALUES('"+name+"',"+kor+","+eng+","+math+")"; 옛날방식
			 */
			ps=conn.prepareStatement(sql);
			//실행하기 전에 ? 의 값을 채운다.
			ps.setString(1, name); // '홍길동' => setDate() '2020-04-16' 
			ps.setInt(2, kor);
			ps.setInt(3, eng);
			ps.setInt(4, math);
			//실행
			ps.executeUpdate(); // commit()포함
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
	// 수정
	public void studentUpdate(int hakbun, String name, int kor, int eng, int math)
	{
		try
		{
			//연결
			getConnection();
			//SQL문장
			String sql = "UPDATE student SET "
						+"name=?, kor=?,eng=?,math=? "
						+"WHERE hakbun=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, kor);
			ps.setInt(3, eng);
			ps.setInt(4, math);
			ps.setInt(5, hakbun);
			//실행요청
			ps.executeUpdate();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disConnection();
		}
	}
	// 삭제
	public void studentDelete(int hakbun)
	{
		try
		{
			//연결
			getConnection();
			String sql = "DELETE FROM student "
						+"WHERE hakbun=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, hakbun);
			//실행요청
			ps.executeUpdate();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disConnection();
		}
	}
}

/*
 *	객체지향 프로그램 (클래스 분리) ==> VO, DTO, Entity
 *							==> DAO
 *							==> Manager
 *							==> Service
 *  ==> 상속, 포함
 *  ==> 오버라이딩, 오버로딩
 *  ==> 인터페이스
 *  ==> 라이브러리
 *  ----------------------------------- MySQL  
*/
public class mainClass3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Thread.sleep(1000); 예외처리 안해서 오류
		//객체생성
		Database db=new Database();
		db.studentListDate();
		System.out.println("=============== 데이터추가 ===============");
		//db.studentInsert("이순신", 90, 89, 78);
		db.studentListDate();
		System.out.println("=============== 데이터삭제 ===============");
		//db.studentDelete(4);
		db.studentListDate();
		System.out.println("=============== 데이터수정 ===============");
		db.studentUpdate(1,"홍길동수정",90,70,90);
		db.studentListDate();
		
	}

}
