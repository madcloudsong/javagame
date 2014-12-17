package sharedData;
import java.math.*;
//游戏数据共享类
public class sharedData {
	public static int knightAp=6;
	public static int knightDp=5;
	public static int knightPrice=100;
	public static int knightRice=1;
	public static int footmanAp=5;
	public static int footmanDp=8;
	public static int footmanPrice=90;
	public static int footmanRice=1;
	public static int archerAp=6;
	public static int archerDp=2;
	public static int archerPrice=80;
	public static int archerRice=1;
	public static int towerAp=30;
	public static int towerDp=30;
	public static int towerPrice=300;
	public static int towerArea=1;
	public static int hallArea=5;
	public static int hallPrice=5000;
	public static int hallAreaOffer=10;
	public static int marketArea=3;
	public static int marketPrice=3000;
	public static int marketGoldOffer=50;
	public static int fieldArea=3;
	public static int fieldPrice=3000;
	public static int fieldRiceOffer=100;
	public static int knightHallArea=2;
	public static int knightHallPrice=2500;
	public static int footmanHallArea=2;
	public static int footmanHallPrice=2000;
	public static int archerHallArea=2;
	public static int archerHallPrice=2000;
	public static int heroRice=5;
	public static int random(int start,int end)
	{
		if(start<0)start=0;
		int x=(int)((end-start+1)*Math.random())+start;
		return x;
	}
	public static int isBash(int x)
	{
		int z=random(0,100);
		if(z<=x)return 1;
		else return 0;
	}

}
