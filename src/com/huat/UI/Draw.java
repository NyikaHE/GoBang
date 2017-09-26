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
 * ������������
 *
 */
public class Draw extends JPanel {
	private int move_x=80;
	private int move_y=60;
	private User user;
    private boolean gamestart;// true�����У�false����
    private int player;// ��ǰ��˭��,1��ʾ���壬2��ʾ����
	private int[][] piece;// 0��ʾû���ӣ�1��ʾ���壬2��ʾ��
	private ImageIcon black,white;
    private int gamestate;//��Ϸ״̬��1��ʾ���˶��ģ�2��ʾ�˻�����
    private Socket ss;

	public Draw() {
		piece = new int[15][15];
		//��ȡ��Ŀ¼�ĸ�·��
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

		// ���߿�Ӵ�
		g2d.setStroke(new BasicStroke(3f));
		g2d.drawLine(move_x, move_y, move_x + 420, move_y);
		g2d.drawLine(move_x, move_y, move_x, move_y + 420);
		g2d.drawLine(move_x, move_y + 420, move_x + 420, move_y + 420);
		g2d.drawLine(move_x + 420, move_y, move_x + 420, move_y + 420);
		g2d.setStroke(new BasicStroke());
		//�������̺���Χ����
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
					// ������
					g.drawImage(black.getImage(),30 * i + move_x-15, 30 * j + move_y-15, 30,30,this);
				} else if (piece[i][j] ==1) {
					// ������
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
				if(!gamestart)return;//��Ϸδ��ʼ
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
					//���˶���
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
     * ���ӷ�����
     */
	public void connect(){
        try {
            ss=new Socket("localhost",8888);
            GameData d=new GameData();
            d.setUser(user);
            IOutil.writeObject(d,ss.getOutputStream());
            //���������ݵ��߳�
            new Thread(new Getmessage()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	//������������Ϣ
    public void sendmessage(GameData a){
        try {
            IOutil.writeObject(a,ss.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/**
	 * �ı�������ɫ
	 */
	public void changecolor(){
		if(player==1) player=2;
		else if(player==2) player=1;
	}
	//��ʾ״̬��Ϣ
	public void showGameInfo(){
		if(player==1){
			QiPan.gameInfo.setText("�ֵ��ڷ�����");
		}else if(player==2){
			QiPan.gameInfo.setText("�ֵ��׷�����");
		}
	}
//get��set����
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

    //�ӷ����������ݵ��̣߳��ڲ���
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
