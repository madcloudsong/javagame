package client;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
//自己重写的图片按钮类 90行
class myButton extends JButton
{
	String myPicPath;
	int myX,myY;
	int playerId;
	int heroId;
	ImageIcon myImg;
	//可能用到的各种构造函数
	myButton()
	{
		super();
		myX=super.getWidth();
		myY=super.getHeight();
	}
	myButton(String s)
	{
		super(s);
		myX=super.getWidth();
		myY=super.getHeight();
	}
	myButton(String s,String picpath,int width,int height)
	{
		super(s);
		myPicPath=picpath;
		myX=width;
		myY=height;
	}
	myButton(String picpath,int width,int height)
	{
		super();
		myPicPath=picpath;
		myX=width;
		myY=height;
	}
	//更新图片
	public void mySetPic(String picpath)
	{
		mySetPicPath(picpath);
		super.repaint();
	}
	//设置图片路径
	public void mySetPicPath(String picpath)
	{
		myPicPath=picpath;
	}
	//设置画的图片大小
	public void mySetXY(int x,int y)
	{
		myX=x;
		myY=y;
	}
	public void mySetX(int x)
	{
		myX=x;
	}
	public void mySetY(int y)
	{
		myY=y;
	}
	public void setHeroId(int x)
	{
		heroId=x;
	}
	public void setPlayerId(int x)
	{
		playerId=x;
	}
	public int getHeroId(int x)
	{
		return heroId;
	}
	public int getPlayerId(int x)
	{
		return playerId;
	}
	public void paintComponent(Graphics g) { 
		super.paintComponent(g); 
		myImg = new ImageIcon(myPicPath); 
		g.drawImage(myImg.getImage(), 0, 0,myX,myY ,null); 
		} 
}
