package com.huat.UI;

import com.huat.bean.User;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * 棋盘
 * @author HE
 *
 */
public class QiPan extends JFrame{
	private JMenuBar bar;//菜单栏
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
		super("五子棋大战");
		init();
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//用于关闭窗口
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		int width=(int)d.getWidth();
		int height=(int)d.getHeight();

		this.setLocation((width-600)/2, (height-600)/2);
		this.setVisible(true);

		addListener();
	}
	//对组件初始化
	public void init() {
		bar=new JMenuBar();
		system=new JMenu("系统功能");
		login=new JMenuItem("登录");
		ptp=new JMenu("人人对弈");
		ptc=new JMenu("人机对弈");
		pp_phb=new JMenuItem("人执黑");
		pp_phw=new JMenuItem("人执白");
		pc_phb=new JMenuItem("人执黑");
		pc_phw=new JMenuItem("人执白");
		draw=new Draw();
		userInfo=new JLabel("hello world");
		userInfo.setHorizontalAlignment(JLabel.CENTER);
		gameInfo=new JLabel("白棋下");
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
		//人人对弈中黑方先下
		pp_phb.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
                draw.setGamestate(1);
				draw.getUser().setColor(2);
				draw.setGamestart(true);
				draw.setPlayer(2);
				gameInfo.setText("游戏开始，黑方下棋");
				draw.connect();
			}
			
		});
		//人人对弈中白方线下
		pp_phw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    draw.setGamestate(1);
				draw.getUser().setColor(1);
                draw.setGamestart(true);
                draw.setPlayer(1);
				gameInfo.setText("游戏开始，白方先下");
				draw.connect();
			}
		});
		//人机对弈中人执黑
		pc_phb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                draw.setGamestate(2);
				draw.getUser().setColor(1);
				gameInfo.setText("游戏开始，黑方先下");
			}
		});
		//人机对弈中人执白
		pc_phw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                draw.setGamestate(2);
				draw.getUser().setColor(2);
				gameInfo.setText("游戏开始，白方先下");
			}
		});
		//登陆操作
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username=JOptionPane.showInputDialog("请输入用户名");
				if(username==null||username.equals("")){
					JOptionPane.showConfirmDialog(null,"用户名不能为空，请重新输入");
					return;
				}else {
                    draw.getUser().setUsername(username);
                    userInfo.setText(username + ",欢迎回到游戏");
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
