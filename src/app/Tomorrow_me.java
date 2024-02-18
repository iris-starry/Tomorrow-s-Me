package app;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Tomorrow_me extends CommonFrame implements ActionListener {

   JLabel l1,l2,l3,l4; //넘버, 제목, 내용, 날짜;
   JTextField tf1,tf2,tf3,tf4;
   JPanel panel;

   Object ob[][]=new Object[0][4];// 데이터 표시(행x) 열만 나오게 설정
   DefaultTableModel model; //데이터 저장부분
   DefaultTableModel model_search; //검색 데이터
   JTable table;
   JScrollPane js;
   String str[]= {"번호","제목","내용","날짜"}; //컬럼명

   //DB연동
   Connection con=null;
   PreparedStatement pstmt=null; //SQL 구문을 실행
   ResultSet rs=null; //select 구문을 사용할 때 반드시 필요

   //홈라벨 
   JLabel con_01 = new JLabel();
   JLabel con_02 = new JLabel();
   JLabel con_03 = new JLabel();
   JLabel con_04 = new JLabel();
   JLabel con_05 = new JLabel();

   JLabel days_01 = new JLabel();
   JLabel days_02 = new JLabel();
   JLabel days_03 = new JLabel();
   JLabel days_04 = new JLabel();
   JLabel days_05 = new JLabel();
   
   //메모라벨
   JLabel JLmemo_01 = new JLabel();
   JLabel JLmemo_02 = new JLabel();
   JLabel JLmemo_03 = new JLabel();
   JLabel JLmemo_04 = new JLabel();
   
   //검색창
   JTextField tf_search= new JTextField();
   
   //이름 라벨
   JLabel JL_name = new JLabel();
   
   public Tomorrow_me() {
      setTitle("내일의 나"); //기본설정
      setSize(new Dimension(800, 730));
      setPreferredSize(new Dimension(800, 730));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x누르면 어떻게 할건지
      setLocationRelativeTo(null); //화면을 가운데로 띄우기
      setLayout(null);
      pack();
      //잠금화면
      JPanel homePanel = new JPanel();
      homePanel.setLayout(null);
      add(homePanel);
      
      //이름
      Label_name();
      JL_name.setBounds(140, 63, 100, 50);
      JL_name.setFont(new Font("굴림", Font.PLAIN, 30));
      homePanel.add(JL_name);
      
      //홈화면으로 넘어가기
      JButton bt_home = new JButton();
      bt_home.setBounds(560, 530, 100, 60);
      bt_home.setIcon(new ImageIcon("./images/Coverback.png"));
      bt_home.setBorderPainted(false);
      homePanel.add(bt_home);

      bt_home.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           homePanel.setVisible(false);
         //패널생성
           JPanel mainPanel = new JPanel(null);

           JPanel mainPanelunder = new JPanel(null);

           //작성
           JButton bt_fill_in = new JButton();
           bt_fill_in.setBounds(20, 30, 100, 80);
           bt_fill_in.setIcon(new ImageIcon("./images/bt_fill_in.png"));
           bt_fill_in.setBorderPainted(false);
           mainPanel.add(bt_fill_in);

           //목록
           JButton bt_list = new JButton();
           bt_list.setBounds(140, 30, 100, 80);
           bt_list.setIcon(new ImageIcon("./images/bt_list.png"));
           bt_list.setBorderPainted(false);
           mainPanel.add(bt_list);

           //메모
           JButton bt_memo = new JButton();
           bt_memo.setBounds(550, 30, 100, 80);
           bt_memo.setIcon(new ImageIcon("./images/bt_memo.png"));
           bt_memo.setBorderPainted(false);
           mainPanel.add(bt_memo);
           
           //설정
           JButton bt_help = new JButton();
           bt_help.setBounds(670, 30, 100, 80);
           bt_help.setIcon(new ImageIcon("./images/bt_setting.png"));
           bt_help.setBorderPainted(false);
           mainPanel.add(bt_help);

           //홈화면 일기
           connect(); //db연결
           
           //1번 라벨
           Labeldata01();
           con_01.setBounds(140, 63, 550, 50);
           con_01.setFont(new Font("굴림", Font.PLAIN, 30));
           days_01.setBounds(480, 22, 200, 50);
           days_01.setFont(new Font("고딕", Font.PLAIN, 15));
           
           //2번 라벨
           Labeldata02();
           con_02.setBounds(140, 200, 550, 50);
           con_02.setFont(new Font("굴림", Font.PLAIN, 30));
           days_02.setBounds(480, 160, 200, 50);
           days_02.setFont(new Font("고딕", Font.PLAIN, 15));
           
           //3라벨
           Labeldata03();
           con_03.setBounds(140, 335, 550, 50);
           con_03.setFont(new Font("굴림", Font.PLAIN, 30));
           days_03.setBounds(480, 295, 200, 50);
           days_03.setFont(new Font("고딕", Font.PLAIN, 15));
           
           //4라벨
           Labeldata04();
           con_04.setBounds(140, 470, 550, 50);
           con_04.setFont(new Font("굴림", Font.PLAIN, 30));
           days_04.setBounds(480, 430, 200, 50);
           days_04.setFont(new Font("고딕", Font.PLAIN, 15));
           
           
           
           //라벨추가
           mainPanelunder.add(con_01);  mainPanelunder.add(days_01); //첫번째, 두번째 ...
           mainPanelunder.add(con_02);  mainPanelunder.add(days_02);
           mainPanelunder.add(con_03);  mainPanelunder.add(days_03);
           mainPanelunder.add(con_04);  mainPanelunder.add(days_04);


           //above Image
           ImagePanel img = new ImagePanel(new ImageIcon("./images/main.png").getImage());
           mainPanel.add(img);

           //under Image
           ImagePanel img2 = new ImagePanel(new ImageIcon("./images/main02.png").getImage());
           mainPanelunder.add(img2);

           mainPanel.setBounds(0, 0, 800, 120);
           mainPanel.setVisible(true);
           add(mainPanel);

           mainPanelunder.setBounds(0, 120, 800, 600);
           mainPanelunder.setVisible(true);
           add(mainPanelunder);

           //diary home
           //폰트 https://m.blog.naver.com/10hsb04/221607286384
           JLabel content_01 = new JLabel();
           content_01.setText("안녕");
           content_01.setBounds(0, 0, 200, 100);
           content_01.setFont(new Font("굴림", Font.PLAIN, 30));
           mainPanel.add(content_01);


           //작성 버튼 액션
           bt_fill_in.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
            	 //다른 화면은 안보이기!
            	 mainPanel.setVisible(false);
            	 mainPanelunder.setVisible(false);
            	 //writePanel
                 JPanel writePanel = new JPanel();
                 writePanel.setBounds(0, 0, 800, 700);
                 writePanel.setLayout(null);
                 writePanel.setVisible(true);
                 add(writePanel);
                 
                                                                                                                                              
                 //1번 라벨
                 //뒤로가기 버튼
                 JButton bt_writePanel_back = new JButton();
                 bt_writePanel_back.setBounds(30, 50, 60, 60);
                 bt_writePanel_back.setIcon(new ImageIcon("./images/bt_fill_in_back.png"));
                 bt_writePanel_back.setBorderPainted(false);
                 writePanel.add(bt_writePanel_back);

                 bt_writePanel_back.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       writePanel.setVisible(false);
                       mainPanel.setVisible(true);
                       mainPanelunder.setVisible(true);
                    }
                 });

                 //완료 버튼
                 JButton bt_finish = new JButton();
                 bt_finish.setBounds(530, 590, 120, 60);
                 bt_finish.setIcon(new ImageIcon("./images/bt_fill_in_fnish.png"));
                 bt_finish.setBorderPainted(false);
                 bt_finish.setBorderPainted(false);
                 writePanel.add(bt_finish);

                 //텍스트
                 //title
                 TextField tf_title = new TextField(20);
                 tf_title.setBounds(245, 140, 360, 55);
                 tf_title.setFont(new Font("함초롬돋움", Font.PLAIN, 35));
                 writePanel.add(tf_title);

                 //내용 
                 TextArea tf_content = new TextArea(0,0);
                 tf_content.setBounds(210, 250, 430, 290);
                 tf_content.setFont(new Font("함초롬돋움", Font.PLAIN, 35));
                 writePanel.add(tf_content);

                 
                 //현재시간 
                 LocalDateTime now = LocalDateTime.now();
                 JLabel nowtime = new JLabel(now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")));

                 //완료버튼 데이터 넣기
                 bt_finish.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       
                       try {
                          updateSQL("INSERT INTO information (num, title, content, days)"
                                + "   VALUES ( (SELECT IFNULL( MAX(i2.num), 0) + 1 FROM information i2) , ?, ?, ?)",
                                tf_title.getText(),
                                tf_content.getText(),
                                nowtime.getText()
                                );

                          infoMsg("등록되었습니다.");
                       }catch (Exception e1) {
                          e1.printStackTrace();
                       }
                       writePanel.setVisible(false);
                       mainPanel.setVisible(true);
                       mainPanelunder.setVisible(true);
                       
                       Labeldata01();
                       Labeldata02();
                       Labeldata03();
                       Labeldata04();

                    }
                 });//완료버튼액션
                 
                 //배경이미지
                 ImagePanel imgwritepanel = new ImagePanel(new ImageIcon("./images/fill_in-001.png").getImage());
                 writePanel.add(imgwritepanel);
              }

           });//작성액션 끝

           //목록 버튼 액션
           bt_list.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
            	 //다른화면은 안보이기!
            	 mainPanel.setVisible(false);
            	 mainPanelunder.setVisible(false);
            	 //listPanel
                 JPanel listPanel = new JPanel();
                 listPanel.setBounds(0, 0, 800, 700);
                 listPanel.setLayout(null);
                 listPanel.setVisible(true);
                 add(listPanel);
                 
                 //뒤로가기 버튼
                 JButton bt_list_back = new JButton();
                 bt_list_back.setBounds(35, 130, 60, 60);
                 bt_list_back.setIcon(new ImageIcon("./images/list_back.png"));
                 bt_list_back.setBorderPainted(false);
                 listPanel.add(bt_list_back);

                 bt_list_back.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       listPanel.setVisible(false);
                       mainPanel.setVisible(true);
                       mainPanelunder.setVisible(true);
                    }
                 });


                 //목록 테이블
                 //https://butgrin.tistory.com/70 테이블
                 //https://blackas119.tistory.com/2 수정
                 l1=new JLabel("번호",JLabel.LEFT);
                 l2=new JLabel("제목",JLabel.CENTER);
                 l3=new JLabel("내용",JLabel.CENTER);
                 l4=new JLabel("날짜",JLabel.CENTER);

                 tf1=new JTextField();
                 tf2=new JTextField();
                 tf3=new JTextField();
                 tf4=new JTextField();

                 listPanel.add(l1); listPanel.add(tf1); //번호
                 listPanel.add(l2); listPanel.add(tf2); //제목
                 listPanel.add(l3); listPanel.add(tf3); //내용
                 listPanel.add(l4); listPanel.add(tf4); //날짜

                 //수정못하는 모델만들기 셀수정 불가
                 model=new DefaultTableModel(ob, str) {
                    //1)데이터 저장[][], 2) 컬럼명
                    public boolean isCellEditable(int row, int column) {
                       return false;
                    }
                 };

                 table=new JTable(model); 


                 table.getTableHeader().setResizingAllowed(false); //컬럼사이즈 고
                 table.getTableHeader().setReorderingAllowed(false); //이동불가

                 js=new JScrollPane(table);
                 js.setBounds(200,180,465,430); //테이블크기
                 js.setVisible(true);
                 listPanel.add("Center", js);

                 connect(); //DB접속하는 메소드
                 select(); //select하는 메소드
                 
                 //검색창
                 //전역변수
                 tf_search.setBounds(483, 135, 120, 32);
                 tf_search.setFont(new Font("함초롬돋움", Font.PLAIN, 10));
                 listPanel.add(tf_search);

                 //검색버튼
                 JButton bt_search= new JButton();
                 bt_search.setBounds(603, 135, 60, 32);
                 bt_search.setIcon(new ImageIcon("./images/bt_search.png"));
                 listPanel.add(bt_search);
                 
                 
                 //검색버튼 액션
                 bt_search.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	
                    	l1=new JLabel("번호",JLabel.LEFT);
                        l2=new JLabel("제목",JLabel.CENTER);
                        l3=new JLabel("내용",JLabel.CENTER);
                        l4=new JLabel("날짜",JLabel.CENTER);

                        tf1=new JTextField();
                        tf2=new JTextField();
                        tf3=new JTextField();
                        tf4=new JTextField();

                        listPanel.add(l1); listPanel.add(tf1); //번호
                        listPanel.add(l2); listPanel.add(tf2); //제목
                        listPanel.add(l3); listPanel.add(tf3); //내용
                        listPanel.add(l4); listPanel.add(tf4); //날짜

                        //수정못하는 모델만들기 셀수정 불가
                        model_search=new DefaultTableModel(ob, str) {
                           //1)데이터 저장[][], 2) 컬럼명
                           public boolean isCellEditable(int row, int column) {
                              return false;
                           }
                        };

                        table=new JTable(model_search); 


                        table.getTableHeader().setResizingAllowed(false); //컬럼사이즈 고
                        table.getTableHeader().setReorderingAllowed(false); //이동불가

                        js=new JScrollPane(table);
                        js.setBounds(200,180,465,430); //테이블크기
                        js.setVisible(true);
                        listPanel.add("Center", js);

                        connect(); //DB접속하는 메소드
                        search_select(); //select하는 메소드
                    }
                 });
                 
                 
                 //수정버튼
                 JButton bt_revise= new JButton();
                 bt_revise.setBounds(220, 38, 50, 60);
                 bt_revise.setIcon(new ImageIcon("./images/bt_revise.png"));
                 bt_revise.setBorderPainted(false);
                 listPanel.add(bt_revise);
                

                 //수정 패널
                 bt_revise.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       JPanel revisePanel = new JPanel();
                       revisePanel.setLayout(null);
                       revisePanel.setBounds(0, 0, 800, 700);
                       revisePanel.setVisible(true);
                       listPanel.setVisible(false);
                       add(revisePanel);
                       
                       //수정 뒤로가기 버튼
                       JButton bt_back = new JButton();
                       bt_back.setBounds(30, 130, 60, 60);
                       bt_back.setIcon(new ImageIcon("./images/bt_revise_back.png"));
                       bt_back.setBorderPainted(false);
                       revisePanel.add(bt_back);
                       
                       //뒤로가기 액션
                       bt_back.addActionListener(new ActionListener() {
                          public void actionPerformed(ActionEvent e) {
                             revisePanel.setVisible(false);
                             mainPanel.setVisible(false);
                             mainPanelunder.setVisible(false);
                             listPanel.setVisible(true);
                          }
                       });

                       l1=new JLabel("번호",JLabel.LEFT);
                       l2=new JLabel("제목",JLabel.CENTER);
                       l3=new JLabel("내용",JLabel.CENTER);
                       l4=new JLabel("날짜",JLabel.CENTER);

                       tf1=new JTextField();
                       tf2=new JTextField();
                       tf3=new JTextField();
                       tf4=new JTextField();

                       revisePanel.add(l1); revisePanel.add(tf1); //번호
                       revisePanel.add(l2); revisePanel.add(tf2); //제목
                       revisePanel.add(l3); revisePanel.add(tf3); //내용
                       revisePanel.add(l4); revisePanel.add(tf4); //날짜


                       model=new DefaultTableModel(ob, str);
                       model.fireTableDataChanged();

                       table=new JTable(model); 

                       table.getTableHeader().setResizingAllowed(false); //컬럼사이즈 고
                       table.getTableHeader().setReorderingAllowed(false); //이동불가

                       js=new JScrollPane(table);
                       js.setBounds(220,190,460,410);
                       js.setVisible(true);
                       revisePanel.add("Center", js);

                       connect(); //DB접속하는 메소드
                       select(); //select하는 메소드

                       //삭제버튼
                       JButton bt_deletion= new JButton();
                       bt_deletion.setBounds(220, 150, 60, 32);
                       bt_deletion.setIcon(new ImageIcon("./images/revisePane_deletion.png"));
                       bt_deletion.setBorderPainted(false);
                       revisePanel.add(bt_deletion);
            
                       //삭제액션
                       bt_deletion.addActionListener(new ActionListener() {
                          public void actionPerformed(ActionEvent e) {
                             if(table.getSelectedRow() == -1) {
                                return;
                             }
                             else {
                                model.removeRow(table.getSelectedRow());
                             }
                          }
                       });

                       //저장버튼
                       JButton bt_Saving= new JButton();
                       bt_Saving.setBounds(290, 150, 60, 32);
                       bt_Saving.setIcon(new ImageIcon("./images/revisePane_Storage.png"));
                       bt_Saving.setBorderPainted(false);
                       revisePanel.add(bt_Saving);
                       //https://imhotk.tistory.com/18
                       //model.fireTableDataChanged();

                       bt_Saving.addActionListener(new ActionListener() {
                          public void actionPerformed(ActionEvent e) {
                             try {
                                updateSQL("DELETE FROM diary.information");
                                model.getRowCount();
                                
                                for (int i = 0; i < model.getRowCount(); i++) {
									updateSQL("INSERT INTO diary.information VALUES(?, ?, ?, ?)", 
											i+1,
											model.getValueAt(i, 1),
											model.getValueAt(i, 2),
											model.getValueAt(i, 3)
											);
								}
                               
                                
                                infoMsg("저장되었습니다.");
                                
                                
                             }catch (Exception e1) {
                                e1.printStackTrace();
                             }finally{
                                try {
                                   pstmt.close();
                                   con.close();
                                } catch (Exception e2) {}
                             }
                          }
                       });


                       //수정패널 배경화면
                       ImagePanel imgrevisePanel = new ImagePanel(new ImageIcon("./images/list02.png").getImage());
                       revisePanel.add(imgrevisePanel);

                       revisePanel.add(imgrevisePanel);

                      



                    }
                 });


                
                 
                 //목록배경이미지
                 ImagePanel imglist = new ImagePanel(new ImageIcon("./images/list.png").getImage());
                 listPanel.add(imglist);


              }
           });//목록액션 끝
           
           //메모 액션
           bt_memo.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  mainPanel.setVisible(false);
                  mainPanelunder.setVisible(false);
                  
                  //메모액션 속 패널 ㅎㅎ
                  JPanel memoPanel = new JPanel();
                  memoPanel.setLayout(null);
                  memoPanel.setBounds(0, 0, 800, 700);
                  memoPanel.setVisible(true);
                  add(memoPanel);
                  
                  //메모 뒤로가기 버튼
                  JButton bt_back = new JButton();
                  bt_back.setBounds(15, 50, 80, 80);
                  bt_back.setIcon(new ImageIcon("./images/memo_back.png"));
                  bt_back.setBorderPainted(false);
                  memoPanel.add(bt_back);
                  
                  //메모뒤로가기 액션
                  bt_back.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                    	memoPanel.setVisible(false);
                        mainPanel.setVisible(true);
                        mainPanelunder.setVisible(true);
                        
                        
                     }
                  });
                                            
                  //메모라벨
                  //홈화면 일기
                  //connect(); //db연결
                  
                  //1번 라벨
                  Labelmemo_01();
                  JLmemo_01 .setBounds(150, 63, 100, 50);
                  JLmemo_01 .setFont(new Font("굴림", Font.PLAIN, 20));
                  memoPanel.add(JLmemo_01);
                  
                  //2번 라벨
                  Labelmemo_02();
                  JLmemo_02 .setBounds(200, 63, 100, 50);
                  JLmemo_02 .setFont(new Font("굴림", Font.PLAIN, 20));
                  memoPanel.add(JLmemo_02);
                  
                  //3번 라벨
                  Labelmemo_03();
                  JLmemo_03 .setBounds(140, 63, 550, 50);
                  JLmemo_03 .setFont(new Font("굴림", Font.PLAIN, 30));
                  memoPanel.add(JLmemo_03);
                  
                  //4번 라벨
                  Labelmemo_04();
                  JLmemo_04 .setBounds(140, 63, 550, 50);
                  JLmemo_04 .setFont(new Font("굴림", Font.PLAIN, 30));
                  memoPanel.add(JLmemo_04);
                  
                  
                  //01메모지수정버튼
                  JButton bt_memo01 = new JButton();
                  bt_memo01.setBounds(345, 310, 80, 50);
                  bt_memo01.setIcon(new ImageIcon("./images/memo_01.png"));
                  bt_memo01.setBorderPainted(false);
                  memoPanel.add(bt_memo01);
                     
                  
                  //01메모지수정 버튼
                  bt_memo01.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                    	memoPanel.setVisible(false);
               
                    	//메모지수정패널
                        JPanel memos01_Panel = new JPanel();
                        memos01_Panel.setLayout(null);
                        memos01_Panel.setBounds(0, 0, 800, 700);
                        memos01_Panel.setVisible(true);
                        add(memos01_Panel);
                        
                        //메모 뒤로가기 버튼
                        JButton bt_memos01_back = new JButton();
                        bt_memos01_back.setBounds(30, 50, 80, 80);
                        bt_memos01_back.setIcon(new ImageIcon("./images/memo_01_back.png"));
                        bt_memos01_back.setBorderPainted(false);
                        memos01_Panel.add(bt_back);
                        
                        //메모지수정뒤로가기 액션
                        bt_back.addActionListener(new ActionListener() {
                           public void actionPerformed(ActionEvent e) {
                        	 memos01_Panel.setVisible(false);
                        	 memoPanel.setVisible(true);
                              
                              
                           }
                        });
                        
                        //내용 01
                        TextArea tf_memo01 = new TextArea(0,0);
                        tf_memo01.setBounds(150, 180, 500, 400);
                        tf_memo01.setFont(new Font("함초롬돋움", Font.PLAIN, 35));
                        memos01_Panel.add(tf_memo01);
                        
                        //메모 완료버튼
                        JButton bt_memo_01_fnish = new JButton();
                        bt_memo_01_fnish.setBounds(670, 550, 100, 80);
                        bt_memo_01_fnish.setIcon(new ImageIcon("./images/memo_01_fnish.png"));
                        bt_memo_01_fnish.setBorderPainted(false);
                        memos01_Panel.add(bt_memo_01_fnish);
                        
                       
                        //01메모 완료 버튼
                        bt_memo_01_fnish.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                         	   memoPanel.setVisible(true);
                               mainPanel.setVisible(false);
                               mainPanelunder.setVisible(false);
                               memos01_Panel.setVisible(false);
                               
                               try {
                            	   updateSQL("DELETE FROM diary.memo_01;");
                                   updateSQL("INSERT INTO memo_01 (num, thismemo)"
                                         + "   VALUES (?, ?)",
                                         1,
                                         tf_memo01.getText()
                                         );

                                   infoMsg("완료되었습니다.");
                                }catch (Exception e1) {
                                   e1.printStackTrace();
                                }
                               
                            }
                         });
                        
                        //목록배경이미지
                        ImagePanel imgmemos01 = new ImagePanel(new ImageIcon("./images/memo_s01.png").getImage());
                        memos01_Panel.add(imgmemos01);
                     }
                  });

                  
                  //02메모지 수정버튼
                  JButton bt_memo02 = new JButton();
                  bt_memo02.setBounds(515, 265, 50, 50);
                  bt_memo02.setIcon(new ImageIcon("./images/memo_02.png"));
                  bt_memo02.setBorderPainted(false);
                  memoPanel.add(bt_memo02);
                  
                  //01메모지수정 버튼
                  bt_memo02.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                    	memoPanel.setVisible(false);
               
                    	//메모지수정패널
                        JPanel memos02_Panel = new JPanel();
                        memos02_Panel.setLayout(null);
                        memos02_Panel.setBounds(0, 0, 800, 700);
                        memos02_Panel.setVisible(true);
                        add(memos02_Panel);
                        
                        //메모 뒤로가기 버튼
                        JButton bt_memos01_back = new JButton();
                        bt_memos01_back.setBounds(30, 50, 80, 80);
                        bt_memos01_back.setIcon(new ImageIcon("./images/memo_02_back.png"));
                        bt_memos01_back.setBorderPainted(false);
                        memos02_Panel.add(bt_back);
                        
                        //메모지수정뒤로가기 액션
                        bt_back.addActionListener(new ActionListener() {
                           public void actionPerformed(ActionEvent e) {
                        	 memoPanel.setVisible(true);
                          	 memos02_Panel.setVisible(false);
                              
                              
                           }
                        });
                        
                        //내용 02
                        TextArea tf_memo02 = new TextArea(0,0);
                        tf_memo02.setBounds(150, 180, 500, 400);
                        tf_memo02.setFont(new Font("함초롬돋움", Font.PLAIN, 35));
                        memos02_Panel.add(tf_memo02);
                        
                        //메모 완료버튼
                        JButton bt_memo_02_fnish = new JButton();
                        bt_memo_02_fnish.setBounds(670, 550, 100, 80);
                        bt_memo_02_fnish.setIcon(new ImageIcon("./images/memo_02_fnish.png"));
                        bt_memo_02_fnish.setBorderPainted(false);
                        memos02_Panel.add(bt_memo_02_fnish);
                        
                       
                        //01메모 완료 버튼
                        bt_memo_02_fnish.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                         	   memoPanel.setVisible(true);
                               mainPanel.setVisible(false);
                               mainPanelunder.setVisible(false);
                               memos02_Panel.setVisible(false);
                               
                               try {
                            	   updateSQL("DELETE FROM diary.memo_02;");
                                   updateSQL("INSERT INTO memo_02 (num, thismemo)"
                                         + "   VALUES (?, ?)",
                                         1,
                                         tf_memo02.getText()
                                         );

                                   infoMsg("완료되었습니다.");
                                }catch (Exception e1) {
                                   e1.printStackTrace();
                                }
                               
                            }
                         });
                        
                        //목록배경이미지
                        ImagePanel imgmemos02 = new ImagePanel(new ImageIcon("./images/memo_s02.png").getImage());
                        memos02_Panel.add(imgmemos02);
                     }
                  });
                  
                  //03메모지
                  JButton bt_memo03 = new JButton();
                  bt_memo03.setBounds(100, 614, 120, 40);
                  bt_memo03.setIcon(new ImageIcon("./images/memo_04.png"));
                  bt_memo03.setBorderPainted(false);
                  memoPanel.add(bt_memo03);
                  
                  bt_memo03.addActionListener(new ActionListener() {
                      public void actionPerformed(ActionEvent e) {
                     	memoPanel.setVisible(false);
                
                     	//메모지수정패널
                         JPanel memos03_Panel = new JPanel();
                         memos03_Panel.setLayout(null);
                         memos03_Panel.setBounds(0, 0, 800, 700);
                         memos03_Panel.setVisible(true);
                         add(memos03_Panel);
                         
                         //메모 뒤로가기 버튼
                         JButton bt_memos01_back = new JButton();
                         bt_memos01_back.setBounds(30, 50, 80, 80);
                         bt_memos01_back.setIcon(new ImageIcon("./images/memo_03_back.png"));
                         bt_memos01_back.setBorderPainted(false);
                         memos03_Panel.add(bt_back);
                         
                         //메모지수정뒤로가기 액션
                         bt_back.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                         	 memos03_Panel.setVisible(false);
                         	 memoPanel.setVisible(true);
                               
                               
                            }
                         });
                         
                         //내용 03
                         TextArea tf_memo03 = new TextArea(0,0);
                         tf_memo03.setBounds(150, 180, 500, 400);
                         tf_memo03.setFont(new Font("함초롬돋움", Font.PLAIN, 35));
                         memos03_Panel.add(tf_memo03);
                         
                         //메모 완료버튼
                         JButton bt_memo_01_fnish = new JButton();
                         bt_memo_01_fnish.setBounds(670, 550, 100, 80);
                         bt_memo_01_fnish.setIcon(new ImageIcon("./images/memo_01_fnish.png"));
                         bt_memo_01_fnish.setBorderPainted(false);
                         memos03_Panel.add(bt_memo_01_fnish);
                         
                        
                         //01메모 완료 버튼
                         bt_memo_01_fnish.addActionListener(new ActionListener() {
                             public void actionPerformed(ActionEvent e) {
                          	   memoPanel.setVisible(true);
                                mainPanel.setVisible(false);
                                mainPanelunder.setVisible(false);
                                memos03_Panel.setVisible(false);
                                
                                try {
                             	   updateSQL("DELETE FROM diary.memo_03;");
                                    updateSQL("INSERT INTO memo_03 (num, thismemo)"
                                          + "   VALUES (?, ?)",
                                          1,
                                          tf_memo03.getText()
                                          );

                                    infoMsg("완료되었습니다.");
                                 }catch (Exception e1) {
                                    e1.printStackTrace();
                                 }
                                
                             }
                          });
                         
                         //목록배경이미지
                         ImagePanel imgmemos01 = new ImagePanel(new ImageIcon("./images/memo_s01.png").getImage());
                         memos03_Panel.add(imgmemos01);
                      }
                   });
                  
                  //04메모지
                  JButton bt_memo04 = new JButton();
                  bt_memo04.setBounds(420, 585, 100, 50);
                  bt_memo04.setIcon(new ImageIcon("./images/memo_03.png"));
                  bt_memo04.setBorderPainted(false);
                  memoPanel.add(bt_memo04);
                  bt_memo01.addActionListener(new ActionListener() {
                      public void actionPerformed(ActionEvent e) {
                     	memoPanel.setVisible(false);
                
                     	//메모지수정패널
                         JPanel memos04_Panel = new JPanel();
                         memos04_Panel.setLayout(null);
                         memos04_Panel.setBounds(0, 0, 800, 700);
                         memos04_Panel.setVisible(true);
                         add(memos04_Panel);
                         
                         //메모 뒤로가기 버튼
                         JButton bt_memos01_back = new JButton();
                         bt_memos01_back.setBounds(30, 50, 80, 80);
                         bt_memos01_back.setIcon(new ImageIcon("./images/memo_01_back.png"));
                         bt_memos01_back.setBorderPainted(false);
                         memos04_Panel.add(bt_back);
                         
                         //메모지수정뒤로가기 액션
                         bt_back.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                         	 memos04_Panel.setVisible(false);
                         	 memoPanel.setVisible(true);
                               
                               
                            }
                         });
                         
                         //내용 01
                         TextArea tf_memo04 = new TextArea(0,0);
                         tf_memo04.setBounds(150, 180, 500, 400);
                         tf_memo04.setFont(new Font("함초롬돋움", Font.PLAIN, 35));
                         memos04_Panel.add(tf_memo04);
                         
                         //메모 완료버튼
                         JButton bt_memo_01_fnish = new JButton();
                         bt_memo_01_fnish.setBounds(670, 550, 100, 80);
                         bt_memo_01_fnish.setIcon(new ImageIcon("./images/memo_01_fnish.png"));
                         bt_memo_01_fnish.setBorderPainted(false);
                         memos04_Panel.add(bt_memo_01_fnish);
                         
                        
                         //01메모 완료 버튼
                         bt_memo_01_fnish.addActionListener(new ActionListener() {
                             public void actionPerformed(ActionEvent e) {
                          	   memoPanel.setVisible(true);
                                mainPanel.setVisible(false);
                                mainPanelunder.setVisible(false);
                                memos04_Panel.setVisible(false);
                                
                                try {
                             	   updateSQL("DELETE FROM diary.memo_04;");
                                    updateSQL("INSERT INTO memo_04 (num, thismemo)"
                                          + "   VALUES (?, ?)",
                                          1,
                                          tf_memo04.getText()
                                          );

                                    infoMsg("완료되었습니다.");
                                 }catch (Exception e1) {
                                    e1.printStackTrace();
                                 }
                                
                             }
                          });
                         
                         //목록배경이미지
                         ImagePanel imgmemos01 = new ImagePanel(new ImageIcon("./images/memo_s01.png").getImage());
                         memos04_Panel.add(imgmemos01);
                      }
                   });
                  
                  //메모배경이미지
                  ImagePanel imgmemo = new ImagePanel(new ImageIcon("./images/memo.png").getImage());
                  memoPanel.add(imgmemo);
                  
               }
            });
           
           //설정액션
           bt_help.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 JPanel hepanel = new JPanel();
                 hepanel.setLayout(null);
                 mainPanel.setVisible(false);
                 mainPanelunder.setVisible(false);
                 hepanel.setBounds(0, 0, 800, 700);
                 hepanel.setVisible(true);

                 //뒤로가기 버튼
                 JButton bt_back = new JButton();
                 bt_back.setBounds(50, 60, 80, 80);
                 bt_back.setIcon(new ImageIcon("./images/setting_back.png"));
                 bt_back.setBorderPainted(false);
                 hepanel.add(bt_back);

                 bt_back.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       hepanel.setVisible(false);
                       mainPanel.setVisible(true);
                       mainPanelunder.setVisible(true);
                    }
                 });
                 
                 //이름
                 connect();
                 Label_name();
                 JL_name.setBounds(600, 130, 100, 30);
                 JL_name.setFont(new Font("굴림", Font.PLAIN, 20));
                 hepanel.add(JL_name);
                 
                 //이름 창
                 TextField tf_name = new TextField(10);
                 tf_name.setBounds(570, 100, 60, 30);
                 tf_name.setFont(new Font("함초롬돋움", Font.PLAIN, 15));
                 hepanel.add(tf_name);
                
                 
                 //홈버튼 
                 JButton bt_home= new JButton();
                 bt_home.setBounds(630, 550, 80, 80);
                 bt_home.setIcon(new ImageIcon("./images/setting_home.png"));
                 bt_home.setBorderPainted(false);
                 hepanel.add(bt_home);
                 
                 bt_home.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                        hepanel.setVisible(false);
                        homePanel.setVisible(true);
                        
                     }
                  });
                 
                 //이름 추가
                 JButton bt_name= new JButton("이름변경");
                 bt_name.setBounds(630, 100, 90, 30);
                 hepanel.add(bt_name);
                 
                
                 //이름변경
                 bt_name.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                        
                        
                        try {
                     	   updateSQL("DELETE FROM diary.name;");
                            updateSQL("INSERT INTO name (num, thisname)"
                                  + "   VALUES (?, ?)",
                                  1,
                                  tf_name.getText()
                                  );

                            infoMsg("완료되었습니다.");
                         }catch (Exception e1) {
                            e1.printStackTrace();
                         }
                        
                     }
                  });



                 //배경이미지
                 ImagePanel imgsetting = new ImagePanel(new ImageIcon("./images/setting.png").getImage());
                 hepanel.add(imgsetting);
                 add(hepanel);
              }
           });//도움말 끝
         }
      });
      //homePanel 배경화면
      ImagePanel imghome = new ImagePanel(new ImageIcon("./images/Cover.png").getImage());
      homePanel.add(imghome);
      homePanel.setBounds(0, 0, 800, 730);
      homePanel.setVisible(true);
      

     
   }
   //db연결
   private void connect() {
      try {
         // 1) 접속할 드라이버를 메모리에 올리기 : 정적 메소드
         //Class.forName("com.mysql.jdbc.Driver");
         String url ="jdbc:mysql://localhost/?allowLoadLocalInfile=true";
         con = DriverManager.getConnection(
               url,
               "root",
               "1234");
         //System.out.println("접속 : "+con);
      }catch(Exception e) {
         System.out.println("DB 접속 오류 : "+e);
      }
   }
   //table 데이터 저장
   public void select() {
      try {
         String sql = "SELECT * FROM diary.information ORDER BY days DESC;";
         var pstmt = con.prepareStatement(sql);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            String num = rs.getString("num");
            String title = rs.getString("title");
            String content = rs.getString("content");   
            String days = rs.getString("days");

            Object data[] = {num,title,content,days};
            model.addRow(data);
         }
      }catch(Exception e) {
         System.out.println("select() 실행 오류 : "+e);

      }
   }
   
   public void search_select() {
	      try {
	         String sql = "SELECT * FROM diary.information WHERE num LIKE '%"+tf_search.getText()+"%' OR title LIKE '%"+tf_search.getText()+"%' OR content LIKE '%"+tf_search.getText()+"%' OR days LIKE '%"+tf_search.getText()+"%' ";
	         var pstmt = con.prepareStatement(sql);
	         rs = pstmt.executeQuery();

	         while(rs.next()) {
	            String num = rs.getString("num");
	            String title = rs.getString("title");
	            String content = rs.getString("content");   
	            String days = rs.getString("days");

	            Object data[] = {num,title,content,days};
	            model_search.addRow(data);
	         }
	      }catch(Exception e) {
	         System.out.println("search_select() 실행 오류 : "+e);

	      }
	   }
   
   
   
   //Label 메모 데이터 저장
   public void Labeldata01() {
      try {
         String sql = "SELECT * FROM diary.information where num=1;";
         var pstmt = con.prepareStatement(sql);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            String content = rs.getString("content");   
            String days = rs.getString("days");

            con_01.setText(content);
            days_01.setText(days);
         }
      }catch(Exception e) {
         System.out.println("Labeldata() 실행 오류 : "+e);

      }
   }
   
   public void Labeldata02() {
      try {
         String sql = "SELECT * FROM diary.information where num=2;";
         var pstmt = con.prepareStatement(sql);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            String content = rs.getString("content");   
            String days = rs.getString("days");

            con_02.setText(content);
            days_02.setText(days);
         }
      }catch(Exception e) {
         System.out.println("Labeldata() 실행 오류 : "+e);

      }
   }
   
   public void Labeldata03() {
      try {
         String sql = "SELECT * FROM diary.information where num=3;";
         var pstmt = con.prepareStatement(sql);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            String content = rs.getString("content");   
            String days = rs.getString("days");

            con_03.setText(content);
            days_03.setText(days);
         }
      }catch(Exception e) {
         System.out.println("Labeldata() 실행 오류 : "+e);

      }
   }
   
   public void Labeldata04() {
      try {
         String sql = "SELECT * FROM diary.information where num=4;";
         var pstmt = con.prepareStatement(sql);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            String content = rs.getString("content");   
            String days = rs.getString("days");

            con_04.setText(content);
            days_04.setText(days);
         }
      }catch(Exception e) {
         System.out.println("Labeldata() 실행 오류 : "+e);

      }
   }
   
   //메모지 내용 저장
   public void Labelmemo_01() {
	      try {
	         String sql = "SELECT * FROM diary.memo_01 where num=1;";
	         var pstmt = con.prepareStatement(sql);
	         rs = pstmt.executeQuery();

	         while(rs.next()) {
	            String thismemo = rs.getString("thismemo");   

	            JLmemo_01.setText(thismemo);
	         }
	      }catch(Exception e) {
	         System.out.println("Labeldata() 실행 오류 : "+e);

	      }
	   }
   
   public void Labelmemo_02() {
	      try {
	         String sql = "SELECT * FROM diary.memo_02 where num=1;";
	         var pstmt = con.prepareStatement(sql);
	         rs = pstmt.executeQuery();

	         while(rs.next()) {
	            String thismemo = rs.getString("thismemo");   

	            JLmemo_02.setText(thismemo);
	         }
	      }catch(Exception e) {
	         System.out.println("Labeldata() 실행 오류 : "+e);

	      }
	   }
   
   public void Labelmemo_03() {
	      try {
	         String sql = "SELECT * FROM diary.memo_03 where num=1;";
	         var pstmt = con.prepareStatement(sql);
	         rs = pstmt.executeQuery();

	         while(rs.next()) {
	            String thismemo = rs.getString("thismemo");   

	            JLmemo_03.setText(thismemo);
	         }
	      }catch(Exception e) {
	         System.out.println("Labeldata() 실행 오류 : "+e);

	      }
	   }
   
   public void Labelmemo_04() {
	      try {
	         String sql = "SELECT * FROM diary.memo_04 where num=1;";
	         var pstmt = con.prepareStatement(sql);
	         rs = pstmt.executeQuery();

	         while(rs.next()) {
	            String thismemo = rs.getString("thismemo");   

	            JLmemo_04.setText(thismemo);
	         }
	      }catch(Exception e) {
	         System.out.println("Labeldata() 실행 오류 : "+e);

	      }
	   }
   
   // 이름 데이터   
   public void Label_name() {
	      try {
	    	 String sql = "SELECT * FROM diary.name where num=1;";
	         var pstmt = con.prepareStatement(sql);
	         rs = pstmt.executeQuery();

	         while(rs.next()) {
	            String name = rs.getString("thisname");   

	            JL_name.setText(name);
	         }
	      }catch(Exception e) {
	         System.out.println("Label_name() 실행 오류 : "+e);

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
      new Tomorrow_me().setVisible(true);
   }
}

class ImagePanel extends JPanel {
   private Image img;

   public ImagePanel(Image img) {
      this.img = img; //매개변수와 멤버변수의 이름이 같을 때 this 를 사용하여 구분할 수 있다.
      setSize(new Dimension(img.getWidth(null), img.getHeight(null))); //컴포넌트 크기 조정 불가능
      setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null))); //컴포넌트 크기 조정가능
      setLayout(null); //어디든지 쓸 수 있게
   }

   public void paintComponent(Graphics g) { //백그라운드 이미지를 넣을 수 있게 해주는 함수
      g.drawImage(img, 0, 0, null); //이미지 불러오기

   }

}
