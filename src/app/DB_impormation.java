package app;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

//https://butgrin.tistory.com/70

public class DB_impormation extends JFrame implements ActionListener {
	
	JLabel l1,l2,l3,l4; //넘버, 제목, 내용, 날짜;
	JTextField tf1,tf2,tf3,tf4;
	JPanel panel;
	
	Object ob[][]=new Object[0][4];// 데이터 표시(행x) 열만 나오게 설정
	DefaultTableModel model; //데이터 저장부분
	JTable table;
	JScrollPane js;
	String str[]= {"번호","제목","내용","날짜"}; //컬럼명
	
	//DB연동
	Connection con=null;
	PreparedStatement pstmt=null; //SQL 구문을 실행
	ResultSet rs=null; //select 구문을 사용할 때 반드시 필요
	
	public DB_impormation() {
		super("테이블에 데이터 입력");
		panel=new JPanel();
		panel.setBackground(Color.black); // <->getBackground()
		//1)문자열 2)위치(left,center,right)
		//default left
		l1=new JLabel("번호",JLabel.LEFT);
		l2=new JLabel("제목",JLabel.CENTER);
		l3=new JLabel("내용",JLabel.CENTER);
		l4=new JLabel("날짜",JLabel.CENTER);
		
		tf1=new JTextField();
		tf2=new JTextField();
		tf3=new JTextField();
		tf4=new JTextField();
		//패널의 배치 - 3행 2열(GridLayout)
		panel.setLayout(new GridLayout(4,4)); //구조 변경
		panel.add(l1); panel.add(tf1); //번호
		panel.add(l2); panel.add(tf2); //제목
		panel.add(l3); panel.add(tf3); //내용
		panel.add(l4); panel.add(tf4); //날짜
		//JTable 가운데 배치
		model=new DefaultTableModel(ob, str); //1)데이터 저장[][], 2) 컬럼명
		table=new JTable(model); 
		js=new JScrollPane(table);
		this.add("Center", js);
			setBounds(250,250,300,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//이벤트 연결하는 코딩
		/*tf1.addActionListener(this);
		tf2.addActionListener(this);
		tf3.addActionListener(this);
		tf4.addActionListener(this);*/
		
		//DB 접속하여 select문장을 사용해 JTable에 보여주는 구문 코딩
		connect(); //DB접속하는 메소드
		select(); //select하는 메소드
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close();
					if(con!=null) con.close();
				}catch(Exception e2) { //설명
					System.exit(0);
				}
			}
		});
	}
	
	//connect()는 내부적으로 호출하기때문에 다른 클래스에서 호출하면 안됨
	private void connect() {
		try {
			// 1) 접속할 드라이버를 메모리에 올리기 : 정적 메소드
			//Class.forName("com.mysql.jdbc.Driver");
			String url ="jdbc:mysql://localhost/?allowLoadLocalInfile=true";
			con = DriverManager.getConnection(
	                url,
	                "root",
	                "1234");
			System.out.println("접속 : "+con);
		}catch(Exception e) {
			System.out.println("DB 접속 오류 : "+e);
		}
	}
	
	public void select() {
		 try {
			 String sql = "select * from diary.information";
			 var pstmt = con.prepareStatement(sql);
			 System.out.println("pstmt : "+pstmt);
			 rs = pstmt.executeQuery();
			 System.out.println("rs : "+rs);
			 
			 while(rs.next()) {
				 String num = rs.getString("num");
				 String title = rs.getString("title");
				 String content = rs.getString("content");	
				 String days = rs.getString("days");
				 
				 Object data[] = {num,title,content,days};
				 model.addRow(data);
				 System.out.println(num+", "+title+", "+content+", "+days);
			 }
		 }catch(Exception e) {
			 System.out.println("select() 실행 오류 : "+e);
			 
		 }
	 }
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==tf1) { //번호을 입력한 후 enter 친 경우
			tf2.requestFocus(); //커서입력
		}else if(e.getSource()==tf2) {
			tf3.requestFocus();
		}else if(e.getSource()==tf3) {
			tf4.requestFocus();
		}else if(e.getSource()==tf4) {
			
			if(tf1.getText().equals("") && tf2.getText().equals("") && tf3.getText().equals("") && tf4.getText().equals("") );
			 JOptionPane.showMessageDialog(this, "값을 입력하세요.");
			 tf1.requestFocus(); //넘버필드부터 입력할 수 있게 커서 이동
			 return;
		}
		//다 입력했다면 값을 모델에 데이터를 입력 : JTable에 화면 결과 출력
		// 형식) 모델객체명.addRow(저장할 값(배열));
		Object data[]= {tf1.getText(),tf1.getText(),tf1.getText(),tf1.getText()};
		model.addRow(data);
		//재입력을 위한 필드 초기화
		tf1.setText(""); tf2.setText(""); tf3.setText(""); tf4.setText("");
		tf1.requestFocus();//커서 이동
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DB_impormation();

	}

}
