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
//服务器端主界面类 200行
class clock extends Thread//控制文本域自动显示最后一行类
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
	JTextArea 聊天记录,聊天输入;
	JButton 输入,保存,强制1,强制2,强制3;
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
//		菜单的建立
		bar1=new JMenuBar();
		mainmenu=new JMenu("服务器控制");
		exit=new JMenuItem("关闭服务器");
		mainmenu.add(exit);
		bar1.add(mainmenu);
		setJMenuBar(bar1);
		exit.addActionListener(this);
		//退出的对话框的建立
		yesnodialog=new yesno(this,"确认么",true);
		yesnodialog.setVisible(false);
		//聊天面板
		chat=new JPanel();
		聊天记录=new JTextArea(3,3); 
		聊天记录.setLineWrap(true);
		JScrollPane jsp=new JScrollPane(聊天记录); 
		聊天记录.setEditable(false);
	
		聊天输入=new JTextArea(3,3); 
		聊天输入.setLineWrap(true);
		JScrollPane jsp2=new JScrollPane(聊天输入); 
		聊天输入.setEditable(true);
		输入=new JButton("发送");
		聊天输入.addKeyListener(this);
		输入.addActionListener(this);
		jsp.setBounds(0,0,290,200);
		jsp2.setBounds(0,200,230,100);
		输入.setBounds(230,200,60,60);
		chat.setLayout(null); 
		chat.add(输入);
		chat.add(jsp); 
		chat.add(jsp2); 
		chat.setBounds(0,0,290,300);
		add(chat);
		//定时器
		clk1=new clock(聊天记录,1000);	
		clk1.start();
		
		保存=new JButton("保存服务器数据");
		保存.addActionListener(this);
		JPanel pan=new JPanel();
		强制1=new JButton("强制退出战斗状态");
		强制1.addActionListener(this);		
		pan.add(强制1);
		强制2=new JButton("电脑城堡数据恢复");
		强制2.addActionListener(this);		
		pan.add(强制2);
		强制3=new JButton("玩家登陆状态清0");
		强制3.addActionListener(this);		
		pan.add(强制3);
		pan.add(保存);
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
		s=mySocketData.CHAT+" "+"GM说:"+s;
		聊天记录.append(s+"\n");
		com.sendtoAll(s);
		聊天输入.setText("");
		聊天输入.requestFocusInWindow();
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
		else if(e.getSource()==输入)
		{			
			sendChat(聊天输入.getText());
		}
		else if(e.getSource()==保存)
		{			
			db.ssaveAll();
			聊天记录.append("保存服务器数据成功！\n");
		}
		else if(e.getSource()==强制1)
		{			
			for(int i=1;i<50;i++)
			{
				if(db.player[i].id!=0)
				{
					db.player[i].inBattle=0;
				}				
			}
			聊天记录.append("已进行强制命令，全体脱离战斗状态！\n");
		}
		else if(e.getSource()==强制2)
		{			
			for(int i=1;i<50;i++)
			{				
				if(db.player[i].login==2)
				{
					db.player[i].recover();
				}				
			}
			聊天记录.append("已进行强制命令，电脑城堡数据恢复！\n");
		}
		else if(e.getSource()==强制3)
		{			
			for(int i=1;i<50;i++)
			{				
				if(db.player[i].login==1)
				{
					db.player[i].login=0;
				}
			}
			聊天记录.append("已进行强制命令，玩家登陆状态设置为0！\n");
		}
		
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getModifiers()==InputEvent.CTRL_MASK
				&&e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			sendChat(聊天输入.getText());
		}
		
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	public void keyReleased(KeyEvent e)
	{
		
	}
		

}
