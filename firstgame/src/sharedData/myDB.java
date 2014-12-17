package sharedData;
//游戏数据管理类  340行
import java.io.*;
import java.util.*;

public class myDB {	
	public hero hero[];
	public magic magic[];
	public user user[];
	public player player[];
	public items items[];
	String herodata="data/herodata.txt";
	String magicdata="data/magicdata.txt";
	String userdata="data/userdata.txt";
	String playerdata="data/playerdata.txt";
	String itemsdata="data/itemsdata.txt";
	//创建武将数组
	public int createHero()
	{
		FileReader file;   
         BufferedReader in;
         hero=new hero[100];
         for(int i=0;i<100;i++)
         {
        	 hero[i]=new hero();
         }
         try{
        	 try{file=new FileReader(herodata);
        	 		in=new BufferedReader(file);}
        	 catch( FileNotFoundException eee){return 0;}
        	 String s;
        	 int i=0;
             while((s=in.readLine())!=null)     
             {
             	StringTokenizer fenxi=new StringTokenizer(s," ");   
                i=Integer.parseInt(fenxi.nextToken());              	
             	hero[i].heroId=i;                 	
            	hero[i].playerId=Integer.parseInt(fenxi.nextToken());
            	hero[i].hired=Integer.parseInt(fenxi.nextToken());
            	hero[i].level=Integer.parseInt(fenxi.nextToken());
            	hero[i].exp=Integer.parseInt(fenxi.nextToken());
            	hero[i].price=Integer.parseInt(fenxi.nextToken());
            	hero[i].name=fenxi.nextToken();
            	hero[i].des=fenxi.nextToken();
            	hero[i].picpath=fenxi.nextToken();
            	hero[i].largepicpath=fenxi.nextToken();            	
            	hero[i].lead=Integer.parseInt(fenxi.nextToken());            	
            	hero[i].power=Integer.parseInt(fenxi.nextToken());
            	hero[i].wise=Integer.parseInt(fenxi.nextToken());
            	hero[i].type=Integer.parseInt(fenxi.nextToken());
            	hero[i].magic1=Integer.parseInt(fenxi.nextToken());
            	hero[i].magic2=Integer.parseInt(fenxi.nextToken());
            	hero[i].magic3=Integer.parseInt(fenxi.nextToken());
            	hero[i].weaponId=Integer.parseInt(fenxi.nextToken());
            	hero[i].helmetId=Integer.parseInt(fenxi.nextToken());
            	hero[i].armorId=Integer.parseInt(fenxi.nextToken());
            	hero[i].shoesId=Integer.parseInt(fenxi.nextToken());
            	hero[i].ringId=Integer.parseInt(fenxi.nextToken());
            	hero[i].gloveId=Integer.parseInt(fenxi.nextToken());   
            	
             }      
             in.close();             
            }
        catch(Exception ee){}
		return 0;
	}
	//创建技能数组
	public int createMagic()	
	{
		FileReader file;   
        BufferedReader in;
        magic=new magic[100];
        for(int i=0;i<100;i++)
        {
       	 magic[i]=new magic();
        }
        try{
       	 try{file=new FileReader(magicdata);
       	 		in=new BufferedReader(file);}
       	 catch( FileNotFoundException eee){return 0;}
       	 String s;
       	 int i=0;
            while((s=in.readLine())!=null)     
            {
            	StringTokenizer fenxi=new StringTokenizer(s," ");   
               i=Integer.parseInt(fenxi.nextToken());              	
            	magic[i].id=i;   
           	magic[i].name=fenxi.nextToken();
           	magic[i].path=fenxi.nextToken();
           	magic[i].path2=fenxi.nextToken();
           	magic[i].des=fenxi.nextToken();               	
           	magic[i].br=Integer.parseInt(fenxi.nextToken());            	
           	magic[i].ap=Integer.parseInt(fenxi.nextToken());
           	magic[i].dp=Integer.parseInt(fenxi.nextToken());
           	magic[i].knight=Integer.parseInt(fenxi.nextToken());
           	magic[i].footman=Integer.parseInt(fenxi.nextToken());
           	magic[i].archer=Integer.parseInt(fenxi.nextToken());
           	magic[i].damage=Integer.parseInt(fenxi.nextToken());
           	magic[i].type=Integer.parseInt(fenxi.nextToken());
           	magic[i].MP=Integer.parseInt(fenxi.nextToken());           	
            }      
            in.close();             
           }
       catch(Exception ee){}
		return 0;
	}
	//创建用户登陆的数组
	public int createUser()
	{
		FileReader file;   
        BufferedReader in;
        user=new user[50];
        for(int i=0;i<50;i++)
        {
       	 user[i]=new user();
        }
        try{
       	 try{file=new FileReader(userdata);
       	 		in=new BufferedReader(file);}
       	 catch( FileNotFoundException eee){return 0;}
       	 String s;
       	 int i=0;
            while((s=in.readLine())!=null)     
            {
            	StringTokenizer fenxi=new StringTokenizer(s," ");   
            	i=Integer.parseInt(fenxi.nextToken());              	
            	user[i].id=i;   
            	user[i].userName=fenxi.nextToken();
            	user[i].userPw=fenxi.nextToken();           	
            	user[i].playerId=Integer.parseInt(fenxi.nextToken());   
            	user[i].islogin=Integer.parseInt(fenxi.nextToken()); 
            }      
            in.close();             
           }
       catch(Exception ee){}
		return 0;
	}
	//创建玩家的数组
	public int createPlayer()
	{
		FileReader file;   
        BufferedReader in;
        player=new player[50];
        for(int i=0;i<50;i++)
        {
       	 player[i]=new player();
        }
        try{
       	 try{file=new FileReader(playerdata);
       	 		in=new BufferedReader(file);}
       	 catch( FileNotFoundException eee){return 0;}
       	 String s;
       	 int i=0;
            while((s=in.readLine())!=null)     
            {
            	StringTokenizer fenxi=new StringTokenizer(s," ");   
            	i=Integer.parseInt(fenxi.nextToken());              	
            	player[i].id=i;  
            	player[i].castlename=fenxi.nextToken();           	
            	player[i].x=Integer.parseInt(fenxi.nextToken());
            	player[i].y=Integer.parseInt(fenxi.nextToken());
            	player[i].areaMax=Integer.parseInt(fenxi.nextToken());
            	player[i].areaNow=Integer.parseInt(fenxi.nextToken());
            	player[i].gold=Integer.parseInt(fenxi.nextToken());
            	player[i].goldpm=Integer.parseInt(fenxi.nextToken());
            	player[i].riceMax=Integer.parseInt(fenxi.nextToken());
            	player[i].riceNow=Integer.parseInt(fenxi.nextToken());
            	player[i].login=Integer.parseInt(fenxi.nextToken());
            	player[i].hallNum=Integer.parseInt(fenxi.nextToken());
            	player[i].marketNum=Integer.parseInt(fenxi.nextToken());
            	player[i].fieldNum=Integer.parseInt(fenxi.nextToken());
            	player[i].knightHallNum=Integer.parseInt(fenxi.nextToken());
            	player[i].footmanHallNum=Integer.parseInt(fenxi.nextToken());
            	player[i].archerHallNum=Integer.parseInt(fenxi.nextToken());
            	player[i].towerNum=Integer.parseInt(fenxi.nextToken());
            	player[i].knightNum=Integer.parseInt(fenxi.nextToken());
            	player[i].footmanNum=Integer.parseInt(fenxi.nextToken());
            	player[i].archerNum=Integer.parseInt(fenxi.nextToken());
            	player[i].inBattle=Integer.parseInt(fenxi.nextToken());
            	player[i].bashRate=Integer.parseInt(fenxi.nextToken());
            	player[i].isDefence=Integer.parseInt(fenxi.nextToken());
            	player[i].MP=Integer.parseInt(fenxi.nextToken());
            	player[i].hero=Integer.parseInt(fenxi.nextToken());
           	
            }      
            in.close();             
           }
       catch(Exception ee){}
		return 0;
	}
	//创建装备的数组
	public int createItems()
	{
		FileReader file;   
        BufferedReader in;
        items=new items[200];
        for(int i=0;i<200;i++)
        {
       	 items[i]=new items();
        }
        try{
       	 try{file=new FileReader(itemsdata);
       	 		in=new BufferedReader(file);}
       	 catch( FileNotFoundException eee){return 0;}
       	 String s;
       	 int i=0;
            while((s=in.readLine())!=null)     
            {
            	StringTokenizer fenxi=new StringTokenizer(s," ");   
            	i=Integer.parseInt(fenxi.nextToken());              	
            	items[i].id=i;   
            	items[i].type=Integer.parseInt(fenxi.nextToken());            
            	items[i].name=fenxi.nextToken();
            	items[i].path=fenxi.nextToken();           
           	    items[i].price=Integer.parseInt(fenxi.nextToken()); 
           	    items[i].lead=Integer.parseInt(fenxi.nextToken());   
           	    items[i].power=Integer.parseInt(fenxi.nextToken()); 
           	    items[i].wise=Integer.parseInt(fenxi.nextToken());   
            }      
            in.close();             
           }
       catch(Exception ee){}
		return 0;
	}	
	//保存武将的数据
	public int saveHero()
	{	
		FileWriter out;
		 try{
			 try{out=new FileWriter(herodata);}
			 catch( FileNotFoundException eee){return 0;}
	           for(int i=0;i<100;i++)
	           {
	        	   
	        	   if(hero[i].heroId==0){continue;}
	        	   char a[]=(hero[i].toMyString()+"\n").toCharArray();
	        	   out.write(a, 0, a.length);
	           }
	           out.close();
	         }
	       catch(IOException ee){}
		return 0;
	}
	//保存技能的数据
	public int saveMagic()
	{
		FileWriter out;
		 try{
			 try{out=new FileWriter(magicdata);}
			 catch( FileNotFoundException eee){return 0;}
	           for(int i=0;i<100;i++)
	           {
	        	   
	        	   if(magic[i].id==0){continue;}
	        	   char a[]=(magic[i].toMyString()+"\n").toCharArray();
	        	   out.write(a, 0, a.length);
	           }
	           out.close();
	         }
	       catch(IOException ee){}
		return 0;
	}
	//保存用户的数据
	public int saveUser()
	{
		FileWriter out;
		 try{
			 try{out=new FileWriter(userdata);}
			 catch( FileNotFoundException eee){return 0;}
	           for(int i=0;i<50;i++)
	           {
	        	   
	        	   if(user[i].id==0){continue;}
	        	   char a[]=(user[i].toMyString()+"\n").toCharArray();
	        	   out.write(a, 0, a.length);
	           }
	           out.close();
	         }
	       catch(IOException ee){}
		return 0;
	}
	//保存玩家的数据
	public int savePlayer()
	{
		FileWriter out;
		 try{
			 try{out=new FileWriter(playerdata);}
			 catch( FileNotFoundException eee){return 0;}
	           for(int i=0;i<50;i++)
	           {
	        	   
	        	   if(player[i].id==0){continue;}
	        	   char a[]=(player[i].toMyString()+"\n").toCharArray();
	        	   out.write(a, 0, a.length);
	           }
	           out.close();
	         }
	       catch(IOException ee){}
		return 0;
	}
	//保存装备的数据
	public int saveItems()
	{
		FileWriter out;
		 try{
			 try{out=new FileWriter(itemsdata);}
			 catch( FileNotFoundException eee){return 0;}
	           for(int i=0;i<200;i++)
	           {
	        	   
	        	   if(items[i].id==0){continue;}
	        	   char a[]=(items[i].toMyString()+"\n").toCharArray();
	        	   out.write(a, 0, a.length);
	           }
	           out.close();
	         }
	       catch(IOException ee){}
		return 0;
	}	
	//服务器端的开始读入数据
	public int screateAll()
	{
		this.createHero();
		this.createItems();
		this.createMagic();
		this.createPlayer();
		this.createUser();
		return 0;
	}
//	客户端的开始读入数据
	public int ccreateAll()
	{
		this.createHero();
		this.createItems();
		this.createMagic();
		return 0;
	}
//	服务器端的保存数据
	public int ssaveAll()
	{
		this.saveHero();
		this.savePlayer();		
		return 0;
	}
//	客户端的保存数据
	public int csaveAll()
	{
		return 0;
	}

}
