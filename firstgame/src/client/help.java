package client;
//��Ϸ�����Ի�����  80��
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
	String str="���Խ׶�һ��36���ǣ���ҵĳǵ�ͼ��������ģ�ÿ����ҿ��Լ��ĳǵ�ͼ���Ƕ�һ�޶��ģ�"
		+"���Կ��Ƶĳǵ�ͼ���ǿ�ͨͼ�ꡣ\n\nͨ������������������߳Ǳ��������ͨ�������г��������ÿ����"
		+"��Ǯ��������ͨ������ũ��������߳Ǳ�����ʳ��Ӧ���ޡ����������Ǳ��ķ���ս��,����ս�з���������������20%����ļʿ��ʱҪע��"
		+"��Ҫ��������ʳ�����ޣ���������ʧ�ܡ�������ʩ��Ҫ�����Ǳ��������\n\n�����������Ĺ��������������"
		+"���۸�Ϲ󣬲�����������ķ������������������ϸߣ��������ϵ͡�\n\n�佫��ͳ�ʹ�ϵ����ս��ʱʿ���Ƿ�"
		+"���ǣ��������һ���ļ��ʣ�������߲��ӵĹ�������Ҳ��ϵ�佫��ͨ������������������ϵ���ӵ�MP��"
		+"��߲��ӵķ�����������ͨ������װ��������佫�����ԡ��佫ͬʱҲ��3�����Ե�����һ����Ϊ�����ԣ�"
		+"�佫����㹻����������ʱ���������������������������Ķࡣ\n\n�佫��3�ּ��ܣ���һ������Ϊ���"
		+"��һ���ֵ��������ڶ�������Ϊս��ʹ�õ�ս��������MP������˫���佫�����Բ�༰����������˺���"
		+"����������Ϊ������岿�ӵ���������һ��������Ӣ�۸���5��ʱ�Զ�ѧ�ᣬ�ڶ�������Ϊ10��������������Ϊ"
		+"20�����佫����ͨ������������������ء�\n\n��������Կ��Ƶĳǳ�ս�����Գǳص����ݻ��Զ����¡�ս��"
		+"����þ��飬���������ʧ�ܣ����������Ի�÷�����һ��Ľ�Ǯ��ս��ʱ���ܿ��Ի��һ��ľ��飬����"
		+"���ӵ�������Ϊ����֮һ��ս�����뿪��Ϸ����ʧ�ܣ�����������Ϊ����֮һ�����Ҳ���þ��顣" +
				"ս����þ�����Է��佫�ļ�������ȣ�����ɱ���Է���λ�������㡣\n\n"
		;
	help(JFrame f,String s,boolean b)
	{
		super (f, s, b);
		yes=new JButton ("ȷ��"); yes.addActionListener(this);
		no=new JButton ("ȡ��"); no.addActionListener(this);
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
