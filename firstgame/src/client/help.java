package client;
//游戏帮助对话框类  80行
import sharedData.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*; 
import java.awt.image.*; 
import javax.imageio.*; 

public class help extends JDialog implements ActionListener
{
	static final int YES=1,NO=0;
	int message=-1; JButton yes, no;
	myButton but1;
	String str="测试阶段一共36个城，玩家的城的图标是随机的，每个玩家看自己的城的图标是独一无二的，"
		+"电脑控制的城的图标是卡通图标。\n\n通过建造议政厅可以提高城堡的面积，通过建造市场可以提高每分钟"
		+"金钱的增长，通过建造农场可以提高城堡的粮食供应上限。箭塔会参与城堡的防御战中,防御战中防御方防御力增加20%。招募士兵时要注意"
		+"不要超过了粮食的上限，否则请求失败。建造设施不要超过城堡的面积。\n\n骑兵具有优秀的攻击力与防御力，"
		+"但价格较贵，步兵具有优秀的防御力，弓兵攻击力较高，防御力较低。\n\n武将的统率关系部队战斗时士气是否"
		+"高涨，提高致命一击的几率；勇武提高部队的攻击力，也关系武将普通攻击的威力；智力关系部队的MP，"
		+"提高部队的防御力。可以通过购买装备来提高武将的属性。武将同时也以3种属性的其中一种作为主属性，"
		+"武将获得足够经验升级的时候，主属性增长比其他属性增长的多。\n\n武将有3种技能，第一个技能为提高"
		+"单一兵种的威力，第二个技能为战斗使用的战法，消耗MP，根据双方武将的属性差距及自身级别计算伤害，"
		+"第三个技能为提高整体部队的威力。第一个技能在英雄高于5级时自动学会，第二个技能为10级，第三个技能为"
		+"20级。武将的普通攻击跟级别与勇武相关。\n\n可以与电脑控制的城池战，电脑城池的数据会自动更新。战斗"
		+"后会获得经验，如果防御方失败，进攻方可以获得防御方一半的金钱。战斗时逃跑可以获得一半的经验，但是"
		+"部队的数量变为二分之一，战斗中离开游戏算做失败，部队数量变为二分之一，并且不获得经验。" +
				"战斗获得经验与对方武将的级别成正比，根据杀死对方单位数量计算。\n\n"
		;
	help(JFrame f,String s,boolean b)
	{
		super (f, s, b);
		yes=new JButton ("确认"); yes.addActionListener(this);
		no=new JButton ("取消"); no.addActionListener(this);
		JPanel pan=new JPanel();
		but1=new myButton("img/icon.jpg",100,100);
		but1.setBounds(140,10,100,100);
		JTextArea text1=new JTextArea(str);
		text1.setEditable(false);
		JScrollPane jsp=new JScrollPane(text1); 		
		jsp.setBounds(10,120,400,300);
		text1.setLineWrap(true);
		JPanel pan2=new JPanel();
		pan.setBounds(0,0,420,420) ;
		pan.setLayout(null);
		pan2.setLayout(null);
		pan2.setBounds(0,420,420,60) ;
		yes.setBounds(80,10,60,30);
		no.setBounds(200,10,60,30);
		pan.add(jsp);pan.add(but1);
		pan2.add(yes);pan2.add(no);
		setLayout(null) ;
		add(pan) ;add(pan2) ;
		setVisible(false);
		setBounds(230,150,420,500) ;
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
