package com.huat.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.huat.Util.IOutil;
import com.huat.bean.GameData;
import com.huat.bean.Point;
import com.huat.bean.User;


/**
 * 绘制下棋区域
 *
 */
public class Draw extends JPanel {
	private int move_x=80;
	private int move_y=60;
	private User user;
    private boolean gamestart;// true进行中，false结束
    private int player;// 当前该谁下,1表示白棋，2表示黑棋
	private int[][] piece;// 0表示没棋子，1表示白棋，2表示黑
	private ImageIcon black,white;
    private int gamestate;//游戏状态，1表示人人对弈，2表示人机对弈
    private Socket ss;

	public Draw() {
		piece = new int[15][15];
		//获取类目录的根路径
        user=new User();
		String basePath = this.getClass().getResource("/").getPath();
		black=new ImageIcon(basePath+"black.jpg");
		white=new ImageIcon(basePath+"white.jpg");
		this.setSize(500, 500);
		this.setBackground(new Color(158, 182, 171));
		addListener();

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		printQiPan(g);
		printQiZi(g);
	}

	public void printQiPan(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		// 给边框加粗
		g2d.setStroke(new BasicStroke(3f));
		g2d.drawLine(move_x, move_y, move_x + 420, move_y);
		g2d.drawLine(move_x, move_y, move_x, move_y + 420);
		g2d.drawLine(move_x, move_y + 420, move_x + 420, move_y + 420);
		g2d.drawLine(move_x + 420, move_y, move_x + 420, move_y + 420);
		g2d.setStroke(new BasicStroke());
		//绘制棋盘和外围数字
		for (int i = 0; i < 15; i++) {
			g.drawLine(move_x, i * 30 + move_y, move_x + 420, i * 30 + move_y);
			g.drawString((i + 1) + "", 60, i * 30 + 65);
			g.drawLine(i * 30 + move_x, move_y, i * 30 + move_x, 420 + move_y);
			g.drawString((i + 1) + "", i * 30 + 75, 45);
		}
	}

	public void printQiZi(Graphics g) {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (piece[i][j] == 2) {
					// 画黑棋
					g.drawImage(black.getImage(),30 * i + move_x-15, 30 * j + move_y-15, 30,30,this);
				} else if (piece[i][j] ==1) {
					// 画白棋
					g.drawImage(white.getImage(), 30 * i + move_x-15, 30 * j + move_y-15, 30,30,this);
				}
			}
		}
	}

	public void addListener(){
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!gamestart)return;//游戏未开始
				if(user.getColor()!=player)return;
				int x=e.getX();
				int y=e.getY();
				if (x > 0 + move_x - 10 && x <= 420 + move_x + 10 && y >= 0 + move_y - 10 && y <= 420 + move_y + 10) {
					x = (int) Math.round((x - move_x) / 30.0);
					y = (int) Math.round((y - move_y) / 30.0);
					if (piece[x][y] != 0) return;
					piece[x][y] = player;

					repaint();
					changecolor();
					showGameInfo();
					//人人对弈
                    if(gamestate==1){
                        GameData data=new GameData();
                        data.setUser(user);
                        data.setPoint(new Point(x,y));
                        sendmessage(data);
                    }else if(gamestate==2){

                    }
				}
			}
		});
	}

    /**
     * 连接服务器
     */
	public void connect(){
        try {
            ss=new Socket("localhost",8888);
            GameData d=new GameData();
            d.setUser(user);
            IOutil.writeObject(d,ss.getOutputStream());
            //启动读数据的线程
            new Thread(new Getmessage()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	//给服务器发信息
    public void sendmessage(GameData a){
        try {
            IOutil.writeObject(a,ss.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/**
	 * 改变棋子颜色
	 */
	public void changecolor(){
		if(player==1) player=2;
		else if(player==2) player=1;
	}
	//显示状态信息
	public void showGameInfo(){
		if(player==1){
			QiPan.gameInfo.setText("轮到黑方下棋");
		}else if(player==2){
			QiPan.gameInfo.setText("轮到白方下棋");
		}
	}
//get和set方法
    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
    public boolean isGamestart() {
        return gamestart;
    }
    public void setGamestart(boolean gamestart) {
        this.gamestart = gamestart;
    }
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
    public int getGamestate() {
        return gamestate;
    }
    public void setGamestate(int gamestate) {
        this.gamestate = gamestate;
    }

    //从服务器读数据的线程，内部类
    class Getmessage implements Runnable{
	    public void run(){
	        while(true){
                try {
                    GameData data=(GameData)IOutil.readObject(ss.getInputStream());
                    piece[data.getPoint().getX()][data.getPoint().getY()]=data.getUser().getColor();
                    repaint();
                    changecolor();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
