package client;
//ȷ�϶Ի����� 40��
import sharedData.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*; 
import java.awt.image.*; 
import javax.imageio.*; 
public class yesno extends JDialog implements ActionListener
{
	static final int YES=1,NO=0;
	int message=-1; JButton yes, no;
	JLabel lab1;
	yesno(JFrame f,String s,boolean b)
	{
		super (f, s, b);
		yes=new JButton ("ȷ��"); yes.addActionListener(this);
		no=new JButton ("ȡ��"); no.addActionListener(this);
		setLayout(new FlowLayout()) ;
		add(yes) ; add(no) ;
		setBounds(330,250,200,80) ;
		addWindowListener(new WindowAdapter()
		{ public void windowClosing (WindowEvent e)
		{ message=-1; setVisible(false); }
		});
	}
	public void actionPerformed(ActionEvent e)
	{ if (e.getSource()==yes)
	{
		message=YES; setVisible(false) ;
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

}
