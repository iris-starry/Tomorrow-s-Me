package setting;

import java.sql.DriverManager;

import app.CommonFrame;

public class setting {
	
	public static void main(String args[]) {
	      try {
	         setup();
	      } catch (Exception e) {
	         //에러 보여주기
	         e.printStackTrace();
	         CommonFrame.errorMsg("셋팅 실패");
	      }
	   }
	public static void setup() throws Exception{
		// Connection con = DriverManager.getConnection();
		var con = DriverManager.getConnection(
                "jdbc:mysql://localhost/?allowLoadLocalInfile=true",
                "root",
                "1234");
		var stmt = con.createStatement();
		 
		stmt.execute("SET GLOBAL local_infile = true;");
	    System.out.println("::DB Connection 생성::");
	    
	    try {
	         stmt.execute("DROP DATABASE `diary`");
	         System.out.println("diary 제거");
	         
	      }catch (Exception e) {
	         System.out.println("diary 존재하지 않음");
	      }
	    
	    //WorkBench DB를 만들어서 SQL 얻기
	    //excute : 매개변수로 전달받은 SQL 구문을 수행하는 메서드
	    
	    
	    stmt.execute("CREATE SCHEMA `diary` DEFAULT CHARACTER SET utf8 ;\r\n");
	    System.out.println("diary DB 생성");
	    
	    System.out.println("diary information 테이블 생성");
	      stmt.execute("CREATE TABLE `diary`.`information` ("
	                + "num INT PRIMARY KEY AUTO_INCREMENT,"
	                + "title VARCHAR(20) NOT NULL,"
	                + "content VARCHAR(50) NOT NULL,"
	                + "days VARCHAR(30) NOT NULL)");
		
		
	    System.out.println("diary memo_01 테이블 생성");
		stmt.execute("CREATE TABLE `diary`.`memo_01` ("
		                + "num INT PRIMARY KEY AUTO_INCREMENT,"
		                + "thismemo VARCHAR(50) NOT NULL)"
		             	);
		
		System.out.println("diary memo_02 테이블 생성");
		stmt.execute("CREATE TABLE `diary`.`memo_02` ("
		                + "num INT PRIMARY KEY AUTO_INCREMENT,"
		                + "thismemo VARCHAR(50) NOT NULL)"
		             	);
		
		System.out.println("diary memo_03 테이블 생성");
		stmt.execute("CREATE TABLE `diary`.`memo_03` ("
		                + "num INT PRIMARY KEY AUTO_INCREMENT,"
		                + "thismemo VARCHAR(50) NOT NULL)"
		             	);
		
		System.out.println("diary memo_04 테이블 생성");
		stmt.execute("CREATE TABLE `diary`.`memo_04` ("
		                + "num INT PRIMARY KEY AUTO_INCREMENT,"
		                + "thismemo VARCHAR(50) NOT NULL)"
		             	);
		    
		System.out.println("diary name 테이블 생성");
		stmt.execute("CREATE TABLE `diary`.`name` ("
		                + "num INT PRIMARY KEY AUTO_INCREMENT,"
		                + "thisname VARCHAR(50) NOT NULL)"
		             	);
	     
	                
	    CommonFrame.infoMsg("셋팅 성공");
	               
	      
	}


	
}
