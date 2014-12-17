package client;
//负责显示其他玩家信息的对话框的建立 130行
import sharedData.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*; 
import java.awt.image.*; 
import javax.imageio.*; 
public class info extends JDialog implements ActionListener
{
	static final int YES=1,NO=0;
	int num;
	int message=-1; JButton yes, no;
	JLabel lab1;
	JTextField text1;
	JTextField name,gold,goldpm,rice,area,heronum,
	knightnum,footmannum,archernum,towernum,hallnum,marketnum,fieldnum;
	info(JFrame f,String s,boolean b)
	{
		super (f, s, b);
		//		信息界面的创建
		
		name=new JTextField("城堡");name.setEditable(false);
		gold=new JTextField("0");gold.setEditable(false);
		goldpm=new JTextField("0");goldpm.setEditable(false);
		rice=new JTextField("0/0");rice.setEditable(false);
		area=new JTextField("0/0");area.setEditable(false);
		knightnum=new JTextField("0");knightnum.setEditable(false);
		footmannum=new JTextField("0");footmannum.setEditable(false);
		archernum=new JTextField("0");archernum.setEditable(false);
		towernum=new JTextField("0");towernum.setEditable(false);
		hallnum=new JTextField("0");hallnum.setEditable(false);
		marketnum=new JTextField("0");marketnum.setEditable(false);
		fieldnum=new JTextField("0");fieldnum.setEditable(false);
		heronum=new JTextField("0");heronum.setEditable(false);
		Box basebox=Box.createVerticalBox();
		Box boxx=Box.createHorizontalBox();
		Box box1=Box.createHorizontalBox();Box box2=Box.createHorizontalBox();
		Box box3=Box.createHorizontalBox();Box box4=Box.createHorizontalBox();
		Box box5=Box.createVerticalBox();Box box6=Box.createVerticalBox();
		Box box7=Box.createVerticalBox();Box box8=Box.createVerticalBox();
		box1.add(Box.createHorizontalStrut(45));
		box1.add(new Label("城堡名称"));
		box1.add(name);
		box1.add(Box.createHorizontalStrut(45));
		box2.add(Box.createHorizontalStrut(15));
		box3.add(Box.createHorizontalStrut(25));
		box4.add(Box.createHorizontalStrut(25));
		box2.add(new Label("现有金钱"));
		box2.add(gold);box2.add(Box.createHorizontalStrut(15));
		box2.add(new Label("城内武将"));
		box2.add(goldpm);
		box3.add(new Label("城堡面积"));
		box3.add(area);
		box4.add(new Label("城堡粮食"));
		box4.add(rice);
		box5.add(new Label("武将级别"));box5.add(Box.createVerticalStrut(12));
		box6.add(heronum);box6.add(Box.createVerticalStrut(12));
		box5.add(new Label("骑兵数量"));box5.add(Box.createVerticalStrut(12));
		box6.add(knightnum);box6.add(Box.createVerticalStrut(12));
		box7.add(new Label("议政厅数量"));box7.add(Box.createVerticalStrut(12));
		box8.add(hallnum);box8.add(Box.createVerticalStrut(12));
		box5.add(new Label("步兵数量"));box5.add(Box.createVerticalStrut(12));
		box6.add(footmannum);box6.add(Box.createVerticalStrut(12));
		box7.add(new Label("市场数量"));box7.add(Box.createVerticalStrut(12));
		box8.add(marketnum);box8.add(Box.createVerticalStrut(12));
		box5.add(new Label("弓兵数量"));box5.add(Box.createVerticalStrut(12));
		box6.add(archernum);box6.add(Box.createVerticalStrut(12));
		box7.add(new Label("农田数量"));box7.add(Box.createVerticalStrut(12));
		box8.add(fieldnum);box8.add(Box.createVerticalStrut(12));
		box7.add(new Label("箭塔数量"));box7.add(Box.createVerticalStrut(12));
		box8.add(towernum);box8.add(Box.createVerticalStrut(12));
		box2.add(Box.createHorizontalStrut(15));
		box3.add(Box.createHorizontalStrut(25));
		box4.add(Box.createHorizontalStrut(25));
		
		boxx.add(Box.createHorizontalStrut(15));
		boxx.add(box5);
		boxx.add(box6);
		boxx.add(Box.createHorizontalStrut(10));
		boxx.add(box7);
		boxx.add(box8);
		boxx.add(Box.createHorizontalStrut(15));
		
		basebox.add(Box.createVerticalStrut(12));
		basebox.add(box1);basebox.add(Box.createVerticalStrut(12));
		basebox.add(box3);basebox.add(Box.createVerticalStrut(12));
		basebox.add(box4);basebox.add(Box.createVerticalStrut(12));
		basebox.add(box2);basebox.add(Box.createVerticalStrut(12));
		basebox.add(boxx);basebox.add(Box.createVerticalStrut(12));		
		
		basebox.setBounds(0,0,290,300);
		add(basebox);
		yes=new JButton ("确认"); yes.addActionListener(this);
		no=new JButton ("取消"); no.addActionListener(this);
		yes.setBounds(70,310,50,20);no.setBounds(220,310,50,20);
		setLayout(null) ;			
		setVisible(false);
		setBounds(200,200,290,330) ;
		addWindowListener(new WindowAdapter()
		{ public void windowClosing (WindowEvent e)
		{ message=-1; setVisible(false); }
		});
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
	public int getNum()
	{
		return num;
	}

}

