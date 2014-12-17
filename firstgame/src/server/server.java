package server;
//�������˼��������˴���ͨ������ 1750��
import sharedData.*;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.UIManager;
//��Ǯ�����߳���
class clockgold extends Thread
{
	ServerThread up;
	myDB db;
	clockgold(ServerThread up,myDB db)
	{
		this.up=up;
		this.db=db;
	}
	public void run()
	{
		while(true)
		{
			try{
				Thread.sleep(1000*60);//ÿ��1���Ӹ���һ��
			}
			catch(Exception e){}
			for(int i=1;i<50;i++)
			{
				if(db.player[i].id==0){continue;}
				db.player[i].gold+=db.player[i].goldpm;
				if(db.player[i].login==1)
				{					
					up.thread[i].send(mySocketData.INITPLAYER
							  +" "+db.player[i].toMyString());
				}				
			}
			db.ssaveAll();
		}
	}
}

//�����߳�
class ServerThread extends Thread
{
  ServerSocket serverSocket;
  Socket clientSocket[];
  DataInputStream in;
  DataOutputStream out;  
  ListenThread thread[];
  mainFrame frame;
  myDB db;
  Socket tempsocket;
  ServerThread(mainFrame frame,myDB db,int port)
  {
      this.frame = frame;
      this.db=db;
      clientSocket=new Socket[50];
      thread=new ListenThread[50];
      try
      {
          serverSocket = new ServerSocket(port); 
      }
      catch(Exception e)
      {
    	  return;
      }
  }
  

  public void run()
  {
	  frame.�����¼.append("�����������������ڼ���...\n");
	  clockgold goldup=new clockgold(this,db);
	  goldup.start();
      try
      {    	  
          while(true)
          {            	  
              tempsocket = serverSocket.accept();
              ListenThread x=new ListenThread(this,tempsocket,db);    
              x.start();
          }
      }
      catch(Exception e)
      {
      }

      try
      {
    	  for(int i=1;i<50;i++)
    	  {
    		  db.user[i].islogin=0;
    		  db.player[i].login=0;
    		  thread[i].stop();
    	  }
          serverSocket.close();
      }
      catch(Exception e)
      {   
		  frame.�����¼.append(e+"\n");
      }

  }
  //����ս������ã��ж��Ƿ�ʼս��������ս���߳�
  public void battle(int attack,int defend)
  {
	  if(db.player[defend].login==0){}	 
	  else if(db.player[defend].isfail()==1)
	  {
		  thread[attack].send(mySocketData.CHAT+" "
				  +db.player[defend].castlename+"̫���ˣ���Ҫ�۸��ˣ�");
		  return ;
	  }
	  else if(db.player[defend].login==1)
	  {
		  if(db.player[defend].inBattle==1)
		  {
			  thread[attack].send(mySocketData.CHAT+" "
					  +db.player[defend].castlename+"��ս����!");
		  }
		  else 	
		  {
			  db.player[attack].battleinit(db);
			  db.player[defend].battleinit(db);
			  db.player[defend].isDefence=1;
			  db.player[attack].isDefence=0;
			  thread[attack].send(mySocketData.BATTLE+" "
					  +mySocketData.BEGIN+" "
					  +db.player[defend].toMyString());
			  thread[defend].send(mySocketData.BATTLE+" "
					  +mySocketData.BEGIN+" "
					  +db.player[attack].toMyString());
			  thread[defend].send(mySocketData.CHAT+" "
					  +db.player[attack].castlename+"�����㣡��");
			  BattleThread ba=new BattleThread(this,db,attack,defend);
			  ba.start();
		  }
	  }
	  else if(db.player[defend].login==2)
	  {
		  if(db.player[defend].inBattle==1)
		  {
			  thread[attack].send(mySocketData.CHAT+" "
					  +db.player[defend].castlename+"��ս����!");
		  }
		  else 	
		  {
			  db.player[attack].battleinit(db);
			  db.player[defend].battleinit(db);
			  db.player[defend].isDefence=1;
			  db.player[attack].isDefence=0;
			  thread[attack].send(mySocketData.BATTLE+" "
					  +mySocketData.BEGIN+" "
					  +db.player[defend].toMyString());
			  BattleThread ba=new BattleThread(this,db,attack,defend);
			  ba.start();
		  }
		  return ;
	  }
	  
  }

//������������ҷ���
  public synchronized void sendtoAll(String s) {
	  int j=0;
	  for(int i=1;i<50;i++)
      {
    	  if(db.user[i].islogin==1)
    	  {
    		  thread[i].send(s);
    		  j=1;
    	  }
      }
	  if(j==0)frame.�����¼.append("����û���˵�½\n");
  }
  //������������ҷ��͵������Լ�����
  public synchronized void sendtoAll(String s,int id) {
	  for(int i=1;i<50;i++)
      {
    	  if(db.user[i].islogin==1&&id!=i)
    	  {
    		  thread[i].send(s);
    	  }
      }
  }
  //������ҵ�½�ĳ�ʼ������

  public synchronized void initClientInfo(int id)
  {
	  frame.�����¼.append("�û�"+id+"�ѱ���ʼ����\n");	 
	  thread[id].send(mySocketData.INITPLAYER+" "+db.player[id].toMyString());  
	  thread[id].send(mySocketData.INITHERO+" "+db.hero[db.player[id].hero].toMyString());  
      for(int i=1;i<50;i++)
      {
    	  if(db.player[i].id!=0&&db.player[i].id!=id)
    	  {
    		  thread[id].send(mySocketData.LOGIN+" "    				  
    				  +db.player[i].id+" "
    				  +db.player[i].castlename+" "
    				  +db.player[i].x+" "
    				  +db.player[i].y+" "
    				  +db.player[i].login);
    	  }
      }
  }
  //������ҵ�½���������ͨ��

  public synchronized void newlogin(int id)
  {
	  frame.�����¼.append("�û�"+id+"������Ϸ��\n");
      for(int i=1;i<50;i++)
      {
    	  if(db.user[i].islogin==1&&id!=i)
    	  {
    		  thread[i].send(mySocketData.LOGIN+" "    				  
    				  +db.player[id].id+" "
    				  +db.player[id].castlename+" "
    				  +db.player[id].x+" "
    				  +db.player[id].y+" "
    				  +db.player[id].login);
    	  }
      }
  }
//������˳����������ͨ��
  public synchronized void newlogout(int id)
  {
	  frame.�����¼.append("�û�"+id+"�뿪��Ϸ��\n");
	  for(int i=1;i<50;i++)
      {
    	  if(db.user[i].islogin==1)
    	  {
    		  thread[i].send(mySocketData.LOGOUT+" "
    				  +db.player[id].id+" "
    				  +db.player[id].castlename+" "
    				  +db.player[id].x+" "
    				  +db.player[id].y+" "
    				  +db.player[id].login);
    	  }
      }
  }
}

//�����߳�
class ListenThread extends Thread
{
  ServerThread serverThread;
  DataInputStream in;
  DataOutputStream out;
  Socket socket;
  myDB db;
  player player;
  int id;
  int login;
  int logout;
  int READY;
  int buffer[]=new int[6];
  int bufferflag;
  ListenThread(ServerThread serverThread,Socket socket,myDB db)
  {	
	  serverThread.frame.�����¼.append("�����߳�����"+"\n");
	  this.db=db;
      this.serverThread=serverThread;
      this.socket=socket;
      try
      {
          in = new DataInputStream(socket.getInputStream());
          out = new DataOutputStream(socket.getOutputStream());
      }catch(Exception e)
      {
      }      
  }
  //������Ϣ
  public void send(String s)
  {
	  try{
		  out.writeUTF(s);
	  }
	  catch (Exception e) {
		  serverThread.frame.�����¼.append(e+"\n");
		  try{
			  socket.close();
			  }
			  catch(Exception ee)
			  {
				  serverThread.frame.�����¼.append("�û�"+id+"�����ж�ʧ��\n");
				  serverThread.frame.�����¼.append(e+"\n");
			  }    	  
    	  db.user[id].islogin=0;
		  db.player[id].login=0;		  
		  login=0;
		  serverThread.newlogout(id);
    	  return ;
      }
  }

  public void run()
  {
      String s="";
      //��֤��½��Ϣ
      try
      {
          s=in.readUTF();
          serverThread.frame.�����¼.append("�յ��û���Ϣ"+s+"\n");
          StringTokenizer fenxi=new StringTokenizer(s," ");  
          int t=Integer.parseInt(fenxi.nextToken());
          if(t==mySocketData.LOGIN)
          {
        	  String username=fenxi.nextToken();
        	  String userpw=fenxi.nextToken();
        	  serverThread.frame.�����¼.append("id"+username+"\n");
        	  serverThread.frame.�����¼.append("pw"+userpw+"\n");
        	  for(int i=1;i<50;i++)
        	  {
        		  if(db.user[i].id!=0&&db.user[i].check(username, userpw)==1)
        		  {
        			  if(db.user[i].islogin==1){return;}
        			  id=db.user[i].id;
        			  serverThread.clientSocket[id]=socket;
        			  serverThread.thread[id]=this;
        			  db.user[i].islogin=1;
        			  db.player[i].login=1;
        			  login=1;
        			  serverThread.initClientInfo(i);
        			  serverThread.newlogin(id);
        			  break;
        		  }        		 
        	  }
        	  if(login!=1){socket.close();return ;}
          }
          else  {socket.close();return;}
    	
      }
      catch(Exception e)
      {
    	  serverThread.frame.�����¼.append(e+"\n");
    	  try{
			  socket.close();
			  }
			  catch(Exception ee)
			  {
				  serverThread.frame.�����¼.append("�û�"+id+"�����ж�ʧ��\n");
				  serverThread.frame.�����¼.append(e+"\n");
			  }    	 
    	  db.user[id].islogin=0;
		  db.player[id].login=0;
		  login=0;
		  serverThread.newlogout(id);
    	  return ;
      }
      //��½����м�����������
      while(true)
      {         
          try
          {
              s=in.readUTF();      
              serverThread.frame.�����¼.append("�յ��û���Ϣ"+s+"\n");
              //�����ӵĴ���
              StringTokenizer fenxi=new StringTokenizer(s," ");  
              int t=Integer.parseInt(fenxi.nextToken());
              //��������
              if(t==mySocketData.CHAT)
              {
            	  String chat=fenxi.nextToken();	 
            	  while(fenxi.hasMoreTokens())
            	  {
            		  chat=chat+" "+fenxi.nextToken();
            	  }
            	  serverThread.sendtoAll(mySocketData.CHAT+" "+chat, id);
            	  serverThread.frame.�����¼.append(chat+"\n");	            	  
              }
              //��������
              else if(t==mySocketData.ATTACK)
              {
            	  int aimId=Integer.parseInt(fenxi.nextToken()); 
            	  serverThread.battle(this.id, aimId);            	  
            	  serverThread.frame.�����¼.append(id+"���󹥻�"+aimId+"\n");	            	  
              }
              //�鿴���������Ϣ
              else if(t==mySocketData.INFOMATION)
              {
            	  int aimId=Integer.parseInt(fenxi.nextToken()); 
            	  this.send(mySocketData.INFOMATION+" "+db.player[aimId].toMyString()
            			  +" "+db.hero[db.player[aimId].hero].level);
            	  serverThread.frame.�����¼.append(id+"����鿴"+aimId+"\n");	            	  
              }
              //������Ʒ
              else if(t==mySocketData.BUY)
              {
            	  int aimId=Integer.parseInt(fenxi.nextToken()); 
            	  if(db.items[aimId].price<=db.player[id].gold)
            	  {
            		  db.player[id].gold-=db.items[aimId].price;
            		  db.hero[db.player[id].hero].addItems(db, aimId);
            		  send(mySocketData.INITHERO
    						  +" "+db.hero[db.player[id].hero].toMyString());
    				  send(mySocketData.INITPLAYER
    						  +" "+db.player[id].toMyString());	
    				  send(mySocketData.CHAT
    						  +" "+"ϵͳ��Ϣ��������Ʒ�ɹ���");	
            	  }
            	  else {
            		  send(mySocketData.CHAT
    						  +" "+"ϵͳ��Ϣ��������Ʒʧ�ܣ�");	
            	  }
              }
              //���������ʩ
              else if(t==mySocketData.BUILD||t==mySocketData.UNBUILD)
              {
            	  int aim=Integer.parseInt(fenxi.nextToken()); 
            	  int num=Integer.parseInt(fenxi.nextToken());
            	  if(t==mySocketData.UNBUILD){num=-num;}   
            	  if(aim==mySocketData.HALL)
            	  {
            		  db.player[this.id].buildHall(db,num);
            	  }
            	  else if(aim==mySocketData.MARKET)
            	  {
            		  db.player[this.id].buildMarket(db,num);
            	  }
            	  else if(aim==mySocketData.FIELD)
            	  {
            		  db.player[this.id].buildField(db,num);
            	  }
            	  else if(aim==mySocketData.KNIGHTHALL)
            	  {
            		  db.player[this.id].buildKnightHall(db,num);
            	  }
            	  else if(aim==mySocketData.FOOTMANHALL)
            	  {
            		  db.player[this.id].buildFootmanHall(db,num);
            	  }
            	  else if(aim==mySocketData.ARCHERHALL)
            	  {
            		  db.player[this.id].buildArcherHall(db,num);
            	  }
            	  else if(aim==mySocketData.TOWER)
            	  {
            		  db.player[this.id].buildTower(db,num);
            	  }
            	  send(mySocketData.INITPLAYER
						  +" "+db.player[id].toMyString());
              }
              //��ļ���ɢ
              else if(t==mySocketData.HIRE||t==mySocketData.UNHIRE)
              {
            	  int aim=Integer.parseInt(fenxi.nextToken()); 
            	  int num=Integer.parseInt(fenxi.nextToken());
            	  if(t==mySocketData.UNHIRE){num=-num;}   
            	  if(aim==mySocketData.KNIGHT)
            	  {
            		  db.player[this.id].hireKnight(db,num);
            	  }
            	  else if(aim==mySocketData.FOOTMAN)
            	  {
            		  db.player[this.id].hireFootman(db,num);
            	  }
            	  else if(aim==mySocketData.ARCHER)
            	  {
            		  db.player[this.id].hireArcher(db,num);
            	  }
            	  else if(aim==mySocketData.HERO)
            	  {
            		  //��ļӢ��
            	  }
            	  send(mySocketData.INITPLAYER
						  +" "+db.player[id].toMyString());
              }
              //ս�����������
              else if(t==mySocketData.BATTLE)
              {
            	  int aimId=Integer.parseInt(fenxi.nextToken()); 
            	  if(aimId==mySocketData.HERO)
            	  {
            		  buffer[1]=Integer.parseInt(fenxi.nextToken()); 
            		  serverThread.frame.�����¼.append(id+"Ӣ�۹���\n");	
            	  }
            	  else if(aimId==mySocketData.ATTACK)
            	  {
            		  int temp=Integer.parseInt(fenxi.nextToken()); 
            		  buffer[temp/10]=temp;
            		  serverThread.frame.�����¼.append(id+"����\n");	
            	  }
            	  else if(aimId==mySocketData.ESCAPE)
            	  {
            		  buffer[1]=30; 
            		  this.READY=1;
            		  serverThread.frame.�����¼.append(id+"����\n");	
            		  serverThread.frame.�����¼.append("READY="+READY+"\n");	
            		  serverThread.frame.�����¼.append("buffer[1]="+buffer[1]+"\n");	
            	  }
            	  else if(aimId==mySocketData.READY)
            	  {
            		  this.READY=1;
            	  }  
            	              	  
              }
          }
          catch(Exception e)
          {
        	  serverThread.frame.�����¼.append("�û�"+id+"�����ж�\n");
        	  if(db.player[id].inBattle==1){this.logout=1;}
        	  serverThread.frame.�����¼.append(e+"\n");
        	  
        	  db.user[id].islogin=0;
			  db.player[id].login=0;
			  login=0;
			  serverThread.newlogout(id);
			  try{
				  Thread.sleep(10000);
				  socket.close();
				  db.user[id].islogin=0;
				  db.player[id].login=0;
				  login=0;
			  }
			  catch(Exception ee)
			  {
				  serverThread.frame.�����¼.append("�û�"+id+"�����ж�ʧ��\n");
				  serverThread.frame.�����¼.append(e+"\n");
				  db.user[id].islogin=0;
				  db.player[id].login=0;
				  login=0;
			  }
        	  return ;
          }
      } 
  }
}
//ս���߳�
class BattleThread extends Thread
{
  ServerThread serverThread;
  myDB db;
  int id;
  int aid;
  int did;
  int ahurt;
  int dhurt;
  int aap[]=new int[6];
  int adp[]=new int[6];
  int dap[]=new int[6];
  int ddp[]=new int[6];
  BattleThread(ServerThread serverThread,myDB db,int a,int d)
  {	
	  this.db=db;
      this.serverThread=serverThread;        
      aid=a;
      did=d;
  }
  //���ݹ����������������˺�
  public int computeAvsB(int a,int b)
  {
	  int x1=0;int x2=0,x3=0,x4=0,x5=0,x6=0;
	  if(a>1000000)x1=(int)((a-1000000)*0.2);
	  if(a>100000)x2=(int)((a-100000)*0.3);
	  if(a>10000)x3=(int)((a-10000)*0.5);
	  if(a>1000)x4=(int)((a-1000)*0.7);
	  if(a>100)x5=(int)((a-100)*0.8);
	  if(a<=100)x6=a;
	  else x6=100;
	  a=x1+x2+x3+x4+x5+x6;
	  int y1=0,y2=0,y3=0,y4=0,y5=0,y6=0;
	  if(b>1000000)y1=(int)((b-1000000)*0.2);
	  if(b>100000)y2=(int)((b-100000)*0.3);
	  if(b>10000)y3=(int)((b-10000)*0.4);
	  if(b>1000)y4=(int)((b-1000)*0.5);
	  if(b>100)y5=(int)((b-100)*0.7);
	  if(b<=100)y6=b;
	  else y6=100;
	  b=y1+y2+y3+y4+y5+y6;
	  int y=(int)Math.sqrt(Math.sqrt(b)+1);
	  int x=a/y/10;
	  return x;
  }
  //���˳���ʧ�ܵĳͷ�
  public void logoutfail(int id)
  {
	  db.player[id].knightNum/=2;
	  db.player[id].footmanNum/=2;
	  db.player[id].archerNum/=2;
	  db.player[id].update();
  }
  //��ս��˫��ͨ��
  public void send(String s)
  {	  
	  send(s,aid);
	  send(s,did);
	  return ;
      
  }
  //��ս����ĳһ��ͨ��
  public void send(String s,int id)
  {	  
	  if(db.player[id].login!=2)
	  {
		  this.serverThread.thread[id].send(s);
	  }
	  return ;
      
  }
  //����ս����Ϣ
  public void sendBattle(String s)
  {
	  send(mySocketData.BATTLE+" "+mySocketData.CHAT+" "+s);
  }
  public void run()
  {
	  int bash=0;
	  String bs[]={"","ʿ�����ǣ�"};
	  if(db.player[did].login==2)  //����Զ�ս
      {
		  db.player[aid].battleinit(db);
		  db.player[did].battleinit(db);
		  serverThread.thread[aid].READY=0;
		  while(true)
		  {		
			  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
					  +" "+db.player[did].toMyString(),aid);	
			  send(mySocketData.INITPLAYER
					  +" "+db.player[aid].toMyString(),aid);
			  if(this.serverThread.thread[aid].logout==1)
			  {
				  logoutfail(aid);		
				  db.player[aid].inBattle=0;
				  db.player[did].inBattle=0;
				  db.player[did].recover();
				  return;
			  }
			  else if(db.player[aid].isfail()==1)
			  {
				  db.hero[db.player[aid].hero].addExp(dhurt/2*db.hero[db.player[did].hero].level);
				  send(mySocketData.INITHERO
						  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
				  send(mySocketData.CHAT+" "+"ս��ʧ�ܣ����"+dhurt/2*db.hero[db.player[did].hero].level+"�㾭��ֵ��",aid);
				
				  db.player[aid].inBattle=0;
				  db.player[did].inBattle=0;
				  send(mySocketData.INITPLAYER
						  +" "+db.player[aid].toMyString(),aid);
				  send(mySocketData.BATTLE+" "+mySocketData.END,aid);				  
				  db.player[did].recover();
				  serverThread.thread[aid].READY=0;
				  return;
			  }
			  else if(db.player[did].isfail()==1)
			  {
				  db.hero[db.player[aid].hero].addExp(dhurt*db.hero[db.player[did].hero].level);	
				  db.player[aid].gold+=db.player[did].gold/2;
				  db.player[did].gold/=2;
				  db.player[aid].inBattle=0;
				  db.player[did].inBattle=0;
				  send(mySocketData.CHAT+" "+"ս��ʤ�������"+dhurt*db.hero[db.player[did].hero].level+"�㾭��ֵ,�õ�"
						  +db.player[did].gold+"��ҡ�",aid);				  
				  send(mySocketData.INITHERO
						  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
				  send(mySocketData.INITPLAYER
						  +" "+db.player[aid].toMyString(),aid);				  
				  send(mySocketData.BATTLE+" "+mySocketData.END,aid);
				  db.player[did].recover();
				  serverThread.thread[aid].READY=0;
				  return;
			  }
			  if(this.serverThread.thread[aid].READY!=1)
			  {
				  try{
				  Thread.sleep(1000);
				  }
				  catch(Exception eee){}
			  }
			  else if(this.serverThread.thread[aid].READY==1)
			  {
				  aap[1]=db.player[aid].computeHAP(db);
				  aap[2]=db.player[aid].computeKAP(db);
				  aap[3]=db.player[aid].computeFAP(db);
				  aap[4]=db.player[aid].computeAAP(db);
				  aap[5]=db.player[aid].computeTAP(db);				  
				  adp[2]=db.player[aid].computeKDP(db);
				  adp[3]=db.player[aid].computeFDP(db);
				  adp[4]=db.player[aid].computeADP(db);
				  adp[5]=db.player[aid].computeTDP(db);
				  dap[1]=db.player[did].computeHAP(db);
				  dap[2]=db.player[did].computeKAP(db);
				  dap[3]=db.player[did].computeFAP(db);
				  dap[4]=db.player[did].computeAAP(db);
				  dap[5]=db.player[did].computeTAP(db);				  
				  ddp[2]=db.player[did].computeKDP(db);
				  ddp[3]=db.player[did].computeFDP(db);
				  ddp[4]=db.player[did].computeADP(db);
				  ddp[5]=db.player[did].computeTDP(db);				  
				  int t=serverThread.thread[aid].buffer[1];					 
				  if(t/10==1)//�佫����
				  {
					  int x=aap[1]/10;
					  bash=aap[1]%10;
					  x=sharedData.random((int)x-5, (int)x+5);
					  if(t%10==2)
					  {
						  int temp=db.player[did].knightNum;
						  db.player[did].knightNum-=x;
						  if(db.player[did].knightNum<0)db.player[did].knightNum=0;
						  dhurt+=temp-db.player[did].knightNum;
						  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
								  +" "+db.player[did].toMyString(),aid);	
						//  send(mySocketData.DROP+" "+mySocketData.KNIGHT+" "+x,aid);
						  String s=db.hero[db.player[aid].hero].name
						  		+"���˹�����"+db.hero[db.player[did].hero].name
						  		+"�����"+bs[bash]+"���"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==3)
					  {
						  int temp=db.player[did].footmanNum;
						  db.player[did].footmanNum-=x;
						  if(db.player[did].footmanNum<0)db.player[did].footmanNum=0;
						  dhurt+=temp-db.player[did].footmanNum;
						  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
								  +" "+db.player[did].toMyString(),aid);	
						//  send(mySocketData.DROP+" "+mySocketData.FOOTMAN+" "+x,aid);
						  String s=db.hero[db.player[aid].hero].name
						  		+"���˹�����"+db.hero[db.player[did].hero].name
						  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==4)
					  {
						  int temp=db.player[did].archerNum;
						  db.player[did].archerNum-=x;
						  if(db.player[did].archerNum<0)db.player[did].archerNum=0;
						  dhurt+=temp-db.player[did].archerNum;
						  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
								  +" "+db.player[did].toMyString(),aid);	
						//  send(mySocketData.DROP+" "+mySocketData.ARCHER+" "+x,aid);
						  String s=db.hero[db.player[aid].hero].name
						  		+"���˹�����"+db.hero[db.player[did].hero].name
						  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==5)
					  {						  
						  db.player[did].towerNum-=x;
						  if(db.player[did].towerNum<0)db.player[did].towerNum=0;
						  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
								  +" "+db.player[did].toMyString(),aid);	
						//  send(mySocketData.DROP+" "+mySocketData.TOWER+" "+x,aid);
						  String s=db.hero[db.player[aid].hero].name
						  		+"���˹�����"+db.hero[db.player[did].hero].name
						  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
				  }
				  else if(t/10==2)//�佫ս��
				  {
					  db.player[aid].MP-=db.magic[db.hero[db.player[aid].hero].magic2].MP;
					  int x=db.magic[db.hero[db.player[aid].hero].magic2]
					                .compute(db, db.player[aid].hero, db.player[did].hero);
					  if(t%10==2)
					  {
						  int temp=db.player[did].knightNum;
						  db.player[did].knightNum-=x;
						  if(db.player[did].knightNum<0)db.player[did].knightNum=0;
						  dhurt+=temp-db.player[did].knightNum;
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
								  +" "+db.player[did].toMyString(),aid);	
						//  send(mySocketData.DROP+" "+mySocketData.KNIGHT+" "+x,aid);
						  String s=db.hero[db.player[aid].hero].name
						  		+"����ս��"
						  		+db.magic[db.hero[db.player[aid].hero].magic2].name
						  		+"��"+db.hero[db.player[did].hero].name
						  		+"��������"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==3)
					  {
						  int temp=db.player[did].footmanNum;
						  db.player[did].footmanNum-=x;
						  if(db.player[did].footmanNum<0)db.player[did].footmanNum=0;
						  dhurt+=temp-db.player[did].footmanNum;
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
								  +" "+db.player[did].toMyString(),aid);	
					//	  send(mySocketData.DROP+" "+mySocketData.FOOTMAN+" "+x,aid);
						  String s=db.hero[db.player[aid].hero].name
						  	+"����ս��"
					  		+db.magic[db.hero[db.player[aid].hero].magic2].name
					  		+db.hero[db.player[did].hero].name
						  		+"���������"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==4)
					  {
						  int temp=db.player[did].archerNum;
						  db.player[did].archerNum-=x;
						  if(db.player[did].archerNum<0)db.player[did].archerNum=0;
						  dhurt+=temp-db.player[did].archerNum;
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
								  +" "+db.player[did].toMyString(),aid);	
						//  send(mySocketData.DROP+" "+mySocketData.ARCHER+" "+x,aid);
						  String s=db.hero[db.player[aid].hero].name
						  +"����ս��"
					  		+db.magic[db.hero[db.player[aid].hero].magic2].name
					  		+db.hero[db.player[did].hero].name
						  		+"���������"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==5)
					  {						  
						  db.player[did].towerNum-=x;
						  if(db.player[did].towerNum<0)db.player[did].towerNum=0;
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
								  +" "+db.player[did].toMyString(),aid);	
					//	  send(mySocketData.DROP+" "+mySocketData.TOWER+" "+x,aid);
						  String s=db.hero[db.player[aid].hero].name
						  +"����ս��"
					  		+db.magic[db.hero[db.player[aid].hero].magic2].name
					  		+db.hero[db.player[did].hero].name
						  		+"���������"+x+"����λ���˺�!";
						  sendBattle(s);
					  }					  
				  }
				  else if(t/10==3)//�佫����
				  {
					  logoutfail(aid);
					  db.hero[db.player[aid].hero].addExp(dhurt/2*db.hero[db.player[did].hero].level);		
					  send(mySocketData.CHAT+" "+"ս�����������"+dhurt/2*db.hero[db.player[did].hero].level+"�㾭��ֵ��",aid);
					  db.player[aid].inBattle=0;
					  db.player[did].inBattle=0;
					  send(mySocketData.INITHERO
							  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
					  send(mySocketData.INITPLAYER
							  +" "+db.player[aid].toMyString(),aid);	
					  send(mySocketData.BATTLE+" "+mySocketData.END,aid);
					  serverThread.frame.�����¼.append("���ܳɹ���ս�����������Ը���\n");
					  db.player[did].recover();
					  serverThread.thread[aid].READY=0;
					  return;
				  }
				  else ;
				  int x=dap[1]/10;
				  bash=dap[1]%10;
				  int xx=sharedData.random(2,4);
				  int haha=0;
				  while(true)
				  {
					  xx=sharedData.random(2,4);
					  if(xx==2&&db.player[aid].knightNum!=0){break;}
					  if(xx==3&&db.player[aid].footmanNum!=0){break;}
					  if(xx==4&&db.player[aid].archerNum!=0){break;}
					  haha++;
					  if(haha>20)break;
				  }
				  if(xx==2)
				  {
					  int temp=db.player[aid].knightNum;					  
					  if(temp-x>=0)temp=x;
					  ahurt+=temp;
					  db.player[aid].hireKnight(db,-temp);
					  send(mySocketData.INITHERO
							  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
					  send(mySocketData.INITPLAYER
							  +" "+db.player[aid].toMyString(),aid);	
					  String s=db.hero[db.player[did].hero].name
					  		+"���˹�����"+db.hero[db.player[aid].hero].name
					  		+"�����"+bs[bash]+"���"+x+"����λ���˺�!";
					  sendBattle(s);
				  }
				  else if(xx==3)
				  {
					  int temp=db.player[aid].footmanNum;					  
					  if(temp-x>=0)temp=x;
					  ahurt+=temp;
					  db.player[aid].hireFootman(db, -temp);
					  send(mySocketData.INITHERO
							  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
					  send(mySocketData.INITPLAYER
							  +" "+db.player[aid].toMyString(),aid);	
					  String s=db.hero[db.player[did].hero].name
					  		+"���˹�����"+db.hero[db.player[aid].hero].name
					  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
					  sendBattle(s);
				  }
				  if(xx==4)
				  {
					  int temp=db.player[aid].archerNum;					  
					  if(temp-x>=0)temp=x;
					  ahurt+=temp;
					  db.player[aid].hireArcher(db,-temp);
					  send(mySocketData.INITHERO
							  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
					  send(mySocketData.INITPLAYER
							  +" "+db.player[aid].toMyString(),aid);	
					  String s=db.hero[db.player[did].hero].name
					  		+"���˹�����"+db.hero[db.player[aid].hero].name
					  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
					  sendBattle(s);
				  }
				  for(int j=2;j<5;j++)
				  {
					  if(serverThread.thread[aid].buffer[j]==0){continue;}					
					  else 
					  {						 
						  t=serverThread.thread[aid].buffer[j];
						  x=computeAvsB(aap[j]/10,ddp[t%10]);
						  bash=aap[j]%10;
						  String n=null;
						  if(j==2){n="���";}
						  else if(j==3){n="����";}
						  else if(j==4){n="����";}
						  else if(j==5){n="����";}
						  else ;
						  if(t%10==2)
						  {							  
							  int temp=db.player[did].knightNum;
							  db.player[did].knightNum-=x;
							  if(db.player[did].knightNum<0)db.player[did].knightNum=0;
							  dhurt+=temp-db.player[did].knightNum;
							  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
									  +" "+db.player[did].toMyString(),aid);	
						//	  send(mySocketData.DROP+" "+mySocketData.KNIGHT+" "+x,aid);
							  String s=db.hero[db.player[aid].hero].name
							  		+n+"������"+db.hero[db.player[did].hero].name
							  		+"�����"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
						  else if(t%10==3)
						  {
							  int temp=db.player[did].footmanNum;
							  db.player[did].footmanNum-=x;
							  if(db.player[did].footmanNum<0)db.player[did].footmanNum=0;
							  dhurt+=temp-db.player[did].footmanNum;
							  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
									  +" "+db.player[did].toMyString(),aid);	
						//	  send(mySocketData.DROP+" "+mySocketData.FOOTMAN+" "+x,aid);
							  String s=db.hero[db.player[aid].hero].name
							  +n+"������"+db.hero[db.player[did].hero].name
							  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
						  else if(t%10==4)
						  {
							  int temp=db.player[did].archerNum;
							  db.player[did].archerNum-=x;
							  if(db.player[did].archerNum<0)db.player[did].archerNum=0;
							  dhurt+=temp-db.player[did].archerNum;
							  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
									  +" "+db.player[did].toMyString(),aid);	
						//	  send(mySocketData.DROP+" "+mySocketData.ARCHER+" "+x,aid);
							  String s=db.hero[db.player[aid].hero].name
							  +n+"������"+db.hero[db.player[did].hero].name
							  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
						  else if(t%10==5)
						  {						  
							  db.player[did].towerNum-=x;
							  if(db.player[did].towerNum<0)db.player[did].towerNum=0;
							  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
									  +" "+db.player[did].toMyString(),aid);	
						//	  send(mySocketData.DROP+" "+mySocketData.TOWER+" "+x,aid);
							  String s=db.hero[db.player[aid].hero].name
							  +n+"������"+db.hero[db.player[did].hero].name
							  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
					  }
				  }				  
				  for(int j=2;j<6;j++)
				  {
					  if(dap[j]/10==0){continue;}					
					  else 
					  {		
						  xx=sharedData.random(2,4);
						  int haha2=0;
						  while(true)
						  {
							  xx=sharedData.random(2,4);
							  if(xx==2&&db.player[aid].knightNum!=0){break;}
							  if(xx==3&&db.player[aid].footmanNum!=0){break;}
							  if(xx==4&&db.player[aid].archerNum!=0){break;}
							  haha2++;
							  if(haha2>20){break;}
						  }					  
						  x=computeAvsB(dap[j]/10,adp[xx]);
						  bash=dap[j]%10;
						  String n=null;
						  if(j==2){n="���";}
						  else if(j==3){n="����";}
						  else if(j==4){n="����";}
						  else if(j==5){n="����";}
						  else ;
						  if(xx==2)
						  {				
							  int temp=db.player[aid].knightNum;							  
							  if(temp-x>=0)temp=x;
							  ahurt+=temp;
							  db.player[aid].hireKnight(db,-temp);
							  send(mySocketData.INITHERO
									  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
							  send(mySocketData.INITPLAYER
									  +" "+db.player[aid].toMyString(),aid);	
							  String s=db.hero[db.player[did].hero].name
							  +n+"������"+db.hero[db.player[aid].hero].name
							  		+"�����"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
						  if(xx==3)
						  {
							  int temp=db.player[aid].footmanNum;							 
							  if(temp-x>=0)temp=x;
							  ahurt+=temp;
							  db.player[aid].hireFootman(db,-temp);
							  send(mySocketData.INITHERO
									  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
							  send(mySocketData.INITPLAYER
									  +" "+db.player[aid].toMyString(),aid);	
							  String s=db.hero[db.player[did].hero].name
							  +n+"������"+db.hero[db.player[aid].hero].name
							  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
						  if(xx==4)
						  {
							  int temp=db.player[aid].archerNum;							  
							  if(temp-x>=0)temp=x;
							  ahurt+=temp;
							  db.player[aid].hireArcher(db,-temp);
							  send(mySocketData.INITHERO
									  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
							  send(mySocketData.INITPLAYER
									  +" "+db.player[aid].toMyString(),aid);	
							  String s=db.hero[db.player[did].hero].name
							  +n+"������"+db.hero[db.player[aid].hero].name
							  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }						 
					  }
				  }		
				  
				  send(mySocketData.BATTLE+" "+mySocketData.READY+" "
						  +db.player[did].toMyString(),aid);	
				 
				  try{
				  serverThread.thread[aid].out.flush();
				  }
				  catch(Exception eee){}
				  serverThread.thread[aid].READY=0;
				  serverThread.thread[aid].buffer[1]=0;
				  serverThread.thread[aid].buffer[2]=0;
				  serverThread.thread[aid].buffer[3]=0;
				  serverThread.thread[aid].buffer[4]=0;
				  serverThread.thread[aid].buffer[5]=0;	
			  }
			  else ;
		  }
      }
	  else if(db.player[did].login==1)  //���˶�ս
      {
		  db.player[aid].battleinit(db);
		  db.player[did].battleinit(db);
		  serverThread.thread[aid].READY=0;
		  serverThread.thread[did].READY=0;
		  while(true)
		  {
			  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
					  +" "+db.player[did].toMyString(),aid);	
			  send(mySocketData.BATTLE+" "+mySocketData.INFOMATION
					  +" "+db.player[aid].toMyString(),did);	
			  send(mySocketData.INITHERO
					  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
			  send(mySocketData.INITPLAYER
					  +" "+db.player[aid].toMyString(),aid);	
			  send(mySocketData.INITHERO
					  +" "+db.hero[db.player[did].hero].toMyString(),did);
			  send(mySocketData.INITPLAYER
					  +" "+db.player[did].toMyString(),did);	
			  if(this.serverThread.thread[aid].logout==1)
			  {
				  logoutfail(aid);		
				  		  
				  db.hero[db.player[did].hero].addExp(ahurt*db.hero[db.player[aid].hero].level);				  
				  send(mySocketData.CHAT+" "+"�Է��˳���Ϸ��ս�����������"+ahurt*db.hero[db.player[aid].hero].level+"�㾭��ֵ��",did);
				  db.player[aid].inBattle=0;
				  db.player[did].inBattle=0;
				  send(mySocketData.INITHERO
						  +" "+db.hero[db.player[did].hero].toMyString(),did);
				  send(mySocketData.INITPLAYER
						  +" "+db.player[did].toMyString(),did);	
				  send(mySocketData.BATTLE+" "+mySocketData.END,did);				  
				  serverThread.thread[did].READY=0;
				  return;
			  }
			  else if(this.serverThread.thread[did].logout==1)
			  {
				  logoutfail(did);		
				 			  
				  db.hero[db.player[aid].hero].addExp(dhurt*db.hero[db.player[did].hero].level);				  			  
				  db.player[aid].gold+=db.player[did].gold/2;					  
				  send(mySocketData.CHAT+" "+"�Է��˳���Ϸ��ս�����������"+dhurt*db.hero[db.player[aid].hero].level+"�㾭��ֵ,�õ�"
						  +db.player[did].gold/2+"��ҡ�",aid);	
				  db.player[did].gold/=2;
				  db.player[aid].inBattle=0;
				  db.player[did].inBattle=0;
				  send(mySocketData.INITHERO
						  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
				  send(mySocketData.INITPLAYER
						  +" "+db.player[aid].toMyString(),aid);					  
				  send(mySocketData.BATTLE+" "+mySocketData.END,aid);
				  serverThread.thread[aid].READY=0;				  
				  return;
			  }
			  else if(db.player[aid].isfail()==1)
			  {					 				  					  
				  db.hero[db.player[did].hero].addExp(ahurt*db.hero[db.player[aid].hero].level);				  
				  send(mySocketData.CHAT+" "+"ս��ʤ�������"+ahurt*db.hero[db.player[aid].hero].level+"�㾭��ֵ��",did);
				  db.hero[db.player[aid].hero].addExp(dhurt*db.hero[db.player[did].hero].level);				  
				  send(mySocketData.CHAT+" "
						  +"ս��ʧ�ܣ����"+dhurt*db.hero[db.player[did].hero].level+"�㾭��ֵ��",aid);	
				 
				  db.player[did].inBattle=0;
				  db.player[aid].inBattle=0;
				  send(mySocketData.INITHERO
						  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
				  send(mySocketData.INITPLAYER
						  +" "+db.player[aid].toMyString(),aid);	
				  send(mySocketData.INITHERO
						  +" "+db.hero[db.player[did].hero].toMyString(),did);
				  send(mySocketData.INITPLAYER
						  +" "+db.player[did].toMyString(),did);
				  send(mySocketData.BATTLE+" "+mySocketData.END,did);
				  send(mySocketData.BATTLE+" "+mySocketData.END,aid);
				  serverThread.thread[aid].READY=0;
				  serverThread.thread[did].READY=0;
				  return;
			  }
			  else if(db.player[did].isfail()==1)
			  {				 
				  db.player[aid].gold+=db.player[did].gold/2;		
				  db.hero[db.player[did].hero].addExp(ahurt*db.hero[db.player[aid].hero].level);				  
				  send(mySocketData.CHAT+" "+"ս��ʧ�ܣ����"+ahurt*db.hero[db.player[aid].hero].level+"�㾭��ֵ��",did);
				  db.hero[db.player[aid].hero].addExp(dhurt*db.hero[db.player[did].hero].level);				  
				  send(mySocketData.CHAT+" "
						  +"ս��ʤ�������"+dhurt*db.hero[db.player[did].hero].level+"�㾭��ֵ,�õ�"
						  +db.player[did].gold/2+"��ҡ�",aid);	
				  db.player[did].gold/=2;				  
				  db.player[did].inBattle=0;
				  db.player[aid].inBattle=0;
				  send(mySocketData.INITHERO
						  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
				  send(mySocketData.INITPLAYER
						  +" "+db.player[aid].toMyString(),aid);	
				  send(mySocketData.INITHERO
						  +" "+db.hero[db.player[did].hero].toMyString(),did);
				  send(mySocketData.INITPLAYER
						  +" "+db.player[did].toMyString(),did);
				  send(mySocketData.BATTLE+" "+mySocketData.END,did);
				  send(mySocketData.BATTLE+" "+mySocketData.END,aid);
				  serverThread.thread[aid].READY=0;
				  serverThread.thread[did].READY=0;
				  return;
			  }
			  else if(this.serverThread.thread[aid].READY!=1
					  ||this.serverThread.thread[did].READY!=1)
			  {
				  try{
				  Thread.sleep(5000);
				  }
				  catch(Exception eee){}
			  }
			  else if(this.serverThread.thread[aid].READY==1
					  &&this.serverThread.thread[did].READY==1)
			  {
				  aap[1]=db.player[aid].computeHAP(db);
				  aap[2]=db.player[aid].computeKAP(db);
				  aap[3]=db.player[aid].computeFAP(db);
				  aap[4]=db.player[aid].computeAAP(db);
				  aap[5]=db.player[aid].computeTAP(db);				  
				  adp[2]=db.player[aid].computeKDP(db);
				  adp[3]=db.player[aid].computeFDP(db);
				  adp[4]=db.player[aid].computeADP(db);
				  adp[5]=db.player[aid].computeTDP(db);
				  dap[1]=db.player[did].computeHAP(db);
				  dap[2]=db.player[did].computeKAP(db);
				  dap[3]=db.player[did].computeFAP(db);
				  dap[4]=db.player[did].computeAAP(db);
				  dap[5]=db.player[did].computeTAP(db);				  
				  ddp[2]=db.player[did].computeKDP(db);
				  ddp[3]=db.player[did].computeFDP(db);
				  ddp[4]=db.player[did].computeADP(db);
				  ddp[5]=db.player[did].computeTDP(db);
				  int t=serverThread.thread[aid].buffer[1];					  
				  if(t/10==1)//�佫����
				  {
					  int x=aap[1]/10;
					  bash=aap[1]%10;
					  x=sharedData.random((int)x-5, (int)x+5);
					  if(t%10==2)
					  {
						  int temp=db.player[did].knightNum;						  
						  if(temp-x>=0)temp=x;
						  dhurt+=temp;
						  db.player[did].hireKnight(db,-temp);						  
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[did].hero].toMyString(),did);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[did].toMyString(),did);
						  String s=db.hero[db.player[aid].hero].name
						  		+"���˹�����"+db.hero[db.player[did].hero].name
						  		+"�����"+bs[bash]+"���"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==3)
					  {
						  int temp=db.player[did].footmanNum;						  
						  if(temp-x>=0)temp=x;
						  dhurt+=temp;
						  db.player[did].hireFootman(db, -temp);						  
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[did].hero].toMyString(),did);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[did].toMyString(),did);
						  String s=db.hero[db.player[aid].hero].name
						  		+"���˹�����"+db.hero[db.player[did].hero].name
						  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==4)
					  {
						  int temp=db.player[did].archerNum;
						  if(temp-x>=0)temp=x;
						  dhurt+=temp;
						  db.player[did].hireArcher(db,-temp);
						  	
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[did].hero].toMyString(),did);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[did].toMyString(),did);
						  String s=db.hero[db.player[aid].hero].name
						  		+"���˹�����"+db.hero[db.player[did].hero].name
						  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==5)
					  {	
						  int temp=db.player[did].towerNum;
						  if(temp-x>=0)temp=x;
						  db.player[did].buildTower(db,-temp);
						  
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[did].hero].toMyString(),did);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[did].toMyString(),did);
						  String s=db.hero[db.player[aid].hero].name
						  		+"���˹�����"+db.hero[db.player[did].hero].name
						  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
				  }
				  else if(t/10==2)//�佫ս��
				  {
					  int x=db.magic[db.hero[db.player[aid].hero].magic2]
					                 .compute(db,db.player[aid].hero, db.player[did].hero);
					  db.player[aid].MP-=
						  db.magic[db.hero[db.player[aid].hero].magic2].MP;
					  if(t%10==2)
					  {
						  int temp=db.player[did].knightNum;						  
						  if(temp-x>=0)temp=x;
						  dhurt+=temp;
						  db.player[did].hireKnight(db, -temp);
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[did].hero].toMyString(),did);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[did].toMyString(),did);
						  String s=db.hero[db.player[aid].hero].name
						  		+"����ս��"
						  		+db.magic[db.hero[db.player[aid].hero].magic2].name
						  		+"��"+db.hero[db.player[did].hero].name
						  		+"��������"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==3)
					  {
						  int temp=db.player[did].footmanNum;						  
						  if(temp-x>=0)temp=x;
						  dhurt+=temp;
						  db.player[did].hireFootman(db,-temp);
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[did].hero].toMyString(),did);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[did].toMyString(),did);
						  String s=db.hero[db.player[aid].hero].name
						  	+"����ս��"
					  		+db.magic[db.hero[db.player[aid].hero].magic2].name
					  		+db.hero[db.player[did].hero].name
						  		+"���������"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==4)
					  {
						  int temp=db.player[did].archerNum;						 
						  if(temp-x>=0)temp=x;
						  dhurt+=temp;
						  db.player[did].hireArcher(db, -temp);
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[did].hero].toMyString(),did);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[did].toMyString(),did);
						  String s=db.hero[db.player[aid].hero].name
						  +"����ս��"
					  		+db.magic[db.hero[db.player[aid].hero].magic2].name
					  		+db.hero[db.player[did].hero].name
						  		+"���������"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==5)
					  {		
						  int temp=db.player[did].towerNum;						  
						  if(temp-x>=0)temp=x;
						  db.player[did].buildTower(db, -temp);
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[did].hero].toMyString(),did);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[did].toMyString(),did);
						  String s=db.hero[db.player[aid].hero].name
						  +"����ս��"
					  		+db.magic[db.hero[db.player[aid].hero].magic2].name
					  		+db.hero[db.player[did].hero].name
						  		+"���������"+x+"����λ���˺�!";
						  sendBattle(s);
					  }					  
				  }
				  else if(t/10==3)//�佫����
				  {
					  logoutfail(aid);
					  db.hero[db.player[aid].hero].addExp(dhurt/2*db.hero[db.player[did].hero].level);					  
					  send(mySocketData.CHAT+" "+"ս�����������"+dhurt/2*db.hero[db.player[did].hero].level+"�㾭��ֵ��",aid);
					  db.hero[db.player[did].hero].addExp(ahurt*db.hero[db.player[aid].hero].level);					 
					  send(mySocketData.CHAT+" "
							  +"�Է����ˣ�ս�����������"+ahurt*db.hero[db.player[aid].hero].level+"�㾭��ֵ��",did);					 
					  db.player[aid].inBattle=0;
					  db.player[did].inBattle=0;
					  send(mySocketData.INITHERO
							  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
					  send(mySocketData.INITPLAYER
							  +" "+db.player[aid].toMyString(),aid);	
					  send(mySocketData.INITHERO
							  +" "+db.hero[db.player[did].hero].toMyString(),did);
					  send(mySocketData.INITPLAYER
							  +" "+db.player[did].toMyString(),did);
					  send(mySocketData.BATTLE+" "+mySocketData.END,aid);
					  send(mySocketData.BATTLE+" "+mySocketData.END,did);
					  serverThread.thread[aid].READY=0;
					  serverThread.thread[did].READY=0;
					  return;
				  }
				  t=serverThread.thread[did].buffer[1];					  
				  if(t/10==1)//�佫����
				  {
					  int x=dap[1]/10;
					  bash=dap[1]%10;
					  x=sharedData.random((int)x-5, (int)x+5);
					  if(t%10==2)
					  {
						  int temp=db.player[aid].knightNum;						  
						  if(temp-x>=0)temp=x;
						  ahurt+=temp;
						  db.player[aid].hireKnight(db, -temp);
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						 
						  String s=db.hero[db.player[did].hero].name
						  		+"���˹�����"+db.hero[db.player[aid].hero].name
						  		+"�����"+bs[bash]+"���"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==3)
					  {
						  int temp=db.player[aid].footmanNum;						  
						  if(temp-x>=0)temp=x;
						  ahurt+=temp;
						  db.player[aid].hireFootman(db, -temp);
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						
						  String s=db.hero[db.player[did].hero].name
						  		+"���˹�����"+db.hero[db.player[aid].hero].name
						  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==4)
					  {
						  int temp=db.player[aid].archerNum;						  
						  if(temp-x>=0)temp=x;
						  ahurt+=temp;
						  db.player[aid].hireArcher(db,-temp);
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						 
						  String s=db.hero[db.player[did].hero].name
						  		+"���˹�����"+db.hero[db.player[aid].hero].name
						  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  
				  }
				  else if(t/10==2)//�佫ս��
				  {
					  int x=db.magic[db.hero[db.player[did].hero].magic2]
					                 .compute(db,db.player[aid].hero, db.player[did].hero);
					  db.player[did].MP-=
						  db.magic[db.hero[db.player[did].hero].magic2].MP;
					  if(t%10==2)
					  {
						  int temp=db.player[aid].knightNum;						  
						  if(temp-x>=0)temp=x;
						  ahurt+=temp;
						  db.player[aid].hireKnight(db, -temp);
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[did].hero].toMyString(),did);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[did].toMyString(),did);
						  String s=db.hero[db.player[did].hero].name
						  		+"����ս��"
						  		+db.magic[db.hero[db.player[did].hero].magic2].name
						  		+"��"+db.hero[db.player[aid].hero].name
						  		+"��������"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==3)
					  {
						  int temp=db.player[aid].footmanNum;						  
						  if(temp-x>=0)temp=x;
						  ahurt+=temp;
						  db.player[aid].hireFootman(db,-temp);
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[did].hero].toMyString(),did);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[did].toMyString(),did);
						  String s=db.hero[db.player[did].hero].name
						  	+"����ս��"
					  		+db.magic[db.hero[db.player[did].hero].magic2].name
					  		+db.hero[db.player[aid].hero].name
						  		+"���������"+x+"����λ���˺�!";
						  sendBattle(s);
					  }
					  if(t%10==4)
					  {
						  int temp=db.player[aid].archerNum;						  
						  if(temp-x>=0)temp=x;
						  ahurt+=temp;
						  db.player[aid].hireArcher(db, -temp);
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[aid].toMyString(),aid);	
						  send(mySocketData.INITHERO
								  +" "+db.hero[db.player[did].hero].toMyString(),did);
						  send(mySocketData.INITPLAYER
								  +" "+db.player[did].toMyString(),did);
						  String s=db.hero[db.player[did].hero].name
						  +"����ս��"
					  		+db.magic[db.hero[db.player[did].hero].magic2].name
					  		+db.hero[db.player[aid].hero].name
						  		+"���������"+x+"����λ���˺�!";
						  sendBattle(s);
					  }					 
				  }
				  else if(t/10==3)//�佫����
				  {
					  logoutfail(did);
					  db.player[aid].gold+=db.player[did].gold/2;						 						  
					  db.hero[db.player[did].hero].addExp(ahurt/2*db.hero[db.player[aid].hero].level);					  
					  send(mySocketData.CHAT+" "+"ս�����������"+ahurt/2*db.hero[db.player[aid].hero].level+"�㾭��ֵ��",did);
					  db.hero[db.player[aid].hero].addExp(dhurt*db.hero[db.player[did].hero].level);					  
					  send(mySocketData.CHAT+" "
							  +"�Է����ˣ�ս�����������"+dhurt*db.hero[db.player[did].hero].level+"�㾭��ֵ,�õ�"
							  +db.player[did].gold/2+"��ҡ�",aid);	
					  db.player[did].gold/=2;					 
					  db.player[did].inBattle=0;
					  db.player[aid].inBattle=0;
					  send(mySocketData.INITHERO
							  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
					  send(mySocketData.INITPLAYER
							  +" "+db.player[aid].toMyString(),aid);	
					  send(mySocketData.INITHERO
							  +" "+db.hero[db.player[did].hero].toMyString(),did);
					  send(mySocketData.INITPLAYER
							  +" "+db.player[did].toMyString(),did);
					  send(mySocketData.BATTLE+" "+mySocketData.END,did);
					  send(mySocketData.BATTLE+" "+mySocketData.END,aid);
					  serverThread.thread[aid].READY=0;
					  serverThread.thread[did].READY=0;
					  return;
				  }
				  for(int j=2;j<5;j++)
				  {
					  if(serverThread.thread[aid].buffer[j]==0){continue;}					
					  else 
					  {						 
						  t=serverThread.thread[aid].buffer[j];
						  int x=computeAvsB(aap[j]/10,ddp[t%10]);
						  bash=aap[j]%10;
						  String n=null;
						  if(j==2){n="���";}
						  else if(j==3){n="����";}
						  else if(j==4){n="����";}
						  else if(j==5){n="����";}
						  else ;
						  if(t%10==2)
						  {							  
							  int temp=db.player[did].knightNum;							  
							  if(temp-x>=0)temp=x;
							  dhurt+=temp;
							  db.player[did].hireKnight(db, -temp);
							 	
							  send(mySocketData.INITHERO
									  +" "+db.hero[db.player[did].hero].toMyString(),did);
							  send(mySocketData.INITPLAYER
									  +" "+db.player[did].toMyString(),did);
							  String s=db.hero[db.player[aid].hero].name
							  		+n+"������"+db.hero[db.player[did].hero].name
							  		+"�����"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
						  if(t%10==3)
						  {
							  int temp=db.player[did].footmanNum;							 
							  if(temp-x>=0)temp=x;
							  dhurt+=temp;
							  db.player[did].hireFootman(db,-temp);
							 	
							  send(mySocketData.INITHERO
									  +" "+db.hero[db.player[did].hero].toMyString(),did);
							  send(mySocketData.INITPLAYER
									  +" "+db.player[did].toMyString(),did);
							  String s=db.hero[db.player[aid].hero].name
							  +n+"������"+db.hero[db.player[did].hero].name
							  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
						  if(t%10==4)
						  {
							  int temp=db.player[did].archerNum;							  
							  if(temp-x>=0)temp=x;
							  dhurt+=temp;
							  db.player[did].hireArcher(db, -temp);
							 
							  send(mySocketData.INITHERO
									  +" "+db.hero[db.player[did].hero].toMyString(),did);
							  send(mySocketData.INITPLAYER
									  +" "+db.player[did].toMyString(),did);
							  String s=db.hero[db.player[aid].hero].name
							  +n+"������"+db.hero[db.player[did].hero].name
							  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
						  if(t%10==5)
						  {		
							  int temp=db.player[did].towerNum;							  
							  if(temp-x>=0)temp=x;
							  db.player[did].buildTower(db,-temp);
							 
							  send(mySocketData.INITHERO
									  +" "+db.hero[db.player[did].hero].toMyString(),did);
							  send(mySocketData.INITPLAYER
									  +" "+db.player[did].toMyString(),did);
							  String s=db.hero[db.player[aid].hero].name
							  +n+"������"+db.hero[db.player[did].hero].name
							  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
					  }
				  }	
				  for(int j=2;j<5;j++)
				  {
					  if(serverThread.thread[did].buffer[j]==0){continue;}					
					  else 
					  {						 
						  t=serverThread.thread[did].buffer[j];
						  int x=computeAvsB(dap[j]/10,adp[t%10]);
						  bash=dap[j]%10;
						  String n=null;
						  if(j==2){n="���";}
						  else if(j==3){n="����";}
						  else if(j==4){n="����";}
						  else if(j==5){n="����";}
						  else ;
						  if(t%10==2)
						  {							  
							  int temp=db.player[aid].knightNum;							  
							  if(temp-x>=0)temp=x;
							  ahurt+=temp;
							  db.player[aid].hireKnight(db,-temp);
							  send(mySocketData.INITHERO
									  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
							  send(mySocketData.INITPLAYER
									  +" "+db.player[aid].toMyString(),aid);	
							 
							  String s=db.hero[db.player[did].hero].name
							  		+n+"������"+db.hero[db.player[aid].hero].name
							  		+"�����"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
						  if(t%10==3)
						  {
							  int temp=db.player[aid].footmanNum;							  
							  if(temp-x>=0)temp=x;
							  ahurt+=temp;
							  db.player[aid].hireFootman(db, -temp);
							  send(mySocketData.INITHERO
									  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
							  send(mySocketData.INITPLAYER
									  +" "+db.player[aid].toMyString(),aid);	
							 
							  String s=db.hero[db.player[did].hero].name
							  +n+"������"+db.hero[db.player[aid].hero].name
							  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }
						  if(t%10==4)
						  {
							  int temp=db.player[aid].archerNum;							  
							  if(temp-x>=0)temp=x;
							  ahurt+=temp;
							  db.player[aid].hireArcher(db, -temp);
							  send(mySocketData.INITHERO
									  +" "+db.hero[db.player[aid].hero].toMyString(),aid);
							  send(mySocketData.INITPLAYER
									  +" "+db.player[aid].toMyString(),aid);	
							
							  String s=db.hero[db.player[did].hero].name
							  +n+"������"+db.hero[db.player[aid].hero].name
							  		+"������"+bs[bash]+"���"+x+"����λ���˺�!";
							  sendBattle(s);
						  }						 
					  }
				  }	
				  
				  send(mySocketData.BATTLE+" "+mySocketData.READY
						  +" "+db.player[did].toMyString(),aid);	
				  send(mySocketData.BATTLE+" "+mySocketData.READY
						  +" "+db.player[aid].toMyString(),did);
				  
				  try{
					  serverThread.thread[aid].out.flush();
					  serverThread.thread[did].out.flush();
					  }
					  catch(Exception eee){}
				  serverThread.thread[aid].READY=0;
				  serverThread.thread[aid].buffer[1]=0;
				  serverThread.thread[aid].buffer[2]=0;
				  serverThread.thread[aid].buffer[3]=0;
				  serverThread.thread[aid].buffer[4]=0;
				  serverThread.thread[aid].buffer[5]=0;	
				  serverThread.thread[did].READY=0;
				  serverThread.thread[did].buffer[1]=0;
				  serverThread.thread[did].buffer[2]=0;
				  serverThread.thread[did].buffer[3]=0;
				  serverThread.thread[did].buffer[4]=0;
				  serverThread.thread[did].buffer[5]=0;	
			  }
			  else ;
		  }
      }
	  else if(db.player[did].login==0) //�벻���ߵ��˶�ս
	  {
		  
	  }
	  else ;
  }
}
//SERVER����
public class server  implements ActionListener{
	myDB db;	
	login loginframe;
	mainFrame mainframe;
	public static void main(String []args)
	{
		

		server x=new server();
		login y=new login("����������",x);
		y.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  
		y.setVisible(true);
		x.loginframe=y;
		
	}
	public void actionPerformed(ActionEvent e)
	{ if (e.getSource()==loginframe.yes)
	{
		loginframe.message=login.YES;			
		loginframe.port=Integer.parseInt(loginframe.text1.getText());
		loginframe.setVisible(false) ;
		
		db=new myDB();
		db.screateAll();		
		mainFrame y=new mainFrame("������",db);	
		mainframe=y;
		y.setVisible(true);
		y.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 	
		
		ServerThread thread=new ServerThread(y,db,loginframe.port);		
		thread.start();		
		y.com=thread;
	}
	else if(e.getSource()==loginframe.no)
	{
		loginframe.message=login.NO; System.exit(1);
	}
	}

}
