package client;
//客户端登陆服务器界面类 100行
import sharedData.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*; 
import java.awt.image.*; 
import javax.imageio.*; 

public class login extends JFrame 
{
	static final int YES=1,NO=0;
	int message=-1; JButton yes, no;
	String id;
	String pw;
	String ip;
	int port;
	myButton logo;
	JLabel lab1,lab2,lab3,lab4;
	JTextField text1,text3,text4;
	JPasswordField text2;
	Box baseBox,boxH1,boxH2,boxV1,boxV2,box1,box2;
	login(String s,Client up)
	{
		super (s);
		logo=new myButton("","img/loginlogo"+sharedData.random(1, 6)+".jpg",300,100);		

		yes=new JButton ("登陆"); yes.addActionListener(up);
		no=new JButton ("取消"); no.addActionListener(up);
		lab1=new JLabel("用户名");lab2=new JLabel("密码");
		lab3=new JLabel("目标ip");lab4=new JLabel("端口");
		text1=new JTextField(15);text2=new JPasswordField(15);
		text3=new JTextField(10);text4=new JTextField(2);
		text1.setText("test11");
		text2.setText("123456");
		text3.setText("127.0.0.1");
		text4.setText("14444");
		baseBox=Box.createVerticalBox();
		box1=Box.createHorizontalBox();
		boxV1=Box.createVerticalBox();		
		boxV2=Box.createVerticalBox();
		boxH2=Box.createHorizontalBox();	
		box2=Box.createHorizontalBox();
		boxV1.add(Box.createVerticalStrut(8));boxV1.add(lab1);
		boxV1.add(Box.createVerticalStrut(8));boxV1.add(lab2);
		boxV1.add(Box.createVerticalStrut(8));boxV2.add(text1);
		boxV2.add(Box.createVerticalStrut(8));boxV2.add(text2);
		box2.add(Box.createHorizontalStrut(15));
		box2.add(lab3);box2.add(Box.createHorizontalStrut(5));box2.add(text3);
		box2.add(Box.createHorizontalStrut(5));
		box2.add(lab4);box2.add(Box.createHorizontalStrut(5));box2.add(text4);
		box2.add(Box.createHorizontalStrut(22));
		boxH2.add(yes);boxH2.add(Box.createHorizontalStrut(12));boxH2.add(no);	
		box1.add(Box.createHorizontalStrut(15));	
		box1.add(boxV1);
		box1.add(Box.createHorizontalStrut(15));		
		box1.add(boxV2);	
		box1.add(Box.createHorizontalStrut(22));
		baseBox.add(box1);
		baseBox.add(Box.createVerticalStrut(12));
		baseBox.add(box2);
		baseBox.add(Box.createVerticalStrut(12));
		baseBox.add(boxH2);
		baseBox.add(Box.createVerticalStrut(12));
		logo.setBounds(0,0,300, 100);
		baseBox.setBounds(0, 110,300,140);
		setLayout(null) ;
		add(logo);
		add(baseBox);
		setBounds(330,250,300,290) ;
		setVisible(true);
		setResizable(false);
		addWindowListener(new WindowAdapter()
		{ public void windowClosing (WindowEvent e)
		{ message=-1; System.exit(1); }
		});
	}	
	public String getID()
	{
		return id;
	}	
	public String getPW()
	{
		return pw;
	}
	public String getIP()
	{
		return ip;
	}
	public int getPort()
	{
		return port;
	}


}
