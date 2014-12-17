package server;
//服务器端登陆类 60行
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
	JLabel lab1,lab2,lab3,lab4;
	JTextField text1,text3,text4;
	login(String s,server up)
	{
		super (s);
		yes=new JButton ("登陆"); yes.addActionListener(up);
		no=new JButton ("取消"); no.addActionListener(up);
		lab4=new JLabel("端口");
		text1=new JTextField(6);
		text1.setText("14444");
		add(lab4);
		add(text1);
		add(yes);
		add(no);
		setLayout(new FlowLayout()) ;		
		setBounds(330,250,300,90) ;
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
