package com.martin.ads.omoshiroi.DBServe.Dao;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.martin.ads.omoshiroi.DBServe.Domain.User;
import com.martin.ads.omoshiroi.activity.Login;
import com.martin.ads.omoshiroi.activity.MyInfo;
import com.martin.ads.omoshiroi.activity.Register;

public class UserDao {
    SQLiteDatabase db, dbs;

    @SuppressLint("WrongConstant")
    public UserDao(Register register) {
        //创建数据库
        db = register.openOrCreateDatabase("user2.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        //创建表,userid,name,password
        db.execSQL("create table if not exists user (userid integer primary key, name varchar(20) default null, password varchar(20))");
    }

    @SuppressLint("WrongConstant")
    public UserDao(Login login) {
        //创建数据库
        db = login.openOrCreateDatabase("user2.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        //创建表,userid,name,password
        db.execSQL("create table if not exists user (userid integer primary key, name varchar(20) default null, password varchar(20))");

    }

    public UserDao() {

    }

    @SuppressLint("WrongConstant")
    public UserDao(MyInfo myInfo) {
        //创建数据库
        db = myInfo.openOrCreateDatabase("user2.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
//        //创建表,userid,name,password
//        db.execSQL("create table if not exists user (userid integer primary key, name varchar(20) default null, password varchar(20))");
    }

    //添加用户
    public boolean addUser(User user) {
        if (user == null) {
            return false;
        }
        db.execSQL("insert into user (userid, name, password) values (?, ?, ?)", new Object[]{user.getUserid(), user.getName(), user.getPassword()});
        return true;
    }

    //删除用户
    public void deleteUser(int userid) {
        db.execSQL("delete from user where userid = ?", new Object[]{userid});
    }

    //查询用户
    public User findUser(int userid) {
        User user = new User();
        Cursor cursor = db.rawQuery("select * from user where userid = ?", new String[]{String.valueOf(userid)});
        user.setUserid(userid);
        while (cursor.moveToNext()) {
            user.setName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
        }
        cursor.close();
        return user;
        //数据库关闭

    }

    //判断用户是否存在
    public boolean isExist(int userid) {
        User user = findUser(userid);
        if (user == null) {
            return false;
        }
        return true;
    }
}
