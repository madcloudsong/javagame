package client;
import java.awt.event.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.image.*; 
import javax.imageio.*; 
import javax.swing.JFrame;


import sharedData.*;
//客户端及客户端与服务器端通信主类  450行
class ClientThread extends Thread
{	
	  Socket socket;
	  DataInputStream in;
	  DataOutputStream out;  	  
	  mainFrame frame;
	  myDB db;	  
	  login logindialog;	  
	  ClientThread(mainFrame frame,myDB db,login t)
	  {
	      this.frame = frame;
	      this.db=db;
	      this.logindialog=t;	
	  }
	  public void send(String s)
	  {
		  try{
			  out.writeUTF(s);
			  //frame.聊天记录.append("发送成功!"+"\n");
		  }
		  catch (Exception e) {frame.聊天记录.append("\n"+"发送失败!"+"\n"); }
	  }
	public void run()
	{
		String s="";
		socket=new Socket();
		try { 
			if(socket.isConnected()){} 		
			else
			{ 		 
			InetAddress  address=InetAddress.getByName(logindialog.ip);		 
	        InetSocketAddress socketAddress=new InetSocketAddress(address,logindialog.port);
	        frame.聊天记录.append("\n"+"正在连接服务器..."+"\n"); 
	        socket.connect(socketAddress);
	        in=new DataInputStream(socket.getInputStream());
	        out=new DataOutputStream(socket.getOutputStream());	                
	        frame.聊天记录.append("\n"+"已连接服务器"+"\n");
	        out.writeUTF(mySocketData.LOGIN+" "+logindialog.id+" "+logindialog.pw);
			}
		  } 	  
		  catch (IOException ee)
		  {
		  	socket=null;
		  	frame.聊天记录.append("\n"+"服务器连接失败..."+":\n");
		  }
		  while(true)
		  {
			  try
	          {
	              s=in.readUTF();  //对连接的处理
	              StringTokenizer fenxi=new StringTokenizer(s," ");  
	              int t=Integer.parseInt(fenxi.nextToken());
	              if(t==mySocketData.CHAT)
	              {
	            	  String chat=fenxi.nextToken();
	            	  while(fenxi.hasMoreTokens())
	            	  {
	            		  chat=chat+" "+fenxi.nextToken();
	            	  }
	            	  frame.聊天记录.append(chat+"\n");	
	            	  if(frame.clipdone==1)
	            	  {
	            		  frame.clip2.play();
	            	  }
	              }
	              else if(t==mySocketData.INFOMATION)
            	  {
            		  player aa=new player();
	            	  int i=Integer.parseInt(fenxi.nextToken());              	
	              	aa.id=i;  
	              	aa.castlename=fenxi.nextToken();           	
	              	aa.x=Integer.parseInt(fenxi.nextToken());
	              	aa.y=Integer.parseInt(fenxi.nextToken());
	              	aa.areaMax=Integer.parseInt(fenxi.nextToken());
	              	aa.areaNow=Integer.parseInt(fenxi.nextToken());
	              	aa.gold=Integer.parseInt(fenxi.nextToken());
	              	aa.goldpm=Integer.parseInt(fenxi.nextToken());
	              	aa.riceMax=Integer.parseInt(fenxi.nextToken());
	              	aa.riceNow=Integer.parseInt(fenxi.nextToken());
	              	aa.login=Integer.parseInt(fenxi.nextToken());
	              	aa.hallNum=Integer.parseInt(fenxi.nextToken());
	              	aa.marketNum=Integer.parseInt(fenxi.nextToken());
	              	aa.fieldNum=Integer.parseInt(fenxi.nextToken());
	              	aa.knightHallNum=Integer.parseInt(fenxi.nextToken());
	              	aa.footmanHallNum=Integer.parseInt(fenxi.nextToken());
	              	aa.archerHallNum=Integer.parseInt(fenxi.nextToken());
	              	aa.towerNum=Integer.parseInt(fenxi.nextToken());
	              	aa.knightNum=Integer.parseInt(fenxi.nextToken());
	              	aa.footmanNum=Integer.parseInt(fenxi.nextToken());
	              	aa.archerNum=Integer.parseInt(fenxi.nextToken());
	              	aa.inBattle=Integer.parseInt(fenxi.nextToken());
	              	aa.bashRate=Integer.parseInt(fenxi.nextToken());
	              	aa.isDefence=Integer.parseInt(fenxi.nextToken());
	              	aa.MP=Integer.parseInt(fenxi.nextToken());
	              	aa.hero=Integer.parseInt(fenxi.nextToken());
	              	int lvl=Integer.parseInt(fenxi.nextToken());
	              	frame.infodialog.name.setText(aa.castlename);
	              	frame.infodialog.gold.setText(aa.gold+"");
	              	frame.infodialog.goldpm.setText(db.hero[aa.hero].name);
	              	frame.infodialog.rice.setText(aa.riceNow+"/"+aa.riceMax);
	              	frame.infodialog.area.setText(aa.areaNow+"/"+aa.areaMax);
	              	frame.infodialog.knightnum.setText(aa.knightNum+"");
	              	frame.infodialog.footmannum.setText(aa.footmanNum+"");
	              	frame.infodialog.archernum.setText(aa.archerNum+"");
	              	frame.infodialog.towernum.setText(aa.towerNum+"");
	              	frame.infodialog.hallnum.setText(aa.hallNum+"");
	              	frame.infodialog.marketnum.setText(aa.marketNum+"");
	              	frame.infodialog.fieldnum.setText(aa.fieldNum+"");
	              	frame.infodialog.heronum.setText(lvl+"");
	              	frame.infodialog.setTitle("信息——"+aa.castlename);
	              	frame.infodialog.setVisible(true);
            	  }
	              else if(t==mySocketData.INITPLAYER)
	              {	            	 
	            	  int i=Integer.parseInt(fenxi.nextToken());              	
	            	  frame.myself.id=i;  
		              	frame.myself.castlename=fenxi.nextToken();           	
		              	frame.myself.x=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.y=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.areaMax=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.areaNow=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.gold=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.goldpm=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.riceMax=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.riceNow=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.login=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.hallNum=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.marketNum=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.fieldNum=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.knightHallNum=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.footmanHallNum=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.archerHallNum=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.towerNum=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.knightNum=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.footmanNum=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.archerNum=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.inBattle=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.bashRate=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.isDefence=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.MP=Integer.parseInt(fenxi.nextToken());
		              	frame.myself.hero=Integer.parseInt(fenxi.nextToken());	              	
	              	frame.castle[frame.myself.x][frame.myself.y].mySetPic("img/castle1.jpg");
	              	frame.castle[frame.myself.x][frame.myself.y].setEnabled(true);
	              	frame.updateInfo();
	              	frame.setTitle("JAVA无双——"+frame.myself.castlename);
	            	 // frame.聊天记录.append("读取玩家数据成功"+"\n");	            	  
	              }
	              else if(t==mySocketData.INITHERO)
	              {
	            	  int i=Integer.parseInt(fenxi.nextToken());              	
	               	db.hero[i].heroId=i;                 	
	               	db.hero[i].playerId=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].hired=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].level=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].exp=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].price=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].name=fenxi.nextToken();
	               	db.hero[i].des=fenxi.nextToken();
	               	db.hero[i].picpath=fenxi.nextToken();
	               	db.hero[i].largepicpath=fenxi.nextToken();            	
	               	db.hero[i].lead=Integer.parseInt(fenxi.nextToken());            	
	               	db.hero[i].power=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].wise=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].type=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].magic1=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].magic2=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].magic3=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].weaponId=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].helmetId=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].armorId=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].shoesId=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].ringId=Integer.parseInt(fenxi.nextToken());
	               	db.hero[i].gloveId=Integer.parseInt(fenxi.nextToken());	
	               	frame.updateInfo();
	            	  //frame.聊天记录.append("读取武将数据成功"+"\n");	            	  
	              }
	              else if(t==mySocketData.LOGIN)
	              {	            	             	  			  
    				  int tid=Integer.parseInt(fenxi.nextToken());	
    				  String tcastlename=fenxi.nextToken();	     
    				  int tx=Integer.parseInt(fenxi.nextToken());	
    				  int ty=Integer.parseInt(fenxi.nextToken());	
    				  int tlogin=Integer.parseInt(fenxi.nextToken());	
    				  if(tid!=0)
    				  {
    				  if(tlogin==1)
    				  {
    					  frame.castle[tx][ty].playerId=tid;
    					  frame.castle[tx][ty].mySetPic("img/castle"
    							  +sharedData.random(2, 4)+".jpg");
    					  frame.castle[tx][ty].setEnabled(true);
    					  frame.聊天记录.append("玩家"+tcastlename+"进入游戏\n");	  
    				  }
    				  else if(tlogin==2)
    				  {
    					  frame.castle[tx][ty].playerId=tid;
    					  frame.castle[tx][ty].mySetPic("img/castle5.jpg");
    					  frame.castle[tx][ty].setEnabled(true);
    				  }
    				  else if(tlogin==0)
    				  {
    					  frame.castle[tx][ty].mySetPic("img/castle6.jpg");
    					  frame.castle[tx][ty].setEnabled(false);
    				  }
    				  else ;	
    				  }
	              }	    
	              else if(t==mySocketData.LOGOUT)
	              {	            	             	  			  
    				  int tid=Integer.parseInt(fenxi.nextToken());	
    				  String tcastlename=fenxi.nextToken();	     
    				  int tx=Integer.parseInt(fenxi.nextToken());	
    				  int ty=Integer.parseInt(fenxi.nextToken());	
    				  int tlogin=Integer.parseInt(fenxi.nextToken());	
    				  if(tid!=0)
    				  {
    				  if(tlogin==1)
    				  {
    					  frame.castle[tx][ty].playerId=tid;
    					  frame.castle[tx][ty].mySetPic("img/castle"
    							  +sharedData.random(2, 4)+".jpg");
    					  frame.castle[tx][ty].setEnabled(true);
    					  //frame.聊天记录.append("玩家"+tcastlename+"进入游戏\n");	  
    				  }
    				  else if(tlogin==2)
    				  {
    					  frame.castle[tx][ty].playerId=tid;
    					  frame.castle[tx][ty].mySetPic("img/castle5.jpg");
    					  frame.castle[tx][ty].setEnabled(true);
    				  }
    				  else if(tlogin==0)
    				  {
    					  frame.castle[tx][ty].mySetPic("img/castle6.jpg");
    					  frame.castle[tx][ty].setEnabled(false);
    					  frame.聊天记录.append("玩家"+tcastlename+"离开游戏\n");	 
    				  }
    				  else ;	
    				  }
	              }	    
	              else if(t==mySocketData.BATTLE)
	              {
	            	  int next=Integer.parseInt(fenxi.nextToken());
	            	  if(next==mySocketData.CHAT)
	            	  {
	            		  String aa=fenxi.nextToken(); 
	            		  frame.战斗信息.append(aa+"\n");	            		  
	            	  }
	            	  else if(next==mySocketData.BEGIN)
	            	  {	            		  
		            	  int i=Integer.parseInt(fenxi.nextToken());              	
		            	  frame.h.id=i;  
			              	frame.h.castlename=fenxi.nextToken();           	
			              	frame.h.x=Integer.parseInt(fenxi.nextToken());
			              	frame.h.y=Integer.parseInt(fenxi.nextToken());
			              	frame.h.areaMax=Integer.parseInt(fenxi.nextToken());
			              	frame.h.areaNow=Integer.parseInt(fenxi.nextToken());
			              	frame.h.gold=Integer.parseInt(fenxi.nextToken());
			              	frame.h.goldpm=Integer.parseInt(fenxi.nextToken());
			              	frame.h.riceMax=Integer.parseInt(fenxi.nextToken());
			              	frame.h.riceNow=Integer.parseInt(fenxi.nextToken());
			              	frame.h.login=Integer.parseInt(fenxi.nextToken());
			              	frame.h.hallNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.marketNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.fieldNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.knightHallNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.footmanHallNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.archerHallNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.towerNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.knightNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.footmanNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.archerNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.inBattle=Integer.parseInt(fenxi.nextToken());
			              	frame.h.bashRate=Integer.parseInt(fenxi.nextToken());
			              	frame.h.isDefence=Integer.parseInt(fenxi.nextToken());
			              	frame.h.MP=Integer.parseInt(fenxi.nextToken());
			              	frame.h.hero=Integer.parseInt(fenxi.nextToken());
		            		  frame.battlebegin();
	            		  frame.updateInfo();
	            		  frame.战斗信息.append("战斗开始\n");	
	            	  }
	            	  else if(next==mySocketData.INFOMATION)
	            	  {
	            		  player aa=new player();
		            	  int i=Integer.parseInt(fenxi.nextToken());              	
		              	aa.id=i;  
		              	aa.castlename=fenxi.nextToken();           	
		              	aa.x=Integer.parseInt(fenxi.nextToken());
		              	aa.y=Integer.parseInt(fenxi.nextToken());
		              	aa.areaMax=Integer.parseInt(fenxi.nextToken());
		              	aa.areaNow=Integer.parseInt(fenxi.nextToken());
		              	aa.gold=Integer.parseInt(fenxi.nextToken());
		              	aa.goldpm=Integer.parseInt(fenxi.nextToken());
		              	aa.riceMax=Integer.parseInt(fenxi.nextToken());
		              	aa.riceNow=Integer.parseInt(fenxi.nextToken());
		              	aa.login=Integer.parseInt(fenxi.nextToken());
		              	aa.hallNum=Integer.parseInt(fenxi.nextToken());
		              	aa.marketNum=Integer.parseInt(fenxi.nextToken());
		              	aa.fieldNum=Integer.parseInt(fenxi.nextToken());
		              	aa.knightHallNum=Integer.parseInt(fenxi.nextToken());
		              	aa.footmanHallNum=Integer.parseInt(fenxi.nextToken());
		              	aa.archerHallNum=Integer.parseInt(fenxi.nextToken());
		              	aa.towerNum=Integer.parseInt(fenxi.nextToken());
		              	aa.knightNum=Integer.parseInt(fenxi.nextToken());
		              	aa.footmanNum=Integer.parseInt(fenxi.nextToken());
		              	aa.archerNum=Integer.parseInt(fenxi.nextToken());
		              	aa.inBattle=Integer.parseInt(fenxi.nextToken());
		              	aa.bashRate=Integer.parseInt(fenxi.nextToken());
		              	aa.isDefence=Integer.parseInt(fenxi.nextToken());
		              	aa.MP=Integer.parseInt(fenxi.nextToken());
		              	aa.hero=Integer.parseInt(fenxi.nextToken());	            		 
	            		  frame.updateInfo();	            		 
	            	  }
	            	  else if(next==mySocketData.END)
	            	  {
	            		 // frame.聊天记录.append("收到服务器战斗结束信息\n");
	            		  frame.escape=1;
	            		  frame.battleend();
	            		  frame.战斗信息.append("战斗结束\n");	    
	            	  }	   
	            	  else if(next==mySocketData.READY)
	            	  {	            		  
		            	  int i=Integer.parseInt(fenxi.nextToken());              	
		            	  frame.h.id=i;  
			              	frame.h.castlename=fenxi.nextToken();           	
			              	frame.h.x=Integer.parseInt(fenxi.nextToken());
			              	frame.h.y=Integer.parseInt(fenxi.nextToken());
			              	frame.h.areaMax=Integer.parseInt(fenxi.nextToken());
			              	frame.h.areaNow=Integer.parseInt(fenxi.nextToken());
			              	frame.h.gold=Integer.parseInt(fenxi.nextToken());
			              	frame.h.goldpm=Integer.parseInt(fenxi.nextToken());
			              	frame.h.riceMax=Integer.parseInt(fenxi.nextToken());
			              	frame.h.riceNow=Integer.parseInt(fenxi.nextToken());
			              	frame.h.login=Integer.parseInt(fenxi.nextToken());
			              	frame.h.hallNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.marketNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.fieldNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.knightHallNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.footmanHallNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.archerHallNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.towerNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.knightNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.footmanNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.archerNum=Integer.parseInt(fenxi.nextToken());
			              	frame.h.inBattle=Integer.parseInt(fenxi.nextToken());
			              	frame.h.bashRate=Integer.parseInt(fenxi.nextToken());
			              	frame.h.isDefence=Integer.parseInt(fenxi.nextToken());
			              	frame.h.MP=Integer.parseInt(fenxi.nextToken());
			              	frame.h.hero=Integer.parseInt(fenxi.nextToken());		            	
	            		frame.bthread=new Thread(frame);  
		              	frame.bthread.start();
	            		  frame.战斗信息.append("下一回合\n");	
	            	  }
	              }	             
	          }
	          catch(Exception e)
	          {
	        	  frame.聊天记录.append(e+"\n");
	        	  try{
	    			  socket.close();
	    			  frame.聊天记录.append("与服务器连接中断\n");
    				  frame.聊天记录.append(e+"\n");
    				  try{
    					  Thread.sleep(3000);
    				  }
    				  catch(Exception e2){}
	    			  System.exit(1);
	    			  }
	    			  catch(Exception ee)
	    			  {
	    				  frame.聊天记录.append("与服务器连接中断\n");
	    				  frame.聊天记录.append(e+"\n");
	    				  try{
	    					  Thread.sleep(3000);
	    				  }
	    				  catch(Exception e2){}
	    				  System.exit(1);
	    			  }
	          }
		  }
	}
}


public class Client implements ActionListener{
	login loginframe;
	Client my;
	mainFrame mainframe;
	myDB db;
	ClientThread com;
	public static void main(String []args)
	{
		Client x=new Client(); 
		x.loginframe=new login("登陆",x);
		x.loginframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  
		x.loginframe.setVisible(true);
		x.loginframe.setIconImage(x.loginframe.getToolkit().getImage("img/icon.jpg"));
				
	}	
	public void actionPerformed(ActionEvent e)
	{ if (e.getSource()==loginframe.yes)
	{
		loginframe.message=login.YES;
		loginframe.id=loginframe.text1.getText();
		loginframe.pw= new String(loginframe.text2.getPassword());
		loginframe.ip=loginframe.text3.getText();
		if(loginframe.id==""||loginframe.pw==""||loginframe.ip==""
			||loginframe.text4.getText()=="")
		{
			return ;
		}
		try{
		loginframe.port=Integer.parseInt(loginframe.text4.getText());
		}
		catch(Exception ee){return ;}
		loginframe.setVisible(false) ;
		
		db=new myDB();
		db.ccreateAll();
		try{
		Thread.sleep(50);
		}
		catch(Exception eex){}
		player newplayer=new player();		
		mainFrame y=new mainFrame("JAVA无双",db,newplayer);			
		y.setIconImage(y.getToolkit().getImage("img/icon.jpg"));
		y.setVisible(true);
		y.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 	
		
		ClientThread thread=new ClientThread(y,db,loginframe);
		thread.start();	
		y.com=thread;
	}
	else if(e.getSource()==loginframe.no)
	{
		loginframe.message=login.NO; System.exit(1);
	}
	}
	
}
