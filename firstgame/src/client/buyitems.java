package client;
//����װ���Ի�����  110��

import sharedData.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*; 
import java.awt.image.*; 
import javax.imageio.*; 
public class buyitems extends JDialog implements ActionListener
{
	static final int YES=1,NO=0;
	int message=-1; JButton yes, no;
	JLabel lab1;
	JPanel pan1,pan2,pan3;
	myJRadioButton but[];
	myButton but1[];
	Box box1;
	Box box[];
	int choice;
	mainFrame frame;
	ButtonGroup group;
	String type[]={"","����","����","ͷ��","��Ʒ","Ь��","����"};
	buyitems(mainFrame f,String s,boolean b)
	{
		super (f, s, b);
		frame=f;
		yes=new JButton ("ȷ��"); yes.addActionListener(this);
		no=new JButton ("ȡ��"); no.addActionListener(this);
		pan1=new JPanel();pan3=new JPanel();pan2=new JPanel();
		box1=Box.createVerticalBox();
		but=new myJRadioButton[200];
		but1=new myButton[200];
		box=new Box[200];		
		JScrollPane jsp3=new JScrollPane(box1); 
		group=new ButtonGroup();
		for(int i=1;i<200;i++)
		{
			if(f.db.items[i].id==0){continue;}
			box[i]=Box.createHorizontalBox();			
			but[i]=new myJRadioButton(f.db.items[i].name,false,f.db.items[i].id);
			but1[i]=new myButton(f.db.items[i].path,27,27);		
			but1[i].setText(" ");			
			box[i].add(but[i]);			
			box[i].add(but1[i]);
			group.add(but[i]);
			box[i].add(Box.createHorizontalStrut(30));
			box[i].add(new JLabel(type[f.db.items[i].type]));
			box[i].add(Box.createHorizontalStrut(30));
			box[i].add(new JLabel(f.db.items[i].price+""));
			box[i].add(Box.createHorizontalStrut(30));
			box[i].add(new JLabel("ͳ��+"+f.db.items[i].lead
					+" ����+"+f.db.items[i].power+" ����+"+f.db.items[i].wise));
			box1.add(Box.createVerticalStrut(10));
			box1.add(box[i]);
		}
		box1.add(Box.createVerticalStrut(30));
		add(jsp3);
		setLayout(null) ;		
		pan2.add(yes) ; pan2.add(no) ;
		yes.setBounds(100,10,70,30) ;no.setBounds(300,10,70,30) ;
		pan1.add(jsp3);
		//box1.setBounds(0,0,600,500);
		pan1.setLayout(null);
		pan2.setLayout(null);
		Box box2=Box.createHorizontalBox();
		box2.add(new JLabel("װ��"));
		box2.add(Box.createHorizontalStrut(50));
		box2.add(new JLabel("λ��"));
		box2.add(Box.createHorizontalStrut(30));
		box2.add(new JLabel("�۸�"));
		box2.add(Box.createHorizontalStrut(60));
		box2.add(new JLabel("����"));
		box2.add(Box.createHorizontalStrut(40));
		pan3.add(box2);
		add(pan3);
		add(pan1);
		add(pan2);
		pan3.setBounds(0,0,500,30) ;
		pan1.setBounds(0,30,500,500) ;
		jsp3.setBounds(0,0,500,500) ;
		pan2.setBounds(0,530,500,80) ;
		setBounds(160,60,507,610) ;
		addWindowListener(new WindowAdapter()
		{ public void windowClosing (WindowEvent e)
		{ message=NO;choice=0; setVisible(false); }
		});
	}
	public void actionPerformed(ActionEvent e)
	{ if (e.getSource()==yes)
	{
		choice=0;
		message=YES; 
		for(int i=1;i<200;i++)
		{
			if(but[i]!=null&&but[i].isSelected()==true)
			{
				choice=but[i].id;				
			}
		}		
		setVisible(false) ;
	}
	else if(e.getSource()==no)
	{
		choice=0;
		message=NO; setVisible(false);
	}
	}
	public int getMessage()
	{
		return message;
	}

}
