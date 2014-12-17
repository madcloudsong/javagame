package client;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
//�Լ���д��ͼƬ��ť�� 90��
class myButton extends JButton
{
	String myPicPath;
	int myX,myY;
	int playerId;
	int heroId;
	ImageIcon myImg;
	//�����õ��ĸ��ֹ��캯��
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
	//����ͼƬ
	public void mySetPic(String picpath)
	{
		mySetPicPath(picpath);
		super.repaint();
	}
	//����ͼƬ·��
	public void mySetPicPath(String picpath)
	{
		myPicPath=picpath;
	}
	//���û���ͼƬ��С
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
