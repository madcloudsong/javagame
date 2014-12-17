package sharedData;

//ÕÊº“¿‡
import sharedData.myDB;
import sharedData.sharedData;

public class player {		
	public int id;
	public String castlename;
	public int x,y;
	public int areaMax;
	public int areaNow;
	public int gold;
	public int goldpm;
	public int riceMax;
	public int riceNow;
	public int login;
	public int hallNum;
	public int marketNum;
	public int fieldNum;
	public int knightHallNum;
	public int footmanHallNum;
	public int archerHallNum;
	public int towerNum;
	public int knightNum;
	public int footmanNum;
	public int archerNum;	
	public int inBattle;
	public int bashRate;
	public int isDefence;
	public int MP;
	public int hero;	
	public String toMyString()
	{
		String s=id+" "+
		 castlename+" "+
		 x+" "+y+" "+
		 areaMax+" "+
		 areaNow+" "+
		 gold+" "+
		 goldpm+" "+
		 riceMax+" "+
		 riceNow+" "+
		 login+" "+
		 hallNum+" "+
		 marketNum+" "+
		 fieldNum+" "+
		 knightHallNum+" "+
		 footmanHallNum+" "+
		 archerHallNum+" "+
		 towerNum+" "+
		 knightNum+" "+
		 footmanNum+" "+
		 archerNum+" "+	
		 inBattle+" "+
		 bashRate+" "+
		 isDefence+" "+
		 MP+" "+
		 hero;
		return s;
	}
	public void recover()
	{
		if(login==2)
		{
			knightNum=knightHallNum;
			footmanNum=footmanHallNum;
			archerNum=archerHallNum;
			towerNum=hallNum;
		}
	}
	public int addHeroNum(myDB db,int x)
	{
		if(gold<db.hero[x].price)return 0;
		if(db.hero[x].hired==2||db.hero[x].hired==1)return 0;
		else {
			if(hero!=0)
			{
				db.hero[hero].hired=2;
				db.hero[hero].playerId=0;
				db.hero[x].gloveId=db.hero[hero].gloveId;
				db.hero[x].weaponId=db.hero[hero].weaponId;
				db.hero[x].helmetId=db.hero[hero].helmetId;
				db.hero[x].armorId=db.hero[hero].armorId;
				db.hero[x].shoesId=db.hero[hero].shoesId;
				db.hero[x].ringId=db.hero[hero].ringId;
			}
			hero=x;
			db.hero[x].hired=1;
			db.hero[x].playerId=id;
		}
		return 1;
	}	
	public int buildHall(myDB db,int x)
	{
		if(x>=0)
		{
			if(gold<sharedData.hallPrice*x||areaMax-areaNow<sharedData.hallArea*x)return 0;		
		}
		if(x<0)
		{
			if(-x>=hallNum)return 0;
		}
		areaNow+=sharedData.hallArea*x;
		if(x>0)gold-=sharedData.hallPrice*x;
		areaMax+=sharedData.hallAreaOffer*x;		
		hallNum+=x;
		return 1;
	}
	public int buildMarket(myDB db,int x)
	{
		if(x>=0)
		{
			if(gold<sharedData.marketPrice*x||areaMax-areaNow<sharedData.marketArea*x)return 0;		
		}
		if(x<0)
		{
			if(-x>=marketNum)return 0;
		}
		areaNow+=sharedData.marketArea*x;
		if(x>0)gold-=sharedData.marketPrice*x;
		goldpm+=sharedData.marketGoldOffer*x;
		marketNum+=x;
		return 1;
	}
	public int buildField(myDB db,int x)
	{
		if(x>=0)
		{
			if(gold<sharedData.fieldPrice*x||areaMax-areaNow<sharedData.fieldArea*x)return 0;		
		}
		if(x<0)
		{
			if(-x>=fieldNum)return 0;
		}
		areaNow+=sharedData.fieldArea*x;
		if(x>0)gold-=sharedData.fieldPrice*x;
		riceMax+=sharedData.fieldRiceOffer*x;		
		fieldNum+=x;
		return 1;
	}
	public int buildKnightHall(myDB db,int x)
	{
		if(x>=0)
		{
			if(gold<sharedData.knightHallPrice*x||areaMax-areaNow<sharedData.knightHallArea*x)return 0;		
		}
		if(x<0)
		{
			if(-x>=knightHallNum)return 0;
		}
		areaNow+=sharedData.knightHallArea*x;
		if(x>0)gold-=sharedData.knightHallPrice*x;
		knightHallNum+=x;
		return 1;
	}
	public int buildFootmanHall(myDB db,int x)
	{
		if(x>=0)
		{
			if(gold<sharedData.footmanHallPrice*x||areaMax-areaNow<sharedData.footmanHallArea*x)return 0;		
		}
		if(x<0)
		{
			if(-x>=footmanHallNum)return 0;
		}
		areaNow+=sharedData.footmanHallArea*x;
		if(x>0)gold-=sharedData.footmanHallPrice*x;
		footmanHallNum+=x;
		return 1;
	}
	public int buildArcherHall(myDB db,int x)
	{
		if(x>=0)
		{
			if(gold<sharedData.archerHallPrice*x||areaMax-areaNow<sharedData.archerHallArea*x)return 0;		
		}
		if(x<0)
		{
			if(-x>=archerHallNum)return 0;
		}
		areaNow+=sharedData.archerHallArea*x;
		if(x>0)gold-=sharedData.archerHallPrice*x;
		archerHallNum+=x;
		return 1;
	}
	public int buildTower(myDB db,int x)
	{
		if(x>=0)
		{
			if(gold<sharedData.towerPrice*x||areaMax-areaNow<sharedData.towerArea*x)return 0;		
		}
		if(x<0)
		{
			if(-x>towerNum)return 0;
		}
		areaNow+=sharedData.towerArea*x;
		if(x>0)gold-=sharedData.towerPrice*x;
		towerNum+=x;
		return 1;
	}
	public int hireKnight(myDB db,int x)
	{
		if(x>=0)
		{
			if(gold<sharedData.knightPrice*x||riceMax-riceNow<sharedData.knightRice*x)return 0;		
		}
		if(x<0)
		{
			if(-x>knightNum)return 0;
		}
		riceNow+=sharedData.knightRice*x;
		if(x>0)gold-=sharedData.knightPrice*x;
		knightNum+=x;
		return 1;
	}
	public int hireFootman(myDB db,int x)
	{
		if(x>=0)
		{
			if(gold<sharedData.footmanPrice*x||riceMax-riceNow<sharedData.footmanRice*x)return 0;		
		}
		if(x<0)
		{
			if(-x>footmanNum)return 0;
		}
		riceNow+=sharedData.footmanRice*x;
		if(x>0)gold-=sharedData.footmanPrice*x;
		footmanNum+=x;
		return 1;
	}
	public int hireArcher(myDB db,int x)
	{
		if(x>=0)
		{
			if(gold<sharedData.archerPrice*x||riceMax-riceNow<sharedData.archerRice*x)return 0;		
		}
		if(x<0)
		{
			if(-x>archerNum)return 0;
		}
		riceNow+=sharedData.archerRice*x;
		if(x>0)gold-=sharedData.archerPrice*x;
		archerNum+=x;
		return 1;
	}
	public int buyItems(myDB db,int heroId,int x)
	{
		
		if(gold<db.items[x].price)return 0;		
		gold-=db.items[x].price;
		db.hero[heroId].addItems(db, x);		
		return 1;
	}
	public int battleinit(myDB db)
	{
		computeMP(db);
		computeBashRate(db);
		inBattle=1;
		return 1;
	}
	public int computeMP(myDB db)
	{
		MP=db.hero[hero].wise/10;
		return 1;
	}
	public int computeBashRate(myDB db)
	{
		bashRate=10;
		if(db.hero[hero].hasMagic3()==1)
		{
			bashRate+=db.magic[db.hero[id].magic3].br;
		}
		bashRate+=db.hero[hero].lead/4;
		if(bashRate>100)bashRate=100;
		return 1;
	}
	public int computeHAP(myDB db)
	{
		int x=db.hero[hero].power
		/10*(db.hero[hero].level+3);
		if(db.hero[hero].hasMagic3()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic3].ap))/100;
		}
		if(sharedData.isBash(bashRate)==1){x*=2.0;x=((int)x)*10+1;}
		else {x=((int)x)*10+0;}
		return x;
	}	
	public int computeKAP(myDB db)
	{
		int x=sharedData.knightAp*knightNum;
		x*=(double)(db.hero[hero].power+50)/100;
		if(db.hero[hero].hasMagic1()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic1].knight))/100;
		}
		if(db.hero[hero].hasMagic3()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic3].ap))/100;
		}
		if(sharedData.isBash(bashRate)==1){x*=2.0;x=((int)x)*10+1;}
		else {x=((int)x)*10+0;}
		return (int)x;
	}
	public int computeKDP(myDB db)
	{
		int x=sharedData.knightDp*knightNum;
		x*=(double)(db.hero[hero].wise+50)/100;
		if(db.hero[hero].hasMagic1()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic1].knight))/100;
		}
		if(db.hero[hero].hasMagic3()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic3].dp))/100;
		}
		if(isDefence==1)x*=1.2;
		return (int)x;
	}
	public int computeFAP(myDB db)
	{
		int x=sharedData.footmanAp*footmanNum;
		x*=(double)(db.hero[hero].power+50)/100;
		if(db.hero[hero].hasMagic1()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic1].footman))/100;
		}
		if(db.hero[hero].hasMagic3()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic3].ap))/100;
		}
		if(sharedData.isBash(bashRate)==1){x*=2.0;x=((int)x)*10+1;}
		else {x=((int)x)*10+0;}
		return (int)x;
	}
	public int computeFDP(myDB db)
	{
		int x=sharedData.footmanDp*footmanNum;
		x*=(double)(db.hero[hero].wise+50)/100;
		if(db.hero[hero].hasMagic1()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic1].footman))/100;
		}
		if(db.hero[hero].hasMagic3()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic3].dp))/100;
		}
		if(isDefence==1)x*=1.2;
		return (int)x;
	}
	public int computeAAP(myDB db)
	{
		int x=sharedData.archerAp*archerNum;
		x*=(double)(db.hero[hero].power+50)/100;
		if(db.hero[hero].hasMagic1()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic1].archer))/100;
		}
		if(db.hero[hero].hasMagic3()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic3].ap))/100;
		}
		if(sharedData.isBash(bashRate)==1){x*=2.0;x=((int)x)*10+1;}
		else {x=((int)x)*10+0;}
		return (int)x;
	}
	public int computeADP(myDB db)
	{
		int x=sharedData.archerDp*archerNum;
		x*=(double)(db.hero[hero].wise+50)/100;
		if(db.hero[hero].hasMagic1()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic1].archer))/100;
		}
		if(db.hero[hero].hasMagic3()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic3].dp))/100;
		}
		if(isDefence==1)x*=1.2;
		return (int)x;
	}
	public int computeTAP(myDB db)
	{
		if(this.isDefence==0)return 0;
		int x=sharedData.towerAp*towerNum;
		x*=(double)(db.hero[hero].power+50)/100;		
		if(db.hero[hero].hasMagic3()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic3].ap))/100;
		}
		if(sharedData.isBash(bashRate)==1){x*=2.0;x=((int)x)*10+1;}
		else {x=((int)x)*10+0;}
		return (int)x;
	}
	public int computeTDP(myDB db)
	{
		if(this.isDefence==0)return 0;
		int x=sharedData.towerDp*towerNum;
		x*=(double)(db.hero[hero].wise+50)/100;		
		if(db.hero[hero].hasMagic3()==1)
		{
			x*=((double)(100+db.magic[db.hero[hero].magic3].dp))/100;
		}
		if(isDefence==1)x*=1.2;
		return (int)x;
	}
	public int escape(myDB db)
	{
		knightNum/=2;
		footmanNum/=2;
		archerNum/=2;
		if(isDefence==1)
		{			
			gold/=2;
		}
		return 1;
	}
	public int isfail()
	{
		if(knightNum<10&&footmanNum<10&&archerNum<10)return 1;
		else return 0;
	}
	public int update()
	{
		riceNow=knightNum*sharedData.knightRice+footmanNum*sharedData.footmanRice
		+archerNum*sharedData.archerRice;
		return 1;
	}

}

