package client;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.*;
//�Լ���д�ĵ�ѡ���� 30��
class myJRadioButton extends JRadioButton
{	
	int id;
	ImageIcon myImg;
	//���캯��
	myJRadioButton()
	{
		super();		
	}	
	myJRadioButton(String picpath,boolean x,int id)
	{
		super(picpath,x);	
		this.id=id;
	}
	//��ȡ��ѡ���id��
	public int getId()
	{
		return id;
	}
	
	
}
