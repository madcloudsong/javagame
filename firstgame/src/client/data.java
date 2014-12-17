package client;
//游戏数据对话框类  80行
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
		"议政厅\t价格："+sharedData.hallPrice+"\t占地："
		+sharedData.hallArea+"\t提供面积："+sharedData.hallAreaOffer+"\n"
		+"市场\t价格："+sharedData.marketPrice+"\t占地："
		+sharedData.marketArea+"\t提供金钱："+sharedData.marketGoldOffer+"\n"
		+"农场\t价格："+sharedData.fieldPrice+"\t占地："
		+sharedData.fieldArea+"\t提供粮食："+sharedData.fieldRiceOffer+"\n"
		+"箭塔\t价格："+sharedData.towerPrice+"\t占地："
		+sharedData.towerArea+"\t攻击力："
		+sharedData.towerAp+"\t防御力："+sharedData.towerDp+"\n"
		+"骑兵\t价格："+sharedData.knightPrice+"\t攻击力："
		+sharedData.knightAp+"\t防御力："+sharedData.knightDp+"\n"
		+"步兵\t价格："+sharedData.footmanPrice+"\t攻击力："
		+sharedData.footmanAp+"\t防御力："+sharedData.footmanDp+"\n"
		+"弓兵\t价格："+sharedData.archerPrice+"\t攻击力："
		+sharedData.archerAp+"\t防御力："+sharedData.archerDp+"\n"
		+"武将升级主属性增加4点，其他属性增加2点"+"\n"
		+"每级升级需要经验为level*level*2000\n\n"		
		;
	data(JFrame f,String s,boolean b,myDB db)
	{
		super (f, s, b);
		yes=new JButton ("确认"); yes.addActionListener(this);
		no=new JButton ("取消"); no.addActionListener(this);
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
