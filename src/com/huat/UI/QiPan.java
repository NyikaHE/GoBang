package com.huat.UI;

import com.huat.bean.User;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * ����
 * @author HE
 *
 */
public class QiPan extends JFrame{
	private JMenuBar bar;//�˵���
	private JMenu system;
	private JMenu ptp;
	private JMenuItem login;
	private JMenuItem pp_phb;
	private JMenuItem pp_phw;
	private JMenu ptc;
	private JMenuItem pc_phb;
	private JMenuItem pc_phw;
	private Draw draw;
	private JLabel userInfo;
	public static JLabel gameInfo;
	public QiPan(){
		super("�������ս");
		init();
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ڹرմ���
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		int width=(int)d.getWidth();
		int height=(int)d.getHeight();

		this.setLocation((width-600)/2, (height-600)/2);
		this.setVisible(true);

		addListener();
	}
	//�������ʼ��
	public void init() {
		bar=new JMenuBar();
		system=new JMenu("ϵͳ����");
		login=new JMenuItem("��¼");
		ptp=new JMenu("���˶���");
		ptc=new JMenu("�˻�����");
		pp_phb=new JMenuItem("��ִ��");
		pp_phw=new JMenuItem("��ִ��");
		pc_phb=new JMenuItem("��ִ��");
		pc_phw=new JMenuItem("��ִ��");
		draw=new Draw();
		userInfo=new JLabel("hello world");
		userInfo.setHorizontalAlignment(JLabel.CENTER);
		gameInfo=new JLabel("������");
		gameInfo.setHorizontalAlignment(JLabel.CENTER);
				
		bar.add(system);
		system.add(login);
		system.add(ptp);
		system.add(ptc);
		
		ptc.add(pc_phb);
		ptc.add(pc_phw);
		ptp.add(pp_phb);
		ptp.add(pp_phw);
		
		this.setLayout(new BorderLayout());
		
		this.setJMenuBar(bar);
		this.add(userInfo, BorderLayout.NORTH);
		this.add(gameInfo, BorderLayout.SOUTH);
		this.add(draw, BorderLayout.CENTER);
	}
	
	
	public void addListener(){
		//���˶����кڷ�����
		pp_phb.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
                draw.setGamestate(1);
				draw.getUser().setColor(2);
				draw.setGamestart(true);
				draw.setPlayer(2);
				gameInfo.setText("��Ϸ��ʼ���ڷ�����");
				draw.connect();
			}
			
		});
		//���˶����а׷�����
		pp_phw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    draw.setGamestate(1);
				draw.getUser().setColor(1);
                draw.setGamestart(true);
                draw.setPlayer(1);
				gameInfo.setText("��Ϸ��ʼ���׷�����");
				draw.connect();
			}
		});
		//�˻���������ִ��
		pc_phb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                draw.setGamestate(2);
				draw.getUser().setColor(1);
				gameInfo.setText("��Ϸ��ʼ���ڷ�����");
			}
		});
		//�˻���������ִ��
		pc_phw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                draw.setGamestate(2);
				draw.getUser().setColor(2);
				gameInfo.setText("��Ϸ��ʼ���׷�����");
			}
		});
		//��½����
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username=JOptionPane.showInputDialog("�������û���");
				if(username==null||username.equals("")){
					JOptionPane.showConfirmDialog(null,"�û�������Ϊ�գ�����������");
					return;
				}else {
                    draw.getUser().setUsername(username);
                    userInfo.setText(username + ",��ӭ�ص���Ϸ");
                }
			}
		});
		
	}
	public Draw getDraw() {
		return draw;
	}
	public void setDraw(Draw draw) {
		this.draw = draw;
	}

}
