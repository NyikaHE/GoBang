package com.huat.bean;

import java.io.Serializable;

/**
 * 玩家信息
 */
public class User implements Serializable{
	
	private String username;//用户名
	private int color;//棋子颜色

	public User() {
		super();
	}
	public User(String username, int color) {
		super();
		this.username = username;
		this.color = color;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (color != user.color) return false;
        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + color;
        return result;
    }

    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	

}
