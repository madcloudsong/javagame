package sharedData;
//游戏装备类 30行
public class items {
	public int id;
	public int type;
	public String name;
	public String path;
	public int price;
	public int lead;
	public int power;
	public int wise;
	public static int WEAPON=1;
	public static int ARMOR=2;
	public static int HELMET=3;
	public static int RING=4;
	public static int SHOES=5;
	public static int GLOVE=6;
	public String toMyString()
	{
		String s=id+" "+
		 type+" "+
		 name+" "+
		 path+" "+
		 price+" "+
		 lead+" "+
		 power+" "+
		 wise;
		return s;
	}
}
