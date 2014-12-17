package client;
//购买武将类，可拓展 60行

import sharedData.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*; 
import java.awt.image.*; 
import javax.imageio.*; 
public class hirehero extends JDialog implements ActionListener
{
	static final int YES=1,NO=0;
	int id;
	int message=-1; JButton yes, no;
	int num;
	JLabel lab1;
	JTextField text1;
	hirehero(myDB db,JFrame f,String s,boolean b)
	{
		super (f, s, b);
		lab1=new JLabel("请输入数量：");
		text1=new JTextField(7);
		yes=new JButton ("确认"); yes.addActionListener(this);
		no=new JButton ("取消"); no.addActionListener(this);
		setLayout(new FlowLayout()) ;
		add(lab1);add(text1);
		add(yes) ; add(no) ;
		setBounds(330,250,300,400) ;
		addWindowListener(new WindowAdapter()
		{ public void windowClosing (WindowEvent e)
		{ message=-1; setVisible(false); }
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
		setVisible(false) ;
	}
	else if(e.getSource()==no)
	{
		message=NO; setVisible(false);
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

