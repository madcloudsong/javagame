package sharedData;
//武将类  160行
public class hero {	
	public int heroId=0;
	public int playerId;
	public int hired;
	public int level;
	public int exp;
	public int price;
	public String name;
	public String des;
	public String picpath;
	public String largepicpath;
	public int lead;
	public int power;
	public int wise;
	public int type;
	public int magic1;
	public int magic2;
	public int magic3;
	public int weaponId;
	public int helmetId;
	public int armorId;
	public int shoesId;
	public int ringId;
	public int gloveId;	
	public static int LEAD=1;
	public static int POWER=1;
	public static int WISE=1;
	//将信息变成字符串
	public String toMyString()
	{
		String s=heroId+" "+playerId+" "
		   +hired+" "+level+" "
		   +exp+" "+price+" "
		   +name+" "+des+" "
		   +picpath+" "+largepicpath+" "+lead
		   +" "+power+" "+wise
		   +" "+type+" "+magic1
		   +" "+magic2+" "+magic3
		   +" "+weaponId+" "+helmetId
		   +" "+armorId+" "+shoesId
		   +" "+ringId+" "+gloveId;
		return s;
	}
	//添加装备并更新数据
	public int addItems(myDB db,int itemsid)
	{
		int ttype=db.items[itemsid].type;
		if(ttype==items.WEAPON)
		{		
			if(weaponId!=0)removeItems(db,weaponId);
			weaponId=itemsid;
		}
		else if(ttype==items.HELMET)
		{		
			if(helmetId!=0)removeItems(db,helmetId);
			helmetId=itemsid;
		}
		else if(ttype==items.ARMOR)
		{		
			if(armorId!=0)removeItems(db,armorId);
			armorId=itemsid;
		}
		else if(ttype==items.SHOES)
		{		
			if(shoesId!=0)removeItems(db,shoesId);
			shoesId=itemsid;
		}
		else if(ttype==items.RING)
		{		
			if(ringId!=0)removeItems(db,ringId);
			ringId=itemsid;
		}
		else if(ttype==items.GLOVE)
		{		
			if(gloveId!=0)removeItems(db,gloveId);
			gloveId=itemsid;
		}
		lead+=db.items[itemsid].lead;
		power+=db.items[itemsid].power;
		wise+=db.items[itemsid].wise;
		return 1;
	}
	public int removeItems(myDB db,int itemsid)
	{
		int ttype=db.items[itemsid].type;
		if(ttype==items.WEAPON)
		{		
			if(weaponId==0)return 0;
			else weaponId=0;
		}
		else if(ttype==items.HELMET)
		{		
			if(helmetId==0)return 0;
			else helmetId=0;
		}
		else if(ttype==items.ARMOR)
		{		
			if(armorId==0)return 0;
			else armorId=0;
		}
		else if(ttype==items.SHOES)
		{		
			if(shoesId==0)return 0;
			else shoesId=0;
		}
		else if(ttype==items.RING)
		{		
			if(ringId==0)return 0;
			else ringId=0;
		}
		else if(ttype==items.GLOVE)
		{		
			if(gloveId==0)return 0;
			else gloveId=0;
		}
		lead-=db.items[itemsid].lead;
		power-=db.items[itemsid].power;
		wise-=db.items[itemsid].wise;
		return 1;
	}
	public int hasMagic1()
	{
		if(level>=5)return 1;
		else return 0;
	}
	public int hasMagic2()
	{
		if(level>=10)return 1;
		else return 0;
	}
	public int hasMagic3()
	{
		if(level>=20)return 1;
		else return 0;
	}
	public int addExp(int x)//每级需要经验的公式为level*level*50
	{
		exp+=x;
		int need=level*level*2000;
		if(exp>=need)
		{
			exp-=need;
			level+=1;
			if(type==1)lead+=2;
			else if(type==2)power+=2;
			else if(type==3)wise+=2;
			else ;
			lead+=2;
			power+=2;
			wise+=2;
			return 1;
		}
		return 0;
	}

}
