package com.martin.ads.omoshiroi.DBServe.Service;

import com.martin.ads.omoshiroi.DBServe.Dao.UserDao;
import com.martin.ads.omoshiroi.DBServe.Domain.User;

public class UserService {
    private UserDao userDao;
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    //添加用户
    public boolean addUser(User user) {
        return userDao.addUser(user);
    }
    //删除用户
    public void deleteUser(int userid) {
        userDao.deleteUser(userid);
    }
    //查询用户
    public User findUser(int userid) {
        return userDao.findUser(userid);
    }
}
