package client;
//输入数量对话框类  80行
import sharedData.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*; 
import java.awt.image.*; 
import javax.imageio.*; 
public class howmany extends JDialog implements ActionListener
{
	static final int YES=1,NO=0;
	int num;
	int message=-1; JButton yes, no;
	JLabel lab1;
	JTextField text1;
	howmany(JFrame f,String s,boolean b)
	{
		super (f, s, b);
		lab1=new JLabel("请输入数量：");
		text1=new JTextField(7);
		text1.setText("0");
		text1.selectAll();
		yes=new JButton ("确认"); yes.addActionListener(this);
		no=new JButton ("取消"); no.addActionListener(this);
		setLayout(new FlowLayout()) ;
		add(lab1);add(text1);
		text1.selectAll();
		add(yes) ; add(no) ;
		setBounds(330,250,200,100) ;	
		text1.addActionListener(this);
		addWindowListener(new WindowAdapter()
		{ public void windowClosing (WindowEvent e)
		{ message=-1; setVisible(false);text1.setText("0");text1.selectAll(); }
		});
	}
	
	public void actionPerformed(ActionEvent e)
	{ if (e.getSource()==yes)
	{
		message=YES; 
		String s=text1.getText();
		try{
		num=Integer.parseInt(s);
		}
		catch(NumberFormatException ee){message=NO;}
		if(num<=0)message=NO;
		setVisible(false) ;
		text1.setText("0");
		text1.selectAll();
	}
	else if(e.getSource()==no)
	{
		message=NO; setVisible(false);
		text1.setText("0");
		text1.selectAll();
	}
	else if (e.getSource()==text1)
	{
		message=YES; 
		String s=text1.getText();
		try{
		num=Integer.parseInt(s);
		}
		catch(NumberFormatException ee){message=NO;}
		if(num<=0)message=NO;
		setVisible(false) ;
		text1.setText("0");
		text1.selectAll();
	}
	}
	public int getMessage()
	{
		return message;
	}
	public int getNum()
	{
		return num;
	}

}
