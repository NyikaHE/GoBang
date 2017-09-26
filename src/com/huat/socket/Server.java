package com.huat.socket;

import com.huat.Util.IOutil;
import com.huat.bean.GameData;
import com.huat.bean.User;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Server {
    private ServerSocket ss;
    private Map<User,Socket> player;//对玩家信息进行储存
    public Server(){
        player= new HashMap<>() ;

        try {
            ss=new ServerSocket(8888);
            while(true) {
                Socket s = ss.accept();//阻塞方法
                System.out.println("helloworld welcom!");
                GameData data=(GameData) IOutil.readObject(s.getInputStream());
                player.put(data.getUser(),s);
                new Thread(new GameServerThread(data.getUser(),s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class GameServerThread implements Runnable{
        private User user;//当前玩家
        private Socket a;

        public GameServerThread(User user, Socket a) {
            this.user = user;
            this.a = a;
        }
        public void run(){
            while(true){
                try {
                    GameData data=(GameData)IOutil.readObject(a.getInputStream());
                    Set<User> users=player.keySet();//将流中获得的用户数据存储到集合中
                    for(User temp:users){
                        if(temp.equals(user))continue;
                        IOutil.writeObject(data,player.get(temp).getOutputStream());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String args[]){
        new Server();
    }
}
