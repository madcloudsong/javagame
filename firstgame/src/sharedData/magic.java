package sharedData;
//武将技能类  60行
public class magic {
	public int id;
	public String name;
	public String path;
	public String path2;
	public String des;
	public int br;
	public int ap;
	public int dp;
	public int knight;
	public int footman;
	public int archer;
	public int damage;
	public int type; 
	public int MP;
	public static int LEAD=1;
	public static int POWER=2;
	public static int WISE=3;
	public static int STRONG=4;
	public static int SUPER=5;
	public String toMyString()
	{
		String s=id+" "+
		 name+" "+
		 path+" "+
		 path2+" "+
		 des+" "+
		 br+" "+
		 ap+" "+
		 dp+" "+
		 knight+" "+
		 footman+" "+
		 archer+" "+
		 damage+" "+
		 type+" "+ 
		 MP;
		return s;
	}
	public int compute(myDB db,int heroId1,int heroId2)//计算技能伤害值
	{
		int x=0;
		if(type==LEAD)
		{
			x=(int)(db.hero[heroId1].lead
					*damage*(db.hero[heroId1].level-5)/db.hero[heroId2].lead);
		}
		else if(type==POWER)
		{
			x=(int)(db.hero[heroId1].power
					*damage*(db.hero[heroId1].level-5)/(db.hero[heroId2].power));
		}
		else if(type==WISE)
		{
			x=(int)(db.hero[heroId1].wise
					*damage*(db.hero[heroId1].level-5)/db.hero[heroId2].wise);
		}
		return sharedData.random(x-25, x+25);
	}

}
