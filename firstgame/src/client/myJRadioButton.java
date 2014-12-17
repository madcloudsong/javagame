package client;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.*;
//自己重写的单选框类 30行
class myJRadioButton extends JRadioButton
{	
	int id;
	ImageIcon myImg;
	//构造函数
	myJRadioButton()
	{
		super();		
	}	
	myJRadioButton(String picpath,boolean x,int id)
	{
		super(picpath,x);	
		this.id=id;
	}
	//获取单选框的id，
	public int getId()
	{
		return id;
	}
	
	
}
