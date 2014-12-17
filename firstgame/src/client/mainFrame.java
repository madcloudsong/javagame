package client;

//�ͻ�����Ϸ��������  1330��
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sharedData.myDB;
import sharedData.mySocketData;
import sharedData.player;
import sharedData.sharedData;

//����TEXTAREA�Զ���ʾ������һ��
class clock extends Thread {
	int time = 1000;

	JTextArea x;

	clock(JTextArea x, int time) {
		this.x = x;
		this.time = time;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(time);
			} catch (Exception e) {
			}
			x.setCaretPosition(x.getDocument().getLength());
		}
	}
}

// ����ͨ��TEXTFIELD��ʱ����ʱû��ʹ��
class clock2 extends Thread {
	int time = 1000;

	JTextField x;

	int num;

	clock2(JTextField x, int time, int num) {
		this.x = x;
		this.time = time;
		this.num = num;
	}

	public void run() {

		while (true) {
			try {
				Thread.sleep(time);
			} catch (Exception e) {
			}
			x.setCaretPosition(x.getDocument().getLength());
		}
	}
}

// ����ս���д�ͼƬ�ĳ���
class largepicThread extends Thread {
	mainFrame frame;

	largepicThread(mainFrame frame) {
		this.frame = frame;
	}

	public void run() {
		frame.largeimg.setVisible(true);
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		frame.largeimg.setVisible(false);
	}

}

// �����ȡ����
class audio extends Thread {
	public AudioClip clip, clip2;

	URL url, url2;

	mainFrame x;

	audio(mainFrame x) {
		super(x);
		this.x = x;
	}

	public void run() {
		x.�����¼.append("���ڶ�ȡ����...\n");
		try {
			File file = new File("audio1.WAV");
			url = file.toURL();
			clip = Applet.newAudioClip(url);
			File file2 = new File("audio2.WAV");
			url2 = file2.toURL();
			clip2 = Applet.newAudioClip(url2);
		} catch (Exception e) {
		}
		x.clip = clip;
		x.clip2 = clip2;
		x.clipdone = 1;
		x.clip.loop();
		x.clipstart.setEnabled(false);
		x.clipstop.setEnabled(true);
	}
}

// ��������
public class mainFrame extends JFrame implements ActionListener, KeyListener,
		Runnable, MouseListener {
	myDB db;

	yesno yesnodialog;

	JMenuBar bar1;

	JMenu mainmenu, infomenu, clipmenu;

	JMenuItem exit, helpm, datam, clipstart, clipstop;

	help helpdialog;

	data datadialog;

	JPanel main, chat, info, battle;

	ClientThread com;

	player myself;

	myButton castle[][] = null;

	Thread largepic;

	howmany howmanydialog;

	Thread bthread;

	JTextArea �����¼, ��������;

	JButton ����;

	audio aud;

	player h;

	// popmenu
	JPopupMenu popMy, popOther;

	JMenu ����, ��ļ, ó��, ��ɢ, ���, Ӣ��;

	JMenuItem ������, �г�, ũ��, ���Ӫ, ����Ӫ, ����Ӫ, ����, �佫, ���, ����, ����, ��װ��, ������2, �г�2,
			ũ��2, ���Ӫ2, ����Ӫ2, ����Ӫ2, ����2, ���2, ����2, ����2, �鿴, ����, �鱨, ˽��;

	// ��Ϣ�������
	JTextField name, gold, goldpm, rice, area, heronum, knightnum, footmannum,
			archernum, towernum, hallnum, marketnum, fieldnum;

	// ս���������
	myButton myhero, yourhero, mykt, yourkt, myfm, yourfm, myar, yourar, mytr,
			yourtr, largeimg;

	JTextField mymp, yourmp, myktnum, yourktnum, myfmnum, yourfmnum, myarnum,
			yourarnum, mytrnum, yourtrnum, myname, yourname, ktname, fmname,
			arname, trname, ktname2, fmname2, arname2, trname2;

	JTextArea ս����Ϣ;

	Box ս�����1, ս�����2;

	JButton bhero, bhattack, bhmagic, battack, baim1, baim2, baim3, baim4,
			bhescape;

	JButton battackturn;

	int turn;

	int choice;

	int inbattle;

	int go;

	clock2 limit;

	int aimId;

	int escape;

	info infodialog;

	heroinfo herodialog;

	buyitems itemsdialog;

	clock clk1, clk2;

	AudioClip clip;

	AudioClip clip2;

	int clipdone;

	mainFrame(String s, myDB db, player myself) {
		super(s);
		try {
			this.db = db;
		} catch (Exception eeeee) {
			System.exit(1);
		}
		this.myself = myself;
		setLayout(null);
		h = new player();
		bthread = new Thread(this);
		// �˵��Ľ���
		bar1 = new JMenuBar();
		mainmenu = new JMenu("��Ϸ����");
		infomenu = new JMenu("��Ϸ��Ϣ");
		clipmenu = new JMenu("��Ϸ����");
		exit = new JMenuItem("�˳���Ϸ");
		helpm = new JMenuItem("��Ϸ����");
		datam = new JMenuItem("��Ϸ����");
		clipstart = new JMenuItem("��������");
		clipstop = new JMenuItem("ֹͣ����");
		mainmenu.add(exit);
		infomenu.add(helpm);
		infomenu.add(datam);
		clipmenu.add(clipstart);
		clipmenu.add(clipstop);
		clipstart.setEnabled(false);
		clipstop.setEnabled(false);
		bar1.add(mainmenu);
		bar1.add(infomenu);
		bar1.add(clipmenu);
		setJMenuBar(bar1);
		exit.addActionListener(this);
		helpm.addActionListener(this);
		datam.addActionListener(this);
		clipstart.addActionListener(this);
		clipstop.addActionListener(this);
		// �˳��ĶԻ���Ľ���
		yesnodialog = new yesno(this, "ȷ��ô", true);
		yesnodialog.setVisible(false);
		yesnodialog.setResizable(false);
		// yesnodialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		infodialog = new info(this, "��Ϣ", true);
		infodialog.setVisible(false);
		infodialog.setResizable(false);
		// infodialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		itemsdialog = new buyitems(this, "����װ��", true);
		itemsdialog.setVisible(false);
		itemsdialog.setResizable(false);
		// itemsdialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		// �����Ի���
		howmanydialog = new howmany(this, "����?", true);
		howmanydialog.setVisible(false);
		howmanydialog.setResizable(false);

		herodialog = new heroinfo(this, "�佫��Ϣ", true, db.hero[myself.hero]);
		herodialog.setVisible(false);
		herodialog.setResizable(false);

		helpdialog = new help(this, "��Ϸ����", true);
		helpdialog.setVisible(false);
		helpdialog.setResizable(false);

		datadialog = new data(this, "��Ϸ����", true, db);
		datadialog.setVisible(false);
		datadialog.setResizable(false);
		// howmanydialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		// ������Ĵ���
		main = new JPanel() { // Ϊ�������һ��ͼƬ����
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon img = new ImageIcon("img/normalbackground.jpg");
				g.drawImage(img.getImage(), 0, 0, 600, 600, null);
			}
		};
		castle = new myButton[6][6];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				castle[i][j] = new myButton("img/castle6.jpg", 60, 60);
				castle[i][j].setBounds(20 + 100 * i, 20 + 100 * j, 60, 60);
				main.add(castle[i][j]);
				castle[i][j].setEnabled(false);
				castle[i][j].addActionListener(this);
			}
		}
		popMy = new JPopupMenu();
		popOther = new JPopupMenu();
		;
		���� = new JMenu("����");
		��ļ = new JMenu("��ļ");
		ó�� = new JMenu("ó��");
		��ɢ = new JMenu("��ɢ");
		��� = new JMenu("���");
		Ӣ�� = new JMenu("�佫");
		������ = new JMenuItem("������");
		������.addActionListener(this);
		�г� = new JMenuItem("�г�");
		�г�.addActionListener(this);
		ũ�� = new JMenuItem("ũ��");
		ũ��.addActionListener(this);
		���Ӫ = new JMenuItem("���Ӫ");
		���Ӫ.addActionListener(this);
		����Ӫ = new JMenuItem("����Ӫ");
		����Ӫ.addActionListener(this);
		����Ӫ = new JMenuItem("����Ӫ");
		����Ӫ.addActionListener(this);
		���� = new JMenuItem("����");
		����.addActionListener(this);
		�佫 = new JMenuItem("�佫");
		�佫.addActionListener(this);
		��� = new JMenuItem("���");
		���.addActionListener(this);
		���� = new JMenuItem("����");
		����.addActionListener(this);
		���� = new JMenuItem("����");
		����.addActionListener(this);
		��װ�� = new JMenuItem("��װ��");
		��װ��.addActionListener(this);
		������2 = new JMenuItem("������");
		������2.addActionListener(this);
		�г�2 = new JMenuItem("�г�");
		�г�2.addActionListener(this);
		ũ��2 = new JMenuItem("ũ��");
		ũ��2.addActionListener(this);
		���Ӫ2 = new JMenuItem("���Ӫ");
		���Ӫ2.addActionListener(this);
		����Ӫ2 = new JMenuItem("����Ӫ");
		����Ӫ2.addActionListener(this);
		����Ӫ2 = new JMenuItem("����Ӫ");
		����Ӫ2.addActionListener(this);
		����2 = new JMenuItem("����");
		����2.addActionListener(this);
		���2 = new JMenuItem("���");
		���2.addActionListener(this);
		����2 = new JMenuItem("����");
		����2.addActionListener(this);
		����2 = new JMenuItem("����");
		����2.addActionListener(this);
		�鿴 = new JMenuItem("�鿴");
		�鿴.addActionListener(this);
		���� = new JMenuItem("����");
		����.addActionListener(this);
		�鱨 = new JMenuItem("�鱨");
		�鱨.addActionListener(this);
		˽�� = new JMenuItem("˽��");
		˽��.addActionListener(this);
		����.add(������);
		����.add(�г�);
		����.add(ũ��);
		// ����.add(���Ӫ);����.add(����Ӫ);����.add(����Ӫ);
		����.add(����);
		��ļ.add(�佫);
		��ļ.add(���);
		��ļ.add(����);
		��ļ.add(����);
		ó��.add(��װ��);
		���.add(������2);
		���.add(�г�2);
		���.add(ũ��2);
		// ���.add(���Ӫ2);���.add(����Ӫ2);���.add(����Ӫ2);
		���.add(����2);
		��ɢ.add(���2);
		��ɢ.add(����2);
		��ɢ.add(����2);
		Ӣ��.add(�鿴);
		popMy.add(����);
		popMy.add(��ļ);
		popMy.add(ó��);
		popMy.add(���);
		popMy.add(��ɢ);
		popMy.add(Ӣ��);
		popOther.add(����);
		popOther.add(�鱨);
		main.addMouseListener(this);
		main.setLayout(null);
		main.setBounds(0, 0, 600, 600);
		main.setVisible(true);

		// ս������Ĵ���
		battle = new JPanel() { // Ϊ�������һ��ͼƬ����
			int rand = sharedData.random(1, 4);

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon img = new ImageIcon("img/battlebackground" +rand
						+ ".jpg");
				g.drawImage(img.getImage(), 0, 0, 600, 600, null);
			}
		};
		// myhero=new myButton(db.hero[myself.hero].picpath,45,45);
		myhero = new myButton("img/knight.gif", 45, 45);
		yourhero = new myButton("img/knight.gif", 45, 45);
		mykt = new myButton("img/knight.gif", 45, 45);
		yourkt = new myButton("img/knight.gif", 45, 45);
		myfm = new myButton("img/footman.gif", 45, 45);
		yourfm = new myButton("img/footman.gif", 45, 45);
		myar = new myButton("img/archer.gif", 45, 45);
		yourar = new myButton("img/archer.gif", 45, 45);
		mytr = new myButton("img/tower.gif", 45, 45);
		yourtr = new myButton("img/tower.gif", 45, 45);
		largeimg = new myButton("img/largepic/largedefault.jpg", 200, 200);
		mymp = new JTextField("0");
		mymp.setEditable(false);
		yourmp = new JTextField("0");
		yourmp.setEditable(false);
		myktnum = new JTextField("0");
		myktnum.setEditable(false);
		yourktnum = new JTextField("0");
		yourktnum.setEditable(false);
		myfmnum = new JTextField("0");
		myfmnum.setEditable(false);
		yourfmnum = new JTextField("0");
		yourfmnum.setEditable(false);
		myarnum = new JTextField("0");
		myarnum.setEditable(false);
		yourarnum = new JTextField("0");
		yourarnum.setEditable(false);
		mytrnum = new JTextField("0");
		mytrnum.setEditable(false);
		yourtrnum = new JTextField("0");
		yourtrnum.setEditable(false);
		myname = new JTextField("�佫");
		myname.setEditable(false);
		yourname = new JTextField("�佫");
		yourname.setEditable(false);
		ktname = new JTextField("���");
		ktname.setEditable(false);
		fmname = new JTextField("����");
		fmname.setEditable(false);
		arname = new JTextField("����");
		arname.setEditable(false);
		trname = new JTextField("����");
		trname.setEditable(false);
		ktname2 = new JTextField("���");
		ktname2.setEditable(false);
		fmname2 = new JTextField("����");
		fmname2.setEditable(false);
		arname2 = new JTextField("����");
		arname2.setEditable(false);
		trname2 = new JTextField("����");
		trname2.setEditable(false);

		ս�����1 = Box.createVerticalBox();
		ս�����2 = Box.createVerticalBox();
		bhero = new JButton("�佫");
		bhattack = new JButton("����");
		bhattack.addActionListener(this);
		bhmagic = new JButton("ս��");
		bhmagic.addActionListener(this);
		bhescape = new JButton("����");
		bhescape.addActionListener(this);
		battack = new JButton("Ŀ��");
		battackturn = new JButton("�佫����");
		battle.add(battackturn);
		battackturn.setBounds(440, 300, 120, 30);
		battackturn.setVisible(false);
		baim1 = new JButton("���");
		baim1.addActionListener(this);
		baim2 = new JButton("����");
		baim2.addActionListener(this);
		baim3 = new JButton("����");
		baim3.addActionListener(this);
		baim4 = new JButton("����");
		baim4.addActionListener(this);
		ս�����1.add(bhero);
		ս�����1.add(bhattack);
		ս�����1.add(bhmagic);
		ս�����1.add(bhescape);
		ս�����2.add(battack);
		ս�����2.add(baim1);
		ս�����2.add(baim2);
		ս�����2.add(baim3);
		ս�����2.add(baim4);
		ս�����1.setBounds(100, 100, 200, 240);
		ս�����1.setVisible(false);
		ս�����2.setBounds(300, 100, 200, 300);
		ս�����2.setVisible(false);
		battle.add(ս�����1);
		battle.add(ս�����2);

		Box box11 = Box.createVerticalBox();
		Box box33 = Box.createVerticalBox();
		Box box44 = Box.createVerticalBox();
		Box box66 = Box.createVerticalBox();
		box11.add(Box.createVerticalStrut(22));
		box11.add(mymp);
		box11.add(Box.createVerticalStrut(22));
		box11.add(myktnum);
		box11.add(Box.createVerticalStrut(22));
		box11.add(myfmnum);
		box11.add(Box.createVerticalStrut(22));
		box11.add(myarnum);
		box11.add(Box.createVerticalStrut(22));
		box11.add(mytrnum);
		box11.add(Box.createVerticalStrut(22));
		box33.add(Box.createVerticalStrut(22));
		box33.add(myname);
		box33.add(Box.createVerticalStrut(22));
		box33.add(ktname);
		box33.add(Box.createVerticalStrut(22));
		box33.add(fmname);
		box33.add(Box.createVerticalStrut(22));
		box33.add(arname);
		box33.add(Box.createVerticalStrut(22));
		box33.add(trname);
		box33.add(Box.createVerticalStrut(22));
		box66.add(Box.createVerticalStrut(22));
		box66.add(yourmp);
		box66.add(Box.createVerticalStrut(22));
		box66.add(yourktnum);
		box66.add(Box.createVerticalStrut(22));
		box66.add(yourfmnum);
		box66.add(Box.createVerticalStrut(22));
		box66.add(yourarnum);
		box66.add(Box.createVerticalStrut(22));
		box66.add(yourtrnum);
		box66.add(Box.createVerticalStrut(22));
		box44.add(Box.createVerticalStrut(22));
		box44.add(yourname);
		box44.add(Box.createVerticalStrut(22));
		box44.add(ktname2);
		box44.add(Box.createVerticalStrut(22));
		box44.add(fmname2);
		box44.add(Box.createVerticalStrut(22));
		box44.add(arname2);
		box44.add(Box.createVerticalStrut(22));
		box44.add(trname2);
		box44.add(Box.createVerticalStrut(22));
		battle.add(myhero);
		myhero.setBounds(480, 40, 45, 45);
		battle.add(yourkt);
		yourkt.setBounds(480, 92, 45, 45);
		battle.add(yourfm);
		yourfm.setBounds(480, 144, 45, 45);
		battle.add(yourar);
		yourar.setBounds(480, 196, 45, 45);
		battle.add(yourtr);
		yourtr.setBounds(480, 248, 45, 45);
		battle.add(yourhero);
		yourhero.setBounds(80, 40, 45, 45);
		battle.add(mykt);
		mykt.setBounds(80, 92, 45, 45);
		battle.add(myfm);
		myfm.setBounds(80, 144, 45, 45);
		battle.add(myar);
		myar.setBounds(80, 196, 45, 45);
		battle.add(mytr);
		mytr.setBounds(80, 248, 45, 45);
		battle.add(box11);
		battle.add(box33);
		battle.add(box44);
		battle.add(box66);
		battle.add(largeimg);
		largeimg.setBounds(200, 100, 200, 200);
		largeimg.setVisible(false);

		box44.setBounds(19, 30, 60, 280);
		box66.setBounds(126, 30, 60, 280);
		box11.setBounds(419, 30, 60, 280);
		box33.setBounds(526, 30, 60, 280);
		ս����Ϣ = new JTextArea(3, 3);
		ս����Ϣ.setLineWrap(true);
		ս����Ϣ.setCaretPosition(ս����Ϣ.getDocument().getLength());
		JScrollPane jsp3 = new JScrollPane(ս����Ϣ);
		ս����Ϣ.setEditable(false);
		jsp3.setBounds(100, 370, 400, 200);
		battle.add(jsp3);
		battle.setLayout(null);
		battle.setBounds(0, 0, 600, 600);
		battle.setVisible(false);

		// �������Ĵ���
		chat = new JPanel();
		�����¼ = new JTextArea(3, 3);
		�����¼.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(�����¼);
		�����¼.setCaretPosition(�����¼.getDocument().getLength());
		�����¼.setEditable(false);
		�������� = new JTextArea(3, 3);
		��������.setLineWrap(true);
		JScrollPane jsp2 = new JScrollPane(��������);
		��������.setEditable(true);
		���� = new JButton("����");
		��������.addKeyListener(this);
		����.addActionListener(this);
		jsp.setBounds(0, 0, 290, 200);
		jsp2.setBounds(0, 200, 230, 100);
		����.setBounds(230, 200, 60, 60);
		chat.setLayout(null);
		chat.add(����);
		chat.add(jsp);
		chat.add(jsp2);
		chat.setBounds(601, 0, 290, 300);

		// ��Ϣ����Ĵ���
		info = new JPanel();
		info.setLayout(null);
		name = new JTextField("�Ǳ�");
		name.setEditable(false);
		gold = new JTextField("0");
		gold.setEditable(false);
		goldpm = new JTextField("0");
		goldpm.setEditable(false);
		rice = new JTextField("0/0");
		rice.setEditable(false);
		area = new JTextField("0/0");
		area.setEditable(false);
		knightnum = new JTextField("0");
		knightnum.setEditable(false);
		footmannum = new JTextField("0");
		footmannum.setEditable(false);
		archernum = new JTextField("0");
		archernum.setEditable(false);
		towernum = new JTextField("0");
		towernum.setEditable(false);
		hallnum = new JTextField("0");
		hallnum.setEditable(false);
		marketnum = new JTextField("0");
		marketnum.setEditable(false);
		fieldnum = new JTextField("0");
		fieldnum.setEditable(false);
		heronum = new JTextField("1");
		heronum.setEditable(false);
		Box basebox = Box.createVerticalBox();
		Box boxx = Box.createHorizontalBox();
		Box box1 = Box.createHorizontalBox();
		Box box2 = Box.createHorizontalBox();
		Box box3 = Box.createHorizontalBox();
		Box box4 = Box.createHorizontalBox();
		Box box5 = Box.createVerticalBox();
		Box box6 = Box.createVerticalBox();
		Box box7 = Box.createVerticalBox();
		Box box8 = Box.createVerticalBox();
		box1.add(Box.createHorizontalStrut(45));
		box1.add(new Label("�Ǳ�����"));
		box1.add(name);
		box1.add(Box.createHorizontalStrut(45));
		box2.add(Box.createHorizontalStrut(10));
		box3.add(Box.createHorizontalStrut(25));
		box4.add(Box.createHorizontalStrut(25));
		box2.add(new Label("���н�Ǯ"));
		box2.add(gold);
		box2.add(Box.createHorizontalStrut(5));
		box2.add(new Label("��Ǯ����"));
		box2.add(goldpm);
		box3.add(new Label("�Ǳ����"));
		box3.add(area);
		box4.add(new Label("�Ǳ���ʳ"));
		box4.add(rice);
		box5.add(new Label("�佫����"));
		box5.add(Box.createVerticalStrut(12));
		box6.add(heronum);
		box6.add(Box.createVerticalStrut(12));
		box5.add(new Label("�������"));
		box5.add(Box.createVerticalStrut(12));
		box6.add(knightnum);
		box6.add(Box.createVerticalStrut(12));
		box7.add(new Label("����������"));
		box7.add(Box.createVerticalStrut(12));
		box8.add(hallnum);
		box8.add(Box.createVerticalStrut(12));
		box5.add(new Label("��������"));
		box5.add(Box.createVerticalStrut(12));
		box6.add(footmannum);
		box6.add(Box.createVerticalStrut(12));
		box7.add(new Label("�г�����"));
		box7.add(Box.createVerticalStrut(12));
		box8.add(marketnum);
		box8.add(Box.createVerticalStrut(12));
		box5.add(new Label("��������"));
		box5.add(Box.createVerticalStrut(12));
		box6.add(archernum);
		box6.add(Box.createVerticalStrut(12));
		box7.add(new Label("ũ������"));
		box7.add(Box.createVerticalStrut(12));
		box8.add(fieldnum);
		box8.add(Box.createVerticalStrut(12));
		box7.add(new Label("��������"));
		box7.add(Box.createVerticalStrut(12));
		box8.add(towernum);
		box8.add(Box.createVerticalStrut(12));
		box2.add(Box.createHorizontalStrut(10));
		box3.add(Box.createHorizontalStrut(25));
		box4.add(Box.createHorizontalStrut(25));

		boxx.add(Box.createHorizontalStrut(10));
		boxx.add(box5);
		boxx.add(box6);
		boxx.add(Box.createHorizontalStrut(5));
		boxx.add(box7);
		boxx.add(box8);
		boxx.add(Box.createHorizontalStrut(10));

		basebox.add(Box.createVerticalStrut(12));
		basebox.add(box1);
		basebox.add(Box.createVerticalStrut(12));
		basebox.add(box3);
		basebox.add(Box.createVerticalStrut(12));
		basebox.add(box4);
		basebox.add(Box.createVerticalStrut(12));
		basebox.add(box2);
		basebox.add(Box.createVerticalStrut(12));
		basebox.add(boxx);
		basebox.add(Box.createVerticalStrut(12));

		basebox.setBounds(0, 0, 290, 300);
		info.add(basebox);
		info.setBounds(601, 301, 290, 300);
		// ��ʱ��
		clk1 = new clock(�����¼, 1000);
		clk2 = new clock(ս����Ϣ, 1000);
		clk1.start();
		clk2.start();
		audio aud = new audio(this);
		aud.start();
		add(chat);
		add(main);
		add(info);
		add(battle);
		setBounds(10, 10, 900, 655);
		setVisible(true);
		setResizable(false);
		validate();
	}

	// ���½������Ϣ
	public void updateInfo() {
		name.setText(myself.castlename);
		gold.setText(myself.gold + "");
		goldpm.setText(myself.goldpm + "");
		rice.setText(myself.riceNow + "/" + myself.riceMax);
		area.setText(myself.areaNow + "/" + myself.areaMax);
		knightnum.setText(myself.knightNum + "");
		footmannum.setText(myself.footmanNum + "");
		archernum.setText(myself.archerNum + "");
		towernum.setText(myself.towerNum + "");
		hallnum.setText(myself.hallNum + "");
		marketnum.setText(myself.marketNum + "");
		fieldnum.setText(myself.fieldNum + "");
		heronum.setText(db.hero[myself.hero].level + "");
		if (this.inbattle == 1) {
			mymp.setText(myself.MP + "");
			myktnum.setText(myself.knightNum + "");
			myarnum.setText(myself.archerNum + "");
			myfmnum.setText(myself.footmanNum + "");
			if (myself.isDefence == 1) {
				mytrnum.setText(myself.towerNum + "");
			} else
				mytrnum.setText("0");
		}
	}

	// �����������Ϣ
	public int sendChat(String s) {
		if (s.equals("")) {
			return 0;
		}
		�����¼.append(myself.castlename + "˵:" + s + "\n");
		s = mySocketData.CHAT + " " + myself.castlename + "˵:" + s;
		com.send(s);
		��������.setText("");
		��������.requestFocusInWindow();
		return 1;
	}

	// ���������¼
	public int setChat(String s) {
		�����¼.append(s + "\n");
		return 1;
	}

	// ����ս����Ϣ
	public int setBattleChat(String s) {
		ս����Ϣ.append(s + "\n");
		return 1;
	}

	// ����ս���еĴ�ͼƬ·��
	public void setLargePic(String s) {
		largeimg.mySetPic(s);
		largepic = new largepicThread(this);
		largepic.start();
	}

	// ս����ʼ����
	public void battlebegin() {
        System.out.println("battlebegin");
		clip.stop();
		clipstart.setEnabled(false);
		clipstop.setEnabled(false);
		battle.setVisible(true);
		main.setVisible(false);
		ս����Ϣ.setText("");
		inbattle = 1;
		escape = 0;
		myself.battleinit(db);
        System.out.println("battleinit");
		mymp.setText(myself.MP + "");
		myktnum.setText(myself.knightNum + "");
		myarnum.setText(myself.archerNum + "");
		myfmnum.setText(myself.footmanNum + "");
		if (myself.isDefence == 1) {
			mytrnum.setText(myself.towerNum + "");
		} else
			mytrnum.setText("0");
		myname.setText(db.hero[myself.hero].name);
		myhero.mySetPic(db.hero[myself.hero].picpath);
        System.out.println("new thread");
		bthread = new Thread(this);
		bthread.start();
	}

	// �ͻ���ս���߳�
	public void run() {
		if (Thread.currentThread() == bthread) {
			yourmp.setText(h.MP + "");
			yourktnum.setText(h.knightNum + "");
			yourarnum.setText(h.archerNum + "");
			yourfmnum.setText(h.footmanNum + "");
			if (h.isDefence == 1) {
				yourtrnum.setText(h.towerNum + "");
			} else
				yourtrnum.setText("0");
			;
            System.out.println("begin if");
			if (h.knightNum == 0)
				baim1.setEnabled(false);
			else
				baim1.setEnabled(true);
			if (h.footmanNum == 0)
				baim2.setEnabled(false);
			else
				baim2.setEnabled(true);
			if (h.archerNum == 0)
				baim3.setEnabled(false);
			else
				baim3.setEnabled(true);
			if (h.towerNum == 0 || h.isDefence == 0)
				baim4.setEnabled(false);
			else
				baim4.setEnabled(true);
			mymp.setText(myself.MP + "");
			myktnum.setText(myself.knightNum + "");
			myarnum.setText(myself.archerNum + "");
			myfmnum.setText(myself.footmanNum + "");
			if (myself.isDefence == 1) {
				mytrnum.setText(myself.towerNum + "");
			} else
				mytrnum.setText("0");
			yourname.setText(db.hero[h.hero].name);
            System.out.println(db.hero[h.hero].picpath);
			yourhero.mySetPic(db.hero[h.hero].picpath);
			if (h.isfail() == 1 || myself.isfail() == 1) {
				return;
			}
			turn = 1;
			go = 0;
			if (db.hero[myself.hero].hasMagic2() == 1
					&& db.magic[db.hero[myself.hero].magic2].MP <= myself.MP) {
				bhmagic.setEnabled(true);
			} else
				bhmagic.setEnabled(false);
			battackturn.setText("�佫����");
			battackturn.setVisible(true);
			ս�����1.setLocation(300, 50);
			ս�����1.setVisible(true);
            System.out.println("begin while");

			while (true) {

				try {
					Thread.sleep(5);

				} catch (Exception e) {
				}

				if (go == 1) {
					break;
				}
				if (escape == 1) {
					return;
				}
			}
			turn = 2;
			go = 0;
			if (myself.knightNum != 0) {
				battackturn.setText("�������");
				battackturn.setVisible(true);
				ս�����2.setLocation(350, turn * 50);
				ս�����2.setVisible(true);

				while (true) {

					try {
						Thread.sleep(5);

					} catch (Exception e) {
					}

					if (go == 1)
						break;
				}
			}
			turn = 3;
			go = 0;
			if (myself.footmanNum != 0) {
				battackturn.setText("��������");
				battackturn.setVisible(true);
				ս�����2.setLocation(350, turn * 50);
				ս�����2.setVisible(true);

				while (true) {

					try {
						Thread.sleep(5);

					} catch (Exception e) {
					}

					if (go == 1)
						break;
				}
			}
			turn = 4;
			go = 0;
			if (myself.archerNum != 0) {
				battackturn.setText("��������");
				battackturn.setVisible(true);
				ս�����2.setLocation(350, turn * 50);
				ս�����2.setVisible(true);

				while (true) {

					try {
						Thread.sleep(5);

					} catch (Exception e) {
					}

					if (go == 1)
						break;
				}
			}
			turn = 5;
			go = 0;
			if (myself.towerNum != 0 && myself.isDefence == 1) {
				battackturn.setText("��������");
				battackturn.setVisible(true);
				ս�����2.setLocation(350, turn * 50);
				ս�����2.setVisible(true);

				while (true) {

					try {
						Thread.sleep(5);

					} catch (Exception e) {
					}
					if (go == 1)
						break;
				}
			}
            System.out.println("setLargePic");
			this.setLargePic(db.hero[myself.hero].largepicpath);
			com.send(mySocketData.BATTLE + " " + mySocketData.READY);
		}
	}

	// ս������
	public void battleend() {
        System.out.println("battleend");
		ս�����1.setVisible(false);
		ս�����2.setVisible(false);
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
		}
		battle.setVisible(false);
		main.setVisible(true);
		inbattle = 0;
		escape = 0;
		clip.loop();
		clipstart.setEnabled(false);
		clipstop.setEnabled(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			yesnodialog.setVisible(true);
			if (yesnodialog.getMessage() == yesno.YES) {
				System.exit(0);
			} else
				return;
		} else if (e.getSource() == ����) {
			sendChat(��������.getText());
		} else if (e.getSource() == clipstart) {
			�����¼.append("\n��ʼ��������\n");
			clip.loop();
			clipstart.setEnabled(false);
			clipstop.setEnabled(true);
		} else if (e.getSource() == clipstop) {
			�����¼.append("\nֹͣ��������\n");
			clip.stop();
			clipstart.setEnabled(true);
			clipstop.setEnabled(false);
		} else if (e.getSource() == helpm) {
			helpdialog.setVisible(true);
		} else if (e.getSource() == datam) {
			datadialog.setVisible(true);
		} else
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 6; j++) {
					if (e.getSource() == castle[i][j]) {
						if (i == myself.x && j == myself.y) {
							int x = castle[i][j].getX();
							int y = castle[i][j].getY();
							popMy.show(main, x + 20, y + 20);
						} else {
							int x = castle[i][j].getX();
							int y = castle[i][j].getY();
							popOther.show(main, x + 20, y + 20);
							aimId = castle[i][j].playerId;
						}
					}
				}
			}
		if (e.getSource() == bhattack) {
			choice = 1;
			ս�����2.setVisible(false);
			ս�����2.setLocation(360, 50);
			ս�����2.setVisible(true);
		} else if (e.getSource() == bhmagic) {
			choice = 2;
			ս�����2.setVisible(false);
			ս�����2.setLocation(360, 80);
			ս�����2.setVisible(true);
		} else if (e.getSource() == bhescape) {
			choice = 3;
			yesnodialog.setVisible(true);
			if (yesnodialog.message == yesno.YES) {
				com.send(mySocketData.BATTLE + " " + mySocketData.ESCAPE);
				// com.send(mySocketData.BATTLE+" "+mySocketData.READY);
				ս�����1.setVisible(false);
				ս�����2.setVisible(false);
				escape = 1;
			}
		} else if (e.getSource() == baim1) {
			ս�����2.setVisible(false);
			if (turn == 1) {
				ս�����1.setVisible(false);
				com.send(mySocketData.BATTLE + " " + mySocketData.HERO + " "
						+ (choice * 10 + 2));
				if (choice == 2) {
					myself.MP -= db.magic[db.hero[myself.hero].magic2].MP;
				}

			} else
				for (int j = 2; j <= 5; j++) {
					if (turn == j) {
						com.send(mySocketData.BATTLE + " "
								+ mySocketData.ATTACK + " " + (j * 10 + 2));
					}
				}
			go = 1;
		} else if (e.getSource() == baim2) {
			ս�����2.setVisible(false);

			if (turn == 1) {
				ս�����1.setVisible(false);
				com.send(mySocketData.BATTLE + " " + mySocketData.HERO + " "
						+ (choice * 10 + 3));
				if (choice == 2) {
					myself.MP -= db.magic[db.hero[myself.hero].magic2].MP;
				}

			} else
				for (int j = 2; j <= 5; j++) {
					if (turn == j) {
						com.send(mySocketData.BATTLE + " "
								+ mySocketData.ATTACK + " " + (j * 10 + 3));
					}
				}
			go = 1;
		} else if (e.getSource() == baim3) {
			ս�����2.setVisible(false);

			if (turn == 1) {
				ս�����1.setVisible(false);
				com.send(mySocketData.BATTLE + " " + mySocketData.HERO + " "
						+ (choice * 10 + 4));
				if (choice == 2) {
					myself.MP -= db.magic[db.hero[myself.hero].magic2].MP;
				}

			} else
				for (int j = 2; j <= 5; j++) {
					if (turn == j) {
						com.send(mySocketData.BATTLE + " "
								+ mySocketData.ATTACK + " " + (j * 10 + 4));
					}
				}
			go = 1;
		} else if (e.getSource() == baim4) {
			ս�����2.setVisible(false);

			if (turn == 1) {
				ս�����1.setVisible(false);
				com.send(mySocketData.BATTLE + " " + mySocketData.HERO + " "
						+ (choice * 10 + 5));
				if (choice == 2) {
					myself.MP -= db.magic[db.hero[myself.hero].magic2].MP;
				}

			} else
				for (int j = 2; j <= 5; j++) {
					if (turn == j) {
						com.send(mySocketData.BATTLE + " "
								+ mySocketData.ATTACK + " " + (j * 10 + 5));
					}
				}
			go = 1;
		} else if (e.getSource() == ������) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildHall(db, howmanydialog.num) == 1) {
					com.send(mySocketData.BUILD + " " + mySocketData.HALL + " "
							+ howmanydialog.num);
				} else {
					�����¼.append("ϵͳ��Ϣ������ʧ�ܣ�\n");
				}
			}
			updateInfo();
		} else if (e.getSource() == ������2) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildHall(db, -howmanydialog.num) == 1) {
					com.send(mySocketData.UNBUILD + " " + mySocketData.HALL
							+ " " + howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == �г�) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildMarket(db, howmanydialog.num) == 1) {
					com.send(mySocketData.BUILD + " " + mySocketData.MARKET
							+ " " + howmanydialog.num);
				} else {
					�����¼.append("ϵͳ��Ϣ������ʧ�ܣ�\n");
				}
			}
			updateInfo();
		} else if (e.getSource() == �г�2) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildMarket(db, -howmanydialog.num) == 1) {
					com.send(mySocketData.UNBUILD + " " + mySocketData.MARKET
							+ " " + howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == ũ��) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildField(db, howmanydialog.num) == 1) {
					com.send(mySocketData.BUILD + " " + mySocketData.FIELD
							+ " " + howmanydialog.num);
				} else {
					�����¼.append("ϵͳ��Ϣ������ʧ�ܣ�\n");
				}
			}
			updateInfo();
		} else if (e.getSource() == ũ��2) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildField(db, -howmanydialog.num) == 1) {
					com.send(mySocketData.UNBUILD + " " + mySocketData.FIELD
							+ " " + howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == ���Ӫ) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildKnightHall(db, howmanydialog.num) == 1) {
					com.send(mySocketData.BUILD + " " + mySocketData.KNIGHTHALL
							+ " " + howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == ���Ӫ2) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildKnightHall(db, -howmanydialog.num) == 1) {
					com
							.send(mySocketData.UNBUILD + " "
									+ mySocketData.KNIGHTHALL + " "
									+ howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == ����Ӫ) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildFootmanHall(db, howmanydialog.num) == 1) {
					com.send(mySocketData.BUILD + " "
							+ mySocketData.FOOTMANHALL + " "
							+ howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == ����Ӫ2) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildFootmanHall(db, -howmanydialog.num) == 1) {
					com.send(mySocketData.UNBUILD + " "
							+ mySocketData.FOOTMANHALL + " "
							+ howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == ����Ӫ) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildArcherHall(db, howmanydialog.num) == 1) {
					com.send(mySocketData.BUILD + " " + mySocketData.ARCHERHALL
							+ " " + howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == ����Ӫ2) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildArcherHall(db, -howmanydialog.num) == 1) {
					com
							.send(mySocketData.UNBUILD + " "
									+ mySocketData.ARCHERHALL + " "
									+ howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == ����) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildTower(db, howmanydialog.num) == 1) {
					com.send(mySocketData.BUILD + " " + mySocketData.TOWER
							+ " " + howmanydialog.num);
				} else {
					�����¼.append("ϵͳ��Ϣ������ʧ�ܣ�\n");
				}
			}
			updateInfo();
		} else if (e.getSource() == ����2) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.buildTower(db, -howmanydialog.num) == 1) {
					com.send(mySocketData.UNBUILD + " " + mySocketData.TOWER
							+ " " + howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == ���) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.hireKnight(db, howmanydialog.num) == 1) {
					com.send(mySocketData.HIRE + " " + mySocketData.KNIGHT
							+ " " + howmanydialog.num);
				} else {
					�����¼.append("ϵͳ��Ϣ����ļʧ�ܣ�\n");
				}
			}
			updateInfo();
		} else if (e.getSource() == ���2) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.hireKnight(db, -howmanydialog.num) == 1) {
					com.send(mySocketData.UNHIRE + " " + mySocketData.KNIGHT
							+ " " + howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == ����) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.hireFootman(db, howmanydialog.num) == 1) {
					com.send(mySocketData.HIRE + " " + mySocketData.FOOTMAN
							+ " " + howmanydialog.num);
				} else {
					�����¼.append("ϵͳ��Ϣ����ļʧ�ܣ�\n");
				}
			}
			updateInfo();
		} else if (e.getSource() == ����2) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.hireFootman(db, -howmanydialog.num) == 1) {
					com.send(mySocketData.UNHIRE + " " + mySocketData.FOOTMAN
							+ " " + howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == ����) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.hireArcher(db, howmanydialog.num) == 1) {
					com.send(mySocketData.HIRE + " " + mySocketData.ARCHER
							+ " " + howmanydialog.num);
				} else {
					�����¼.append("ϵͳ��Ϣ����ļʧ�ܣ�\n");
				}
			}
			updateInfo();
		} else if (e.getSource() == ����2) {
			howmanydialog.setVisible(true);
			if (howmanydialog.message == howmany.YES) {
				if (myself.hireArcher(db, -howmanydialog.num) == 1) {
					com.send(mySocketData.UNHIRE + " " + mySocketData.ARCHER
							+ " " + howmanydialog.num);
				}
			}
			updateInfo();
		} else if (e.getSource() == �佫) {
			updateInfo();
		} else if (e.getSource() == ��װ��) {
			itemsdialog.setVisible(true);
			if (itemsdialog.message == howmany.YES && itemsdialog.choice != 0) {
				if (myself.gold >= db.items[itemsdialog.choice].price) {
					com.send(mySocketData.BUY + " " + itemsdialog.choice);
				} else {
					�����¼.append("ϵͳ��Ϣ��Ǯ������\n");
				}
			}
			updateInfo();
		} else if (e.getSource() == �鿴) {
			herodialog.update(db.hero[myself.hero]);
			herodialog.setVisible(true);
			updateInfo();
		} else if (e.getSource() == ����) {
			if (myself.isfail() == 1) {
				�����¼.append("��̫���ˣ���Ҫ�������\n");
				return;
			}
			com.send(mySocketData.ATTACK + " " + aimId);
			updateInfo();
		} else if (e.getSource() == �鱨) {
			com.send(mySocketData.INFOMATION + " " + aimId);
			updateInfo();
		}

	}

	public void keyPressed(KeyEvent e) {
		if (e.getModifiers() == InputEvent.CTRL_MASK
				&& e.getKeyCode() == KeyEvent.VK_ENTER) {
			sendChat(��������.getText());
		}

	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
			popMy.show(main, x, y);
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

}
