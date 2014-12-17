package client;

//武将信息类  270行
import sharedData.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*; 
import java.awt.image.*; 
import javax.imageio.*; 

public class heroinfo extends JDialog implements ActionListener
{
	mainFrame frame;
	static final int YES=1,NO=0;
	int message=-1; JButton yes, no;	
	String type[]={"","统率型","勇武型","智力型"};
	myButton hbut,hbut2;
	JTextField htext;
	JTextField htext2,htext3;
	myButton mbut1,mbut2,mbut3;
	JTextField mtext1,mtext2,mtext3;
	
	myButton ibut1,ibut2,ibut3,ibut4,ibut5,ibut6;
	JTextField itext1,itext2,itext3,itext4,itext5,itext6;
	
	heroinfo(mainFrame f,String s,boolean b,hero h)
	{
		super (f, s, b);
		frame=f;
		JPanel pan1=new JPanel();
		pan1.setLayout(null);
		pan1.setBounds(0,0,500,160);
		hbut=new myButton("img/icon.jpg",100,100);
		pan1.add(hbut);
		hbut.setBounds(10,10,100,100);
		hbut2=new myButton("img/icon.jpg",150,150);
		pan1.add(hbut2);
		hbut2.setBounds(330,10,150,150);
		htext=new JTextField(h.name);
		htext3=new JTextField(h.des);
		pan1.add(htext);
		htext.setBounds(120,10,200,40);
		htext.setEditable(false);
		pan1.add(htext3);
		htext3.setBounds(120,60,200,40);
		htext3.setEditable(false);
		htext2=new JTextField("等级"+h.level+"经验"+h.exp
				+" "+type[h.type]+" 统率"+h.lead+"勇武"+h.power+"智力"+h.wise);
		pan1.add(htext2);
		htext2.setBounds(10,120,310,40);
		htext2.setEditable(false);
		
		JPanel pan2=new JPanel();
		pan2.setLayout(null);
		pan2.setBounds(0,160,500,170);
		mbut1=new myButton("img/icon.jpg",40,40);
		pan2.add(mbut1);
		mbut1.setBounds(10,10,40,40);
		mtext1=new JTextField("");
		mtext1.setBounds(60,10,420,40);
		mtext1.setEditable(false);
		pan2.add(mtext1);
		mbut2=new myButton("img/icon.jpg",40,40);
		pan2.add(mbut2);
		mbut2.setBounds(10,60,40,40);
		mtext2=new JTextField("");
		mtext2.setBounds(60,60,420,40);
		mtext2.setEditable(false);
		pan2.add(mtext2);
		mbut3=new myButton("img/icon.jpg",40,40);
		pan2.add(mbut3);
		mbut3.setBounds(10,110,40,40);
		mtext3=new JTextField("");
		mtext3.setBounds(60,110,420,40);
		mtext3.setEditable(false);		
		pan2.add(mtext3);	
		
		JPanel pan3=new JPanel();
		pan3.setLayout(null);
		pan3.setBounds(0,320,500,320);
		ibut1=new myButton("img/icon.jpg",40,40);
		JTextField lab1=new JTextField("  武器：");
		lab1.setEditable(false);
		lab1.setBounds(10,10,60,40);
		pan3.add(lab1);
		pan3.add(ibut1);
		ibut1.setBounds(80,10,40,40);
		itext1=new JTextField("");
		itext1.setBounds(130,10,350,40);
		itext1.setEditable(false);
		pan3.add(itext1);
		ibut2=new myButton("img/icon.jpg",40,40);
		JTextField lab2=new JTextField("  头盔：");
		lab2.setEditable(false);
		lab2.setBounds(10,60,60,40);
		pan3.add(lab2);
		pan3.add(ibut2);
		ibut2.setBounds(80,60,40,40);
		itext2=new JTextField("");
		itext2.setBounds(130,60,350,40);
		itext2.setEditable(false);
		pan3.add(itext2);
		ibut3=new myButton("img/icon.jpg",40,40);
		JTextField lab3=new JTextField("  铠甲：");
		lab3.setEditable(false);
		lab3.setBounds(10,110,60,40);
		pan3.add(lab3);
		pan3.add(ibut3);
		ibut3.setBounds(80,110,40,40);
		itext3=new JTextField("");
		itext3.setBounds(130,110,350,40);
		itext3.setEditable(false);
		pan3.add(itext3);
		ibut4=new myButton("img/icon.jpg",40,40);
		JTextField lab4=new JTextField("  手套：");
		lab4.setEditable(false);
		lab4.setBounds(10,160,60,40);
		pan3.add(lab4);
		pan3.add(ibut4);
		ibut4.setBounds(80,160,40,40);
		itext4=new JTextField("");
		itext4.setBounds(130,160,350,40);
		itext4.setEditable(false);
		pan3.add(itext4);
		ibut5=new myButton("img/icon.jpg",40,40);
		JTextField lab5=new JTextField("  鞋子：");
		lab5.setEditable(false);
		lab5.setBounds(10,210,60,40);
		pan3.add(lab5);
		pan3.add(ibut5);
		ibut5.setBounds(80,210,40,40);
		itext5=new JTextField("");
		itext5.setBounds(130,210,350,40);
		itext5.setEditable(false);
		pan3.add(itext5);
		ibut6=new myButton("img/icon.jpg",40,40);
		JTextField lab6=new JTextField("  饰品：");
		lab6.setEditable(false);
		lab6.setBounds(10,260,60,40);
		pan3.add(lab6);
		pan3.add(ibut6);
		ibut6.setBounds(80,260,40,40);
		itext6=new JTextField("");
		itext6.setBounds(130,260,350,40);
		itext6.setEditable(false);
		pan3.add(itext6);
		
		add(pan1);
		add(pan2);
		add(pan3);
		setLayout(null) ;	
		setBounds(160,20,500,660) ;
		addWindowListener(new WindowAdapter()
		{ public void windowClosing (WindowEvent e)
		{ message=NO; setVisible(false); }
		});
	}
	//更新武将信息
	public void update(hero h)
	{
		try{
		this.setTitle("武将信息——"+h.name);
		hbut.mySetPic(h.picpath);
		hbut2.mySetPic(h.largepicpath);
		htext.setText("   "+h.name);
		htext3.setText("   "+h.des);
		htext2.setText(" 等级 "+h.level+" 经验 "+h.exp
				+" "+type[h.type]+" 统率 "+h.lead+" 勇武 "+h.power+" 智力 "+h.wise);
		
		mbut1.mySetPic(frame.db.magic[h.magic1].path);
		mbut2.mySetPic(frame.db.magic[h.magic2].path);
		mbut3.mySetPic(frame.db.magic[h.magic3].path);
		mtext1.setText("    "+frame.db.magic[h.magic1].name
				+"\t"+frame.db.magic[h.magic1].des);				
		mtext2.setText("    "+frame.db.magic[h.magic2].name
				+"\t"+frame.db.magic[h.magic2].des
				+"\t伤害: "+frame.db.magic[h.magic2].damage
				+"\t消耗MP: "+frame.db.magic[h.magic2].MP);
		mtext3.setText("    "+frame.db.magic[h.magic3].name
				+"\t"+frame.db.magic[h.magic3].des);
		if(h.weaponId==0)
		{
			ibut1.mySetPic("img/noitems.jpg");	
			itext1.setText("  无");
		}
		else{		
		ibut1.mySetPic(frame.db.items[h.weaponId].path);
		itext1.setText("  "+frame.db.items[h.weaponId].name
				+"\t统率+"+frame.db.items[h.weaponId].lead
				+" 勇武"+frame.db.items[h.weaponId].power
				+" 智力"+frame.db.items[h.weaponId].wise);
		}
		if(h.helmetId==0)
		{
			ibut2.mySetPic("img/noitems.jpg");	
			itext2.setText("  无");
		}
		else{
		ibut2.mySetPic(frame.db.items[h.helmetId].path);
		itext2.setText("  "+frame.db.items[h.helmetId].name
				+"\t统率+"+frame.db.items[h.helmetId].lead
				+" 勇武"+frame.db.items[h.helmetId].power
				+" 智力"+frame.db.items[h.helmetId].wise);
		}
		if(h.armorId==0)
		{
			ibut3.mySetPic("img/noitems.jpg");	
			itext3.setText("  无");
		}
		else{
		ibut3.mySetPic(frame.db.items[h.armorId].path);	
		itext3.setText("  "+frame.db.items[h.armorId].name
				+"\t统率+"+frame.db.items[h.armorId].lead
				+" 勇武"+frame.db.items[h.armorId].power
				+" 智力"+frame.db.items[h.armorId].wise);		
		}
		if(h.gloveId==0)
		{
			ibut4.mySetPic("img/noitems.jpg");	
			itext4.setText("  无");
		}
		else{
		ibut4.mySetPic(frame.db.items[h.gloveId].path);
		itext4.setText("  "+frame.db.items[h.gloveId].name
				+"\t统率+"+frame.db.items[h.gloveId].lead
				+" 勇武"+frame.db.items[h.gloveId].power
				+" 智力"+frame.db.items[h.gloveId].wise);
		}
		if(h.shoesId==0)
		{
			ibut5.mySetPic("img/noitems.jpg");	
			itext5.setText("  无");
		}
		else{
		ibut5.mySetPic(frame.db.items[h.shoesId].path);
		itext5.setText("  "+frame.db.items[h.shoesId].name
				+"\t统率+"+frame.db.items[h.shoesId].lead
				+" 勇武"+frame.db.items[h.shoesId].power
				+" 智力"+frame.db.items[h.shoesId].wise);
		}
		if(h.ringId==0)
		{
			ibut6.mySetPic("img/noitems.jpg");	
			itext6.setText("  无");
		}
		else{
		ibut6.mySetPic(frame.db.items[h.ringId].path);
		itext6.setText("  "+frame.db.items[h.ringId].name
				+"\t统率+"+frame.db.items[h.ringId].lead
				+" 勇武"+frame.db.items[h.ringId].power
				+" 智力"+frame.db.items[h.ringId].wise);	
		}
		}
		catch(Exception e){frame.聊天记录.append(e+"\n");}
		
	}
	public void actionPerformed(ActionEvent e)
	{ if (e.getSource()==yes)
	{		
		message=YES;		
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

}
