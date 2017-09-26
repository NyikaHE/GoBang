package com.huat.bean;

import java.io.Serializable;

public class GameData implements Serializable{
    private User user;
    private Point point;

    public GameData() {
        super();
    }

    public GameData(User user, Point point) {
        super();
        this.user = user;
        this.point = point;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
