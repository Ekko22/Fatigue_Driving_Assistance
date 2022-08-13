package com.martin.ads.omoshiroi.DBServe.Domain;

//实体类
/**
 * Created by py on 2022/8/13.
 */
public class User {
    private int userid;
    private String name;
    private String password;

    public User(int parseInt, String toString, String toString1) {
        this.userid = parseInt;
        this.name = toString;
        this.password = toString1;
    }

    public User() {

    }

    public User(int parseInt, String toString) {
        this.userid = parseInt;
        this.name = toString;
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userid +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
