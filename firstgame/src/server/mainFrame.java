package server;
import sharedData.*;

import java.awt.*;
import java.awt.event.*;

import sharedData.player;
import javax.swing.*;

import java.util.*; 
import java.awt.image.*; 
import javax.imageio.*; 
import java.net.*;
import java.io.*;
//���������������� 200��
class clock extends Thread//�����ı����Զ���ʾ���һ����
{
	int time=1000;
	JTextArea x;
	clock(JTextArea x,int time)
	{
		this.x=x;
		this.time=time;
	}
	public void run()
	{
		while(true)
		{
			try{
				Thread.sleep(time);
			}
			catch(Exception e){}
			x.setCaretPosition( x.getDocument().getLength());
		}
	}
}

public class mainFrame extends JFrame implements ActionListener,KeyListener
{
	myDB db;
	JTextArea �����¼,��������;
	JButton ����,����,ǿ��1,ǿ��2,ǿ��3;
	JPanel chat;
	yesno yesnodialog;
	JMenuBar bar1;
	JMenu mainmenu;
	JMenuItem exit;
	ServerThread com;
	clock clk1;
	mainFrame(String s,myDB db)
	{
		super(s);
		this.db=db;	
//		�˵��Ľ���
		bar1=new JMenuBar();
		mainmenu=new JMenu("����������");
		exit=new JMenuItem("�رշ�����");
		mainmenu.add(exit);
		bar1.add(mainmenu);
		setJMenuBar(bar1);
		exit.addActionListener(this);
		//�˳��ĶԻ���Ľ���
		yesnodialog=new yesno(this,"ȷ��ô",true);
		yesnodialog.setVisible(false);
		//�������
		chat=new JPanel();
		�����¼=new JTextArea(3,3); 
		�����¼.setLineWrap(true);
		JScrollPane jsp=new JScrollPane(�����¼); 
		�����¼.setEditable(false);
	
		��������=new JTextArea(3,3); 
		��������.setLineWrap(true);
		JScrollPane jsp2=new JScrollPane(��������); 
		��������.setEditable(true);
		����=new JButton("����");
		��������.addKeyListener(this);
		����.addActionListener(this);
		jsp.setBounds(0,0,290,200);
		jsp2.setBounds(0,200,230,100);
		����.setBounds(230,200,60,60);
		chat.setLayout(null); 
		chat.add(����);
		chat.add(jsp); 
		chat.add(jsp2); 
		chat.setBounds(0,0,290,300);
		add(chat);
		//��ʱ��
		clk1=new clock(�����¼,1000);	
		clk1.start();
		
		����=new JButton("�������������");
		����.addActionListener(this);
		JPanel pan=new JPanel();
		ǿ��1=new JButton("ǿ���˳�ս��״̬");
		ǿ��1.addActionListener(this);		
		pan.add(ǿ��1);
		ǿ��2=new JButton("���ԳǱ����ݻָ�");
		ǿ��2.addActionListener(this);		
		pan.add(ǿ��2);
		ǿ��3=new JButton("��ҵ�½״̬��0");
		ǿ��3.addActionListener(this);		
		pan.add(ǿ��3);
		pan.add(����);
		pan.setLayout(new FlowLayout());
		pan.setBounds(300,0,200,200);
		add(pan);
		
		setLayout(null);
		setBounds(109,109,600,400) ;
		setVisible(true);
		setResizable(false);
		validate();
	}	
	public int sendChat(String s)
	{
		if(s.equals("")){return 0;}		
		s=mySocketData.CHAT+" "+"GM˵:"+s;
		�����¼.append(s+"\n");
		com.sendtoAll(s);
		��������.setText("");
		��������.requestFocusInWindow();
		return 1;
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==exit)
		{
			yesnodialog.setVisible(true);
			if(yesnodialog.getMessage()==yesno.YES)
			{
				System.exit(0);
			}
			else return ;			
		}
		else if(e.getSource()==����)
		{			
			sendChat(��������.getText());
		}
		else if(e.getSource()==����)
		{			
			db.ssaveAll();
			�����¼.append("������������ݳɹ���\n");
		}
		else if(e.getSource()==ǿ��1)
		{			
			for(int i=1;i<50;i++)
			{
				if(db.player[i].id!=0)
				{
					db.player[i].inBattle=0;
				}				
			}
			�����¼.append("�ѽ���ǿ�����ȫ������ս��״̬��\n");
		}
		else if(e.getSource()==ǿ��2)
		{			
			for(int i=1;i<50;i++)
			{				
				if(db.player[i].login==2)
				{
					db.player[i].recover();
				}				
			}
			�����¼.append("�ѽ���ǿ��������ԳǱ����ݻָ���\n");
		}
		else if(e.getSource()==ǿ��3)
		{			
			for(int i=1;i<50;i++)
			{				
				if(db.player[i].login==1)
				{
					db.player[i].login=0;
				}
			}
			�����¼.append("�ѽ���ǿ�������ҵ�½״̬����Ϊ0��\n");
		}
		
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getModifiers()==InputEvent.CTRL_MASK
				&&e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			sendChat(��������.getText());
		}
		
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	public void keyReleased(KeyEvent e)
	{
		
	}
		

}
