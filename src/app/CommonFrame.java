// 출처 : https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=sks6624&logNo=150173231530
// 그리고 전공심화동아리
package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CommonFrame extends JFrame {
	//https://heather-hm.tistory.com/3
	static Connection con; //데이터베이스와 연결하는 객체
	static Statement stmt; //SQL문장을 실행하고 결과를 반환하는 기능들을 캡슐화한 인터페이스
	ResultSet rs;
	//어떤 생성자가 호출되든 그 전에 공통으로 초기화시키고 싶은 작업이 있다면 인스턴스 블록에서 처리
	//https://selfdevelope.tistory.com/576\
	//연결주소
	 static {
	      try {
	         con = DriverManager.getConnection("jdbc:mysql://localhost/diary", "root", "1234");
	         //데이터베이스로 SQL 문을 보내기 위한 SQLServerStatement 개체
	         stmt = con.createStatement();
	      } catch (SQLException e) { ////근원지를 찾아서 단계별로 에러를 출력한다
	         e.printStackTrace();
	      }
	   }
	 
	 //메세지
	 public static void errorMsg(String text) {
	      JOptionPane.showMessageDialog(null, text, "경고", JOptionPane.ERROR_MESSAGE);
	 }
	 
	 public static void infoMsg(String text) {
		 JOptionPane.showMessageDialog(null, text, "정보", JOptionPane.INFORMATION_MESSAGE);
	   }
	 
	 //데이터 업로드
	 public static ResultSet updateSQL(String sql, Object... paramter) {
	      try {
	         var pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	         
	         for (int i = 0; i < paramter.length; i++) {
	            pstmt.setObject(i + 1, paramter[i]);
	         }
	         
	         //INSER, UPDATE, DELETE 데이터 변경
	         pstmt.executeUpdate();
	         
	         return pstmt.getGeneratedKeys();
	         
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      return null;
	   }
	 
		 
	 
	 
	 public static void main(String[] args) {
		 
	 }
}
