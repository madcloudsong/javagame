package client;
//��Ϸ���ݶԻ�����  80��
import sharedData.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*; 
import java.awt.image.*; 
import javax.imageio.*; 
public class data extends JDialog implements ActionListener
{
	static final int YES=1,NO=0;
	int message=-1; JButton yes, no;
	myButton but1;
	String str=
		"������\t�۸�"+sharedData.hallPrice+"\tռ�أ�"
		+sharedData.hallArea+"\t�ṩ�����"+sharedData.hallAreaOffer+"\n"
		+"�г�\t�۸�"+sharedData.marketPrice+"\tռ�أ�"
		+sharedData.marketArea+"\t�ṩ��Ǯ��"+sharedData.marketGoldOffer+"\n"
		+"ũ��\t�۸�"+sharedData.fieldPrice+"\tռ�أ�"
		+sharedData.fieldArea+"\t�ṩ��ʳ��"+sharedData.fieldRiceOffer+"\n"
		+"����\t�۸�"+sharedData.towerPrice+"\tռ�أ�"
		+sharedData.towerArea+"\t��������"
		+sharedData.towerAp+"\t��������"+sharedData.towerDp+"\n"
		+"���\t�۸�"+sharedData.knightPrice+"\t��������"
		+sharedData.knightAp+"\t��������"+sharedData.knightDp+"\n"
		+"����\t�۸�"+sharedData.footmanPrice+"\t��������"
		+sharedData.footmanAp+"\t��������"+sharedData.footmanDp+"\n"
		+"����\t�۸�"+sharedData.archerPrice+"\t��������"
		+sharedData.archerAp+"\t��������"+sharedData.archerDp+"\n"
		+"�佫��������������4�㣬������������2��"+"\n"
		+"ÿ��������Ҫ����Ϊlevel*level*2000\n\n"		
		;
	data(JFrame f,String s,boolean b,myDB db)
	{
		super (f, s, b);
		yes=new JButton ("ȷ��"); yes.addActionListener(this);
		no=new JButton ("ȡ��"); no.addActionListener(this);
		JPanel pan=new JPanel();
		but1=new myButton("img/icon.jpg",100,100);
		but1.setBounds(170,10,100,100);
		JTextArea text1=new JTextArea(str);
		text1.setEditable(false);
		JScrollPane jsp=new JScrollPane(text1); 		
		jsp.setBounds(10,120,450,300);
		text1.setLineWrap(true);
		JPanel pan2=new JPanel();
		pan.setBounds(0,0,470,420) ;
		pan.setLayout(null);
		pan2.setLayout(null);
		pan2.setBounds(0,420,470,60) ;
		yes.setBounds(80,10,60,30);
		no.setBounds(200,10,60,30);
		pan.add(jsp);pan.add(but1);
		pan2.add(yes);pan2.add(no);
		setLayout(null) ;
		add(pan) ;add(pan2) ;
		setVisible(false);
		setBounds(230,150,470,500) ;
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
