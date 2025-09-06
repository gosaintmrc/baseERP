package com.gosaint.service.permissionManagement.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gosaint.dao.permissionManagement.UsersDao;
import com.gosaint.model.permissionManagement.Users;
import com.gosaint.service.permissionManagement.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDao usersDao;

    @Override
    public int createUser(Users user) {
        return usersDao.insert(user);
    }

    @Override
    public Users getUserById(String userId) {
        return usersDao.selectById(userId);
    }

    @Override
    public Users getUserByUsername(String username) {
        return usersDao.selectByUsername(username);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersDao.selectAll();
    }

    @Override
    public PageInfo<Users> getAllUsersByPage(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Users> users = usersDao.selectAll();
        return new PageInfo<>(users);
    }

    @Override
    public int updateUser(Users user) {
        return usersDao.update(user);
    }

    @Override
    public int deleteUser(String userId) {
        return usersDao.delete(userId);
    }
}